// AuthController.java
package com.hexaware.easypay.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.easypay.model.Employee;
import com.hexaware.easypay.model.UserEntity;
import com.hexaware.easypay.model.UserRole;
import com.hexaware.easypay.repository.EmployeeRepository;
import com.hexaware.easypay.repository.UserRepository;
import com.hexaware.easypay.security.JwtService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserDetailsService userDetailsService,
            UserRepository userRepository,
            EmployeeRepository employeeRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // First, verify the user exists
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            
            // Attempt authentication
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
                )
            );

            // If authentication successful, generate token
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(userDetails);
                
                // Get employee ID from UserEntity
                UserEntity user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                
                // Build response
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
                response.put("username", userDetails.getUsername());
                
                // Add employee ID if the user has an associated employee
                if (user.getEmployee() != null) {
                    response.put("employeeId", user.getEmployee().getId());
                }

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid credentials"));
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("User not found: " + e.getMessage()));
        } catch (BadCredentialsException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("Invalid username or password"));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("Authentication failed: " + e.getMessage()));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            // Check if username already exists
            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Username already exists"));
            }

            // Verify employee exists and is active
            Employee employee = employeeRepository.findByEmployeeCodeAndEmail(
                    request.getEmployeeCode(), 
                    request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee code or email"));

            if (!employee.isActive()) {
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Employee is not active"));
            }

            // Create new user entity
            UserEntity user = new UserEntity();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(UserRole.ROLE_EMPLOYEE); // Default role for new registrations
            user.setEmployee(employee);

            // Save user
            userRepository.save(user);

            // Generate JWT token
            String token = jwtService.generateToken(
                User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())))
                    .build()
            );

            return ResponseEntity.ok(new AuthResponse(token));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Registration failed: " + e.getMessage()));
        }
    }
}


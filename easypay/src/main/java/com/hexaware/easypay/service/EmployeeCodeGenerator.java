package com.hexaware.easypay.service;

import java.util.Random;

public class EmployeeCodeGenerator {
    
    public static String generateEmployeeCode() {
        Random random = new Random();
        int number = random.nextInt(9000) + 1000; // generates a number between 1000 and 9999
        return "EMP" + number;
    }
}
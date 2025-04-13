import React, { useEffect, useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { 
  faUsers, 
  faMoneyBillWave, 
  faCalendarAlt, 
  faClipboardList,
  faChartLine,
  faMoneyBill
} from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';
import '../styles/Dashboard.css';
import axiosInstance from '../services/axiosConfig';



const Dashboard = () => {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [dashboardData, setDashboardData] = useState({
      leaveBalance: null,
      payrollData: null,
      pendingRequests: []
  });

  const userRole = localStorage.getItem('role');
  const employeeId = localStorage.getItem('employeeId');

  useEffect(() => {
      const fetchDashboardData = async () => {
          try {
              setLoading(true);
              setError('');
              console.log("User", userRole, employeeId,)
              if (userRole === 'ROLE_EMPLOYEE' && employeeId) {
                  // Fetch employee specific data
                  const [leaveDetails, payroll, leaveRequests] = await Promise.all([
                      axiosInstance.get(`/leave-details/employee/${employeeId}`),
                      axiosInstance.get(`/payrolls/employee/${employeeId}`),
                      axiosInstance.get(`/leave-requests/employee/${employeeId}`)
                  ]);
                  console.log("User Details", leaveDetails, payroll, leaveRequests)
                  setDashboardData({
                      leaveBalance: leaveDetails.data,
                      payrollData: payroll.data[0],
                      pendingRequests: leaveRequests.data.filter(req => req.status === 'PENDING')
                  });
              } else if (userRole === 'ROLE_ADMIN') {
                  // Fetch admin dashboard data
                  const response = await axiosInstance.get('/dashboard/admin-stats');
                  setDashboardData(response.data);
              }
          } catch (err) {
              console.error('Error fetching dashboard data:', err);
              setError('Failed to fetch dashboard data. Please try again later.');
          } finally {
              setLoading(false);
          }
      };

      fetchDashboardData();
  }, [userRole, employeeId]);

  if (loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="error-message">{error}</div>;

  return (
      <div className="dashboard">
          <div className="dashboard-header">
              <h1>{userRole === 'ADMIN' ? 'Admin Dashboard' : 'Employee Dashboard'}</h1>
              
          </div>

          {userRole === 'ADMIN' ? (
              // Admin Dashboard Content
              <>
                  <div className="stats-grid">
                      <StatCard 
                          icon={faUsers}
                          title="Total Employees"
                          value= "6"//{dashboardData.totalEmployees}
                          trend={2.5}
                      />
                      <StatCard 
                          icon={faMoneyBillWave}
                          title="Monthly Payroll"
                          value="₹2,89,450"//{`$${dashboardData.monthlyPayroll}`}
                          trend={-1.2}
                      />
                      <StatCard 
                          icon={faCalendarAlt}
                          title="Leave Requests"
                          value={dashboardData.leaveRequests}
                      />
                      <StatCard 
                          icon={faClipboardList}
                          title="Pending Actions"
                          value={dashboardData.pendingActions}
                      />
                  </div>

                  <div className="dashboard-grid">
                      <RecentActivity activities={dashboardData.recentActivities} />
                      <PendingApprovals approvals={dashboardData.pendingApprovals} />
                  </div>
              </>
          ) : (
              // Employee Dashboard Content
              <>
                  <div className="stats-grid">
                      <StatCard 
                          icon={faCalendarAlt}
                          title="Leave Balance"
                          value={`${dashboardData.leaveBalance?.paidLeavesTotal - 
                                  dashboardData.leaveBalance?.paidLeavesUsed || 0} Days`}
                      />
                      <StatCard 
                          icon={faMoneyBill}
                          title="Latest Salary"
                          value={`₹${dashboardData.payrollData?.netSalary?.toLocaleString() || 0}`}
                      />
                      <StatCard 
                          icon={faClipboardList}
                          title="Pending Requests"
                          value={dashboardData.pendingRequests.length}
                      />
                  </div>

                  <div className="dashboard-grid">
                      <LeaveSummary leaveBalance={dashboardData.leaveBalance} />
                      <LeaveRequestsList requests={dashboardData.pendingRequests} />
                  </div>
              </>
          )}
      </div>
  );
};


const StatCard = ({ icon, title, value, trend }) => {
  return (
    <div className="stat-card">
      <div className="stat-icon">
        <FontAwesomeIcon icon={icon} />
      </div>
      <div className="stat-info">
        <h3>{title}</h3>
        <p className="stat-value">{value}</p>
        {trend && (
          <span className={`trend ${trend > 0 ? 'positive' : 'negative'}`}>
            {trend > 0 ? '↑' : '↓'} {Math.abs(trend)}%
          </span>
        )}
      </div>
    </div>
  );
};

const RecentActivity = () => {
  const activities = [
    { title: "Payroll Processed", time: "2 hours ago", type: "payroll" },
    { title: "New Employee Added", time: "5 hours ago", type: "employee" },
    { title: "Leave Request Approved", time: "1 day ago", type: "leave" }
  ];

  return (
    <div className="dashboard-card">
      <h2>Recent Activity</h2>
      <div className="activity-list">
        {activities.map((activity, index) => (
          <div key={index} className="activity-item">
            <FontAwesomeIcon 
              icon={activity.type === 'payroll' ? faMoneyBillWave : 
                    activity.type === 'employee' ? faUsers : faCalendarAlt} 
              className="activity-icon"
            />
            <div className="activity-details">
              <p className="activity-title">{activity.title}</p>
              <span className="activity-time">{activity.time}</span>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

const PendingApprovals = () => {
  const approvals = [
    { title: "Leave Request - Alzeb Khan", type: "leave" },
    { title: "Leave Request - Alzeb Khan", type: "leave" },
    { title: "Leave Request - Shivam Kumar", type: "leave" }
  ];

  return (
    <div className="dashboard-card">
      <h2>Pending Approvals</h2>
      <div className="approvals-list">
        {approvals.map((approval, index) => (
          <div key={index} className="approval-item">
            <FontAwesomeIcon icon={faClipboardList} className="approval-icon" />
            <span className="approval-title">{approval.title}</span>
            {/* <button className="btn-review">Review</button> */}
          </div>
        ))}
      </div>
    </div>
  );
};

// Helper Components for Dashboard

const LeaveSummary = ({ leaveBalance }) => {
  if (!leaveBalance) return null;

  return (
      <div className="dashboard-card">
          <h2>Leave Summary</h2>
          <div className="leave-summary">
              <div className="leave-type-item">
                  <span>Casual Leaves</span>
                  <div className="leave-count">
                      <span className="used">{leaveBalance.casualLeavesUsed}</span>
                      <span className="divider">/</span>
                      <span className="total">{leaveBalance.casualLeavesTotal}</span>
                  </div>
              </div>
              <div className="leave-type-item">
                  <span>Sick Leaves</span>
                  <div className="leave-count">
                      <span className="used">{leaveBalance.sickLeavesUsed}</span>
                      <span className="divider">/</span>
                      <span className="total">{leaveBalance.sickLeavesTotal}</span>
                  </div>
              </div>
              <div className="leave-type-item">
                  <span>Paid Leaves</span>
                  <div className="leave-count">
                      <span className="used">{leaveBalance.paidLeavesUsed}</span>
                      <span className="divider">/</span>
                      <span className="total">{leaveBalance.paidLeavesTotal}</span>
                  </div>
              </div>
          </div>
      </div>
  );
};

const LeaveRequestsList = ({ requests }) => {
  if (!requests || requests.length === 0) {
      return (
          <div className="dashboard-card">
              <h2>Recent Leave Requests</h2>
              <p className="no-data">No pending leave requests</p>
          </div>
      );
  }

  return (
      <div className="dashboard-card">
          <h2>Recent Leave Requests</h2>
          <div className="leave-requests-list">
              {requests.map(request => (
                  <div key={request.id} className="leave-request-item">
                      <div className="request-info">
                          <div className="request-type">{request.leaveType}</div>
                          <div className="request-dates">
                              {new Date(request.startDate).toLocaleDateString()} - 
                              {new Date(request.endDate).toLocaleDateString()}
                          </div>
                      </div>
                      <span className={`status-badge ${request.status.toLowerCase()}`}>
                          {request.status}
                      </span>
                  </div>
              ))}
          </div>
      </div>
  );
};

export default Dashboard;


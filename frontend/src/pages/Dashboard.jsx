import React from 'react';
import { useAuth } from '../context/AuthContext';
import Navbar from '../components/common/Navbar';
import { Link } from 'react-router-dom';

const Dashboard = () => {
  const { user } = useAuth();

  const getRoleFeatures = (role) => {
    const features = {
      OWNER: [
        { name: 'Manage Hostels', icon: '🏢', description: 'Add, edit, and view all hostels', link: '/hostels' },
        { name: 'Manage Rooms', icon: '🛏️', description: 'Configure rooms and amenities', link: '/rooms' },
        { name: 'View Bookings', icon: '📋', description: 'Track all bookings and reservations', link: '/bookings' },
        { name: 'Payment Reports', icon: '💰', description: 'Monitor payment history', link: '/payments' },
      ],
      WARDEN: [
        { name: 'Manage Bookings', icon: '📋', description: 'Approve and manage bookings', link: '/bookings' },
        { name: 'Handle Complaints', icon: '📝', description: 'Resolve student complaints', link: '/complaints' },
        { name: 'Visitor Management', icon: '👥', description: 'Track visitor check-ins', link: '/visitors' },
        { name: 'Room Status', icon: '🛏️', description: 'View room availability', link: '/rooms' },
      ],
      STUDENT: [
        { name: 'Book Room', icon: '🏠', description: 'Search and book available rooms', link: '/book-room' },
        { name: 'My Bookings', icon: '📋', description: 'View your booking history', link: '/my-bookings' },
        { name: 'Raise Complaint', icon: '📝', description: 'Submit maintenance requests', link: '/complaints' },
        { name: 'Register Visitor', icon: '👥', description: 'Pre-register visitors', link: '/visitors' },
      ],
      GUARD: [
        { name: 'Visitor Check-in', icon: '✅', description: 'Check-in visitors', link: '/visitor-checkin' },
        { name: 'Visitor Check-out', icon: '📤', description: 'Check-out visitors', link: '/visitor-checkout' },
        { name: 'View Visitors', icon: '👥', description: 'View all visitor records', link: '/visitors' },
        { name: 'Emergency Contacts', icon: '📞', description: 'Access emergency contacts', link: '/contacts' },
      ],
    };
    return features[role] || [];
  };

  const features = getRoleFeatures(user?.role);

  const stats = [
    { name: 'Total Users', value: '0', icon: '👥', color: 'bg-blue-500' },
    { name: 'Active Bookings', value: '0', icon: '📋', color: 'bg-green-500' },
    { name: 'Pending Complaints', value: '0', icon: '📝', color: 'bg-yellow-500' },
    { name: 'Today\'s Visitors', value: '0', icon: '👨‍👩‍👧', color: 'bg-purple-500' },
  ];

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />

      <main className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        {/* Welcome Section */}
        <div className="px-4 py-6 sm:px-0">
          <div className="bg-gradient-to-r from-indigo-500 to-purple-600 rounded-lg shadow-lg p-8 text-white mb-8">
            <h1 className="text-3xl font-bold mb-2">
              Welcome back, {user?.fullName}! 👋
            </h1>
            <p className="text-indigo-100">
              You're logged in as <span className="font-semibold">{user?.role}</span>
            </p>
          </div>

          {/* Stats Grid */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
            {stats.map((stat, index) => (
              <div key={index} className="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-gray-600 text-sm font-medium">{stat.name}</p>
                    <p className="text-3xl font-bold text-gray-900 mt-2">{stat.value}</p>
                  </div>
                  <div className={`${stat.color} w-12 h-12 rounded-full flex items-center justify-center text-2xl`}>
                    {stat.icon}
                  </div>
                </div>
              </div>
            ))}
          </div>

          {/* Features Grid */}
          <div className="mb-8">
            <h2 className="text-2xl font-bold text-gray-900 mb-6">Quick Actions</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
              {features.map((feature, index) => (
                <Link
                  key={index}
                  to={feature.link}
                  className="bg-white rounded-lg shadow-md p-6 hover:shadow-xl transition transform hover:-translate-y-1 cursor-pointer"
                >
                  <div className="text-4xl mb-4">{feature.icon}</div>
                  <h3 className="text-lg font-semibold text-gray-900 mb-2">{feature.name}</h3>
                  <p className="text-gray-600 text-sm">{feature.description}</p>
                  <div className="mt-4 text-indigo-600 text-sm font-medium flex items-center">
                    Access now
                    <svg className="w-4 h-4 ml-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
                    </svg>
                  </div>
                </Link>
              ))}
            </div>
          </div>

          {/* Recent Activity (Placeholder) */}
          <div className="bg-white rounded-lg shadow-md p-6">
            <h2 className="text-xl font-semibold text-gray-900 mb-4">Recent Activity</h2>
            <div className="text-center py-12 text-gray-500">
              <svg className="w-16 h-16 mx-auto mb-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
              </svg>
              <p>No recent activity to display</p>
              <p className="text-sm mt-2">Activity will appear here once you start using the system</p>
            </div>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Dashboard;

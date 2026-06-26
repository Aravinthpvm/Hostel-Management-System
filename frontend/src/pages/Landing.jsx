import React from 'react';
import { Link } from 'react-router-dom';

const Landing = () => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 to-white flex items-center">
      <div className="max-w-4xl mx-auto px-6 py-20 text-center">
        <h1 className="text-4xl sm:text-5xl font-extrabold text-gray-900 mb-6">
          Welcome to Hostel Management
        </h1>
        <p className="text-lg text-gray-600 mb-8">
          Manage hostels, rooms and bookings efficiently. Built for owners, wardens and students.
        </p>

        <div className="flex justify-center space-x-4">
          <Link
            to="/register"
            className="inline-flex items-center px-6 py-3 rounded-md bg-indigo-600 text-white font-medium hover:bg-indigo-700 transition"
          >
            Create Account
          </Link>
          <Link
            to="/login"
            className="inline-flex items-center px-6 py-3 rounded-md border border-indigo-600 text-indigo-600 font-medium hover:bg-indigo-50 transition"
          >
            Sign In
          </Link>
        </div>

        <div className="mt-12 text-sm text-gray-500">
          <p>
            Already part of an institution? <Link to="/login" className="text-indigo-600 underline">Sign in</Link> to access your dashboard.
          </p>
        </div>
      </div>
    </div>
  );
};

export default Landing;

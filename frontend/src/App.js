import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import ProtectedRoute from './components/common/ProtectedRoute';
import Login from './pages/Login';
import Register from './pages/Register';
import Landing from './pages/Landing';
import Dashboard from './pages/Dashboard';
import Profile from './pages/Profile';
import HostelList from './pages/hostels/HostelList';
import CreateHostel from './pages/hostels/CreateHostel';
import HostelDetails from './pages/hostels/HostelDetails';
import CreateRoom from './pages/rooms/CreateRoom';

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Landing />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route
            path="/dashboard"
            element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            }
          />
          <Route
            path="/profile"
            element={
              <ProtectedRoute>
                <Profile />
              </ProtectedRoute>
            }
          />
          <Route
            path="/hostels"
            element={
              <ProtectedRoute>
                <HostelList />
              </ProtectedRoute>
            }
          />
          <Route
            path="/hostels/create"
            element={
              <ProtectedRoute allowedRoles={['OWNER']}>
                <CreateHostel />
              </ProtectedRoute>
            }
          />
          <Route
            path="/hostels/:hostelId"
            element={
              <ProtectedRoute>
                <HostelDetails />
              </ProtectedRoute>
            }
          />
          <Route
            path="/hostels/:hostelId/rooms/create"
            element={
              <ProtectedRoute allowedRoles={['OWNER']}>
                <CreateRoom />
              </ProtectedRoute>
            }
          />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;

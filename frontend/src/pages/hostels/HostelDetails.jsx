import React, { useState, useEffect, useCallback } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { hostelService } from '../../services/hostelService';
import { roomService } from '../../services/roomService';
import { useAuth } from '../../context/AuthContext';
import Navbar from '../../components/common/Navbar';

const HostelDetails = () => {
  const { hostelId } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();
  
  const [hostel, setHostel] = useState(null);
  const [rooms, setRooms] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const fetchHostelDetails = useCallback(async () => {
    try {
      const data = await hostelService.getHostelById(hostelId);
      setHostel(data);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to fetch hostel details');
    } finally {
      setLoading(false);
    }
  }, [hostelId]);

  const fetchRooms = useCallback(async () => {
    try {
      const data = await roomService.getRoomsByHostelId(hostelId);
      setRooms(data);
    } catch (err) {
      console.error('Failed to fetch rooms:', err);
    }
  }, [hostelId]);

  useEffect(() => {
    fetchHostelDetails();
    fetchRooms();
  }, [fetchHostelDetails, fetchRooms]);

  const getRoomStatusColor = (status) => {
    const colors = {
      AVAILABLE: 'bg-green-100 text-green-800',
      OCCUPIED: 'bg-blue-100 text-blue-800',
      MAINTENANCE: 'bg-yellow-100 text-yellow-800',
      RESERVED: 'bg-purple-100 text-purple-800',
    };
    return colors[status] || 'bg-gray-100 text-gray-800';
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50">
        <Navbar />
        <div className="flex items-center justify-center h-screen">
          <div className="text-center">
            <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600 mx-auto"></div>
            <p className="mt-4 text-gray-600">Loading hostel details...</p>
          </div>
        </div>
      </div>
    );
  }

  if (!hostel) {
    return (
      <div className="min-h-screen bg-gray-50">
        <Navbar />
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
          <div className="text-center">
            <p className="text-red-600">Hostel not found</p>
            <button
              onClick={() => navigate('/hostels')}
              className="mt-4 text-blue-600 hover:text-blue-800"
            >
              Back to Hostels
            </button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />
      
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Back Button */}
        <div className="mb-6">
          <button
            onClick={() => navigate('/hostels')}
            className="flex items-center text-gray-600 hover:text-gray-900"
          >
            <svg className="w-5 h-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
            </svg>
            Back to Hostels
          </button>
        </div>

        {error && (
          <div className="mb-4 p-4 bg-red-50 border border-red-200 rounded-lg text-red-600">
            {error}
          </div>
        )}

        {/* Hostel Header */}
        <div className="bg-white rounded-lg shadow-md overflow-hidden mb-6">
          {/* Images */}
          <div className="h-64 bg-gradient-to-r from-blue-400 to-purple-500">
            {hostel.images && hostel.images.length > 0 ? (
              <img
                src={hostel.images[0]}
                alt={hostel.name}
                className="w-full h-full object-cover"
              />
            ) : (
              <div className="flex items-center justify-center h-full">
                <svg className="h-32 w-32 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
                </svg>
              </div>
            )}
          </div>

          {/* Details */}
          <div className="p-6">
            <div className="flex justify-between items-start mb-4">
              <div>
                <h1 className="text-3xl font-bold text-gray-900 mb-2">{hostel.name}</h1>
                <p className="text-gray-600 flex items-center">
                  <svg className="w-5 h-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                  </svg>
                  {hostel.address}, {hostel.city}, {hostel.state} - {hostel.pincode}
                </p>
              </div>

              {user?.role === 'OWNER' && (
                <div className="flex gap-2">
                  <button
                    onClick={() => navigate(`/hostels/${hostelId}/rooms/create`)}
                    className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
                  >
                    + Add Room
                  </button>
                  <button
                    onClick={() => navigate(`/hostels/${hostelId}/edit`)}
                    className="px-4 py-2 bg-gray-100 text-gray-700 rounded-lg hover:bg-gray-200 transition-colors"
                  >
                    Edit Hostel
                  </button>
                </div>
              )}
            </div>

            {hostel.description && (
              <p className="text-gray-700 mb-6">{hostel.description}</p>
            )}

            {/* Stats */}
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-6">
              <div className="bg-blue-50 p-4 rounded-lg">
                <p className="text-sm text-gray-600">Total Floors</p>
                <p className="text-2xl font-bold text-blue-600">{hostel.totalFloors}</p>
              </div>
              <div className="bg-green-50 p-4 rounded-lg">
                <p className="text-sm text-gray-600">Total Rooms</p>
                <p className="text-2xl font-bold text-green-600">{hostel.totalRooms || 0}</p>
              </div>
              <div className="bg-purple-50 p-4 rounded-lg">
                <p className="text-sm text-gray-600">Available Rooms</p>
                <p className="text-2xl font-bold text-purple-600">
                  {rooms.filter(r => r.status === 'AVAILABLE').length}
                </p>
              </div>
              <div className="bg-orange-50 p-4 rounded-lg">
                <p className="text-sm text-gray-600">Security Deposit</p>
                <p className="text-2xl font-bold text-orange-600">₹{hostel.securityDeposit || 0}</p>
              </div>
            </div>

            {/* Amenities */}
            {hostel.amenities && hostel.amenities.length > 0 && (
              <div>
                <h3 className="text-lg font-semibold text-gray-900 mb-3">Amenities</h3>
                <div className="flex flex-wrap gap-2">
                  {hostel.amenities.map((amenity, index) => (
                    <span
                      key={index}
                      className="px-3 py-1 bg-blue-100 text-blue-700 rounded-full text-sm"
                    >
                      {amenity}
                    </span>
                  ))}
                </div>
              </div>
            )}
          </div>
        </div>

        {/* Rooms Section */}
        <div className="bg-white rounded-lg shadow-md p-6">
          <div className="flex justify-between items-center mb-6">
            <h2 className="text-2xl font-bold text-gray-900">Rooms</h2>
            {user?.role === 'OWNER' && (
              <Link
                to={`/hostels/${hostelId}/rooms/create`}
                className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
              >
                + Add Room
              </Link>
            )}
          </div>

          {rooms.length === 0 ? (
            <div className="text-center py-12">
              <svg className="mx-auto h-12 w-12 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
              </svg>
              <h3 className="mt-2 text-sm font-medium text-gray-900">No rooms yet</h3>
              <p className="mt-1 text-sm text-gray-500">
                {user?.role === 'OWNER' ? 'Get started by adding a room.' : 'No rooms are available yet.'}
              </p>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              {rooms.map((room) => (
                <div
                  key={room.id}
                  className="border border-gray-200 rounded-lg p-4 hover:shadow-md transition-shadow"
                >
                  <div className="flex justify-between items-start mb-3">
                    <h3 className="text-lg font-semibold text-gray-900">
                      Room {room.roomNumber}
                    </h3>
                    <span className={`px-2 py-1 rounded text-xs font-medium ${getRoomStatusColor(room.status)}`}>
                      {room.status}
                    </span>
                  </div>

                  <div className="space-y-2 text-sm text-gray-600">
                    <p><span className="font-medium">Floor:</span> {room.floorNumber}</p>
                    <p><span className="font-medium">Type:</span> {room.roomType}</p>
                    <p><span className="font-medium">Capacity:</span> {room.capacity} person(s)</p>
                    <p><span className="font-medium">Occupancy:</span> {room.currentOccupancy || 0}/{room.capacity}</p>
                    <p className="text-lg font-semibold text-blue-600">
                      ₹{room.rentPerMonth}/month
                    </p>
                  </div>

                  {room.amenities && room.amenities.length > 0 && (
                    <div className="mt-3">
                      <div className="flex flex-wrap gap-1">
                        {room.amenities.slice(0, 2).map((amenity, index) => (
                          <span
                            key={index}
                            className="px-2 py-1 bg-gray-100 text-gray-600 text-xs rounded"
                          >
                            {amenity}
                          </span>
                        ))}
                        {room.amenities.length > 2 && (
                          <span className="px-2 py-1 bg-gray-100 text-gray-600 text-xs rounded">
                            +{room.amenities.length - 2}
                          </span>
                        )}
                      </div>
                    </div>
                  )}
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default HostelDetails;

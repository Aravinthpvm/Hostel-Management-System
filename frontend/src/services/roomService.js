import api from './api';

export const roomService = {
  // Create a new room
  createRoom: async (roomData) => {
    const response = await api.post('/rooms', roomData);
    return response.data;
  },

  // Get all rooms for a hostel
  getRoomsByHostelId: async (hostelId) => {
    const response = await api.get(`/rooms/hostel/${hostelId}`);
    return response.data;
  },

  // Get available rooms for a hostel
  getAvailableRoomsByHostelId: async (hostelId) => {
    const response = await api.get(`/rooms/hostel/${hostelId}/available`);
    return response.data;
  },

  // Get room by ID
  getRoomById: async (roomId) => {
    const response = await api.get(`/rooms/${roomId}`);
    return response.data;
  },

  // Update room
  updateRoom: async (roomId, roomData) => {
    const response = await api.put(`/rooms/${roomId}`, roomData);
    return response.data;
  },

  // Update room status
  updateRoomStatus: async (roomId, status) => {
    const response = await api.patch(`/rooms/${roomId}/status?status=${status}`);
    return response.data;
  },

  // Delete room
  deleteRoom: async (roomId) => {
    const response = await api.delete(`/rooms/${roomId}`);
    return response.data;
  },
};

import api from './api';

export const hostelService = {
  // Create a new hostel
  createHostel: async (hostelData) => {
    const response = await api.post('/hostels', hostelData);
    return response.data;
  },

  // Get all hostels
  getAllHostels: async () => {
    const response = await api.get('/hostels');
    return response.data;
  },

  // Get hostels owned by the current user
  getMyHostels: async () => {
    const response = await api.get('/hostels/my-hostels');
    return response.data;
  },

  // Get hostel by ID
  getHostelById: async (hostelId) => {
    const response = await api.get(`/hostels/${hostelId}`);
    return response.data;
  },

  // Update hostel
  updateHostel: async (hostelId, hostelData) => {
    const response = await api.put(`/hostels/${hostelId}`, hostelData);
    return response.data;
  },

  // Delete hostel
  deleteHostel: async (hostelId) => {
    const response = await api.delete(`/hostels/${hostelId}`);
    return response.data;
  },
};

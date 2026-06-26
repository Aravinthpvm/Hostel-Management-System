import api from './api';

const userService = {
  getCurrentUser: async () => {
    const response = await api.get('/users/me');
    return response.data;
  },

  updateProfile: async (userData) => {
    const response = await api.put('/users/profile', userData);
    // Update localStorage with new user data
    if (response.data.success && response.data.data) {
      const currentUser = JSON.parse(localStorage.getItem('user') || '{}');
      const updatedUser = {
        ...currentUser,
        fullName: response.data.data.fullName,
        phoneNumber: response.data.data.phoneNumber,
        profileImage: response.data.data.profileImage
      };
      localStorage.setItem('user', JSON.stringify(updatedUser));
    }
    return response.data;
  },

  getUserById: async (userId) => {
    const response = await api.get(`/users/${userId}`);
    return response.data;
  }
};

export default userService;

import api from './api';

const authService = {
    cadastrar: async (credentials) => {
      try {
        const response = await api.post('/auth/signup', credentials);

        return response.data;
      } catch (error) {
        throw error;
      }
    },
}

export default authService;
import api from './api';

const authService = {
    login: async (credentials) => {
      try {
        const response = await api.post('/auth/signin', credentials);

        localStorage.setItem('user_data', JSON.stringify(response.data));

        return response.data;
      } catch (error) {
        throw error;
      }
    },

    logout: () => {
        localStorage.removeItem('user_data');
        if(localStorage.getItem('user_data') == null) {
            return true;
        } else {
            return false;
        }
    },

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
import axios from "axios";

const api = axios.create (
    {
        baseURL: 'http://localhost:80',
        timeout: 10000,
        headers: {
            'Content-Type': 'application/json', 
            'Accept': 'application/json',
        }
    }
)

api.interceptors.request.use(
    config => {
      const userData = localStorage.getItem('user_data');
      if (token) {
        config.headers.Authorization = `Bearer ${userData.accesToken}`;
      }
      return config;
    },
    error => Promise.reject(error)
);

export default api;
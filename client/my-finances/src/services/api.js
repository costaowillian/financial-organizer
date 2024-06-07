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
  async config => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => Promise.reject(error)
);

export default api;
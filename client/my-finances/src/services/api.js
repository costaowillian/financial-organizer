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
    const userData = await JSON.parse(localStorage.getItem('user_data'));
    if (userData && userData.accessToken) {
      config.headers.Authorization = `Bearer ${userData.accessToken}`;
    }
    return config;
  },
  error => Promise.reject(error)
);

export default api;
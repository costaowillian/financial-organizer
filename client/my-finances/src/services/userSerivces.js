import api from './api';

const userService = {
  get: (url) => api.get(url),
};

export default userService;
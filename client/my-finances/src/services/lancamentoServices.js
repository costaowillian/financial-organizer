import api from './api';

const lancamentoService = {
    get: (url) => api.get(url),
    post: (url, data) => api.post(url, data),
    patch: (url,  data) => api.patch(url, data),
    delete: (url) => api.delete(url),
    put: (url) => api.put(url)
}

export default lancamentoService;
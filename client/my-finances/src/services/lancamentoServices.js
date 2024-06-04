import api from './api';

const lancamentoService = {
    get: (url) => api.get(url),
    post: (data) => api.post(data),
    patch: (data) => api.patch(data),
    delete: (url) => api.delete(url),
    put: (url) => api.put(url)
}

export default lancamentoService;
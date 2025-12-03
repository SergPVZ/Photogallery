import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Вспомогательная функция для форматирования даты
const formatDateTime = (dateString) => {
    if (!dateString) return new Date().toISOString();
    return new Date(dateString).toISOString();
};

// Фотографы
export const photographerService = {
    getAll: () => api.get('/photographer'),
    getById: (id) => api.get(`/photographer/${id} получить по id`),
    getByName: (firstName, lastName) =>
        api.get(`/photographer/получить фотографа по имени?firstName=${firstName}&lastName=${lastName}`),
    create: (data) => {
        const requestData = {
            firstName: data.firstName,
            lastName: data.lastName,
            email: data.email,
            phone: data.phone,
            address: data.address,
            city: data.city,
            registrationDate: formatDateTime(data.registrationDate),
            isActive: data.isActive !== undefined ? data.isActive : true,
            updatedAt: formatDateTime(data.updatedAt)
        };
        console.log('Sending photographer data:', requestData);
        return api.post('/photographer/добавить нового фотографа', requestData);
    },

    update: (id, data) => {
        const requestData = {
            firstName: data.firstName,
            lastName: data.lastName,
            email: data.email,
            phone: data.phone,
            address: data.address,
            city: data.city,
            registrationDate: formatDateTime(data.registrationDate),
            isActive: data.isActive,
            updatedAt: formatDateTime(data.updatedAt)
        };
        return api.put(`/photographer/${id}`, requestData);
    },
    delete: (id) => api.delete(`/photographer/${id}`),
};

// Фотографии
export const photoService = {
    getById: (id) => api.get(`/photo/${id} получить фотографию по id`),
    create: (data) => {
        const requestData = {
            name: data.name,
            genrePictures: data.genrePictures,
            uploadDate: formatDateTime(data.uploadDate),
            updateAt: formatDateTime(data.updateAt)
        };
        return api.post('/photo/добавить новую фотографию', requestData);
    },
};

// Пользователи
export const viewerService = {
    getById: (id) => api.get(`/${id}`),
    create: (data) => {
        const requestData = {
            name: data.name,
            email: data.email,
            city: data.city,
            registrationDate: formatDateTime(data.registrationDate),
            isActive: data.isActive !== undefined ? data.isActive : true,
            updatedAt: formatDateTime(data.updatedAt)
        };
        console.log('Sending viewer data:', requestData);
        return api.post('/добавления нового пользователя', requestData);
    },
    update: (id, data) => {
        const requestData = {
            name: data.name,
            email: data.email,
            city: data.city,
            registrationDate: formatDateTime(data.registrationDate),
            isActive: data.isActive,
            updatedAt: formatDateTime(data.updatedAt)
        };
        return api.put(`/${id}`, requestData);
    },
    delete: (id) => api.delete(`/${id}`),
};

export default api;
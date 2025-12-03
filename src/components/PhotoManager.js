// src/components/PhotoManager.js
import React, { useState } from 'react';
import { photoService } from '../services/api';
import './PhotoManager.css';

const PhotoManager = () => {
    const [formData, setFormData] = useState({
        name: '',
        genrePictures: '',
        uploadDate: new Date().toISOString().slice(0, 16),
        updateAt: new Date().toISOString().slice(0, 16)
    });
    const [searchId, setSearchId] = useState('');
    const [currentPhoto, setCurrentPhoto] = useState(null);

    const loadPhoto = async (id) => {
        try {
            const response = await photoService.getById(id);
            setCurrentPhoto(response.data);
        } catch (error) {
            alert('Фотография не найдена');
            console.error('Error loading photo:', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await photoService.create(formData);
            alert('Фотография успешно добавлена!');
            setFormData({
                name: '',
                genrePictures: '',
                uploadDate: new Date().toISOString().slice(0, 16),
                updateAt: new Date().toISOString().slice(0, 16)
            });
            loadPhoto(response.data.id);
        } catch (error) {
            alert('Ошибка при добавлении фотографии');
            console.error('Error creating photo:', error);
        }
    };

    return (
        <div className="photo-manager">
            <div className="manager-container">
                <div className="form-section">
                    <h2>Добавить новую фотографию</h2>
                    <form onSubmit={handleSubmit} className="photo-form">
                        <input
                            type="text"
                            placeholder="Название фотографии"
                            value={formData.name}
                            onChange={(e) => setFormData({...formData, name: e.target.value})}
                            required
                        />

                        <select
                            value={formData.genrePictures}
                            onChange={(e) => setFormData({...formData, genrePictures: e.target.value})}
                            required
                        >
                            <option value="">Выберите жанр</option>
                            <option value="Портрет">Портрет</option>
                            <option value="Пейзаж">Пейзаж</option>
                            <option value="Натюрморт">Натюрморт</option>
                            <option value="Архитектура">Архитектура</option>
                            <option value="Макросъемка">Макросъемка</option>
                            <option value="Репортаж">Репортаж</option>
                        </select>

                        <label>
                            Дата загрузки:
                            <input
                                type="datetime-local"
                                value={formData.uploadDate}
                                onChange={(e) => setFormData({...formData, uploadDate: e.target.value})}
                                required
                            />
                        </label>

                        <button type="submit" className="btn-primary">Добавить фотографию</button>
                    </form>
                </div>

                <div className="search-section">
                    <h2>Поиск фотографии по ID</h2>
                    <div className="search-container">
                        <input
                            type="text"
                            placeholder="Введите ID фотографии"
                            value={searchId}
                            onChange={(e) => setSearchId(e.target.value)}
                            className="search-input"
                        />
                        <button
                            onClick={() => loadPhoto(searchId)}
                            className="btn-secondary"
                        >
                            Найти
                        </button>
                    </div>

                    {currentPhoto && (
                        <div className="photo-details">
                            <h3>Информация о фотографии</h3>
                            <div className="details-grid">
                                <div><strong>ID:</strong> {currentPhoto.id}</div>
                                <div><strong>Название:</strong> {currentPhoto.name}</div>
                                <div><strong>Жанр:</strong> {currentPhoto.genrePictures}</div>
                                <div><strong>Дата загрузки:</strong> {new Date(currentPhoto.uploadDate).toLocaleString()}</div>
                                <div><strong>Обновлено:</strong> {new Date(currentPhoto.updateAt).toLocaleString()}</div>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default PhotoManager;

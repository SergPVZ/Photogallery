import React, { useState } from 'react';
import { viewerService } from '../services/api';
import './ViewerManager.css';

const ViewerManager = () => {
    const [formData, setFormData] = useState({
        name: '',
        email: '',
        city: '',
        registrationDate: new Date().toISOString().slice(0, 16),
        isActive: true,
        updatedAt: new Date().toISOString().slice(0, 16)
    });
    const [searchId, setSearchId] = useState('');
    const [currentViewer, setCurrentViewer] = useState(null);

    const loadViewer = async (id) => {
        try {
            const response = await viewerService.getById(id);
            setCurrentViewer(response.data);
        } catch (error) {
            alert('Пользователь не найден');
            console.error('Error loading viewer:', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await viewerService.create(formData);
            alert('Пользователь успешно создан!');
            setFormData({
                name: '',
                email: '',
                city: '',
                registrationDate: new Date().toISOString().slice(0, 16),
                isActive: true,
                updatedAt: new Date().toISOString().slice(0, 16)
            });
            loadViewer(response.data.id);
        } catch (error) {
            alert('Ошибка при создании пользователя');
            console.error('Error creating viewer:', error);
        }
    };

    const handleUpdate = async (id) => {
        try {
            await viewerService.update(id, formData);
            alert('Пользователь успешно обновлен!');
            loadViewer(id);
        } catch (error) {
            alert('Ошибка при обновлении пользователя');
            console.error('Error updating viewer:', error);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Вы уверены, что хотите удалить пользователя?')) {
            try {
                await viewerService.delete(id);
                alert('Пользователь успешно удален!');
                setCurrentViewer(null);
                setSearchId('');
            } catch (error) {
                alert('Ошибка при удалении пользователя');
                console.error('Error deleting viewer:', error);
            }
        }
    };

    return (
        <div className="viewer-manager">
            <div className="manager-container">
                <div className="form-section">
                    <h2>Добавить нового пользователя</h2>
                    <form onSubmit={handleSubmit} className="viewer-form">
                        <input
                            type="text"
                            placeholder="Имя пользователя"
                            value={formData.name}
                            onChange={(e) => setFormData({...formData, name: e.target.value})}
                            required
                        />

                        <div className="form-row">
                            <input
                                type="email"
                                placeholder="Email"
                                value={formData.email}
                                onChange={(e) => setFormData({...formData, email: e.target.value})}
                                required
                            />

                            <input
                                type="text"
                                placeholder="Город"
                                value={formData.city}
                                onChange={(e) => setFormData({...formData, city: e.target.value})}
                                required
                            />
                        </div>

                        <div className="form-row">
                            <label>
                                Дата регистрации:
                                <input
                                    type="datetime-local"
                                    value={formData.registrationDate}
                                    onChange={(e) => setFormData({...formData, registrationDate: e.target.value})}
                                    required
                                />
                            </label>

                            <label className="checkbox-label">
                                <input
                                    type="checkbox"
                                    checked={formData.isActive}
                                    onChange={(e) => setFormData({...formData, isActive: e.target.checked})}
                                />
                                Активен
                            </label>
                        </div>

                        <button type="submit" className="btn-primary">Создать пользователя</button>
                    </form>
                </div>

                <div className="search-section">
                    <h2>Поиск пользователя по ID</h2>
                    <div className="search-container">
                        <input
                            type="text"
                            placeholder="Введите ID пользователя"
                            value={searchId}
                            onChange={(e) => setSearchId(e.target.value)}
                            className="search-input"
                        />
                        <button
                            onClick={() => loadViewer(searchId)}
                            className="btn-secondary"
                        >
                            Найти
                        </button>
                    </div>

                    {currentViewer && (
                        <div className="viewer-details">
                            <h3>Информация о пользователе</h3>
                            <div className="details-grid">
                                <div><strong>ID:</strong> {currentViewer.id}</div>
                                <div><strong>Имя:</strong> {currentViewer.name}</div>
                                <div><strong>Email:</strong> {currentViewer.email}</div>
                                <div><strong>Город:</strong> {currentViewer.city}</div>
                                <div><strong>Дата регистрации:</strong> {new Date(currentViewer.registrationDate).toLocaleString()}</div>
                                <div><strong>Статус:</strong> {currentViewer.active ? 'Активен' : 'Неактивен'}</div>
                            </div>

                            <div className="action-buttons">
                                <button
                                    onClick={() => handleUpdate(currentViewer.id)}
                                    className="btn-warning"
                                >
                                    Обновить
                                </button>
                                <button
                                    onClick={() => handleDelete(currentViewer.id)}
                                    className="btn-danger"
                                >
                                    Удалить
                                </button>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default ViewerManager;

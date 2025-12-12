import React, { useState } from 'react';
import { photographerService } from '../services/api';

const PhotographerManager = () => {
    const [photographers, setPhotographers] = useState([]);
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        address: '',
        city: '',
        registrationDate: new Date().toISOString().slice(0, 16),
        isActive: true
    });
    const [searchId, setSearchId] = useState('');
    const [searchName, setSearchName] = useState({
        firstName: '',
        lastName: ''
    });
    const [currentPhotographer, setCurrentPhotographer] = useState(null);
    const [searchResults, setSearchResults] = useState([]);

    const loadPhotographer = async (id) => {
        try {
            const response = await photographerService.getById(id);
            setCurrentPhotographer(response.data);
            setSearchResults([]); // Очищаем результаты поиска по имени
        } catch (error) {
            alert('Фотограф не найден');
            console.error('Error loading photographer:', error);
        }
    };

    const searchByName = async () => {
        if (!searchName.firstName.trim() || !searchName.lastName.trim()) {
            alert('Пожалуйста, введите имя и фамилию для поиска');
            return;
        }

        try {
            const response = await photographerService.getByName(
                searchName.firstName,
                searchName.lastName
            );
            setSearchResults(response.data);
            setCurrentPhotographer(null); // Очищаем детали одного фотографа
        } catch (error) {
            alert('Фотографы не найдены');
            console.error('Error searching photographers:', error);
            setSearchResults([]);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await photographerService.create(formData);
            alert('Фотограф успешно создан!');
            setFormData({
                firstName: '',
                lastName: '',
                email: '',
                phone: '',
                address: '',
                city: '',
                registrationDate: new Date().toISOString().slice(0, 16),
                isActive: true
            });
            loadPhotographer(response.data.id);
        } catch (error) {
            alert('Ошибка при создании фотографа');
            console.error('Error creating photographer:', error);
        }
    };

    const handleUpdate = async (id) => {
        try {
            await photographerService.update(id, formData);
            alert('Фотограф успешно обновлен!');
            loadPhotographer(id);
        } catch (error) {
            alert('Ошибка при обновлении фотографа');
            console.error('Error updating photographer:', error);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Вы уверены, что хотите удалить фотографа?')) {
            try {
                await photographerService.delete(id);
                alert('Фотограф успешно удален!');
                setCurrentPhotographer(null);
                setSearchId('');
                setSearchResults([]);
            } catch (error) {
                alert('Ошибка при удалении фотографа');
                console.error('Error deleting photographer:', error);
            }
        }
    };

    const loadPhotographerFromSearch = (photographer) => {
        setCurrentPhotographer(photographer);
        setSearchId(photographer.id);
    };

    return (
        <div className="photographer-manager">
            <div className="manager-container">
                <div className="form-section">
                    <h2>Добавить нового фотографа</h2>
                    <form onSubmit={handleSubmit} className="photographer-form">
                        <div className="form-row">
                            <input
                                type="text"
                                placeholder="Имя"
                                value={formData.firstName}
                                onChange={(e) => setFormData({...formData, firstName: e.target.value})}
                                required
                            />
                            <input
                                type="text"
                                placeholder="Фамилия"
                                value={formData.lastName}
                                onChange={(e) => setFormData({...formData, lastName: e.target.value})}
                                required
                            />
                        </div>

                        <div className="form-row">
                            <input
                                type="email"
                                placeholder="Email"
                                value={formData.email}
                                onChange={(e) => setFormData({...formData, email: e.target.value})}
                                required
                            />
                            <input
                                type="tel"
                                placeholder="Телефон"
                                value={formData.phone}
                                onChange={(e) => setFormData({...formData, phone: e.target.value})}
                                required
                            />
                        </div>

                        <input
                            type="text"
                            placeholder="Адрес"
                            value={formData.address}
                            onChange={(e) => setFormData({...formData, address: e.target.value})}
                            required
                        />

                        <input
                            type="text"
                            placeholder="Город"
                            value={formData.city}
                            onChange={(e) => setFormData({...formData, city: e.target.value})}
                            required
                        />

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

                        <button type="submit" className="btn-primary">Создать фотографа</button>
                    </form>
                </div>

                <div className="search-section">
                    <h2>Поиск фотографов</h2>

                    {/* Поиск по ID */}
                    <div className="search-group">
                        <h3>Поиск по ID</h3>
                        <div className="search-container">
                            <input
                                type="text"
                                placeholder="Введите ID фотографа"
                                value={searchId}
                                onChange={(e) => setSearchId(e.target.value)}
                                className="search-input"
                            />
                            <button
                                onClick={() => loadPhotographer(searchId)}
                                className="btn-secondary"
                            >
                                Найти по ID
                            </button>
                        </div>
                    </div>

                    {/* Поиск по имени и фамилии */}
                    <div className="search-group">
                        <h3>Поиск по имени и фамилии</h3>
                        <div className="search-container">
                            <input
                                type="text"
                                placeholder="Имя"
                                value={searchName.firstName}
                                onChange={(e) => setSearchName({...searchName, firstName: e.target.value})}
                                className="search-input"
                            />
                            <input
                                type="text"
                                placeholder="Фамилия"
                                value={searchName.lastName}
                                onChange={(e) => setSearchName({...searchName, lastName: e.target.value})}
                                className="search-input"
                            />
                            <button
                                onClick={searchByName}
                                className="btn-secondary"
                            >
                                Найти по имени
                            </button>
                        </div>
                    </div>

                    {/* Результаты поиска по имени */}
                    {searchResults.length > 0 && (
                        <div className="search-results">
                            <h3>Найдено фотографов: {searchResults.length}</h3>
                            <div className="results-list">
                                {searchResults.map(photographer => (
                                    <div key={photographer.id} className="result-item">
                                        <div className="result-info">
                                            <strong>{photographer.firstName} {photographer.lastName}</strong>
                                            <div>Email: {photographer.email}</div>
                                            <div>Телефон: {photographer.phone}</div>
                                            <div>Город: {photographer.city}</div>
                                        </div>
                                        <button
                                            onClick={() => loadPhotographerFromSearch(photographer)}
                                            className="btn-secondary"
                                        >
                                            Подробнее
                                        </button>
                                    </div>
                                ))}
                            </div>
                        </div>
                    )}

                    {/* Детали фотографа */}
                    {currentPhotographer && (
                        <div className="photographer-details">
                            <h3>Информация о фотографе</h3>
                            <div className="details-grid">
                                <div><strong>ID:</strong> {currentPhotographer.id}</div>
                                <div><strong>Имя:</strong> {currentPhotographer.firstName}</div>
                                <div><strong>Фамилия:</strong> {currentPhotographer.lastName}</div>
                                <div><strong>Email:</strong> {currentPhotographer.email}</div>
                                <div><strong>Телефон:</strong> {currentPhotographer.phone}</div>
                                <div><strong>Адрес:</strong> {currentPhotographer.address}</div>
                                <div><strong>Город:</strong> {currentPhotographer.city}</div>
                                <div><strong>Дата регистрации:</strong> {new Date(currentPhotographer.registrationDate).toLocaleString()}</div>
                                <div><strong>Статус:</strong> {currentPhotographer.active ? 'Активен' : 'Неактивен'}</div>
                            </div>

                            <div className="action-buttons">
                                <button
                                    onClick={() => handleUpdate(currentPhotographer.id)}
                                    className="btn-warning"
                                >
                                    Обновить
                                </button>
                                <button
                                    onClick={() => handleDelete(currentPhotographer.id)}
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

export default PhotographerManager;

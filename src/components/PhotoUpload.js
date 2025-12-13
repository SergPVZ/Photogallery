import React, {useState} from "react";
import {photographerService} from "../services/api";
import './PhotoUpload.css';

const PhotoUpload = () => {
    const [selectedFile, setSelectedFile] = useState(null);
    const [uploadData, setUploadData] = useState({
        photographerId: '',
        description: '',
        tags: ''
    })
    const [photographers, setPhotographers] = useState([]);
    const [uploadProgress, setUploadProgress]= useState(0);
    const [isUploading, setIsUploading]= useState(false);
    const [uploadedPhoto, setUploadedPhoto]= useState(null);

    // Загрузка списка фотографий
    React.useEffect(() => {
        const loadPhotographers = async() => {
            try {                                           // возможно удалить
                const response = await photographerService.getAll()
                setPhotographers(response.data);
            } catch (error) {
                console.error('Ошибка загрузки: ' + error);
            }
        };
        loadPhotographers();
    }, [])

    const handleFileSelect = (e) => {
        const file = e.target.files[0];
        if (file) {
            if (!file.type.match('image.*')) {
                alert('Выберете файл');
                return;
            }
            if (file.size > 10 *1024 *1024) {
                alert('Файл больше 10 МБ. Выберете другой');
                return;
            }
            setSelectedFile(file);
            setUploadedPhoto(null);
        }
    };

    const handleUpload = async (e) => {
        e.preventDefault();
        if (!selectedFile) {
            alert('Выберете файл для загрузки');
            return;
        }
        if (!uploadData.photographerId) {
            alert('Выберете фотографа');
            return;
        }

        const formData = new FormData();
        formData.append('file', selectedFile);
        formData.append('photographerId', uploadData.photographerId);
        formData.append('description', uploadData.description);
        formData.append('tags', uploadData.tags);

        setIsUploading(true);
        setUploadProgress(0);

        try {
            // Симуляция прогресса загрузки
            const progressInterval = setInterval(() => {
                setUploadProgress(prev => {
                    if (prev >= 90) {
                        clearInterval(progressInterval);
                        return prev;
                    }
                    return prev + 10;
                });
            }, 200);

            const response = await fetch('http://localhost:8080/api/photos/upload', {
                method: 'POST',
                body: formData,
            });

            clearInterval(progressInterval);
            setUploadProgress(100);

            if (response.ok) {
                const data = await response.json();
                setUploadedPhoto(data);
                alert('Фото успешно загружено!');

                // Сброс формы
                setSelectedFile(null);
                setUploadData({
                    photographerId: '',
                    description: '',
                    tags: ''
                });
                document.getElementById('file-input').value = '';
            } else {
                throw new Error('Ошибка загрузки файла');
            }
        } catch (error) {
            console.error('Upload error:', error);
            alert('Ошибка при загрузке фото: ' + error.message);
        } finally {
            setIsUploading(false);
            setTimeout(() => setUploadProgress(0), 1000);
        }
    };

    const handleDragOver = (e) => {
        e.preventDefault();
        e.stopPropagation();
    };

    const handleDrop = (e) => {
        e.preventDefault();
        e.stopPropagation();

        const files = e.dataTransfer.files;
        if (files.length > 0) {
            handleFileSelect({ target: { files } });
        }
    };

    return (
        <div className="photo-upload">
            <div className="upload-container">
                <h2>Загрузка фотографий</h2>

                <form onSubmit={handleUpload} className="upload-form">
                    {/* Область для перетаскивания файлов */}
                    <div
                        className={`drop-zone ${selectedFile ? 'has-file' : ''}`}
                        onDragOver={handleDragOver}
                        onDrop={handleDrop}
                        onClick={() => document.getElementById('file-input').click()}
                    >
                        <input
                            id="file-input"
                            type="file"
                            accept="image/*"
                            onChange={handleFileSelect}
                            style={{ display: 'none' }}
                        />

                        {selectedFile ? (
                            <div className="file-info">
                                <div className="file-preview">
                                    {selectedFile.type.includes('image') && (
                                        <img
                                            src={URL.createObjectURL(selectedFile)}
                                            alt="Preview"
                                            className="preview-image"
                                        />
                                    )}
                                </div>
                                <div className="file-details">
                                    <strong>{selectedFile.name}</strong>
                                    <div>Размер: {(selectedFile.size / 1024 / 1024).toFixed(2)} MB</div>
                                    <div>Тип: {selectedFile.type}</div>
                                </div>
                            </div>
                        ) : (
                            <div className="drop-zone-content">
                                <div className="upload-icon">📁</div>
                                <p>Перетащите фото сюда или нажмите для выбора</p>
                                <p className="hint">Поддерживаются: JPG, PNG, GIF (до 10MB)</p>
                            </div>
                        )}
                    </div>

                    {/* Информация о фотографе */}
                    <div className="form-group">
                        <label>ID фотографа:</label>
                        <input
                            type="text"
                            value={uploadData.photographerId}
                            onChange={(e) => setUploadData({...uploadData, photographerId: e.target.value})}
                            placeholder="Введите ID фотографа"
                            required
                        />
                    </div>

                    {/* Описание */}
                    <div className="form-group">
                        <label>Описание:</label>
                        <textarea
                            value={uploadData.description}
                            onChange={(e) => setUploadData({...uploadData, description: e.target.value})}
                            placeholder="Описание фото"
                            rows="5"
                        />
                    </div>

                    {/* Теги */}
                    <div className="form-group">
                        <label>Теги (через запятую):</label>
                        <input
                            type="text"
                            value={uploadData.tags}
                            onChange={(e) => setUploadData({...uploadData, tags: e.target.value})}
                            placeholder="портрет, пейзаж, натюрморт, лето, стритфото, зима, макросьёмка, репортаж"
                        />
                    </div>

                    {/* Прогресс загрузки */}
                    {isUploading && (
                        <div className="upload-progress">
                            <div className="progress-bar">
                                <div
                                    className="progress-fill"
                                    style={{ width: `${uploadProgress}%` }}
                                ></div>
                            </div>
                            <div className="progress-text">{uploadProgress}%</div>
                        </div>
                    )}

                    {/* Кнопка загрузки */}
                    <button
                        type="submit"
                        className="btn-upload"
                        disabled={isUploading || !selectedFile}
                    >
                        {isUploading ? 'Загрузка...' : 'Загрузить фото'}
                    </button>
                </form>

                {/* Информация о загруженном фото */}
                {uploadedPhoto && (
                    <div className="upload-success">
                        <h3>✅ Фото успешно загружено!</h3>
                        <div className="uploaded-info">
                            <div><strong>Имя файла:</strong> {uploadedPhoto.originalFilename}</div>
                            <div><strong>Размер:</strong> {(uploadedPhoto.size / 1024).toFixed(2)} KB</div>
                            <div><strong>Дата загрузки:</strong> {new Date(uploadedPhoto.uploadDate).toLocaleString()}</div>
                            <div><strong>ID:</strong> {uploadedPhoto.id}</div>

                            {uploadedPhoto.fileUrl && (
                                <div className="photo-link">
                                    <a
                                        href={uploadedPhoto.fileUrl}
                                        target="_blank"
                                        rel="noopener noreferrer"
                                    >
                                        Просмотреть фото
                                    </a>
                                </div>
                            )}
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default PhotoUpload;
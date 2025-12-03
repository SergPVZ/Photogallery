// src/App.js
import React, { useState } from 'react';
import './App.css';
import PhotographerManager from './components/PhotographerManager';
import PhotoManager from './components/PhotoManager';
import ViewerManager from './components/ViewerManager';

function App() {
    const [activeTab, setActiveTab] = useState('photographers');


    return (
        <div className="App">
            <header className="app-header">
                <h1>Фото Галерея</h1>
                <nav className="nav-tabs">
                    <button
                        className={activeTab === 'photographers' ? 'active' : ''}
                        onClick={() => setActiveTab('photographers')}
                    >

                        Фотографы
                    </button>
                    <button
                        className={activeTab === 'photos' ? 'active' : ''}
                        onClick={() => setActiveTab('photos')}
                    >

                        Фотографии
                    </button>
                    <button
                        className={activeTab === 'viewers' ? 'active' : ''}
                        onClick={() => setActiveTab('viewers')}
                    >
                        Пользователи
                    </button>
                </nav>
            </header>

            <main className="app-main">
                {activeTab === 'photographers' && <PhotographerManager />}
                {activeTab === 'photos' && <PhotoManager />}
                {activeTab === 'viewers' && <ViewerManager />}
            </main>
        </div>
    );
}

export default App;

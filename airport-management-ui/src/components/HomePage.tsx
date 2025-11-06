import React from 'react';
import { useNavigate } from 'react-router-dom';
import './HomePage.css';

const HomePage: React.FC = () => {
    const navigate = useNavigate();

    return (
        <div className="home-page">
            <div className="hero-section">
                <h1>Airport Management System</h1>
                <p>Manage flights and passengers efficiently</p>
            </div>

            <div className="dashboard-cards">
                <div className="dashboard-card" onClick={() => navigate('/flights')}>
                    <div className="card-icon">✈️</div>
                    <h2>Flights Management</h2>
                    <p>Manage flight schedules, status, gates, and terminals</p>
                    <ul>
                        <li>View all flights</li>
                        <li>Create new flights</li>
                        <li>Update flight status</li>
                        <li>Manage gates & terminals</li>
                    </ul>
                    <button className="card-button">Manage Flights</button>
                </div>

                <div className="dashboard-card" onClick={() => navigate('/passengers')}>
                    <div className="card-icon">👥</div>
                    <h2>Passengers Management</h2>
                    <p>Manage passenger information and bookings</p>
                    <ul>
                        <li>View all passengers</li>
                        <li>Add new passengers</li>
                        <li>Search passengers</li>
                        <li>Update passenger details</li>
                    </ul>
                    <button className="card-button">Manage Passengers</button>
                </div>
            </div>
        </div>
    );
};

export default HomePage;
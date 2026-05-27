import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useCart } from '../context/CartContext';
import './Css/styles.css';
import './Css/premium.css';
import Footer from './Footer';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

function UserProfile({ userId, token, onBack }) {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [editMode, setEditMode] = useState(false);
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        homeAddress: '',
        email: ''
    });

    const [message, setMessage] = useState({ text: '', type: '' });

    const authHeader = { headers: { Authorization: `Bearer ${token}` } };

    useEffect(() => {
        fetchUserData();
    }, [userId]);

    const fetchUserData = async () => {
        try {
            const res = await axios.get(`${API_BASE_URL}/users/${userId}`, authHeader);
            setUser(res.data);
            setFormData({
                firstName: res.data.firstName,
                lastName: res.data.lastName,
                homeAddress: res.data.homeAddress,
                email: res.data.email
            });
            setLoading(false);
        } catch (err) {
            console.error("Errore caricamento dati utente:", err);
            setLoading(false);
        }
    };

    const handleProfileUpdate = async (e) => {
        e.preventDefault();
        try {
            const res = await axios.put(`${API_BASE_URL}/users/${userId}`, formData, authHeader);
            setUser(res.data);
            setEditMode(false);
            showMessage("Profilo aggiornato con successo!", "success");
        } catch (err) {
            showMessage("Errore durante l'aggiornamento.", "error");
        }
    };

    const showMessage = (text, type) => {
        setMessage({ text, type });
        setTimeout(() => setMessage({ text: '', type: '' }), 3000);
    };

    if (loading) return <div className="uv-loading"><div className="uv-loading-spinner"></div></div>;

    return (
        <div className="uv-page">
            <header className="uv-topbar">
                <div className="uv-topbar-left">
                    <button className="uv-btn uv-btnSecondary" onClick={onBack}>← Torna allo Store</button>
                    <div className="uv-title" style={{ marginLeft: '20px' }}>Il Tuo Profilo</div>
                </div>
            </header>

            <main className="uv-content" style={{ display: 'flex', flexDirection: 'column', gap: '40px', padding: '40px 20px', maxWidth: '1000px', margin: '0 auto' }}>

                {message.text && (
                    <div className={`uv-toast ${message.type}`} style={{ position: 'static', transform: 'none', margin: '0 auto' }}>
                        {message.text}
                    </div>
                )}

                <section className="uv-profile-section" style={{ background: '#fff', padding: '30px', borderRadius: '16px', boxShadow: '0 4px 20px rgba(0,0,0,0.05)' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '24px' }}>
                        <h2 style={{ margin: 0 }}>Dati Personali</h2>
                        <button className="uv-btn uv-btnSecondary" onClick={() => setEditMode(!editMode)}>
                            {editMode ? 'Annulla' : 'Modifica'}
                        </button>
                    </div>

                    {!editMode ? (
                        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
                            <div><strong>Nome:</strong> <p>{user.firstName}</p></div>
                            <div><strong>Cognome:</strong> <p>{user.lastName}</p></div>
                            <div><strong>Email:</strong> <p>{user.email}</p></div>
                            <div><strong>Indirizzo:</strong> <p>{user.homeAddress}</p></div>
                        </div>
                    ) : (
                        <form onSubmit={handleProfileUpdate} style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
                            <div>
                                <label className="uv-payLabel">Nome</label>
                                <input className="uv-input" value={formData.firstName} onChange={e => setFormData({...formData, firstName: e.target.value})} />
                            </div>
                            <div>
                                <label className="uv-payLabel">Cognome</label>
                                <input className="uv-input" value={formData.lastName} onChange={e => setFormData({...formData, lastName: e.target.value})} />
                            </div>
                            <div>
                                <label className="uv-payLabel">Indirizzo</label>
                                <input className="uv-input" value={formData.homeAddress} onChange={e => setFormData({...formData, homeAddress: e.target.value})} />
                            </div>
                            <div style={{ gridColumn: 'span 2' }}>
                                <button type="submit" className="uv-btn uv-btnPrimary" style={{ width: '100%' }}>Salva Modifiche</button>
                            </div>
                        </form>
                    )}
                </section>
            </main>
            <Footer />
        </div>
    );
}

export default UserProfile;

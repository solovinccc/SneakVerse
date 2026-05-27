import React, { useState, useMemo } from 'react';
import axios from 'axios';
import './Css/styles.css';
import './Css/premium.css';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL + '/auth';

function LoginModal({ onClose, onLoginSuccess }) {
    const [step, setStep] = useState(1);
    const [isLoginMode, setIsLoginMode] = useState(true);

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [homeAddress, setHomeAddress] = useState('');

    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [loading, setLoading] = useState(false);

    const pwStrength = useMemo(() => {
        if (!password || isLoginMode) return null;
        if (password.length >= 8 && /[A-Z]/.test(password) && /[0-9]/.test(password)) return 'strong';
        if (password.length >= 6) return 'medium';
        return 'weak';
    }, [password, isLoginMode]);

    const handleEmailCheck = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess('');
        setLoading(true);
        try {
            const res = await axios.get(`${API_BASE_URL}/check-email?email=${email}`);
            setIsLoginMode(res.data === true || res.data.exists === true);
            setStep(2);
        } catch (err) {
            if (err.response?.status === 404) {
                setIsLoginMode(false);
                setStep(2);
            } else {
                setError('Errore di connessione al server.');
            }
        } finally {
            setLoading(false);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess('');
        setLoading(true);
        try {
            if (isLoginMode) {
                const res = await axios.post(`${API_BASE_URL}/login`, { email, password });
                onLoginSuccess(res.data.token, res.data.userId);
            } else {
                if (password !== confirmPassword) {
                    setError('Le password non coincidono.');
                    return;
                }
                await axios.post(`${API_BASE_URL}/register`, {
                    firstName, lastName, email, password, homeAddress
                });
                setIsLoginMode(true);
                setPassword('');
                setConfirmPassword('');
                setSuccess('Registrazione completata! Ora inserisci la password per accedere.');
            }
        } catch (err) {
            console.error('errore handleSubmit:', err);
            setError(err.response?.data?.message || 'Errore di connessione al server.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="uv-loginOverlay" onClick={onClose}>
            <div className="uv-loginCard" onClick={e => e.stopPropagation()}>

                <div className="uv-loginLeft">
                    <h2>SBLOCCA NUOVI<br />VANTAGGI</h2>
                    <p>Iscriviti o accedi per ottenere:</p>
                    <ul>
                        <li>✓ Bonus di benvenuto</li>
                        <li>✓ Spedizione gratuita</li>
                        <li>✓ Accesso in anteprima ai drop</li>
                        <li>✓ Edizioni limitate</li>
                    </ul>
                </div>

                <div className="uv-loginRight">
                    <button onClick={onClose} className="uv-loginClose">✕</button>

                    <h2 className="uv-loginTitle">
                        {step === 1 ? 'ACCEDI O REGISTRATI' : (isLoginMode ? 'BENTORNATO' : 'CREA IL TUO ACCOUNT')}
                    </h2>

                    {error && <div className="uv-loginAlert error">{error}</div>}
                    {success && <div className="uv-loginAlert success">{success}</div>}

                    {step === 1 ? (
                        <form onSubmit={handleEmailCheck}>
                            <input className="uv-loginInput" type="email" required placeholder="INDIRIZZO EMAIL *" value={email} onChange={e => setEmail(e.target.value)} />
                            <button className="uv-loginSubmit" type="submit" disabled={loading}>
                                {loading ? 'Attendere...' : 'CONTINUA →'}
                            </button>
                        </form>
                    ) : (
                        <>
                            <div className="uv-loginEmailBar">
                                <span>{email}</span>
                                <button type="button" className="uv-loginEditBtn" onClick={() => { setStep(1); setError(''); setSuccess(''); }}>
                                    Modifica
                                </button>
                            </div>

                            <form onSubmit={handleSubmit}>
                                {!isLoginMode && (
                                    <>
                                        <div className="uv-loginRow">
                                            <input className="uv-loginInput" required placeholder="NOME *" value={firstName} onChange={e => setFirstName(e.target.value)} />
                                            <input className="uv-loginInput" required placeholder="COGNOME *" value={lastName} onChange={e => setLastName(e.target.value)} />
                                        </div>
                                        <input className="uv-loginInput" required placeholder="INDIRIZZO DI CASA *" value={homeAddress} onChange={e => setHomeAddress(e.target.value)} />
                                    </>
                                )}

                                <input className="uv-loginInput" type="password" required placeholder={isLoginMode ? 'PASSWORD *' : 'CREA PASSWORD *'} value={password} onChange={e => setPassword(e.target.value)} />

                                {!isLoginMode && pwStrength && (
                                    <div className="uv-pwStrength">
                                        <div className={`uv-pwStrength-bar ${pwStrength}`}></div>
                                    </div>
                                )}

                                {!isLoginMode && (
                                    <input className="uv-loginInput" type="password" required placeholder="RIPETI PASSWORD *" value={confirmPassword} onChange={e => setConfirmPassword(e.target.value)} />
                                )}

                                <button className="uv-loginSubmit" type="submit" disabled={loading}>
                                    {loading ? 'Attendere...' : (isLoginMode ? 'ACCEDI' : 'CREA ACCOUNT')}
                                </button>
                            </form>
                        </>
                    )}
                </div>
            </div>
        </div>
    );
}

export default LoginModal;
import React, { useState } from "react";

function Register({ onRegister, onLogin }) {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [homeAddress, setHomeAddress] = useState("");
    const [adminKey, setAdminKey] = useState("");
    const [message, setMessage] = useState("");

    const handleRegister = (e) => {
        if(e) e.preventDefault();
        if(!username || !password) {
            setMessage("Username e password obbligatori");
            return;
        }

        const payload = { username, password, homeAddress };
        if(adminKey) payload.adminKey = adminKey;

        fetch("http://localhost:8080/api/auth/register", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(payload)
        })
        .then(res => {
            if(!res.ok) throw new Error("Registrazione fallita");
            setMessage("Account creato con successo!");
            setTimeout(() => onRegister(), 1500); 
        })
        .catch(err => setMessage(err.message));
    };

return (
    <div className="auth-full-page">
        {/* LOGO ESTERNO ALLA CARD */}
        <div className="auth-logo-external">
            <h2>SneakVerse</h2>
            <span>CREA UN NUOVO ACCOUNT</span>
        </div>

        <div className="auth-card">
            <form className="auth-form" onSubmit={handleRegister}>
                <div className="auth-input-group">
                    <label>Username</label>
                    <input 
                        type="text"
                        placeholder="Scegli il tuo username" 
                        value={username} 
                        onChange={e => setUsername(e.target.value)} 
                        required
                    />
                </div>

                <div className="auth-input-group">
                    <label>Password</label>
                    <input 
                        type="password" 
                        placeholder="Minimo 8 caratteri" 
                        value={password} 
                        onChange={e => setPassword(e.target.value)} 
                        required
                    />
                </div>

                <div className="auth-input-group">
                    <label>Indirizzo di Residenza</label>
                    <input 
                        type="text"
                        placeholder="Via, Città, CAP" 
                        value={homeAddress} 
                        onChange={e => setHomeAddress(e.target.value)} 
                    />
                </div>

                <div className="auth-input-group" style={{ borderTop: "1px solid #3f4451", paddingTop: "15px", marginTop: "5px" }}>
                    <label>Admin Key (Opzionale)</label>
                    <input 
                        type="password" 
                        placeholder="Chiave segreta per Staff" 
                        value={adminKey} 
                        onChange={e => setAdminKey(e.target.value)} 
                    />
                </div>

                <button type="submit" className="auth-btn">
                    Registrati
                </button>
            </form>

            {message && (
                <div className={`message-banner ${message.includes("fallita") ? "error" : "success"}`}>
                    {message}
                </div>
            )}

            <p className="auth-footer">
                Hai già un account? <span onClick={onLogin}>Accedi qui</span>
            </p>
        </div>
    </div>
);
}

export default Register;
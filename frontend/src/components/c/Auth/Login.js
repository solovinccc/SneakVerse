import React, { useState } from "react";
import "../View/Css/Auth.css";

function Login({ onLogin, onRegister }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false); 

  const handleLogin = (e) => {
    if (e) e.preventDefault();

    if (!username || !password) {
      setMessage("Username e password obbligatori");
      return;
    }

    setMessage("");
    setIsLoading(true); 

    fetch("http://localhost:8080/api/auth/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password }),
    })
      .then((res) => {
        if (!res.ok) throw new Error("Credenziali non valide");
        return res.json();
      })
      .then((data) => {
        localStorage.setItem("token", data.token);
        localStorage.setItem("role", data.role);
        localStorage.setItem("userId", data.userId);

        
        setTimeout(() => {
          onLogin(data.role);
        }, 1100);
      })
      .catch((err) => {
        setIsLoading(false);
        setMessage(err.message);
      });
  };


  if (isLoading) {
    return (
      <div className="auth-full-page">
        <div className="auth-loading">
          <div className="auth-loading-logo">SneakVerse</div>
          <div className="auth-loading-spinner" />
          <div className="auth-loading-text">Accesso in corso…</div>
        </div>
      </div>
    );
  }

  return (
    <div className="auth-full-page">
      <div className="auth-logo-external">
        <h2>SneakVerse</h2>
        <span>BENTORNATO!</span>
      </div>

      <div className="auth-card">
        <form className="auth-form" onSubmit={handleLogin}>
          <div className="auth-input-group">
            <label>Username</label>
            <input
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              placeholder="Inserisci il tuo username"
              disabled={isLoading}
            />
          </div>

          <div className="auth-input-group">
            <label>Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="••••••••"
              disabled={isLoading}
            />
          </div>

          <button type="submit" className="auth-btn" disabled={isLoading}>
            Accedi
          </button>
        </form>

        {message && <div className="message-banner error">{message}</div>}

        <p className="auth-footer">
          Nuovo su SneakVerse? <span onClick={onRegister}>Registrati ora</span>
        </p>
      </div>
    </div>
  );
}

export default Login;

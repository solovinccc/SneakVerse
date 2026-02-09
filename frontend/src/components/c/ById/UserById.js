import { useState } from "react";

function UserById() {
    const [id, setId] = useState("");
    const [user, setUser] = useState(null);
    const [error, setError] = useState(null);

    const handleSearch = () => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/users/${id}`, {
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.ok ? res.json() : Promise.reject("Utente non trovato"))
        .then(data => { setUser(data); setError(null); })
        .catch(err => { setError(err); setUser(null); });
    };

    return (
        <div className="search-container">
            <h3>Cerca Utente per ID</h3>
            <div className="search-box">
                <input type="number" value={id} onChange={(e) => setId(e.target.value)} placeholder="Inserisci ID..." />
                <button onClick={handleSearch} className="btn-save">Cerca</button>
            </div>
            {error && <p className="error-msg">{error}</p>}
            {user && (
                <div className="detail-card">
                    <p><strong>Username:</strong> {user.username}</p>
                    <p><strong>Indirizzo:</strong> {user.homeAddress}</p>
                </div>
            )}
        </div>
    );
}

export default UserById;
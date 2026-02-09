import { useState } from "react";

function UserDelete() {
    const [id, setId] = useState("");

    const handleDelete = () => {
        if (!id) return alert("Inserisci un ID");
        if (!window.confirm(`Sei sicuro di voler eliminare l'utente #${id}?`)) return;

        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/users/${id}`, {
            method: "DELETE",
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.ok ? alert("Utente eliminato!") : alert("Errore: Utente non trovato o protetto"))
        .catch(err => console.error(err));
    };

    return (
        <div className="form-container">
            <h3 style={{color: "#e74c3c"}}>Elimina Utente</h3>
            <div className="admin-form">
                <input type="number" placeholder="ID Utente" value={id} onChange={e => setId(e.target.value)} />
                <button onClick={handleDelete} className="btn-delete">Elimina Definitivamente</button>
            </div>
        </div>
    );
}

export default UserDelete;
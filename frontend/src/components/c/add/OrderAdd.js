import { useState } from "react";

function OrderAdd() {
    const [userId, setUserId] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        const token = localStorage.getItem("token");

        fetch("http://localhost:8080/api/orders", {
            method: "POST",
            headers: { 
                "Authorization": `Bearer ${token}`, 
                "Content-Type": "application/json" 
            },
            body: JSON.stringify({ userId: userId })
        })
        .then(res => {
            if (res.ok) {
                alert("Ordine creato con successo! La data è stata generata dal sistema.");
                setUserId("");
            } else {
                alert("Errore nella creazione dell'ordine. Controlla che l'ID Utente esista.");
            }
        })
        .catch(err => console.error("Errore:", err));
    };

    return (
        <div className="form-container">
            <h3>Nuovo Ordine</h3>
            <p className="info-text">La data dell'ordine verrà registrata automaticamente dal server.</p>
            <form onSubmit={handleSubmit} className="admin-form">
                <input 
                    type="number" 
                    placeholder="Inserisci ID Utente" 
                    value={userId}
                    onChange={e => setUserId(e.target.value)} 
                    required 
                />
                <button type="submit" className="btn-save">Crea Ordine</button>
            </form>
        </div>
    );
}

export default OrderAdd;
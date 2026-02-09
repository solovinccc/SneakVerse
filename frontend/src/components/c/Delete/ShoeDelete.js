import { useState } from "react";

function ShoeDelete() {
    const [id, setId] = useState("");

    const handleDelete = () => {
        if (!window.confirm("Eliminare questa scarpa dal catalogo?")) return;
        const token = localStorage.getItem("token");

        fetch(`http://localhost:8080/api/shoes/${id}`, {
            method: "DELETE",
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.ok ? alert("Scarpa rimossa!") : alert("Errore nell'eliminazione"));
    };

    return (
        <div className="form-container">
            <h3>Elimina Scarpa</h3>
            <div className="admin-form">
                <input type="number" placeholder="ID Scarpa" onChange={e => setId(e.target.value)} />
                <button onClick={handleDelete} className="btn-delete">Rimuovi Prodotto</button>
            </div>
        </div>
    );
}

export default ShoeDelete;
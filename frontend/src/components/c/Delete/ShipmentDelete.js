import { useState } from "react";

function ShipmentDelete() {
    const [id, setId] = useState("");

    const handleDelete = () => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/shipments/${id}`, {
            method: "DELETE",
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.ok ? alert("Spedizione rimossa") : alert("Errore"));
    };

    return (
        <div className="form-container">
            <h3>Elimina Spedizione</h3>
            <div className="admin-form">
                <input type="number" placeholder="ID Spedizione" onChange={e => setId(e.target.value)} />
                <button onClick={handleDelete} className="btn-delete">Elimina</button>
            </div>
        </div>
    );
}

export default ShipmentDelete;
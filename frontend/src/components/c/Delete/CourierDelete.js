import { useState } from "react";

function CourierDelete() {
    const [id, setId] = useState("");

    const handleDelete = () => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/couriers/${id}`, {
            method: "DELETE",
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.ok ? alert("Corriere rimosso") : alert("Errore"));
    };

    return (
        <div className="form-container">
            <h3>Elimina Corriere</h3>
            <div className="admin-form">
                <input type="number" placeholder="ID Corriere" onChange={e => setId(e.target.value)} />
                <button onClick={handleDelete} className="btn-delete">Elimina</button>
            </div>
        </div>
    );
}

export default CourierDelete;
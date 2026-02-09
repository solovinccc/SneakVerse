import { useState } from "react";

function OrderDelete() {
    const [id, setId] = useState("");

    const handleDelete = () => {
        if (!window.confirm("Annullare ed eliminare l'ordine?")) return;
        const token = localStorage.getItem("token");

        fetch(`http://localhost:8080/api/orders/${id}`, {
            method: "DELETE",
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.ok ? alert("Ordine eliminato") : alert("Errore"));
    };

    return (
        <div className="form-container">
            <h3>Elimina Ordine</h3>
            <div className="admin-form">
                <input type="number" placeholder="ID Ordine" onChange={e => setId(e.target.value)} />
                <button onClick={handleDelete} className="btn-delete">Elimina</button>
            </div>
        </div>
    );
}

export default OrderDelete;
import { useState } from "react";

function OrderItemDelete() {
    const [id, setId] = useState("");

    const handleDelete = () => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/orderitems/${id}`, {
            method: "DELETE",
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.ok ? alert("Item rimosso dall'ordine") : alert("Errore"));
    };

    return (
        <div className="form-container">
            <h3>Rimuovi Item</h3>
            <div className="admin-form">
                <input type="number" placeholder="ID Riga Ordine" onChange={e => setId(e.target.value)} />
                <button onClick={handleDelete} className="btn-delete">Rimuovi</button>
            </div>
        </div>
    );
}

export default OrderItemDelete;
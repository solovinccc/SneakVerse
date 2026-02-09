import { useState } from "react";

function ShipmentUpdate() {
    const [id, setId] = useState("");
    const [formData, setFormData] = useState({ orderId: "", courierId: "" });

    const handleUpdate = (e) => {
        e.preventDefault();
        const filteredData = Object.fromEntries(Object.entries(formData).filter(([_, v]) => v !== ""));

        if(Object.keys(filteredData).length === 0) {
            alert("Nessun dato da aggiornare");
            return;
        }

        fetch(`http://localhost:8080/api/shipments/${id}`, {
            method: "PUT",
            headers: { "Authorization": `Bearer ${localStorage.getItem("token")}`, "Content-Type": "application/json" },
            body: JSON.stringify(filteredData)
        }).then(res => res.ok ? alert("Tracking aggiornato!") : alert("Errore"));
    };

    return (
        <div className="form-container">
            <h3>Aggiorna Spedizione</h3>
            <div className="search-box" style={{marginBottom: "20px"}}>
                <input type="number" placeholder="ID Spedizione da modificare" onChange={e => setId(e.target.value)} required />
            </div>
            <form onSubmit={handleUpdate} className="admin-form">
                <input type="number" placeholder="ID Ordine" onChange={e => setFormData({...formData, orderId: e.target.value})} />
                <input type="number" placeholder="ID Corriere" onChange={e => setFormData({...formData, courierId: e.target.value})} />
                <button type="submit" className="btn-save">Aggiorna</button>
            </form>
        </div>
    );
}
export default ShipmentUpdate;
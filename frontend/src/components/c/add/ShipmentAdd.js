import { useState } from "react";

function ShipmentAdd() {
    const [formData, setFormData] = useState({ 
        orderId: "", 
        courierId: "", 
        trackingNumber: "",
        shipmentDate: new Date().toISOString().split('T')[0]
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        const token = localStorage.getItem("token");

        fetch("http://localhost:8080/api/shipments", {
            method: "POST",
            headers: { 
                "Authorization": `Bearer ${token}`, 
                "Content-Type": "application/json" 
            },
            body: JSON.stringify(formData)
        })
        .then(res => res.ok ? alert("Spedizione registrata!") : alert("Errore"));
    };

    return (
        <div className="form-container">
            <h3>Registra Spedizione</h3>
            <form onSubmit={handleSubmit} className="admin-form">
                <input 
                    type="number" 
                    placeholder="ID Ordine" 
                    onChange={e => setFormData({...formData, orderId: e.target.value})} 
                    required 
                />
                <input 
                    type="number" 
                    placeholder="ID Corriere" 
                    onChange={e => setFormData({...formData, courierId: e.target.value})} 
                    required 
                />
                <button type="submit" className="btn-save">Conferma Spedizione</button>
            </form>
        </div>
    );
}

export default ShipmentAdd;
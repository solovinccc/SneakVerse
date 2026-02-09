import { useState } from "react";

function OrderUpdate() {
    const [id, setId] = useState("");
    const [formData, setFormData] = useState({ 
        userId: "", 
        orderDate: "", 
        orderItemId: "", 
        shipmentId: "" 
    });

    const handleUpdate = (e) => {
        e.preventDefault();
        const token = localStorage.getItem("token");
        
        const filteredData = Object.fromEntries(
            Object.entries(formData).filter(([_, v]) => v !== "")
        );

        if(Object.keys(filteredData).length === 0) {
            alert("Nessun dato da aggiornare");
            return;
        }

        fetch(`http://localhost:8080/api/orders/${id}`, {
            method: "PUT",
            headers: { 
                "Authorization": `Bearer ${token}`, 
                "Content-Type": "application/json" 
            },
            body: JSON.stringify(filteredData)
        })
        .then(res => res.ok ? alert("Ordine aggiornato con successo!") : alert("Errore nell'aggiornamento (Controlla ID)"));
    };

    return (
        <div className="form-container">
            <h3>Modifica Ordine</h3>
            <div className="search-box" style={{marginBottom: "20px"}}>
                <input 
                    type="number" 
                    placeholder="ID Ordine da modificare" 
                    onChange={e => setId(e.target.value)} 
                    required 
                />
            </div>
            
            <form onSubmit={handleUpdate} className="admin-form">
                <input 
                    type="number" 
                    placeholder="Nuovo ID Utente" 
                    onChange={e => setFormData({...formData, userId: e.target.value})} 
                />
                <input 
                    type="date" 
                    placeholder="Nuova Data Ordine"
                    onChange={e => setFormData({...formData, orderDate: e.target.value})} 
                />
                <input 
                    type="number" 
                    placeholder="Nuovo ID Item Ordine" 
                    onChange={e => setFormData({...formData, orderItemId: e.target.value})} 
                />
                <input 
                    type="number" 
                    placeholder="Nuovo ID Spedizione" 
                    onChange={e => setFormData({...formData, shipmentId: e.target.value})} 
                />
                <button type="submit" className="btn-save">Salva Modifiche Ordine</button>
            </form>
        </div>
    );
}

export default OrderUpdate;
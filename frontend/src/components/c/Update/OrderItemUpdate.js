import { useState } from "react";

function OrderItemUpdate() {
    const [id, setId] = useState("");
    const [formData, setFormData] = useState({ 
        shoeId: "", 
        orderId: "", 
        quantity: "" 
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

        fetch(`http://localhost:8080/api/orderitems/${id}`, {
            method: "PUT",
            headers: { 
                "Authorization": `Bearer ${token}`, 
                "Content-Type": "application/json" 
            },
            body: JSON.stringify(filteredData)
        })
        .then(res => res.ok ? alert("Dettaglio ordine aggiornato!") : alert("Errore (Controlla ID e validità ID scarpa/ordine)"));
    };

    return (
        <div className="form-container">
            <h3>Modifica Elemento Ordine</h3>
            
            <div className="search-box" style={{marginBottom: "20px"}}>
                <input 
                    type="number" 
                    placeholder="ID Elemento Ordine da modificare" 
                    onChange={e => setId(e.target.value)} 
                    required 
                />
            </div>
            
            <form onSubmit={handleUpdate} className="admin-form">
                <div className="auth-input-group">
                    <input 
                        type="number" 
                        placeholder="Nuovo ID Scarpa" 
                        onChange={e => setFormData({...formData, shoeId: e.target.value})} 
                    />
                </div>
                <div className="auth-input-group">
                    <input 
                        type="number" 
                        placeholder="Nuovo ID Ordine" 
                        onChange={e => setFormData({...formData, orderId: e.target.value})} 
                    />
                </div>
                <div className="auth-input-group">
                    <input 
                        type="number" 
                        placeholder="Nuova Quantità" 
                        onChange={e => setFormData({...formData, quantity: e.target.value})} 
                    />
                </div>
                
                <button type="submit" className="btn-save">Aggiorna Elemento</button>
            </form>
        </div>
    );
}

export default OrderItemUpdate;
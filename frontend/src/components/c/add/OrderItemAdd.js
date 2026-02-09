import { useState } from "react";

function OrderItemAdd() {
    const [formData, setFormData] = useState({ 
        orderId: "", 
        shoeId: "", 
        quantity: 1 
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        const token = localStorage.getItem("token");

        fetch("http://localhost:8080/api/orderitems", {
            method: "POST",
            headers: { 
                "Authorization": `Bearer ${token}`, 
                "Content-Type": "application/json" 
            },
            body: JSON.stringify(formData)
        })
        .then(res => res.ok ? alert("Oggetto aggiunto all'ordine!") : alert("Errore"));
    };

    return (
        <div className="form-container">
            <h3>Aggiungi Item a Ordine</h3>
            <form onSubmit={handleSubmit} className="admin-form">
                <input 
                    type="number" 
                    placeholder="ID Ordine" 
                    onChange={e => setFormData({...formData, orderId: e.target.value})} 
                    required 
                />
                <input 
                    type="number" 
                    placeholder="ID Scarpa" 
                    onChange={e => setFormData({...formData, shoeId: e.target.value})} 
                    required 
                />
                <input 
                    type="number" 
                    placeholder="Quantità" 
                    min="1"
                    value={formData.quantity}
                    onChange={e => setFormData({...formData, quantity: e.target.value})} 
                    required 
                />
                <button type="submit" className="btn-save">Aggiungi</button>
            </form>
        </div>
    );
}

export default OrderItemAdd;
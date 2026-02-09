import { useState } from "react";

function ShoeAdd() {
    const [formData, setFormData] = useState({ 
        shoeName: "", 
        shoeSize: "", 
        shoePrice: "", 
        brandId: "",
        imageUrl: ""
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        const token = localStorage.getItem("token");

        fetch("http://localhost:8080/api/shoes", {
            method: "POST",
            headers: { 
                "Authorization": `Bearer ${token}`, 
                "Content-Type": "application/json" 
            },
            body: JSON.stringify(formData)
        })
        .then(res => res.ok ? alert("Scarpa aggiunta!") : alert("Errore nell'inserimento"));
    };

    return (
        <div className="form-container">
            <h3>Aggiungi Nuova Scarpa</h3>
            <form onSubmit={handleSubmit} className="admin-form">
                <input 
                    type="text" 
                    placeholder="Modello" 
                    onChange={e => setFormData({...formData, shoeName: e.target.value})} 
                    required 
                />
                <input 
                    type="number" 
                    step="0.01"
                    placeholder="Taglia" 
                    onChange={e => setFormData({...formData, shoeSize: e.target.value})} 
                    required 
                />
                <input 
                    type="number" 
                    step="0.01" 
                    placeholder="Prezzo (€)" 
                    onChange={e => setFormData({...formData, shoePrice: e.target.value})} 
                    required 
                />
                <input 
                    type="number" 
                    placeholder="ID Brand" 
                    onChange={e => setFormData({...formData, brandId: e.target.value})} 
                    required 
                />
                <input 
                    type="text" 
                    placeholder="Url immagine" 
                    onChange={e => setFormData({...formData, imageUrl: e.target.value})} 
                    required 
                />
                <button type="submit" className="btn-save">Salva Prodotto</button>
            </form>
        </div>
    );
}

export default ShoeAdd;
import { useState } from "react";

function ShoeUpdate() {
    const [id, setId] = useState("");
    const [formData, setFormData] = useState({ shoeName: "", shoeSize: "", shoePrice: "", brandId: "", items: "", imageUrl: ""  });

    const handleUpdate = (e) => {
        e.preventDefault();
        const filteredData = Object.fromEntries(Object.entries(formData).filter(([_, v]) => v !== ""));
        fetch(`http://localhost:8080/api/shoes/${id}`, {
            method: "PUT",
            headers: { "Authorization": `Bearer ${localStorage.getItem("token")}`, "Content-Type": "application/json" },
            body: JSON.stringify(filteredData)
        }).then(res => res.ok ? alert("Dati scarpa aggiornati!") : alert("Errore (Controlla ID)"));
    };

    return (
        <div className="form-container">
            <h3>Aggiorna Scarpa</h3>
            <div className="search-box" style={{marginBottom: "20px"}}>
                <input type="number" placeholder="ID Scarpa da modificare" onChange={e => setId(e.target.value)} required />
            </div>
            <form onSubmit={handleUpdate} className="admin-form">
                <input type="text" placeholder="Modello" onChange={e => setFormData({...formData, shoeName: e.target.value})} />
                <input type="number" placeholder="Taglia" onChange={e => setFormData({...formData, shoeSize: e.target.value})} />
                <input type="number" step="0.01" placeholder="Prezzo" onChange={e => setFormData({...formData, shoePrice: e.target.value})} />
                <input type="number" placeholder="ID Brand" onChange={e => setFormData({...formData, brandId: e.target.value})} />
                <input type="number" placeholder="ID item" onChange={e => setFormData({...formData, items: e.target.value})} />
                <input type="text" placeholder="Url immagine" onChange={e => setFormData({...formData, imageUrl: e.target.value})} />
                <button type="submit" className="btn-save">Aggiorna Prodotto</button>
            </form>
        </div>
    );
}
export default ShoeUpdate;
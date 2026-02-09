import { useState } from "react";

function BrandUpdate() {
    const [id, setId] = useState("");
    const [brandName, setBrandName] = useState("");

    const handleUpdate = (e) => {
        e.preventDefault();
        if (!brandName) return alert("Inserisci il nuovo nome");
        fetch(`http://localhost:8080/api/brands/${id}`, {
            method: "PUT",
            headers: { "Authorization": `Bearer ${localStorage.getItem("token")}`, "Content-Type": "application/json" },
            body: JSON.stringify({ brandName })
        }).then(res => res.ok ? alert("Brand aggiornato!") : alert("Errore"));
    };

    return (
        <div className="form-container">
            <h3>Modifica Brand</h3>
            <div className="search-box" style={{marginBottom: "20px"}}>
                <input type="number" placeholder="ID Brand da modificare" onChange={e => setId(e.target.value)} required />
            </div>
            <form onSubmit={handleUpdate} className="admin-form">
                <input type="text" placeholder="Nuovo Nome Brand" onChange={e => setBrandName(e.target.value)} />
                <button type="submit" className="btn-save">Salva Brand</button>
            </form>
        </div>
    );
}
export default BrandUpdate;
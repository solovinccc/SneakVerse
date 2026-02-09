import { useState } from "react";

function BrandAdd() {
    const [brandName, setBrandName] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        const token = localStorage.getItem("token");

        fetch("http://localhost:8080/api/brands", {
            method: "POST",
            headers: { 
                "Authorization": `Bearer ${token}`, 
                "Content-Type": "application/json" 
            },
            body: JSON.stringify({ brandName })
        })
        .then(res => res.ok ? alert("Brand aggiunto!") : alert("Errore"));
    };

    return (
        <div className="form-container">
            <h3>Nuovo Brand</h3>
            <form onSubmit={handleSubmit} className="admin-form">
                <input 
                    type="text" 
                    placeholder="Nome Brand" 
                    onChange={e => setBrandName(e.target.value)} 
                    required 
                />
                <button type="submit" className="btn-save">Crea Brand</button>
            </form>
        </div>
    );
}

export default BrandAdd;
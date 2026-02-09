import { useState } from "react";

function CourierAdd() {
    const [formData, setFormData] = useState({ 
        name: "", 
        phoneNumber: "" 
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        const token = localStorage.getItem("token");

        fetch("http://localhost:8080/api/couriers", {
            method: "POST",
            headers: { 
                "Authorization": `Bearer ${token}`, 
                "Content-Type": "application/json" 
            },
            body: JSON.stringify(formData)
        })
        .then(res => res.ok ? alert("Corriere aggiunto!") : alert("Errore"));
    };

    return (
        <div className="form-container">
            <h3>Nuovo Corriere</h3>
            <form onSubmit={handleSubmit} className="admin-form">
                <input 
                    type="text" 
                    placeholder="Telefono" 
                    onChange={e => setFormData({...formData, phoneNumber: e.target.value})} 
                />
                <button type="submit" className="btn-save">Salva</button>
            </form>
        </div>
    );
}

export default CourierAdd;
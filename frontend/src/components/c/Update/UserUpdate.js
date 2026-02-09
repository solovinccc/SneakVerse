import { useState } from "react";

function UserUpdate() {
    const [id, setId] = useState("");
    const [formData, setFormData] = useState({ 
        username: "", 
        password: "", 
        homeAddress: "", 
        role: "USER" 
    });

    const handleUpdate = (e) => {
        e.preventDefault();
        const token = localStorage.getItem("token");

        const filteredData = Object.fromEntries(
            Object.entries(formData).filter(([_, v]) => v !== "" && v !== null)
        );

        if(Object.keys(filteredData).length === 0) {
            alert("Nessun dato da aggiornare");
            return;
        }

        fetch(`http://localhost:8080/api/users/${id}`, {
            method: "PUT",
            headers: { 
                "Authorization": `Bearer ${token}`, 
                "Content-Type": "application/json" 
            },
            body: JSON.stringify(filteredData)
        })
        .then(res => res.ok ? alert("Dati aggiornati!") : alert("Errore (Controlla ID)"));
    };

    return (
        <div className="form-container">
            <h3>Modifica Utente</h3>
            <div className="search-box" style={{marginBottom: "20px"}}>
                <input type="number" placeholder="ID Utente da modificare" onChange={e => setId(e.target.value)} required />
            </div>
            
            <form onSubmit={handleUpdate} className="admin-form">
                <input type="text" placeholder="Nuovo Username" onChange={e => setFormData({...formData, username: e.target.value})} />
                <input type="text" placeholder="Nuova Password (Reset)" onChange={e => setFormData({...formData, password: e.target.value})} />
                <input type="text" placeholder="Nuovo Indirizzo" onChange={e => setFormData({...formData, homeAddress: e.target.value})} />
                <select onChange={e => setFormData({...formData, role: e.target.value})}>
                    <option value="USER">USER</option>
                    <option value="ADMIN">ADMIN</option>
                </select>
                <button type="submit" className="btn-save">Salva Modifiche</button>
            </form>
        </div>
    );
}

export default UserUpdate;
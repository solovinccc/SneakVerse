import { useState } from "react";

function UserAdd() {
    const [formData, setFormData] = useState({ 
        username: "", 
        password: "", 
        homeAddress: "", 
        role: "USER" 
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        const token = localStorage.getItem("token");

        fetch("http://localhost:8080/api/users", {
            method: "POST",
            headers: { 
                "Authorization": `Bearer ${token}`, 
                "Content-Type": "application/json" 
            },
            body: JSON.stringify(formData)
        })
        .then(res => {
            if (res.ok) {
                alert("Utente creato con successo!");
                setFormData({ username: "", password: "", homeAddress: "", role: "USER" });
            } else {
                alert("Errore: username forse già esistente?");
            }
        })
        .catch(err => console.error(err));
    };

    return (
        <div className="form-container">
            <h3>Crea Nuovo Utente</h3>
            <form onSubmit={handleSubmit} className="admin-form">
                <input 
                    type="text" 
                    placeholder="Username" 
                    value={formData.username}
                    onChange={e => setFormData({...formData, username: e.target.value})} 
                    required 
                />
                <input 
                    type="password" 
                    placeholder="Password" 
                    value={formData.password}
                    onChange={e => setFormData({...formData, password: e.target.value})} 
                    required 
                />
                <input 
                    type="text" 
                    placeholder="Indirizzo" 
                    value={formData.homeAddress}
                    onChange={e => setFormData({...formData, homeAddress: e.target.value})} 
                />
                <select 
                    value={formData.role} 
                    onChange={e => setFormData({...formData, role: e.target.value})}
                >
                    <option value="USER">USER</option>
                    <option value="ADMIN">ADMIN</option>
                </select>
                <button type="submit" className="btn-save">Registra Utente</button>
            </form>
        </div>
    );
}

export default UserAdd;
import React, { useState } from "react";

function UserAdd() {
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        homeAddress: "",
        role: "USER"
    });

    const handleSubmit = (e) => {
        e.preventDefault();
        const token = localStorage.getItem("token");

        fetch("http://sneakverse.it/api/users", {
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
                    setFormData({ firstName: "", lastName: "", email: "", password: "", homeAddress: "", role: "USER" });
                } else {
                    alert("Errore: email già esistente");
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
                    placeholder="Nome"
                    value={formData.firstName}
                    onChange={e => setFormData({ ...formData, firstName: e.target.value })}
                    required
                />
                <input
                    type="text"
                    placeholder="Cognome"
                    value={formData.lastName}
                    onChange={e => setFormData({ ...formData, lastName: e.target.value })}
                    required
                />

                <input
                    type="text"
                    placeholder="Email"
                    value={formData.email}
                    onChange={e => setFormData({ ...formData, email: e.target.value })}
                    required
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={formData.password}
                    onChange={e => setFormData({ ...formData, password: e.target.value })}
                    required
                />
                <input
                    type="text"
                    placeholder="Indirizzo"
                    value={formData.homeAddress}
                    onChange={e => setFormData({ ...formData, homeAddress: e.target.value })}
                />
                <button type="submit" className="btn-save">Registra Utente</button>
            </form>
        </div>
    );
}

export default UserAdd;
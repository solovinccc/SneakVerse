import { useState } from "react";

function BrandDelete() {
    const [id, setId] = useState("");

    const handleDelete = () => {
        if (!window.confirm("Attenzione: Se elimini il Brand, controlla che non ci siano scarpe collegate!")) return;
        const token = localStorage.getItem("token");

        fetch(`http://localhost:8080/api/brands/${id}`, {
            method: "DELETE",
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.ok ? alert("Brand eliminato") : alert("Errore: Vincoli DB o ID errato"));
    };

    return (
        <div className="form-container">
            <h3>Elimina Brand</h3>
            <div className="admin-form">
                <input type="number" placeholder="ID Brand" onChange={e => setId(e.target.value)} />
                <button onClick={handleDelete} className="btn-delete">Elimina Brand</button>
            </div>
        </div>
    );
}

export default BrandDelete;
import { useState } from "react";

function CourierUpdate() {
    const [id, setId] = useState("");
    const [phoneNumber, setPhoneNumber ] = useState("");

    const handleUpdate = (e) => {
        e.preventDefault();
        if (!phoneNumber) return alert("Inserisci il nuovo numero di telefono");
        fetch(`http://localhost:8080/api/couriers/${id}`, {
            method: "PUT",
            headers: { "Authorization": `Bearer ${localStorage.getItem("token")}`, "Content-Type": "application/json" },
            body: JSON.stringify({ phoneNumber })
        }).then(res => res.ok ? alert("Dati corriere aggiornati!") : alert("Errore"));
    };

    return (
        <div className="form-container">
            <h3>Modifica Corriere</h3>
            <div className="search-box" style={{marginBottom: "20px"}}>
                <input type="number" placeholder="ID Corriere da modificare" onChange={e => setId(e.target.value)} required />
            </div>
            <form onSubmit={handleUpdate} className="admin-form">
                <input type="text" placeholder="Nuovo Telefono" onChange={e => setPhoneNumber( e.target.value)} />
                <button type="submit" className="btn-save">Salva Modifiche</button>
            </form>
        </div>
    );
}
export default CourierUpdate;
import { useState } from "react";

function CourierById() {
    const [id, setId] = useState("");
    const [courier, setCourier] = useState(null);

    const handleSearch = () => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/couriers/${id}`, {
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.json())
        .then(data => setCourier(data));
    };

    return (
        <div className="search-container">
            <h3>Dettaglio Corriere</h3>
            <input type="number" value={id} onChange={(e) => setId(e.target.value)} />
            <button onClick={handleSearch}>Cerca</button>
            {courier && <div className="detail-card"><p>Tel: {courier.phoneNumber}</p></div>}
        </div>
    );
}

export default CourierById;
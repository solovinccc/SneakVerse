import { useState } from "react";

function ShipmentById() {
    const [id, setId] = useState("");
    const [shipment, setShipment] = useState(null);

    const handleSearch = () => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/shipments/${id}`, {
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.json())
        .then(data => setShipment(data));
    };

    return (
        <div className="search-container">
            <h3>Dettaglio Spedizione</h3>
            <input type="number" value={id} onChange={(e) => setId(e.target.value)} />
            <button onClick={handleSearch}>Cerca</button>
            {shipment && (
                <div className="detail-card">
                    <p>Tracking: {shipment.trackingNumber}</p>
                    <p>Data: {shipment.shipmentDate}</p>
                </div>
            )}
        </div>
    );
}

export default ShipmentById;
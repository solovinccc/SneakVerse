import React, { useEffect, useState } from "react";

function ShipmentList() {
    const [shipments, setShipments] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://sneakverse.it/api/shipments", {
            headers: { "Authorization": `Bearer ${token}` }
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error(`Errore: ${res.status}. Verifica i permessi ADMIN.`);
                }
                return res.json();
            })
            .then(data => setShipments(data))
            .catch(err => {
                console.error("Errore fetch shipments:", err);
                setError(err.message);
            });
    }, []);

    if (error) return <div className="error-msg">{error}</div>;

    return (
        <div className="list-container">
            <h2>Spedizioni</h2>
            <table className="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>ID Ordine</th>
                    </tr>
                </thead>
                <tbody>
                    {shipments.map(s => (
                        <tr key={s.shipmentId}>
                            <td>#{s.shipmentId}</td>
                            <td>{s.orderId}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default ShipmentList;
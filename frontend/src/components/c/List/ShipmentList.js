import { useEffect, useState } from "react";

function ShipmentList() {
    const [shipments, setShipments] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/shipments", {
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.json())
        .then(data => setShipments(data))
        .catch(err => console.error(err));
    }, []);

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
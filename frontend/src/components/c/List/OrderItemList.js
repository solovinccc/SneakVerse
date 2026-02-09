import { useEffect, useState } from "react";

function OrderItemList() {
    const [items, setItems] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/orderitems", {
            method: "GET",
            headers: { "Authorization": `Bearer ${token}`,
                        "Content-Type": "application/json" }
        })
        .then(res => res.ok ? res.json() : Promise.reject(res))
        .then(data => setItems(data))
        .catch(err => setError("Errore nel caricamento brand"));
    }, []);

    return (
        <div className="list-container">
            <div className="list-header">
                <h2>Gestione Item</h2>
                <span className="badge">{items.length} Item:</span>
            </div>
            
            <table className="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Quantita'</th>
                        <th>ID Ordine</th>
                        <th>ID Scarpe</th>
                    </tr>
                </thead>
                <tbody>
                    {items.length === 0 ? (
                        <tr>
                            <td colSpan="4" style={{ textAlign: "center", padding: "20px" }}>
                                Nessun item trovato.
                            </td>
                        </tr>
                    ) : (
                        items.map(item => (
                            <tr key={item.userId}>
                                <td><strong>#{item.orderItemId}</strong></td>
                                <td>{item.quantity}</td>
                                <td>{item.orderId}</td>
                                <td>{item.shoeId}</td>
                            </tr>
                        ))
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default OrderItemList;
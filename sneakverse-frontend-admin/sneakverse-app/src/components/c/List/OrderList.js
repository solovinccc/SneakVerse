import React, { useEffect, useState } from "react";

function OrderList() {
    const [orders, setOrders] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://sneakverse.it/api/orders", {
            headers: { "Authorization": `Bearer ${token}` }
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error(`Errore: ${res.status}. Verifica i permessi ADMIN.`);
                }
                return res.json();
            })
            .then(data => {
                console.log("Risposta dal server per Ordini:", data);
                if (Array.isArray(data)) {
                    setOrders(data);
                } else {
                    console.error("Il server non ha restituito una lista! Ha restituito:", data);
                    setOrders([]);
                }
            })
            .catch(err => {
                console.error("Errore fetch ordini:", err);
                setError(err.message);
                setOrders([]);
            });
    }, []);

    if (error) return <div className="error-msg">{error}</div>;

    return (
        <div className="list-container">
            <div className="list-header">
                <h2>Ordini Ricevuti</h2>
                <span className="badge">{orders?.length || 0} Totali</span>
            </div>
            <table className="admin-table">
                <thead>
                    <tr>
                        <th>ID Ordine</th>
                        <th>Data</th>
                        <th>ID Utente</th>
                        <th>ID item</th>
                        <th>Pagamento</th>
                        <th>Stato</th>
                    </tr>
                </thead>
                <tbody>
                    {orders?.length > 0 ? (
                        orders.map(order => (
                            <tr key={order.orderId}>
                                <td>#{order.orderId}</td>
                                <td>{new Date(order.orderDate).toLocaleString("it-IT")}</td>
                                <td>{order.userId}</td>
                                <td>
                                    {order.items && order.items.length > 0
                                        ? order.items.map(item => item.shoeId).join(", ")
                                        : "Nessun item"}
                                </td>
                                <td>{order.paymentMethod}</td>
                                <td>{order.status}</td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="6" style={{ textAlign: "center" }}>
                                Nessun ordine trovato o errore di caricamento.
                            </td>
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default OrderList;
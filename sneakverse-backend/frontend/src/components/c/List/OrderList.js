import { useEffect, useState } from "react";

function OrderList() {
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8081/api/orders", {
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.json())
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
            setOrders([]); 
        });
    }, []);

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
                    </tr>
                </thead>
                <tbody>
                    {orders?.length > 0 ? (
                        orders.map(order => (
                            <tr key={order.orderId}>
                                <td>#{order.orderId}</td>
                                <td>{order.orderDate}</td>
                                <td>{order.userId}</td>
                                <td>{order.orderItemId ? order.orderItemId.join(", ") : "Nessun item"}</td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="4" style={{ textAlign: "center" }}>
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
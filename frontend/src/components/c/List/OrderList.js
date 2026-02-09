import { useEffect, useState } from "react";

function OrderList() {
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/orders", {
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.json())
        .then(data => setOrders(data))
        .catch(err => console.error(err));
    }, []);

    return (
        <div className="list-container">
            <div className="list-header">
                <h2>Ordini Ricevuti</h2>
                <span className="badge">{orders.length} Totali</span>
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
                    {orders.map(order => (
                        <tr key={order.orderId}>
                            <td>#{order.orderId}</td>
                            <td>{order.orderDate}</td>
                            <td>{order.userId}</td>
                            <td>{order.orderItemId.join(", ")}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default OrderList;
import { useState } from "react";

function OrderById() {
    const [id, setId] = useState("");
    const [order, setOrder] = useState(null);

    const handleSearch = () => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/orders/${id}`, {
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.json())
        .then(data => setOrder(data));
    };

    return (
        <div className="search-container">
            <h3>Cerca Ordine</h3>
            <input type="number" value={id} onChange={(e) => setId(e.target.value)} />
            <button onClick={handleSearch}>Cerca</button>
            {order && (
                <div className="detail-card">
                    <p>Data: {order.orderDate}</p>
                    <p>User ID: {order.userId}</p>
                    <p>Item ID: {order.orderItemId.join(", ")}</p>
                </div>
            )}
        </div>
    );
}

export default OrderById;
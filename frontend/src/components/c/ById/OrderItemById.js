import { useState } from "react";

function OrderItemById() {
    const [id, setId] = useState("");
    const [item, setItem] = useState(null);

    const handleSearch = () => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/orderitems/${id}`, {
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.json())
        .then(data => setItem(data));
    };

    return (
        <div className="search-container">
            <h3>Cerca Order Item</h3>
            <input type="number" value={id} onChange={(e) => setId(e.target.value)} />
            <button onClick={handleSearch}>Cerca</button>
            {item && <div className="detail-card"><p>Ordine ID: {item.orderId}</p><p> Scarpa ID: {item.shoeId}</p><p>Quantità: {item.quantity}</p></div>}
        </div>
    );
}

export default OrderItemById;
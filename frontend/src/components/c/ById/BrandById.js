import { useState } from "react";

function BrandById() {
    const [id, setId] = useState("");
    const [brand, setBrand] = useState(null);

    const handleSearch = () => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/brands/${id}`, {
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.json())
        .then(data => setBrand(data))
        .catch(() => setBrand(null));
    };

    return (
        <div className="search-container">
            <h3>Cerca Brand</h3>
            <div className="search-box">
                <input type="number" value={id} onChange={(e) => setId(e.target.value)} />
                <button onClick={handleSearch}>Trova</button>
            </div>
            {brand && <div className="detail-card"><p>Nome: {brand.brandName}</p></div>}
        </div>
    );
}

export default BrandById;
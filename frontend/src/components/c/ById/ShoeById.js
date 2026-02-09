import { useState } from "react";

function ShoeById() {
    const [id, setId] = useState("");
    const [shoe, setShoe] = useState(null);
    const [error, setError] = useState(null);

    const handleSearch = () => {
        const token = localStorage.getItem("token");
        fetch(`http://localhost:8080/api/shoes/${id}`, {
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.ok ? res.json() : Promise.reject("Scarpa non trovata"))
        .then(data => { setShoe(data); setError(null); })
        .catch(err => { setError(err); setShoe(null); });
    };

    return (
        <div className="search-container">
            <h3>Dettaglio Scarpa</h3>
            <div className="search-box">
                <input type="number" value={id} onChange={(e) => setId(e.target.value)} placeholder="ID Scarpa..." />
                <button onClick={handleSearch} className="btn-save">Cerca</button>
            </div>
            {shoe && (
                <div className="detail-card">
                    <h4>{shoe.model}</h4>
                    <p>Nome: {shoe.shoeName}</p>
                    <p>Taglia: {shoe.shoeSize}</p>
                    <p>Prezzo: {shoe.shoePrice}€</p>
                    <p>Brand ID: {shoe.brandId}</p>
                    <p>Item ID: {shoe.items.join(", ")}</p>
                    <p>Immagine Url: {shoe.imageUrl}</p>
                </div>
            )}
        </div>
    );
}

export default ShoeById;
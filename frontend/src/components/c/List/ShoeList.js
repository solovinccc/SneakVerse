import { useEffect, useState } from "react";

function ShoeList() {
    const [shoes, setShoes] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/shoes", {
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.ok ? res.json() : Promise.reject(res))
        .then(data => setShoes(data))
        .catch(err => setError("Errore nel caricamento scarpe"));
    }, []);

    if (error) return <div className="error-msg">{error}</div>;

    return (
        <div className="list-container">
            <div className="list-header">
                <h2>Catalogo Scarpe</h2>
                <span className="badge">{shoes.length} Modelli</span>
            </div>
            <table className="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Taglia</th>
                        <th>Prezzo</th>
                        <th>Brand ID</th>
                        <th>Immagine Url</th>                        
                        <th>Item ID</th>
                    </tr>
                </thead>
                <tbody>
                    {shoes.map(shoe => (
                        <tr key={shoe.shoeId}>
                            <td>#{shoe.shoeId}</td>
                            <td>{shoe.shoeName}</td>
                            <td>{shoe.shoeSize}</td>
                            <td>{shoe.shoePrice}€</td>
                            <td>{shoe.brandId}</td>
                            <td>{shoe.imageUrl}</td>
                            <td>{shoe.items.join(", ")}</td>                            
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default ShoeList;
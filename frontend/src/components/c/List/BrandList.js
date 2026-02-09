import { useEffect, useState } from "react";

function BrandList() {
    const [brands, setBrands] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/brands", {
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.ok ? res.json() : Promise.reject(res))
        .then(data => setBrands(data))
        .catch(err => setError("Errore nel caricamento brand"));
    }, []);

    return (
        <div className="list-container">
            <div className="list-header">
                <h2>Marche</h2>
                <span className="badge">{brands.length} Brand</span>
            </div>
            <table className="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nome Brand</th>
                    </tr>
                </thead>
                <tbody>
                    {brands.map(brand => (
                        <tr key={brand.brandId}>
                            <td>#{brand.brandId}</td>
                            <td>{brand.brandName}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default BrandList;
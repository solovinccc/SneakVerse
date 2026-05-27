import React, { useEffect, useState } from "react";

function BrandList() {
    const [brands, setBrands] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://sneakverse.it/api/brands", {
            headers: { "Authorization": `Bearer ${token}` }
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error(`Errore: ${res.status}. Verifica i permessi ADMIN.`);
                }
                return res.json();
            })
            .then(data => setBrands(data))
            .catch(err => {
                console.error("Errore fetch brand:", err);
                setError(err.message);
            });
    }, []);

    if (error) return <div className="error-msg">{error}</div>;

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
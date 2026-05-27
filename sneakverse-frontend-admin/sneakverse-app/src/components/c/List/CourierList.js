import React, { useEffect, useState } from "react";

function CourierList() {
    const [couriers, setCouriers] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://sneakverse.it/api/couriers", {
            headers: { "Authorization": `Bearer ${token}` }
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error(`Errore: ${res.status}. Verifica i permessi ADMIN.`);
                }
                return res.json();
            })
            .then(data => setCouriers(data))
            .catch(err => {
                console.error("Errore fetch couriers:", err);
                setError(err.message);
            });
    }, []);

    if (error) return <div className="error-msg">{error}</div>;

    return (
        <div className="list-container">
            <h2>Corrieri Convenzionati</h2>
            <table className="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Telefono</th>
                    </tr>
                </thead>
                <tbody>
                    {couriers.map(c => (
                        <tr key={c.courierId}>
                            <td>#{c.courierId}</td>
                            <td>{c.phoneNumber}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default CourierList;
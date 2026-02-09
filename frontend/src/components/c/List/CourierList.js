import { useEffect, useState } from "react";

function CourierList() {
    const [couriers, setCouriers] = useState([]);

    useEffect(() => {
        const token = localStorage.getItem("token");
        fetch("http://localhost:8080/api/couriers", {
            headers: { "Authorization": `Bearer ${token}` }
        })
        .then(res => res.json())
        .then(data => setCouriers(data))
        .catch(err => console.error(err));
    }, []);

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
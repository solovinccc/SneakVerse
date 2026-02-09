import { useEffect, useState } from "react";

function UserList() {
    const [users, setUsers] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem("token");

        fetch("http://localhost:8080/api/users", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        })
        .then(res => {
            if (!res.ok) {
                throw new Error(`Errore: ${res.status}. Verifica i permessi ADMIN.`);
            }
            return res.json();
        })
        .then(data => {
            setUsers(data);
        })
        .catch(err => {
            console.error("Errore fetch users: ", err);
            setError(err.message);
        });
    }, []);

    if (error) return <div className="error-msg"> {error}</div>;

    return (
        <div className="list-container">
            <div className="list-header">
                <h2>Gestione Utenti</h2>
                <span className="badge">{users.length} Registrati</span>
            </div>
            
            <table className="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Indirizzo</th>
                        <th>Ordini (ID)</th>
                    </tr>
                </thead>
                <tbody>
                    {users.length === 0 ? (
                        <tr>
                            <td colSpan="4" style={{ textAlign: "center", padding: "20px" }}>
                                Nessun utente trovato.
                            </td>
                        </tr>
                    ) : (
                        users.map(user => (
                            <tr key={user.userId}>
                                <td><strong>#{user.userId}</strong></td>
                                <td>{user.username}</td>
                                <td>{user.homeAddress || "Non specificato"}</td>
                                <td>
                                    {user.orderId && user.orderId.length > 0 ? (
                                        <span className="order-pill">{user.orderId.join(", ")}</span>
                                    ) : (
                                        <span className="no-orders">Nessuno</span>
                                    )}
                                </td>
                            </tr>
                        ))
                    )}
                </tbody>
            </table>
        </div>
    );
}

export default UserList;
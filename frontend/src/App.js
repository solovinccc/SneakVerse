import { useState } from "react";
import Login from "./components/c/Auth/Login";
import Register from "./components/c/Auth/Register";
import Management from "./components/c/View/Management"; 
import UserView from "./components/c/View/UserView"; 

function App() {
    const [role, setRole] = useState(localStorage.getItem("role") || null);
    const [showRegister, setShowRegister] = useState(false);

    const handleLogin = (userRole) => setRole(userRole);

    if(role === "ADMIN") return <Management />;
    if(role === "USER") return <UserView />;

    
    return (
        <div className="App">
            {showRegister ? (
                <Register 
                    onRegister={() => setShowRegister(false)} 
                    onLogin={() => setShowRegister(false)} 
                />
            ) : (
                <Login 
                    onLogin={handleLogin} 
                    onRegister={() => setShowRegister(true)} 
                />
            )}
            
        </div>
    );
}

export default App;
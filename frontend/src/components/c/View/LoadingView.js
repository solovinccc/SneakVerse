import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Css/Loading.css";

function LoadingPage() {
  const navigate = useNavigate();

  useEffect(() => {
    const timer = setTimeout(() => {
      const token = localStorage.getItem("token");
      const role = localStorage.getItem("role"); 

      if (!token) {
        navigate("/login");
        return;
      }

      if (role === "ADMIN") {
        navigate("/management");
      } else {
        navigate("/userview");
      }
    }, 1200); 

    return () => clearTimeout(timer);
  }, [navigate]);

  return (
    <div className="loading-page">
      <div className="loading-logo">SneakVerse</div>
      <div className="loading-spinner"></div>
      <div className="loading-text">Stiamo preparando la tua esperienza…</div>
    </div>
  );
}

export default LoadingPage;

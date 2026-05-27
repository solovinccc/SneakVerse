import React, { useState } from "react";
import "./Css/Management.css";

// --- LIST ---
import UserList from "../List/UserList";
import ShoeList from "../List/ShoeList";
import BrandList from "../List/BrandList";
import OrderList from "../List/OrderList";
import OrderItemList from "../List/OrderItemList";
import ShipmentList from "../List/ShipmentList";
import CourierList from "../List/CourierList";

// --- BY ID ---
import UserById from "../ById/UserById";
import OrderById from "../ById/OrderById";
import ShipmentById from "../ById/ShipmentById";
import CourierById from "../ById/CourierById";
import OrderItemById from "../ById/OrderItemById";
import ShoeById from "../ById/ShoeById";
import BrandById from "../ById/BrandById";

// --- ADD ---
import UserAdd from "../add/UserAdd";
import OrderAdd from "../add/OrderAdd";
import ShipmentAdd from "../add/ShipmentAdd";
import CourierAdd from "../add/CourierAdd";
import OrderItemAdd from "../add/OrderItemAdd";
import ShoeAdd from "../add/ShoeAdd";
import BrandAdd from "../add/BrandAdd";

// --- UPDATE ---
import UserUpdate from "../Update/UserUpdate";
import OrderUpdate from "../Update/OrderUpdate";
import ShipmentUpdate from "../Update/ShipmentUpdate";
import CourierUpdate from "../Update/CourierUpdate";
import OrderItemUpdate from "../Update/OrderItemUpdate";
import ShoeUpdate from "../Update/ShoeUpdate";
import BrandUpdate from "../Update/BrandUpdate";

// --- DELETE ---
import UserDelete from "../Delete/UserDelete";
import OrderDelete from "../Delete/OrderDelete";
import ShipmentDelete from "../Delete/ShipmentDelete";
import CourierDelete from "../Delete/CourierDelete";
import OrderItemDelete from "../Delete/OrderItemDelete";
import ShoeDelete from "../Delete/ShoeDelete";
import BrandDelete from "../Delete/BrandDelete";

function Management() {
  const [all, setAll] = useState("");
  const [currentEntity, setCurrentEntity] = useState("user");

  const renderAll = () => {
    switch (all) {
      // --- LISTE ---
      case "users": return <UserList />;
      case "shoes": return <ShoeList />;
      case "brands": return <BrandList />;
      case "orders": return <OrderList />;
      case "orderItems": return <OrderItemList />;
      case "shipments": return <ShipmentList />;
      case "couriers": return <CourierList />;

      // --- RICERCA ---
      case "userSearch": return <UserById />;
      case "orderSearch": return <OrderById />;
      case "shipmentSearch": return <ShipmentById />;
      case "courierSearch": return <CourierById />;
      case "itemSearch": return <OrderItemById />;
      case "shoeSearch": return <ShoeById />;
      case "brandSearch": return <BrandById />;

      // --- AGGIUNTA ---
      case "userAdd": return <UserAdd />;
      case "orderAdd": return <OrderAdd />;
      case "shipmentAdd": return <ShipmentAdd />;
      case "courierAdd": return <CourierAdd />;
      case "itemAdd": return <OrderItemAdd />;
      case "shoeAdd": return <ShoeAdd />;
      case "brandAdd": return <BrandAdd />;

      // --- UPDATE ---
      case "userUpdate": return <UserUpdate />;
      case "orderUpdate": return <OrderUpdate />;
      case "shipmentUpdate": return <ShipmentUpdate />;
      case "courierUpdate": return <CourierUpdate />;
      case "itemUpdate": return <OrderItemUpdate />;
      case "shoeUpdate": return <ShoeUpdate />;
      case "brandUpdate": return <BrandUpdate />;

      // --- DELETE ---
      case "userDelete": return <UserDelete />;
      case "orderDelete": return <OrderDelete />;
      case "shipmentDelete": return <ShipmentDelete />;
      case "courierDelete": return <CourierDelete />;
      case "itemDelete": return <OrderItemDelete />;
      case "shoeDelete": return <ShoeDelete />;
      case "brandDelete": return <BrandDelete />;

      default:
        return (
          <center>
          <div>
            <h2>Pannello di Gestione</h2>
            <p>Seleziona un'entità e un'operazione per iniziare.</p>
          </div>
          </center>
        );
    }
  };

  
  const selectEntity = (entityName, listKey) => {
    setCurrentEntity(entityName);
    setAll(listKey);
  };

  return (
    <div className="mgmt-container">
      <aside className="mgmt-sidebar">
        <div className="logo-section">
          <h2>SneakVerse</h2>
          <span>Admin Panel</span>
        </div>

        <nav className="nav-menu">
          <div className="nav-section">
            <h4>DATI GENERALI</h4>
            <button className={all === "users" ? "active" : ""} onClick={() => selectEntity("user", "users")}> Utenti</button>
            <button className={all === "shoes" ? "active" : ""} onClick={() => selectEntity("shoe", "shoes")}> Scarpe</button>
            <button className={all === "brands" ? "active" : ""} onClick={() => selectEntity("brand", "brands")}> Brand</button>
            <button className={all === "orders" ? "active" : ""} onClick={() => selectEntity("order", "orders")}> Ordini</button>
            <button className={all === "orderItems" ? "active" : ""} onClick={() => selectEntity("item", "orderItems")}> Item Ordini</button>
            <button className={all === "shipments" ? "active" : ""} onClick={() => selectEntity("shipment", "shipments")}> Spedizioni</button>
            <button className={all === "couriers" ? "active" : ""} onClick={() => selectEntity("courier", "couriers")}> Corrieri</button>
          </div>

          <div className="nav-section">
            <h4>OPERAZIONI ({currentEntity.toUpperCase()})</h4>
            <div className="action-buttons">
              <button onClick={() => setAll(`${currentEntity}Search`)}> Cerca</button>
              <button onClick={() => setAll(`${currentEntity}Add`)}> Aggiungi</button>
              <button onClick={() => setAll(`${currentEntity}Update`)}> Modifica</button>
              <button onClick={() => setAll(`${currentEntity}Delete`)}> Elimina</button>
            </div>
            <h4>NOTA BENE: <br/> Selezionare entità, e in seguito l'operazione</h4>
          </div>
        </nav>
      </aside>

      <main className="mgmt-main">
        <header className="mgmt-topbar">
          <div className="breadcrumb">Management / {all || "Dashboard"}</div>
          <button className="logout-btn" onClick={() => {localStorage.clear(); window.location.reload();}}>Logout</button>
        </header>
        
        <div className="mgmt-card">
          {renderAll()}
        </div>
      </main>
    </div>
  );
}

export default Management;
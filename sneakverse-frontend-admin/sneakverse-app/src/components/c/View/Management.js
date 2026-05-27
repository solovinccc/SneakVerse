import React, { useState } from "react";

import UserList from "../List/UserList";
import ShoeList from "../List/ShoeList";
import BrandList from "../List/BrandList";
import OrderList from "../List/OrderList";
import OrderItemList from "../List/OrderItemList";
import ShipmentList from "../List/ShipmentList";
import CourierList from "../List/CourierList";

import UserById from "../ById/UserById";
import OrderById from "../ById/OrderById";
import ShipmentById from "../ById/ShipmentById";
import CourierById from "../ById/CourierById";
import OrderItemById from "../ById/OrderItemById";
import ShoeById from "../ById/ShoeById";
import BrandById from "../ById/BrandById";

import UserAdd from "../add/UserAdd";
import OrderAdd from "../add/OrderAdd";
import ShipmentAdd from "../add/ShipmentAdd";
import CourierAdd from "../add/CourierAdd";
import OrderItemAdd from "../add/OrderItemAdd";
import ShoeAdd from "../add/ShoeAdd";
import BrandAdd from "../add/BrandAdd";

import UserUpdate from "../Update/UserUpdate";
import OrderUpdate from "../Update/OrderUpdate";
import ShipmentUpdate from "../Update/ShipmentUpdate";
import CourierUpdate from "../Update/CourierUpdate";
import OrderItemUpdate from "../Update/OrderItemUpdate";
import ShoeUpdate from "../Update/ShoeUpdate";
import BrandUpdate from "../Update/BrandUpdate";

import UserDelete from "../Delete/UserDelete";
import OrderDelete from "../Delete/OrderDelete";
import ShipmentDelete from "../Delete/ShipmentDelete";
import CourierDelete from "../Delete/CourierDelete";
import OrderItemDelete from "../Delete/OrderItemDelete";
import ShoeDelete from "../Delete/ShoeDelete";
import BrandDelete from "../Delete/BrandDelete";

function Management({ token, onLogout }) {
  const [all, setAll] = useState("");
  const [currentEntity, setCurrentEntity] = useState("user");

  const props = { token };

  const renderAll = () => {
    switch (all) {
      case "users": return <UserList {...props} />;
      case "shoes": return <ShoeList {...props} />;
      case "brands": return <BrandList {...props} />;
      case "orders": return <OrderList {...props} />;
      case "orderItems": return <OrderItemList {...props} />;
      case "shipments": return <ShipmentList {...props} />;
      case "couriers": return <CourierList {...props} />;

      case "userSearch": return <UserById {...props} />;
      case "orderSearch": return <OrderById {...props} />;
      case "shipmentSearch": return <ShipmentById {...props} />;
      case "courierSearch": return <CourierById {...props} />;
      case "itemSearch": return <OrderItemById {...props} />;
      case "shoeSearch": return <ShoeById {...props} />;
      case "brandSearch": return <BrandById {...props} />;

      case "userAdd": return <UserAdd {...props} />;
      case "orderAdd": return <OrderAdd {...props} />;
      case "shipmentAdd": return <ShipmentAdd {...props} />;
      case "courierAdd": return <CourierAdd {...props} />;
      case "itemAdd": return <OrderItemAdd {...props} />;
      case "shoeAdd": return <ShoeAdd {...props} />;
      case "brandAdd": return <BrandAdd {...props} />;

      case "userUpdate": return <UserUpdate {...props} />;
      case "orderUpdate": return <OrderUpdate {...props} />;
      case "shipmentUpdate": return <ShipmentUpdate {...props} />;
      case "courierUpdate": return <CourierUpdate {...props} />;
      case "itemUpdate": return <OrderItemUpdate {...props} />;
      case "shoeUpdate": return <ShoeUpdate {...props} />;
      case "brandUpdate": return <BrandUpdate {...props} />;

      case "userDelete": return <UserDelete {...props} />;
      case "orderDelete": return <OrderDelete {...props} />;
      case "shipmentDelete": return <ShipmentDelete {...props} />;
      case "courierDelete": return <CourierDelete {...props} />;
      case "itemDelete": return <OrderItemDelete {...props} />;
      case "shoeDelete": return <ShoeDelete {...props} />;
      case "brandDelete": return <BrandDelete {...props} />;

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

  const handleLogout = () => {
    if (window.Liferay) {
      window.location.href = "/c/portal/logout";
    } else {
      onLogout();
    }
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
            <button className={all === "users" ? "active" : ""} onClick={() => selectEntity("user", "users")}>Utenti</button>
            <button className={all === "shoes" ? "active" : ""} onClick={() => selectEntity("shoe", "shoes")}>Scarpe</button>
            <button className={all === "brands" ? "active" : ""} onClick={() => selectEntity("brand", "brands")}>Brand</button>
            <button className={all === "orders" ? "active" : ""} onClick={() => selectEntity("order", "orders")}>Ordini</button>
            <button className={all === "orderItems" ? "active" : ""} onClick={() => selectEntity("item", "orderItems")}>Item Ordini</button>
            <button className={all === "shipments" ? "active" : ""} onClick={() => selectEntity("shipment", "shipments")}>Spedizioni</button>
            <button className={all === "couriers" ? "active" : ""} onClick={() => selectEntity("courier", "couriers")}>Corrieri</button>
          </div>

          <div className="nav-section">
            <h4>OPERAZIONI ({currentEntity.toUpperCase()})</h4>
            <div className="action-buttons">
              <button onClick={() => setAll(`${currentEntity}Search`)}>Cerca</button>
              <button onClick={() => setAll(`${currentEntity}Add`)}>Aggiungi</button>
              <button onClick={() => setAll(`${currentEntity}Update`)}>Modifica</button>
              <button onClick={() => setAll(`${currentEntity}Delete`)}>Elimina</button>
            </div>
            <h4>NOTA BENE: <br /> Selezionare entità, e in seguito l'operazione</h4>
          </div>
        </nav>
      </aside>

      <main className="mgmt-main">
        <header className="mgmt-topbar">
          <div className="breadcrumb">Management / {all || "Dashboard"}</div>
          <button className="logout-btn" onClick={handleLogout}>Logout</button>
        </header>
        <div className="mgmt-card">{renderAll()}</div>
      </main>
    </div>
  );
}

export default Management;
import { useEffect, useMemo, useState, useCallback } from "react";
import axios from "axios";
import "./Css/styles.css";
import "./Css/premium.css";
import { useCart } from "../context/CartContext";
import Footer from "./Footer";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

function useToast() {
  const [toasts, setToasts] = useState([]);
  const show = useCallback((message, type = "default") => {
    const id = Date.now();
    setToasts(prev => [...prev, { id, message, type }]);
    setTimeout(() => setToasts(prev => prev.filter(t => t.id !== id)), 3000);
  }, []);
  return { toasts, show };
}

function SkeletonGrid() {
  return (
    <div className="uv-skeleton-grid">
      {Array.from({ length: 6 }).map((_, i) => (
        <div className="uv-skeleton-card" key={i}>
          <div className="uv-skeleton-img" />
          <div className="uv-skeleton-text">
            <div className="uv-skeleton-line" />
            <div className="uv-skeleton-line short" />
            <div className="uv-skeleton-line btn" />
          </div>
        </div>
      ))}
    </div>
  );
}

function UserStore({ userEmail, userId, token, onLogout, onNavigateProfile }) {
  const [shoes, setShoes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const toast = useToast();

  const [search, setSearch] = useState("");
  const [brandSearch, setBrandSearch] = useState("");
  const [size, setSize] = useState("ALL");
  const [priceMin, setPriceMin] = useState("");
  const [priceMax, setPriceMax] = useState("");
  const [sort, setSort] = useState("featured");
  const [addedMap, setAddedMap] = useState({});

  const { cart, addToCart, removeFromCart, updateQuantity, clearCart, cartTotal, cartCount } = useCart();

  const [cartOpen, setCartOpen] = useState(false);

  const [ordersOpen, setOrdersOpen] = useState(false);
  const [orders, setOrders] = useState([]);
  const [ordersLoading, setOrdersLoading] = useState(false);
  const [expandedOrderId, setExpandedOrderId] = useState(null);

  const [payOpen, setPayOpen] = useState(false);
  const [paymentMethod, setPaymentMethod] = useState("CARD");
  const [checkoutLoading, setCheckoutLoading] = useState(false);

  const [selectedShoe, setSelectedShoe] = useState(null);
  const [selectedSize, setSelectedSize] = useState("");
  const [viewModalOpen, setViewModalOpen] = useState(false);

  const [cardNumber, setCardNumber] = useState("");
  const [cardName, setCardName] = useState("");
  const [cardExp, setCardExp] = useState("");
  const [cardCvv, setCardCvv] = useState("");
  const [paypalEmail, setPaypalEmail] = useState("");
  const [userProfile, setUserProfile] = useState(null);

  const authHeader = { headers: { Authorization: `Bearer ${token}` } };

  const onlyDigits = (s) => String(s || "").replace(/\D/g, "");
  const formatCardNumber = (raw) => { const d = onlyDigits(raw).slice(0, 16); return d.replace(/(.{4})/g, "$1 ").trim(); };
  const formatExp = (raw) => { const d = onlyDigits(raw).slice(0, 4); if (d.length <= 2) return d; return `${d.slice(0, 2)}/${d.slice(2)}`; };
  const isEmail = (v) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(String(v || "").trim());

  const paymentValid = useMemo(() => {
    if (paymentMethod === "CARD") {
      const n = onlyDigits(cardNumber);
      const exp = String(cardExp || "").trim();
      const cvv = onlyDigits(cardCvv);
      const name = String(cardName || "").trim();
      return n.length === 16 && exp.length === 5 && cvv.length >= 3 && cvv.length <= 4 && name.length >= 3;
    }
    if (paymentMethod === "PAYPAL") return isEmail(paypalEmail);
    if (paymentMethod === "CASH_ON_DELIVERY") return true;
    return false;
  }, [paymentMethod, cardNumber, cardExp, cardCvv, cardName, paypalEmail]);

  useEffect(() => {
    setCardNumber(""); setCardName(""); setCardExp(""); setCardCvv(""); setPaypalEmail("");
  }, [paymentMethod]);

  const fetchAllShoes = async () => {
    const res = await axios.get(`${API_BASE_URL}/shoes`, authHeader);
    return res.data;
  };

  const fetchOrders = async () => {
    if (!userId) return;
    setOrdersLoading(true);
    try {
      const rawToken = localStorage.getItem("token") || "";
      const cleanToken = rawToken.replace(/^"|"$/g, '');
      if (!cleanToken) {
        setError("Effettua il login per vedere gli ordini.");
        setOrdersLoading(false);
        return;
      }
      const config = { headers: { Authorization: `Bearer ${cleanToken}` } };
      const res = await axios.get(`${API_BASE_URL}/orders/user/${userId}`, config);
      setOrders(Array.isArray(res.data) ? res.data : []);
    } catch (e) {
      console.error("Errore completo dal backend:", e.response || e);
      toast.show("Errore caricamento ordini", "error");
    } finally {
      setOrdersLoading(false);
    }
  };

  const formatDate = (dateStr) => {
    if (!dateStr) return "N/D";
    try {
      return new Intl.DateTimeFormat("it-IT", {
        day: "numeric",
        month: "short",
        year: "numeric",
        hour: "2-digit",
        minute: "2-digit"
      }).format(new Date(dateStr));
    } catch {
      return dateStr;
    }
  };

  const getStatusInfo = (status) => {
    const s = String(status || "").toUpperCase();
    switch (s) {
      case "PROCESSING": return { label: "In preparazione", class: "processing", step: 1, text: "Stiamo preparando il tuo pacco." };
      case "SHIPPED": return { label: "Spedito", class: "shipped", step: 2, text: "Affidato al corriere." };
      case "DELIVERED": return { label: "Consegnato", class: "delivered", step: 3, text: "Consegna completata." };
      default: return { label: "Ricevuto", class: "processing", step: 1, text: "Ordine confermato." };
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        setShoes(await fetchAllShoes());
      } catch (err) {
        setError(err?.message || "Errore");
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [token]);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const res = await axios.get(`${API_BASE_URL}/users/${userId}`, authHeader);
        setUserProfile(res.data);
      } catch (e) {
        console.error("Errore caricamento profilo:", e);
      }
    };
    if (userId && token) fetchProfile();
  }, [userId, token, payOpen]); 

  useEffect(() => {
    try { localStorage.setItem("cart", JSON.stringify(cart)); } catch { }
  }, [cart]);

  useEffect(() => {
    const t = setTimeout(async () => {
      const q = brandSearch.trim();
      try {
        if (q === "") { setShoes(await fetchAllShoes()); setError(""); return; }
        const res = await axios.get(`${API_BASE_URL}/shoes/by-brand-name?brand=${encodeURIComponent(q)}`, authHeader);
        setShoes(res.data);
        setError("");
      } catch (e) {
        setError("Brand non trovato");
      }
    }, 350);
    return () => clearTimeout(t);
  }, [brandSearch, token]);

  const handleAddToCart = (shoe) => {
    const cartProduct = {
      id: shoe.shoeId,
      name: shoe.shoeName,
      price: Number(shoe.shoePrice ?? 0),
      size: shoe.shoeSize,
      image: shoe.imageUrl
    };
    addToCart(cartProduct);
    setAddedMap((m) => ({ ...m, [shoe.shoeId]: true }));
    setTimeout(() => setAddedMap((m) => ({ ...m, [shoe.shoeId]: false })), 1200);
    toast.show(`${shoe.shoeName} aggiunto al carrello ✓`, "success");
  };

  const openViewModal = (shoe) => {
    setSelectedShoe(shoe);
    setSelectedSize(shoe.shoeSize);
    setViewModalOpen(true);
  };

  const handleAddToCartFromModal = () => {
    if (!selectedShoe || !selectedSize) return;
    const finalShoe = shoes.find(s => s.shoeName === selectedShoe.shoeName && Number(s.shoeSize) === Number(selectedSize));
    if (finalShoe) {
      handleAddToCart(finalShoe);
      setViewModalOpen(false);
    } else {
      toast.show("Taglia non disponibile", "error");
    }
  };

  const sizes = useMemo(() => {
    const set = new Set(shoes.map((s) => s?.shoeSize).filter((v) => v != null).map((v) => Number(v)));
    return ["ALL", ...Array.from(set).sort((a, b) => a - b).map((n) => String(n))];
  }, [shoes]);

  const filteredShoes = useMemo(() => {
    const q = search.trim().toLowerCase();
    const min = priceMin === "" ? null : Number(priceMin);
    const max = priceMax === "" ? null : Number(priceMax);
    let list = shoes.filter((s) => {
      const nameOk = q === "" || String(s.shoeName || "").toLowerCase().includes(q);
      const sizeOk = size === "ALL" || Number(s.shoeSize) === Number(size);
      const price = Number(s.shoePrice ?? 0);
      const minOk = min === null || (!Number.isNaN(min) && price >= min);
      const maxOk = max === null || (!Number.isNaN(max) && price <= max);
      return nameOk && sizeOk && minOk && maxOk;
    });
    switch (sort) {
      case "price_asc": list.sort((a, b) => Number(a.shoePrice ?? 0) - Number(b.shoePrice ?? 0)); break;
      case "price_desc": list.sort((a, b) => Number(b.shoePrice ?? 0) - Number(a.shoePrice ?? 0)); break;
      case "name_asc": list.sort((a, b) => String(a.shoeName ?? "").localeCompare(String(b.shoeName ?? ""))); break;
      case "name_desc": list.sort((a, b) => String(b.shoeName ?? "").localeCompare(String(a.shoeName ?? ""))); break;
      default: break;
    }
    return list;
  }, [shoes, search, size, priceMin, priceMax, sort]);

  const openOrders = async () => { setOrdersOpen(true); await fetchOrders(); };
  const proceedToCheckout = () => { if (!cart.length) return; setPayOpen(true); };

  const confirmCheckout = async () => {
    if (!userId) { toast.show("Utente non valido. Ricarica la pagina.", "error"); return; }
    if (!cart.length || !paymentValid) return;
    setCheckoutLoading(true);
    setError("");
    try {
      const body = { 
        userId, 
        paymentMethod, 
        items: cart.map((c) => ({ shoeId: c.id, quantity: c.quantity })),
        cardNumber: paymentMethod === "CARD" ? cardNumber : null,
        expiryDate: paymentMethod === "CARD" ? cardExp : null,
        cvv: paymentMethod === "CARD" ? cardCvv : null
      };
      await axios.post(`${API_BASE_URL}/orders/checkout`, body, authHeader);
      clearCart();
      setPayOpen(false);
      setCartOpen(false);
      setOrdersOpen(true);
      await fetchOrders();
      toast.show("Ordine confermato! 🎉", "success");
    } catch (e) {
      toast.show(e.response?.data?.message || "Errore checkout", "error");
    } finally {
      setCheckoutLoading(false);
    }
  };

  const closeAllOverlays = () => { setCartOpen(false); setOrdersOpen(false); setPayOpen(false); setViewModalOpen(false); };

  if (loading) return (
    <div className="uv-page">
      <div className="uv-promo"><span className="uv-promo-inner">🔥 SPEDIZIONE GRATUITA SOPRA I €100 &nbsp;•&nbsp; 🏷️ NUOVI ARRIVI OGNI SETTIMANA &nbsp;•&nbsp; ⚡ RESI GRATUITI ENTRO 30 GIORNI</span></div>
      <header className="uv-topbar"><div className="uv-topbar-left"><div className="uv-title">SneakVerse</div></div></header>
      <div className="uv-content"><div className="uv-gridArea"><SkeletonGrid /></div></div>
    </div>
  );

  return (
    <div className="uv-page">
      <div className="uv-toast-container">
        {toast.toasts.map(t => (
          <div key={t.id} className={`uv-toast ${t.type}`}>{t.message}</div>
        ))}
      </div>

      {(cartOpen || ordersOpen || payOpen || viewModalOpen) && (
        <div className="uv-cartOverlay" role="button" tabIndex={0} onClick={closeAllOverlays} onKeyDown={(e) => { if (e.key === "Escape") closeAllOverlays(); }} />
      )}

      <aside className={`uv-cartDrawer ${cartOpen ? "open" : ""}`}>
        <div className="uv-cartHeader">
          <div className="uv-cartTitle">Carrello</div>
          <button className="uv-cartClose" onClick={() => setCartOpen(false)}>✕</button>
        </div>
        {cart.length === 0 ? (
          <div className="uv-cartList" style={{ alignItems: "center", justifyContent: "center", color: "var(--text-muted)" }}>Il carrello è vuoto.</div>
        ) : (
          <>
            <div className="uv-cartList">
              {cart.map((item) => (
                <div className="uv-cartRow" key={item.id}>
                  <div className="uv-cartRowLeft">
                    <div className="uv-cartName">{item.name}</div>
                    <div className="uv-muted">Taglia {item.size} · €{Number(item.price ?? 0).toFixed(2)}</div>
                    <div className="uv-cartQty">
                      <button className="uv-qtyBtn" onClick={() => updateQuantity(item.id, -1)}>−</button>
                      <span>{item.quantity}</span>
                      <button className="uv-qtyBtn" onClick={() => updateQuantity(item.id, 1)}>+</button>
                      <button className="uv-removeBtn" onClick={() => removeFromCart(item.id)}>Rimuovi</button>
                    </div>
                  </div>
                  <div className="uv-cartRowRight">€{(Number(item.price ?? 0) * Number(item.quantity ?? 0)).toFixed(2)}</div>
                </div>
              ))}
            </div>
            <div className="uv-cartFooter">
              <div className="uv-cartTotalRow"><span>Totale</span><strong>€{cartTotal.toFixed(2)}</strong></div>
              <button className="uv-btn uv-btnPrimary" onClick={proceedToCheckout}>Procedi all'acquisto</button>
              <button className="uv-btn uv-btnSecondary" onClick={clearCart}>Svuota carrello</button>
            </div>
          </>
        )}
      </aside>

      <aside className={`uv-ordersDrawer ${ordersOpen ? "open" : ""}`}>
        <div className="uv-cartHeader">
          <div className="uv-cartTitle">I tuoi Ordini Premium</div>
          <button className="uv-cartClose" onClick={() => setOrdersOpen(false)}>✕</button>
        </div>
        <div className="uv-ordersList">
          {ordersLoading ? (
            <div style={{ padding: "60px 20px", textAlign: "center" }}>
              <div className="uv-spinner" style={{ margin: "0 auto" }}></div>
              <p style={{ color: "var(--text-muted)", marginTop: "16px", fontWeight: 600 }}>Sincronizzazione ordini...</p>
            </div>
          ) : orders.length === 0 ? (
            <div style={{ padding: "60px 20px", textAlign: "center", color: "var(--text-muted)" }}>
              <div style={{ fontSize: "3.5rem", marginBottom: "16px" }}>📦</div>
              <p style={{ fontWeight: 600, fontSize: "1.1rem" }}>Nessun ordine effettuato.</p>
              <p style={{ fontSize: "0.85rem", marginTop: "8px" }}>I tuoi acquisti premium appariranno qui.</p>
            </div>
          ) : (
            orders.map((o) => {
              const isExpanded = expandedOrderId === o.orderId;
              const status = getStatusInfo(o.status);

              const estDelivery = new Date(new Date(o.orderDate).getTime() + (3 * 24 * 60 * 60 * 1000)).toLocaleDateString('it-IT', { day: 'numeric', month: 'short' });
              const totalAmount = Number(o.total ?? 0);
              const shippingCost = totalAmount > 100 ? 0 : 9.90;
              const subTotal = totalAmount - shippingCost;
              const progressWidth = status.step === 1 ? "0%" : status.step === 2 ? "50%" : "100%";

              return (
                <div
                  className={`uv-orderCard-premium ${isExpanded ? "expanded" : ""}`}
                  key={o.orderId}
                  onClick={() => setExpandedOrderId(isExpanded ? null : o.orderId)}
                >
                  <div className="uv-orderHeader-main">
                    <div>
                      <div className="uv-orderId-tag">ORDINE #{o.orderId}</div>
                      <div className="uv-orderDate">{formatDate(o.orderDate)}</div>
                      <div className={`uv-status-badge ${status.class}`}>{status.label}</div>
                    </div>
                    <div className="uv-orderTotal-main">€{totalAmount.toFixed(2)}</div>
                  </div>

                  <div className="uv-order-timeline">
                    <div className="uv-timeline-track">
                      <div className="uv-timeline-progress" style={{ width: progressWidth }}></div>
                    </div>

                    <div className={`uv-timeline-step-wrap ${status.step >= 1 ? "active" : ""}`}>
                      <div className="uv-timeline-dot">✓</div>
                      <div className="uv-timeline-label">CONFERMATO</div>
                    </div>
                    <div className={`uv-timeline-step-wrap ${status.step >= 2 ? "active" : ""}`}>
                      <div className="uv-timeline-dot">{status.step >= 2 ? "✓" : ""}</div>
                      <div className="uv-timeline-label">SPEDITO</div>
                    </div>
                    <div className={`uv-timeline-step-wrap ${status.step >= 3 ? "active" : ""}`}>
                      <div className="uv-timeline-dot">{status.step >= 3 ? "✓" : ""}</div>
                      <div className="uv-timeline-label">CONSEGNATO</div>
                    </div>
                  </div>

                  {!isExpanded && (
                    <button className="uv-open-order-btn">Dettagli Ordine</button>
                  )}

                  {isExpanded && (
                    <div className="uv-order-details-expanded" onClick={e => e.stopPropagation()}>

                      <div className="uv-order-items-wrapper">
                        {(o.items || []).map((it) => (
                          <div key={`${o.orderId}-${it.shoeId}`} className="uv-order-item-row">
                            <div style={{ display: "flex", gap: "16px", alignItems: "center" }}>
                              <div className="uv-order-item-info">
                                <div className="uv-order-item-name">{it.shoeName}</div>
                                <div className="uv-order-item-meta">Taglia: {it.shoeSize} &nbsp;|&nbsp; Qtà: {it.quantity}</div>
                              </div>
                            </div>
                            <div className="uv-order-item-price">
                              €{(Number(it.shoePrice ?? 0) * Number(it.quantity ?? 0)).toFixed(2)}
                            </div>
                          </div>
                        ))}
                      </div>

                      <div className="uv-order-breakdown">
                        <div className="uv-breakdown-row"><span>Subtotale</span><span>€{subTotal.toFixed(2)}</span></div>
                        <div className="uv-breakdown-row"><span>Spedizione</span><span>{shippingCost === 0 ? "Gratuita" : `€${shippingCost.toFixed(2)}`}</span></div>
                        <div className="uv-breakdown-row total"><span>Totale Ordine</span><span>€{totalAmount.toFixed(2)}</span></div>
                      </div>

                      <hr className="uv-divider-dotted" />

                      <div className="uv-order-extra-info">
                        <div className="uv-info-block">
                          <span className="uv-detail-label">PAGAMENTO</span>
                          <span className="uv-detail-value" style={{ fontSize: "0.85rem", textTransform: "capitalize" }}>
                            {String(o.paymentMethod || "CARTA").replace(/_/g, " ").toLowerCase()}
                          </span>
                        </div>
                        <div className="uv-info-block">
                          <span className="uv-detail-label">CONSEGNA STIMATA</span>
                          <span className="uv-detail-value">Entro il {estDelivery}</span>
                        </div>
                      </div>

                      <div className="uv-order-support-hint">
                        Hai bisogno di aiuto? <span onClick={() => toast.show("Servizio Clienti: support@sneakverse.com", "default")}>Contatta l'assistenza</span>
                      </div>

                      <div className="uv-order-actions">
                        <button className="uv-order-btn outline" onClick={() => setExpandedOrderId(null)}>Chiudi</button>
                        <button className="uv-order-btn solid" onClick={() => toast.show("Tracciamento: IT" + o.orderId + "NX", "default")}>
                          Traccia Pacco
                        </button>
                      </div>

                    </div>
                  )}
                </div>
              );
            })

          )}
        </div>
      </aside>

      {payOpen && (
        <div className="uv-payModal">
          <div className="uv-payCard">
            <div className="uv-payTitle">Metodo di pagamento</div>
            <div className="uv-paySubtitle">Inserisci i dati (non verranno salvati).</div>
            <div className="uv-payGroup">
              <label className="uv-payOption"><input type="radio" name="pay" value="CARD" checked={paymentMethod === "CARD"} onChange={(e) => setPaymentMethod(e.target.value)} /><span>Carta</span></label>
              <label className="uv-payOption"><input type="radio" name="pay" value="PAYPAL" checked={paymentMethod === "PAYPAL"} onChange={(e) => setPaymentMethod(e.target.value)} /><span>PayPal</span></label>
              <label className="uv-payOption"><input type="radio" name="pay" value="CASH_ON_DELIVERY" checked={paymentMethod === "CASH_ON_DELIVERY"} onChange={(e) => setPaymentMethod(e.target.value)} /><span>Contrassegno</span></label>
            </div>
            {paymentMethod === "CARD" && (
              <div className="uv-payDetails">
                <div><label className="uv-payLabel">Numero carta</label><input className="uv-input" inputMode="numeric" placeholder="1234 5678 9012 3456" value={cardNumber} onChange={(e) => setCardNumber(formatCardNumber(e.target.value))} /></div>
                <div><label className="uv-payLabel">Intestatario</label><input className="uv-input" placeholder="Nome e Cognome" value={cardName} onChange={(e) => setCardName(e.target.value)} /></div>
                <div className="uv-payRow two">
                  <div><label className="uv-payLabel">Scadenza</label><input className="uv-input" inputMode="numeric" placeholder="MM/AA" value={cardExp} onChange={(e) => setCardExp(formatExp(e.target.value))} /></div>
                  <div><label className="uv-payLabel">CVV</label><input className="uv-input" inputMode="numeric" placeholder="123" value={cardCvv} onChange={(e) => setCardCvv(onlyDigits(e.target.value).slice(0, 4))} /></div>
                </div>
              </div>
            )}
            {paymentMethod === "PAYPAL" && (
              <div className="uv-payDetails">
                <label className="uv-payLabel">Email PayPal</label>
                <input className="uv-input" inputMode="email" placeholder="nome@paypal.com" value={paypalEmail} onChange={(e) => setPaypalEmail(e.target.value)} />
              </div>
            )}
            <div className="uv-payTotal"><span>Totale da pagare</span><strong>€{cartTotal.toFixed(2)}</strong></div>
            <div className="uv-payActions">
              <button className="uv-btn uv-btnSecondary" onClick={() => setPayOpen(false)} disabled={checkoutLoading}>Indietro</button>
              <button className="uv-btn uv-btnPrimary" onClick={confirmCheckout} disabled={checkoutLoading || !paymentValid}>{checkoutLoading ? "Elaborazione..." : "Conferma Ordine"}</button>
            </div>
          </div>
        </div>
      )}

      <div className="uv-promo">
        <span className="uv-promo-inner">🔥 SPEDIZIONE GRATUITA SOPRA I €100 &nbsp;&nbsp;•&nbsp;&nbsp; 🏷️ NUOVI ARRIVI OGNI SETTIMANA &nbsp;&nbsp;•&nbsp;&nbsp; ⚡ RESI GRATUITI ENTRO 30 GIORNI &nbsp;&nbsp;•&nbsp;&nbsp; 🔥 SPEDIZIONE GRATUITA SOPRA I €100</span>
      </div>

      <header className="uv-topbar">
        <div className="uv-topbar-left">
          <div className="uv-title">SneakVerse</div>
          <div className="uv-subtitle">{filteredShoes.length} risultati</div>
        </div>
        <div className="uv-topbar-right">
          <button className="uv-cartPill" onClick={() => setCartOpen(true)}>Carrello <span className="uv-cartBadge">{cartCount}</span></button>
          <button className="uv-ordersPill" onClick={openOrders}>Ordini <span className="uv-ordersBadge">{orders.length}</span></button>
          <button className="uv-btn uv-btnSecondary" onClick={onNavigateProfile} style={{ padding: '8px 16px', fontSize: '0.8rem' }}>Profilo</button>
          <div style={{ display: "flex", alignItems: "center", gap: "8px", marginLeft: "12px" }}>
            <span style={{ fontSize: "0.875rem", fontWeight: 600 }}>Ordina:</span>
            <select className="uv-select" style={{ padding: "8px", width: "auto" }} value={sort} onChange={(e) => setSort(e.target.value)}>
              <option value="featured">In evidenza</option>
              <option value="price_asc">Prezzo crescente</option>
              <option value="price_desc">Prezzo decrescente</option>
            </select>
          </div>
          <button className="uv-logout" onClick={onLogout}>Logout</button>
        </div>
      </header>

      <div className="uv-content">
        <aside className="uv-filters">
          <div className="uv-filterBlock">
            <div className="uv-filterTitle">Ricerca modello</div>
            <input className="uv-input" placeholder="Cerca modello..." value={search} onChange={(e) => setSearch(e.target.value)} />
          </div>
          <div className="uv-filterBlock">
            <div className="uv-filterTitle">Ricerca brand</div>
            <input className="uv-input" placeholder="Es. Nike, Adidas..." value={brandSearch} onChange={(e) => setBrandSearch(e.target.value)} />
          </div>
          <div className="uv-filterBlock">
            <div className="uv-filterTitle">Taglia</div>
            <select className="uv-select" value={size} onChange={(e) => setSize(e.target.value)}>
              {sizes.map((s) => <option key={s} value={s}>{s === "ALL" ? "Tutte le taglie" : s}</option>)}
            </select>
          </div>
          <div className="uv-filterBlock">
            <div className="uv-filterTitle">Fascia di prezzo</div>
            <div style={{ display: "flex", gap: "8px" }}>
              <input className="uv-input" inputMode="decimal" placeholder="Min €" value={priceMin} onChange={(e) => setPriceMin(e.target.value)} />
              <input className="uv-input" inputMode="decimal" placeholder="Max €" value={priceMax} onChange={(e) => setPriceMax(e.target.value)} />
            </div>
          </div>
          <button className="uv-btn uv-btnSecondary" style={{ width: "100%" }} onClick={() => { setSearch(""); setBrandSearch(""); setSize("ALL"); setPriceMin(""); setPriceMax(""); setSort("featured"); }}>
            Reset Filtri
          </button>
        </aside>
        <main className="uv-gridArea">
          {error && <div style={{ color: "red", fontWeight: "bold", marginBottom: "20px" }}>{error}</div>}
          <div className="uv-grid full">
            {filteredShoes.map((shoe, index) => (
              <article className="uv-card" key={shoe.shoeId} style={{ animationDelay: `${Math.min(index * 0.05, 0.4)}s` }}>
                <div className="uv-imgWrap">
                  {index < 3 && <span className="uv-badge-new">New</span>}
                  {shoe.imageUrl ? (
                    <img className="uv-img" src={`/images/${shoe.imageUrl}`} alt={shoe.shoeName} onError={(e) => { e.currentTarget.onerror = null; e.currentTarget.src = "https://via.placeholder.com/600x400?text=No+Image"; }} />
                  ) : (
                    <div className="uv-imgFallback">👟</div>
                  )}
                </div>
                <div className="uv-meta">
                  <div className="uv-verified-badge">Verified Authentic</div>
                  <div className="uv-name" title={shoe.shoeName}>{shoe.shoeName}</div>
                  <div className="uv-muted">Taglia {shoe.shoeSize}</div>
                  <div className="uv-price-wrap">
                    <div className="uv-price">€{Number(shoe.shoePrice ?? 0).toFixed(2)}</div>
                    <div className={`uv-price-trend ${index % 3 === 0 ? "down" : ""}`}>
                      {index % 3 === 0 ? "↓" : "↑"} €{(Math.random() * 20).toFixed(0)}
                    </div>
                  </div>
                </div>
                <button className="uv-btn uv-btnPrimary" onClick={() => openViewModal(shoe)}>
                  Visualizza
                </button>
              </article>
            ))}
          </div>
        </main>
      </div>

      {viewModalOpen && selectedShoe && (
        <div className="uv-viewModal-overlay" onClick={() => setViewModalOpen(false)}>
          <div className="uv-viewCard-fixed" onClick={(e) => e.stopPropagation()}>
            <button className="uv-viewClose-fixed" onClick={() => setViewModalOpen(false)}>✕</button>

            <div className="uv-viewLeft-fixed">
              <div className="uv-badge-authentic">✓ Verified Authentic</div>
              {selectedShoe.imageUrl ? (
                <img className="uv-viewImg-fixed" src={`/images/${selectedShoe.imageUrl}`} alt={selectedShoe.shoeName} />
              ) : (
                <div className="uv-viewImgFallback">👟</div>
              )}
            </div>

            <div className="uv-viewRight-fixed">
              <div className="uv-viewHeader-fixed">
                <div className="uv-viewBrand">SNEAKVERSE PREMIUM</div>
                <div className="uv-viewName-fixed">{selectedShoe.shoeName}</div>
                <div className="uv-viewPrice-fixed">€{Number(selectedShoe.shoePrice ?? 0).toFixed(2)}</div>
              </div>

              <div className="uv-viewScrollArea">
                <div className="uv-modal-bento">
                <div className="uv-modal-bento-item">
                  <span className="uv-modal-bento-label">Condizione</span>
                  <span className="uv-modal-bento-value">Nuovo (DS)</span>
                </div>
                <div className="uv-modal-bento-item">
                  <span className="uv-modal-bento-label">Autenticazione</span>
                  <span className="uv-modal-bento-value">Garantita</span>
                </div>
                <div className="uv-modal-bento-item">
                  <span className="uv-modal-bento-label">Spedizione</span>
                  <span className="uv-modal-bento-value">Gratuita</span>
                </div>
                <div className="uv-modal-bento-item">
                  <span className="uv-modal-bento-label">Reso</span>
                  <span className="uv-modal-bento-value">30 Giorni</span>
                </div>
              </div>

                <div className="uv-viewDesc-fixed">
                  Queste sneakers esclusive rappresentano il mix perfetto tra design innovativo e comfort superiore. 
                  Realizzate con materiali premium e una cura meticolosa per i dettagli, sono pensate per chi non accetta compromessi tra stile e performance.
                </div>

                <div className="uv-viewSizeSection">
                  <div className="uv-viewSizeTitle">Seleziona Taglia (EU)</div>
                  <div className="uv-sizeGrid">
                    {shoes
                      .filter(s => s.shoeName === selectedShoe.shoeName)
                      .sort((a, b) => Number(a.shoeSize) - Number(b.shoeSize))
                      .map(s => (
                        <button
                          key={s.shoeId}
                          className={`uv-sizeChip ${Number(selectedSize) === Number(s.shoeSize) ? "active" : ""}`}
                          onClick={() => setSelectedSize(s.shoeSize)}
                        >
                          {s.shoeSize}
                        </button>
                      ))}
                  </div>
                </div>
              </div>

              <div className="uv-viewFooter-fixed">
                <button className="uv-btn uv-btnPrimary uv-viewAddBtn" onClick={handleAddToCartFromModal}>
                  Aggiungi al carrello — €{Number(selectedShoe.shoePrice ?? 0).toFixed(2)}
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      <Footer />
    </div>
  );
}

export default UserStore;
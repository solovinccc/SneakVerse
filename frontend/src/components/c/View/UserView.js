import { useEffect, useMemo, useState } from "react";
import "./Css/UserView.css";

function UserView() {
  const [userData, setUserData] = useState(null);
  const [shoes, setShoes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [filtersOpen, setFiltersOpen] = useState(true);
  const [search, setSearch] = useState("");
  const [brandSearch, setBrandSearch] = useState("");
  const [size, setSize] = useState("ALL");
  const [priceMin, setPriceMin] = useState("");
  const [priceMax, setPriceMax] = useState("");
  const [sort, setSort] = useState("featured");
  const [addedMap, setAddedMap] = useState({});

  const [avgPrice, setAvgPrice] = useState("");
  const [avgLoading, setAvgLoading] = useState("");

  const [cartOpen, setCartOpen] = useState(false);
  const [cart, setCart] = useState(() => {
    try {
      const raw = localStorage.getItem("cart");
      return raw ? JSON.parse(raw) : [];
    } catch {
      return [];
    }
  });

  const [ordersOpen, setOrdersOpen] = useState(false);
  const [orders, setOrders] = useState([]);
  const [ordersLoading, setOrdersLoading] = useState(false);

  const [payOpen, setPayOpen] = useState(false);
  const [paymentMethod, setPaymentMethod] = useState("CARD");
  const [checkoutLoading, setCheckoutLoading] = useState(false);

  const [cardNumber, setCardNumber] = useState("");
  const [cardName, setCardName] = useState("");
  const [cardExp, setCardExp] = useState("");
  const [cardCvv, setCardCvv] = useState("");
  const [paypalEmail, setPaypalEmail] = useState("");

  const token = localStorage.getItem("token");
  const userIdRaw = localStorage.getItem("userId");
  const userId = userIdRaw ? Number(userIdRaw) : null;

  const onlyDigits = (s) => String(s || "").replace(/\D/g, "");
  const formatCardNumber = (raw) => {
    const d = onlyDigits(raw).slice(0, 16);
    return d.replace(/(.{4})/g, "$1 ").trim();
  };
  const formatExp = (raw) => {
    const d = onlyDigits(raw).slice(0, 4);
    if (d.length <= 2) return d;
    return `${d.slice(0, 2)}/${d.slice(2)}`;
  };
  const isEmail = (v) =>
    /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(String(v || "").trim());

  const paymentValid = useMemo(() => {
    if (paymentMethod === "CARD") {
      const n = onlyDigits(cardNumber);
      const exp = String(cardExp || "").trim();
      const cvv = onlyDigits(cardCvv);
      const name = String(cardName || "").trim();
      return (
        n.length === 16 &&
        exp.length === 5 &&
        cvv.length >= 3 &&
        cvv.length <= 4 &&
        name.length >= 3
      );
    }
    if (paymentMethod === "PAYPAL") return isEmail(paypalEmail);
    if (paymentMethod === "CASH_ON_DELIVERY") return true;
    return false;
  }, [paymentMethod, cardNumber, cardExp, cardCvv, cardName, paypalEmail]);

  useEffect(() => {
    setCardNumber("");
    setCardName("");
    setCardExp("");
    setCardCvv("");
    setPaypalEmail("");
  }, [paymentMethod]);

  const fetchAllShoes = async () => {
    const shoesRes = await fetch(`http://localhost:8080/api/shoes`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    if (!shoesRes.ok) throw new Error("Errore caricamento catalogo");
    return shoesRes.json();
  };

  const fetchAvgPrice = async () => {
    setAvgLoading(true);
    try {
      const res = await fetch("http://localhost:8080/api/shoes/avg-price", {
        headers: { Authorization: `Bearer ${token}` },
      });
      if (!res.ok) throw new Error("Errore caricamento prezzo medio");
      const data = await res.json().catch(() => null);

      const value =
        typeof data === "number"
          ? data
          : data && typeof data.averagePrice !== "undefined"
            ? Number(data.averagePrice)
            : Number(data);

      setAvgPrice(Number.isFinite(value) ? value : null);
    } catch (e) {
      setAvgPrice(null);
    } finally {
      setAvgLoading(false);
    }
  };

  const fetchOrders = async () => {
    if (!userId) return;
    setOrdersLoading(true);
    try {
      const res = await fetch(
        `http://localhost:8080/api/orders/user/${userId}`,
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );
      if (!res.ok) throw new Error("Errore caricamento ordini");
      const data = await res.json();
      setOrders(Array.isArray(data) ? data : []);
    } catch (e) {
      setError(e?.message || "Errore");
    } finally {
      setOrdersLoading(false);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const userRes = await fetch(
          `http://localhost:8080/api/users/${userId}`,
          {
            headers: { Authorization: `Bearer ${token}` },
          },
        );
        if (userRes.ok) setUserData(await userRes.json());
        setShoes(await fetchAllShoes());
        await fetchAvgPrice();
      } catch (err) {
        setError(err?.message || "Errore");
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [token, userId]);

  useEffect(() => {
    try {
      localStorage.setItem("cart", JSON.stringify(cart));
    } catch {}
  }, [cart]);

  useEffect(() => {
    const t = setTimeout(async () => {
      const q = brandSearch.trim();
      try {
        if (q === "") {
          setShoes(await fetchAllShoes());
          setError("");
          return;
        }
        const res = await fetch(
          `http://localhost:8080/api/shoes/by-brand-name?brand=${encodeURIComponent(q)}`,
          { headers: { Authorization: `Bearer ${token}` } },
        );
        if (!res.ok)
          throw new Error("Brand non trovato o errore ricerca brand");
        setShoes(await res.json());
        setError("");
      } catch (e) {
        setError(e?.message || "Errore");
      }
    }, 350);

    return () => clearTimeout(t);
  }, [brandSearch, token]);

  const addToCart = (shoe) => {
    setCart((prev) => {
      const existing = prev.find((i) => i.shoeId === shoe.shoeId);
      if (existing) {
        return prev.map((i) =>
          i.shoeId === shoe.shoeId ? { ...i, quantity: i.quantity + 1 } : i,
        );
      }
      return [
        ...prev,
        {
          shoeId: shoe.shoeId,
          shoeName: shoe.shoeName,
          shoePrice: Number(shoe.shoePrice ?? 0),
          shoeSize: shoe.shoeSize,
          imageUrl: shoe.imageUrl,
          quantity: 1,
        },
      ];
    });

    setAddedMap((m) => ({ ...m, [shoe.shoeId]: true }));
    setTimeout(() => setAddedMap((m) => ({ ...m, [shoe.shoeId]: false })), 650);
  };

  const removeFromCart = (shoeId) =>
    setCart((prev) => prev.filter((i) => i.shoeId !== shoeId));
  const incQty = (shoeId) =>
    setCart((prev) =>
      prev.map((i) =>
        i.shoeId === shoeId ? { ...i, quantity: i.quantity + 1 } : i,
      ),
    );
  const decQty = (shoeId) =>
    setCart((prev) =>
      prev
        .map((i) =>
          i.shoeId === shoeId ? { ...i, quantity: i.quantity - 1 } : i,
        )
        .filter((i) => i.quantity > 0),
    );
  const clearCart = () => setCart([]);

  const cartCount = useMemo(
    () => cart.reduce((sum, item) => sum + Number(item.quantity || 0), 0),
    [cart],
  );
  const cartTotal = useMemo(
    () =>
      cart.reduce(
        (sum, item) =>
          sum + (Number(item.shoePrice) || 0) * (item.quantity || 0),
        0,
      ),
    [cart],
  );

  const sizes = useMemo(() => {
    const set = new Set(
      shoes
        .map((s) => s?.shoeSize)
        .filter((v) => v !== null && v !== undefined)
        .map((v) => Number(v)),
    );
    return [
      "ALL",
      ...Array.from(set)
        .sort((a, b) => a - b)
        .map((n) => String(n)),
    ];
  }, [shoes]);

  const filteredShoes = useMemo(() => {
    const q = search.trim().toLowerCase();
    const min = priceMin === "" ? null : Number(priceMin);
    const max = priceMax === "" ? null : Number(priceMax);

    let list = shoes.filter((s) => {
      const nameOk =
        q === "" ||
        String(s.shoeName || "")
          .toLowerCase()
          .includes(q);
      const sizeOk = size === "ALL" || Number(s.shoeSize) === Number(size);
      const price = Number(s.shoePrice ?? 0);
      const minOk = min === null || (!Number.isNaN(min) && price >= min);
      const maxOk = max === null || (!Number.isNaN(max) && price <= max);
      return nameOk && sizeOk && minOk && maxOk;
    });

    switch (sort) {
      case "price_asc":
        list = list
          .slice()
          .sort((a, b) => Number(a.shoePrice ?? 0) - Number(b.shoePrice ?? 0));
        break;
      case "price_desc":
        list = list
          .slice()
          .sort((a, b) => Number(b.shoePrice ?? 0) - Number(a.shoePrice ?? 0));
        break;
      case "name_asc":
        list = list
          .slice()
          .sort((a, b) =>
            String(a.shoeName ?? "").localeCompare(String(b.shoeName ?? "")),
          );
        break;
      case "name_desc":
        list = list
          .slice()
          .sort((a, b) =>
            String(b.shoeName ?? "").localeCompare(String(a.shoeName ?? "")),
          );
        break;
      default:
        break;
    }

    return list;
  }, [shoes, search, size, priceMin, priceMax, sort]);

  const resultsCount = filteredShoes.length;

  const openOrders = async () => {
    setOrdersOpen(true);
    await fetchOrders();
  };

  const proceedToCheckout = () => {
    if (!cart.length) return;
    setPayOpen(true);
  };

  const confirmCheckout = async () => {
    if (!userId) {
      setError("userId non valido");
      return;
    }
    if (!cart.length) return;
    if (!paymentValid) return;

    setCheckoutLoading(true);
    setError("");

    try {
      const body = {
        userId,
        paymentMethod,
        items: cart.map((c) => ({ shoeId: c.shoeId, quantity: c.quantity })),
      };

      const res = await fetch(`http://localhost:8080/api/orders/checkout`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(body),
      });

      if (!res.ok) {
        const txt = await res.text().catch(() => "");
        throw new Error(txt || "Errore checkout");
      }

      clearCart();
      setPayOpen(false);
      setCartOpen(false);

      setOrdersOpen(true);
      await fetchOrders();
    } catch (e) {
      setError(e?.message || "Errore");
    } finally {
      setCheckoutLoading(false);
    }
  };

  const closeAllOverlays = () => {
    setCartOpen(false);
    setOrdersOpen(false);
    setPayOpen(false);
  };

  const anyOverlayOpen = cartOpen || ordersOpen || payOpen;

  if (loading) {
    return (
      <div className="uv-page">
        <div className="uv-loading">Caricamento…</div>
      </div>
    );
  }

  return (
    <div className="uv-page">
      {anyOverlayOpen && (
        <div
          className="uv-cartOverlay"
          role="button"
          tabIndex={0}
          onClick={closeAllOverlays}
          onKeyDown={(e) => {
            if (e.key === "Escape") closeAllOverlays();
          }}
        />
      )}

      <aside
        className={`uv-cartDrawer ${cartOpen ? "open" : ""}`}
        aria-hidden={!cartOpen}
      >
        <div className="uv-cartHeader">
          <div className="uv-cartTitle">Carrello</div>
          <button
            className="uv-cartClose"
            onClick={() => setCartOpen(false)}
            aria-label="Chiudi carrello"
          >
            ✕
          </button>
        </div>

        {cart.length === 0 ? (
          <div className="uv-cartEmpty">Il carrello è vuoto.</div>
        ) : (
          <>
            <div className="uv-cartList">
              {cart.map((item) => (
                <div className="uv-cartRow" key={item.shoeId}>
                  <div className="uv-cartRowLeft">
                    <div className="uv-cartName">{item.shoeName}</div>
                    <div className="uv-cartMeta">
                      Taglia {item.shoeSize} · €
                      {Number(item.shoePrice ?? 0).toFixed(2)}
                    </div>

                    <div className="uv-cartQty">
                      <button
                        className="uv-qtyBtn"
                        onClick={() => decQty(item.shoeId)}
                        aria-label="Diminuisci quantità"
                      >
                        −
                      </button>
                      <div className="uv-qtyNum">{item.quantity}</div>
                      <button
                        className="uv-qtyBtn"
                        onClick={() => incQty(item.shoeId)}
                        aria-label="Aumenta quantità"
                      >
                        +
                      </button>

                      <button
                        className="uv-removeBtn"
                        onClick={() => removeFromCart(item.shoeId)}
                      >
                        Rimuovi
                      </button>
                    </div>
                  </div>

                  <div className="uv-cartRowRight">
                    €
                    {(
                      Number(item.shoePrice ?? 0) * Number(item.quantity ?? 0)
                    ).toFixed(2)}
                  </div>
                </div>
              ))}
            </div>

            <div className="uv-cartFooter">
              <div className="uv-cartTotalRow">
                <span>Totale</span>
                <strong>€{cartTotal.toFixed(2)}</strong>
              </div>

              <button
                className="uv-btn uv-btnPrimary"
                onClick={proceedToCheckout}
                disabled={cart.length === 0}
              >
                Procedi all’acquisto
              </button>

              <button className="uv-btnSecondary" onClick={clearCart}>
                Svuota carrello
              </button>
            </div>
          </>
        )}
      </aside>

      <aside
        className={`uv-ordersDrawer ${ordersOpen ? "open" : ""}`}
        aria-hidden={!ordersOpen}
      >
        <div className="uv-cartHeader">
          <div className="uv-cartTitle">Ordini (ultime 12 ore)</div>
          <button
            className="uv-cartClose"
            onClick={() => setOrdersOpen(false)}
            aria-label="Chiudi ordini"
          >
            ✕
          </button>
        </div>

        {ordersLoading ? (
          <div className="uv-cartEmpty">Caricamento ordini…</div>
        ) : orders.length === 0 ? (
          <div className="uv-cartEmpty">Nessun ordine nelle ultime 12 ore.</div>
        ) : (
          <div className="uv-ordersList">
            {orders.map((o) => (
              <div className="uv-orderCard" key={o.orderId}>
                <div className="uv-orderTop">
                  <div className="uv-orderId">Ordine #{o.orderId}</div>
                  <div className="uv-orderTotal">
                    €{Number(o.total ?? 0).toFixed(2)}
                  </div>
                </div>

                <div className="uv-orderMeta">
                  <div className="uv-orderMetaRow">
                    <span>Pagamento</span>
                    <strong>{o.paymentMethod}</strong>
                  </div>
                  <div className="uv-orderMetaRow">
                    <span>Corriere</span>
                    <strong>
                      {o.courierPhoneNumber
                        ? `📞 ${o.courierPhoneNumber}`
                        : "Non disponibile"}
                    </strong>
                  </div>
                </div>

                <div className="uv-orderItems">
                  {(o.items || []).map((it) => (
                    <div
                      className="uv-orderItemRow"
                      key={`${o.orderId}-${it.shoeId}`}
                    >
                      <div className="uv-orderItemLeft">
                        <div className="uv-orderItemName">{it.shoeName}</div>
                        <div className="uv-orderItemSub">
                          Taglia {it.shoeSize} · x{it.quantity}
                        </div>
                      </div>
                      <div className="uv-orderItemRight">
                        €
                        {(
                          Number(it.shoePrice ?? 0) * Number(it.quantity ?? 0)
                        ).toFixed(2)}
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            ))}
          </div>
        )}
      </aside>

      {payOpen && (
        <div
          className="uv-payModal"
          role="dialog"
          aria-modal="true"
          aria-label="Pagamento"
        >
          <div className="uv-payCard">
            <div className="uv-payTitle">Metodo di pagamento</div>
            <div className="uv-paySubtitle">
              Inserisci i dati (non verranno salvati).
            </div>

            <div className="uv-payGroup">
              <label className="uv-payOption">
                <input
                  type="radio"
                  name="pay"
                  value="CARD"
                  checked={paymentMethod === "CARD"}
                  onChange={(e) => setPaymentMethod(e.target.value)}
                />
                <span>Carta</span>
              </label>

              <label className="uv-payOption">
                <input
                  type="radio"
                  name="pay"
                  value="PAYPAL"
                  checked={paymentMethod === "PAYPAL"}
                  onChange={(e) => setPaymentMethod(e.target.value)}
                />
                <span>PayPal</span>
              </label>

              <label className="uv-payOption">
                <input
                  type="radio"
                  name="pay"
                  value="CASH_ON_DELIVERY"
                  checked={paymentMethod === "CASH_ON_DELIVERY"}
                  onChange={(e) => setPaymentMethod(e.target.value)}
                />
                <span>Contrassegno</span>
              </label>
            </div>

            {paymentMethod === "CARD" && (
              <div className="uv-payDetails">
                <div className="uv-payRow">
                  <div className="uv-payField">
                    <label className="uv-payLabel">Numero carta</label>
                    <input
                      className="uv-payInput"
                      inputMode="numeric"
                      placeholder="1234 5678 9012 3456"
                      value={cardNumber}
                      onChange={(e) =>
                        setCardNumber(formatCardNumber(e.target.value))
                      }
                    />
                  </div>
                </div>

                <div className="uv-payRow">
                  <div className="uv-payField">
                    <label className="uv-payLabel">Intestatario</label>
                    <input
                      className="uv-payInput"
                      placeholder="Nome e Cognome"
                      value={cardName}
                      onChange={(e) => setCardName(e.target.value)}
                    />
                  </div>
                </div>

                <div className="uv-payRow two">
                  <div className="uv-payField">
                    <label className="uv-payLabel">Scadenza</label>
                    <input
                      className="uv-payInput"
                      inputMode="numeric"
                      placeholder="MM/AA"
                      value={cardExp}
                      onChange={(e) => setCardExp(formatExp(e.target.value))}
                    />
                  </div>
                  <div className="uv-payField">
                    <label className="uv-payLabel">CVV</label>
                    <input
                      className="uv-payInput"
                      inputMode="numeric"
                      placeholder="123"
                      value={cardCvv}
                      onChange={(e) =>
                        setCardCvv(onlyDigits(e.target.value).slice(0, 4))
                      }
                    />
                  </div>
                </div>
              </div>
            )}

            {paymentMethod === "PAYPAL" && (
              <div className="uv-payDetails">
                <div className="uv-payRow">
                  <div className="uv-payField">
                    <label className="uv-payLabel">Email PayPal</label>
                    <input
                      className="uv-payInput"
                      inputMode="email"
                      placeholder="nome@paypal.com"
                      value={paypalEmail}
                      onChange={(e) => setPaypalEmail(e.target.value)}
                    />
                  </div>
                </div>
              </div>
            )}

            {paymentMethod === "CASH_ON_DELIVERY" && (
              <div className="uv-payDetails">
                <div className="uv-payNote">
                  Pagherai alla consegna. Potrebbe essere richiesto un piccolo
                  sovrapprezzo dal corriere.
                </div>
              </div>
            )}

            <div className="uv-payTotal">
              <span>Totale</span>
              <strong>€{cartTotal.toFixed(2)}</strong>
            </div>

            {!paymentValid && (
              <div className="uv-payError">
                Controlla i dati di pagamento prima di confermare.
              </div>
            )}

            <div className="uv-payActions">
              <button
                className="uv-btnSecondary"
                onClick={() => setPayOpen(false)}
                disabled={checkoutLoading}
              >
                Indietro
              </button>
              <button
                className="uv-btn uv-btnPrimary"
                onClick={confirmCheckout}
                disabled={checkoutLoading || !paymentValid}
              >
                {checkoutLoading ? "Ordine in corso…" : "Conferma e ordina"}
              </button>
            </div>
          </div>
        </div>
      )}

      <header className="uv-topbar">
        <div className="uv-topbar-left">
          <div className="uv-title">
            Sneakers e scarpe{" "}
            {userData?.username ? `· ${userData.username}` : ""}
          </div>
          <div className="uv-subtitle">{resultsCount} risultati</div>
        </div>

        <div className="uv-heroBrand">SneakVerse</div>

        <div className="uv-topbar-right">
          <div className="uv-avgPill" title="Prezzo medio attuale del catalogo">
            <div className="uv-avgLabel">Prezzo medio</div>
            <div className="uv-avgValue">
              {avgLoading
                ? "…"
                : avgPrice === null
                  ? "—"
                  : `€${avgPrice.toFixed(2)}`}
            </div>
          </div>

          <button
            className="uv-cartPill"
            onClick={() => setCartOpen(true)}
            title="Apri carrello"
          >
            Carrello <span className="uv-cartBadge">{cartCount}</span>
          </button>

          <button
            className="uv-ordersPill"
            onClick={openOrders}
            title="Apri ordini"
          >
            Ordini <span className="uv-ordersBadge">{orders.length}</span>
          </button>

          <div className="uv-selectwrap">
            <label className="uv-label">Ordina per</label>
            <select
              className="uv-select"
              value={sort}
              onChange={(e) => setSort(e.target.value)}
            >
              <option value="featured">In evidenza</option>
              <option value="price_asc">Prezzo: crescente</option>
              <option value="price_desc">Prezzo: decrescente</option>
              <option value="name_asc">Nome: A-Z</option>
              <option value="name_desc">Nome: Z-A</option>
            </select>
          </div>

          <button
            className="uv-logout"
            onClick={() => {
              localStorage.clear();
              window.location.reload();
            }}
            title="Logout"
          >
            Logout
          </button>
        </div>
      </header>

      <div className="uv-content">
        {filtersOpen && (
          <aside className="uv-filters">
            <div className="uv-filterBlock">
              <div className="uv-filterTitle">Ricerca modello</div>
              <input
                className="uv-input"
                placeholder="Cerca modello…"
                value={search}
                onChange={(e) => setSearch(e.target.value)}
              />
            </div>

            <div className="uv-filterBlock">
              <div className="uv-filterTitle">Ricerca brand</div>
              <input
                className="uv-input"
                placeholder="Es. Nike, Adidas…"
                value={brandSearch}
                onChange={(e) => setBrandSearch(e.target.value)}
              />
              <div className="uv-muted" style={{ marginTop: 8 }}>
                Tip: scrivi il nome esatto del brand.
              </div>
            </div>

            <div className="uv-filterBlock">
              <div className="uv-filterTitle">Taglia</div>
              <select
                className="uv-select full"
                value={size}
                onChange={(e) => setSize(e.target.value)}
              >
                {sizes.map((s) => (
                  <option key={s} value={s}>
                    {s === "ALL" ? "Tutte" : s}
                  </option>
                ))}
              </select>
            </div>

            <div className="uv-filterBlock">
              <div className="uv-filterTitle">Prezzo</div>
              <div className="uv-row">
                <input
                  className="uv-input"
                  inputMode="decimal"
                  placeholder="Min"
                  value={priceMin}
                  onChange={(e) => setPriceMin(e.target.value)}
                />
                <input
                  className="uv-input"
                  inputMode="decimal"
                  placeholder="Max"
                  value={priceMax}
                  onChange={(e) => setPriceMax(e.target.value)}
                />
              </div>
            </div>

            <div className="uv-filterActions">
              <button
                className="uv-btnSecondary"
                onClick={async () => {
                  setSearch("");
                  setBrandSearch("");
                  setSize("ALL");
                  setPriceMin("");
                  setPriceMax("");
                  setSort("featured");
                  try {
                    setShoes(await fetchAllShoes());
                    setError("");
                  } catch (e) {
                    setError(e?.message || "Errore");
                  }
                }}
              >
                Reset filtri
              </button>
            </div>

            {error && <div className="uv-error">{error}</div>}
          </aside>
        )}

        <main className="uv-gridArea">
          <div className={`uv-grid ${filtersOpen ? "" : "full"}`}>
            {filteredShoes.map((shoe) => (
              <article className="uv-card" key={shoe.shoeId}>
                <div className="uv-imgWrap">
                  {shoe.imageUrl ? (
                    <img
                      className="uv-img"
                      src={require(`./Img/${shoe.imageUrl}`)}
                      alt={shoe.shoeName}
                      onError={(e) => {
                        e.currentTarget.src =
                          "https://via.placeholder.com/600x400?text=Image";
                      }}
                    />
                  ) : (
                    <div className="uv-imgFallback">👟</div>
                  )}
                </div>

                <div className="uv-meta">
                  <div className="uv-name">{shoe.shoeName}</div>
                  <div className="uv-muted">Taglia {shoe.shoeSize}</div>
                  <div className="uv-price">
                    €{Number(shoe.shoePrice ?? 0).toFixed(2)}
                  </div>
                </div>

                <button
                  className={`uv-btn ${addedMap[shoe.shoeId] ? "added" : ""}`}
                  onClick={() => addToCart(shoe)}
                >
                  {addedMap[shoe.shoeId]
                    ? "Aggiunto ✓"
                    : "Aggiungi al carrello"}
                </button>
              </article>
            ))}
          </div>
        </main>
      </div>
    </div>
  );
}

export default UserView;

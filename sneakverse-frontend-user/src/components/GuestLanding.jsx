import { useEffect, useState, useMemo } from "react";
import axios from "axios";
import "./Css/styles.css";
import "./Css/premium.css";
import LoginModal from "./LoginModal";
import { useCart } from "../context/CartContext";
import Footer from "./Footer";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

function GuestLanding({ onLoginSuccess }) {
  const [allShoes, setAllShoes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showLogin, setShowLogin] = useState(false);
  const { cartCount, addToCart } = useCart();

  const [selectedShoe, setSelectedShoe] = useState(null);
  const [selectedSize, setSelectedSize] = useState("");
  const [viewModalOpen, setViewModalOpen] = useState(false);

  const [featuredShoes, setFeaturedShoes] = useState([]);

  useEffect(() => {
    if (allShoes.length > 0) {
      const pickRandom = () => {
        const shuffled = [...allShoes].sort(() => 0.5 - Math.random());
        setFeaturedShoes(shuffled.slice(0, 3));
      };

      pickRandom();
      const interval = setInterval(pickRandom, 2000);
      return () => clearInterval(interval);
    }
  }, [allShoes]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await axios.get(`${API_BASE_URL}/shoes`);
        setAllShoes(res.data);
      } catch (err) {
        console.error("Errore caricamento scarpe:", err);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  const openViewModal = (shoe) => {
    setSelectedShoe(shoe);
    setSelectedSize(shoe.shoeSize);
    setViewModalOpen(true);
  };

  const handleAddToCartFromModal = () => {
    if (!selectedShoe || !selectedSize) return;
    const finalShoe = allShoes.find(s => s.shoeName === selectedShoe.shoeName && Number(s.shoeSize) === Number(selectedSize));
    if (finalShoe) {
      addToCart({
        id: finalShoe.shoeId,
        name: finalShoe.shoeName,
        price: Number(finalShoe.shoePrice ?? 0),
        size: finalShoe.shoeSize,
        image: finalShoe.imageUrl
      });
      setViewModalOpen(false);
    }
  };

  if (loading) return (
    <div className="uv-loading">
      <div className="uv-loading-spinner"></div>
      Caricamento...
    </div>
  );

  return (
    <div className="uv-page">
      {showLogin && (
        <LoginModal
          onClose={() => setShowLogin(false)}
          onLoginSuccess={(token, userId) => {
            setShowLogin(false);
            onLoginSuccess(token, userId);
          }}
        />
      )}

      {(viewModalOpen) && (
        <div className="uv-cartOverlay" onClick={() => setViewModalOpen(false)} />
      )}

      <header className="uv-topbar" style={{ background: '#000', borderBottom: 'none' }}>
        <div className="uv-topbar-left">
          <div className="uv-title" style={{ color: '#fff' }}>SneakVerse</div>
        </div>
        <div style={{ display: 'flex', alignItems: 'center', gap: '20px' }}>
        </div>
      </header>

      <section className="uv-guestHero-video-container">
        <div className="uv-hero-video-side">
          <video autoPlay loop muted playsInline>
            <source src="https://videos.pexels.com/video-files/8533918/8533918-uhd_2560_1440_25fps.mp4" type="video/mp4" />
          </video>
        </div>

        <div className="uv-hero-center-box">
          <span className="uv-hero-tagline" style={{ color: '#fff', opacity: 0.6 }}>SneakVerse Elite</span>
          <h1 className="uv-hero-main-title" style={{ fontSize: '3.5rem', color: '#fff' }}>
            Step into the <br /> <span style={{ WebkitTextStroke: '1px #fff', WebkitTextFillColor: 'transparent' }}>SneakVerse</span>
          </h1>
          <button className="uv-btn uv-btnPrimary" onClick={() => setShowLogin(true)} style={{ marginTop: '30px', padding: '16px 40px' }}>
            Esplora Ora
          </button>
        </div>

        <div className="uv-hero-video-side">
          <video autoPlay loop muted playsInline>
            <source src="https://videos.pexels.com/video-files/8402432/8402432-uhd_2560_1440_25fps.mp4" type="video/mp4" />
          </video>
        </div>
      </section>

      <div className="uv-brand-bar">
        <div className="uv-brand-logo">NIKE</div>
        <div className="uv-brand-logo">JORDAN</div>
        <div className="uv-brand-logo">ADIDAS</div>
        <div className="uv-brand-logo">YEEZY</div>
        <div className="uv-brand-logo">OFF-WHITE</div>
        <div className="uv-brand-logo">NEW BALANCE</div>
      </div>

      {featuredShoes.length > 0 && (
        <section className="uv-featured-section">
          <div className="uv-section-header">
            <h2 className="uv-section-title">In Evidenza</h2>
            <p className="uv-section-subtitle">Scelte casuali dalla nostra collezione.</p>
          </div>
          <div className="uv-featured-grid">
            {featuredShoes.map((shoe) => (
              <article className="uv-featured-card" key={shoe.shoeId} onClick={() => openViewModal(shoe)}>
                <div className="uv-featured-imgWrap">
                  <img src={shoe.imageUrl ? `/images/${shoe.imageUrl}` : "https://via.placeholder.com/600x400?text=Sneaker"} alt={shoe.shoeName} />
                </div>
                <div className="uv-featured-info">
                  <span className="uv-featured-tag">Special Pick</span>
                  <h3 className="uv-featured-name">{shoe.shoeName}</h3>
                  <div className="uv-featured-price">€{Number(shoe.shoePrice ?? 0).toFixed(2)}</div>
                </div>
              </article>
            ))}
          </div>
        </section>
      )}

      <section className="uv-carousel-section">
        <div className="uv-section-header">
          <h2 className="uv-section-title">Ultimi Arrivi</h2>
          <button className="uv-btn uv-btnSecondary" style={{ border: 'none', fontWeight: 700 }} onClick={() => setShowLogin(true)}>Vedi Tutti →</button>
        </div>
        <div className="uv-carousel-container">
          {allShoes.slice(0, 8).map((shoe) => (
            <div className="uv-carousel-item" key={shoe.shoeId}>
              <article className="uv-card" onClick={() => openViewModal(shoe)} style={{ cursor: 'pointer' }}>
                <div className="uv-imgWrap">
                  <img className="uv-img" src={shoe.imageUrl ? `/images/${shoe.imageUrl}` : "https://via.placeholder.com/600x400?text=Sneaker"} alt={shoe.shoeName} />
                </div>
                <div className="uv-meta">
                  <div className="uv-verified-badge">Verified Authentic</div>
                  <div className="uv-name">{shoe.shoeName}</div>
                  <div className="uv-price">€{Number(shoe.shoePrice ?? 0).toFixed(2)}</div>
                </div>
              </article>
            </div>
          ))}
        </div>
      </section>

      <section className="uv-culture-bento">
        <h2 className="uv-culture-bento-title">Join The Culture</h2>
        <div className="uv-bento-grid">
          <div className="uv-bento-item sport" onClick={() => setShowLogin(true)}>
            <img src="https://images.pexels.com/photos/14337851/pexels-photo-14337851.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1" alt="Sport" />
            <div className="uv-bento-overlay">
              <span className="uv-bento-tag">Performance</span>
              <h3 className="uv-bento-title">Beyond the Court</h3>
            </div>
          </div>
          <div className="uv-bento-item" onClick={() => setShowLogin(true)}>
            <img src="https://images.pexels.com/photos/10484483/pexels-photo-10484483.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1" alt="Street" />
            <div className="uv-bento-overlay">
              <span className="uv-bento-tag">Urban Style</span>
              <h3 className="uv-bento-title">Street Essential</h3>
            </div>
          </div>
          <div className="uv-bento-item" onClick={() => setShowLogin(true)}>
            <img src="https://images.pexels.com/photos/18155790/pexels-photo-18155790.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1" alt="Elegant" />
            <div className="uv-bento-overlay">
              <span className="uv-bento-tag">Premium</span>
              <h3 className="uv-bento-title">High Fashion</h3>
            </div>
          </div>
        </div>
      </section>

      <section className="uv-membership-section" style={{ padding: '120px 48px', textAlign: 'center', background: '#fff' }}>
        <h2 className="uv-section-title">I VANTAGGI DI ESSERE UN MEMBRO</h2>
        <div className="uv-member-grid" style={{ display: 'grid', gridTemplateColumns: 'repeat(3, 1fr)', gap: '60px', maxWidth: '1200px', margin: '60px auto 0' }}>
          <div className="uv-member-card" style={{ textAlign: 'left' }}>
            <span className="uv-member-number" style={{ fontSize: '0.75rem', fontWeight: 900, color: '#ff4b2b', display: 'block', marginBottom: '12px' }}>01</span>
            <h3 style={{ fontSize: '1.5rem', fontWeight: 900, textTransform: 'uppercase', marginBottom: '16px' }}>Accesso Esclusivo</h3>
            <p style={{ fontSize: '0.95rem', color: '#444', lineHeight: 1.6 }}>Ottieni la priorità assoluta sui lanci più attesi e sulle collaborazioni in edizione limitata.</p>
          </div>
          <div className="uv-member-card" style={{ textAlign: 'left' }}>
            <span className="uv-member-number" style={{ fontSize: '0.75rem', fontWeight: 900, color: '#ff4b2b', display: 'block', marginBottom: '12px' }}>02</span>
            <h3 style={{ fontSize: '1.5rem', fontWeight: 900, textTransform: 'uppercase', marginBottom: '16px' }}>Spedizione Member</h3>
            <p style={{ fontSize: '0.95rem', color: '#444', lineHeight: 1.6 }}>Goditi la spedizione gratuita su ogni ordine e resi facilitati per 30 giorni.</p>
          </div>
          <div className="uv-member-card" style={{ textAlign: 'left' }}>
            <span className="uv-member-number" style={{ fontSize: '0.75rem', fontWeight: 900, color: '#ff4b2b', display: 'block', marginBottom: '12px' }}>03</span>
            <h3 style={{ fontSize: '1.5rem', fontWeight: 900, textTransform: 'uppercase', marginBottom: '16px' }}>Premi e Offerte</h3>
            <p style={{ fontSize: '0.95rem', color: '#444', lineHeight: 1.6 }}>Accumula punti con ogni acquisto e sblocca sconti riservati ed eventi speciali.</p>
          </div>
        </div>
        <button className="uv-btn uv-btnPrimary" style={{ marginTop: '60px', padding: '16px 60px' }} onClick={() => setShowLogin(true)}>
          Unisciti a noi
        </button>
      </section>

      {viewModalOpen && selectedShoe && (
        <div className="uv-viewModal">
          <div className="uv-viewCard">
            <button className="uv-viewClose" onClick={() => setViewModalOpen(false)}>✕</button>
            <div className="uv-viewLeft">
              <img className="uv-viewImg" src={selectedShoe.imageUrl ? `/images/${selectedShoe.imageUrl}` : "https://via.placeholder.com/600x400?text=No+Image"} alt={selectedShoe.shoeName} />
            </div>
            <div className="uv-viewRight">
              <div className="uv-verified-badge">Verified Authentic</div>
              <div className="uv-viewName">{selectedShoe.shoeName}</div>
              <div className="uv-viewPrice">€{Number(selectedShoe.shoePrice ?? 0).toFixed(2)}</div>
              <div className="uv-viewDesc">Queste sneakers esclusive rappresentano il mix perfetto tra design innovativo e comfort superiore.</div>
              <div className="uv-viewSizeSection">
                <div className="uv-viewSizeTitle">Seleziona Taglia</div>
                <div className="uv-sizeGrid">
                  {allShoes
                    .filter(s => s.shoeName === selectedShoe.shoeName)
                    .sort((a, b) => Number(a.shoeSize) - Number(b.shoeSize))
                    .map(s => (
                      <button key={s.shoeId} className={`uv-sizeChip ${Number(selectedSize) === Number(s.shoeSize) ? "active" : ""}`} onClick={() => setSelectedSize(s.shoeSize)}>
                        {s.shoeSize}
                      </button>
                    ))}
                </div>
              </div>
              <button className="uv-btn uv-btnPrimary uv-viewAddBtn" onClick={handleAddToCartFromModal}>Aggiungi al carrello</button>
            </div>
          </div>
        </div>
      )}

      <Footer />
    </div>
  );
}

export default GuestLanding;
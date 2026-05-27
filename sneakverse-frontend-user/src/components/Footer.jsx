import React from 'react';

function Footer() {
  return (
    <footer className="uv-footer">
      <div className="uv-footer-grid">
        <div>
          <div className="uv-footer-brand">SneakVerse</div>
          <p className="uv-footer-desc">
            La tua destinazione per le sneakers più esclusive. 
            Autenticità garantita, spedizione veloce e resi gratuiti.
          </p>
        </div>
        <div>
          <h4>Shop</h4>
          <ul>
            <li>Nuovi Arrivi</li>
            <li>Più Venduti</li>
            <li>Edizioni Limitate</li>
            <li>Saldi</li>
          </ul>
        </div>
        <div>
          <h4>Aiuto</h4>
          <ul>
            <li>Spedizioni</li>
            <li>Resi e Rimborsi</li>
            <li>FAQ</li>
            <li>Contattaci</li>
          </ul>
        </div>
        <div>
          <h4>Azienda</h4>
          <ul>
            <li>Chi Siamo</li>
            <li>Privacy Policy</li>
            <li>Termini di Servizio</li>
            <li>Lavora con noi</li>
          </ul>
        </div>
      </div>
      <div className="uv-footer-bottom">
        <span>© 2026 SneakVerse. Tutti i diritti riservati.</span>
      </div>
    </footer>
  );
}

export default Footer;

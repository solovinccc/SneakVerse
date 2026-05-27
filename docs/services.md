#  Analisi dei Microservizi

In questa sezione esploriamo nel dettaglio le responsabilità e le caratteristiche tecniche di ogni microservizio del progetto SneakVerse.

---

##  API Gateway (`sneakverse-gateway`)
Il gateway è il punto di ingresso per tutte le chiamate esterne.
- **Tecnologia**: Spring Cloud Gateway.
- **Porta**: 8000.
- **Responsabilità**:
    - Routing dinamico verso i servizi backend.
    - Rate limiting.
    - Aggregazione dei log.
    - CORS configuration centralizzata.

##  Auth Service (`sneakverse-auth`)
Gestisce l'identità degli utenti e la sicurezza.
- **Tecnologia**: Spring Boot + Spring Security + Redis.
- **Porta**: 8083.
- **Responsabilità**:
    - Login e generazione JWT.
    - Validazione dei token.
    - Logout tramite blacklist su Redis.
    - Integrazione con il database utenti.

##  Products Service (`sneakverse-products`)
Il catalogo digitale delle sneakers.
- **Tecnologia**: Spring Boot.
- **Porta**: 8084.
- **Responsabilità**:
    - Gestione dell'inventario (CRUD prodotti).
    - Gestione varianti (taglie, colori).
    - Ricerca avanzata.

##  Orders Service (`sneakverse-orders-service`)
Gestisce il ciclo di vita degli acquisti.
- **Tecnologia**: Spring Boot + Kafka.
- **Porta**: 8082.
- **Responsabilità**:
    - Creazione e tracciamento ordini.
    - Integrazione con i pagamenti.
    - Comunicazione asincrona via Kafka per notificare altri servizi (es. magazzino) della vendita.

##  Monolith (`sneakverse-backend`)
Il cuore storico del sistema che gestisce le logiche legacy e l'orchestrazione iniziale.
- **Tecnologia**: Spring Boot.
- **Porta**: 8081.
- **Responsabilità**:
    - Integrazione con Liferay.
    - Logiche di business cross-dominio.
    - Gestione utenti admin.

##  Frontend User (`sneakverse-frontend-user`)
L'interfaccia cliente finale.
- **Tecnologia**: React + Vanilla CSS.
- **Porta**: 3001.
- **Responsabilità**:
    - Navigazione catalogo.
    - Gestione carrello (persistente).
    - Visualizzazione storico ordini.

## ⚙️ Frontend Admin (`sneakverse-frontend-admin`)
Dashboard per la gestione operativa, integrata come modulo aggiuntivo (portlet) all'interno di Liferay Portal.
- **Tecnologia**: React (v16.8) + Liferay NPM Bundler (JS Portlet).
- **Responsabilità**:
    - Widget inserito ed esposto sulle pagine admin di Liferay.
    - Dashboard di monitoraggio e statistiche di vendita.
    - Gestione manuale e operativa di ordini, prodotti e clienti.
- **Avvio/Deployment**: Compilato e deployato direttamente nella directory `deploy` di Liferay tramite npm (`npm run deploy`).

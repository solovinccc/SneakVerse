#  SneakVerse

Benvenuti in **SneakVerse**, la piattaforma e-commerce di nuova generazione dedicata agli amanti delle sneakers. Il progetto è costruito su un'architettura a microservizi moderna, scalabile e resiliente.

##  Panoramica del Progetto

SneakVerse non è solo un negozio online, ma un ecosistema completo che integra:
- **Storefront per l'utente**: Un'interfaccia premium ispirata ai leader del settore (Nike, Adidas).
- **Pannello Admin**: Gestione completa di prodotti, ordini e utenti.
- **Microservizi Scalabili**: Servizi indipendenti per autenticazione, ordini e catalogo prodotti.
- **Infrastruttura Enterprise**: Integrazione con Liferay Portal per la gestione avanzata dei contenuti.

##  Stack Tecnologico

### Backend
- **Java & Spring Boot**: Core dei microservizi.
- **Spring Cloud Gateway**: API Gateway per il routing centralizzato.
- **Spring Security & OAuth2**: Sicurezza e gestione JWT.

### Frontend
- **React**: Per le interfacce utente dinamiche e reattive.
- **Vanilla CSS**: Design custom e premium senza dipendenze pesanti.

### Infrastruttura & Dati
- **MySQL**: Database relazionale per la persistenza dei dati.
- **Redis**: Caching e gestione delle sessioni.
- **Apache Kafka**: Broker di messaggistica per la comunicazione asincrona tra servizi.
- **Docker & Kubernetes**: Containerizzazione e orchestrazione.
- **Liferay Portal**: Gestione admin e portale.

##  Struttura del Progetto

```bash
.
├── sneakverse-auth/            # Servizio di Autenticazione (JWT, Redis)
├── sneakverse-backend/         # Monolith/Core Backend
├── sneakverse-gateway/          # API Gateway (Porta 8000)
├── sneakverse-orders-service/  # Microservizio Gestione Ordini (Kafka)
├── sneakverse-products/        # Microservizio Catalogo Prodotti
├── sneakverse-frontend-user/   # Frontend lato Utente
├── sneakverse-frontend-admin/  # Frontend lato Admin
├── sneakverse-prod/            # Configurazioni Helm per Kubernetes
├── liferay-portal/             # Integrazione e configurazione Liferay
└── docker-compose.yml          # Setup per sviluppo locale
```

##  Come Iniziare

### Prerequisiti
- Docker e Docker Compose installati.
- Java 17+ (per sviluppo locale).
- Node.js & npm (per i frontend).

### Avvio Rapido (Locale)
Per avviare l'intero ecosistema SneakVerse in locale:

1. Clona il repository.
2. Avvia i servizi infrastrutturali e i microservizi:
   ```bash
   docker-compose up -d --build
   ```
3. Il Gateway sarà disponibile su `http://localhost:8000`.
4. Il Frontend Utente sarà accessibile su `http://localhost:3001`.

##  Documentazione Dettagliata

Per approfondire ogni aspetto del progetto, consulta la documentazione dedicata:

1. [ Architettura di Sistema](docs/architecture.md)
2. [ Guida al Setup e Configurazione](docs/setup-guide.md)
3. [ Analisi dei Microservizi](docs/services.md)
4. [ Deployment (Kubernetes/Helm)](docs/deployment.md)

---

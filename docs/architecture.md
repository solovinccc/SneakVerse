# 🏗️ Architettura di Sistema

SneakVerse adotta un'architettura a **Microservizi** per garantire scalabilità orizzontale, manutenibilità e isolamento dei guasti.

## 🗺️ Diagramma Logico

Il flusso delle richieste segue questo schema:

1.  **Client (Web/Admin)**: Invia richieste HTTP.
2.  **API Gateway (Spring Cloud Gateway)**: Punto di ingresso unico. Gestisce il routing verso i servizi appropriati.
3.  **Auth Service**: Verifica i token JWT e comunica con Redis per la gestione delle sessioni.
4.  **Business Services**: (Orders, Products, Monolith) Gestiscono la logica di dominio.
5.  **Event Bus (Kafka)**: Permette la comunicazione asincrona (es. creazione ordine -> aggiornamento magazzino).
6.  **Databases**: Ogni servizio gestisce la propria persistenza (Pattern: Database per Service).

## 🛡️ Modello di Sicurezza

La sicurezza è basata su **OAuth2** e **JWT (JSON Web Tokens)**:

-   **Authentication**: Gestita dal `sneakverse-auth` service.
-   **Authorization**: I microservizi agiscono come Resource Server, validando i JWT emessi.
-   **JWKS (JSON Web Key Set)**: Il gateway e i servizi recuperano le chiavi pubbliche per la validazione dei token in modo dinamico.
-   **Redis**: Utilizzato per gestire il logout (blacklist dei token) e le sessioni attive.

## 📡 Comunicazione tra Servizi

### Sincrona (REST)
Utilizzata per operazioni che richiedono una risposta immediata.
- Il Gateway instrada le chiamate REST ai microservizi.
- Chiamate inter-service (es. Monolith -> Orders Service) avvengono via HTTP utilizzando nomi di servizio risolti internamente (K8s Service Discovery o Docker Network).

### Asincrona (Kafka)
Utilizzata per disaccoppiare i servizi e migliorare la resilienza.
- **Produzione Eventi**: `orders-service` produce eventi su topic Kafka quando un ordine viene creato.
- **Consumo Eventi**: Altri servizi (es. notifiche, magazzino) ascoltano questi topic per reagire ai cambiamenti di stato.

## 📦 Gestione dei Dati

Il sistema utilizza più istanze di MySQL (o una singola istanza con database multipli) per mantenere l'isolamento:
- `sneakverse`: Database per il modulo monolith (prodotti, utenti core).
- `sneakverse_orders`: Database specifico per la gestione ordini.
- `sneakverse_products`: Database per il nuovo catalogo prodotti.
- `lportal`: Database dedicato a Liferay.

## 🏗️ Infrastruttura

L'infrastruttura è completamente containerizzata con **Docker**:
- Ogni servizio ha il suo `Dockerfile`.
- `docker-compose.yml` orchestra l'intero stack per lo sviluppo.
- **Kubernetes** gestisce il deployment in produzione tramite i template in `sneakverse-prod`.

# ⚙️ Guida al Setup e Configurazione

Questa guida ti aiuterà a configurare l'ambiente di sviluppo locale per SneakVerse.

## 📋 Prerequisiti

Assicurati di avere i seguenti strumenti installati:
- **Docker & Docker Compose**: Per orchestrare i servizi.
- **Java JDK 17+**: Necessario per compilare i microservizi Spring Boot.
- **Node.js (v18+) & npm**: Per i progetti frontend.
- **Git**: Per la gestione del codice.

## 🚀 Avvio Rapido con Docker Compose

Il modo più semplice per vedere SneakVerse in azione è usare Docker Compose.

1.  **Clona il Repository**:
    ```bash
    git clone <repository-url>
    cd SneakVerse
    ```

2.  **Configura l'Host**:
    Aggiungi la seguente riga al tuo file `hosts` (es. `C:\Windows\System32\drivers\etc\hosts` su Windows o `/etc/hosts` su Linux):
    ```
    127.0.0.1 sneakverse.it
    ```

3.  **Inizializzazione Database**:
    Il file `init.sql` nella root viene eseguito automaticamente al primo avvio del container MySQL per creare i database e le tabelle necessarie.

4.  **Avvio dei Servizi**:
    ```bash
    docker-compose up -d --build
    ```
    *Nota: Il primo avvio potrebbe richiedere diversi minuti per il download delle immagini e la build dei JAR.*

## 🛠️ Sviluppo Individuale dei Microservizi

Se desideri lavorare su un singolo microservizio senza Docker:

### Backend (Spring Boot)
1. Entra nella cartella del servizio (es. `sneakverse-auth`).
2. Configura le variabili d'ambiente necessarie (puoi trovarle nel `docker-compose.yml` o nel `application.properties`).
3. Avvia con Maven/Gradle:
   ```bash
   ./mvnw spring-boot:run
   ```

### Frontend (React)
1. Entra nella cartella frontend (es. `sneakverse-frontend-user`).
2. Installa le dipendenze:
   ```bash
   npm install
   ```
3. Avvia in modalità sviluppo:
   ```bash
   npm start
   ```

## 🔑 Credenziali di Default

- **Database MySQL**: `root` / `root`
- **Liferay Admin**: (Configurato durante il primo setup di Liferay)
- **Redis**: Porta 6379 (senza password di default nel setup dev)

## 🐳 Troubleshooting

- **Memoria insufficiente**: Liferay e i microservizi Java sono pesanti. Assicurati che Docker abbia almeno 8GB di RAM allocata.
- **Porte occupate**: Controlla che le porte 8000, 8080, 8081, 8082, 8083, 8084 (Products), 3001 e 3306 siano libere.
- **Connessione Database**: Se un servizio non parte perché non trova il database, riavvialo: `docker-compose restart <service-name>`.

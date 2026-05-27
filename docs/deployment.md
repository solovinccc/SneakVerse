#  Deployment (Kubernetes & Helm)

SneakVerse è progettato per essere eseguito in un ambiente Kubernetes (K8s). La gestione dei manifesti è semplificata dall'uso di **Helm**.

##  Struttura Helm (`sneakverse-prod`)

Il progetto utilizza un Chart Helm centralizzato situato in `sneakverse-prod/`.

- **`values.yaml`**: Contiene tutte le configurazioni globali e specifiche per i servizi (URL database, segreti, tag delle immagini).
- **`templates/`**: Contiene i file YAML per:
    - **Deployments**: Definizione dei pod per ogni microservizio.
    - **Services**: Esposizione interna dei servizi.
    - **ConfigMaps & Secrets**: Gestione delle variabili d'ambiente e credenziali.
    - **Ingress**: Esposizione esterna tramite il dominio `sneakverse.it`.

##  Istruzioni per il Deployment

Per distribuire lo stack su un cluster K8s (es. Minikube o Kind):

1.  **Configurazione Context**:
    Assicurati di essere connesso al cluster corretto.
    ```bash
    kubectl config current-context
    ```

2.  **Installazione con Helm**:
    Dalla cartella root del progetto:
    ```bash
    helm install sneakverse ./sneakverse-prod
    ```

3.  **Verifica Status**:
    ```bash
    kubectl get pods
    kubectl get svc
    ```

##  Gestione dei Segreti

I segreti (password DB, chiavi JWT) sono gestiti tramite il template `sneakverse-secrets`.
*Nota: In produzione, si consiglia l'uso di External Secrets Operator o Vault.*

##  Configurazione Dominio

Per accedere all'app tramite `sneakverse.it`, assicurati che l'Ingress Controller sia attivo sul cluster e che il file `hosts` punti all'IP del nodo K8s.

##  Pipeline CI/CD (Suggerita)

Sebbene non inclusa nel repository, si consiglia una pipeline che:
1. Esegua i test unitari su ogni push.
2. Esegua la build delle immagini Docker.
3. Push delle immagini su un Container Registry (es. Docker Hub, GCR).
4. Aggiorni il tag dell'immagine nel `values.yaml` e applichi l'aggiornamento con `helm upgrade`.

# Microservices Project with Spring Cloud Gateway and OAuth2

## Overview

This project demonstrates a microservices architecture with a central **Gateway**, a **frontend** React application, and backend services (`java-backend`, `email-service`). The Gateway handles routing, session management, and authentication using **Google OAuth2**.

Users must log in via Google to access the application. The `/profile` endpoint returns user information (name, email) obtained from the Google ID token.

---

## Architecture

### 1. Gateway
- Implemented with **Spring Cloud Gateway**.
- Runs on port `8080`.
- Routes:
  - `/` → Frontend
  - `/static/**` and `/assets/**` → Frontend static assets
  - `/api/**` → Java backend
  - `/email/**` → Email service
- Handles **OAuth2 login** via Google.
- Protects `/profile` and backend API routes.
- Uses `SaveSession` filter to maintain session state across requests.

### 2. Frontend
- **React** application served via **Nginx**.
- Makes fetch requests to `/profile` endpoint to check login status.
- If unauthorized (`401`), shows `Login with Google` button.
- After successful login, fetches user info and displays it on the UI.

---

## Deployment

- **Docker images** built for:
  - Frontend
  - Gateway
  - Java backend
- **Kubernetes manifests** in the `k8s/` folder deploy services to **Google Kubernetes Engine (GKE)**.
- Gateway exposes the application via `LoadBalancer` service.

### Access

- Public URL (replace with your EXTERNAL-IP from GKE):



- Login via **Google OAuth2** is required.

---

## CI/CD

- **GitHub Actions** workflow builds Docker images and deploys to GKE automatically on `push` to `master`.
- Steps:
1. Checkout repository.
2. Authenticate to Google Cloud using a service account stored in `GitHub Secrets`.
3. Build backend and gateway images, push to Google Container Registry.
4. Build frontend, package with Nginx, push Docker image.
5. Apply Kubernetes manifests and restart deployments.

**Secrets used:**
- `GCP_PROJECT_ID`
- `GKE_CLUSTER`
- `GKE_ZONE`
- `GCP_SA_KEY` (Service Account JSON)
- `GOOGLE_CLIENT_ID`
- `GOOGLE_CLIENT_SECRET`

---

## OAuth2 Configuration

- Google OAuth2 client is configured with:
- `client-id`
- `client-secret`
- Scopes: `openid`, `profile`, `email`
- Redirect URI: `{baseUrl}/login/oauth2/code/{registrationId}`
- Gateway manages the login flow and session.
- `/profile` endpoint returns the authenticated user's name and email.

---

## Usage

1. Open the public URL in a browser.
2. Click `Login with Google`.
3. Upon successful login, the frontend fetches `/profile` and displays the user's information.
4. All API requests to `/api/**` or `/email/**` are routed through the Gateway and protected by OAuth2.

---

### Local Port Forwarding for Gateway

If you want to test the gateway locally through Kubernetes, you can forward the service port 80 from the cluster to your localhost 8080:

```bash
kubectl port-forward service/gateway-service 8080:80


### Add your secrets
apiVersion: v1
kind: Secret
metadata:
  name: google-oauth
type: Opaque
stringData:
  client-id: ""
  client-secret: ""

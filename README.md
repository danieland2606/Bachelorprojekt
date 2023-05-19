# MeowMed

## Buildanleitung:

### Vorraussetzung:
Docker oder Docker Desktop muss installiert und gestartet sein.
Unter Linux muss auch Docker-Compose installiert sein.

### Inhalt der docker-compose:
- Projekt von EDA (UI-Port 80, Customer-Port 8071, Policy-Port 8072)
- Projekt von RDA (UI-Port 80, Customer-Port 8081, Policy-Port 8082)
- Swagger Oberfläche auf Port 81

### Erzeugen vom EDA Projekt:
```bash
docker compose --profile eda up -d --build
```
### Erzeugen vom RDA Projekt:
```bash
docker compose --profile rda up -d --build
```

### Häufige Fehler:
"mvnw: Not found" : Zeichenset von der mvnw-Datei ist dann auf CRLF. LF wird benötigt. GitBash öffnen und dos2unix.exe auf alle mvnw-Dateien nutzen ("dos2unix.exe Projektordner/service/mvnw")
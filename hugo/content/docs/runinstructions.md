---
title: How To Run
description: How to run the application
authors:
  - jakob
date: 2025-03-02
lastmod: 2025-03-03
---

## Starten des Programms

### Voraussetzungen

- docker compose
- Java 21

### Starten der Datenbank

```shell
./postgres-start.sh
```

### Starten der Anwendung

```shell
cd javafx
./mvnw clean package
java -jar target/helpr-*-runner.jar
```

### Troubleshooting

```shell
# Datenbank stoppen
./postgres-stop.sh

# Daten löschen
./postgres-delete-volumes.sh

# Datenbank starten
./postgres-start.sh
```

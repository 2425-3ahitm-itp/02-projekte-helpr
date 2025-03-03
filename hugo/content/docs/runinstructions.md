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

### Starten der Datenbank

```shell
cd javafx/src/main/docker
docker compose up
```


### Setup der Datenbank

1. SQL in `javafx/src/main/resources/database/schema.sql` kopieren.
2. `localhost:8090` im browser öffnen
3. Einmaliges beliebiges passwort festlegen
4. Server Hinzufügen
    ```
    General
     Name: Helpr
    Connection
     Hostname: postgres
     Port: 5432
     Username: app
     Password: app
    -> Save
    ```

5. Server `Helpr`
6. Rechtsklick auf Datenbank `helpr` -> Query Tool
7. schema.sql einfügen
8. Play button drücken "Execute Script"


### Starten der Anwendung

```shell
cd javafx
./mvnw package
./mvnw quarkus:run
```
---
title: How To Run
description: How to run the application
authors:
  - jakob
date: 2025-03-02
lastmod: 2025-03-03
---

## Prerequisites

- docker compose

## Starting the Database
```shell
cd javafx/src/main/docker
docker compose up
```

## Database Setup
1. Copy SQL from `javafx/src/main/resources/database/schema.sql`.

2. Open `localhost:8090` in your browser.

3. Set any password of your choice (one-time).

4. Add Server:

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

5. Select Server `Helpr`.

6. Right-click on database `helpr` -> Query Tool.

7. Paste schema.sql.

8. Click the Play button `Execute Script`.

## Starting the Application

```shell
cd javafx
./mvnw package
./mvnw quarkus:run
```
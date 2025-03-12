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

## Starting the Application

```shell
cd javafx
./mvnw package
./mvnw quarkus:run
```
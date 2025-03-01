---
title: Userstories
date: 2024-11-19
lastmod: 2024-11-19
author: jakob
tags: [user stories]
---


## Account erstellen/Anmeldung

<span style="color: rgba(var(--color-primary-300))">

Als Nutzer  
möchte ich einen Account erstellen können,  
um mich später anmelden zu können.

</span>

`Priorität: 1`

### Akzeptanzkriterien

- Ein Nutzer hat einen Nutzernamen und ein Passwort
- Nutzernamen sind einzigartig
- Ein Nutzer kann sich mit seinem Nutzernamen und Passwort anmelden


## Aufgaben erstellen

<span style="color: rgba(var(--color-primary-300))">

Als Hilfe suchender Nutzer  
möchte ich Aufgabenstellungen posten können,  
damit mir geholfen werden kann.

</span>

`Priorität: 1`

### Akzeptanzkriterien

- Der Nutzer muss angemeldet sein
- Aufgabenstellungen müssen gespeichert werden können
- Der Nutzer kann seine Aufgabenstellungen sehen und verwalten


## Aufgaben finden

<span style="color: rgba(var(--color-primary-300))">

Als helfender Nutzer  
möchte ich andere Aufgabenstellungen in meiner Umgebung sehen können,  
um anderen zu helfen.  

</span>

`Priorität: 1`

### Akzeptanzkriterien

- Nutzer können andere Aufgaben sehen
- Die Aufgaben werden nach Nähe gefiltert


## Aufgabenfilterung

<span style="color: rgba(var(--color-primary-300))">

Als helfender Nutzer  
möchte ich unter den Aufgaben filtern können,  
um meine Stärken am besten einsetzen zu können.

</span>

`Priorität: 2`

### Akzeptanzkriterien

- Es gibt Filteroptionen, um die Ergebnisvielfalt zu vermindern
- Filteroptionen sind z.B.: Distanz, Indoor, Outdoor, Arbeitsaufwand, Gebiet


## Kontaktaufnahme mit Aufgabensteller

<span style="color: rgba(var(--color-primary-300))">

Als helfender Nutzer  
möchte ich Aufgabensteller kontaktieren können,  
um mich als Helfer zu bewerben.

</span>

`Priorität: 1`

### Akzeptanzkriterien

- Der helfende Nutzer muss angemeldet sein
- Es gibt einen Chat zwischen Hilfe suchendem und helfendem Nutzer


## Helfer auswählen

<span style="color: rgba(var(--color-primary-300))">

Als Hilfesuchender  
möchte ich einen Bewerber meiner Aufgabe auswählen können.

</span>

`Priorität: 1`

### Akzeptanzkriterien

- Der Hilfesuchende muss unter den Bewerbern auswählen können, wer die Aufgabe bekommt


## Bewerber annehmen

<span style="color: rgba(var(--color-primary-300))">

Als ausgewählter Bewerber  
möchte ich entscheiden können, ob ich die Aufgabe annehme.

</span>

`Priorität: 1`

### Akzeptanzkriterien

- Beim Annehmen der Aufgabe werden alle anderen Bewerber automatisch abgelehnt
- Die Aufgabe wird im System als Aktiv gekennzeichnet
- Die Aufgabe ist nicht mehr für andere Nutzer sichtbar



## Klassendiagramm

{{< plantuml >}}

@startuml
class User {
-id
-username
-password
-location
}


class Task {
-id
-title
-description
-location
-status
'    -createdAt
-category
-estimatedEffort
-chats
}


class Chat {
-id
-users
-messages
-status
}

class Message {
-id
-content
-sentAt
-isRead
-sender
}

enum TaskStatus {
OPEN
ACTIVE
COMPLETED
CANCELLED
}


enum ApplicationStatus {
PENDING
ACCEPTED
REJECTED
}

User "1" -- "*" Task : creates
User "1" -- "*" Task : does
Chat "1" -- "*" Message : contains
Task "1" -- "*" User : as applicants
Task "1" -- "*" Chat
Chat "1" -- "2" User
Task "1" -- "1" TaskStatus
Chat "1" -- "1" ApplicationStatus

{{< /plantuml >}}


# 🌩️ Unwetterdatenbank – JavaFX-Projekt mit Microsoft SQL Server

Dieses Projekt wurde im Rahmen der Lehrveranstaltung **„Datenbank“** entwickelt. Ziel war es, eine Anwendung zur **Ablage und Verwaltung von Daten** auf einem **Datenbankserver** zu erstellen sowie eine **grafische Benutzeroberfläche (GUI)** bereitzustellen.

## 📌 Projektbeschreibung

Die Anwendung ermöglicht die Verwaltung einer Datenbank zur Dokumentation verschiedener **Unwetterereignisse**. Dabei wurden die folgenden Kernfunktionen implementiert:

- Verbindung zur Microsoft SQL Server-Datenbank (*Unwetterdatenbank*)
- Benutzerfreundliche GUI mit **JavaFX**
- Login-Funktion mit Rollenkonzept
- CRUD-Operationen (je nach Benutzerrolle) auf verschiedene Tabellen
- **Exportfunktion** zum Speichern der Daten im **XML-Format (.xml)** über JAXB

## 🗃️ Datenbankstruktur

Die Datenbank besteht aus mehreren logisch verknüpften Tabellen:

| Tabelle       | Beschreibung                                                  |
|---------------|---------------------------------------------------------------|
| `Unwetterart` | Art des Unwetters (z. B. Hagel, Dürre, Schneefall, Sturm usw.) |
| `Organisation`| Zuständige Organisationen zur Beseitigung von Schäden         |
| `Region`      | Geografische Regionen, in denen das Ereignis stattfand        |
| `Schade`      | Beschreibung der entstandenen Schäden                         |
| `Ereignis`    | Zentrale Tabelle, die Informationen aus allen obigen sammelt  |

## 👥 Benutzerrollen und Logins

Die Anwendung bietet drei Benutzerrollen mit unterschiedlichen Berechtigungen:

| Rolle    | Benutzername     | Passwort        | Berechtigungen                   |
|----------|------------------|------------------|----------------------------------|
| Admin    | `admin`          | `admin`          | Lesen, Schreiben, Löschen        |
| Manager  | `readwriter`     | `readwriter`     | Lesen, Schreiben                 |
| Reader   | `reader`         | `reader`         | Nur Lesen                        |

## 🖼️ Screenshots

- Login-Seite
  ![image](https://github.com/user-attachments/assets/1f83e16a-1bdc-42de-a456-c52643f62a8c)

- Ereignis-Tabelle mit Filter- und Bearbeitungsoptionen
  ![image](https://github.com/user-attachments/assets/83519147-c438-4952-97b0-d1e8a18eaf4f)
 

## 🛠️ Technologien

- **Java** mit **JavaFX** für die Benutzeroberfläche
- **Microsoft SQL Server Management Studio (SSMS)** für die Datenbankverwaltung
- **FXML** zur Gestaltung der GUI
- **JDBC** zur Verbindung zwischen Java und SQL Server
- **JAXB** für den **Export von Daten als XML-Datei**

## 📄 Lizenz

Dieses Projekt wurde im Rahmen eines Hochschulkurses entwickelt und ist ausschließlich für **Lern- und Demonstrationszwecke** gedacht.

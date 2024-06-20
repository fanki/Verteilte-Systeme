1.Tag Kurs-Übersicht und Einstieg Serverseitige Programmierung
Projekt kann im Terminal im Hauptverzeichnis mit diesem Befehl Kompiliert und gestartet werden ./mvnw compile quarkus:dev

2.Tag DB-Anbindung mit Quarkus Panache
Erste Repos und Services hinzugefügt. Docker, Hibernate ORM with Panache, JDBC Driver - MySQL hinzugefügt.

API-Endpoints
Blog Endpoints
GET /blog
Gibt alle Blogs zurück. Optional kann nach Autor gefiltert werden.

Query Parameter:

authorId (optional): ID des Autors, um Blogs zu filtern.
Responses:

200 OK: Erfolgreiche Antwort, gibt eine Liste von Blogs zurück.
404 Not Found: Keine Blogs gefunden.
POST /blog
Fügt einen neuen Blog hinzu.

Request Body:

Blog-Objekt im JSON-Format.
Responses:

201 Created: Blog erfolgreich erstellt.
Author Endpoints
GET /author
Gibt alle Autoren zurück.

Responses:

200 OK: Erfolgreiche Antwort, gibt eine Liste von Autoren zurück.
POST /author
Fügt einen neuen Autor hinzu.

Request Body:

Author-Objekt im JSON-Format.
Responses:

201 Created: Autor erfolgreich erstellt.
PUT /author/{id}
Editiert einen vorhandenen Autor.

Path Parameter:

id (erforderlich): ID des Autors, der bearbeitet werden soll.
Request Body:

Author-Objekt im JSON-Format.
Responses:

200 OK: Autor erfolgreich bearbeitet.
404 Not Found: Autor nicht gefunden.
Installation und Ausführung
Klone das Repository:

bash
Code kopieren
git clone <https://github.com/fanki/Verteilte-Systeme/tree/main>
Wechsle in das Verzeichnis:

bash
Code kopieren
cd <code-with-quarkus>
Starte die Anwendung mit Quarkus:

bash
Code kopieren
./mvnw compile quarkus:dev

Die API ist nun unter http://localhost:8080 verfügbar.


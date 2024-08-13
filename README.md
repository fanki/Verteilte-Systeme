Blog API Projekt
Übersicht

Diese Dokumentation beschreibt die Blog-API, die es ermöglicht, Blogs, Autoren und Kommentare zu verwalten. Die API besteht aus mehreren Ressourcen, die über HTTP-Methoden zugänglich sind. Die Hauptkomponenten der API sind:

AuthorResource – verwaltet Autoren
BlogResource – verwaltet Blogs
CommentResource – verwaltet Kommentare
DataInitialization – initialisiert Beispiel-Blogs beim Start der Anwendung
Entity-Klassen – definieren die Datenstruktur der Entitäten: Autor, Blog, Kommentar
Repository-Klassen – bieten Zugriff auf die Datenbank für die Entitäten

API Endpunkte
1. Autor-Management
AuthorResource
GET /author

Gibt alle Autoren zurück.
Antworttyp: application/json – Liste von Autoren.
POST /author

Fügt einen neuen Autor hinzu.
Anfrage-Body: application/json – Autor-Daten (Name, Biografie).
Antworttyp: application/json – Erstellter Autor.
PUT /author/{id}

Aktualisiert die Informationen eines Autors.
Anfrage-Body: application/json – Aktualisierte Autor-Daten.
Antworttyp: application/json – Aktualisierter Autor oder Fehlermeldung (404, wenn Autor nicht gefunden).

2. Blog-Management
BlogResource
GET /blog

Gibt alle Blogs zurück oder filtert Blogs nach authorId.
Anfrage-Parameter:
authorId (optional) – ID des Autors zur Filterung.
Antworttyp: application/json – Liste von Blogs.
POST /blog

Fügt einen neuen Blog hinzu.
Anfrage-Body: application/json – Blog-Daten (Titel, Inhalt, Kategorie, Autor).
Antworttyp: application/json – Erstellter Blog.
DELETE /blog/{id}

Löscht einen Blog anhand der ID.
Antworttyp: 204 No Content – Erfolgsmeldung oder Fehlermeldung (404, wenn Blog nicht gefunden).
GET /blog/category

Gibt Blogs zurück, die zu einer bestimmten Kategorie gehören.
Anfrage-Parameter:
category – Kategorie zur Filterung.
Antworttyp: application/json – Liste von Blogs.

3. Kommentar-Management
CommentResource
GET /comments

Gibt alle Kommentare für einen bestimmten Blog zurück.
Anfrage-Parameter:
blogId – ID des Blogs zur Filterung.
Antworttyp: application/json – Liste von Kommentaren.
POST /comments/{blogId}

Fügt einen neuen Kommentar zu einem Blog hinzu.
Anfrage-Body: application/json – Kommentar-Daten (Inhalt).
Antworttyp: application/json – Erstellter Kommentar oder Fehlermeldung (404, wenn Blog nicht gefunden).
DELETE /comments/{id}

Löscht einen Kommentar anhand der ID.
Antworttyp: 204 No Content – Erfolgsmeldung oder Fehlermeldung (404, wenn Kommentar nicht gefunden).


Entitäten
Author

ID: Long (Primärschlüssel)
Name: String (Name des Autors)
Biography: String (Biografie des Autors)
Blog

ID: Long (Primärschlüssel)
Title: String (Titel des Blogs)
Content: String (Inhalt des Blogs)
Category: String (Kategorie des Blogs)
Author: Author (Autor des Blogs, ManyToOne-Beziehung)
Comment

ID: Long (Primärschlüssel)
Content: String (Inhalt des Kommentars)
Blog: Blog (Blog, zu dem der Kommentar gehört, ManyToOne-Beziehung)

Services
AuthorService

getAllAuthors(): Gibt eine Liste aller Autoren zurück.
addAuthor(Author author): Fügt einen neuen Autor hinzu.
updateAuthor(Long id, Author author): Aktualisiert einen bestehenden Autor.
deleteAuthor(Long id): Löscht einen Autor anhand der ID.
BlogService

getBlogs(): Gibt eine Liste aller Blogs zurück.
getBlogsByAuthor(Long authorId): Gibt Blogs eines bestimmten Autors zurück.
getBlogsByCategory(String category): Gibt Blogs einer bestimmten Kategorie zurück.
addBlog(Blog blog): Fügt einen neuen Blog hinzu.
deleteBlog(Long id): Löscht einen Blog anhand der ID.
getBlogsFiltered(Long authorId, String title): Gibt Blogs zurück, gefiltert nach Autor und/oder Titel.
findById(Long id): Gibt einen Blog anhand der ID zurück.
CommentService

addComment(Blog blog, Comment comment): Fügt einen Kommentar zu einem Blog hinzu.
getComments(Long blogId): Gibt alle Kommentare eines Blogs zurück.
getCommentsByBlog(Long blogId): Gibt Kommentare eines bestimmten Blogs zurück.
deleteComment(Long id): Löscht einen Kommentar anhand der ID.
DataInitialization

init(): Initialisiert Beispiel-Blogs beim Start der Anwendung.

Repository-Klassen
AuthorRepository: Bietet Zugriff auf die Author-Entitäten.
BlogRepository: Bietet Zugriff auf die Blog-Entitäten.
CommentRepository: Bietet Zugriff auf die Comment-Entitäten.

Hinweise
Achten Sie darauf, dass die @Transactional-Anmerkung verwendet wird, um sicherzustellen, dass Datenbankoperationen ordnungsgemäß ausgeführt werden.
Die @Inject-Anmerkung wird verwendet, um die Abhängigkeiten zwischen den verschiedenen Klassen zu verwalten.

OpenAPI Dokumentation
Die OpenAPI-Dokumentation beschreibt die verfügbaren Endpunkte, die Operationen und die möglichen Antworten. Sie können sie verwenden, um die API zu testen und zu integrieren.

Installation und Ausführung
Klone das Repository:

bash
Code kopieren
git clone https://github.com/fanki/Verteilte-Systeme/tree/main
Wechsle in das Verzeichnis:

bash
Code kopieren
cd <code-with-quarkus>
Starte die Anwendung mit Quarkus:

bash
Code kopieren
./mvnw compile quarkus:dev
Die API ist nun unter http://localhost:8080 verfügbar.
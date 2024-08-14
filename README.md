# Blog API Projekt

## Übersicht

Diese Dokumentation beschreibt die Blog-API, die es ermöglicht, Blogs, Autoren, Kommentare und Tags zu verwalten. Die API besteht aus mehreren Ressourcen, die über HTTP-Methoden zugänglich sind. Die Hauptkomponenten der API sind:

- **AuthorResource** – verwaltet Autoren
- **BlogResource** – verwaltet Blogs
- **CommentResource** – verwaltet Kommentare
- **TagResource** – verwaltet Tags
- **DataInitialization** – initialisiert Beispiel-Blogs beim Start der Anwendung
- **Entity-Klassen** – definieren die Datenstruktur der Entitäten: Autor, Blog, Kommentar, Tag
- **Repository-Klassen** – bieten Zugriff auf die Datenbank für die Entitäten
- **Service-Klassen** – enthalten die Geschäftslogik für die Entitäten

## API Endpunkte

### 1. Autor-Management

#### `GET /author`
Gibt alle Autoren zurück.
- **Antworttyp**: `application/json` – Liste von Autoren.

#### `POST /author`
Fügt einen neuen Autor hinzu.
- **Anfrage-Body**: `application/json` – Autor-Daten (Name, Biografie).
- **Antworttyp**: `application/json` – Erstellter Autor.
- **Fehler**:
  - `400 Bad Request` – Ungültige Anfrage.

#### `PUT /author/{id}`
Aktualisiert die Informationen eines Autors.
- **Anfrage-Body**: `application/json` – Aktualisierte Autor-Daten.
- **Antworttyp**: `application/json` – Aktualisierter Autor.
- **Fehler**:
  - `404 Not Found` – Autor nicht gefunden.
  - `400 Bad Request` – Ungültige Anfrage.

### 2. Blog-Management

#### `GET /blog`
Gibt alle Blogs zurück oder filtert Blogs nach `authorId`, `title` oder `tag`.
- **Anfrage-Parameter**:
  - `authorId` (optional) – ID des Autors zur Filterung.
  - `title` (optional) – Titel zum Filtern der Blogs.
  - `tag` (optional) – Tag zum Filtern der Blogs.
- **Antworttyp**: `application/json` – Liste von Blogs.
- **Fehler**:
  - `404 Not Found` – Keine Blogs gefunden.

#### `POST /blog`
Fügt einen neuen Blog hinzu.
- **Anfrage-Body**: `application/json` – Blog-Daten (Titel, Inhalt, Kategorie, Autor, Tags).
- **Antworttyp**: `application/json` – Erstellter Blog.
- **Fehler**:
  - `400 Bad Request` – Ungültige Anfrage.

#### `DELETE /blog/{id}`
Löscht einen Blog anhand der ID.
- **Antworttyp**: `204 No Content` – Erfolgsmeldung.
- **Fehler**:
  - `404 Not Found` – Blog nicht gefunden.

### 3. Kommentar-Management

#### `GET /blog/{blogId}/comments`
Gibt alle Kommentare für einen bestimmten Blog zurück.
- **Anfrage-Parameter**:
  - `blogId` – ID des Blogs zur Filterung.
- **Antworttyp**: `application/json` – Liste von Kommentaren.
- **Fehler**:
  - `404 Not Found` – Keine Kommentare gefunden.

#### `POST /blog/{blogId}/comments`
Fügt einen neuen Kommentar zu einem Blog hinzu.
- **Anfrage-Body**: `application/json` – Kommentar-Daten (Inhalt).
- **Antworttyp**: `application/json` – Erstellter Kommentar.
- **Fehler**:
  - `404 Not Found` – Blog nicht gefunden.
  - `400 Bad Request` – Ungültige Anfrage.

#### `DELETE /comments/{id}`
Löscht einen Kommentar anhand der ID.
- **Antworttyp**: `204 No Content` – Erfolgsmeldung.
- **Fehler**:
  - `404 Not Found` – Kommentar nicht gefunden.

### 4. Tag-Management

#### `GET /tag`
Gibt alle Tags zurück.
- **Antworttyp**: `application/json` – Liste von Tags.

#### `POST /tag`
Fügt einen neuen Tag hinzu.
- **Anfrage-Body**: `application/json` – Tag-Daten (Name).
- **Antworttyp**: `application/json` – Erstellter Tag.
- **Fehler**:
  - `400 Bad Request` – Ungültige Anfrage.

#### `POST /blog/{blogId}/tags`
Fügt einen Tag zu einem Blog hinzu.
- **Anfrage-Body**: `application/json` – Tag-Daten (Name).
- **Antworttyp**: `application/json` – Hinzugefügter Tag.
- **Fehler**:
  - `404 Not Found` – Blog nicht gefunden.
  - `400 Bad Request` – Ungültige Anfrage.

## Entitäten

### Author
- **ID**: Long (Primärschlüssel)
- **Name**: String (Name des Autors)
- **Biography**: String (Biografie des Autors)

### Blog
- **ID**: Long (Primärschlüssel)
- **Title**: String (Titel des Blogs)
- **Content**: String (Inhalt des Blogs)
- **Category**: String (Kategorie des Blogs)
- **Author**: Author (Autor des Blogs, ManyToOne-Beziehung)
- **Tags**: List[Tag] (Tags des Blogs, ManyToMany-Beziehung)

### Comment
- **ID**: Long (Primärschlüssel)
- **Content**: String (Inhalt des Kommentars)
- **Blog**: Blog (Blog, zu dem der Kommentar gehört, ManyToOne-Beziehung)

### Tag
- **ID**: Long (Primärschlüssel)
- **Name**: String (Name des Tags)

## Services

### AuthorService
- `getAllAuthors()`: Gibt eine Liste aller Autoren zurück.
- `addAuthor(Author author)`: Fügt einen neuen Autor hinzu.
- `updateAuthor(Long id, Author author)`: Aktualisiert einen bestehenden Autor.
- `deleteAuthor(Long id)`: Löscht einen Autor anhand der ID.

### BlogService
- `getBlogs()`: Gibt eine Liste aller Blogs zurück.
- `getBlogsByAuthor(Long authorId)`: Gibt Blogs eines bestimmten Autors zurück.
- `getBlogsByCategory(String category)`: Gibt Blogs einer bestimmten Kategorie zurück.
- `getBlogsFiltered(Long authorId, String title, String tag)`: Gibt Blogs zurück, gefiltert nach Autor, Titel und/oder Tag.
- `addBlog(Blog blog)`: Fügt einen neuen Blog hinzu.
- `deleteBlog(Long id)`: Löscht einen Blog anhand der ID.
- `findById(Long id)`: Gibt einen Blog anhand der ID zurück.

### CommentService
- `addComment(Blog blog, Comment comment)`: Fügt einen Kommentar zu einem Blog hinzu.
- `getComments(Long blogId)`: Gibt alle Kommentare eines Blogs zurück.
- `deleteComment(Long id)`: Löscht einen Kommentar anhand der ID.

### TagService
- `getAllTags()`: Gibt eine Liste aller Tags zurück.
- `addTag(Tag tag)`: Fügt einen neuen Tag hinzu.
- `addTagToBlog(Long blogId, Tag tag)`: Fügt einen Tag zu einem Blog hinzu.
- `getTagsByBlog(Long blogId)`: Gibt alle Tags eines Blogs zurück.
- `deleteTag(Long id)`: Löscht einen Tag anhand der ID.

### DataInitialization
- `init()`: Initialisiert Beispiel-Blogs beim Start der Anwendung.

## Repository-Klassen

- **AuthorRepository**: Bietet Zugriff auf die Author-Entitäten.
- **BlogRepository**: Bietet Zugriff auf die Blog-Entitäten.
- **CommentRepository**: Bietet Zugriff auf die Comment-Entitäten.
- **TagRepository**: Bietet Zugriff auf die Tag-Entitäten.

## Hinweise
- Achten Sie darauf, dass die `@Transactional`-Anmerkung verwendet wird, um sicherzustellen, dass Datenbankoperationen ordnungsgemäß ausgeführt werden.
- Die `@Inject`-Anmerkung wird verwendet, um die Abhängigkeiten zwischen den verschiedenen Klassen zu verwalten.

## OpenAPI Dokumentation
Die OpenAPI-Dokumentation beschreibt die verfügbaren Endpunkte, die Operationen und die möglichen Antworten. Sie können sie verwenden, um die API zu testen und zu integrieren. Die OpenAPI-Spezifikation ist in der Datei `openapi.json` zu finden.

## Installation und Ausführung

1. Klone das Repository:
    ```bash
    git clone https://github.com/fanki/Verteilte-Systeme/tree/main
    ```
2. Wechsle in das Verzeichnis:
    ```bash
    cd code-with-quarkus
    ```
3. Starte die Anwendung mit Quarkus:
    ```bash
    ./mvnw compile quarkus:dev
    ```

Die API ist nun unter [http://localhost:8080](http://localhost:8080) verfügbar.

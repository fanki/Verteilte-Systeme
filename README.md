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
- **Zugänglichkeit**: Öffentlich
- **Antworttyp**: `application/json` – Liste von Autoren.

#### `POST /author`
Fügt einen neuen Autor hinzu.
- **Zugänglichkeit**: Authentifizierung erforderlich, Rolle: Admin
- **Anfrage-Body**: `application/json` – Autor-Daten (Name, Biografie).
- **Antworttyp**: `application/json` – Erstellter Autor.
- **Fehler**:
  - `400 Bad Request` – Ungültige Anfrage.

#### `PUT /author/{id}`
Aktualisiert die Informationen eines Autors.
- **Zugänglichkeit**: Authentifizierung erforderlich, Rolle: Admin
- **Anfrage-Body**: `application/json` – Aktualisierte Autor-Daten.
- **Antworttyp**: `application/json` – Aktualisierter Autor.
- **Fehler**:
  - `404 Not Found` – Autor nicht gefunden.
  - `400 Bad Request` – Ungültige Anfrage.

### 2. Blog-Management

#### `GET /blog`
Gibt alle Blogs zurück oder filtert Blogs nach `authorId`, `title` oder `tag`.
- **Zugänglichkeit**: Öffentlich
- **Anfrage-Parameter**:
  - `authorId` (optional) – ID des Autors zur Filterung.
  - `title` (optional) – Titel zum Filtern der Blogs.
  - `tag` (optional) – Tag zum Filtern der Blogs.
- **Antworttyp**: `application/json` – Liste von Blogs.
- **Fehler**:
  - `404 Not Found` – Keine Blogs gefunden.

#### `POST /blog`
Fügt einen neuen Blog hinzu.
- **Zugänglichkeit**: Authentifizierung erforderlich, Rolle: Author
- **Anfrage-Body**: `application/json` – Blog-Daten (Titel, Inhalt, Kategorie, Autor, Tags).
- **Antworttyp**: `application/json` – Erstellter Blog.
- **Fehler**:
  - `400 Bad Request` – Ungültige Anfrage.

#### `DELETE /blog/{id}`
Löscht einen Blog anhand der ID.
- **Zugänglichkeit**: Authentifizierung erforderlich, Rolle: Admin
- **Antworttyp**: `204 No Content` – Erfolgsmeldung.
- **Fehler**:
  - `404 Not Found` – Blog nicht gefunden.

### 3. Kommentar-Management

#### `GET /blog/{blogId}/comments`
Gibt alle Kommentare für einen bestimmten Blog zurück.
- **Zugänglichkeit**: Öffentlich
- **Anfrage-Parameter**:
  - `blogId` – ID des Blogs zur Filterung.
- **Antworttyp**: `application/json` – Liste von Kommentaren.
- **Fehler**:
  - `404 Not Found` – Keine Kommentare gefunden.

#### `POST /blog/{blogId}/comments`
Fügt einen neuen Kommentar zu einem Blog hinzu.
- **Zugänglichkeit**: Authentifizierung erforderlich, Rolle: Author
- **Anfrage-Body**: `application/json` – Kommentar-Daten (Inhalt).
- **Antworttyp**: `application/json` – Erstellter Kommentar.
- **Fehler**:
  - `404 Not Found` – Blog nicht gefunden.
  - `400 Bad Request` – Ungültige Anfrage.

#### `DELETE /comments/{id}`
Löscht einen Kommentar anhand der ID.
- **Zugänglichkeit**: Authentifizierung erforderlich, Rolle: Admin
- **Antworttyp**: `204 No Content` – Erfolgsmeldung.
- **Fehler**:
  - `404 Not Found` – Kommentar nicht gefunden.

### 4. Tag-Management

#### `GET /tag`
Gibt alle Tags zurück.
- **Zugänglichkeit**: Öffentlich
- **Antworttyp**: `application/json` – Liste von Tags.

#### `POST /tag`
Fügt einen neuen Tag hinzu.
- **Zugänglichkeit**: Authentifizierung erforderlich, Rolle: Admin
- **Anfrage-Body**: `application/json` – Tag-Daten (Name).
- **Antworttyp**: `application/json` – Erstellter Tag.
- **Fehler**:
  - `400 Bad Request` – Ungültige Anfrage.

#### `POST /blog/{blogId}/tags`
Fügt einen Tag zu einem Blog hinzu.
- **Zugänglichkeit**: Authentifizierung erforderlich, Rolle: Admin
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
- `getCommentsByBlog(Long blogId)`: Gibt alle Kommentare eines Blogs zurück.
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

## Berechtigungskonzept und Authentifizierungs-Setup

### Benutzer-Rollen und Berechtigungen
Die folgenden Benutzerrollen sind in der API definiert und bestimmen, welche Aktionen die Benutzer ausführen können:

1. **Admin**
   - Kann Autoren hinzufügen, aktualisieren und löschen.
   - Kann Blogs hinzufügen und löschen.
   - Kann Kommentare löschen.
   - Kann Tags hinzufügen und Blogs zuweisen.

2. **Author**
   - Kann Blogs hinzufügen.
   - Kann Kommentare zu Blogs hinzufügen.

3. **User**
   - Kann Blogs und Kommentare lesen.

### Authentifizierungs-Setup austesten

Um das Authentifizierungs-Setup und die Benutzerrollen zu testen, folge diesen Schritten:

1. **Keycloak-Konfiguration:**
   - Richte Keycloak mit den notwendigen Realms, Clients und Benutzerrollen ein. Verwende die Quarkus Dev-Services, um Keycloak automatisch zu starten.
   - Beispiel-Konfiguration:
     - Realm: `blog`
     - Clients: `backend-service`
     - Benutzer: `admin` (Rolle: Admin), `author` (Rolle: Author), `user` (Rolle: User)
   
2. **JWT-Token abrufen:**
   - Melde dich bei Keycloak an, um einen JWT-Token zu erhalten. Dieser Token wird benötigt, um geschützte Endpunkte aufzurufen.
   - Beispiel:
     ```bash
     http -v --form --auth backend-service:secret POST http://localhost:8180/realms/blog/protocol/openid-connect/token \
     username=simon password=simon grant_type=password client_id=backend-service
     ```

3. **Token für API-Aufrufe verwenden:**
   - Verwende den JWT-Token in den HTTP-Headern deiner API-Aufrufe:
     ```bash
     http POST http://localhost:8080/blog Authorization:"Bearer <TOKEN>" \
     Content-Type:application/json title="Neuer Blog" content="Inhalt des Blogs" category="Tech" authorId:=1
     ```

4. **Testfälle für Berechtigungen:**
   - Teste, ob Benutzer ohne ausreichende Berechtigungen blockiert werden und ob die richtigen HTTP-Statuscodes zurückgegeben werden.
   - Zum Beispiel, wenn ein `Author` versucht, einen Blog zu löschen, sollte dies blockiert werden.

## DTOs und Validierung

### AuthorDTO
- **Felder:**
  - `name`: Der Name des Autors. Muss ein nicht-leerer String sein.
  - `biography`: Die Biografie des Autors. Optionaler Text.
  
- **Validierung:**
  - `name` darf nicht leer sein.

### BlogDTO
- **Felder:**
  - `title`: Der Titel des Blogs. Muss ein nicht-leerer String sein.
  - `content`: Der Inhalt des Blogs. Muss ein nicht-leerer String sein.
  - `category`: Die Kategorie des Blogs. Optional.
  - `authorId`: Die ID des Autors, der den Blog erstellt hat.
  - `tags`: Eine Liste von Tags für den Blog. Optional.
  
- **Validierung:**
  - `title` und `content` dürfen nicht leer sein.
  - `authorId` muss vorhanden und gültig sein.

### CommentDTO
- **Felder:**
  - `content`: Der Inhalt des Kommentars. Muss ein nicht-leerer String sein.
  - `blogId`: Die ID des Blogs, zu dem der Kommentar gehört.
  
- **Validierung:**
  - `content` darf nicht leer sein.
  - `blogId` muss vorhanden und gültig sein.

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

## Packaging
Mit folgendem befehl wird ein Package erstellt.
./mvn package
 
Es wird die quarkus-run.jar Datei erstellt welche im Ordner `target/quarkus-app/`
Die Dependencies werden dabei in den Ordner `target/quarkus-app/lib/`kopiert.
 
Es wird auch ein Docker image bei dabei erstellt. `fanki/blog-backend:0.1`
 
## running the application
mit `java -jar target/quarkus-app/quarkus-run.jar`

## Start als Container
 
docker network create blog-nw

# Keycloak starten
docker run --name keycloak --network blog-nw -v ${PWD}/src/main/resources/blog-realm.json:/opt/keycloak/data/import/realm.json -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -e KC_HTTP_PORT=8180 -e KC_HOSTNAME_URL=http://keycloak:8180 -p 8180:8180 -d quay.io/keycloak/keycloak:22.0.1 start-dev --import-realm

# Datenbank starten
docker run --name blog-mysql -p 3306:3306 --network blog-nw -e MYSQL_ROOT_PASSWORD=vs4tw -e MYSQL_USER=dbuser -e MYSQL_PASSWORD=dbuser -e MYSQL_DATABASE=blogdb -d mysql:8.0

# App starten
docker run --name blog-backend --network blog-nw -e QUARKUS_DATASOURCE_JDBC_URL=jdbc:mysql://blog-mysql:3306/blogdb -e QUARKUS_DATASOURCE_USERNAME=dbuser -e QUARKUS_DATASOURCE_PASSWORD=dbuser -e QUARKUS_OIDC_AUTH_SERVER_URL=http://keycloak:8180/realms/blog -e QUARKUS_OIDC_CLIENT_ID=backend-service -e QUARKUS_OIDC_CREDENTIALS_SECRET=secret! -p 8080:8080 -d fanki/blog-backend:0.1

## Bearer Token holen
http -v --form --auth backend-service:secret! POST http://keycloak:8180/realms/blog/protocol/openid-connect/token username=alice password=alice grant_type=password

## Beispielanfragen (Sample Requests)

Hier sind einige Beispielanfragen, die mit `httpie` durchgeführt werden können, um die API zu testen:

```bash
# Beispiel für das Abrufen aller Blogs
http GET http://localhost:8080/blog Authorization:"Bearer <TOKEN>"

# Beispiel für das Hinzufügen eines neuen Blogs
http POST http://localhost:8080/blog Authorization:"Bearer <TOKEN>" \
Content-Type:application/json title="Neuer Blog" content="Inhalt des Blogs" category="Tech" authorId:=1

# Beispiel für das Abrufen aller Autoren
http GET http://localhost:8080/author Authorization:"Bearer <TOKEN>"

# Beispiel für das Hinzufügen eines neuen Autors
http POST http://localhost:8080/author Authorization:"Bearer <TOKEN>" \
Content-Type:application/json name="John Doe" biography="Softwareentwickler aus Zürich."

# Beispiel für das Löschen eines Blogs
http DELETE http://localhost:8080/blog/1 Authorization:"Bearer <TOKEN>"

# Beispiel für das Hinzufügen eines Kommentars zu einem Blog
http POST http://localhost:8080/blog/1/comments Authorization:"Bearer <TOKEN>" \
Content-Type:application/json content="Sehr hilfreicher Artikel!" blogId:=1

# Fehlerbehandlung und Response Codes

Die API verwendet folgende HTTP-Statuscodes, um den Status von Anfragen zurückzugeben:

- **200 OK**: Die Anfrage war erfolgreich.
- **201 Created**: Eine neue Ressource wurde erfolgreich erstellt.
- **400 Bad Request**: Die Anfrage war fehlerhaft oder unvollständig.
- **401 Unauthorized**: Die Anfrage erfordert eine Authentifizierung.
- **403 Forbidden**: Der Benutzer hat nicht die erforderlichen Berechtigungen.
- **404 Not Found**: Die angeforderte Ressource wurde nicht gefunden.
- **500 Internal Server Error**: Ein Serverfehler ist aufgetreten.

# Architekturüberblick

Die Blog-API ist in mehrere Schichten unterteilt:

- **Controller-Schicht (Resource-Klassen)**: Diese Schicht kümmert sich um die Verarbeitung der HTTP-Anfragen und die Rückgabe der entsprechenden Antworten. Die Controller rufen die Services auf, um die Geschäftslogik auszuführen.
- **Service-Schicht**: Die Geschäftslogik der Anwendung wird in den Service-Klassen implementiert. Diese Schicht ist für die Verarbeitung der Daten und die Anwendung der Geschäftsregeln verantwortlich.
- **Repository-Schicht**: Diese Schicht stellt den Zugriff auf die Datenbank sicher und führt CRUD-Operationen (Erstellen, Lesen, Aktualisieren, Löschen) auf den Entitäten durch.

Die Anwendung verwendet Dependency Injection (DI) für die Verwaltung der Abhängigkeiten zwischen den verschiedenen Schichten, was die Testbarkeit und Wartbarkeit des Codes verbessert.

# Swagger/OpenAPI Integration

Die API-Dokumentation ist interaktiv über die Swagger-UI verfügbar. Diese ermöglicht es, die Endpunkte der API direkt im Browser zu testen und die Struktur der API im Detail zu erkunden. Die Swagger-UI ist unter folgender URL erreichbar:

[http://localhost:8080/q/swagger-ui](http://localhost:8080/q/swagger-ui)


### MySQL Flexible Server als unsere Datenbank aufsetzen
# Umgebungsvariablen setzen
$env:RESOURCE_GROUP = "rg-blog-db"
$env:MYSQL_SERVER_NAME = "mysql-flex-blog"
$env:REGION = "westeurope"

# Anmelden und Ressourcengruppe erstellen
az login
az account set --subscription "DeineSubscriptionIDoderName"
az group create --name $env:RESOURCE_GROUP --location $env:REGION

# MySQL Flexible Server erstellen
az mysql flexible-server create --resource-group $env:RESOURCE_GROUP --name $env:MYSQL_SERVER_NAME --location $env:REGION --admin-user myadmin --admin-password MySecurePassword123! --sku-name Standard_B1ms --tier Burstable --storage-size 32 --public-access 0.0.0.0

# Firewall-Regel hinzufügen (optional)
az mysql flexible-server firewall-rule create --resource-group $env:RESOURCE_GROUP --name $env:MYSQL_SERVER_NAME --rule-name allowip --start-ip-address <gewünschte-IP> --end-ip-address <gewünschte-IP>

# MySQL-Einstellung für Keycloak-Kompatibilität setzen
az mysql flexible-server parameter set --resource-group $env:RESOURCE_GROUP --server-name $env:MYSQL_SERVER_NAME --name sql_generate_invisible_primary_key --value OFF

# Ressourcengruppe und alle zugehörigen Ressourcen löschen
az group delete --name $env:RESOURCE_GROUP --no-wait --yes


# Beispielhafte Lösung im Windows CMD für MySQL Flexible Server Setup

## a) Setup Vorbereiten
Führe die folgenden Befehle aus, um die Umgebung vorzubereiten:

```cmd
az login

az account set --subscription "Azure für Bildungseinrichtungen"

set RESOURCE_GROUP="d-rg-blog-example"
set MYSQL_SERVER_NAME="d-mysql-blog-example"
set LOCATION="germanywestcentral"
```

## b) Ressourcengruppe
Erstelle die Ressourcengruppe:

```cmd
az group create --name %RESOURCE_GROUP% --location %LOCATION%
```

## c) MySQL Flexible Server
Erstelle den MySQL Flexible Server und konfiguriere die Firewall:

```cmd
az mysql flexible-server create ^
    --name %MYSQL_SERVER_NAME% ^
    --resource-group %RESOURCE_GROUP%  ^
    --location %LOCATION%  ^
    --public-access None  ^
    --database-name test ^
    --admin-user db_adm ^
    --admin-password my$avePass9 ^
    --sku-name Standard_B1s ^
    --storage-size 32 ^
    --tier Burstable ^
    --version 8.0.21 

az mysql flexible-server firewall-rule create ^
    --resource-group %RESOURCE_GROUP% ^
    --name %MYSQL_SERVER_NAME% ^
    --rule-name allowip ^
    --start-ip-address 0.0.0.0
```

### MySQL Parameter für Keycloak Kompatibilität
Falls nötig, setze das Parameter für Keycloak:

```cmd
az mysql flexible-server parameter set --name sql_generate_invisible_primary_key --resource-group %RESOURCE_GROUP% --server-name %MYSQL_SERVER_NAME% --value OFF
```

## Optional: Zugriff von deinem System auf die DB
Falls du von deinem System aus auf die Datenbank zugreifen möchtest:

```cmd
For /f %i in ('http https://ipecho.net/plain -b') do set "MY_IP=%i"
echo %MY_IP%

az mysql flexible-server firewall-rule create ^
    --resource-group %RESOURCE_GROUP% ^
    --name %MYSQL_SERVER_NAME% ^
    --rule-name allowip ^
    --start-ip-address %MY_IP%
```

## d) Ressourcengruppe aufräumen
Um die Ressourcengruppe und alle zugehörigen Ressourcen zu löschen:

```cmd
az group delete --name %RESOURCE_GROUP%
```
### Datenbank einrichten
# Verbindung zum MySQL-Server
mysql -u simon -p -h mysql-flex-blog-new.mysql.database.azure.com --ssl

# In der MySQL-Shell:
CREATE DATABASE keycloak_db;
CREATE DATABASE blog_db;

CREATE USER 'keycloak_user'@'%' IDENTIFIED BY 'simon';
GRANT ALL PRIVILEGES ON keycloak_db.* TO 'keycloak_user'@'%';

CREATE USER 'blog_user'@'%' IDENTIFIED BY 'simon';
GRANT ALL PRIVILEGES ON blog_db.* TO 'blog_user'@'%';

USE keycloak_db;
CREATE TABLE test_table_keycloak (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);
INSERT INTO test_table_keycloak (name) VALUES ('Beispielname1'), ('Beispielname2');

USE blog_db;
CREATE TABLE test_table_blog (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100)
);
INSERT INTO test_table_blog (title) VALUES ('Blogtitel1'), ('Blogtitel2');

/* package org.acme.blog.control;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;

import org.acme.blog.boundry.AuthorResource;
import org.acme.blog.entity.Author;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class AuthorServiceTest {

    @Inject
    AuthorService authorService;

    @Inject
    AuthorResource authorResource;

    @Test
    @TestSecurity(user = "Simon", roles = { "admin" })
    @Transactional
    void testAddAuthor() {
        // Arrange
        Author author = new Author("New Author");

        // Act
        authorService.addAuthor(author);
        Response response = authorResource.getAllAuthors();
        List<Author> authors = response.readEntity(new GenericType<List<Author>>() {});

        // Assert
        assertEquals(1, authors.size());
        assertEquals("New Author", authors.get(0).getName());
    }

    @Test
    @TestSecurity(user = "Simon", roles = { "admin" })
    @Transactional
    void testUpdateAuthor() {
        // Arrange
        Author author = new Author("Old Name");
        authorService.addAuthor(author);
        Long authorId = author.getId();
        author.setName("Updated Name");

        // Act
        authorService.updateAuthor(authorId, author);
        Response response = authorResource.getAllAuthors();
        List<Author> authors = response.readEntity(new GenericType<List<Author>>() {});
        Author updatedAuthor = authors.stream()
                .filter(a -> a.getId().equals(authorId))
                .findFirst()
                .orElse(null);

        // Assert
        assertNotNull(updatedAuthor);
        assertEquals("Updated Name", updatedAuthor.getName());
    }

    @Test
    @TestSecurity(user = "Simon", roles = { "admin" })
    @Transactional
    void testDeleteAuthor() {
        // Arrange
        Author author = new Author("Author to Delete");
        authorService.addAuthor(author);
        Long authorId = author.getId();
        Response responseBefore = authorResource.getAllAuthors();
        List<Author> authorsBefore = responseBefore.readEntity(new GenericType<List<Author>>() {});

        // Act
        authorService.deleteAuthor(authorId);
        Response responseAfter = authorResource.getAllAuthors();
        List<Author> authorsAfter = responseAfter.readEntity(new GenericType<List<Author>>() {});

        // Assert
        assertEquals(authorsBefore.size() - 1, authorsAfter.size());
    }

    @Test
    @TestSecurity(user = "alice", roles = { "user" })
    @Transactional
    void testAddAuthorWithoutAuthorization() {
        // Arrange
        Author author = new Author("Unauthorized Author");

        // Act & Assert
        // Teste, dass eine SecurityException ausgelöst wird
        Exception exception = assertThrows(SecurityException.class, () -> {
            authorService.addAuthor(author);
        });

        String expectedMessage = "Access denied";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @TestSecurity(user = "alice", roles = { "user" })
    @Transactional
    void testDeleteAuthorWithoutAuthorization() {
        // Arrange
        Author author = new Author("Unauthorized Delete");
        authorService.addAuthor(author);
        Long authorId = author.getId();

        // Act & Assert
        // Teste, dass eine SecurityException ausgelöst wird
        Exception exception = assertThrows(SecurityException.class, () -> {
            authorService.deleteAuthor(authorId);
        });

        String expectedMessage = "Access denied";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
 */
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
        Author author = new Author("New Author");

        authorService.addAuthor(author);
        Response response = authorResource.getAllAuthors();
        List<Author> authors = response.readEntity(new GenericType<List<Author>>() {});

        assertEquals(1, authors.size());
        assertEquals("New Author", authors.get(0).getName());
    }

    @Test
    @TestSecurity(user = "alice", roles = { "user" })
    @Transactional
    void testAddAuthorWithoutAuthorization() {
        Author author = new Author("Unauthorized Author");

        Exception exception = assertThrows(SecurityException.class, () -> {
            authorService.addAuthor(author);
        });

        assertEquals("Access denied", exception.getMessage());
    }
}
 */
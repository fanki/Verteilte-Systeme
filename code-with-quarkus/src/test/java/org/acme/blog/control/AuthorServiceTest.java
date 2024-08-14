package org.acme.blog.control;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import org.acme.blog.boundry.AuthorResource;
import org.acme.blog.entity.Author;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class AuthorServiceTest {
    
    @Inject
    AuthorService authorService;

    @Inject
AuthorResource authorResource;

    @Test
    void testAddAuthor() {
        // Arrange
        Author author = new Author("New Author");

        // Act
        authorService.addAuthor(author);
        List<Author> authors = authorResource.getAllAuthors();

        // Assert
        assertEquals(1, authors.size());
        assertEquals("New Author", authors.get(0).getName());
    }

    @Test
    void testUpdateAuthor() {
        // Arrange
        Author author = new Author("Old Name");
        authorService.addAuthor(author);
        Long authorId = author.getId();
        author.setName("Updated Name");

        // Act
        authorService.updateAuthor(authorId, author);
        Author updatedAuthor = authorResource.getAllAuthors().stream()
                .filter(a -> a.getId().equals(authorId))
                .findFirst()
                .orElse(null);

        // Assert
        assertNotNull(updatedAuthor);
        assertEquals("Updated Name", updatedAuthor.getName());
    }

    @Test
    void testDeleteAuthor() {
        // Arrange
        Author author = new Author("Author to Delete");
        authorService.addAuthor(author);
        Long authorId = author.getId();
        List<Author> authorsBefore = authorResource.getAllAuthors();

        // Act
        authorService.deleteAuthor(authorId);
        List<Author> authorsAfter = authorResource.getAllAuthors();

        // Assert
        assertEquals(authorsBefore.size() - 1, authorsAfter.size());
    }
}

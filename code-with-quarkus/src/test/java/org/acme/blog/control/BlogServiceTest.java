/* package org.acme.blog.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.acme.blog.boundry.BlogRessource;
import org.acme.blog.dto.BlogDTO;
import org.acme.blog.entity.Author;
import org.acme.blog.entity.Blog;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

@QuarkusTest
public class BlogServiceTest {

    @Inject
    BlogService blogService;

    @Inject
    AuthorService authorService;

    @Inject
    BlogRessource blogResource;

    @Test
    @TestSecurity(user = "Simon", roles = { "admin" })
    @Transactional
    void listingAndAddingBlogs() {
        // Arrange: Erstelle einen neuen Blog und überprüfe die Anzahl der vorhandenen Blogs
        Blog blog = new Blog("Testing Blog", "This is my testing blog");
        int blogsBefore = blogService.getBlogs().size();

        // Act: Füge den Blog hinzu
        blogService.addBlog(blog);
        List<Blog> blogs = blogService.getBlogs();

        // Assert: Überprüfe, ob der Blog hinzugefügt wurde
        assertEquals(blogsBefore + 1, blogs.size());
        assertEquals(blog.getTitle(), blogs.get(blogs.size() - 1).getTitle());
    }

    @Test
    @TestSecurity(user = "alice", roles = { "user" })
    @Transactional
    void testAddBlogWithoutAuthorization() {
        // Arrange: Erstelle einen BlogDTO ohne Admin-Rechte
        BlogDTO blogDTO = new BlogDTO("Unauthorized Blog", "This should fail", "Category", 1L, "tag1");

        // Act & Assert: Teste, dass eine `SecurityException` bei nicht autorisiertem Zugriff geworfen wird
        Exception exception = assertThrows(SecurityException.class, () -> {
            blogResource.addBlog(blogDTO, null);
        });

        String expectedMessage = "Access denied";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @TestSecurity(user = "alice", roles = { "user" })
    @Transactional
    void testDeleteBlogWithoutAuthorization() {
        // Arrange: Füge einen Blog hinzu und teste das Löschen ohne Berechtigung
        Author author = new Author("Test Author");
        authorService.addAuthor(author);
        Blog blog = new Blog("Unauthorized Delete", "This should fail", "Category", author);
        blogService.addBlog(blog);

        // Act & Assert: Teste, dass eine `SecurityException` bei nicht autorisiertem Löschen geworfen wird
        Exception exception = assertThrows(SecurityException.class, () -> {
            blogResource.deleteBlog(blog.getId());
        });

        String expectedMessage = "Access denied";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}
 */
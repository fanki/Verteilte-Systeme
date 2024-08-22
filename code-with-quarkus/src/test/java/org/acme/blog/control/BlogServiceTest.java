package org.acme.blog.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.acme.blog.boundry.BlogRessource;
import org.acme.blog.entity.Blog;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class BlogServiceTest {

    @Inject
    BlogService blogService;

    @Inject
    AuthorService authorService;

    @Inject
    BlogRessource blogResource;

    @Test
    void listingAndAddingBlogs() {
        // Arrange
        Blog blog = new Blog("Testing Blog", "This is my testing blog");
        int blogsBefore;
        List<Blog> blogs;

        // Act
        blogsBefore = blogService.getBlogs().size();
        blogService.addBlog(blog);
        blogs = blogService.getBlogs();

        // Assert
        assertEquals(blogsBefore + 1, blogs.size());
        assertEquals(blog, blogs.get(blogs.size() - 1));
    }

    @Test
    void testAddBlogWithoutAuthorization() {
        // Arrange
        Blog blog = new Blog("Unauthorized Blog", "This should fail");

        // Act & Assert
        // Hier testen wir, dass der Zugriff ohne korrekte Rolle fehlschlägt
        Exception exception = assertThrows(SecurityException.class, () -> {
            blogService.addBlog(blog);
        });

        String expectedMessage = "Access denied";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testDeleteBlogWithoutAuthorization() {
        // Arrange
        Blog blog = new Blog("Unauthorized Delete", "This should fail");
        blogService.addBlog(blog);

        // Act & Assert
        // Hier testen wir, dass der Zugriff ohne korrekte Rolle fehlschlägt
        Exception exception = assertThrows(SecurityException.class, () -> {
            blogService.deleteBlog(blog.getId());
        });

        String expectedMessage = "Access denied";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}

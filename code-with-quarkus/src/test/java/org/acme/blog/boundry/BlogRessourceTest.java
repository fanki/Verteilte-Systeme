package org.acme.blog.boundry;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.acme.blog.control.AuthorService;
import org.acme.blog.control.BlogService;
import org.acme.blog.entity.Author;
import org.acme.blog.entity.Blog;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class BlogRessourceTest {
    @Inject
    BlogService blogService;

    @Inject
    AuthorService authorService;

    @Inject
    BlogRessource blogResource;

    @Test
    @Transactional
    public void testAddBlogWithAuthor() {
        // Arrange: Erstelle einen Autor und speichere ihn über den AuthorService
        Author author = new Author("Joel Bärtschi");
        authorService.addAuthor(author);

        // Act: Erstelle einen Blog und füge ihn über die BlogResource hinzu
        Blog blog = new Blog();
        blog.setTitle("Test Blog");
        blog.setContent("This is a test blog");
        blog.setAuthor(author); // Setze den Autor für den Blog

        blogResource.addBlog(blog);

        // Assert: Überprüfe, ob der Blog erfolgreich hinzugefügt wurde
        List<Blog> blogs = blogService.getBlogs();
        assertEquals(3, blogs.size());

        Blog addedBlog = blogs.get(2);
        assertEquals("Test Blog", addedBlog.getTitle());
        assertEquals("This is a test blog", addedBlog.getContent());
        assertEquals("Joel Bärtschi", addedBlog.getAuthor().getName());
    }
}

package org.acme.blog.boundry;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import org.acme.blog.control.AuthorService;
import org.acme.blog.control.BlogService;
import org.acme.blog.dto.BlogDTO;
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
        // Arrange
        Author author = new Author("Joel Bärtschi");
        authorService.addAuthor(author);

        // Act
        // Hier ist der Tag-Parameter jetzt vorhanden
        BlogDTO blogDTO = new BlogDTO("Test Blog", "This is a test blog", "General", author.getId(), "tag1, tag2");
        Response response = blogResource.addBlog(blogDTO, null); // UriInfo kann null sein
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());

        // Assert
        List<Blog> blogs = blogService.getBlogs();
        assertEquals(3, blogs.size()); // Adjust based on initial setup

        Blog addedBlog = blogs.get(2);
        assertEquals("Test Blog", addedBlog.getTitle());
        assertEquals("This is a test blog", addedBlog.getContent());
        assertEquals("Joel Bärtschi", addedBlog.getAuthor().getName());
    }

    @Test
    @Transactional
    void testDeleteBlog() {
        // Arrange
        Author author = new Author("Author to Delete");
        authorService.addAuthor(author);
        Blog blog = new Blog("Blog to Delete", "Content", "General", author);
        blogService.addBlog(blog);
        List<Blog> blogsBefore = blogService.getBlogs();

        // Act
        Response response = blogResource.deleteBlog(blog.getId());
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        // Assert
        List<Blog> blogsAfter = blogService.getBlogs();
        assertEquals(blogsBefore.size() - 1, blogsAfter.size());
    }
}

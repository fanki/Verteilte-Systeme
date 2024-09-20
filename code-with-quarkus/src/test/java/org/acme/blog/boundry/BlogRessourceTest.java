/* package org.acme.blog.boundry;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.acme.blog.control.AuthorService;
import org.acme.blog.control.BlogService;
import org.acme.blog.dto.BlogDTO;
import org.acme.blog.entity.Author;
import org.acme.blog.entity.Blog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class BlogRessourceTest {

    @Inject
    BlogService blogService;

    @Inject
    AuthorService authorService;

    @Inject
    BlogRessource blogResource;

    @BeforeEach
    @Transactional
    public void cleanDatabase() {
        // Clean up logic before each test if needed
        blogService.getBlogs().forEach(blog -> blogService.deleteBlog(blog.getId()));
        // Add logic to clean authors if needed
    }

    @Test
    @TestSecurity(user = "Simon", roles = { "admin" })
    @Transactional
    public void testAddBlogWithAuthor() {
        // Arrange
        Author author = new Author("Joel Bärtschi");
        authorService.addAuthor(author);

        BlogDTO blogDTO = new BlogDTO("Test Blog", "This is a test blog", "General", author.getId(), "tag1, tag2");

        UriInfo mockUriInfo = createMockUriInfo(); // Using Mockito to mock UriInfo

        // Act
        Response response = blogResource.addBlog(blogDTO, mockUriInfo);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus(), "Blog was not successfully created.");
        List<Blog> blogs = blogService.getBlogs();
        assertEquals(1, blogs.size(), "The number of blogs is incorrect.");

        Blog addedBlog = blogs.get(0);
        assertEquals("Test Blog", addedBlog.getTitle(), "Blog title mismatch.");
        assertEquals("This is a test blog", addedBlog.getContent(), "Blog content mismatch.");
        assertEquals("Joel Bärtschi", addedBlog.getAuthor().getName(), "Blog author mismatch.");
    }

    @Test
    @TestSecurity(user = "Simon", roles = { "admin" })
    @Transactional
    public void testDeleteBlog() {
        // Arrange
        Author author = new Author("Author to Delete");
        authorService.addAuthor(author);
        Blog blog = new Blog("Blog to Delete", "Content", "General", author);
        blogService.addBlog(blog);
        List<Blog> blogsBefore = blogService.getBlogs();

        // Act
        Response response = blogResource.deleteBlog(blog.getId());

        // Assert
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus(), "Blog was not successfully deleted.");
        List<Blog> blogsAfter = blogService.getBlogs();
        assertEquals(blogsBefore.size() - 1, blogsAfter.size(), "Number of blogs after deletion is incorrect.");
    }

    @Test
    @TestSecurity(user = "alice", roles = { "user" })
    @Transactional
    public void testAddBlogWithoutAuthorization() {
        // Arrange
        BlogDTO blogDTO = new BlogDTO("Unauthorized Blog", "This should fail", "General", 1L, "tag1");

        // Act & Assert
        Exception exception = assertThrows(SecurityException.class, () -> {
            blogResource.addBlog(blogDTO, null);
        });

        assertEquals("Access denied", exception.getMessage(), "Access denied message mismatch.");
    }

    @Test
    @TestSecurity(user = "alice", roles = { "user" })
    @Transactional
    public void testDeleteBlogWithoutAuthorization() {
        // Arrange
        Author author = new Author("Author to Delete");
        authorService.addAuthor(author);
        Blog blog = new Blog("Unauthorized Delete", "This should fail", "General", author);
        blogService.addBlog(blog);

        // Act & Assert
        Exception exception = assertThrows(SecurityException.class, () -> {
            blogResource.deleteBlog(blog.getId());
        });

        assertEquals("Access denied", exception.getMessage(), "Access denied message mismatch.");
    }
    private UriInfo createMockUriInfo() {
    UriInfo mockUriInfo = Mockito.mock(UriInfo.class);
    UriBuilder mockUriBuilder = Mockito.mock(UriBuilder.class);

    // Define the behavior of the mocked UriBuilder
    Mockito.when(mockUriBuilder.path(Mockito.anyString())).thenReturn(mockUriBuilder);

    // Define the behavior of the mocked UriInfo
    Mockito.when(mockUriInfo.getAbsolutePathBuilder()).thenReturn(mockUriBuilder);

    return mockUriInfo;
}
}
 */
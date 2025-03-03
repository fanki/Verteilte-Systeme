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
        blogService.getBlogs().forEach(blog -> blogService.deleteBlog(blog.getId()));
    }

    @Test
    @TestSecurity(user = "Simon", roles = { "admin" })
    @Transactional
    public void testAddBlogWithAuthor() {
        Author author = new Author("Joel Bärtschi");
        authorService.addAuthor(author);

        BlogDTO blogDTO = new BlogDTO("Test Blog", "This is a test blog", "General", author.getId(), "tag1, tag2");
        UriInfo mockUriInfo = createMockUriInfo();

        Response response = blogResource.addBlog(blogDTO, mockUriInfo);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        List<Blog> blogs = blogService.getBlogs();
        assertEquals(1, blogs.size());

        Blog addedBlog = blogs.get(0);
        assertEquals("Test Blog", addedBlog.getTitle());
        assertEquals("This is a test blog", addedBlog.getContent());
        assertEquals("Joel Bärtschi", addedBlog.getAuthor().getName());
    }

    private UriInfo createMockUriInfo() {
        UriInfo mockUriInfo = Mockito.mock(UriInfo.class);
        UriBuilder mockUriBuilder = Mockito.mock(UriBuilder.class);
        Mockito.when(mockUriBuilder.path(Mockito.anyString())).thenReturn(mockUriBuilder);
        Mockito.when(mockUriInfo.getAbsolutePathBuilder()).thenReturn(mockUriBuilder);
        return mockUriInfo;
    }
}
 */
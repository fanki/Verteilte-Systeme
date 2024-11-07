package org.acme.blog.control;

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
        Author author = new Author("Author for Blog");
        authorService.addAuthor(author);
        
        Blog blog = new Blog("Testing Blog", "This is my testing blog", "Category", author);
        blogService.addBlog(blog);
        
        List<Blog> blogs = blogService.getBlogs();
        assertEquals("Testing Blog", blogs.get(blogs.size() - 1).getTitle());
    }

    @Test
    @TestSecurity(user = "alice", roles = { "user" })
    @Transactional
    void testAddBlogWithoutAuthorization() {
        BlogDTO blogDTO = new BlogDTO("Unauthorized Blog", "This should fail", "Category", 1L, "tag1");

        Exception exception = assertThrows(SecurityException.class, () -> {
            blogResource.addBlog(blogDTO, null);
        });

        assertEquals("Access denied", exception.getMessage());
    }
}

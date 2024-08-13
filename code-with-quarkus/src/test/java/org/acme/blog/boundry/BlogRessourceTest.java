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
        // Arrange
        Author author = new Author("Joel Bärtschi");
        authorService.addAuthor(author);

        // Act
        Blog blog = new Blog("Test Blog", "This is a test blog", "General", author);
        blogResource.addBlog(blog);

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
        blogResource.deleteBlog(blog.getId());
        List<Blog> blogsAfter = blogService.getBlogs();

        // Assert
        assertEquals(blogsBefore.size() - 1, blogsAfter.size());
    }
}

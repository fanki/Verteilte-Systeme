package org.acme.blog.control;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.acme.blog.entity.Blog;
import org.acme.blog.entity.Comment;
import org.acme.blog.entity.Author;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class CommentServiceTest {

    @Inject
    CommentService commentService;

    @Inject
    BlogService blogService;

    @Inject
    AuthorService authorService;

    @Test
    @TestSecurity(user = "Simon", roles = { "admin" })
    @Transactional
    void testAddComment() {
        Author author = new Author("Author for Comment");
        authorService.addAuthor(author);
        
        Blog blog = new Blog("Blog for Comment", "Content", "General", author);
        blogService.addBlog(blog);
        Comment comment = new Comment("This is a comment", blog,author);

        commentService.addComment(blog, comment);
        List<Comment> comments = commentService.getCommentsByBlog(blog.getId());

        assertEquals(1, comments.size());
        Comment retrievedComment = comments.get(0);
        assertEquals("This is a comment", retrievedComment.getContent());
        assertNotNull(retrievedComment.getId());
    }

    @Test
    @TestSecurity(user = "alice", roles = { "user" })
    @Transactional
    void testAddCommentWithoutAuthorization() {
        Author author = new Author("Unauthorized Author");
        authorService.addAuthor(author);
        
        Blog blog = new Blog("Unauthorized Comment", "This should fail", "General", author);
        Comment comment = new Comment("Unauthorized Comment", blog,author);

        Exception exception = assertThrows(SecurityException.class, () -> {
            commentService.addComment(blog, comment);
        });

        assertEquals("Access denied", exception.getMessage());
    }
}

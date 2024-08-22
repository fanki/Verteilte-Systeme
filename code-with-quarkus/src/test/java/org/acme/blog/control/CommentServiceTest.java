package org.acme.blog.control;

import io.quarkus.test.junit.QuarkusTest;
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
    @Transactional
    void testAddComment() {
        // Arrange
        Author author = new Author("Author for Comment");
        authorService.addAuthor(author);
        Blog blog = new Blog("Blog for Comment", "Content", "General", author);
        blogService.addBlog(blog);
        Comment comment = new Comment("This is a comment", blog);

        // Act
        commentService.addComment(blog, comment);
        List<Comment> comments = commentService.getCommentsByBlog(blog.getId());

        // Assert
        assertEquals(1, comments.size());
        Comment retrievedComment = comments.get(0);
        assertEquals("This is a comment", retrievedComment.getContent());
        assertEquals(blog.getId(), retrievedComment.getBlog().getId());
        assertNotNull(retrievedComment.getId(), "Comment ID should be generated and not null");
    }

    @Test
    void testDeleteComment() {
        // Arrange
        Author author = new Author("Author for Comment Deletion");
        authorService.addAuthor(author);
        Blog blog = new Blog("Blog for Comment Deletion", "Content", "General", author);
        blogService.addBlog(blog);
        Comment comment = new Comment("Comment to Delete", blog);
        commentService.addComment(blog, comment);
        List<Comment> commentsBefore = commentService.getCommentsByBlog(blog.getId());

        // Act
        commentService.deleteComment(comment.getId());
        List<Comment> commentsAfter = commentService.getCommentsByBlog(blog.getId());

        // Assert
        assertEquals(commentsBefore.size() - 1, commentsAfter.size());
    }

    @Test
    void testAddCommentWithoutAuthorization() {
        // Arrange
        Blog blog = new Blog("Unauthorized Comment", "This should fail");
        Comment comment = new Comment("Unauthorized Comment", blog);

        // Act & Assert
        // Hier testen wir, dass der Zugriff ohne korrekte Rolle fehlschlägt
        Exception exception = assertThrows(SecurityException.class, () -> {
            commentService.addComment(blog, comment);
        });

        String expectedMessage = "Access denied";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testDeleteCommentWithoutAuthorization() {
        // Arrange
        Blog blog = new Blog("Unauthorized Delete", "This should fail");
        Comment comment = new Comment("Unauthorized Delete Comment", blog);
        commentService.addComment(blog, comment);

        // Act & Assert
        // Hier testen wir, dass der Zugriff ohne korrekte Rolle fehlschlägt
        Exception exception = assertThrows(SecurityException.class, () -> {
            commentService.deleteComment(comment.getId());
        });

        String expectedMessage = "Access denied";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}

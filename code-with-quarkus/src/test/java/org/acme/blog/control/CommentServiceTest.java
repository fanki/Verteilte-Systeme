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
        List<Comment> comments = commentService.getComments(blog.getId());

        // Assert
        assertEquals(1, comments.size());
        assertEquals("This is a comment", comments.get(0).getContent());
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
        List<Comment> commentsBefore = commentService.getComments(blog.getId());

        // Act
        commentService.deleteComment(comment.getId());
        List<Comment> commentsAfter = commentService.getComments(blog.getId());

        // Assert
        assertEquals(commentsBefore.size() - 1, commentsAfter.size());
    }
}

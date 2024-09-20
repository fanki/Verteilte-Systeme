package org.acme.blog.control;

import java.util.List;
import java.util.Optional;

import org.acme.blog.entity.Blog;
import org.acme.blog.entity.Comment;
import org.acme.blog.repository.CommentRepository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class CommentService {

    @Inject
    CommentRepository commentRepository;

    @Transactional
    public boolean addComment(Blog blog, Comment comment) {
        if (blog == null || comment == null) {
            return false;  // Keine Nullwerte zulassen
        }
        comment.setBlog(blog);
        commentRepository.persist(comment);
        return true;
    }

    public List<Comment> getCommentsByBlog(Long blogId) {
        return commentRepository.find("blog.id", blogId).list();
    }

    @Transactional
    public boolean deleteComment(Long id) {
        Optional<Comment> comment = Optional.ofNullable(commentRepository.findById(id));
        if (comment.isPresent()) {
            commentRepository.delete(comment.get());
            return true;
        }
        return false;
    }
}

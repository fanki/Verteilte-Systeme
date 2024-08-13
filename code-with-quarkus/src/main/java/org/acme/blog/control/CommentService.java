package org.acme.blog.control;

import java.util.List;

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
    public void addComment(Blog blog, Comment comment) {
        comment.setBlog(blog);
        commentRepository.persist(comment);
    }

    public List<Comment> getComments(Long blogId) {
        return commentRepository.find("blog.id", blogId).list();
    }

    public List<Comment> getCommentsByBlog(Long blogId) {
        return commentRepository.find("blog.id", blogId).list();
    }

    @Transactional
    public boolean deleteComment(Long id) {
        Comment comment = commentRepository.findById(id);
        if (comment != null) {
            commentRepository.delete(comment);
            return true;
        }
        return false;
    }
}

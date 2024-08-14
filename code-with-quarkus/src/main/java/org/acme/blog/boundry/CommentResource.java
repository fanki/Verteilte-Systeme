package org.acme.blog.boundry;

import java.util.List;

import org.acme.blog.control.BlogService;
import org.acme.blog.control.CommentService;
import org.acme.blog.dto.CommentDTO;
import org.acme.blog.entity.Blog;
import org.acme.blog.entity.Comment;
import org.acme.blog.repository.CommentRepository;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/comments")
public class CommentResource {

    @Inject
    CommentService commentService;

    @Inject
    BlogService blogService;

    @Inject
    CommentRepository commentRepository;

    @GET
    public List<Comment> getAllComments() {
        return commentRepository.listAll();
    }

    @POST
    @Path("/{blogId}")
    public Response addComment(@PathParam("blogId") Long blogId, @Valid CommentDTO commentDTO) {
        Blog blog = blogService.findById(blogId);
        if (blog == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Blog not found").build();
        }
        Comment comment = new Comment(commentDTO.content(), blog);
        commentService.addComment(blog, comment);
        return Response.status(Response.Status.CREATED).entity(comment).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteComment(@PathParam("id") long id) {
        boolean deleted = commentService.deleteComment(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("Comment not found").build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

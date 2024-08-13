package org.acme.blog.boundry;

import java.util.List;

import org.acme.blog.control.BlogService;
import org.acme.blog.control.CommentService;
import org.acme.blog.entity.Blog;
import org.acme.blog.entity.Comment;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("comments")
public class CommentResource {
    @Inject
    CommentService commentService;

    @Inject
    BlogService blogService;

    @GET
    public Response getComments(@QueryParam("blogId") Long blogId) {
        List<Comment> comments = commentService.getComments(blogId);
        if (comments.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No comments found for the given blog").build();
        }
        return Response.ok(comments).build();
    }

    @POST
    @Path("/{blogId}")
    public Response addComment(@PathParam("blogId") Long blogId, Comment comment) {
        Blog blog = blogService.findById(blogId);
        if (blog == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Blog not found").build();
        }
        commentService.addComment(blog, comment);
        return Response.status(Response.Status.CREATED).entity(comment).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteComment(@PathParam("id") long id) {
        commentService.deleteComment(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

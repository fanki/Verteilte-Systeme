package org.acme.blog.boundry;

import java.util.List;

import org.acme.blog.control.BlogService;
import org.acme.blog.control.CommentService;
import org.acme.blog.dto.CommentDTO;
import org.acme.blog.entity.Author;
import org.acme.blog.entity.Blog;
import org.acme.blog.entity.Comment;
import org.acme.blog.repository.AuthorRepository;
import org.acme.blog.repository.CommentRepository;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/blogs/{blogId}/comments")
public class CommentResource {

    @Inject
    CommentService commentService;

    @Inject
    BlogService blogService;

    @Inject
    AuthorRepository authorRepository;

    @Inject
    CommentRepository commentRepository;

    @GET
    @PermitAll
    public Response getCommentsByBlog(@PathParam("blogId") Long blogId) {
        Blog blog = blogService.findById(blogId);
        if (blog == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Blog not found").build();
        }
        List<Comment> comments = commentService.getCommentsByBlog(blogId);
        if (comments.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No comments found").build();
        }
        return Response.ok(comments).build();
    }

    @POST
    @RolesAllowed({"Admin", "Author"})
    public Response addComment(@PathParam("blogId") Long blogId, @Valid CommentDTO commentDTO, @Context UriInfo uriInfo) {
        Blog blog = blogService.findById(blogId);
        if (blog == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Blog not found").build();
        }

        Author author = authorRepository.findById(commentDTO.authorId());
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Author not found").build();
        }

        Comment comment = new Comment(commentDTO.content(), blog, author);
        commentService.addComment(blog, comment);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(comment.getId())).build())
                       .entity(comment)
                       .build();
    }

    @DELETE
    @Path("/{commentId}")
    @RolesAllowed("Admin")
    public Response deleteCommentById(@PathParam("commentId") long commentId) {
        boolean deleted = commentService.deleteComment(commentId);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("Comment not found").build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

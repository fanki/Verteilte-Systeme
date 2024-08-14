package org.acme.blog.boundry;

import java.util.List;

import org.acme.blog.control.BlogService;
import org.acme.blog.control.CommentService;
import org.acme.blog.dto.BlogDTO;
import org.acme.blog.dto.CommentDTO;
import org.acme.blog.entity.Blog;
import org.acme.blog.entity.Comment;
import org.acme.blog.entity.Author;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/blog")
public class BlogRessource {

    @Inject
    BlogService blogService;

    @Inject
    CommentService commentService;
    

    @GET
    public Response getBlogs(@QueryParam("authorId") Long authorId, @QueryParam("title") String title) {
        List<Blog> blogs = blogService.getBlogsFiltered(authorId, title);
        if (blogs.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No blogs found").build();
        }
        return Response.ok(blogs).build();
    }

    @POST
    public Response addBlog(@Valid BlogDTO blogDTO, @Context UriInfo uriInfo) {
        Author author = blogService.findAuthorById(blogDTO.authorId()); 
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Author not found").build();
        }
        Blog blog = new Blog(blogDTO.title(), blogDTO.content(), blogDTO.category(), author, null); 
        blogService.addBlog(blog);
        return Response.status(Response.Status.CREATED).entity(blog).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBlog(@PathParam("id") long id) {
        blogService.deleteBlog(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @POST
    @Path("/{blogId}/comments")
    public Response addComment(@PathParam("blogId") Long blogId, @Valid CommentDTO commentDTO) {
        Blog blog = blogService.findById(blogId);
        if (blog == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Blog not found").build();
        }
        Comment comment = new Comment(commentDTO.content(), blog);
        commentService.addComment(blog, comment);
        return Response.status(Response.Status.CREATED).entity(comment).build();
    }

    @GET
    @Path("/{blogId}/comments")
    public Response getComments(@PathParam("blogId") Long blogId) {
        List<Comment> comments = commentService.getCommentsByBlog(blogId);
        if (comments.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No comments found").build();
        }
        return Response.ok(comments).build();
    }

    @DELETE
    @Path("/{blogId}/comments/{id}")
    public Response deleteComment(@PathParam("id") Long id) {
        boolean deleted = commentService.deleteComment(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).entity("Comment not found").build();
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/category")
    public Response getBlogsByCategory(@QueryParam("category") String category) {
        List<Blog> blogs = blogService.getBlogsByCategory(category);
        if (blogs.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No blogs found for the given category").build();
        }
        return Response.ok(blogs).build();
    }
}

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

@Path("blog")
public class BlogRessource {
    @Inject
    BlogService blogService;

    @Inject
    CommentService commentService;

    @GET
    public Response getBlogs(@QueryParam("authorId") Long authorId) {
        if (authorId != null) {
            List<Blog> blogs = blogService.getBlogsByAuthor(authorId);
            if (blogs.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No blogs found for the given author").build();
            }
            return Response.ok(blogs).build();
        }
        return Response.ok(blogService.getBlogs()).build();
    }

    @POST
    public Response addBlog(Blog blog) {
        blogService.addBlog(blog);
        return Response.status(Response.Status.CREATED).entity(blog).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBlog(@PathParam("id") long id) {
        blogService.deleteBlog(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    public Response getBlogs(@QueryParam("authorId") Long authorId, @QueryParam("title") String title) {
        List<Blog> blogs = blogService.getBlogsFiltered(authorId, title);
        if (blogs.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No blogs found").build();
        }
        return Response.ok(blogs).build();
    }

    @POST
    @Path("/{blogId}/comments")
    public Response addComment(@PathParam("blogId") Long blogId, Comment comment) {
        Blog blog = blogService.findById(blogId);
        if (blog == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Blog not found").build();
        }
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

package org.acme.blog.boundry;

import java.util.List;

import org.acme.blog.control.BlogService;
import org.acme.blog.entity.Blog;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("blog")
public class BlogRessource {
    @Inject
    BlogService blogService;

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
    public Response addBlog(Blog blog){
        blogService.addBlog(blog);
        return Response.status(Response.Status.CREATED).entity(blog).build();
    }
}

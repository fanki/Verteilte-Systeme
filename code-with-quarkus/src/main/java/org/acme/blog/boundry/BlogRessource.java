package org.acme.blog.boundry;

import java.util.List;
import org.acme.blog.control.BlogService;
import org.acme.blog.dto.BlogDTO;
import org.acme.blog.entity.Blog;
import org.acme.blog.entity.Author;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@Path("/blog")
public class BlogRessource {

    @Inject
    BlogService blogService;

    @Inject
    @Channel("validation-request")
    Emitter<Blog> validationRequestEmitter;

    @GET
    @PermitAll
    public Response getBlogs(@QueryParam("authorId") Long authorId, @QueryParam("title") String title) {
        List<Blog> blogs = blogService.getBlogsFiltered(authorId, title);
        if (blogs == null || blogs.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No blogs found").build();
        }
        return Response.ok(blogs).build();
    }

    @POST
    @RolesAllowed({"Author", "Admin"})
    public Response addBlog(@Valid BlogDTO blogDTO, @Context UriInfo uriInfo) {
        Author author = blogService.findAuthorById(blogDTO.authorId()); 
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Author not found").build();
        }

        // ✅ Blog-Entity überprüfen
        Blog blog = new Blog(blogDTO.title(), blogDTO.content(), blogDTO.category(), author, false);
        blogService.addBlog(blog);

        // ✅ Blog zur Validierung an Kafka senden
        validationRequestEmitter.send(blog);

        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(blog.getId())).build())
                      .entity(blog)
                      .build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("Admin")
    public Response deleteBlog(@PathParam("id") long id) {
        Blog blog = blogService.findById(id);
        if (blog == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Blog not found").build();
        }
        blogService.deleteBlog(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @GET
    @Path("/category")
    @PermitAll
    public Response getBlogsByCategory(@QueryParam("category") String category) {
        List<Blog> blogs = blogService.getBlogsByCategory(category);
        if (blogs.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No blogs found for the given category").build();
        }
        return Response.ok(blogs).build();
    }

    // ✅ Kafka-Consumer: Erhält Validierungsergebnisse und aktualisiert den Blog-Status
    @Incoming("validation-response")
    @Transactional
    public void updateBlogStatus(Blog blog) {
        Blog existingBlog = blogService.findById(blog.getId());
        if (existingBlog != null) {
            existingBlog.setApproved(blog.isApproved());
        }
    }
}

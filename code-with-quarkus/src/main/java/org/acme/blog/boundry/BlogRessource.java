package org.acme.blog.boundry;

import java.util.List;
import java.util.Optional;

import org.acme.blog.control.BlogService;
import org.acme.blog.dto.BlogDTO;
import org.acme.blog.entity.Blog;
import org.acme.blog.messaging.ValidationRequest;
import org.acme.blog.messaging.ValidationResponse;
import org.acme.blog.repository.BlogRepository;
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

import io.quarkus.logging.Log;

@Path("/blog")
public class BlogRessource {

    @Inject
    BlogService blogService;

    @Inject
    BlogRepository blogRepository;

    @Inject
    @Channel("validation-request")
    Emitter<ValidationRequest> validationRequestEmitter;

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
    @RolesAllowed({ "Author", "Admin" })
    public Response addBlog(@Valid BlogDTO blogDTO, @Context UriInfo uriInfo) {
        if (blogDTO.authorName() == null || blogDTO.authorName().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Author name must not be blank")
                    .build();
        }

        // 🔍 Suche den Autor anhand des Namens
        Author author = blogService.findAuthorByName(blogDTO.authorName());

        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Author not found: " + blogDTO.authorName())
                    .build();
        }

        // 📌 Erstelle einen neuen Blog-Eintrag
        Blog blog = new Blog(blogDTO.title(), blogDTO.content(), blogDTO.category(), author, false);
        blogService.addBlogEntry(blog);

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

    // ✅ Kafka-Consumer: Erhält Validierungsergebnisse und aktualisiert den
    // Blog-Status
    @Incoming("validation-response")
    @Transactional
    public void updateBlogStatus(Blog blog) {
        Blog existingBlog = blogService.findById(blog.getId());
        if (existingBlog != null) {
            existingBlog.setApproved(blog.isApproved());
        }
    }

    /*
     * @Incoming("validation-response")
     * 
     * @Transactional
     * public void processValidationResponse(ValidationResponse validationResponse)
     * {
     * Log.debug("Validation Response: " + validationResponse);
     * Optional<Blog> blogOptional =
     * blogRepository.findByIdOptional(validationResponse.id());
     * 
     * if (blogOptional.isEmpty()) {
     * Log.warn("Blog post not found for validation response.");
     * return;
     * }
     * 
     * Blog blog = blogOptional.get();
     * blog.setApproved(validationResponse.valid());
     * blog.persist();
     * }
     */

}

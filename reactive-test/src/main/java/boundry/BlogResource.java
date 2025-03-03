package boundry;

import java.util.List;

import entity.Blog;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.MediaType;
import repository.BlogRepository;

@Path("/blogs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BlogResource {

    @Inject
    BlogRepository blogRepository;

    // Blog erstellen
    @POST
    @Transactional
    public Response create(@Valid Blog blog) {
        try {
            blogRepository.persist(blog);
            return Response.status(Status.CREATED).entity(blog).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error saving blog: " + e.getMessage()).build();
        }
    }

    // Alle Blogs abrufen
    @GET
    public List<Blog> getAll() {
        return blogRepository.listAll();
    }

    // Blog nach ID abrufen
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Blog blog = blogRepository.findById(id);
        if (blog == null) {
            return Response.status(Status.NOT_FOUND).entity("Blog not found with ID: " + id).build();
        }
        return Response.ok(blog).build();
    }

    // Blogs nach Autor filtern
    @GET
    @Path("/author/{author}")
    public List<Blog> getByAuthor(@PathParam("author") String author) {
        return blogRepository.findByAuthor(author);
    }

    // Blog aktualisieren
    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid Blog updatedBlog) {
        Blog existingBlog = blogRepository.findById(id);
        if (existingBlog == null) {
            return Response.status(Status.NOT_FOUND).entity("Blog not found with ID: " + id).build();
        }

        existingBlog.title = updatedBlog.title;
        existingBlog.content = updatedBlog.content;
        existingBlog.author = updatedBlog.author;
        existingBlog.publishedDate = updatedBlog.publishedDate;

        return Response.ok(existingBlog).build();
    }

    // Blog löschen
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = blogRepository.deleteById(id);
        if (!deleted) {
            return Response.status(Status.NOT_FOUND).entity("Blog not found with ID: " + id).build();
        }
        return Response.noContent().build();
    }
}

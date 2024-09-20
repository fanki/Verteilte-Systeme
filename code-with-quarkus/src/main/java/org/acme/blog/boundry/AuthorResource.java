package org.acme.blog.boundry;

import org.acme.blog.control.AuthorService;
import org.acme.blog.dto.AuthorDTO;
import org.acme.blog.entity.Author;
import org.acme.blog.repository.AuthorRepository;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Optional;

@Path("/author")
public class AuthorResource {

    @Inject
    AuthorService authorService;

    @Inject
    AuthorRepository authorRepository;

    @GET
    @Authenticated
    public Response getAllAuthors() {
        List<Author> authors = authorRepository.listAll();
        // Leere Liste ist keine Ausnahme, deshalb 200 OK mit leerer Liste
        return Response.ok(authors).build();
    }

    @POST
    @RolesAllowed("Admin")
    public Response addAuthor(@Valid AuthorDTO authorDTO, @Context UriInfo uriInfo) {
        Author author = new Author(authorDTO.name(), authorDTO.biography());
        authorService.addAuthor(author);
        return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(author.getId())).build())
                       .entity(author)
                       .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("Admin")
    public Response editAuthor(@PathParam("id") long id, @Valid AuthorDTO authorDTO) {
        Optional<Author> updatedAuthor = authorService.updateAuthor(id, new Author(authorDTO.name(), authorDTO.biography()));
        if (updatedAuthor.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("Author not found").build();
        }
        return Response.ok(updatedAuthor.get()).build();
    }
}

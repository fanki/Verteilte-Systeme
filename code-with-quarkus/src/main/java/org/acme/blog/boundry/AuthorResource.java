package org.acme.blog.boundry;

import org.acme.blog.control.AuthorService;
import org.acme.blog.dto.AuthorDTO;
import org.acme.blog.entity.Author;
import org.acme.blog.repository.AuthorRepository;

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

@Path("/author")
public class AuthorResource {

    @Inject
    AuthorService authorService;

    @Inject
    AuthorRepository authorRepository;

    @GET
    public List<Author> getAllAuthors() {
        return authorRepository.listAll();
    }

    @POST
    public Response addAuthor(@Valid AuthorDTO authorDTO, @Context UriInfo uriInfo) {
        Author author = new Author(authorDTO.name(), authorDTO.biography());
        authorService.addAuthor(author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @PUT
    @Path("/{id}")
    public Response editAuthor(@PathParam("id") long id, @Valid AuthorDTO authorDTO) {
        Author author = new Author(authorDTO.name(), authorDTO.biography());
        Author updatedAuthor = authorService.updateAuthor(id, author);
        if (updatedAuthor == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Author not found").build();
        }
        return Response.ok(updatedAuthor).build();
    }

    public Author updateAuthor(Long id, Author author) {
        Author existingAuthor = authorRepository.findById(id);
        if (existingAuthor != null) {
            existingAuthor.setName(author.getName());
            existingAuthor.setBiography(author.getBiography()); // Update Biografie
            authorRepository.persist(existingAuthor);
        }
        return existingAuthor;
    }
}

package org.acme.blog.boundry;

import org.acme.blog.control.AuthorService;
import org.acme.blog.entity.Author;
import org.acme.blog.repository.AuthorRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/author")
public class AuthorResource {
    
    @Inject
    AuthorService authorService;

     @Inject
    AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.listAll();
    }

    @GET
    public List<Author> getAuthors(){
        return authorService.getAllAuthors();
    }

    @POST
    public Response addAuthor(Author author){
        authorService.addAuthor(author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @PUT
    @Path("/{id}")
    public Response editAuthor(@PathParam("id") long id, Author author){
        Author updatedAuthor = authorService.updateAuthor(id, author);
        if (updatedAuthor == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Author not found").build();
        }
        return Response.ok(updatedAuthor).build();
    }

    @Transactional
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

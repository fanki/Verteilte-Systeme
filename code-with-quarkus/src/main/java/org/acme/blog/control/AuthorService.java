package org.acme.blog.control;

import java.util.List;

import org.acme.blog.entity.Author;
import org.acme.blog.repository.AuthorRepository;
import org.acme.blog.control.AuthorService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Dependent
public class AuthorService {

    @Inject
    AuthorRepository authorRepository;

    @Inject
    AuthorService authorService;

    public List<Author> getAllAuthors() {
        return authorRepository.listAll();
    }

    @Transactional
    public void addAuthor(Author author) {
        authorRepository.persist(author);
    }

    @Transactional
    public Author updateAuthor(Long id, Author author) {
        Author existingAuthor = authorRepository.findById(id);
        if (existingAuthor != null) {
            existingAuthor.setName(author.getName());
            authorRepository.persist(existingAuthor);
        }
        return existingAuthor;
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") long id) {
        authorService.deleteAuthor(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}

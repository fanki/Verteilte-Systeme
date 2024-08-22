package org.acme.blog.control;

import org.acme.blog.entity.Author;
import org.acme.blog.repository.AuthorRepository;
import org.acme.blog.control.AuthorService;

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

    @Transactional
    public void deleteAuthor(long id) {
        Author author = authorRepository.findById(id);
        if (author != null) {
            authorRepository.delete(author);
        }
    }

    @Transactional
    public Author findById(Long id) {
        return authorRepository.findById(id);
    }
}

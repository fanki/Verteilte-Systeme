package org.acme.blog.control;

import java.util.List;

import org.acme.blog.entity.Author;
import org.acme.blog.repository.AuthorRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class AuthorService {

    @Inject
    AuthorRepository authorRepository;

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
}

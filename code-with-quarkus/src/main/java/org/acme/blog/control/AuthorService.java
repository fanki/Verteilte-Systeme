package org.acme.blog.control;

import org.acme.blog.entity.Author;
import org.acme.blog.repository.AuthorRepository;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Optional;

@Dependent
public class AuthorService {

    @Inject
    AuthorRepository authorRepository;

    @Transactional
    public void addAuthor(Author author) {
        if (author.getName() == null || author.getName().isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be null or empty");
        }
        authorRepository.persist(author);
    }

    @Transactional
    public Optional<Author> updateAuthor(Long id, Author updatedAuthor) {
        Author existingAuthor = authorRepository.findById(id);
        if (existingAuthor != null) {
            boolean updated = false;

            // Nur bei wirklicher Änderung speichern
            if (!existingAuthor.getName().equals(updatedAuthor.getName())) {
                existingAuthor.setName(updatedAuthor.getName());
                updated = true;
            }

            if (updated) {
                authorRepository.persist(existingAuthor);
            }
            return Optional.of(existingAuthor);
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteAuthor(long id) {
        Author author = authorRepository.findById(id);
        if (author != null) {
            authorRepository.delete(author);
            return true;
        }
        return false;
    }

    @Transactional
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(authorRepository.findById(id));
    }
}

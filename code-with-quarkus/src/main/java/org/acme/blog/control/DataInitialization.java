package org.acme.blog.control;

import org.acme.blog.entity.Author;
import org.acme.blog.entity.Blog;
import org.acme.blog.entity.Comment;
import org.acme.blog.repository.AuthorRepository;
import org.acme.blog.control.BlogService;
import org.acme.blog.control.CommentService;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class DataInitialization {

    private static final Logger LOG = Logger.getLogger(DataInitialization.class);

    @Inject
    BlogService blogService;

    @Inject
    CommentService commentService;

    @Inject
    AuthorRepository authorRepository;

    @Transactional
    public void init(@Observes StartupEvent event) {
        // Initial Authors
        Author author1 = new Author("John Doe", "A seasoned blogger and tech enthusiast.");
        Author author2 = new Author("Jane Smith", "An experienced writer with a passion for travel.");

        authorRepository.persist(author1);
        authorRepository.persist(author2);

        LOG.info("Authors initialized.");

        // Initial Blogs
        Blog[] initialBlogs = {
            new Blog("Blog Nummer 1", "Blog let's go!", "Tech", author1),
            new Blog("Blog Nummer 2", "Hurra!", "Travel", author2),
            new Blog("Blog Nummer 3", "A new blog about Quarkus.", "Tech", author1),
            new Blog("Blog Nummer 4", "Exploring new places.", "Travel", author2)
        };

        for (Blog blog : initialBlogs) {
            try {
                blogService.addBlog(blog);
                LOG.infof("Blog '%s' erfolgreich erstellt.", blog.getTitle());
            } catch (Exception e) {
                LOG.errorf("Fehler beim Erstellen des Blogs '%s': %s", blog.getTitle(), e.getMessage());
            }
        }

        // Initial Comments
        Comment[] initialComments = {
            new Comment("Great blog on tech!", initialBlogs[0]),
            new Comment("Very informative.", initialBlogs[2]),
            new Comment("I loved the travel tips.", initialBlogs[1]),
            new Comment("Looking forward to more posts!", initialBlogs[3])
        };

        for (Comment comment : initialComments) {
            try {
                commentService.addComment(comment.getBlog(), comment);
                LOG.infof("Kommentar '%s' erfolgreich hinzugefügt.", comment.getContent());
            } catch (Exception e) {
                LOG.errorf("Fehler beim Hinzufügen des Kommentars '%s': %s", comment.getContent(), e.getMessage());
            }
        }

        LOG.info("Kommentare initialisiert.");
        LOG.info("Dateninitialisierung abgeschlossen.");
    }
}

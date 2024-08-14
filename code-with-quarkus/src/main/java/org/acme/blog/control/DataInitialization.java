package org.acme.blog.control;

import java.util.List;

import org.acme.blog.entity.Author;
import org.acme.blog.entity.Blog;
import org.acme.blog.entity.Comment;
import org.acme.blog.entity.Tag;
import org.acme.blog.repository.AuthorRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class DataInitialization {

    private static final Logger LOG = Logger.getLogger(DataInitialization.class);

    @Inject
    BlogService blogService;

    @Inject
    CommentService commentService;

    @Inject
    TagService tagService;

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

        // Initial Tags
        Tag tagTech = new Tag("Tech");
        Tag tagTravel = new Tag("Travel");

        tagService.addTag(tagTech);
        tagService.addTag(tagTravel);

        LOG.info("Tags initialized.");

        // Initial Blogs
        Blog[] initialBlogs = {
            new Blog("Blog Nummer 1", "Blog let's go!", "Tech", author1, List.of(tagTech)),
            new Blog("Blog Nummer 2", "Hurra!", "Travel", author2, List.of(tagTravel)),
            new Blog("Blog Nummer 3", "A new blog about Quarkus.", "Tech", author1, List.of(tagTech)),
            new Blog("Blog Nummer 4", "Exploring new places.", "Travel", author2, List.of(tagTravel))
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

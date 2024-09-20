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
        try {
            initAuthors();
            initTags();
            initBlogsAndComments();
            LOG.info("Dateninitialisierung abgeschlossen.");
        } catch (Exception e) {
            LOG.error("Fehler bei der Dateninitialisierung: " + e.getMessage(), e);
            throw e; // Optional: Die Ausnahme erneut werfen, um sicherzustellen, dass die
                     // Transaktion zurückgerollt wird
        }
    }

    private void initAuthors() {
        if (authorRepository.findAll().list().isEmpty()) {
            Author author1 = new Author("John Doe", "A seasoned blogger and tech enthusiast.");
            Author author2 = new Author("Jane Smith", "An experienced writer with a passion for travel.");

            authorRepository.persist(author1);
            authorRepository.persist(author2);

            LOG.info("Authors initialized.");
        } else {
            LOG.info("Authors already initialized.");
        }
    }

    private void initTags() {
        if (tagService.getAllTags().isEmpty()) {
            Tag tagTech = new Tag("Tech");
            Tag tagTravel = new Tag("Travel");

            tagService.addTag(tagTech);
            tagService.addTag(tagTravel);

            LOG.info("Tags initialized.");
        } else {
            LOG.info("Tags already initialized.");
        }
    }

    private void initBlogsAndComments() {
        if (blogService.getBlogs().isEmpty()) {
            // Initial Blogs
            Blog[] initialBlogs = {
                    new Blog("Blog Nummer 1", "Blog let's go!", "Tech", findAuthorByName("John Doe"),
                            List.of(findTagByName("Tech"))),
                    new Blog("Blog Nummer 2", "Hurra!", "Travel", findAuthorByName("Jane Smith"),
                            List.of(findTagByName("Travel"))),
                    new Blog("Blog Nummer 3", "A new blog about Quarkus.", "Tech", findAuthorByName("John Doe"),
                            List.of(findTagByName("Tech"))),
                    new Blog("Blog Nummer 4", "Exploring new places.", "Travel", findAuthorByName("Jane Smith"),
                            List.of(findTagByName("Travel")))
            };

            for (Blog blog : initialBlogs) {
                blogService.addBlog(blog);
                LOG.infof("Blog '%s' erfolgreich erstellt.", blog.getTitle());
            }
            // Initial Comments
            Comment[] initialComments = {
                    new Comment("Great blog on tech!", initialBlogs[0], findAuthorByName("John Doe")),
                    new Comment("Very informative.", initialBlogs[2], findAuthorByName("John Doe")),
                    new Comment("I loved the travel tips.", initialBlogs[1], findAuthorByName("Jane Smith")),
                    new Comment("Looking forward to more posts!", initialBlogs[3], findAuthorByName("Jane Smith"))
            };

            for (Comment comment : initialComments) {
                commentService.addComment(comment.getBlog(), comment);
                LOG.infof("Kommentar '%s' erfolgreich hinzugefügt.", comment.getContent());
            }

            LOG.info("Blogs und Kommentare initialisiert.");
        } else {
            LOG.info("Blogs and Comments already initialized.");
        }
    }

    private Author findAuthorByName(String name) {
        return authorRepository.find("name", name).firstResultOptional()
                .orElseThrow(() -> new IllegalStateException("Autor " + name + " nicht gefunden"));
    }

    private Tag findTagByName(String name) {
        return tagService.getAllTags().stream()
                .filter(tag -> tag.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Tag " + name + " nicht gefunden"));
    }
}

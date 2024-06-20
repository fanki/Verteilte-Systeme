package org.acme.blog.control;

import org.acme.blog.entity.Blog;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DataInitialization {
    
@Inject
BlogService blogService;

    @Transactional
    public void init(@Observes StartupEvent event) {
        // Initialize Data (only if there is no Data around)
        var blog1 = new Blog("Blog Nummer 1", "Blog let's go!");
        var blog2 = new Blog("Blog Nummer 2", "Hurra!");

        blogService.addBlog(blog1);
        blogService.addBlog(blog2);
    }
}

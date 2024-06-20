package org.acme.blog.control;

import java.util.List;

import org.acme.blog.entity.Blog;
import org.acme.blog.repository.BlogRepository;

import io.quarkus.logging.Log;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class BlogService {
    @Inject
    BlogRepository blogRepository;

    public List<Blog> getBlogs() {
        var blogs = blogRepository.listAll();
        Log.info("Returning " + blogs.size() + " blogs");
        return blogs;
    }

    public List<Blog> getBlogsByAuthor(Long authorId) {
        return blogRepository.find("author.id", authorId).list();
    }

    @Transactional
    public void addBlog(Blog blog) {
        Log.info("Adding blog " + blog.getTitle());
        blogRepository.persist(blog);
    }
}

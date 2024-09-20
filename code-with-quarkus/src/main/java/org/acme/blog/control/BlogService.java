package org.acme.blog.control;

import java.util.List;
import java.util.Optional;

import org.acme.blog.entity.Author;
import org.acme.blog.entity.Blog;
import org.acme.blog.repository.AuthorRepository;
import org.acme.blog.repository.BlogRepository;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class BlogService {

    @Inject
    BlogRepository blogRepository;

    @Inject
    AuthorRepository authorRepository;

    public List<Blog> getBlogs() {
        var blogs = blogRepository.listAll();
        Log.info("Returning " + blogs.size() + " blogs");
        return blogs;
    }

    public List<Blog> getBlogsByAuthor(Long authorId) {
        return blogRepository.find("author.id", authorId).list();
    }

    public List<Blog> getBlogsByCategory(String category) {
        return blogRepository.find("category", category).list();
    }

    @Transactional
    public void addBlog(Blog blog) {
        Log.info("Adding blog " + blog.getTitle());
        blogRepository.persist(blog);
    }

    @Transactional
    public boolean deleteBlog(long id) {
        Blog blog = blogRepository.findById(id);
        if (blog != null) {
            blogRepository.delete(blog);
            return true; // Rückgabe des Erfolgs
        } else {
            Log.info("Blog with ID " + id + " not found");
            return false; // Rückgabe bei Nichterfolg
        }
    }

    public List<Blog> getBlogsFiltered(Long authorId, String title) {
        if (authorId != null && title != null) {
            return blogRepository.find("author.id = ?1 and title like ?2", authorId, "%" + title + "%").list();
        } else if (authorId != null) {
            return blogRepository.find("author.id", authorId).list();
        } else if (title != null) {
            return blogRepository.find("title like ?1", "%" + title + "%").list();
        } else {
            return blogRepository.listAll();
        }
    }

    public Blog findById(Long id) {
        return blogRepository.findById(id);
    }

    @Transactional
    public Author findAuthorById(Long id) {
        return authorRepository.findById(id);
    }
}

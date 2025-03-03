package repository;

import entity.Blog;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class BlogRepository implements PanacheRepository<Blog> {

    public Blog findByTitle(String title) {
        return find("title", title).firstResult();
    }

    public boolean deleteByTitle(String title) {
        return delete("title", title) > 0;
    }

    public List<Blog> findByAuthor(String author) {
        return list("author", author);
    }
}

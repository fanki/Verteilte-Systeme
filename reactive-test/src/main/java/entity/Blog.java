package entity;

import java.time.LocalDate;
import java.util.Objects;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "blogs")
public class Blog extends PanacheEntity {

    @NotBlank(message = "Title must not be blank")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    @Column(nullable = false)
    public String title;

    @NotBlank(message = "Content must not be blank")
    @Size(max = 10000, message = "Content cannot exceed 10,000 characters")
    @Column(nullable = false, length = 10000)
    public String content;

    @NotBlank(message = "Author must not be blank")
    @Size(max = 50, message = "Author name cannot exceed 50 characters")
    @Column(nullable = false)
    public String author;

    @NotNull(message = "Published date must not be null")
    @Column(nullable = false)
    public LocalDate publishedDate;

    // Standard Constructor
    public Blog() {}

    // Custom Constructor
    public Blog(String title, String content, String author, LocalDate publishedDate) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.publishedDate = publishedDate;
    }

    // Utility methods (e.g., find by title)
    public static Blog findByTitle(String title) {
        return find("title", title).firstResult();
    }

    public static boolean deleteByTitle(String title) {
        return delete("title", title) > 0;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", publishedDate=" + publishedDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return Objects.equals(id, blog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

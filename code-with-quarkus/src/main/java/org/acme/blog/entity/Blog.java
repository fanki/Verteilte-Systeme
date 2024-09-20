package org.acme.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Titel darf nicht null sein.")
    @Size(min = 5, message = "Titel muss mindestens 5 Zeichen lang sein.")
    private String title;

    @NotBlank(message = "Inhalt darf nicht leer sein.")
    private String content;

    @Size(max = 100, message = "Kategorie darf nicht länger als 100 Zeichen sein.")
    private String category;

    @ManyToOne
    @NotNull(message = "Autor darf nicht null sein.")
    private Author author;

    @ManyToMany
    @JoinTable(
      name = "Blog_Tags",
      joinColumns = @JoinColumn(name = "blog_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    public Blog(String title, String content, String category, Author author, List<Tag> tags) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    public Blog(String title, String content, String category, Author author) {
        this(title, content, category, author, null);
    }

    public Blog(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

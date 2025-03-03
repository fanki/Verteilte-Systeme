package org.acme.blog.entity;

import jakarta.persistence.*;
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

    // ✅ NEU: Status der Validierung
    private boolean approved = false;

    public Blog(String title, String content, String category, Author author, List<Tag> tags, boolean approved) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.author = author;
        this.approved = approved;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    public Blog(String title, String content, String category, Author author, boolean approved) {
        this(title, content, category, author, null, approved);
    }

    public Blog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // ✅ Getter und Setter für den neuen `approved`-Status
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Long getId() {
        return id;
    }
    
}

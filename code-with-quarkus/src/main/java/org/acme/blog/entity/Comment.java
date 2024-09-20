package org.acme.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Kommentar darf nicht leer sein.")
    @Size(max = 1000, message = "Kommentar darf maximal 1000 Zeichen lang sein.")
    private String content;

    @ManyToOne
    @NotNull(message = "Blog darf nicht null sein.")
    private Blog blog;

    @ManyToOne
    @NotNull(message = "Author darf nicht null sein.")
    private Author author;

    // Constructor, der auch den Author akzeptiert
    public Comment(String content, Blog blog, Author author) {
        this.content = content;
        this.blog = blog;
        this.author = author;
    }

}

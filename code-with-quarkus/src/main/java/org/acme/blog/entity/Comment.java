package org.acme.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Kommentar darf nicht leer sein.")
    private String content;
    
    @ManyToOne
    @NotNull(message = "Blog darf nicht null sein.")
    private Blog blog;
    
    public Comment() {
    }
    
    public Comment(String content, Blog blog) {
        this.content = content;
        this.blog = blog;
    }
}

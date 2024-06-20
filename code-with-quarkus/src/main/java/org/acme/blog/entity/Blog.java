package org.acme.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Blog {
    @Id 
    @GeneratedValue
    private Long id;
    private String title;
    private String content;

    @ManyToOne
    public Author author;
    
    public Blog(String title, String content) {
        this.title = title;
        this.content = content;
    }
    
    
    public Blog(String title, String content, Author author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
    
}

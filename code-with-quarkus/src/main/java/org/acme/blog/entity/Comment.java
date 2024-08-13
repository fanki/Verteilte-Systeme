package org.acme.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String content;
    
    @ManyToOne
    private Blog blog;
    
    public Comment() {
    }
    
    public Comment(String content, Blog blog) {
        this.content = content;
        this.blog = blog;
    }
}

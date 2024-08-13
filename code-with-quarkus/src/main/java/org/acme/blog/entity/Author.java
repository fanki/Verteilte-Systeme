package org.acme.blog.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Author {
    @Id 
    @GeneratedValue
    private Long id;
    private String name;
    private String biography;

    public Author(){
        
    }
    public Author(String name){
        this.name = name;
    }

    public Author(String name, String biography){
        this.name = name;
        this.biography = biography;
    }
}

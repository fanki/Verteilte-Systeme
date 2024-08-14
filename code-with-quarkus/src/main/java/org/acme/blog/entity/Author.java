package org.acme.blog.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Author {
    @Id 
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Name darf nicht leer sein.")
    @Size(min = 2, message = "Name sollte mindestens 2 Zeichen enthalten.")
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

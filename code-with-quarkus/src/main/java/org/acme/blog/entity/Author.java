package org.acme.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id") // Gleichheit nur anhand der ID
public class Author {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Strategie für die ID-Generierung
    private Long id;

    @NotBlank(message = "Name darf nicht leer sein.")
    @Size(min = 2, message = "Name sollte mindestens 2 Zeichen enthalten.")
    private String name;

    @Size(max = 500, message = "Biografie sollte nicht länger als 500 Zeichen sein.") // Validierung für die Biografie
    private String biography;

    // Weitere Attribute wie birthdate und email können hier hinzugefügt werden

    public Author() {
        // Standard-Konstruktor
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(String name, String biography) {
        this.name = name;
        this.biography = biography;
    }
}

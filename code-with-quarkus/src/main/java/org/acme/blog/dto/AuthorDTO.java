package org.acme.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorDTO(
    @NotBlank(message = "Der Name darf nicht leer sein und muss zwischen 2 und 50 Zeichen lang sein.") 
    @Size(min = 2, max = 50, message = "Der Name muss zwischen 2 und 50 Zeichen lang sein.") 
    String name,

    @Size(max = 500, message = "Die Biografie darf nicht länger als 500 Zeichen sein.") 
    String biography
) {}

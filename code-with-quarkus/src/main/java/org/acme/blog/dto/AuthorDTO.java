package org.acme.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorDTO(
    @NotBlank @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters long") String name,
    @Size(max = 500, message = "Biography must be less than 500 characters long") String biography
) { }

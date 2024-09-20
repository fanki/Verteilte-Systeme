package org.acme.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TagDTO(
    @NotBlank(message = "Tag name cannot be blank")
    @Size(min = 1, max = 30, message = "Tag name must be between 1 and 30 characters long")
    String name
) { }

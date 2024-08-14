package org.acme.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record BlogDTO(
    @NotBlank @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters long") String title,
    @NotBlank @Size(min = 10, message = "Content must be at least 10 characters long") String content,
    @Size(max = 50, message = "Category must be less than 50 characters long") String category,
    @NotNull Long authorId,
    @Pattern(regexp = "^[a-zA-Z0-9-_ ]+$", message = "Tags can only contain letters, numbers, hyphens, underscores, and spaces") String tags
) { }

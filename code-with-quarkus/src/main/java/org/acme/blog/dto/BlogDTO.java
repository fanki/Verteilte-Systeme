package org.acme.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public record BlogDTO(
    @NotBlank(message = "Title is mandatory and cannot be blank")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters long")
    String title,

    @NotBlank(message = "Content is mandatory and cannot be blank")
    @Size(min = 10, message = "Content must be at least 10 characters long")
    String content,

    @Size(max = 50, message = "Category must be less than 50 characters long")
    String category,

    @NotNull(message = "Author ID is required and cannot be null")
    Long authorId,

    @Pattern(regexp = "^[a-zA-Z0-9-_ ]+$", message = "Tags can only contain letters, numbers, hyphens, underscores, and spaces")
    @Size(max = 100, message = "Tags must be less than 100 characters long")
    String tags
) { }

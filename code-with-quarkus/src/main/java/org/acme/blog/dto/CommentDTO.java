package org.acme.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentDTO(
    @NotBlank(message = "Content cannot be blank")
    @Size(min = 1, max = 1000, message = "Content must be between 1 and 1000 characters long")
    String content,

    @NotNull(message = "Blog ID is required and cannot be null")
    Long blogId,

    @NotNull(message = "Author ID is required and cannot be null")
    Long authorId
) { }

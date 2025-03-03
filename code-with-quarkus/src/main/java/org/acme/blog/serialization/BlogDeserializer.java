package org.acme.blog.serialization;

import org.acme.blog.entity.Blog;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class BlogDeserializer extends ObjectMapperDeserializer<Blog> {
    public BlogDeserializer() {
        super(Blog.class);
    }
}

package org.acme.blog.serialization;

import org.acme.blog.entity.Blog;
import io.quarkus.kafka.client.serialization.ObjectMapperSerializer;

public class BlogSerializer extends ObjectMapperSerializer<Blog> {
    public BlogSerializer() {
        super();
    }
}

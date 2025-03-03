package serialization;

import entity.Blog;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class BlogDeserializer extends ObjectMapperDeserializer<Blog> {
    public BlogDeserializer() {
        super(Blog.class);
    }
}

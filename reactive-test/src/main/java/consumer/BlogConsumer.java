package consumer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import entity.Blog;
import repository.BlogRepository;

@ApplicationScoped
public class BlogConsumer {

    @Inject
    BlogRepository blogRepository;

    @Incoming("blogs-incoming") // Kafka-Channel
    public void consumeBlog(Blog blog) {
        System.out.println("Received Blog: " + blog);
        
        // Persist Blog nach Empfang
        blogRepository.persist(blog);
        System.out.println("Blog saved to DB: " + blog.getTitle());
    }
}

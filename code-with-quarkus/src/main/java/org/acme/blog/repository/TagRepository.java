package org.acme.blog.repository;

import org.acme.blog.entity.Tag;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TagRepository implements PanacheRepositoryBase<Tag, Long> {
}

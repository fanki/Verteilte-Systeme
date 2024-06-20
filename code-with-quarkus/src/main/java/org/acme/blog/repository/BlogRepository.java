package org.acme.blog.repository;

import jakarta.enterprise.context.ApplicationScoped;

import org.acme.blog.entity.Blog;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class BlogRepository implements PanacheRepository<Blog> {
}

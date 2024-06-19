package org.jackson.quarkussocial.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jackson.quarkussocial.domain.model.Posts;

@ApplicationScoped
public class PostRepository implements PanacheRepository<Posts> {
}

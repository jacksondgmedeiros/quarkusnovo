package org.jackson.quarkussocial.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.jackson.quarkussocial.domain.model.Users;

//cria uma instancia dentro do repositório
@ApplicationScoped
public class UserRepository implements PanacheRepository<Users> {

}

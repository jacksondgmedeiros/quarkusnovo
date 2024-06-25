package org.jackson.quarkussocial.domain.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import org.jackson.quarkussocial.domain.model.Follower;
import org.jackson.quarkussocial.domain.model.Users;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class FollowerRepository implements PanacheRepository<Follower> {

    public boolean follows(Users follower, Users user) {
        //passando parametros para a query
        var params = Parameters.with("follower", follower).and("user", user).map();

        // query para verificar se encontrou no banco o resultado e com o Optional retorna true or false
        PanacheQuery<Follower> query = find("follower = :follower and user = :user", params);
        Optional<Follower> result = query.firstResultOptional();

        // retorno se for true
        return result.isPresent();
    }

    public List<Follower> findByUser(Long userId) {
        PanacheQuery<Follower> query = find("user.id", userId);
        return query.list();
    }

    public void deleteByFollowerAndUser(Long followerId, Long userId) {

        var params = Parameters.with("userId", userId).and("followerId", followerId).map();

        delete("follower.id = :followerId and user.id = :userId", params);
    }
}

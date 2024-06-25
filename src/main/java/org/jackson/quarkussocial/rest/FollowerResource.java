package org.jackson.quarkussocial.rest;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.h2.engine.User;
import org.jackson.quarkussocial.domain.model.Follower;
import org.jackson.quarkussocial.domain.repository.FollowerRepository;
import org.jackson.quarkussocial.domain.repository.UserRepository;
import org.jackson.quarkussocial.rest.dto.FollowerRequest;
import org.jackson.quarkussocial.rest.dto.FollowerResponse;
import org.jackson.quarkussocial.rest.dto.FolloweraPerUserResponse;

import java.util.stream.Collectors;

@Path("/users/{user_id}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

    private final FollowerRepository repository;
    private final UserRepository userRepository;

    @Inject
    public FollowerResource(FollowerRepository repository, UserRepository userRepository) {

        this.repository = repository;
        this.userRepository = userRepository;
    }

    @GET
    public Response listFollowers(@PathParam("user_id") Long user_id) {

        var user = userRepository.findById(user_id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var list = repository.findByUser(user_id);
        FolloweraPerUserResponse responseObject = new FolloweraPerUserResponse();
        responseObject.setFollowersCount(list.size());
        var followerList = list.stream().map(FollowerResponse::new).collect(Collectors.toList());

        responseObject.setContent(followerList);

        return Response.ok(responseObject).build();
    }

    @PUT
    @Transactional
    public Response followerUser(@PathParam("user_id") Long user_id, FollowerRequest request) {

        if (user_id.equals(request.getFollowerId())){
            return Response.status(Response.Status.CONFLICT).entity("Você não pode seguir a si mesmo")
                    .build();
        }

        var user = userRepository.findById(user_id);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var follower = userRepository.findById(request.getFollowerId());

        boolean follows = repository.follows(follower, user);

        if(!follows){

        var entity = new Follower();
        entity.setUser(user);
        entity.setFollower(follower);
        repository.persist(entity);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.CONFLICT).entity("Você já segue esse usuário").build();

    }

    @DELETE
    @Transactional
    public Response unfollowerUser(@PathParam("user_id") Long user_id, @QueryParam("followerId") Long followerId) {
        var user = userRepository.findById(user_id);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        repository.deleteByFollowerAndUser(followerId, user_id);

        return Response.status(Response.Status.NO_CONTENT).build();
    }

}

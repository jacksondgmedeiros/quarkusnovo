package org.jackson.quarkussocial.rest;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import oracle.jdbc.proxy.annotation.Post;
import org.jackson.quarkussocial.domain.model.Posts;
import org.jackson.quarkussocial.domain.model.Users;
import org.jackson.quarkussocial.domain.repository.FollowerRepository;
import org.jackson.quarkussocial.domain.repository.PostRepository;
import org.jackson.quarkussocial.domain.repository.UserRepository;
import org.jackson.quarkussocial.rest.dto.CreatePostRequest;
import org.jackson.quarkussocial.rest.dto.PostResponse;
import org.jboss.logging.annotations.Pos;

import java.util.List;
import java.util.stream.Collectors;

@Path("/users/{userId}/posts")
// informa que irá enviar e receber dados Json
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private UserRepository userRepository;
    private final PostRepository repository;
    private final FollowerRepository followerRepository;

    @Inject
    public PostResource(UserRepository userRepository, PostRepository repository, FollowerRepository followerRepository) {
        this.userRepository = userRepository;
        this.repository = repository;
        this.followerRepository = followerRepository;
    }

    @POST
    @Transactional
    public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request){

       Users user = userRepository.findById(userId);
       if (user == null){
           return Response.status(Response.Status.NOT_FOUND).build();
       }

        Posts post = new Posts();
        post.setTexto(request.getTexto());
        post.setUser(user);

        repository.persist(post);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listPost(@PathParam("userId") Long userId, @HeaderParam("followerId") Long followerId){

        Users user = userRepository.findById(userId);
        if (user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if(followerId == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Você esqueceu do cabeçalho para o seguidor").build();
        }

        Users follower = userRepository.findById(followerId);

        if(follower == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("seguidor inexistente").build();
        }


        boolean followes = followerRepository.follows(follower, user);
        if (!followes){
            return Response.status(Response.Status.FORBIDDEN).entity("Você não pode ver esses posts").build();
        }

        PanacheQuery<Posts> query = repository.find("user", Sort.by("dateTime", Sort.Direction.Descending), user);

        List<Posts> list = query.list();

        List<PostResponse> postResponseList = list.stream().map(PostResponse::FromEntity).collect(Collectors.toList());
        return Response.ok(postResponseList).build();
    }
}

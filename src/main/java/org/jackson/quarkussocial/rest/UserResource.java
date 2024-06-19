package org.jackson.quarkussocial.rest;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.h2.engine.User;
import org.jackson.quarkussocial.domain.model.Users;
import org.jackson.quarkussocial.domain.repository.UserRepository;
import org.jackson.quarkussocial.rest.dto.CreateUserRequest;
import org.jackson.quarkussocial.rest.dto.ResponseError;

import java.util.Set;

// Path aponta qual é a uri de acesso
@Path("/users")

// informa que irá enviar e receber dados Json
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    private final UserRepository repository;
    private final Validator validator;

    @Inject
    public UserResource(UserRepository repository, Validator validator){

        this.repository = repository;
        this.validator = validator;
    }

    // Métoto post para  criar via http
    @POST

//    quando precisa realizar metodo de escrtita no banco precisa colocar
    @Transactional
    //metodo para criar passando os dados de dentro do CreateUserRequest
    public Response createUser( CreateUserRequest userRequest){

        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(userRequest);

        if (!violations.isEmpty()){

            return ResponseError.createFromValdation(violations).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

//        inicia a instancia
        Users user = new Users();
        user.setId(userRequest.getId());
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());

        repository.persist(user);
        return Response.status(Response.Status.CREATED.getStatusCode()).entity(user).build();
    }

    @GET
    public Response lisUsers(){
        PanacheQuery<Users> query = repository.findAll();
        return Response.ok(query.list()).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteUser( @PathParam("id") Long id){
        Users user = repository.findById(id);

        if (user != null) {
            repository.delete(user);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, CreateUserRequest userData){
        Users user = repository.findById(id);
        if (user != null) {
           user.setName(userData.getName());
           user.setAge(userData.getAge());
           return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}

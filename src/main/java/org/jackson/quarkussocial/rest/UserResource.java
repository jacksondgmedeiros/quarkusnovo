package org.jackson.quarkussocial.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jackson.quarkussocial.rest.dto.CreateUserRequest;

// Path aponta qual é a uri de acesso
@Path("/users")

// informa que irá enviar e receber dados Json
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    // Métoto post para  criar via http
    @POST

    //metodo para criar passando os dados de dentro do CreateUserRequest
    public Response createUser( CreateUserRequest userRequest){

        // retorna uma Resposta passando como parametro o userRequest
        return Response.ok(userRequest).build();
    }

    @GET
    public Response lisUsers(){
        return Response.ok().build();
    }
}

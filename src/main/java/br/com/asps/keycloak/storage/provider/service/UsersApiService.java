package br.com.asps.keycloak.storage.provider.service;

import br.com.asps.keycloak.storage.provider.dto.UserResponse;
import br.com.asps.keycloak.storage.provider.dto.VerifyPasswordResponse;

import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/usuarios")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public interface UsersApiService {

    @GET
    UserResponse findByUsername(@QueryParam("userName") String userName);

    @GET
    UserResponse findByEmail(@QueryParam("email") String email);

    @POST
    @Path("/{userName}/verify-password")
    VerifyPasswordResponse verifyUserPassword(@PathParam("userName") String userName, @QueryParam("password") String password);
}
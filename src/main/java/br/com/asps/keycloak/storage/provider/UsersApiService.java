package br.com.asps.keycloak.storage.provider;

import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/users")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public interface UsersApiService {

    @GET
    @Path("/{username}")
    User getUserDetails(@PathParam("username") String username);

    @POST
    @Path("/{username}/verify-password")
    VerifyPasswordResponse verifyUserPassword(@PathParam("username") String username, String password);
}

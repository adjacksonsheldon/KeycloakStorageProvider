package br.com.asps.keycloak.storage.provider.provider;

import br.com.asps.keycloak.storage.provider.service.UsersApiService;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class RemoteUserStorageProviderFactory implements UserStorageProviderFactory<RemoteUserStorageProvider> {
    private static final String PROVIDER_NAME = "remote-storage-provider";

    @Override
    public RemoteUserStorageProvider create(KeycloakSession keycloakSession, ComponentModel componentModel) {
        return new RemoteUserStorageProvider(keycloakSession, componentModel, buildHttpClient());
    }

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }

    private UsersApiService buildHttpClient(){
        String uri = System.getenv("REMOTE_SERVICE_URI");
        Client client = ClientBuilder.newClient();

        System.out.println("Criou o client");

        WebTarget target = client.target(uri);
        System.out.println("Criou o target");

        ResteasyWebTarget rtarget = (ResteasyWebTarget)target;

        return rtarget.proxy(UsersApiService.class);
    }
}

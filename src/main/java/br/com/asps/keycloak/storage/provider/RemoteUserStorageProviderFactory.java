package br.com.asps.keycloak.storage.provider;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
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
        return new RemoteUserStorageProvider(keycloakSession, componentModel, buildHttpClient("http://192.168.1.14:8099"));
    }

    @Override
    public String getId() {
        return PROVIDER_NAME;
    }

    private UsersApiService buildHttpClient(String uri){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(uri);
        ResteasyWebTarget rtarget = (ResteasyWebTarget)target;
        return rtarget.proxy(UsersApiService.class);
    }
}

package br.com.asps.keycloak.storage.provider;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialManager;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class RemoteUserStorageProvider implements UserStorageProvider, UserLookupProvider, CredentialInputValidator {

    private final KeycloakSession session;
    private final ComponentModel model;
    private final UsersApiService usersApiService;

    public RemoteUserStorageProvider(KeycloakSession session, ComponentModel model, UsersApiService usersApiService) {
        this.session = session;
        this.model = model;
        this.usersApiService = usersApiService;
    }

    @Override
    public void close() {

    }

    @Override
    public UserModel getUserById(String id, RealmModel realmModel) {
        return null;
    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
        final var user = this.usersApiService.getUserDetails(username);

        if(nonNull(user)){
            return userModelFromUser(user, realm);
        }

        return null;
    }

    private UserModel userModelFromUser(User user, RealmModel realm){
        return new AbstractUserAdapter(session, realm, model) {
            @Override
            public String getUsername() {
                return user.getUserName();
            }
        };
    }


    @Override
    public UserModel getUserByEmail(String email, RealmModel realmModel) {
        return null;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return PasswordCredentialModel.TYPE.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        if(!supportsCredentialType(credentialType)){
            return false;
        }

        return !getUserCredentialManager().getStoredCredentialsByType(realm, user, credentialType).isEmpty();
    }

    private UserCredentialManager getUserCredentialManager() {
        return session.userCredentialManager();
    }

    @Override
    public boolean isValid(RealmModel realmModel, UserModel user, CredentialInput credentialInput) {

        final var response = usersApiService.verifyUserPassword(user.getUsername(), credentialInput.getChallengeResponse());

        if(isNull(response)){
            return false;
        }

        return response.getResult();
    }
}

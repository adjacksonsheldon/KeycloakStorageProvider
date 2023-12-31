package br.com.asps.keycloak.storage.provider.provider;

import br.com.asps.keycloak.storage.provider.dto.UserResponse;
import br.com.asps.keycloak.storage.provider.dto.VerifyPasswordResponse;
import br.com.asps.keycloak.storage.provider.service.UsersApiService;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.UserCredentialStore;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.adapter.AbstractUserAdapter;
import org.keycloak.storage.user.UserLookupProvider;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class RemoteUserStorageProvider implements UserStorageProvider, UserLookupProvider, CredentialInputValidator {
    private KeycloakSession session;
    private ComponentModel model;
    private UsersApiService usersService;

    public RemoteUserStorageProvider(KeycloakSession session, ComponentModel model, UsersApiService usersService) {
        this.session = session;
        this.model = model;
        this.usersService = usersService;
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub

    }

    @Override
    public UserModel getUserById(String id, RealmModel realm) {
        StorageId storageId = new StorageId(id);
        String username = storageId.getExternalId();

        return getUserByUsername(username, realm);
    }

    @Override
    public UserModel getUserByUsername(String userName, RealmModel realm) {
        UserResponse userResponse = usersService.findByUsername(userName);

        if (nonNull(userResponse)) {
            return createUserModel(userName, realm);
        }

        return null;
    }

    private UserModel createUserModel(String username, RealmModel realm) {
        return new AbstractUserAdapter(session, realm, model) {
            @Override
            public String getUsername() {
                return username;
            }
        };
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
        UserResponse userResponse = usersService.findByEmail(email);

        if (nonNull(userResponse)) {
            return createUserModel(userResponse.getUserName(), realm);
        }

        return null;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return PasswordCredentialModel.TYPE.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        if (!supportsCredentialType(credentialType)) {
            return false;
        }
        return !getCredentialStore().getStoredCredentialsByType(realm, user, credentialType).isEmpty();
    }

    private UserCredentialStore getCredentialStore() {
        return session.userCredentialManager();
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {
        VerifyPasswordResponse verifyPasswordResponse = usersService.verifyUserPassword(user.getUsername(),
                credentialInput.getChallengeResponse());

        if (isNull(verifyPasswordResponse))
            return false;

        return verifyPasswordResponse.getResult();
    }
}
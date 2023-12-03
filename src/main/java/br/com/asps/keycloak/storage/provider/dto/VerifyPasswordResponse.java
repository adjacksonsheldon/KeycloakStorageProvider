package br.com.asps.keycloak.storage.provider.dto;

public class VerifyPasswordResponse {
    private boolean result;
    public VerifyPasswordResponse() {}

    public boolean getResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
}
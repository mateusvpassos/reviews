package br.com.mateus.crud.endpoint.domain;

public enum Role {
    ADMIN("Administrator"),
    USER("User");

    private String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

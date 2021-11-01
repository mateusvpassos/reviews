package br.com.mateus.crud.endpoint.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    @Id
    @Column(nullable = false, unique = true, length = 20)
    private String id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    public User(String id, String name, String email, String password){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User() {}

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void setId(String id) { this.id = id;}
    public void setPassword(String password) {this.password = password;}
    public void setEmail(String email) { this.email = email; }
    public void setName(String name) {
        this.name = name;
    }
}

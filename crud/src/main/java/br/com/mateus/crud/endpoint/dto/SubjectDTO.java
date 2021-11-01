package br.com.mateus.crud.endpoint.dto;

import br.com.mateus.crud.endpoint.domain.Subject;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class SubjectDTO implements Serializable {
    @NotBlank
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    public SubjectDTO(Long id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
    }
    public SubjectDTO(Subject subject){
        this.id = subject.getId();
        this.title = subject.getTitle();
        this.description = subject.getDescription();
    }

    public String getDescription() {
        return description;
    }
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}

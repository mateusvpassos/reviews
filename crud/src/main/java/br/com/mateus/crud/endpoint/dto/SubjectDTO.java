package br.com.mateus.crud.endpoint.dto;

import br.com.mateus.crud.endpoint.domain.Subject;

import java.io.Serializable;

public class SubjectDTO implements Serializable {
    private Long id;
    private String title;
    private String description;

    public SubjectDTO(Long id, String title, String description){
        super();
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
}

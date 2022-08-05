package br.com.mateus.crud.endpoint.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.google.common.base.MoreObjects;

import br.com.mateus.crud.endpoint.domain.Subject;

public class SubjectDTO implements Serializable {

    @NotBlank(message = "The title is required")
    @Size(min = 3, max = 100, message = "The title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "The description is required")
    @Size(max = 255, message = "The description must be less than 255 characters")
    private String description;

    public SubjectDTO() {
    }

    public SubjectDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public SubjectDTO(Subject subject) {
        this.title = subject.getTitle();
        this.description = subject.getDescription();
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("description", description)
                .toString();
    }

    public Subject toSubjectEntity() {
        return new Subject.Builder()
                .title(title)
                .description(description)
                .build();
    }
}

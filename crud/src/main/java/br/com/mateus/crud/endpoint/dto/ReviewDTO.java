package br.com.mateus.crud.endpoint.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.google.common.base.MoreObjects;

import br.com.mateus.crud.endpoint.domain.Review;

public class ReviewDTO implements Serializable {

    @NotBlank(message = "User Email is required")
    @Email(message = "User Email is invalid")
    private String userEmail;

    @NotBlank(message = "Subject Title is required")
    private String subjectTitle;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Rate is required")
    private short rate;

    public ReviewDTO(String userEmail, String subjectTitle, String description, Short rate) {
        this.userEmail = userEmail;
        this.subjectTitle = subjectTitle;
        this.description = description;
        this.rate = rate;
    }

    public ReviewDTO(Review review) {
        this.userEmail = review.getUser().getEmail();
        this.subjectTitle = review.getSubject().getTitle();
        this.description = review.getDescription();
        this.rate = review.getRate();
    }

    public ReviewDTO() {
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getRate() {
        return rate;
    }

    public void setRate(short rate) {
        this.rate = rate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userEmail", userEmail)
                .add("subjectTitle", subjectTitle)
                .add("description", description)
                .add("rate", rate).toString();
    }
}

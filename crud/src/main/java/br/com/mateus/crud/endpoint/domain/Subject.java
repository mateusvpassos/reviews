package br.com.mateus.crud.endpoint.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import br.com.mateus.crud.endpoint.util.GenerateSourceIdUtil;

@Entity
@Table(name = "subject")
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private long id;

    @Column(name = "source_id", unique = true, updatable = false, nullable = false)
    private String sourceId;

    @Column(nullable = false, length = 100, unique = true, name = "title")
    private String title;

    @Column(nullable = false, length = 255, name = "description")
    private String description;

    public Subject(String title, String description) {
        this.sourceId = GenerateSourceIdUtil.generateSourceId();
        this.title = title;
        this.description = description;
    }

    public Subject(Builder builder) {
        this.id = builder.id;
        this.sourceId = builder.sourceId;
        this.title = builder.title;
        this.description = builder.description;
    }

    public Subject() {
    }

    public static class Builder {
        private long id;
        private String sourceId;
        private String title;
        private String description;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Subject build() {
            this.sourceId = GenerateSourceIdUtil.generateSourceId();
            return new Subject(this);
        }
    }

    public long getId() {
        return id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("sourceId", sourceId)
                .add("title", title)
                .add("description", description)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, sourceId, title, description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Subject other = (Subject) obj;

        return Objects.equal(id, other.id)
                && Objects.equal(sourceId, other.sourceId)
                && Objects.equal(title, other.title)
                && Objects.equal(description, other.description);
    }
}

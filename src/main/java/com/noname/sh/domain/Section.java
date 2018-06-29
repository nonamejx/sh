package com.noname.sh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Section.
 */
@Entity
@Table(name = "section")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "section")
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 65535)
    @Column(name = "text", length = 65535, nullable = false)
    private String text;

    @Column(name = "audio_name")
    private String audioName;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_tag")
    private String imageTag;

    @NotNull
    @Column(name = "part_number", nullable = false)
    private Long partNumber;

    @OneToMany(mappedBy = "section")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Question> questions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Section text(String text) {
        this.text = text;
        return this;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public Section audioName(String audioName) {
        this.audioName = audioName;
        return this;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Section imageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    public String getImageTag() {
        return imageTag;
    }

    public void setImageTag(String imageTag) {
        this.imageTag = imageTag;
    }

    public Section imageTag(String imageTag) {
        this.imageTag = imageTag;
        return this;
    }

    public Long getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(Long partNumber) {
        this.partNumber = partNumber;
    }

    public Section partNumber(Long partNumber) {
        this.partNumber = partNumber;
        return this;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Section questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Section addQuestion(Question question) {
        this.questions.add(question);
        question.setSection(this);
        return this;
    }

    public Section removeQuestion(Question question) {
        this.questions.remove(question);
        question.setSection(null);
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Section section = (Section) o;
        if (section.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), section.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Section{" +
               "id=" + getId() +
               ", text='" + getText() + "'" +
               ", audioName='" + getAudioName() + "'" +
               ", imageName='" + getImageName() + "'" +
               ", imageTag='" + getImageTag() + "'" +
               ", partNumber=" + getPartNumber() +
               "}";
    }
}

package com.noname.sh.service.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the Section entity.
 */
public class SectionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 65535)
    private String text;

    private String audioName;

    private String imageName;

    private String imageTag;

    @NotNull
    private Long partNumber;

    private Set<QuestionDTO> questions;

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

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageTag() {
        return imageTag;
    }

    public void setImageTag(String imageTag) {
        this.imageTag = imageTag;
    }

    public Long getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(Long partNumber) {
        this.partNumber = partNumber;
    }

    public Set<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(final Set<QuestionDTO> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof SectionDTO)) return false;

        final SectionDTO that = (SectionDTO) o;

        return new EqualsBuilder()
            .append(getId(), that.getId())
            .append(getText(), that.getText())
            .append(getAudioName(), that.getAudioName())
            .append(getImageName(), that.getImageName())
            .append(getImageTag(), that.getImageTag())
            .append(getPartNumber(), that.getPartNumber())
            .append(getQuestions(), that.getQuestions())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getId())
            .append(getText())
            .append(getAudioName())
            .append(getImageName())
            .append(getImageTag())
            .append(getPartNumber())
            .append(getQuestions())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("text", text)
            .append("audioName", audioName)
            .append("imageName", imageName)
            .append("imageTag", imageTag)
            .append("partNumber", partNumber)
            .append("questions", questions)
            .toString();
    }
}

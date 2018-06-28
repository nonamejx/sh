package com.noname.sh.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SectionDTO sectionDTO = (SectionDTO) o;
        if (sectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SectionDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", audioName='" + getAudioName() + "'" +
            ", imageName='" + getImageName() + "'" +
            ", imageTag='" + getImageTag() + "'" +
            ", partNumber=" + getPartNumber() +
            "}";
    }
}

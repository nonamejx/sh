package com.noname.sh.service.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the Question entity.
 */
public class QuestionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 1, max = 65535)
    private String title;

    private Long sectionId;

    private String sectionText;

    private Set<AnswerDTO> answers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionText() {
        return sectionText;
    }

    public void setSectionText(String sectionText) {
        this.sectionText = sectionText;
    }

    public Set<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(final Set<AnswerDTO> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (!(o instanceof QuestionDTO)) return false;

        final QuestionDTO that = (QuestionDTO) o;

        return new EqualsBuilder()
            .append(getId(), that.getId())
            .append(getTitle(), that.getTitle())
            .append(getSectionId(), that.getSectionId())
            .append(getSectionText(), that.getSectionText())
            .append(getAnswers(), that.getAnswers())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getId())
            .append(getTitle())
            .append(getSectionId())
            .append(getSectionText())
            .append(getAnswers())
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("title", title)
            .append("sectionId", sectionId)
            .append("sectionText", sectionText)
            .append("answers", answers)
            .toString();
    }
}

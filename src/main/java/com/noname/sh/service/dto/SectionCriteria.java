package com.noname.sh.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Section entity. This class is used in SectionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /sections?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SectionCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter text;

    private StringFilter audioName;

    private StringFilter imageName;

    private StringFilter imageTag;

    private LongFilter partNumber;

    private LongFilter questionId;

    public SectionCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getText() {
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }

    public StringFilter getAudioName() {
        return audioName;
    }

    public void setAudioName(StringFilter audioName) {
        this.audioName = audioName;
    }

    public StringFilter getImageName() {
        return imageName;
    }

    public void setImageName(StringFilter imageName) {
        this.imageName = imageName;
    }

    public StringFilter getImageTag() {
        return imageTag;
    }

    public void setImageTag(StringFilter imageTag) {
        this.imageTag = imageTag;
    }

    public LongFilter getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(LongFilter partNumber) {
        this.partNumber = partNumber;
    }

    public LongFilter getQuestionId() {
        return questionId;
    }

    public void setQuestionId(LongFilter questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "SectionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (text != null ? "text=" + text + ", " : "") +
                (audioName != null ? "audioName=" + audioName + ", " : "") +
                (imageName != null ? "imageName=" + imageName + ", " : "") +
                (imageTag != null ? "imageTag=" + imageTag + ", " : "") +
                (partNumber != null ? "partNumber=" + partNumber + ", " : "") +
                (questionId != null ? "questionId=" + questionId + ", " : "") +
            "}";
    }

}

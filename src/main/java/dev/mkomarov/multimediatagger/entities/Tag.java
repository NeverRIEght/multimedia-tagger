package dev.mkomarov.multimediatagger.entities;

import java.util.Objects;

public class Tag {
    private TagType tagType;
    private String value;

    public Tag(String value) {
        this.tagType = TagType.GENERAL;
        this.value = value;
    }

    public Tag(TagType tagType, String value) {
        this.tagType = tagType;
        this.value = value;
    }

    public TagType getTagType() {
        return tagType;
    }

    public void setTagType(TagType tagType) {
        this.tagType = tagType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return tagType.toString() + '/' + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return tagType == tag.tagType && Objects.equals(value, tag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagType, value);
    }
}

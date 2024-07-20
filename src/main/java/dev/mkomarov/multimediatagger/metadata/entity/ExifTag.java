package dev.mkomarov.multimediatagger.metadata.entity;

import java.util.Objects;

public class ExifTag {
    private final String name;
    private final String value;

    public ExifTag(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExifTag exifTag = (ExifTag) o;
        return Objects.equals(name, exifTag.name) && Objects.equals(value, exifTag.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.getClass().getSimpleName())
                .append("{")
                .append("name='").append(name).append('\'')
                .append(", value='").append(value).append('\'')
                .append('}');

        return sb.toString();
    }
}

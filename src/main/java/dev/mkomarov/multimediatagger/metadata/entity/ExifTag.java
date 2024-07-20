package dev.mkomarov.multimediatagger.metadata.entity;

public record ExifTag(String name, String value) {

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

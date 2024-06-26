package lt.esde.students.entities;

public enum TagType {
    AUTHOR,
    CLOTHES,
    CREATION_DATE,
    FANDOM,
    FAVOURITE,
    GENERAL,
    GROUP,
    LOCATION,
    MODIFICATION_DATE,
    PERSON,
    RATING,
    SOURCE;

    public TagType fromString(String tagType) {
        return TagType.valueOf(tagType.toUpperCase());
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}

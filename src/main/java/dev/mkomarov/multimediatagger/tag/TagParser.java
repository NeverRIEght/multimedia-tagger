package dev.mkomarov.multimediatagger.tag;

import dev.mkomarov.multimediatagger.json.JsonTagDeserializer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static dev.mkomarov.multimediatagger.utils.FileUtil.getFile;

public class TagParser {

    private TagParser() {
        throw new IllegalStateException("Utility class");
    }

    public static Tag parseTagText(String tagText) {
        if (tagText == null || tagText.isEmpty()) throw new RuntimeException("Tag text is empty");
        tagText = tagText.trim();

        if (!tagText.contains("/")) return new Tag(tagText);

        String[] tagParts = tagText.split("/");
        if (tagParts.length != 2) throw new RuntimeException("Invalid tag format");
        String tagType = tagParts[0];
        String tagValue = tagParts[1];

        Tag.TagType parsedTagType = parseTagType(tagType);

        return new Tag(parsedTagType, tagValue);
    }

    private static Tag.TagType parseTagType(String tagTypeString) {
        return switch (tagTypeString) {
            case "char", "character", "person", "per", "pers" -> Tag.TagType.PERSON;
            case "clothes", "clothing" -> Tag.TagType.CLOTHES;
            case "fandom", "topic" -> Tag.TagType.FANDOM;
            case "author", "creator", "auth", "aut" -> Tag.TagType.AUTHOR;
            case "source", "origin", "src" -> Tag.TagType.SOURCE;
            case "rating", "sfw", "nsfw" -> Tag.TagType.RATING;
            case "group", "grp" -> Tag.TagType.GROUP;
            case "fav", "favourite", "favorite" -> Tag.TagType.FAVOURITE;
            case "date", "creation", "created", "credate" -> Tag.TagType.CREATION_DATE;
            case "mod", "modification", "modified", "moddate" -> Tag.TagType.MODIFICATION_DATE;
            case "location", "loc" -> Tag.TagType.LOCATION;
            default -> Tag.TagType.GENERAL;
        };
    }

    public static Collection<Tag> getTagsFromFile(String path) {
        File file = getFile(path);
        return getTagsFromFile(file);
    }

    public static Collection<Tag> getTagsFromFile(File fromFile) {
        File jsonFile = getJsonMetadataFile(fromFile);
        if (!jsonFile.exists()) return Collections.emptyList();
        return JsonTagDeserializer.deserializeTagsForFile(fromFile);
    }

    public static List<Tag> getTagsFromFiles(List<File> fromFiles) {
        List<Tag> tagsList = new ArrayList<>();
        for (File file : fromFiles) {
            tagsList.addAll(getTagsFromFile(file));
        }
        return tagsList;
    }

    public static File getJsonMetadataFile(File file) {
        return new File(file.getParent() + File.separator + file.getName() + "_mtdata.json");
    }
}

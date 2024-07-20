package dev.mkomarov.multimediatagger.utils;

import dev.mkomarov.multimediatagger.entities.Tag;
import dev.mkomarov.multimediatagger.entities.TagType;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static dev.mkomarov.multimediatagger.utils.FileUtil.getFile;

public class TagUtil {

    public Tag parseTagText(String tagText) {
        if (tagText == null || tagText.isEmpty()) throw new RuntimeException("Tag text is empty");

        if (!tagText.contains("/")) return new Tag(tagText);

        String[] tagParts = tagText.split("/");
        if (tagParts.length != 2) throw new RuntimeException("Invalid tag format");
        String tagType = tagParts[0].trim();
        String tagValue = tagParts[1].trim();

        TagType parsedTagType = parseTagType(tagType);

        return new Tag(parsedTagType, tagValue);
    }

    private TagType parseTagType(String tagTypeString) {
        return switch (tagTypeString) {
            case "char", "character", "person", "per", "pers" -> TagType.PERSON;
            case "clothes", "clothing" -> TagType.CLOTHES;
            case "fandom", "topic" -> TagType.FANDOM;
            case "author", "creator", "auth", "aut" -> TagType.AUTHOR;
            case "source", "origin", "src" -> TagType.SOURCE;
            case "rating", "sfw", "nsfw" -> TagType.RATING;
            case "group", "grp" -> TagType.GROUP;
            case "fav", "favourite", "favorite" -> TagType.FAVOURITE;
            case "date", "creation", "created", "credate" -> TagType.CREATION_DATE;
            case "mod", "modification", "modified", "moddate" -> TagType.MODIFICATION_DATE;
            case "location", "loc" -> TagType.LOCATION;
            default -> TagType.GENERAL;
        };
    }

    public List<Tag> getTagsFromFile(String path) {
        File file = getFile(path);
        return getTagsFromFile(file);
    }

    public List<Tag> getTagsFromFile(File file) {
        File jsonFile = getJsonFile(file);
        if (!jsonFile.exists()) return Collections.emptyList();
//        return getTagsFromJson(jsonFile);
        return Collections.emptyList();
    }

    public List<Tag> getTagsFromFiles(List<File> files) {
        List<Tag> tagsList = new ArrayList<>();
        for (File file : files) {
            tagsList.addAll(getTagsFromFile(file));
        }
        return tagsList;
    }

//    public List<Tag> getTagsFromJson(File jsonFile) {
//        if (!jsonFile.exists()) throw new RuntimeException("File not found: " + jsonFile.getAbsolutePath());
//
//        List<Tag> tagsList = new ArrayList<>();
//
//        try (FileReader fileReader = new FileReader(jsonFile)) {
//            Gson gson = new Gson();
//            Tag[] tagsArray = gson.fromJson(fileReader, Tag[].class);
//            Collections.addAll(tagsList, tagsArray);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return tagsList;
//    }
//
//    public String serializeTagsToJson(List<Tag> tags) {
//        Gson gson = new Gson();
//        return gson.toJson(tags);
//    }

    public File getJsonFile(File file) {
        return new File(file.getParent() + File.separator + file.getName() + "_mtdata.json");
    }
}

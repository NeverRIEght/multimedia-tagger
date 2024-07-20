package dev.mkomarov.multimediatagger.json;

import dev.mkomarov.multimediatagger.entities.Tag;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Serializes tags to JSON format and saves them to a file.
 */
public class JsonTagSerializer {
    private static final String SOFTWARE_NAME = "MultimediaTagger";
    private static final String STANDARD_VERSION = "1.0";

    private JsonTagSerializer() {
    }

    /**
     * Serializes tags to JSON format and saves them to a file.
     *
     * @param tags tags to serialize
     * @param forFile file to save tags for
     */
    public static void serializeTagsForFile(List<Tag> tags, File forFile) {
        JSONObject forFileJsonObject = new JSONObject();
        forFileJsonObject.put("fileName", forFile.getName());
        forFileJsonObject.put("software", SOFTWARE_NAME);
        forFileJsonObject.put("tagSystemVersion", STANDARD_VERSION);
        forFileJsonObject.put("tags", serializeTags(tags));

        String json = getJsonString(forFileJsonObject);
        String jsonFilePath = getJsonFilePath(forFile);
        saveJsonAsFile(json, jsonFilePath);
    }

    private static String getJsonFilePath(File forFile) {
        return forFile.getName() + ".json";
    }

    private static JSONArray serializeTags(List<Tag> tags) {
        JSONArray jsonTags = new JSONArray();
        for (Tag tag : tags) {
            jsonTags.put(serializeTag(tag));
        }
        return jsonTags;
    }

    private static JSONObject serializeTag(Tag tag) {
        JSONObject jsonTag = new JSONObject();
        jsonTag.put("tagType", tag.getTagType().toString());
        jsonTag.put("value", tag.getValue());
        return jsonTag;
    }

    private static String getJsonString(JSONObject jsonObject) {
        return jsonObject.toString(4);
    }

    private static void saveJsonAsFile(String json, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
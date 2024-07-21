package dev.mkomarov.multimediatagger.json;

import dev.mkomarov.multimediatagger.entities.Tag;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

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
    public static void serializeTagsForFile(Collection<Tag> tags, File forFile) {
        JSONObject forFileJsonObject = new JSONObject();
        forFileJsonObject.put("fileName", forFile.getName());
        forFileJsonObject.put("software", SOFTWARE_NAME);
        forFileJsonObject.put("tagSystemVersion", STANDARD_VERSION);
        forFileJsonObject.put("tags", serializeTags(tags));

        String jsonFilePath = getJsonFilePath(forFile);
        saveJsonAsFile(forFileJsonObject, jsonFilePath);
    }

    private static String getJsonFilePath(File forFile) {
        return forFile.getName() + ".json";
    }

    private static JSONArray serializeTags(Collection<Tag> tags) {
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

    private static void saveJsonAsFile(JSONObject jsonObject, String filePath) {
        String jsonString = JsonUtil.getJsonAsString(jsonObject);
        saveJsonAsFile(jsonString, filePath);
    }

    private static void saveJsonAsFile(String jsonString, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(jsonString);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
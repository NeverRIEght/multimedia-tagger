package dev.mkomarov.multimediatagger.json;

import dev.mkomarov.multimediatagger.entities.Tag;
import dev.mkomarov.multimediatagger.utils.FileUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static dev.mkomarov.multimediatagger.json.JsonUtil.SOFTWARE_NAME;

/**
 * Deserializes tags from JSON file
 */
public class JsonTagDeserializer {
    private JsonTagDeserializer() {
    }

    public static Collection<Tag> deserializeTagsFromFile(File fromFile) {
        FileUtil.checkFile(fromFile);

        JSONObject jsonObject = getJsonFromFile(fromFile);

        String fileName = jsonObject.getString("fileName");

        if (!fileName.equals(fromFile.getName())) {
            throw new RuntimeException("File name in JSON does not match the file name. Expected: " + fromFile.getName() + ", actual: " + fileName);
        }

        String software = jsonObject.getString("software");

        if(!software.equals(SOFTWARE_NAME)) {
            throw new RuntimeException("Software name in JSON does not match the software name. Expected: " + SOFTWARE_NAME + ", actual: " + software);
        }

        String tagSystemVersion = jsonObject.getString("tagSystemVersion");

        if(!tagSystemVersion.equals(JsonUtil.STANDARD_VERSION)) {
            throw new RuntimeException("Tag system version in JSON does not match the tag system version. Expected: " + JsonUtil.STANDARD_VERSION + ", actual: " + tagSystemVersion);
        }

        return deserializeTags(jsonObject.getJSONArray("tags"));
    }

    private static Collection<Tag> deserializeTags(JSONArray tagsArray) {
        List<Tag> tags = new ArrayList<>();
        for (int i = 0; i < tagsArray.length(); i++) {
            JSONObject jsonObject = tagsArray.getJSONObject(i);
            tags.add(deserializeTag(jsonObject));
        }
        return tags;
    }

    private static Tag deserializeTag(JSONObject jsonObject) {
        String tagTypeString = jsonObject.getString("tagType");
        Tag.TagType tagType = Tag.TagType.valueOf(tagTypeString);
        String value = jsonObject.getString("value");
        return new Tag(tagType, value);
    }

    private static JSONObject getJsonFromFile(File fromFile) {
        try {
            String fileContent = new String(Files.readAllBytes(fromFile.toPath()));
            return new JSONObject(fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package lt.sdc.students.multimediatagger.serializer;

import lt.sdc.students.multimediatagger.metadata.exif.entities.ExifTag;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonSerializer {
    public static JSONObject serializeExifTagsToJson(List<ExifTag> tags) {
        JSONObject json = new JSONObject();

        JSONArray jsonTagsArray = new JSONArray();
        for (ExifTag tag : tags) {
            JSONObject jsonTag = new JSONObject();
            jsonTag.put("Id", tag.getId());
            jsonTag.put("Name", tag.getName());
            jsonTag.put("Writable", tag.getWritable());
            jsonTag.put("Group", tag.getGroup());
            jsonTag.put("ValuesNotes", tag.getValuesNotes());
            jsonTagsArray.put(jsonTag);
        }
        json.put("ExifTags", jsonTagsArray);

        return json;
    }

    public static void saveJsonAsFile(JSONObject json, String path) {
        saveJsonAsFile(json, new File(path));
    }

    public static void saveJsonAsFile(JSONObject json, File file) {
        if (file.isFile()) {
            throw new IllegalArgumentException("File already exists");
        }
//        if (!FileUtil.getFileExtension(file).equals("json")) {
//            throw new IllegalArgumentException("File is not a json file");
//        }
        if (!file.getAbsolutePath().endsWith(".json")) {
            throw new IllegalArgumentException("File is not a json file");
        }

        try (FileWriter fileWriter = new FileWriter(file.getAbsolutePath())){
            fileWriter.write(json.toString(1));
        } catch (IOException e) {
            throw new RuntimeException("Can not save json as file");
        }
    }
}
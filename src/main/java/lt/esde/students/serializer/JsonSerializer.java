package lt.esde.students.serializer;

import lt.esde.students.metadata.exif.entities.ExifTag;
import org.json.JSONArray;
import org.json.JSONObject;

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

        return json;
    }
}
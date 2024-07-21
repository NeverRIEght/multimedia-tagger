package dev.mkomarov.multimediatagger.json;

import org.json.JSONObject;

public class JsonUtil {

    private JsonUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getJsonAsString(JSONObject jsonObject) {
        return jsonObject.toString(4);
    }
}

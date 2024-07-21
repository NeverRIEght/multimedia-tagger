package dev.mkomarov.multimediatagger.json;

import org.json.JSONObject;

public class JsonUtil {
    public static final String SOFTWARE_NAME = "MultimediaTagger";
    public static final String STANDARD_VERSION = "1.0";

    private JsonUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getJsonAsString(JSONObject jsonObject) {
        return jsonObject.toString(4);
    }
}

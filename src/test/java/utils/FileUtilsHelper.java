package utils;

public class FileUtilsHelper {

    public static String getConfigValue(String key, String defaultValue) {
        return System.getProperty(key, defaultValue);
    }
}

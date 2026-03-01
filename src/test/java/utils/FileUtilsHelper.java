package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

/**
 * File operations for mobile automation: screenshots, config, APK path.
 */
public class FileUtilsHelper {

    private static final String SCREENSHOTS_DIR = "screenshots";
    private static final String CONFIG_FILE = "config.properties";

    /**
     * Get the project root directory.
     */
    public static Path getProjectRoot() {
        return Paths.get(System.getProperty("user.dir"));
    }

    /**
     * Get APK path from project directory or config.
     */
    public static String getApkPath(String apkName) {
        Path projectRoot = getProjectRoot();
        Path apkPath = projectRoot.resolve("apks").resolve(apkName);

        if (Files.exists(apkPath)) {
            return apkPath.toAbsolutePath().toString();
        }

        // Fallback: check if APK exists in project root
        Path rootApk = projectRoot.resolve(apkName);
        if (Files.exists(rootApk)) {
            return rootApk.toAbsolutePath().toString();
        }

        return null;
    }

    /**
     * Ensure apks directory exists and return path.
     */
    public static Path ensureApksDirectory() {
        Path apksDir = getProjectRoot().resolve("apks");
        try {
            Files.createDirectories(apksDir);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create apks directory", e);
        }
        return apksDir;
    }

    /**
     * Take screenshot and save to screenshots folder with timestamp.
     */
    public static String captureScreenshot(TakesScreenshot driver, String prefix) throws IOException {
        Path screenshotsPath = getProjectRoot().resolve(SCREENSHOTS_DIR);
        Files.createDirectories(screenshotsPath);

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = prefix + "_" + timestamp + ".png";
        File destFile = screenshotsPath.resolve(fileName).toFile();

        File srcFile = driver.getScreenshotAs(OutputType.FILE);
        Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        return destFile.getAbsolutePath();
    }

    /**
     * Load config properties from config.properties.
     */
    public static Properties loadConfig() {
        Properties props = new Properties();
        Path configPath = getProjectRoot().resolve(CONFIG_FILE);

        if (!Files.exists(configPath)) {
            return props;
        }

        try (InputStream is = Files.newInputStream(configPath)) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }
        return props;
    }

    /**
     * Get config value with default.
     */
    public static String getConfigValue(String key, String defaultValue) {
        return loadConfig().getProperty(key, defaultValue);
    }
}

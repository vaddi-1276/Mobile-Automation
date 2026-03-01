package pages;

import org.openqa.selenium.By;
import io.appium.java_client.AppiumBy;

public class youtubeLocators {

    public static final By permission_allow_button = By.id("com.android.permissioncontroller:id/permission_allow_button");
    public static final By search_button = By.id("com.google.android.youtube:id/search_button");
    public static final By search_edit_text = By.id("com.google.android.youtube:id/search_edit_text");

    public static final By search_button_accessibility_id = AppiumBy.accessibilityId("Search YouTube");
    public static final By search_edit_text_accessibility_id = AppiumBy.accessibilityId("Search");

    // UiAutomator2 selectors - more flexible, work when content-desc varies
    public static final By search_ui_selector = AppiumBy.androidUIAutomator(
            "new UiSelector().descriptionContains(\"Search\")");
    public static final By search_edit_text_ui_selector = AppiumBy.androidUIAutomator(
            "new UiSelector().resourceId(\"com.google.android.youtube:id/search_edit_text\")");
}

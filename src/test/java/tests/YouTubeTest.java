package tests;

import org.openqa.selenium.By;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.GestureActions;
import utils.FileUtilsHelper;
import pages.youtubeLocators;

import java.net.URL;
import java.time.Duration;

/**
 * YouTube.apk automation with gesture actions and file operations.
 * Place YouTube.apk in project_root/apks/ folder.
 */
public class YouTubeTest {

    AndroidDriver driver;
    GestureActions gestures;

    @BeforeClass
    public void setup() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setUdid(FileUtilsHelper.getConfigValue("device.udid", "emulator-5554"));
        options.setAutomationName("UiAutomator2");
        options.setAutoGrantPermissions(true);
        options.setNewCommandTimeout(Duration.ofSeconds(300));
        options.setAdbExecTimeout(Duration.ofSeconds(120));
        options.setAppWaitDuration(Duration.ofSeconds(60000));
        options.setAppPackage("com.google.android.youtube");
        options.setAppActivity("com.google.android.apps.youtube.app.WatchWhileActivity");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        gestures = new GestureActions(driver);
    }

    @Test
    public void searchAndScrollWithGestures() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        Thread.sleep(8000); // Allow app to fully load (YouTube can be slow on cold start)

        // Handle permission popup
        try {
            driver.findElement(youtubeLocators.permission_allow_button).click();
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("No permission popup");
        }

        // Open Search - try multiple locators (YouTube UI varies by version/region)
        boolean searchOpened = false;
        By[] searchLocators = {
                youtubeLocators.search_button_accessibility_id,
                youtubeLocators.search_edit_text_accessibility_id,
                youtubeLocators.search_ui_selector,
                youtubeLocators.search_button,
                youtubeLocators.search_edit_text
        };
        for (By locator : searchLocators) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
                searchOpened = true;
                break;
            } catch (Exception e) {
                // Try next locator
            }
        }
        if (!searchOpened) {
            throw new RuntimeException("Could not find or click search element. Try: adb shell uiautomator dump /sdcard/window_dump.xml && adb pull /sdcard/window_dump.xml to inspect UI.");
        }
        Thread.sleep(3000);

        // Enter search (try resource ID first, then UiSelector)
        try {
            driver.findElement(youtubeLocators.search_edit_text).sendKeys("Mobile Automation Course");
        } catch (Exception e) {
            driver.findElement(youtubeLocators.search_edit_text_ui_selector).sendKeys("Mobile Automation Course");
        }
        Thread.sleep(2000);
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        Thread.sleep(5000);

        // Gesture: Swipe up to scroll through results
        for (int i = 0; i < 3; i++) {
            gestures.scrollDown(1);
            Thread.sleep(1500);
        }

    }

    @Test(dependsOnMethods = "searchAndScrollWithGestures")
    public void gestureActionsDemo() throws InterruptedException {
        // Swipe left (e.g., skip video in Shorts)
        gestures.swipeLeft();
        Thread.sleep(1000);

        // Swipe up - scroll down
        gestures.swipeUp();
        Thread.sleep(1000);

        // Swipe down - scroll up
        gestures.swipeDown();
        Thread.sleep(2000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

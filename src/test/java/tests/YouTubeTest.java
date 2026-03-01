package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.GestureActions;
import utils.FileUtilsHelper;

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

        options.setAppPackage("com.google.android.youtube");
        options.setAppActivity("com.google.android.apps.youtube.app.WatchWhileActivity");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        gestures = new GestureActions(driver);
    }

    @Test
    public void searchAndScrollWithGestures() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        Thread.sleep(5000);

        // Handle permission popup
        try {
            driver.findElement(By.id("com.android.permissioncontroller:id/permission_allow_button")).click();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("No permission popup");
        }

        // Open Search
        wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Search"))).click();
        Thread.sleep(3000);

        // Enter search
        driver.findElement(By.id("com.google.android.youtube:id/search_edit_text"))
                .sendKeys("Mobile Automation Course");
        Thread.sleep(2000);
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        Thread.sleep(5000);

        // Gesture: Swipe up to scroll through results
        for (int i = 0; i < 3; i++) {
            gestures.scrollDown(1);
            Thread.sleep(1500);
        }

        // Take screenshot and save to file
        String savedPath = FileUtilsHelper.captureScreenshot(driver, "youtube_search");
        System.out.println("Screenshot saved: " + savedPath);
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
        Thread.sleep(1000);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

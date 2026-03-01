package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.GestureActions;
import utils.FileUtilsHelper;

import java.net.URL;
import java.time.Duration;

public class Mobile_Automation_Search_in_youtube {

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
    public void searchMobileAutomationCourse() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        Thread.sleep(5000);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("YouTube"))).click();
        } catch (Exception e) {
            // App may already be opened
        }

        try {
            driver.findElement(By.id("com.android.permissioncontroller:id/permission_allow_button")).click();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("No permission popup");
        }

        Thread.sleep(3000);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Search YouTube"))).click();
        } catch (Exception e) {
            wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId("Search"))).click();
        }

        Thread.sleep(3000);

        driver.findElement(By.id("com.google.android.youtube:id/search_edit_text"))
                .sendKeys("Mobile Automation Course");

        Thread.sleep(2000);
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        Thread.sleep(5000);

        // Gesture: Scroll down through results
        gestures.scrollDown(2);
        Thread.sleep(2000);

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

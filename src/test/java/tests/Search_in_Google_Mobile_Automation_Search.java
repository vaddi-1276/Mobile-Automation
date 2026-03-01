package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.AppiumBy;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.GestureActions;
import utils.FileUtilsHelper;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ChromeLocators;

import java.net.URL;
import java.time.Duration;

public class Search_in_Google_Mobile_Automation_Search {
    
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
            options.setAppPackage("com.android.chrome");
            options.setAppActivity("com.google.android.apps.chrome.Main");
        
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        gestures = new GestureActions(driver);
    }

    @Test
    public void searchInChrome() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        Thread.sleep(5000);

        // Dismiss first-run dialogs (terms, sign-in)
        try {
            driver.findElement(ChromeLocators.terms_accept).click();
            Thread.sleep(1000);
        } catch (Exception ignored) {}
        try {
            driver.findElement(ChromeLocators.signin_fre_dismiss_button).click();
            Thread.sleep(1000);
        } catch (Exception ignored) {}

        // Dismiss "Enhanced ad privacy" dialog - click "Got it"
        try {
            driver.findElement(ChromeLocators.ack_button).click();
        } catch (Exception e1) {
            try {
                driver.findElement(ChromeLocators.got_it_button).click();
            } catch (Exception ignored) {}
        }
        Thread.sleep(1000);

        // search_box_text is clickable but NOT editable (TextView). url_bar is the actual EditText.
        // Click search_box_text to open omnibox, then type into url_bar.
        var searchBox = wait.until(d -> {
            try {
                return d.findElement(ChromeLocators.search_box_text);
            } catch (Exception e) {
                return d.findElement(ChromeLocators.url_bar);
            }
        });
        searchBox.click();
        Thread.sleep(1000);

        // Type into url_bar (the actual EditText) - search_box_text cannot accept sendKeys
        var urlBar = wait.until(d -> {
            try {
                return d.findElement(ChromeLocators.url_bar);
            } catch (Exception e) {
                return d.findElement(ChromeLocators.url_bar);
            }
        });
        urlBar.sendKeys("Mobile Automation Course");
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }
}

package pages;

import org.openqa.selenium.By;
import io.appium.java_client.AppiumBy;

public class ChromeLocators {

    public static final By terms_accept = AppiumBy.id("com.android.chrome:id/terms_accept");
    public static final By signin_fre_dismiss_button = AppiumBy.id("com.android.chrome:id/signin_fre_dismiss_button");
    public static final By ack_button = AppiumBy.id("com.android.chrome:id/ack_button");
    public static final By got_it_button = AppiumBy.androidUIAutomator("new UiSelector().text(\"Got it\")");
    public static final By search_box_text = By.id("com.android.chrome:id/search_box_text");
    public static final By url_bar = By.id("com.android.chrome:id/url_bar");

}
package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;
import java.util.Objects;

/**
 * Reusable gesture actions for mobile automation (swipe, scroll, tap, long press).
 */
public class GestureActions {

    private static final String FINGER = "finger";
    private static final Duration DURATION_ZERO = Duration.ZERO;
    private static final Duration GESTURE_DURATION = Duration.ofMillis(500);

    private final AppiumDriver driver;

    public GestureActions(AppiumDriver driver) {
        this.driver = driver;
    }

    /**
     * Swipe from start point to end point.
     */
    public void swipe(int startX, int startY, int endX, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, FINGER);
        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Objects.requireNonNull(DURATION_ZERO), PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Objects.requireNonNull(GESTURE_DURATION), PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(swipe));
    }

    /**
     * Swipe left (e.g., skip video, dismiss).
     */
    public void swipeLeft() {
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.getWidth() * 0.8);
        int endX = (int) (size.getWidth() * 0.2);
        int y = size.getHeight() / 2;
        swipe(startX, y, endX, y);
    }

    /**
     * Swipe right.
     */
    public void swipeRight() {
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.getWidth() * 0.2);
        int endX = (int) (size.getWidth() * 0.8);
        int y = size.getHeight() / 2;
        swipe(startX, y, endX, y);
    }

    /**
     * Swipe up (scroll down).
     */
    public void swipeUp() {
        Dimension size = driver.manage().window().getSize();
        int x = size.getWidth() / 2;
        int startY = (int) (size.getHeight() * 0.8);
        int endY = (int) (size.getHeight() * 0.2);
        swipe(x, startY, x, endY);
    }

    /**
     * Swipe down (scroll up).
     */
    public void swipeDown() {
        Dimension size = driver.manage().window().getSize();
        int x = size.getWidth() / 2;
        int startY = (int) (size.getHeight() * 0.2);
        int endY = (int) (size.getHeight() * 0.8);
        swipe(x, startY, x, endY);
    }

    /**
     * Swipe up multiple times (scroll down through feed).
     */
    public void scrollDown(int times) {
        for (int i = 0; i < times; i++) {
            swipeUp();
            sleep(800);
        }
    }

    /**
     * Swipe down multiple times (scroll up).
     */
    public void scrollUp(int times) {
        for (int i = 0; i < times; i++) {
            swipeDown();
            sleep(800);
        }
    }

    /**
     * Tap at coordinates.
     */
    public void tap(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, FINGER);
        Sequence tap = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Objects.requireNonNull(DURATION_ZERO), PointerInput.Origin.viewport(), x, y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(tap));
    }

    /**
     * Tap on element center.
     */
    public void tap(WebElement element) {
        int x = element.getLocation().getX() + element.getSize().getWidth() / 2;
        int y = element.getLocation().getY() + element.getSize().getHeight() / 2;
        tap(x, y);
    }

    /**
     * Long press at coordinates.
     */
    public void longPress(int x, int y, Duration holdDuration) {
        Duration hold = Objects.requireNonNull(holdDuration, "holdDuration");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, FINGER);
        Sequence longPress = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Objects.requireNonNull(DURATION_ZERO), PointerInput.Origin.viewport(), x, y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Objects.requireNonNull(hold), PointerInput.Origin.viewport(), x, y))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(longPress));
    }

    /**
     * Long press on element (default 1.5 seconds).
     */
    public void longPress(WebElement element) {
        int x = element.getLocation().getX() + element.getSize().getWidth() / 2;
        int y = element.getLocation().getY() + element.getSize().getHeight() / 2;
        longPress(x, y, Objects.requireNonNull(Duration.ofMillis(1500)));
    }

    /**
     * Double tap at coordinates (e.g., YouTube like).
     */
    public void doubleTap(int x, int y) {
        tap(x, y);
        sleep(100);
        tap(x, y);
    }

    /**
     * Double tap on element.
     */
    public void doubleTap(WebElement element) {
        int x = element.getLocation().getX() + element.getSize().getWidth() / 2;
        int y = element.getLocation().getY() + element.getSize().getHeight() / 2;
        doubleTap(x, y);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted during gesture", e);
            
        }
    }
}

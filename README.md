# Mobile Automation

Appium-based Android automation framework for testing the YouTube app. Uses TestNG, gesture actions, config-driven setup, and automated screenshots.

---

## Prerequisites

| Requirement | Version / Notes |
|-------------|-----------------|
| **Java** | 11+ |
| **Maven** | 3.6+ |
| **Appium Server** | 2.x with UiAutomator2 driver |
| **Android** | Emulator or physical device with USB debugging |
| **Node.js** | Required for Appium |

---

## Project Structure

```
Mobile Automation/
├── apks/                    # Place YouTube.apk here (optional)
├── screenshots/             # Auto-generated test screenshots
├── config.properties        # Device UDID, Appium URL
├── pom.xml                  # Maven dependencies
├── testng.xml               # TestNG suite configuration
└── src/test/java/
    ├── tests/
    │   ├── FirstClass.java   # YouTube search & scroll test
    │   └── YouTubeTest.java # Search, scroll & gesture demo
    └── utils/
        ├── FileUtilsHelper.java   # Config, APK path, screenshots
        └── GestureActions.java    # Swipe, scroll, tap, long-press
```

---

## Quick Start

### 1. Install Appium

```bash
npm install -g appium
appium driver install uiautomator2
```

### 2. Start Appium Server

```bash
appium
# Server runs at http://127.0.0.1:4723
```

### 3. Start Android Emulator / Connect Device

- Start an Android emulator, or
- Connect a physical device with USB debugging enabled

Get device UDID:
```bash
adb devices
```

### 4. Configure Device

Edit `config.properties` and set your device UDID:

```properties
device.udid=emulator-5554
```

### 5. (Optional) Add YouTube APK

Place `YouTube.apk` in the `apks/` folder. If not present, the framework uses the pre-installed YouTube app.

### 6. Run Tests

```bash
mvn clean test
```

Or run a specific test class:

```bash
mvn test -Dtest=FirstClass
mvn test -Dtest=YouTubeTest
```

---

## Configuration

| Key | Description | Default |
|-----|-------------|---------|
| `device.udid` | Android device/emulator ID (`adb devices`) | `emulator-5554` |
| `appium.url` | Appium server URL | `http://127.0.0.1:4723` |

---

## Test Classes

| Class | Description |
|-------|-------------|
| **FirstClass** | Opens YouTube, searches "Mobile Automation Course", scrolls, captures screenshot |
| **YouTubeTest** | `searchAndScrollWithGestures` — search & scroll; `gestureActionsDemo` — swipe left/up/down (e.g. Shorts-style navigation) |

---

## Utils

### FileUtilsHelper

- `getConfigValue(key, default)` — Read config values
- `getApkPath(apkName)` — Resolve APK from `apks/` folder
- `captureScreenshot(driver, prefix)` — Save timestamped screenshots to `screenshots/`

### GestureActions

- `swipeLeft()` / `swipeRight()` — Horizontal swipes
- `swipeUp()` / `swipeDown()` — Vertical scroll
- `scrollDown(times)` / `scrollUp(times)` — Repeated scroll
- `tap(x, y)` / `tap(WebElement)` — Tap
- `longPress(WebElement)` — Long press
- `doubleTap(WebElement)` — Double tap (e.g. YouTube like)

---

## Screenshots

Screenshots are stored under `screenshots/` with names like:

```
youtube_search_20250219_143022.png
```

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| **Connection refused** | Ensure Appium server is running (`appium`) |
| **No device found** | Run `adb devices` and set correct `device.udid` in config |
| **YouTube not installed** | Add `YouTube.apk` to `apks/` folder |
| **Permission popup** | Framework auto-grants permissions; retry if needed |
| **Element not found** | Increase wait timeout or ensure YouTube app version is compatible |

---

## Dependencies

- **Appium Java Client** 10.0.0  
- **Selenium** 4.35.0  
- **TestNG** 7.9.0  

---

## License

Internal / project-specific use.

package logging;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.model.ScreenCapture;
import org.testng.Reporter;

/**
 * Default Test Logging class.
 */
public class Report extends Reporter {

    private static ThreadLocal<ExtentTest> currentTest = new InheritableThreadLocal<>();

    private Report() {} // Prevents accidental instantiation of a static-only class

    // Initialization method only, do not call this method outside BaseTest
    public static void setCurrentTest(ExtentTest test) {
        currentTest.set(test);
    }

    public static void log(String s) {
        logInfo(s, true);
    }

    public static void logError(String s) { logError(s, true); }

    public static void logError(String e, boolean logToStandardOutput) {
        if (logToStandardOutput) {
            System.out.println(e);
        }
        currentTest.get().fail(e);
    }

    public static void logInfo(String s, boolean logToStandardOutput) {
        if (logToStandardOutput) {
            System.out.println(s);
        }
        currentTest.get().info(s);
    }

    public static void addScreenshot(String base64Screen) {
        if (!base64Screen.startsWith("data:")) {
            base64Screen = "data:image/png;base64," + base64Screen;
        }
        currentTest.get().log(Status.FAIL,
                ScreenCapture
                        .builder()
                        .base64(base64Screen)
                        .title("Click the above button to see the screenshot.")
                        .build());
    }

}

package testrunner;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import logging.Report;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.*;

import asserts.Validate;
import driver.DriverFactory;
import driver.DriverManager;
import listeners.TestMethodListener;
import logging.Logging;
import utils.Architecture;
import utils.Context;
import utils.PropertyReader;

import java.lang.reflect.Method;

@Listeners({TestMethodListener.class})
public abstract class BaseTest implements Logging {
	
	protected static DriverManager driverManager;
	protected static final ExtentReports extent = new ExtentReports();
	protected ThreadLocal<ExtentTest> te = new ThreadLocal<>();
	protected Context context = new Context();
	protected Validate validate;
	public static PropertyReader properties = new PropertyReader();

	private static String driverName;
	private boolean isApi = false;

	@BeforeSuite
	@Parameters({"driverName"})
	protected void initializeSuite(@Optional("CHROME") String driverName) {
		ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
		extent.attachReporter(spark);
		extent.setAnalysisStrategy(AnalysisStrategy.TEST);

		extent.setSystemInfo("Operating System", Architecture.getOperatingSystemType().name());
		extent.setSystemInfo("64 bits", String.valueOf(Architecture.is64bits()));
		extent.setSystemInfo("Driver", driverName);

		if (driverName.toUpperCase().contains("API")) {
			this.setApi(true);
		}

		setDriverName(driverName);

		properties = new PropertyReader();
	}

	@AfterSuite
	protected void flush() {
		extent.flush();
	}

	@BeforeMethod(alwaysRun = true)
	protected void setup(Method m) {
		// Init extent reports single test
		te.set(extent.createTest(m.getName()));

		Report.setCurrentTest(te.get());

		if (this.isApi()) {
			validate = new Validate();
		} else {
			initializeDriverManager(getDriverName());
			validate = new Validate(this.getDriver());
			driverManager.getDriver().navigate().to(context.getContextUrl());
		}
	}

	@AfterMethod
	protected void cleanUp() {
		if (!this.isApi()) {
			driverManager.quitDriver();
		}

		extent.addTestRunnerOutput(Reporter.getOutput());
	}

	/** Separating initialization of DriverManager because it's a static class and can have strange behaviors while running tests in parallel.
	 *  The synchronized Keyword is to prevent 2 Threads from calling a static class at the same time.
	 *  Read more about synchronization in <a>https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
	 */
	private synchronized void initializeDriverManager(String driverName) {
		if (null == driverManager) {
			driverManager = DriverFactory.valueOf(driverName).getDriverManager();
		} else {
			driverManager.getDriver();
		}
	}

	public Validate getValidator() {
		return validate;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	protected WebDriver getDriver() {
		return driverManager.getDriver();
	}

	public boolean isApi() { return isApi; }

	public void setApi(boolean api) { isApi = api; }
}

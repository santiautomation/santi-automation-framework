package testrunner;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import asserts.Validate;
import driver.DriverFactory;
import driver.DriverManager;
import listeners.TestMethodListener;
import logging.Logging;
import utils.Constants;

@Listeners({TestMethodListener.class})
public abstract class BaseTest implements Logging {
	
	protected static DriverManager driverManager;
	protected Validate validate;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({"driverName"})
	protected void setup(@Optional("CHROME") String driverName) {		
		initializeDriverManager(driverName);
		
		validate = new Validate(this.getDriver());		
		driverManager.getDriver().navigate().to(Constants.getContextUrl());
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
	
	
	@AfterMethod
	protected void cleanUp() {
		driverManager.quitDriver();
		Reporter.getOutput();
	}
	
	protected WebDriver getDriver() {
		return driverManager.getDriver();
	}
}

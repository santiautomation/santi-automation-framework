package tests.blaze;

import logging.Report;
import org.testng.annotations.Test;

import pages.blaze.BlazeBasePage;
import testrunner.BaseTest;

import java.util.concurrent.atomic.AtomicInteger;

public class SampleTest extends BaseTest {

	@Test(description = "Sample test -- This should call the Listener and print the description !")
	public void thisisasampletest() throws RuntimeException {
		Report.logInfo("Starting Sample test - class one first method in the list", true);

		BlazeBasePage basePage = new BlazeBasePage(getDriver());

		validate.assertTrue(getDriver().getCurrentUrl().contains("demoblaze.com"), "Validating the current URL.");

		basePage.testClick();
		Report.log("finishing");
		Report.logError("This is an error message, ");
		basePage.typeContactEmail("randommail@fakeemail.com");
	}

	private static AtomicInteger exec = new AtomicInteger(1);

	@Test(description = "Test 2 of parallel tests", dependsOnMethods = { "thisisasampletest" }, alwaysRun = false)
	public void testTwo() throws Exception {
		Report.logInfo("Second seq test - class one second method in the list", false);
		BlazeBasePage basePage = new BlazeBasePage(getDriver());

		// Failing the test on purpose, just once,
		// to validate that the Retry Implementation is working
		if (exec.get() == 1) {
			exec.incrementAndGet();
			validate.assertTrue(!getDriver().getCurrentUrl().contains("demoblaze.com"), "Validating the current URL.");
		} else {
			validate.assertTrue(getDriver().getCurrentUrl().contains("demoblaze.com"), "Validating the current URL.");
		}

	}

}
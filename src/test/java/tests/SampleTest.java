package tests;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;

import pages.BlazeBasePage;
import testrunner.BaseTest;

public class SampleTest extends BaseTest {

	@Test(description = "Sample test -- This should call the Listener and print the description !")
	public void thisisasampletest() throws Exception {
		System.out.println("Class SampleTest, method 'thisisasampletest'");
		
		Reporter.log("Starting Sample test - class one first method in the list");

		BlazeBasePage basePage = new BlazeBasePage(getDriver());

		validate.assertTrue(getDriver().getCurrentUrl().contains("demoblaze.com"), "Validating the current URL.");

		basePage.testClick();
		basePage.typeContactEmail("randommail@fakeemail.com");

		Reporter.log("Test 'SampleTest' finished");

		Reporter.getOutput();
	}

	
	@Test(description = "Test 2 of parallel tests", dependsOnMethods = { "thisisasampletest" }, alwaysRun = false)
	public void testTwo() throws Exception {
		System.out.println("Class SampleTest, method 'testTwo'");
		Reporter.log("Starting Sample test - class one second method in the list");
		BlazeBasePage basePage = new BlazeBasePage(getDriver());
		validate.assertTrue(getDriver().getCurrentUrl().contains("demoblaze.com"), "Validating the current URL.");
	}

	@Test(description = "Test 3 of parallel tests", dependsOnMethods = { "testTwo" })
	public void testThree() throws Exception {
		System.out.println("Class SampleTest, method 'testThree'");
		Reporter.log("Starting Sample test - class one third method in the list");
		BlazeBasePage basePage = new BlazeBasePage(getDriver());
		validate.assertTrue(!getDriver().getCurrentUrl().contains("demoblaze.com"), "Validating the current URL.");
	}
	
}
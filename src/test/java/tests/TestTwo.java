package tests;

import logging.Report;
import org.testng.annotations.Test;

import pages.BlazeBasePage;
import testrunner.BaseTest;

public class TestTwo extends BaseTest {

	@Test(description = "Test 2 of parallel tests - diff class")
	public void testOneClassTwo() throws Exception {
		
		BlazeBasePage basePage = new BlazeBasePage(getDriver());

		Report.logInfo("Class 2 test 1", true);
		validate.assertTrue(getDriver().getCurrentUrl().contains("demoblaze.com"), "Validating the current URL.");
	}
	
}

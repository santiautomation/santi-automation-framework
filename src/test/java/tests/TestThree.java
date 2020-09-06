package tests;

import org.testng.annotations.Test;

import pages.BlazeBasePage;
import testrunner.BaseTest;

public class TestThree extends BaseTest {

	@Test(description = "Test 3 of parallel tests")
	public void testOneClassThree() throws Exception {
		BlazeBasePage basePage = new BlazeBasePage(getDriver());
		validate.assertTrue(getDriver().getCurrentUrl().contains("demoblaze.com"), "Validating the current URL.");
	}
	
}

package utilities;

import java.net.MalformedURLException;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.google.common.collect.Multimap;

import pages.BlazeBasePage;
import testrunner.BaseTest;

public class CheckLinks extends BaseTest {

	@Test
	public void checkLinks() throws MalformedURLException {
		BlazeBasePage basePage = new BlazeBasePage(getDriver());

		Multimap<String, String> results = basePage.checkLinks();
		
		validate.assertTrue(results.isEmpty(), "Validating if there are any broken links in the Website");
		
		if (!results.isEmpty()) {
			results.forEach((from, brokenLink) -> Reporter.log("URL: " + from + " - Broken Link: " + brokenLink));
		} else {
			Reporter.log("No Broken Links detected.");
		}
	}
}

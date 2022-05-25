package asserts;

import logging.Report;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import utils.Screenshots;

/**
 * Wrapper Class over TestNG's Soft Assert, that allows performing some additional tasks, such as Logging each validation
 *  
 * 
 * @author Santiago Schumacher
 *
 */
public class Validate extends SoftAssert {

	private Screenshots screen = new Screenshots();
	private WebDriver d;
	
	public Validate(WebDriver driver) {
		this.d = driver;
	}

	@Override
    public void onAssertFailure(IAssert<?> a, AssertionError ex) {
		this.printError();
	}

	public void printError() {
		if (d != null && screen != null) { // To prevent API tests from attempting to take screenshots.
			Report.addScreenshot(screen.takeScreenshot(d));
		}
	}
	
	@Override
	public void assertTrue(final boolean condition, final String message) {
		Report.log(message + " - " + condition);
		super.assertTrue(condition, message);
	}

	public void assertStringContains(String fullMessage, String contains) {
		String msg = String.format("Asserting that the String %s contains the message %s", fullMessage, contains);
		Report.log(msg);

		if (fullMessage.contains(contains)) {
			super.assertTrue(true, msg);
		} else {
			super.assertTrue(false, msg);
		}
	}
}

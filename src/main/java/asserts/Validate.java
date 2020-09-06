package asserts;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

import utils.Screenshots;

public class Validate extends SoftAssert {

	private Screenshots screen = new Screenshots();
	private WebDriver d;
	
	public Validate(WebDriver driver) {
		this.d = driver;
	}

	@Override
    public void onAssertFailure(IAssert<?> a, AssertionError ex) {
		screen.takeScreenshot(d);
	}
	
	@Override
	public void assertTrue(final boolean condition, final String message) {
		Reporter.log(message + " - " + condition);
		super.assertTrue(condition, message);
	}
}

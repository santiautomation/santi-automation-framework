package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import seleniumutils.customelements.Element;
import seleniumutils.customelements.LogElement;

public class BlazeBasePage extends BasePage {

	public BlazeBasePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(how = How.PARTIAL_LINK_TEXT, using = "Contact")
	private LogElement contactLink;
	
	@FindBy(id = "recipient-email")
	private LogElement contactEmailInput;
	
	public void testClick() {
		contactLink.click();
	}
	
	public void typeContactEmail(String email) {
		w.waitForElementToBeVisible(driver, contactEmailInput.getWrappedElement(), 2);
		contactEmailInput.sendKeys(email);
	}
}

package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import seleniumutils.Waits;
import seleniumutils.customelements.ElementFactory;

/**
 * All your Page Objects should extend BasePage. It contains a generic constructor so its subclasses only need to call super(driver) in its construtors
 * instead of calling PageFactory directly.
 * Also acts as wrapper for all the Page Navigation methods (forward, back, refresh).
 * Contains generic methods that can be applied in any Page Object.
 * 
 * @author Santiago Schumacher
 *
 */
public abstract class BasePage {

	protected WebDriver driver;
	protected Waits w = new Waits();
	
	public BasePage(WebDriver driver) {
		setDriver(driver);
		ElementFactory.initElements(driver, this);
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	protected WebDriver getDriver() {
		return this.driver;
	}
	
	public void navigateBack() {
		driver.navigate().back();
	}
	
	public void navigateForward() {
		driver.navigate().forward();
	}
	
	public void refresh() {
		driver.navigate().refresh();
	}
	
}

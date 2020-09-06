package seleniumutils.customelements;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.testng.Reporter;

public class Element implements LogElement {

	private final WebElement ele;
	private String by;
	private String locator;
	
	public Element(final WebElement ele) {
		this.ele = ele;
		this.setElementName();
	}

	private void setElementName() {
		if (this.ele != null && StringUtils.isBlank(by) || StringUtils.isBlank(locator)) {
			String temp = this.getWrappedElement().toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + ""); 
			this.setBy("BY: '" + temp.split(":")[0] + "' - ");
			this.setLocator("LOCATOR: '" + temp.split(":")[1] + "' - ");
		}
		
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public String getLocator() {
		return locator;
	}

	public void setLocator(String locator) {
		this.locator = locator;
	}

	@Override
	public WebElement getWrappedElement() {
		return ele;
	}
	
	public void click() {
		Reporter.log("Clicking on Element: " + this.getBy() + this.getLocator());
		
		ele.click();
	}
	
	public String getText() {
		Reporter.log("Element text: " + ele.getText());
		return ele.getText();
	}
	
	public void sendKeys(CharSequence... keysToSend) {
		Reporter.log("Sending Keys: " + StringUtils.join(keysToSend));
		ele.sendKeys(keysToSend);
	}
	
	@Override
	public void submit() {
		Reporter.log("Submitting form.");
		ele.submit();
	}

	@Override
	public void clear() {
		Reporter.log("Clearing Input");
		ele.clear();
	}

	@Override
	public String getTagName() {
		Reporter.log("Tag Name: " + ele.getTagName());
		return ele.getTagName();
	}

	@Override
	public String getAttribute(String name) {
		Reporter.log("Attribute: " + name + " value is: " + name);
		return ele.getAttribute(name);
	}

	@Override
	public boolean isSelected() {
		return ele.isSelected();
	}

	@Override
	public boolean isEnabled() {
		return ele.isEnabled();
	}

	@Override
	public List<WebElement> findElements(By by) {
		return ele.findElements(by);
	}

	@Override
	public WebElement findElement(By by) {
		return ele.findElement(by);
	}

	@Override
	public boolean isDisplayed() {
		return ele.isDisplayed();
	}

	@Override
	public Point getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension getSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle getRect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCssValue(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Coordinates getCoordinates() {
		// TODO Auto-generated method stub
		return null;
	}

}

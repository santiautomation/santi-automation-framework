package seleniumutils;

import java.time.Duration;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Helper class to create all the different waits.
 * You should not call driver directly in the Page Objects, instead, call a method from this class. 
 * 
 * @author Santiago Schumacher
 *
 */
public class Waits {
	
	private static <T> T waitForCondition(WebDriver driver, Integer timeOutSeconds, Function<WebDriver, T> condition) {
		return new WebDriverWait(driver, Duration.ofSeconds(timeOutSeconds))
                .ignoring(WebDriverException.class) // Ignores all the Selenium related exceptions, ie: TimeOutException, StaleElementException, UnreachableBrowserException etc.
                .until(condition);
	}

	public void waitForElementToBeVisible(WebDriver driver, final WebElement element, int seconds) {
		waitForCondition(driver, seconds, d -> element.isDisplayed());
	}
	
	public void waitForElementToDisappear(WebDriver driver, final WebElement element, int seconds) {
		waitForCondition(driver, seconds, d -> !element.isDisplayed());
	}
	
	public WebElement findElement(WebDriver driver, final String locator, int seconds) {
		By by = By.xpath(locator);
		return findElement(driver, by, seconds);
	}
	
	public WebElement findElement(WebDriver driver, By locator, int seconds) {
		return waitForCondition(driver, seconds, ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public boolean isTextPresent(WebDriver driver, String text, int seconds) {
		return waitForCondition(driver, seconds, d -> d.getPageSource().contains(text));
	}
	
}

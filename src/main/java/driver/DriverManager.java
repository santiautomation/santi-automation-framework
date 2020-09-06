package driver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import logging.Logging;
import utils.Architecture;

public abstract class DriverManager implements Logging {

	protected ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
	protected abstract WebDriver createDriver();
	
	public void quitDriver() {
		if (null != drivers.get()) {
			try {
				getLogger().info("Closing Selenium WebDriver instance in Thread: " + Thread.currentThread().getId());
				drivers.get().quit();
				drivers.remove();
			} catch (Exception e) {
				getLogger().error("Unable to gracefully quit WebDriver.", e);
			}
			
		}
	}
	
	public WebDriver getDriver() {
		if (null == drivers.get()) {
			drivers.set(this.createDriver());
		}
		drivers.get().manage().timeouts().implicitlyWait(1L, TimeUnit.SECONDS);
		
		return drivers.get();
	}
	
	protected boolean is64bits() {
		return Architecture.is64bits();
	}
}

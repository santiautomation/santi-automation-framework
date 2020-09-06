package driver;

import static io.github.bonigarcia.wdm.DriverManagerType.FIREFOX;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FirefoxDriverManager extends DriverManager {

	@Override
	protected WebDriver createDriver() {
		getLogger().info("Initializing Firefox Driver  in Thread: " + Thread.currentThread().getId());
		if (!super.is64bits()) {
			WebDriverManager.getInstance(FIREFOX).arch32().setup();
		} else {
			WebDriverManager.getInstance(FIREFOX).setup();
		}
		
		return new FirefoxDriver();
	}

}

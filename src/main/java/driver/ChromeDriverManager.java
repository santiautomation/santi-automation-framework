package driver;

import static io.github.bonigarcia.wdm.DriverManagerType.CHROME;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ChromeDriverManager extends DriverManager {
	
	@Override
	protected WebDriver createDriver() {
		getLogger().info("Initializing Chrome Driver in Thread: " + Thread.currentThread().getId());
		if (!super.is64bits()) {
			WebDriverManager.getInstance(CHROME).arch32().setup();
		} else {
			WebDriverManager.getInstance(CHROME).setup();
		}
		
		return new ChromeDriver(getChromeOptions());
	}

	

	private ChromeOptions getChromeOptions() {
		ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-features=EnableEphemeralFlashPermission");
        options.addArguments("--disable-infobars");

		return options;
	}

}

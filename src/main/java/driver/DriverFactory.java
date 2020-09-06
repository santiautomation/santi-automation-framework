package driver;

import org.openqa.selenium.WebDriver;

public enum DriverFactory {

	CHROME { 
		@Override
		public DriverManager getDriverManager() {
			return new ChromeDriverManager();
		}
	},
	FIREFOX {
		@Override
		public DriverManager getDriverManager() {
			return new FirefoxDriverManager();
		}
	};
	
	public abstract DriverManager getDriverManager();
}

package utils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import logging.Logging;

public class Screenshots implements Logging {

    public String takeScreenshot(WebDriver d) {
    	getLogger().debug("Attempting to take a Screenshot. ");

        try {
        	TakesScreenshot ts = (TakesScreenshot) d;
            String base64Screenshot = ts.getScreenshotAs(OutputType.BASE64);

            return base64Screenshot;
        } catch (Exception e) {
        	getLogger().error("Failure trying to take the Screenshot.");
        }

        return "";
    }
	
}

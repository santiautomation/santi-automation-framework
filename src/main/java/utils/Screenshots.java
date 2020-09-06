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

	private static final String PATH = "." + File.separator + "screenshots" + File.separator;
	
    public void takeScreenshot(WebDriver d) {
    	getLogger().debug("Attempting to take a Screenshot. ");
        
        String fileName = String.format("Screenshot-%s.jpg", Calendar.getInstance().getTimeInMillis());
        
        try {
        	TakesScreenshot ts = (TakesScreenshot) d;
            File srcFile = ts.getScreenshotAs(OutputType.FILE);
            File destFile = new File(PATH  + fileName);
           
            try{
                FileUtils.copyFile(srcFile, destFile);
            } catch (IOException e) {
            	getLogger().error("Failure copying Screenshot for source to destination, ", e);
            }
        } catch (Exception e) {
        	getLogger().error("Failure trying to take the Screenshot.");
        }
        
    }
	
}

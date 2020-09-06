package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import logging.Logging;

public class PropertyReader implements Logging {

	private Properties prop = new Properties();

    public PropertyReader() {   	
        try (InputStream in = getClass().getResourceAsStream("/" + Constants.PROPERTIES_NAME)){
            try {
            	getLogger().debug("Attempting .properties file load.");
                prop.load(in);
            } catch (FileNotFoundException e) {
            	getLogger().error(Constants.PROPERTIES_NAME + " Property file not found", e.getLocalizedMessage());
            } 
        
        } catch (IOException e) {
        	getLogger().error("Error reading file " + Constants.PROPERTIES_NAME, e.getLocalizedMessage());
        }
    }

    public String getString(String propertyName) {
        return prop.getProperty(propertyName);
    }
    
    public Integer getInt(String propertyName) {
    	int temp = -1;
    	
    	try {
    		temp = Integer.parseInt(prop.getProperty(propertyName));
    	} catch (NumberFormatException e) {
    		getLogger().error("The property named: " + propertyName + " cannot be parsed to an Int.");
    	}
        return temp;
    }
}

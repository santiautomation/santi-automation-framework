package logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Logging {
	
	default Logger getLogger() {
		System.setProperty("log4j.configurationFile", "log4j2.xml");
        return LogManager.getLogger(getClass());
    }
}

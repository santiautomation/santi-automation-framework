package testrunner;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

import utils.Constants;

/**
 * Retry class. If a test were to fail, it will try (1) more time. 
 * The number can be adjusted by changing the Constants.MAX_RETRY_COUNT value.
 * 
 * @author Santiago Schumacher
 *
 */
public class RetryAnalizer implements IRetryAnalyzer {

	private int retryCount= 0;
    private int maxRetryCount = Constants.MAX_RETRY_COUNT;
 
    @Override
    public boolean retry(ITestResult result) {
        if (++retryCount < maxRetryCount) {
            Reporter.log("Test failed. Retrying. Iteration: " + retryCount);
            return true;
        }
        return false;
    }
}

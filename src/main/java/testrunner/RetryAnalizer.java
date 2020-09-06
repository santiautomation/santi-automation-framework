package testrunner;

import org.testng.IRetryAnalyzer;
import org.testng.ITestNGListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import utils.Constants;

public class RetryAnalizer implements IRetryAnalyzer, ITestNGListener {

	private int retryCount= 0;
    private int maxRetryCount= Constants.MAX_RETRY_COUNT;
 
    @Override
    public boolean retry(ITestResult result) {
        if (++retryCount < maxRetryCount) {
            Reporter.log("Test failed. Retrying. Iteration: " + retryCount);
            return true;
        }
        return false;
    }
}

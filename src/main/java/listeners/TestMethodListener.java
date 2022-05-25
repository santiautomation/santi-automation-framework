package listeners;

import asserts.Validate;
import logging.Report;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestClass;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;
import org.testng.internal.TestResult;

import logging.Logging;
import testrunner.BaseTest;

/**
 * Code that is executed before/after every method.
 * 'This listener will only be invoked for configuration (BeforeXXX/AfterXXX)  and test methods' -> From TestNG official documentation
 * @author Santiago Schumacher
 *
 */
public class TestMethodListener implements IInvokedMethodListener, Logging {

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.isTestMethod()) {
			Report.logInfo("Beginning execution of Test: " + method.getTestMethod().getMethodName(), true);
			
			if (null != method.getTestMethod().getDescription() && !method.getTestMethod().getDescription().isEmpty()) {
				Report.logInfo(method.getTestMethod().getDescription(), true);
			}
		}
		
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (null != testResult.getThrowable()) {
			// Exception happened during the Test.
			getLogger().error(testResult.getThrowable().getMessage());
		}

		ITestClass invokingClass = method.getTestMethod().getTestClass();
		Object[] classInstance = invokingClass.getInstances(true);
		if (method.isTestMethod()) {
			BaseTest testCase = (BaseTest) classInstance[0];
			SoftAssert asserts = testCase.getValidator();
			AssertionError err = null;
			try {
				asserts.assertAll();
			} catch (AssertionError e) {
				err = e;
			}

			if (null != err || null != testResult.getThrowable()) {
				Report.logError("Error found during execution of the Test.", false);

				if (null != err) {
					Report.logError(err.getLocalizedMessage(), false);
					testResult.setThrowable(err);
				} else {
					Report.logError(testResult.getThrowable().getLocalizedMessage(), false);
				}

				testCase.getValidator().printError(); // Add screenshot on Test Exception or other errors.

				testResult.setStatus(TestResult.FAILURE);
			}

		}
	}

}
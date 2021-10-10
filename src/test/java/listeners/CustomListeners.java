package listeners;

import java.io.IOException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import base.TestBase;
import utilities.TestUtil;

public class CustomListeners extends TestBase implements ITestListener{
	
	

	public void onTestStart(ITestResult result) {
	
      test = report.createTest(result.getTestClass().getName()+"     @TestCase : "+result.getMethod().getMethodName());
      testReport.set(test);
      if(!TestUtil.isTestRunnable(result.getName(), excel))
      {
    	  throw new SkipException("Skipping the testcase  " +result.getName()+"as the Runmode is set as NO");
      }
	}

	public void onTestSuccess(ITestResult result) {
		String methodName=result.getMethod().getMethodName();
		String logText="<b>"+"TEST CASE:- "+ methodName.toUpperCase()+ " PASSED"+"</b>";		
		Markup m=MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		testReport.get().pass(m);
		
		//test.log(Status.PASS, result.getName().toUpperCase()+ " PASSED");
		//report.endTest(test);
		//report.flush();
		
	}
	public void onTestFailure(ITestResult result) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String failureLogg="TEST CASE FAILED";
		Markup m = MarkupHelper.createLabel(failureLogg, ExtentColor.RED);
		testReport.get().log(Status.FAIL, m);

		//test.log(Status.FAIL, result.getName().toUpperCase()+ "Failed due to Exception : " +result.getThrowable());
		test.addScreenCaptureFromPath(TestUtil.mailScreenshotpath);
		Reporter.log("Click Screenshot");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href=\"TestUtil.mailScreenshotpath\"> screenshot </a>");
		Reporter.log("<br>");
		//Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href=\"TestUtil.mailScreenshotpath\"> <img src=\"TestUtil.mailScreenshotpath\" height=200 width=200></img> </a>");
		//report.endTest(test);
		//report.flush();
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		Markup m= MarkupHelper.createLabel("Skipping the testcase " +result.getName() +" as the Runmode is set as NO", ExtentColor.LIME);
		testReport.get().log(Status.SKIP, m);
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) {
		if(report!=null)
		{
		// TODO Auto-generated method stub
		report.flush();
		}
		
	}
	

}

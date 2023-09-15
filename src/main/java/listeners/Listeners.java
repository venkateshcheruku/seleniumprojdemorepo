package listeners;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import resources.Base;
import utilities.ExtentReporter;

public class Listeners extends Base implements ITestListener {

	WebDriver driver = null;
	ExtentReports extentreport = ExtentReporter.getExtentReport();
	ExtentTest extenttest;
	ThreadLocal<ExtentTest> extentTestThread = new ThreadLocal<ExtentTest>();
	
	@Override
	public void onTestStart(ITestResult result) {
		
		 //extentreport = ExtentReporter.getExtentReport();
		 String testName = result.getName();
		 extenttest = extentreport.createTest(testName);
		 extentTestThread.set(extenttest);
		 
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		String testName = result.getName();
		//extenttest.log(Status.PASS, testName+ "got passed");
		extentTestThread.get().log(Status.PASS,"Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		//extenttest.fail(result.getThrowable());
		extentTestThread.get().fail(result.getThrowable());
		
		String testMethodname = result.getName();
		
		try {
			 driver = (WebDriver)result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		try {
			String screenshotFilepath = takeScreenshot(testMethodname, driver);
			extentTestThread.get().addScreenCaptureFromPath(screenshotFilepath, testMethodname);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		
	}

	@Override
	public void onStart(ITestContext context) {
		
	}

	@Override
	public void onFinish(ITestContext context) {
		
		extentreport.flush();
	}
	
	

}

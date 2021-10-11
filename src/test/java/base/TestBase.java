package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ExcelReader;
import utilities.ExtentManager;
import utilities.TestUtil;

public class TestBase {

	/* Initializing Web Driver,Properties,Logs,Excel,ExtentReport,DB,Mail here */

	public static WebDriver driver;
	public static Properties OR = new Properties();
	public static Properties config = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports report = ExtentManager.getInstance();
	public static ExtentTest test;
	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();
	public static String browser;

	@BeforeSuite
	public void setUp() {
		BasicConfigurator.configure();
		if (driver == null) {
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config file loaded!!!");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {

				browser = System.getenv("browser");
			} else {

				browser = config.getProperty("browser");

			}

			config.setProperty("browser", browser);

			if (config.getProperty("browser").equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				log.debug("Firefox Launched!!");
			} else if (config.getProperty("browser").equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.debug("Chrome Launched!!");
			} else if (config.getProperty("browser").equals("ie")) {
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				log.debug("IE Launched!!");
			}
			driver.get(config.getProperty("testsuiteurl"));
			log.debug("Navigated to : " + config.getProperty("testsuiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver, 5);
		}
	}

	public void click(String locator) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		test.log(Status.INFO, " Clicking on : " + locator);
	}

	static WebElement dropdown;

	public void select(String locator, String value) {
		dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		test.log(Status.INFO, "Selecting from dropdown : " + locator + " Entered Value is : " + value);

	}

	public void type(String locator, String value) {
		driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		test.log(Status.INFO, " Typing in : " + locator + "  Entered value as : " + value);
	}

	public boolean isElementPresent(By by, String string) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e)

		{
			return false;
		}
	}

	public static void verifyEquals(String Actual, String Expected) throws IOException {
		try {
			Assert.assertEquals(Actual, Expected);
		} catch (Throwable t) {
			TestUtil.captureScreenshot();
			// ReportNG
			Reporter.log("<br>");
			Reporter.log("Verfication Failure :" + t.getMessage());
			Reporter.log("<br>");
			Reporter.log(
					"<a target=\"_blank\"href=\"TestUtil.mailScreenshotpath\"> <img src=\"TestUtil.mailScreenshotpath\" height=200 width=200></img> </a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			// Extent Report
			// String failureLogg="TEST CASE FAILED";
			Markup m = MarkupHelper.createLabel("Verification failed due to Exception : " + t.getMessage(),
					ExtentColor.RED);
			testReport.get().log(Status.FAIL, m);

			// test.log(Status.FAIL, "Verification failed due to Exception : "
			// +t.getMessage());
			test.addScreenCaptureFromPath(TestUtil.mailScreenshotpath);
		}
	}

	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();

		}
		log.debug("Test Execution completed !!");
	}
}

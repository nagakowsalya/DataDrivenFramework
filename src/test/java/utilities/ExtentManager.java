package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;
	
	    public static ExtentReports getInstance() {
	    	
	    	if(extent==null)
	    	{
	    		
	    	ExtentSparkReporter spark = new ExtentSparkReporter(System.getProperty("user.dir")+"\\test-output\\html\\extent.html");
	    	

	    	spark.config().setTheme(Theme.STANDARD);
	    	spark.config().setDocumentTitle("Automation Test Report");
	    	spark.config().setEncoding("utf-8");
	    	spark.config().setReportName("Data Driven Framework Report");
	        
	        extent = new ExtentReports();
	        extent.attachReporter(spark);
	        extent.setSystemInfo("Automation Tester", "Naga Kowsalya");
	        /*extent.setSystemInfo("Organization", "Way2Automation");
	        extent.setSystemInfo("Build no", "W2A-1234");
	        extent.loadConfig(new File(System.getProperty("user.dir")+"\\src\\test\\resources\\extentconfig\\ReportsConfig.xml"));
			
	       
	        htmlReporter.config().setTheme(Theme.STANDARD);
	        htmlReporter.config().setDocumentTitle("Data Driven Framework");
	        htmlReporter.config().setEncoding("utf-8");
	        htmlReporter.config().setReportName("Automation Report");
	        
	      
	        extent.addSystemInfo("Automation Tester", "Naga Kowsalya");
	        //extent.setSystemInfo("Organization", "Way2Automation");
	        //extent.setSystemInfo("Build no", "W2A-1234");
	         * 
	         * */
	         
	    	}
	        
	        return extent;
	    }

	    
	  /*  public static String screenshotPath;
		public static String screenshotName;
		
		public static void captureScreenshot() {

			File scrFile = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);

			Date d = new Date();
			screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

			try {
				FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\reports\\" + screenshotName));
			} catch (IOException e) {
				
				e.printStackTrace();
			}

		
		}*/
	

	}

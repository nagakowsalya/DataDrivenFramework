package utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import base.TestBase;

public class TestUtil extends TestBase {
	
	public static String mailScreenshotpath;
	public static void captureScreenshot() throws IOException
	
	{
		
		Calendar cal = new GregorianCalendar();
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int date = cal.get(Calendar.DATE);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		mailScreenshotpath = System.getProperty("user.dir")+"\\target\\surefire-reports\\screenshot\\"+year+"_"+(month+1)+"_"+date+"_"+hour+"_"+min+"_"+sec+".jpg"; 
		FileUtils.copyFile(srcFile, new File(mailScreenshotpath));
	}
	
	@DataProvider(name="dp")
	public Object[][] getData(Method m) {
		String sheetName = m.getName();
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][1];

		 Hashtable<String,String> table = null;

		for (int rowNum = 2; rowNum <= rows; rowNum++) {

			 table = new Hashtable<String,String>();

			for (int colNum = 0; colNum < cols; colNum++) {
				
			table.put(excel.getCellData(sheetName, colNum, 1),excel.getCellData(sheetName, colNum, rowNum));

				data[rowNum-2][0]= table;
		}

	
		}
		
		return data;
	}

	public static boolean isTestRunnable(String Testname,ExcelReader excel)
	{
		String sheetName = "test_suite";
		int rows= excel.getRowCount(sheetName);
		
		for(int rNum=2; rNum<=rows;rNum++)
		{
			String testcase= excel.getCellData(sheetName, "TCID", rNum);
			if(testcase.equalsIgnoreCase(Testname))
			{
				String runmode=excel.getCellData(sheetName, "Runmode", rNum);
						
						if(runmode.equalsIgnoreCase("Y"))
							return true;
						else 
							return false;
							
			}
		
	}
		return false;
	}
	// make zip of reports
			public static void zip(String filepath){
			 	try
			 	{
			 		File inFolder=new File(filepath);
			 		File outFolder=new File("Reports.zip");
			 		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
			 		BufferedInputStream in = null;
			 		byte[] data  = new byte[1000];
			 		String files[] = inFolder.list();
			 		for (int i=0; i<files.length; i++)
			 		{
			 			in = new BufferedInputStream(new FileInputStream
			 			(inFolder.getPath() + "/" + files[i]), 1000);  
			 			out.putNextEntry(new ZipEntry(files[i])); 
			 			int count;
			 			while((count = in.read(data,0,1000)) != -1)
			 			{
			 				out.write(data, 0, count);
			 			}
			 			out.closeEntry();
		  }
		  out.flush();
		  out.close();
			 	
		}
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  } 	

			}
}

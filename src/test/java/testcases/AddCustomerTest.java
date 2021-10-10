package testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import base.TestBase;
import utilities.TestUtil;

public class AddCustomerTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void addCustomerTest(Hashtable<String,String> data) {
		
		if(!data.get("runmode").equalsIgnoreCase("Y"))
		{
			throw new SkipException("Skipping the test case as the Runmode is set as NO");
		}

		click("addCustBtn");
		type("firstname",data.get("firstname"));
		type("lastname",data.get("lastname"));
		type("postcode",data.get("postcode"));
		click("addCustSubBtn");
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
	
		alert.accept();
		
	}
}
package testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.TestBase;

public class BankManagerLoginTest extends TestBase {
@Test
	public void bankManagerLoginTest() throws IOException
	{
	//verifyEquals("abcd","dfgr");
	log.debug("Inside login Test");	
	click("bmlBtn");
	Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn")), "Logged in was not successful"));
	
	log.debug("Login successfully executed");
	
	}
}

package tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.AccountPage;
import pageobjects.LandingPage;
import pageobjects.LoginPage;
import resources.Base;

 public class LoginTest extends Base {
	
	Logger log;
	public WebDriver driver;
	
	
	@Test(dataProvider="getLoginData")
	public void login(String email, String password, String expectedResult) throws IOException, InterruptedException {
		
		
		
		
		LandingPage landingpage = new LandingPage(driver);
		landingpage.myaccountDropdown().click();
		log.debug("Clicked on Myaccount Dropdown");
		landingpage.loginoption().click();
		log.debug("Clicked on login option");
		
		Thread.sleep(2000);
		
		LoginPage loginpage = new LoginPage(driver);
		//loginpage.emailAddressField().sendKeys(prop.getProperty("loginemail"));
		//loginpage.passwordField().sendKeys(prop.getProperty("loginpassword"));
		loginpage.emailAddressField().sendKeys(email);
		log.debug("Entered the Email Address");
		loginpage.passwordField().sendKeys(password);
		log.debug("Enetered the Password");
		loginpage.loginButton().click();
		log.debug("Clicked on the Login Button");
		
		AccountPage accountpage = new AccountPage(driver);
		
		//Assert.assertTrue(accountpage.accountinformation().isDisplayed());
		String actualresult;
		try {
			System.out.println("inside try block");
			
			accountpage.accountinformation().isDisplayed();
			actualresult="Successful";
			log.debug("User got logged in");
			
		}catch(Exception e) {
			System.out.println("inside catch block");
			actualresult="Failure";
			log.debug("User failed to login");
			
		}
		
		AssertJUnit.assertEquals(actualresult, expectedResult);
		log.info("Login Test has Passed");
	}
	
	@BeforeMethod
	public void openApplication() throws IOException {
		
		log = LogManager.getLogger(LoginTest.class.getName());
		driver = initializedriver(); 
		log.debug("Browser has launched");
		driver.get(prop.getProperty("url"));
		log.debug("Navigated to the URL");
		
	}
	@AfterMethod
	public void closoure() {
		driver.quit();
		log.debug("Browser is closed");
	}
	
	@DataProvider
	public Object[][] getLoginData() {
		
		Object[][] data = {{"vv124124@gmail.com","Second@123","Successful"}};
		
		return data;
	}
}

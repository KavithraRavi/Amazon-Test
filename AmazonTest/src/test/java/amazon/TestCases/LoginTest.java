package amazon.TestCases;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginTest extends BaseClass
{
	
	public void loginTest() throws IOException, InterruptedException
	{
		
		driver.get(baseUrl);
		logger.info("URL is opened");
		
		WebDriverWait wait = new WebDriverWait(driver,5);
		
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//a[@id='nav-link-accountList']"))).build().perform();
		
		driver.findElement(By.xpath("//*[@id=\"nav-flyout-ya-signin\"]/a")).click();
		driver.findElement(By.xpath("//input[@id='ap_email']")).sendKeys(username);
		logger.info("Entered username");
		
		driver.findElement(By.xpath("//input[@id='continue']")).click();
		logger.info("clicked continue button");
		
		driver.findElement(By.xpath("//input[@id='ap_password']")).sendKeys(password);
		logger.info("Entered Password");
		
		driver.findElement(By.xpath("//*[@id=\"signInSubmit\"]")).click();
		logger.info("Clicked Submit button");
		

		if(wait.until(ExpectedConditions.titleContains("Amazon.de: Low Prices in Electronics, Books, Sports Equipment & more")))
				
		{
			Assert.assertTrue(true);
			logger.info("Logged into Amazon");
		}
		else
		{
			Assert.assertTrue(false);
			logger.info("Login Failed");
		}
	}
}

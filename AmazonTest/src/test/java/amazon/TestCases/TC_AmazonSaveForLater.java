package amazon.TestCases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_AmazonSaveForLater extends BaseClass {
	
	@Test
	public void AmazonSaveForLater() throws IOException, InterruptedException
	{
		
		String cart="//*[@id=\"nav-cart\"]";
		String cartItemsxpath = "//*[@data-itemtype=\"active\"]";
		WebDriverWait wait = new WebDriverWait(driver, 30);
		String dataIdsaveforlater;
		LoginTest lp = new LoginTest();
		lp.loginTest();
		
		driver.findElement(By.xpath(cart)).click();
		if(driver.getTitle().equals("Amazon.de Basket"))	
		{
			logger.info("IN AMAZON CART PAGE");
			
			List<WebElement> cartItems = driver.findElements(By.xpath(cartItemsxpath));
			
			if(cartItems.size()!=0)
				{
					System.out.println("Have items in the cart");
					//Getting the product id of the items in the cart
					dataIdsaveforlater = cartItems.get(0).getAttribute("data-asin");
					System.out.println(dataIdsaveforlater);
					
					wait.until(ExpectedConditions.elementToBeClickable(cartItems.get(0).findElement(By.xpath("//input[@data-action=\"save-for-later\"]")))).click();
										
					driver.navigate().refresh();
					
					List<Object> cartitemsidaftersave = (driver.findElements(By.xpath(cartItemsxpath))).stream().map(element -> element.getAttribute("data-asin")).collect(Collectors.toList());
					
					//Verify if the product saved for later 
					ArrayList<String> saveforlateritemsId = verifySaveForLater();
					
					System.out.println("verify");
				
					if((saveforlateritemsId.contains(dataIdsaveforlater)) && (!(cartitemsidaftersave.contains(dataIdsaveforlater))))
									{
										Assert.assertTrue(true);
										logger.info("Test Passed");
									}
						else
						{
							Assert.assertFalse(false);
							logger.info("Test Failed");
						
						}
					
					
		}
		else
		{
			
			System.out.println("The cart is empty");
			Assert.assertFalse(false);
		}
		
	}
	
}
	//Get the product id of save for later items for test case verification
	public ArrayList<String> verifySaveForLater() throws InterruptedException
	{
		
		System.out.println("in save for later page");
		String xpathSaveForLate = "//div[@id=\"sc-saved-cart-items\"]//div[@data-itemtype=\"saved\"]";
		
		WebDriverWait wait = new WebDriverWait(driver, 100);
		Actions at = new Actions(driver);
		Thread.sleep(3000);
		
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		at.sendKeys(Keys.PAGE_DOWN).build().perform();
		at.sendKeys(Keys.PAGE_DOWN).build().perform();
		
		driver.navigate().refresh();
		
		List<WebElement> saveforlateritems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpathSaveForLate)));
		
		ArrayList<String> savedforlaterhref = (ArrayList<String>) saveforlateritems.stream().map(element -> element.getAttribute("data-asin")).collect(Collectors.toList());
		
		ArrayList<String> saveforlateritemsId = new ArrayList<String>();
		
		
		
		for(int i=0;i<saveforlateritems.size();i++)
		{
			saveforlateritemsId.add((driver.findElements(By.xpath(xpathSaveForLate))).get(i).getAttribute("data-asin"));
			
		}
		return savedforlaterhref;
		
		
	}

}

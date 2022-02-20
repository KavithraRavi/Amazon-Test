package amazon.TestCases;

import java.io.IOException;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TC_AmazonSearch extends BaseClass
{
	
	//Declaration of variables
	String searchResults = "//*[@data-component-type=\"s-search-result\"]"+"//ancestor::div[@data-asin and not(.//span[.=\"Sponsored\"])]"
																		+"//ancestor::div[@data-asin and not(.//span[.=\"Temporarily out of stock.\"])]";
	ArrayList<String> dataidSearchResults = new ArrayList<String>();
	ArrayList<String> dataidCartItems = new ArrayList<String>();
	String cartItemsxpath = "//*[@data-itemtype=\"active\"]";
	String cart="//*[@id=\"nav-cart\"]";
	
		
	@Test
	public void AmazonSearch() throws IOException, InterruptedException
	{
		
		
		LoginTest lp = new LoginTest();
		lp.loginTest();
		
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("drucker");
		driver.findElement(By.id("nav-search-submit-button")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		
		//Adding first 2 products from search results to the amazon cart
		for(int i=0;i<2;i++)
		{
			Thread.sleep(3000);
			
			dataidSearchResults.add((driver.findElements(By.xpath(searchResults))).get(i).getAttribute("data-asin"));
			wait.until(ExpectedConditions.elementToBeClickable((driver.findElements(By.xpath(searchResults))).get(i).findElement(By.tagName("a")))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@id='add-to-cart-button'])"))).click();
			//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@aria-labelledby=\"attachSiNoCoverage-announce\"])[1]"))).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"attach-close_sideSheet-link\"]"))).click();
			
			
			if(i!=1) 
			{
				driver.navigate().back();
				driver.navigate().back();
			}
			else
			{
				logger.info("added 2 items to the cart");
			}
			
		}
		

		//Verifying if 2 products are added to basket
		ArrayList<String> cartItemIds = verifyCart();
		
		boolean TestPassed = cartItemIds.containsAll(dataidSearchResults);
		if(TestPassed==true)
			{
			
				logger.info("Test Passed");
				Assert.assertTrue(true);
			}
		else
			{
				captureScreen(driver,"logintest");
				logger.info("Test Failed");
				Assert.assertTrue(false);
			}
		
			
	}
	
	//Method for getting product id of items in the cart	
	public ArrayList<String> verifyCart()
	{
		driver.findElement(By.xpath(cart)).click();
		
		if(driver.getTitle().equals("Amazon.de Basket"))	
		{
			driver.navigate().refresh();
			logger.info("IN AMAZON CART");
			
			int size = driver.findElements(By.xpath(cartItemsxpath)).size();
			for(int j=0;j<size;j++)
			{
				dataidCartItems.add(driver.findElements(By.xpath(cartItemsxpath)).get(j).getAttribute("data-asin"));
			}
		}
		return dataidCartItems;
	}
}

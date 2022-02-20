package amazon.TestCases;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC_AmazonMoveToBasket extends BaseClass {

	@Test
	public void AmazonMoveToBasket() throws IOException, InterruptedException
	{
		
		
		LoginTest lp=new LoginTest();
		lp.loginTest();
		
		
		String cart="//*[@id=\"nav-cart\"]";
		
		driver.findElement(By.xpath(cart)).click();
		
		WebDriverWait wait = new WebDriverWait(driver, 100);
		
		String xpathSaveForLate = "//form[@id=\"savedCartViewForm\"]//div[@id=\"sc-saved-cart-items\"]//div[@data-itemtype=\"saved\"]";
				
		List<WebElement> saveforlateritems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpathSaveForLate)));
		
		String movetobasketitem = saveforlateritems.get(0).getAttribute("data-asin");
		
		WebElement moveToBasketItems = saveforlateritems.get(0);
		
		String cartItemsxpath = "//*[@data-itemtype=\"active\"]";
		
		
		wait.until(ExpectedConditions.elementToBeClickable(moveToBasketItems.findElement(By.xpath("//*[contains(@class,\"a-button a-button-base sc-action-button\")]//input[@data-action=\"move-to-cart\"]")))).click();
		
		driver.navigate().refresh();
		
		List<String> cartitemsidaftermove = (driver.findElements(By.xpath(cartItemsxpath))).stream().map(element -> element.getAttribute("data-asin")).collect(Collectors.toList());
		
		
		//Verifying if the product is moved to Basket from Save for Later
		boolean TestPassed = ((cartitemsidaftermove.contains(movetobasketitem)));
		
		if(TestPassed == true)
		{
			Assert.assertTrue(true);
			logger.info("Test passed");
		}
		else
		{
			Assert.assertFalse(false);
			logger.info("Test failed");
		}
		
	}
	
}

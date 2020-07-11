package com.amazon.testscripts;
import org.testng.annotations.Test;

import net.portal.mobile.core.BaseTest;

public class AmazonAutomationTest extends BaseTest {
	
	String productName;
	
	@Test(groups = { "Mobile"})
	public void verifyProductSearchAmazon() throws Exception {
		
		reportLog("1.Search Product" +searchProduct);
		mobileHomePage.searchProduct(searchProduct);
		
		reportLog("2.Select any random product from screen");
		mobileHomePage.selectRandomProduct();
		
		reportLog("3.Click to product");
		mobileProductPage=mobileHomePage.clickToProduct();
		
		reportLog("4.Get product title and click to add to cart");
		productName=mobileProductPage.getProductTitle();
		mobileProductPage.clickToAddToCart();
		
		reportLog("5.Navigate to checkout Page");
		checkoutPage=mobileProductPage.navigateToCheckOutPage();
		
		reportLog("6.Verify description on page is same as added product");
		checkoutPage.verifyProductDescription(productName);
	
	}
  
}

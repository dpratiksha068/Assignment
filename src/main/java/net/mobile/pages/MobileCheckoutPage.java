package net.mobile.pages;

import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import net.mobile.appium.core.MobileCoreFunctions;

public class MobileCheckoutPage extends MobileCoreFunctions {
	
	public MobileCheckoutPage(AppiumDriver<MobileElement> driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(mobileDriver), this);
	}

	@AndroidFindBy(xpath = "(//android.view.View[@resource-id='activeCartViewForm']//android.widget.TextView)[1]")
	private MobileElement productDescription;

	/* Verify Product Description on Checkout Page*/
	public void verifyProductDescription(String descToBeVerify)
	{
		    waitForElementToBecomeVisible(productDescription,20);
			Assert.assertTrue(productDescription.getText().contains(descToBeVerify));
	
	}
	

}

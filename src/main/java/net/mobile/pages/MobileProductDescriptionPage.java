package net.mobile.pages;

import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import net.mobile.appium.core.MobileCoreFunctions;

public class MobileProductDescriptionPage extends MobileCoreFunctions {

	public MobileProductDescriptionPage(AppiumDriver<MobileElement> driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(mobileDriver), this);
	}

	@AndroidFindBy(xpath = "//android.view.View[contains(@resource-id,'title_feature_div')]/android.view.View")
	private MobileElement product_title;

	@AndroidFindBy(xpath = "//android.view.View[@resource-id='bylineInfo']//android.widget.TextView")
	private MobileElement product_info;

	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='add-to-cart-button']")
	private MobileElement addtoCardBTN;

	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id='add-to-cart-button']")
	private List<MobileElement> addtoCardBTNLst;

	@AndroidFindBy(xpath = "//android.widget.FrameLayout[contains(@resource-id,'chrome_action_bar_cart')]")
	private MobileElement checkoutIcon;

	/* Get Product Description */
	public String getProductTitle() {
		waitForElementToBecomeVisible(product_info, 20);
		String productName = product_info.getText().trim();
		return productName;
	}

	/* Click Add to Cart Button */
	public void clickToAddToCart() {
		try {
			do {
				scrollFromTopToBottom();
			} while (addtoCardBTNLst.size() == 0);

			waitForElementToBecomeVisible(addtoCardBTN, 20);
			addtoCardBTN.click();
		} catch (Exception e) {
			Assert.assertTrue(false, "Add Cart is not clickable" + addtoCardBTN);
		}

	}

	/* Navigate To CheckoutPage */
	public MobileCheckoutPage navigateToCheckOutPage() {
		MobileCheckoutPage checkoutPage = new MobileCheckoutPage(mobileDriver);
		waitForElementToBecomeVisible(checkoutIcon, 20);
		longPress(checkoutIcon);
		PageFactory.initElements(new AppiumFieldDecorator(mobileDriver), checkoutPage);
		return checkoutPage;
	}

}

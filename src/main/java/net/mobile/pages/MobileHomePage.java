package net.mobile.pages;

import java.util.List;

import org.openqa.selenium.By;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import io.appium.java_client.android.AndroidDriver;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import net.mobile.appium.core.MobileCoreFunctions;

public class MobileHomePage extends MobileCoreFunctions {

	public MobileHomePage(AppiumDriver<MobileElement> driver) {
		super(driver);
		PageFactory.initElements(new AppiumFieldDecorator(mobileDriver), this);
	}

	@AndroidFindBy(className = "android.widget.EditText")
	private MobileElement searchProductBox;

	@AndroidBy(xpath = "//android.widget.TextView[contains(@resource-id,'/item_title')]")
	private List<MobileElement> itemTextList;

	@AndroidBy(xpath = "//*[contains(@resource-id,'list_product_linear_layout')]")
	private List<MobileElement> productList;

	@AndroidFindBy(xpath = "//android.widget.TextView[contains(@resource-id,'rs_results_count_text')]")
	private MobileElement resultCount;

	@AndroidFindBy(xpath = "(//android.widget.LinearLayout[contains(@resource-id,'iss_search_dropdown_item_suggestions')])[1]")
	private MobileElement suggestions;

	/* Search Product */
	public void searchProduct(String productTobeSearch) throws InterruptedException {
		
		try {
			waitForElementToBecomeVisible(searchProductBox, 20);
			searchProductBox.click();
			waitForElementToBecomeVisible(searchProductBox, 20);
			searchProductBox.sendKeys(productTobeSearch);
			((AndroidDriver<MobileElement>) mobileDriver).pressKey(new KeyEvent(AndroidKey.ENTER));
		} catch (Exception e) {
			Assert.assertTrue(false, "Search Box is not present" + searchProductBox);
		}

	}

	/* Select Any Random Product */
	public void selectRandomProduct() throws InterruptedException {
		for (int i = 0; i < 2; i++) {
			scrollFromTopToBottom();
		}
	}

	/* Click to the product */
	public MobileProductDescriptionPage clickToProduct() {

		_normalWait(3000);
		MobileProductDescriptionPage mobileProductPage = new MobileProductDescriptionPage(mobileDriver);
		List<MobileElement> itemList = mobileDriver
				.findElements(By.xpath("//android.widget.TextView[contains(@resource-id,'/item_title')]"));
		for (int product = 0; product < itemList.size(); product++) {
			waitForElementToBecomeVisible(itemList.get(1), 20);
			itemList.get(1).click();
			break;
		}

		PageFactory.initElements(new AppiumFieldDecorator(mobileDriver), mobileProductPage);
		return mobileProductPage;

	}

}
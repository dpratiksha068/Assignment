package net.mobile.appium.core;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import net.mobile.pages.MobileHomePage;

public class MobileCoreFunctions {

	protected MobileHomePage mobileHomePage;

	protected AppiumDriver<MobileElement> mobileDriver;
	public int globalWaitTime = 80;
	public int DEFAULT_WAIT_4_ELEMENT = 15;
	public int DEFAULT_WAIT_2_ELEMENT = 30;
	public int DEFAULT_WAIT_ELEMENT = 6000;

	public MobileCoreFunctions(AppiumDriver<MobileElement> driver) {
		this.mobileDriver = driver;
	}

	public AppiumDriver<MobileElement> getApiumDriver() {
		return mobileDriver;
	}

	public AndroidDriver<MobileElement> getAndroidDriver() {
		return (AndroidDriver<MobileElement>) mobileDriver;
	}

	public IOSDriver<MobileElement> getIOSDriver() {
		return (IOSDriver<MobileElement>) mobileDriver;
	}

	public boolean isElementPresent(String locator) {
		try {
			mobileDriver.findElement(ByLocator(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isElementPresent(WebElement element) {
		try {
			element.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public By ByLocator(String locator) {
		By result = null;
		if (locator.startsWith("//")) {
			result = By.xpath(locator);
		} else if (locator.startsWith("css=")) {
			result = By.cssSelector(locator.replace("css=", ""));
		} else if (locator.startsWith("#")) {
			result = By.id(locator.replace("#", ""));
		} else if (locator.startsWith("name=")) {
			result = By.name(locator.replace("name=", ""));
		} else if (locator.startsWith("link=")) {
			result = By.linkText(locator.replace("link=", ""));
		} else {
			result = By.className(locator);
		}
		return result;
	}

	public boolean isElementPresent(By by) {
		try {
			mobileDriver.findElement(by);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void moveElement(By source, By target) {
		WebElement elSource = this.find(source);
		WebElement elDest = this.find(target);
		TouchAction action = new TouchAction(mobileDriver);
		action.longPress(ElementOption.element(elSource)).moveTo(ElementOption.element(elDest)).release().perform();

	}

	public String getAttribute(By by, String attribute) {
		WebElement element = find(by);
		return element.getAttribute(attribute);
	}

	public String getText(By by) {
		WebElement element = find(by);
		return element.getText();
	}

	public void hideKeyboard() {
		mobileDriver.hideKeyboard();
		_normalWait(globalWaitTime);

	}

	public void clickOn(By by) {
		mobileDriver.findElement(by).click();
	}

	public WebElement click(By by) {
		waitForElementPresent(by);
		WebElement element = find(by);
		element.click();
		return element;
	}

	public WebElement click(WebElement element) {
		element.click();
		return element;
	}

	public void createFolder(String folderPath) {
		File file = new File(folderPath);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	public WebElement clear(By by) {
		WebElement element = find(by);
		element.clear();
		return element;
	}

	public void setText(MobileElement element, String text) {
		element.clear();
		longPress(element);
		element.sendKeys(text);
	}

	/*
	 * public void TouchAction(WebElement we){ MultiTouchAction test=new
	 * MultiTouchAction(mobileDriver); TouchActions test1=new
	 * TouchActions(mobileDriver); }
	 */

	public void clearAndSetText(MobileElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}

	public WebElement setText(By by, String text) {
		MobileElement element = find(by);
		element.clear();
		element.sendKeys(text);
		return element;
	}

	public WebElement appendText(By by, String text) {
		WebElement element = find(by);
		element.sendKeys(text);
		return element;
	}

	public MobileElement find(By by) {
		waitForElementPresent(by);
		return mobileDriver.findElement(by);
	}

	public void waitForElementPresent(By by) {
		WebDriverWait wait = new WebDriverWait(mobileDriver, globalWaitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void waitForElement(By by) {
		WebDriverWait wait = new WebDriverWait(mobileDriver, globalWaitTime);
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	public void waitForElementNotPresent(String locator) {
		WebDriverWait wait = new WebDriverWait(mobileDriver, globalWaitTime);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(ByLocator(locator)));
	}

	public void waitForElementToBecomeVisible(WebElement element, int time) {
		WebDriverWait wait = new WebDriverWait(mobileDriver, time);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void reportLog(String message) {
		Reporter.log(message + "<br>");
	}

	public void scrollToElementTouchAction(MobileElement startElement, MobileElement endElement) {
		new TouchAction(mobileDriver).press(ElementOption.element(startElement))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(globalWaitTime)))
				.moveTo(ElementOption.element(endElement)).release().perform();

	}

	public void TouchScreen() {
		org.openqa.selenium.Dimension size = mobileDriver.manage().window().getSize();
		int x = size.width / 2;
		int y = size.height / 2;
		TouchAction action1 = new TouchAction(mobileDriver).longPress(PointOption.point(x, y))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1500)));
		action1.perform();
	}

	public List<MobileElement> getMobileElements(By by) {
		return mobileDriver.findElements(by);
	}

	public String longPress(WebElement onElement) {
		String imagePath = null;
		for (int i = 0; i < 1; i++) {
			TouchAction action = new TouchAction(mobileDriver);
			action.longPress(ElementOption.element(onElement))
					.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(3))).release().perform();

		}
		_normalWait(globalWaitTime);
		return imagePath;
	}

	/** normal wait for thread. */
	public void _normalWait(long timeOut) {
		try {
			Thread.sleep(timeOut);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/** Store text from a locator */
	public String getText(WebElement element) {
		Assert.assertTrue(element.isDisplayed(), "Element Locator :" + element + " Not found");
		return element.getText();
	}
	/* Verify Element is clickable */

	public boolean isClickable(MobileElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(mobileDriver, 5);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/* Scroll From Top To bottom */

	public void scrollFromTopToBottom() {
		Dimension dim = mobileDriver.manage().window().getSize();
		int height = dim.getHeight();
		int width = dim.getWidth();
		int x = width / 2;
		int top_y = (int) (height * 0.80);
		int bottom_y = (int) (height * 0.20);
		TouchAction ts = new TouchAction(mobileDriver);
		ts.longPress(PointOption.point(x, top_y)).moveTo(PointOption.point(x, bottom_y)).release().perform();

	}

	/* Get Only Current Date */
	public String currentOnlyDate() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("d");
		String strDate = formatter.format(date);
		return strDate;
	}

	/**
	 * function : Fetch the System's current date with time
	 * 
	 */
	public String getTimeStamp() {
		Date date = new Date();

		return new Timestamp(date.getTime()).toString().replace(" ", "");

	}

	public void tap(TapOptions element) {
		TouchAction ac = new TouchAction(mobileDriver);
		ac.tap(element).perform();

	}

}

/*
  Class to initialize all application page objects and manage WebDriver browser
  object. Each and every test script class must extend this. This class does
  not use any of the Selenium APIs directly, and adds support to make this
  framework tool independent.

  @author 360Logica
 * @since 1.0
 *
 * @version 1.0
 */
package net.portal.mobile.core;


import java.io.BufferedReader;
import java.io.File;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.Date;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import net.mobile.pages.MobileCheckoutPage;
import net.mobile.pages.MobileHomePage;
import net.mobile.pages.MobileProductDescriptionPage;
import net.portal.utilities.Utilities;

public abstract class BaseTest {

	public static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
	private static final String BREAK_LINE = "\n";

	public static ExtentTest test;
	public static ExtentReports extent;
	protected MobileHomePage mobileHomePage;
	protected MobileProductDescriptionPage mobileProductPage;
	protected MobileCheckoutPage checkoutPage;
	private static AppiumDriver<MobileElement> appiumDriver;

	public static String resultPath, searchProduct,platformName,udid,deviceName,platformVersion;

	@BeforeSuite
	public void before() throws Exception {
		searchProduct = Configuration.readApplicationFile("productToBesearch");
		platformName = Configuration.readApplicationFile("AndroiPlatformName");
		udid = Configuration.readApplicationFile("AndoidUdid");
		deviceName=Configuration.readApplicationFile("AndroidDeviceName");
		searchProduct = Configuration.readApplicationFile("productToBesearch");
		platformVersion=Configuration.readApplicationFile("AndroidPlatformVersion");
	}

	@BeforeMethod
	public MobileHomePage setUp(Method method) throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("deviceName", deviceName);
		capabilities.setCapability("udid", udid);
		capabilities.setCapability("app",
				Utilities.getPath() + "\\src\\test\\resources\\webdriver\\Amazon_shopping.apk");
		capabilities.setCapability("appPackage", "com.amazon.mShop.android.shopping");
		capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
		capabilities.setCapability("platformVersion", platformVersion);
		capabilities.setCapability("autoGrantPermissions", "true");
		capabilities.setCapability("noReset", "true");
		capabilities.setCapability("fullReset", "false");
		appiumDriver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
		Thread.sleep(3000);
		mobileHomePage = new MobileHomePage(appiumDriver);
		PageFactory.initElements(new AppiumFieldDecorator(appiumDriver), mobileHomePage);
		Thread.sleep(5000);
		return mobileHomePage;

	}

	@AfterMethod(alwaysRun = true)
	public void afterMainMethod(ITestResult result) throws IOException, InterruptedException {
		appiumDriver.quit();
		try {
			finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	@AfterSuite(alwaysRun = true)
	public void tearDownSuite() throws IOException {
		appiumDriver.quit();
	}

	public String getPathUpload() {
		String path;
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("/", "//");
		return path;
	}

	/**
	 * Report logs
	 *
	 * @param : message
	 * @throws DocumentException
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws BadElementException
	 */
	public void reportLog(String msg) {
		message = msg;
		message = BREAK_LINE + message;
		logger.info("Message: " + message);
		Reporter.log(message);
	}

	static String message = "";

	public static String getMessage() {
		return message;
	}

	/**
	 * function : Fetch the System's current date with time
	 * 
	 */
	public String getTimeStamp() {
		Date date = new Date();
		return new Timestamp(date.getTime()).toString().replace(" ", "");
	}

	/**
	 * @return
	 * @function: Get formatted Time stamp
	 * 
	 */
	public String getFormattedTimeStamp() {

		LocalDateTime currentTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMM-uuuu HH:mm:ss");
		String formatDateTime = currentTime.format(formatter);
		return formatDateTime;

	}

	/**
	 * 
	 * 
	 * @throws ParseException
	 * @function: Conversion Of Date Format from yyyy/MM/d to dd-MMM-yyyy
	 * 
	 */
	public String conversionOfDateFormat(String sDate1) throws ParseException {
		SimpleDateFormat fr = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
		Date d = fr.parse(sDate1);
		DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		String formatDateTime = format.format(d);
		return formatDateTime;
	}

	/**
	 * Function: Get current date.
	 * 
	 * @return
	 */
	public String currentDate() {
		final DateFormat format = new SimpleDateFormat("dd-MMM-YYYY");
		Date date = new Date();
		final String currentDate = format.format(date);
		return currentDate;
	}

	/**
	 * Generate Random Numeric String of length
	 */
	public static String GenerateRandomNumber(int charLength) {
		return String.valueOf(charLength < 1 ? 0
				: new Random().nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
						+ (int) Math.pow(10, charLength - 1));
	}

	/**
	 * Function: Get random string
	 * 
	 * @param lettersNum
	 * @return
	 */
	public static String generateRandomString(int lettersNum) {
		StringBuilder finalString = new StringBuilder();

		int numberOfLetters = 25;
		long randomNumber;
		for (int i = 0; i < lettersNum; i++) {
			char letter = 97;
			randomNumber = Math.round(Math.random() * numberOfLetters);
			letter += randomNumber;
			finalString.append(String.valueOf(letter));
		}
		return finalString.toString();
	}

	/**
	 * Generate AlphanumericString of length
	 */
	public String generateRandomAlphanumericString(int lenthOfString) {
		String generatedString = RandomStringUtils.randomAlphanumeric(lenthOfString);
		return generatedString;
	}

	/**
	 * Function: Get random integer
	 * 
	 * @param aStart
	 * @param aEnd
	 * @return
	 */

	public int getRandomInteger(final long aStart, final long aEnd) {
		final Random aRandom = new Random();
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		// get the range, casting to long to avoid overflow problems
		final long range = aEnd - aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		final long fraction = (long) (range * aRandom.nextDouble());
		final int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}

	/**
	 * Compressing Reports folder in a compressed Zip file for archiving reports
	 * 
	 */

	/**
	 * read all connect devices uuid
	 *
	 * @return : it return list of uuid of connected devices
	 */
	public static List<String> getAttachedDevicesList() {

		List<String> devicesID = new ArrayList<>();
		try {

			Process process = Runtime.getRuntime().exec(getAndroidPath() + "//platform-tools//adb devices");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String s;

			while ((s = reader.readLine()) != null) {
				if (s.contains("device") && !s.contains("attached")) {
					String[] device = s.split("\t");
					devicesID.add(device[0]);
				}
			}

		} catch (IOException e) {
		}
		return devicesID;

	}

	/**
	 * get android home path
	 *
	 * @return : return ANDROID HOME path
	 */
	public static String getAndroidPath() {
		String androidHome = System.getProperty("ANDROID_HOME");
		if (androidHome == null) {
			androidHome = System.getenv("ANDROID_HOME");
		}
		return androidHome;
	}
	
	

}
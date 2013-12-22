package com.Utilities;

import static org.testng.Assert.fail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 
 * Author :: Mahesh Ambati Email :: ursmahesh.tester@gmail.com Mobile ::
 * 9600021944
 * 
 */

public class WrapperFunctions {

	private static final Integer ELEMENT_WAIT_TIMEOUT = 60000;
	protected static WebDriver driver;
	protected static String REPORT_DIRECTORY;
	protected static String DOMAIN;
	protected Integer WAIT_PERIOD;
	protected boolean EXPLICIT_WAIT = false;
	public final static int PAGE_TIMEOUT_TIME = 60000;
	public final static long ACTION_TIMEOUT_TIME = 60000;

	/*
	 * DELAY is a value which will be used in waitForElement method and can be
	 * increased if the environment is slow.
	 */
	private static final Integer DELAY = 1;
	private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyz";

	public WebElement webElement;

	/**
	 * 
	 * @param element
	 * @return
	 * @return
	 */
	public WrapperFunctions clickWithoutWait(WebElement element) {

		if (element.isEnabled()) {
			element.click();
		} else
			fail("Element: " + element + " NOT found!");
		return this;
	}

	/**
	 * This method is for forced waiting
	 * 
	 * @param element
	 * @return
	 */
	public static void sleepFor(long i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * 
	 * Method can be used to return wait until page is loaded fully based on
	 * document return state.
	 * 
	 * @param driver
	 * @return
	 */
	public static boolean waitForPageToLoad(WebDriver driver) {
		try {
			long start = System.currentTimeMillis();
			try {
				Thread.sleep(700);
			} catch (InterruptedException e1) {

				e1.printStackTrace();
			}
			while (System.currentTimeMillis() - start < PAGE_TIMEOUT_TIME) {
				if (((JavascriptExecutor) driver)
						.executeScript("return document.readyState").toString()
						.equalsIgnoreCase("complete")) {
					return true;
				} else {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception ignore) {
		}
		return false;

	}

	public static String Date() {
		// DateFormat dateFormat = new SimpleDateFormat(�yyyy/MM/dd HH:mm:ss�);
		DateFormat dateFormat = new SimpleDateFormat("yyyy ss");
		// get current date time with Date()
		Date date = new Date();
		String DATE = dateFormat.format(date);
		DATE = DATE.replace("/", "");
		DATE = DATE.replace(":", "");
		DATE = DATE.replace(" ", "");
		return DATE;
	}

	@SuppressWarnings("deprecation")
	public static String randomString(int lo, int hi) {
		int n = rand(lo, hi);
		byte b[] = new byte[n];
		for (int i = 0; i < n; i++)
			b[i] = (byte) rand('a', 'z');
		return new String(b, 0);
	}

	public static int rand(int min, int max) {
		Random generator = new Random();
		// nextInt() can only return random number from 0 to a max.
		// can instead find a random number from 0 to max - min, and then add
		// min to bring number within range.
		return generator.nextInt(max - min) + min;
	}

	/**
	 * 
	 * @param appDriver
	 * @param locator
	 * @param expectedAttribute
	 * @return
	 */
	public String getAttribute(WebDriver appDriver, By locator,
			String expectedAttribute) {
		String attributeValue = null;
		try {
			appDriver.findElement(locator);
			attributeValue = appDriver.findElement(locator).getAttribute(
					expectedAttribute);
		} catch (NoSuchElementException e) {
		}
		return attributeValue;
	}

	/**
	 * Method for Explicit Wait
	 */
	public void enableExplicitWait() {
		if (!EXPLICIT_WAIT) {
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			EXPLICIT_WAIT = true;
		}
	}

	/**
	 * Method for Implicit Wait
	 */

	public void enableImplicitWait() {
		if (EXPLICIT_WAIT) {
			driver.manage().timeouts()
					.implicitlyWait(WAIT_PERIOD, TimeUnit.SECONDS);
			EXPLICIT_WAIT = false;
		}
	}

	// This Method is to verify page title on any page
	public static boolean verifyPageTitle(WebDriver driver, String PageTitle) {

		if (isTextPresentIn(driver, PageTitle, 10)) {
			return true;
		} else
			return false;

	}

	/**
	 * @param driver
	 * @param locator
	 * @return
	 */

	public String getText(WebDriver driver, By locator) {
		WebElement element = waitForElement(driver, locator, 20);
		if (element != null) {
			return driver.findElement(locator).getText().trim();
		}
		fail("[ELEMENT SEARCH FAILED] ELEMENT WITH DESCRIPTION " + locator
				+ " IS NOT FOUND; TEXT CANNOT BE EXTRACTED!");
		return "";
	}

	public static String getTextWithoutTrim(WebDriver driver, By locator) {
		WebElement element = waitForElement(driver, locator, 20);
		if (element != null) {
			return driver.findElement(locator).getText();
		}
		fail("[ELEMENT SEARCH FAILED] ELEMENT WITH DESCRIPTION " + locator
				+ " IS NOT FOUND; TEXT CANNOT BE EXTRACTED!");
		return "";
	}

	public String getText(WebDriver driver, By locator, int sec) {
		WebElement element = waitForElement(driver, locator, sec);
		if (element != null) {
			return driver.findElement(locator).getText().trim();
		}
		fail("[ELEMENT SEARCH FAILED] ELEMENT WITH DESCRIPTION " + locator
				+ " IS NOT FOUND; TEXT CANNOT BE EXTRACTED!");
		return "";
	}

	/**
	 * @param driver
	 * @param locator
	 * @param expectedText
	 * @return
	 */

	public static boolean textPresentIn(WebDriver driver, By locator,
			String expectedText) {
		try {
			if (elementPresent(driver, locator)) {
				String textFound = driver.findElement(locator).getText();
				if (textFound.contains(expectedText)) {
					return true;
				}
			}
		} catch (NoSuchElementException e) {
			fail("[ELEMENT SEARCH FAILED] ELEMENT WITH DESCRIPTION " + locator
					+ " NOT FOUND; TEXT CANNOT BE VERIFIED!");
			return false;
		}
		return false;
	}

	/**
	 * @param locator
	 * @param expectedText
	 * @param driver
	 * @return
	 */

	public boolean textNotPresentIn(By locator, String expectedText,
			WebDriver driver) {
		try {
			if (elementPresent(driver, locator)) {
				driver.findElement(locator);
				String textFound = driver.findElement(locator).getText();
				if (textFound.contains(expectedText)) {
					return false;
				}
			}
		} catch (NoSuchElementException e) {
			return true;
		}
		return true;
	}

	/**
	 * @param driver
	 * @param webElement
	 * @return
	 */

	public static boolean elementPresent(WebDriver driver, By webElement) {
		try {
			waitForElementPresent(driver, webElement);
			WebElement element = driver.findElement(webElement);
			if (element.isDisplayed()) {
				return true;
			} else {
				return false;
			}
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @param driver
	 * @param webElement
	 * @return
	 */

	public static boolean elementNotPresent(WebDriver driver, By webElement) {
		try {
			driver.findElement(webElement);
			return false;
		} catch (NoSuchElementException e) {
			return true;
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @return
	 */

	public boolean elementVisible(WebDriver driver, By locator) {
		try {
			WebElement element = driver.findElement(locator);
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * @param driver
	 * @param element
	 * @param sec
	 * @return
	 */

	public static boolean waitForElementToBePresent(WebDriver driver,
			By element, Integer sec) {

		Wait<WebDriver> wait = new WebDriverWait(driver, DELAY * sec);
		ExpectedCondition<WebElement> condition = new ElementPresent(element);
		try {
			@SuppressWarnings("unused")
			WebElement we = wait.until(condition);
			return true;
		} catch (WebDriverException e) {
			return false;
		}
	}

	public boolean waitForElementToBePresent(WebDriver driver,
			WebElement element, Integer wait) {
		Integer count = 0;

		while (count < wait) {
			try {
				if (element.isEnabled()) {
					return true;
				}
			} catch (NoSuchElementException e) {
				sleepFor(1000);
				count = count + 1;
			}
		}
		return false;
	}

	/**
	 * @param driver
	 * @param element
	 * @return
	 */

	public boolean waitForElementToBePresent(WebDriver driver, By element) {
		Integer count = 0;
		while (count < ELEMENT_WAIT_TIMEOUT) {
			if (elementPresent(driver, element)) {
				return true;
			} else {
				sleepFor(1000);
				count = count + 1;
			}
		}
		return false;
	}

	/**
	 * @param driver
	 * @param userNameTextBox
	 * @return
	 */

	public boolean elementPresent(WebDriver driver, WebElement userNameTextBox) {
		try {
			userNameTextBox.getTagName();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * @param optionLocator
	 * @return
	 */

	public String getSelectedOption(WebElement optionLocator) {
		Select listBox = new Select(optionLocator);
		return listBox.getFirstSelectedOption().getText();
	}

	/**
	 * @param optionLocator
	 * @param option
	 */

	public void selectOption(WebElement optionLocator, String option) {
		Select listBox = new Select(optionLocator);
		listBox.selectByValue(option);
	}

	/**
	 * @param driver
	 * @param wait
	 * @return
	 */

	public WebDriver switchWindow(WebDriver driver, Integer wait) {

		sleepFor(wait);
		for (String handle : driver.getWindowHandles()) {
			if (!driver.getWindowHandle().equals(handle)) {
				driver.switchTo().window(handle);
				return driver;
			}
		}
		return driver;
	}

	/**
	 * @param driver
	 */

	public void switchWindow(WebDriver driver) {

		for (String handle : driver.getWindowHandles()) {
			if (handle == "")
				continue;
			if (!driver.getWindowHandle().equals(handle)) {
				driver.switchTo().window(handle);
				break;
			}
		}
	}

	public void shiftFocusToSingleWindow(WebDriver driver) {

		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
			break;
		}
	}

	/**
	 * @param driver
	 * @param windowTitle
	 * @return
	 */

	public boolean switchWindow(WebDriver driver, String windowTitle) {
		for (String handle : driver.getWindowHandles()) {
			driver.switchTo().window(handle);
			driver.switchTo().window(handle).getTitle();
			if (driver.getTitle()
					.compareToIgnoreCase(windowTitle.toLowerCase()) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param driver
	 * @param locator
	 */

	public void hoverMouseOn(WebDriver driver, String locator) {
		WebDriverBackedSelenium selenium = new WebDriverBackedSelenium(driver,
				driver.getCurrentUrl());
		selenium.mouseOver(locator);
	}

	/**
	 * @param driver
	 * @param element
	 * @param wait
	 * @return
	 */

	public boolean waitForElementToDissappear(WebDriver driver, By element,
			Integer wait) {
		Integer count = 0;
		while (count < wait) {

			try {
				driver.findElement(element);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
				}
			} catch (NotFoundException e) {
				return true;
			}
			count = count + 1;
		}
		return false;
	}

	/**
	 * @param driver
	 * @param element
	 * @param sec
	 * @return
	 */

	public static WebElement waitForElement(WebDriver driver, By element,
			int sec) {
		Wait<WebDriver> wait = new WebDriverWait(driver, DELAY * sec);
		ExpectedCondition<WebElement> condition = new ElementPresent(element);
		try {
			WebElement we = wait.until(condition);
			return we;
		} catch (WebDriverException e) {
			return null;
		}
	}

	/**
	 * @param driver
	 * @param locator
	 * @param expectedText
	 * @param sec
	 * @return
	 */

	public static boolean verifyTextPresentIn(WebDriver driver, By locator,
			String expectedText, int sec) {
		sleepFor(3000);
		WebElement we = waitForElement(driver, locator, sec);
		if (we != null) {
			if (we.getText().contains(expectedText)) {
				return true;
			} else if (we.getText().equalsIgnoreCase(expectedText)) {
				return true;
			} else if (we.getText().replace("\n", "").replace("\r", "")
					.contains(expectedText.replace("\r", "").replace("\n", ""))) {
				return true;
			} else if (driver.getPageSource().contains(expectedText)) {
				return true;
			} else if (driver.getPageSource().equalsIgnoreCase(expectedText)) {
				return true;
			} else {
				fail("[TEXT SEARCH FAILED] ELEMENT WITH TEXT " + expectedText
						+ " NOT FOUND; TEXT CANNOT BE VERIFIED!");
				return false;
			}
		} else if (driver.getPageSource().contains(expectedText)) {
			return true;
		} else if (driver.getPageSource().equalsIgnoreCase(expectedText)) {
			return true;
		}
		fail("[TEXT SEARCH FAILED] ELEMENT WITH TEXT " + expectedText
				+ " NOT FOUND; TEXT CANNOT BE VERIFIED!");
		return false;
	}

	/**
	 * @param driver
	 * @param expectedText
	 * @param sec
	 * @return
	 */

	public boolean verifyTextPresentIn(WebDriver driver, String expectedText,
			int sec) {
		sleepFor((DELAY * sec) * 1000);
		if (driver.getPageSource().contains(expectedText)) {
			return true;
		} else {
			fail("[TEXT SEARCH FAILED] ELEMENT WITH TEXT " + expectedText
					+ " NOT FOUND; TEXT CANNOT BE VERIFIED!");
			return false;
		}

	}

	public static boolean verifyTextNotPresentIn(WebDriver driver, By locator,
			String expectedText, int sec) {
		WebElement we = waitForElement(driver, locator, sec);
		if (we != null) {
			if (we.getText().contains(expectedText)) {
				fail("[TEXT SEARCH FAILED] ELEMENT WITH TEXT " + expectedText
						+ " HAS BEEN FOUND; TEXT SHOULD NOT APPEAR!");
				return false;
			} else if (we.getText().equalsIgnoreCase(expectedText)) {
				fail("[TEXT SEARCH FAILED] ELEMENT WITH TEXT " + expectedText
						+ " HAS BEEN FOUND; TEXT SHOULD NOT APPEAR!");
				return false;
			} else if (driver.getPageSource().contains(expectedText)) {
				fail("[TEXT SEARCH FAILED] ELEMENT WITH TEXT " + expectedText
						+ " HAS BEEN FOUND; TEXT SHOULD NOT APPEAR!");
				return false;
			} else if (driver.getPageSource().equalsIgnoreCase(expectedText)) {
				fail("[TEXT SEARCH FAILED] ELEMENT WITH TEXT " + expectedText
						+ " HAS BEEN FOUND; TEXT SHOULD NOT APPEAR!");
				return false;
			} else {
				return true;
			}
		} else if (driver.getPageSource().contains(expectedText)) {
			fail("[TEXT SEARCH FAILED] ELEMENT WITH TEXT " + expectedText
					+ " HAS BEEN FOUND; TEXT SHOULD NOT APPEAR!");
			return false;
		} else if (driver.getPageSource().equalsIgnoreCase(expectedText)) {
			fail("[TEXT SEARCH FAILED] ELEMENT WITH TEXT " + expectedText
					+ " HAS BEEN FOUND; TEXT SHOULD NOT APPEAR!");
			return false;
		}
		return true;
	}

	/**
	 * @param driver
	 * @param expectedText
	 * @param sec
	 * @return
	 */

	public boolean verifyTextNotPresentIn(WebDriver driver,
			String expectedText, int sec) {
		sleepFor((DELAY * sec) * 1000);
		if (driver.getPageSource().contains(expectedText)) {
			fail("[TEXT SEARCH FAILED] ELEMENT WITH TEXT " + expectedText
					+ " HAS BEEN FOUND; TEXT SHOULD NOT APPEAR!");
			return false;
		} else {
			return true;
		}

	}

	/**
	 * @param driver
	 * @param locator
	 * @param linkAction
	 * @return
	 */

	public static WebElement link(WebDriver driver, By locator,
			String linkAction) {

		WebElement element = waitForElement(driver, locator, 20);
		waitForElementPresent(driver, locator);
		if (linkAction.compareToIgnoreCase("exists") != 0) {
			try {
				if (linkAction.compareToIgnoreCase("click") == 0) {
					element.click();
				} else {
					fail("LINK ACTION NOT VALID. TEST FAILED.");
				}
			} catch (IllegalStateException e) {
				element.click();
			} catch (NoSuchElementException e) {
				fail(locator + " LINK NOT FOUND. TEST FAILED.");
			} catch (NullPointerException e) {
				fail(locator + " LINK NOT FOUND. TEST FAILED.");
			}
		}

		return element;
	}

	/**
	 * @param driver
	 * @param locator
	 * @param textBoxAction
	 * @param value
	 * @return
	 */

	public static WebElement textbox(WebDriver driver, By locator,
			String textBoxAction, String value) {
		waitForElementPresent(driver, locator);
		WebElement element = waitForElement(driver, locator, 20);
		if (element == null) {
			element = waitForElement(driver, locator, 20);
		}

		try {
			if (element != null) {
				try {
					if (element.getAttribute("type").compareToIgnoreCase(
							"hidden") == 0) {
						element = null;
					}
				} catch (IllegalStateException e) {
				}
			}
		} catch (NullPointerException e) {
			// Catch any NPE that might be thrown by the getAttribute
		}

		if (textBoxAction.compareToIgnoreCase("exists") != 0) {
			try {
				if (textBoxAction.compareToIgnoreCase("enter") == 0) {
					element.clear();
					element.sendKeys(value);
				} else {
					fail("TEXTBOX ACTION NOT VALID. TEST FAILED.");
				}
			} catch (IllegalStateException e) {
				element.clear();
				element.sendKeys(value);
			} catch (NoSuchElementException e) {
				fail(locator + " TEXTBOX NOT FOUND. TEST FAILED.");
			} catch (NullPointerException e) {
				fail(locator + " TEXTBOX NOT FOUND. TEST FAILED.");
			}
		}
		return element;
	}

	/**
	 * @param driver
	 * @param locator
	 * @param selectAction
	 * @param value
	 * @return
	 */

	public Select selectByVisibleText(WebDriver driver, By locator,
			String selectAction, String value) {

		Select localeList;

		WebElement element = waitForElement(driver, locator, 20);
		if (element == null) {
			element = waitForElement(driver, locator, 20);
		}

		if (element != null) {
			try {
				if (element.getAttribute("type").equalsIgnoreCase("hidden")) {
					element = null;
				}
			} catch (IllegalStateException e) {
			} catch (NullPointerException e) {
			}
		}

		if (element != null) {
			try {
				localeList = new Select(element);
			} catch (IllegalStateException e) {
				localeList = new Select(element);
			}
		} else {
			localeList = null;
		}

		if (!selectAction.equalsIgnoreCase("exists")) {

			try {
				if (selectAction.equalsIgnoreCase("select")) {
					localeList.selectByVisibleText(value);
				} else {
					fail(" SELECT ACTION NOT VALID. TEST FAILED.");
				}
			} catch (IllegalStateException e) {
				localeList.selectByVisibleText(value);
			} catch (NoSuchElementException e) {
				fail(locator + " LISTBOX NOT FOUND. TEST FAILED.");
			} catch (NullPointerException e) {
				fail(locator + " LISTBOX NOT FOUND. TEST FAILED.");
			} catch (org.openqa.selenium.WebDriverException e) {
				fail(locator + " ACTION FAILED. TEST FAILED.");
			}
		}

		return localeList;
	}

	/**
	 * @param driver
	 * @param locator
	 * @param selectAction
	 * @param value
	 * @return
	 */

	public static Select selectByValue(WebDriver driver, By locator,
			String selectAction, String value) {

		Select localeList;

		WebElement element = waitForElement(driver, locator, 20);
		if (element == null) {
			element = waitForElement(driver, locator, 20);
		}

		if (element != null) {
			try {
				if (element.getAttribute("type").compareToIgnoreCase("hidden") == 0) {
					element = null;
				}
			} catch (IllegalStateException e) {
			} catch (NullPointerException e) {
			}

		}

		if (element != null) {
			localeList = new Select(element);
		} else {
			localeList = null;
		}

		if (selectAction.compareToIgnoreCase("exists") != 0) {
			try {
				if (selectAction.compareToIgnoreCase("select") == 0) {
					localeList.selectByValue(value);
				} else {
					fail(" SELECT ACTION NOT VALID. TEST FAILED.");
				}
			} catch (IllegalStateException e) {
				localeList.selectByValue(value);
			} catch (NoSuchElementException e) {
				fail(locator + " LISTBOX NOT FOUND. TEST FAILED.");
			} catch (NullPointerException e) {
				fail(locator + " LISTBOX NOT FOUND. TEST FAILED.");
			} catch (org.openqa.selenium.WebDriverException e) {
				fail(locator
						+ "WEB DRIVER EXCEPTION, ACTION FAILED. TEST FAILED.");
			}
		}
		return localeList;
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param radioAction
	 * @return
	 */

	public static void SelectValueFromDropDown(By Locator, String Select_Value) {
		try {
			new Select(driver.findElement(Locator))
					.selectByVisibleText(Select_Value);
		} catch (NoSuchElementException e) {
			fail(Locator + " SELECT ACTION NOT VALID. TEST FAILED.");

		}
	}

	public static void SelectValueFromDropDown_ByIndex(By Locator,
			int Select_Value) {
		try {
			new Select(driver.findElement(Locator))
					.selectByIndex(Select_Value);
		} catch (NoSuchElementException e) {
			fail(Locator + " SELECT ACTION NOT VALID. TEST FAILED.");

		}
	}

	public static void DeSelectAll(By Locator) {
		try {
			new Select(driver.findElement(Locator)).deselectAll();
		} catch (NoSuchElementException ignore) {
		}
	}

	public static WebElement radiobutton(WebDriver driver, By locator,
			String radioAction) {

		WebElement element = waitForElement(driver, locator, 20);

		if (radioAction.compareToIgnoreCase("exists") != 0) {
			try {
				if (radioAction.compareToIgnoreCase("click") == 0) {
					element.click();
				} else {
					fail("RADIO BTN ACTION NOT VALID. TEST FAILED.");
				}
			} catch (IllegalStateException e) {
				element.click();
			} catch (NoSuchElementException e) {
				fail(locator + " RADIO BTN NOT FOUND. TEST FAILED.");
			} catch (NullPointerException e) {
				fail(locator + " RADIO BTN NOT FOUND. TEST FAILED.");
			}
		}

		return element;
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param action
	 * @return
	 */

	public WebElement webelement(WebDriver driver, By locator, String action) {

		WebElement element = waitForElement(driver, locator, 20);

		if (action.compareToIgnoreCase("exists") != 0) {
			try {
				if (action.compareToIgnoreCase("click") == 0) {
					element.click();
				} else {
					fail("WEBELEMENT ACTION NOT VALID. TEST FAILED.");
				}
			} catch (IllegalStateException e) {
				element.click();
			} catch (NoSuchElementException e) {
				fail(locator + " WEBELEMENT NOT FOUND. TEST FAILED.");
			} catch (NullPointerException e) {
				fail(locator + " WEBELEMENT NOT FOUND. TEST FAILED.");
			}
		}

		return element;
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 * @param action
	 * @return
	 */

	public static WebElement button(WebDriver driver, By locator, String action) {

		WebElement element = waitForElement(driver, locator, 20);
		waitForElementPresent(driver, locator);
		if (action.compareToIgnoreCase("exists") != 0) {
			try {
				if (action.compareToIgnoreCase("click") == 0) {
					elementIsClickable(driver, locator);
					element.click();
				} else {
					fail("BUTTON ACTION NOT VALID. TEST FAILED.");
				}
			} catch (IllegalStateException e) {
				element.click();
			} catch (StaleElementReferenceException e) {

				System.out
						.println("StaleElementReferenceException Found, Wait for some more time.....");
				element = waitForElement(driver, locator, 20);
				sleepFor(8000);
			} catch (NoSuchElementException e) {
				fail(locator + " BUTTON NOT FOUND. TEST FAILED.");
			} catch (NullPointerException e) {
				fail(locator + " BUTTON NOT FOUND. TEST FAILED.");
			} catch (WebDriverException e) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			}
		}

		return element;
	}

	public static WebElement doubleClick_button(WebDriver driver, By locator,
			String action) {
		Actions actions = new Actions(driver);
		WebElement element = waitForElement(driver, locator, 20);
		waitForElementPresent(driver, locator);
		if (action.compareToIgnoreCase("exists") != 0) {
			try {
				if (action.compareToIgnoreCase("click") == 0) {
					elementIsClickable(driver, locator);
					actions.doubleClick(element);

				} else {
					fail("BUTTON ACTION NOT VALID. TEST FAILED.");
				}
			} catch (IllegalStateException e) {
				element.click();
			} catch (StaleElementReferenceException e) {

				System.out
						.println("StaleElementReferenceException Found, Wait for some more time.....");
				element = waitForElement(driver, locator, 20);
				sleepFor(8000);
			} catch (NoSuchElementException e) {
				fail(locator + " BUTTON NOT FOUND. TEST FAILED.");
			} catch (NullPointerException e) {
				fail(locator + " BUTTON NOT FOUND. TEST FAILED.");
			} catch (WebDriverException e) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			}
		}

		return element;
	}

	/**
	 * Checks to see if the element is enabled or disabled
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */

	public boolean isElementEnabled(WebDriver driver, By locator) {

		WebElement element = waitForElement(driver, locator, 20);
		if (element.isEnabled()) {
			return true;
		} else
			return false;
	}

	/**
	 * Similar to verifyTextPresentIn but with out the Assert stmt.
	 * 
	 * @param driver
	 * @param expectedText
	 * @param sec
	 * @return
	 */

	public static boolean isTextPresentIn(WebDriver driver,
			String expectedText, int sec) {
		sleepFor((DELAY * sec) * 1000);
		if (driver.getPageSource().contains(expectedText)) {
			return true;
		} else {
			if (driver.getTitle().contains(expectedText)) {
				return true;
			} else {
				return false;
			}
		}

	}

	/**
	 * Similar to verifyTextNotPresentIn but with out the Assert stmt.
	 * 
	 * @param driver
	 * @param expectedText
	 * @param sec
	 * @return
	 */

	public boolean isTextNotPresentIn(WebDriver driver, String expectedText,
			int sec) {
		sleepFor((DELAY * sec) * 1000);
		if (driver.getPageSource().contains(expectedText)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Text area to allow for comma seperated value
	 * 
	 * @param driver
	 * @param locator
	 * @param textBoxAction
	 * @param value
	 * @return
	 */

	public WebElement textarea(WebDriver driver, By locator,
			String textBoxAction, String value) {

		WebElement element = waitForElement(driver, locator, 20);
		if (element == null) {
			element = waitForElement(driver, locator, 20);
		}

		try {
			if (element != null) {
				try {
					if (element.getAttribute("type").compareToIgnoreCase(
							"hidden") == 0) {
						element = null;
					}
				} catch (IllegalStateException e) {
				}
			}
		} catch (NullPointerException e) {
			// Catch any NPE that might be thrown by the getAttribute
		}

		if (textBoxAction.compareToIgnoreCase("exists") != 0) {
			try {
				if (textBoxAction.compareToIgnoreCase("enter") == 0) {
					element.clear();
					if (value.contains(",")) {
						StringTokenizer st = new StringTokenizer(value, ",");
						while (st.hasMoreTokens()) {
							String val = st.nextToken();
							System.out.println(val);
							element.sendKeys(val);
							if (st.hasMoreElements()) {
								element.sendKeys(",");
							}
						}
					} else {
						element.sendKeys(value);
					}
				} else {
					fail("TEXTBOX ACTION NOT VALID. TEST FAILED.");
				}
			} catch (IllegalStateException e) {
				element.clear();
				if (value.contains(",")) {
					StringTokenizer st = new StringTokenizer(value, ",");
					while (st.hasMoreTokens()) {
						String val = st.nextToken();
						System.out.println(val);
						element.sendKeys(val);
						if (st.hasMoreElements()) {
							element.sendKeys(",");
						}
					}
				} else {
					element.sendKeys(value);
				}
			} catch (NoSuchElementException e) {
				fail(locator + " TEXTBOX NOT FOUND. TEST FAILED.");
			} catch (NullPointerException e) {
				fail(locator + " TEXTBOX NOT FOUND. TEST FAILED.");
			}
		}
		return element;
	}

	/**
	 * This call for selectFromDropDown is for when the element will only be in
	 * one location on the page and will call the version that takes in 2
	 * element locations with the second xpath as a blank string as that method
	 * will handle the logic of figure out which one to use.
	 * 
	 * @param driver
	 *            : The webdriver
	 * @param menuToClick
	 *            : The By element of the select menu to click on
	 * @param theListXpath
	 *            : The String of the xpath for the first element in the drop
	 *            down menu
	 * @param value
	 *            : The value to select from the drop down menu
	 */

	public void selectFromDropDown(WebDriver driver, By menuToClick,
			String theListXpath, String value) {
		selectFromDropDown(driver, menuToClick, theListXpath, "", value);
	}

	/**
	 * This method will take the by element of a drop down menu, click on it,
	 * then iterate through the list of the elements in the drop down menu
	 * looking for the element equal to value. The two strings are for when an
	 * element might be in one of two locations on a page
	 * 
	 * @param driver
	 *            : The webdriver
	 * @param menuToClick
	 *            : The By element of the select menu to click on
	 * @param theListXpath
	 *            : The String of the xpath for the first element in the drop
	 *            down menu
	 * @param secondListXpath
	 *            : The String of the xpath for the second element in the drop
	 *            down menu
	 * @param value
	 *            : The value to select from the drop down menu
	 */
	public void selectFromDropDown(WebDriver driver, By menuToClick,
			String theListXpath, String secondListXpath, String value) {

		String loopString;
		int count = 1;
		boolean foundValue = false;
		boolean usedSecondXpath = false;

		WebElement we = waitForElement(driver, menuToClick, 20);
		we.click();

		By theList = By.xpath(theListXpath);
		WebElement listElement = waitForElement(driver, theList, 20);
		// Since the page layout can change try the two different possible
		// xpaths for the drop down list
		if (listElement == null) {
			theList = By.xpath(secondListXpath);
			listElement = waitForElement(driver, theList, 20);
			usedSecondXpath = true;
		}

		// Loop through the elements looking for value, click if you find it
		while (listElement != null && foundValue == false) {
			if (listElement.getText().compareToIgnoreCase(value) == 0) {
				listElement.click();
				foundValue = true;
			} else {
				count++;
				if (!usedSecondXpath)
					loopString = theListXpath.concat("[" + count + "]");
				else
					loopString = secondListXpath.concat("[" + count + "]");
				theList = By.xpath(loopString);
				listElement = waitForElement(driver, theList, 20);
			}
		}
		if (listElement == null && foundValue == false)
			fail("TEST FAILED: Could not find value in select menu");
	}

	/**
	 * This method will select a value from a dropdown menu that is made up of
	 * div elements rather than a select (due to style issues)
	 * 
	 * @param driver
	 *            : The web driver for the page
	 * @param menuToClick
	 *            : The By element of the drop down menu
	 * @param theList
	 *            : The By of the list of values in the drop down. This should
	 *            be the classname used to label all the elements
	 * @param value
	 *            : The value being looked for
	 */

	public void selectFromDropDown(WebDriver driver, By menuToClick,
			By theList, String value) {

		boolean foundValueInDropDown = false;

		// Click the initial div element to pull open the drop down
		WebElement we = waitForElement(driver, menuToClick, 20);
		we.click();

		// Take the List by element and get all the elements on the page with
		// that as the class
		List<WebElement> dropDownListElements = driver.findElements(theList);

		// Go through the dropDownListElements and find the one with text equal
		// to value, and click it
		for (WebElement element : dropDownListElements) {
			if (element.getText().equalsIgnoreCase(value) == true) {
				element.click();
				foundValueInDropDown = true;
			}
		}
		if (foundValueInDropDown == false)
			fail("TEST FAILED: Could not find value in select menu");
	}

	public static void saveScreenShot(final String pSource, final String name,
			final String folderName) {
		try {
			final File f = new File(REPORT_DIRECTORY + "/" + folderName);
			try {
				f.mkdir();
			} catch (final Exception e) {
				e.fillInStackTrace();
			}
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new FileWriter(f + "/" + name
						+ ".html"));
				out.write(pSource.replace("/is-bin", "http://" + DOMAIN
						+ "/is-bin"));
			} finally {
				if (null != out) {
					out.close();
				}
			}
		} catch (final Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	/**
	 * Converts string to int
	 * 
	 * @param num
	 * @return
	 */
	public static int convertStringToInt(final String num) {
		return Integer.parseInt(num);
	}

	/**
	 * Generate Random Email
	 * 
	 * @param ID
	 * @return
	 */
	public static String generateRandomID(String ID) {

		Random randomGenrator = new Random();
		int randomInt = randomGenrator.nextInt(999999);
		String uniqueID = ID + randomInt;
		return uniqueID;

	}

	/**
	 * Generate Random Email
	 * 
	 * @param Locator
	 * @param driver
	 */
	@SuppressWarnings("unused")
	public static boolean elementIsClickable(WebDriver driver, By Locator) {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		try {
			WebElement element = wait.until(ExpectedConditions
					.elementToBeClickable(Locator));
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			fail("Is Clickable Method: Failing for some other exceptions");
			return false;
		}

		return true;

	}

	/**
	 * ��* This method generates random string ��* @return ��
	 */

	public static String newString(int RANDOM_STRING_LENGTH) {

		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
			int number = getRandomNumber();
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	/**
	 * This method generates random numbers
	 * 
	 * @return int
	 */

	private static int getRandomNumber() {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(CHAR_LIST.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}

	/**
	 * Generate Random Email
	 * 
	 * @return
	 */
	public static String generateFLName(String initial, int length) {
		String name = initial + newString(length);
		return name;
	}

	public static boolean CookieNullVerification(String CookieName) {
		sleepFor(3000);
		boolean Status;
		Status = driver.manage().getCookieNamed(CookieName) == null;
		return Status;
	}

	public static void DeleteCookie(String CookieName) {
		driver.manage().deleteCookieNamed(CookieName);
	}

	public static void AcceptifAlertPresent() {
		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException ignore) {
		}
	}

	public static boolean ErrorMessageValidation(By element, String MessageText) {

		boolean Status = false;

		if (driver.findElement(element).getText()
				.equalsIgnoreCase(MessageText)) {
			Status = true;
		} else {
			fail("Failed when validating Error Message..");
		}
		return Status;
	}

	public static boolean VerifynewTab_And_Close(String VerifURLText) {

		boolean Status = false;
		String currentHandle = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		handles.remove(currentHandle);
		if (handles.size() > 0) {
			driver.switchTo().window(handles.iterator().next());

			Status = driver.getCurrentUrl().contains(VerifURLText);
		}
		driver.close();
		driver.switchTo().window(currentHandle);
		waitForPageToLoad(driver);
		return Status;
	}

	public static void MouseOVerAndClick(By MovetoElement, By ClickElement) {
		Actions act = new Actions(driver);
		// JavascriptExecutor executor = (JavascriptExecutor)driver;

		WebElement element = driver.findElement(MovetoElement);
		act.moveToElement(element);
		act.click();
		act.perform();
		sleepFor(2000);
		waitForElementPresent(driver, ClickElement);

	}

	public static void MouseOverElement(By Locator) {
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(Locator)).build().perform();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void waitForElementPresent(WebDriver driver, By locator) {
		String documentation = "Waiting upto " + ACTION_TIMEOUT_TIME
				+ "ms for element with locator: \"" + locator
				+ "\" to appear on page.";
		System.out.println(documentation);
		try {
			long timeStamp = System.currentTimeMillis();
			while (driver.findElements(locator).size() == 0
					&& (System.currentTimeMillis() - timeStamp <= ACTION_TIMEOUT_TIME)) {
				Thread.sleep(500);
			}
			if (System.currentTimeMillis() - timeStamp > ACTION_TIMEOUT_TIME) {
				throw new TimeoutException("Waited 10 seconds for " + locator
						+ " but failed to find its presence.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void do_Refresh() {
		driver.navigate().refresh();
		waitForPageToLoad(driver);
	}

	public static String genarate_FutureTimeString() {
		Date date = new Date();

		SimpleDateFormat SDF = new SimpleDateFormat("yyyy");
		String Day = SDF.format(date);
		int Year = Integer.parseInt(Day);
		Year = Year + 1;

		SimpleDateFormat SDF1 = new SimpleDateFormat("MM");
		String Month = SDF1.format(date);

		SimpleDateFormat SDF2 = new SimpleDateFormat("dd");
		String Date = SDF2.format(date);

		SimpleDateFormat SDF3 = new SimpleDateFormat("hh:mm:ss");
		String Time = SDF3.format(date);

		String CurrentBeforeDay = (Year + "-" + Month + "-" + Date + " " + Time);
		return CurrentBeforeDay;
	}

}

class ElementPresent implements ExpectedCondition<WebElement> {
	private final By locator;

	public ElementPresent(By locator) {
		this.locator = locator;
	}

	public WebElement apply(WebDriver driver) {
		return driver.findElement(locator);
	}

}
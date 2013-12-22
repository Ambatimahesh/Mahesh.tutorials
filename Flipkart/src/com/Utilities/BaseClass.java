package com.Utilities;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass extends WrapperFunctions {

	protected static WebDriver driver = null;

	@BeforeClass(alwaysRun = true, timeOut = 300000)
	@Parameters({ "Environment", "ApplicationToTest", "Browser",
			"BrowserVersion", "Os", "SauceKey" })
	public static void OpenHome(String Environment, String ApplicationToTest,
			String Browser, String BrowserVersion, String Os, String SauceKey)
			throws Exception {
		DesiredCapabilities caps = new DesiredCapabilities();
		if (Environment.contains("Local")) {
			switch (Browser) {
			case "FF":
				driver = new FirefoxDriver();
				break;
			case "Chrome":
				driver = new ChromeDriver();
				break;
			case "IE":
				driver = new InternetExplorerDriver();
				break;
			}
		} else if (Environment.contains("SauceLabs")) {
			switch (Browser) {
			case "FF":
				DesiredCapabilities.firefox();
				caps.setCapability("platform", Os);
				caps.setCapability("version", BrowserVersion);
				driver = new RemoteWebDriver(new URL(SauceKey), caps);
				break;
			case "Chrome":
				DesiredCapabilities.chrome();
				caps.setCapability("platform", Os);
				caps.setCapability("version", BrowserVersion);
				driver = new RemoteWebDriver(new URL(SauceKey), caps);
				break;
			case "IE":
				DesiredCapabilities.internetExplorer();
				caps.setCapability("platform", Os);
				caps.setCapability("version", BrowserVersion);
				driver = new RemoteWebDriver(new URL(SauceKey), caps);
				break;
			}
		}
		System.out
				.println("====================================================================");
		System.out.println("Environment:: " + Environment);
		System.out.println("Browser:: " + Browser);
		System.out
				.println("====================================================================");
		driver.get(ApplicationToTest);
		driver.manage().window().maximize();
		waitForPageToLoad(driver);
	}

	@AfterClass(alwaysRun = true)
	public void CloseApplication() {
		try {
			String className = this.getClass().getSimpleName();
			driver.manage().deleteAllCookies();
			driver.close();
			System.out
					.println("====================================================================");
			System.out.println("Completed test " + className + "." + "()");
			System.out
					.println("====================================================================");
		} catch (Exception ignore) {

		}
		try {
			if (driver != null) {
				driver.quit();
				driver = null;
			}
		} catch (Exception ignore) {
		}
	}
}

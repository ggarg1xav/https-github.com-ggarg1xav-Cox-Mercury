package com.xavient.util;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.xavient.pages.DashBoardView;

public class BaseClass {
	WebDriver driver;
	Logger logger = Logger.getLogger(BaseClass.class);
	/**
	 * Method is initalizing driver with defined browser properties.
	 * @author AJameel
	 * @param browser
	 * @return driver Object
	 */
	public  WebDriver Browser_Selection(String browser)  {

		String path = System.getProperty("user.dir");
		if (browser.equalsIgnoreCase("firefox")) {
			// create firefox instance
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}

		else if (browser.equalsIgnoreCase("Chrome")) {
			// set path to chromedriver.exe
		
			System.setProperty(
					"webdriver.chrome.driver",
					path
					+ Properties_Reader.readProperty("chrome_driver"));

			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}

		else if (browser.equalsIgnoreCase("ie")) {

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities
			.setCapability(
					InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
					true);
			capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);

			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			System.setProperty(
					"webdriver.ie.driver",
					path
					+ Properties_Reader.readProperty("ie_driver"));
			driver = new InternetExplorerDriver(capabilities);
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			driver.manage().window().maximize();
			logger.info("Browser launched successfully");
		}

		else {
			// If no browser passed throw exception
			logger.info("Browser is not correct");
			try {
				throw new Exception("Browser is not correct");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return driver;
	}
}
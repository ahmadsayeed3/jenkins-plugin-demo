package com.pCloudy.testNG;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class LocalRun {

	static AndroidDriver<WebElement> driver;

	public static void main(String[] args) throws MalformedURLException {
		try {
			String deviceName = System.getenv("deviceName");
			System.out.println("Username using environment variable in windows : " + deviceName);

			String pCloudy_CloudEndpoint = System.getenv("pCloudy_CloudEndpoint");
			System.out.println("Username using environment variable in windows : " + pCloudy_CloudEndpoint);

			System.out.println();
			Map<String, String> env = System.getenv();
			for (String envName : env.keySet()) {
				System.out.format("%s=%s%n", envName, env.get(envName));
			}
			System.out.println();

			System.out.println("Start.................");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("pCloudy_Username", "ahmad.sayeed@sstsinc.com");
			capabilities.setCapability("pCloudy_ApiKey", "s6bxrj8jgmyz848k6h4my3k9");
			capabilities.setCapability("pCloudy_DurationInMinutes", 20);
			capabilities.setCapability("newCommandTimeout", 600);
			capabilities.setCapability("launchTimeout", 90000);
			capabilities.setCapability("pCloudy_DeviceFullName", deviceName);
			capabilities.setCapability("platformVersion", "9.0.0");
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("automationName", "uiautomator2");
			capabilities.setCapability("pCloudy_ApplicationName", "pCloudyAppiumDemo.apk");
			capabilities.setCapability("appPackage", "com.pcloudy.appiumdemo");
			capabilities.setCapability("appActivity", "com.ba.mobile.LaunchActivity");
			capabilities.setCapability("pCloudy_WildNet", "false");
			capabilities.setCapability("pCloudy_EnableVideo", "false");
			capabilities.setCapability("pCloudy_EnablePerformanceData", "false");
			capabilities.setCapability("pCloudy_EnableDeviceLogs", "false");
			driver = new AndroidDriver<WebElement>(new URL("https://device.pcloudy.com/appiumcloud/wd/hub"),
					capabilities);

			System.out.println("Session Id: " + driver.getSessionId().toString());

			WebElement element = driver
					.findElement(By.xpath("//android.widget.Button[@resource-id='com.pcloudy.appiumdemo:id/accept']"));
			String path = elementScreenshot(element);
			System.out.println("Path: " + path);

			BufferedImage originalImage = ImageIO.read(new File(path));
			Color pixelColor = new Color(originalImage.getRGB(25, 25));
			int red = pixelColor.getRed();
			int green = pixelColor.getGreen();
			int blue = pixelColor.getBlue();
			System.out.println(red + ", " + green + ", " + blue);

			Graphics2D graphics = originalImage.createGraphics();
			pixelColor = graphics.getBackground();
			red = pixelColor.getRed();
			green = pixelColor.getGreen();
			blue = pixelColor.getBlue();
			System.out.println("Backgroud: " + red + ", " + green + ", " + blue);
		} catch (Exception e) {
			System.out.println("Exception while: ");
			e.printStackTrace();
		} finally {
			if (driver != null)
				driver.quit();
		}

		System.out.println("End.................");
	}

	public static String elementScreenshot(WebElement ele) {

		File screenshotLocation = null;
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			BufferedImage fullImg = ImageIO.read(scrFile);
			// Get the location of element on the page
			Point point = ele.getLocation();
			// Get width and height of the element
			int eleWidth = ele.getSize().getWidth();
			int eleHeight = ele.getSize().getHeight();
			// Crop the entire page screenshot to get only element screenshot
			BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
			ImageIO.write(eleScreenshot, "png", scrFile);

			String path = "screenshots/" + UUID.randomUUID() + "" + ".png";

			screenshotLocation = new File(System.getProperty("user.dir") + "/" + path);
			FileUtils.copyFile(scrFile, screenshotLocation);

			System.out.println(screenshotLocation.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return screenshotLocation.toString();

	}

}

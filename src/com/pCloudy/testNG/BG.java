package com.pCloudy.testNG;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
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

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class BG {

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
//			capabilities.setCapability("pCloudy_Username", "ahmad.sayeed@sstsinc.com");
//			capabilities.setCapability("pCloudy_ApiKey", "s6bxrj8jgmyz848k6h4my3k9");
//			capabilities.setCapability("pCloudy_DurationInMinutes", 20);
//			capabilities.setCapability("pCloudy_DeviceFullName", deviceName);
//			capabilities.setCapability("pCloudy_WildNet", "false");
//			capabilities.setCapability("pCloudy_EnableVideo", "false");
//			capabilities.setCapability("pCloudy_EnablePerformanceData", "false");
//			capabilities.setCapability("pCloudy_EnableDeviceLogs", "false");
//			capabilities.setCapability("platformVersion", "9.0.0");
//			capabilities.setCapability("pCloudy_ApplicationName", "pCloudyAppiumDemo.apk");
			
			
			capabilities.setCapability("noSign", true);
			capabilities.setCapability("noReset", true);
			capabilities.setCapability("fullReset", false);
			capabilities.setCapability("deviceName", "PL2GAR4832302659");
			capabilities.setCapability("newCommandTimeout", 600);
			capabilities.setCapability("launchTimeout", 90000);
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("automationName", "uiautomator2");
			
			capabilities.setCapability("appPackage", "edu.northwell.dpxapp");
			capabilities.setCapability("appActivity", "edu.northwell.dpxapp.MainActivity");

//			driver = new AndroidDriver<WebElement>(new URL("https://device.pcloudy.com/appiumcloud/wd/hub"),
//					capabilities);
			driver = new AndroidDriver<WebElement>(new URL("http://localhost:4723/wd/hub"), capabilities);

			System.out.println("Session Id: " + driver.getSessionId().toString());

			Thread.sleep(7 * 1000);
			driver.findElement(By.xpath("//android.widget.EditText[@content-desc=\"UserSignin-Input-1:Email input\"]"))
					.sendKeys("testpatientdfd+24@gmail.com");

			Thread.sleep(5 * 1000);
			driver.findElement(
					By.xpath("//android.widget.EditText[@content-desc=\"UserSignin-Input-2:Password input\"]"))
					.sendKeys("Testing1!");

			Thread.sleep(5 * 1000);
			driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"UserSignin-Button-1:Log in button\"]/android.widget.TextView"))
					.click();

			Thread.sleep(10 * 1000);
			WebElement element = driver.findElement(
					By.xpath("//android.widget.Button[@content-desc=\"Home-AppTile-Button_1:Get started button\"]"));
			String path = elementScreenshot(element);

			printColor(path);
			
			Thread.sleep(5 * 1000);
			// Swipe Up
			swipeUPMost();
			
			Thread.sleep(5 * 1000);
			element = driver.findElement(By.xpath(
					"//android.view.View[@content-desc=\"HomeCarousel-Article-0\"]/android.view.ViewGroup[2]/android.widget.TextView[1]"));

			path = elementScreenshot(element);
			System.out.println("Path: " + path);
			printColor(path);

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

	private static void printColor(String path) throws IOException {
		System.out.println("Path: " + path);
		BufferedImage originalImage = ImageIO.read(new File(path));
		Color pixelColor = new Color(originalImage.getRGB(10, 10));
		int red = pixelColor.getRed();
		int green = pixelColor.getGreen();
		int blue = pixelColor.getBlue();
		
		String hex = String.format("#%02x%02x%02x", red, green, blue);  
		System.out.println(red + ", " + green + ", " + blue + " : " + hex);
//		Graphics2D graphics = originalImage.createGraphics();
//		pixelColor = graphics.getBackground();
//		red = pixelColor.getRed();
//		green = pixelColor.getGreen();
//		blue = pixelColor.getBlue();
//		System.out.println("Backgroud: " + red + ", " + green + ", " + blue);
	}
	
	public static void swipeUPMost() throws InterruptedException {
		org.openqa.selenium.Dimension windowSize = driver.manage().window().getSize();

		int startx = 10;
		int startY = (int) (windowSize.height * 0.99);
		int anchor = (int) (windowSize.width * 0.3);
		new TouchAction<>(driver).press(PointOption.point(anchor, startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
				.moveTo(PointOption.point(anchor, startx)).release().perform();
	}
}

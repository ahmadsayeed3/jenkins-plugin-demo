package com.pCloudy.testNG;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Runner {

	AppiumDriverLocalService service;
	AppiumDriver<WebElement> driver;
	String folder_name;
	DateFormat df;
	
	@BeforeTest
	public void setUpSuite() throws Exception {
		
	}
	
	@Parameters({"deviceName"})
	@BeforeMethod
	public void prepareTest(String deviceName) throws IOException, InterruptedException {
		
		
		System.out.println();
		Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {
			System.out.format("%s=%s%n", envName, env.get(envName));
		}
		System.out.println();
		
//		DesiredCapabilities capabilities = new DesiredCapabilities();
//		
//		capabilities.setCapability("pCloudy_Username", "Enter your email-id");
//		capabilities.setCapability("pCloudy_ApiKey", "Enter your API Key");
//		capabilities.setCapability("pCloudy_ApplicationName", "pCloudyAppiumDemo.apk");
//		capabilities.setCapability("pCloudy_DurationInMinutes", 10);
//		capabilities.setCapability("pCloudy_DeviceManafacturer", deviceName);
//		//capabilities.setCapability("pCloudy_DeviceVersion", "8.0.0");
//		//capabilities.setCapability("pCloudy_DeviceFullName", "Samsung_GalaxyTabA_Android_7.1.1");
//		capabilities.setCapability("newCommandTimeout", 600);
//		capabilities.setCapability("launchTimeout", 90000);
//		capabilities.setCapability("automationName", "uiautomator2");
//		capabilities.setCapability("appPackage", "com.pcloudy.appiumdemo");
//		capabilities.setCapability("appActivity", "com.ba.mobile.LaunchActivity");
//		driver = new AndroidDriver(new URL("https://device.pcloudy.com/appiumcloud/wd/hub"), capabilities);
		
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
		AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(new URL("https://device.pcloudy.com/appiumcloud/wd/hub"), capabilities);
		
	}

	@Test
	public void Test() throws IOException, InterruptedException {

		//Click on Accept button
        driver.findElement(By.xpath("//android.widget.Button[@resource-id='com.pcloudy.appiumdemo:id/accept']")).click(); 
        captureScreenShots();
        
        //Click on Flight button
        driver.findElement(By.xpath("//android.widget.Button[@resource-id='com.pcloudy.appiumdemo:id/flightButton']")).click();
        captureScreenShots();
        
        //Select from location
        driver.findElement(By.xpath("//android.widget.Spinner[@resource-id='com.pcloudy.appiumdemo:id/spinnerfrom']")).click();
        captureScreenShots();
		driver.findElement(By.xpath("//android.widget.CheckedTextView[@resource-id='android:id/text1' and @text='Bangalore, India (BLR)']")).click();
		captureScreenShots();
		
		//Select to location
		driver.findElement(By.xpath("//android.widget.Spinner[@resource-id='com.pcloudy.appiumdemo:id/spinnerto']")).click();
		captureScreenShots();
        driver.findElement(By.xpath("//android.widget.CheckedTextView[@resource-id='android:id/text1' and @text='Pune, India (PNQ)']")).click();
        captureScreenShots();
        
        //Select one way trip
        driver.findElement(By.xpath("//android.widget.RadioButton[@resource-id='com.pcloudy.appiumdemo:id/singleTrip']")).click();
        captureScreenShots();
        
        //Select departure time
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.pcloudy.appiumdemo:id/txtdepart']")).click();
        captureScreenShots();
        driver.findElement(By.xpath("//android.widget.Button[@resource-id='android:id/button1' and @text='OK']")).click();
        captureScreenShots();
        
        //Click on search flights button
        driver.findElement(By.xpath("//android.widget.Button[@resource-id='com.pcloudy.appiumdemo:id/searchFlights']")).click();
        captureScreenShots();
	}


	@AfterMethod
	public void endTest() throws  IOException {

		driver.quit();
	}

	//Capture screenshot
	public void captureScreenShots() throws IOException {
        folder_name="screenshot";
        File f=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        //Date format for screenshot file name
        df=new  SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        //create dir with given folder name
        new File(folder_name).mkdir();
        //Setting file name
        String file_name=df.format(new Date())+".png";
        //copy screenshot file into screenshot folder.
        FileUtils.copyFile(f, new File(folder_name + "/" + file_name));
    }
}

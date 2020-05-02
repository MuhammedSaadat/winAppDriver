package Automation.winAppDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;


//automation.careSuiteClient.utility
public class CareSuiteMedPassScenario extends CareSuiteMedPassScenarioMethods{


	@BeforeClass
	public void setup() {
		try {
			// Launch application
			DesiredCapabilities appCapabilities = new DesiredCapabilities();
			appCapabilities.setCapability("app", "C:\\Program Files (x86)\\CareSuite Client TEST\\QuickMAR.exe");
			driver = new WindowsDriver<WebElement>(new URL("http://127.0.0.1:4723"), appCapabilities);

			//Switching to Login window			
			desktopSession = createDesktopSession();
			waitForElement2("QuickMAR Login","Name",desktopSession,20);
						
			//Get Window title
			System.out.println("Window title is : " + desktopSession.findElementByName("TitleBar").getAttribute("Value.Value"));

			// Entering credentials
			/*desktopSession.findElementByAccessibilityId("UserNameTextBox").sendKeys("test3");
			desktopSession.findElementByAccessibilityId("PasswordTextBox").sendKeys("test");*/
			desktopSession.findElementByAccessibilityId("UserNameTextBox").sendKeys("Facility_Admin__CSS_AUT");
			desktopSession.findElementByAccessibilityId("PasswordTextBox").sendKeys("test");
			
			// Clicking on 'OK' button
			desktopSession.findElementByAccessibilityId("FormOkButton").click();
			//waitForElement2("  Park Place Manor 3  (Facil ID: 221)","Name",desktopSession,30);
			waitForElement2("radTitleBar1","AccessID",desktopSession,30);
			
			// Switching to Home window
			Set<String> allwindowHandles  = driver.getWindowHandles();
			driver.switchTo().window(allwindowHandles.iterator().next());
			
			// Maximizing the window
			driver.manage().window().maximize();
			Thread.sleep(4000);
			
			//driver.getWindowHandles().size(); driver.getTitle();
			System.out.println("Window title is : " + driver.findElementByName("radTitleBar1").getAttribute("Value.Value"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	@Test
	public void medPassScenario1() {
		try {

			//Navigate to Residents -> Manage Orders
			navigateToResidentsManageOrders();
			
			//Creating a PRN Medication
			createPRNMedication();
			
			//Navigate to Pass Meds -> PRN
			navigateToPassMedsPRN();
			
			//Selecting the Resident
			selectingResident();
			
			//Selecting the Medication
			selectingMedication();
			
			//Enter the details and complete
			enterDetailsAndComplete();
			
			//Selecting the Resident
			selectingResident();
			
			//Selecting the Medication history
			selectingMedication1();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	@AfterClass
	public void tearDown() {
		try {

			// Clicking on 'Log off' and 'Yes' button
			driver.findElementByName("Log off").click();
			waitForElement2("Yes","Name",driver,20);
			driver.findElementByName("Yes").click();
			
			//Clicking on 'Cancel' button			
			desktopSession = createDesktopSession();
			waitForElement2("QuickMAR Login","Name",desktopSession,20);
			WebElement quickMARLoginWindow = desktopSession.findElementByName("QuickMAR Login");
			WebElement cancelButton = quickMARLoginWindow.findElement(By.name("Cancel"));
			cancelButton.click();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	

}
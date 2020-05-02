package Automation.winAppDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
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

public class careSuiteApp {

	// Class variables
	WindowsDriver<WebElement> driver;
	WindowsDriver<WebElement> desktopSession;

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
			desktopSession.findElementByAccessibilityId("UserNameTextBox").sendKeys("test3");
			desktopSession.findElementByAccessibilityId("PasswordTextBox").sendKeys("test");
			/*desktopSession.findElementByAccessibilityId("UserNameTextBox").sendKeys("Facility_Admin__CSS_AUT");
			desktopSession.findElementByAccessibilityId("PasswordTextBox").sendKeys("test");*/
			
			// Clicking on 'OK' button
			desktopSession.findElementByAccessibilityId("FormOkButton").click();
			waitForElement2("  Park Place Manor 3  (Facil ID: 221)","Name",desktopSession,30);
			//waitForElement2("  Cigniti Automated  (Facil ID: 591)","Name",desktopSession,30);
			
			// Switching to Home window
			Set<String> allwindowHandles  = driver.getWindowHandles();
			driver.switchTo().window(allwindowHandles.iterator().next());
			
			// Maximizing the window
			driver.manage().window().maximize();
			System.out.println("Window title is : " + driver.findElementByName("radTitleBar1").getAttribute("Value.Value"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	
	// Working code : Residents - Manage Orders - Select a resident - Add -
	// Medication - From dropdown - Administer dropdown
	// - Time dropdown - Enter date : 'Start' field - 'Given by:' checkbox,Radio
	// button
	@Test(enabled = true)
	public void careSuite2() throws InterruptedException, MalformedURLException {
		try {
			
			// Clicking on 'Resident' menu
			waitForElement2("Residents","Name",driver,20); 
			driver.findElementByName("Residents").click();
			
			//Create a desktop session
			desktopSession = createDesktopSession();

			// Creating a 'Manage Orders' option using desktop session object
			waitForElement2("Manage Orders","Name",desktopSession,20); 
			desktopSession.findElementByName("Manage Orders").click();
			desktopSession.quit();
			
			//The below is working code for IsDisplayed and IsEnabled
			/*//Validate 'Add' button is displayed and disabled
			waitForElement2("btnAddDropdown","Name",driver,20); 
			try
			{
			System.out.println("Add button is displayed : " + driver.findElementByName("btnAddDropdown").isDisplayed());
			}
			catch (Exception e)
			{
				
			}
			System.out.println("Add button is enabled : " + driver.findElementByName("btnAddDropdown").isEnabled());*/
			
			//Clicking on the resident using application session object
			waitForElement2("lvPatients","AccessId",driver,20); 
			WebElement residentsPane=driver.findElementByAccessibilityId("lvPatients");
			WebElement selectResidentResidentsPane=residentsPane.findElement(By.xpath("//*[contains(@AutomationId,'ListViewSubItem-0')]"));
			selectResidentResidentsPane.click();
			
			//Selecting a medication
			waitForElement2("lvwTreatments","AccessId",driver,20); 
			WebElement medicationPane=driver.findElementByAccessibilityId("lvwTreatments");
			WebElement selectMedicationResidentsPane=medicationPane.findElement(By.xpath("//*[contains(@AutomationId,'ListViewItem-0')]"));
			selectMedicationResidentsPane.click();
		
			//Attaching a document
			//Clicking on attachment icon
			waitForElement2("btnDocs","AccessId",driver,20);
			WebElement attachmentIcon = driver.findElementByAccessibilityId("btnDocs");
			attachmentIcon.click();
			
			//Clicking on 'Add' button
			waitForElement2("DocumentAttachmentsFrm","AccessId",driver,20);
			WebElement documentAttachmentsWindow = driver.findElementByAccessibilityId("DocumentAttachmentsFrm");
			WebElement addButtonDocumentAttachmentsWindow = documentAttachmentsWindow.findElement(By.name("Add"));
			addButtonDocumentAttachmentsWindow.click();
			
			//Selecting the document
			waitForElement2("Open","Name",driver,20);
			WebElement fileExplorerWindow = documentAttachmentsWindow.findElement(By.name("Open"));
			waitForElement2("File name:","Name",driver,20);
			WebElement fileNameTextboxFileExplorerWindow = fileExplorerWindow.findElement(By.xpath("//*[@ClassName='Edit'][@Name='File name:']"));
			fileNameTextboxFileExplorerWindow.sendKeys("C:\\CareSuiteManagerAutomationFiles\\AutomationTestingFilesUpload\\SampleTestInvoice.pdf");
			
			//Clicking on Open button
			WebElement openButtonFileExplorerWindow = fileExplorerWindow.findElement(By.xpath("//*[@ClassName='Button'][@Name='Open']"));
			openButtonFileExplorerWindow.click();
			
			//Clicking on 'Save' button
			waitForElement2("Save","Name",driver,20);
			WebElement saveButtonDocumentAttachmentsWindow = documentAttachmentsWindow.findElement(By.name("Save"));
			saveButtonDocumentAttachmentsWindow.click();
			
			//Closing the window
			desktopSession = createDesktopSession();
			waitForElement2("DocumentAttachmentsFrm","AccessId",desktopSession,20);
			documentAttachmentsWindow = desktopSession.findElementByAccessibilityId("DocumentAttachmentsFrm");
			documentAttachmentsWindow.click();
			Actions actions = new Actions(desktopSession);
	        actions.keyDown(Keys.ALT);
	        actions.sendKeys(Keys.F4);
	        actions.keyUp(Keys.ALT);
	        actions.perform();
			
	        
	    	// Clicking on 'Add' dropdown
			waitForElement2("btnAddDropdown","Name",driver,20); 
			System.out.println("Add button is enabled : " + driver.findElementByName("btnAddDropdown").isEnabled());
			driver.findElementByName("btnAddDropdown").click();

			// Create a desktop session
			desktopSession = createDesktopSession();

			// Selecting 'Medication' option
			waitForElement2("Medication","Name",desktopSession,20); 
			desktopSession.findElementByName("Medication").click();
			desktopSession.quit();
			
			// Selecting option from 'From' dropdown
			waitForElement2("Given by:","Name",driver,20);
			WebElement fromDropdown = driver.findElementByAccessibilityId("unitOfMeasureListRadComboBox");
			WebElement editBoxFromDropdown = fromDropdown.findElement(By.xpath("//Edit"));
			while (true) {
				editBoxFromDropdown.sendKeys(Keys.DOWN);
				System.out.println(editBoxFromDropdown.getAttribute("Value.Value"));
				if (editBoxFromDropdown.getAttribute("Value.Value").equalsIgnoreCase("POWDER"))
					break;

			}

			// Selecting option from 'Administer in' dropdown
			WebElement administerInDropdown = driver.findElementByAccessibilityId("medPassSelectionComboBox1");
			WebElement openButtonAdministerInDropdown = administerInDropdown.findElement(By.name("Open"));
			openButtonAdministerInDropdown.click();
			desktopSession = createDesktopSession();
			WebElement listDropdown = desktopSession.findElementByClassName("ComboLBox");
			WebElement optionListDropdown = listDropdown.findElement(By.name("Treatment Pass"));
			optionListDropdown.click();
			desktopSession.quit();

			// Select time from dropdown
			WebElement timeDropdown = driver.findElementByAccessibilityId("cboTimeOfDay");
			WebElement openButtonTimeDropdown = timeDropdown.findElement(By.name("Open"));
			openButtonTimeDropdown.click();
			desktopSession = createDesktopSession();
			WebElement timeListDropdown = desktopSession.findElementByClassName("ComboLBox");
			WebElement optionTimeListDropdown = timeListDropdown.findElement(By.name("12:00 AM"));
			optionTimeListDropdown.click();
			desktopSession.quit();
			
			// Entering data in 'Start' field
			WebElement startField = driver.findElementByAccessibilityId("dtoStart");
			WebElement startTextField = startField.findElement(By.xpath("//Pane[@AutomationId='dtpDate']"));
			startTextField.click();
			Thread.sleep(1000);
			startTextField.sendKeys("2030");
			startTextField.sendKeys(Keys.ARROW_RIGHT);
			Thread.sleep(1000);
			startTextField.sendKeys("8");
			startTextField.sendKeys(Keys.ARROW_RIGHT);
			Thread.sleep(1000);
			startTextField.sendKeys("24");
			Thread.sleep(1000);

			// Select 'Given by:' checkbox
			WebElement givenByCheckbox = driver.findElementByName("Given by:");
			givenByCheckbox.click();
			
			// Select 'Family' radio button
			desktopSession = createDesktopSession();
			waitForElement2("Family","Name",desktopSession,20);
			WebElement givenByCheckboxMenuContents = desktopSession.findElementByAccessibilityId("pnlContents");
			givenByCheckboxMenuContents.findElement(By.name("Family")).click();
			
			// Closing the Pane
			WebElement paneTitleHeader = desktopSession.findElementByAccessibilityId("pnlTitle");
			paneTitleHeader.findElement(By.name("X")).click();
			
			//Selecting 'Origin' date
			WebElement originDate = driver.findElementByAccessibilityId("dtOriginDate");
		
			//Getting Co-ordinates
			int height=originDate.getSize().getHeight();
			int widght=originDate.getSize().getWidth();
			Actions act= new Actions(driver);
			act.moveToElement(originDate, 100, 10).click().build().perform();
			
			//Select date from Calendar
			WebElement calendar;
			try
			{
				desktopSession = createDesktopSession();
				calendar=desktopSession.findElementByName("Calendar Control");
			}
			catch (Exception e)
			{
				act= new Actions(driver);
				originDate = driver.findElementByAccessibilityId("dtOriginDate");
				act.moveToElement(originDate, 100, 10).click().build().perform();
				desktopSession = createDesktopSession();
			}
			
			waitForElement2("Calendar Control","Name",desktopSession,20);
			calendar=desktopSession.findElementByName("Calendar Control");
			WebElement date = calendar.findElement(By.name("20"));
			date.click();
			
			//Clicking on Add dropdown
			WebElement treatmentSchedulesSection = driver.findElementByAccessibilityId("treatmentSchedulesControl1");
			WebElement addDropdown = treatmentSchedulesSection.findElement(By.name("btnAddDropdown"));
			addDropdown.click();
			
			//Selecting daily
			desktopSession = createDesktopSession();
			waitForElement2("DropDown","Name",desktopSession,20);
			WebElement dailyOptionDropdown = desktopSession.findElementByName("DAILY");
			dailyOptionDropdown.click();
			
			//Selecting checkbox from 'To (optional)'
			act= new Actions(driver);
			waitForElement2("More","Name",driver,20);
			WebElement toOptionalCheckBox = driver.findElementByAccessibilityId("dtEndTime");
			act.moveToElement(toOptionalCheckBox, 5, 10).click().build().perform();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Working code : Admin - Settings - Facility Settings - Radio button,
	// Checkbox, Enter time ,Clear field
	@Test(enabled = true)
	public void careSuite3() throws InterruptedException, MalformedURLException {
		try {
			// Navigate to Admin -> Settings -> Facility Settings
			// Clicking on 'Admin' menu
			waitForElement2("Admin","Name",driver,15);
			driver.findElementByName("Admin").click();
			//Thread.sleep(4000);

			// Clicking on 'Settings' option
			desktopSession = createDesktopSession();
			waitForElement2("AdminDropDown","Name",desktopSession,20);
			WebElement adminMenuList = desktopSession.findElementByName("AdminDropDown");
			WebElement settingsOptionAdminMenuList = adminMenuList.findElement(By.name("Settings"));
			settingsOptionAdminMenuList.click();
			//Thread.sleep(3000);

			// Clicking on 'Facility Settings' option
			desktopSession = createDesktopSession();
			waitForElement2("SettingsDropDown","Name",desktopSession,20);
			WebElement settingsMenuList = desktopSession.findElementByName("SettingsDropDown");
			WebElement facilitySettingsMenuList = settingsMenuList.findElement(By.name("Facility Settings"));
			facilitySettingsMenuList.click();
			//Thread.sleep(20000);

			// Select 'Level 1:Full name' radio button
			waitForElement2("Enable Intake/Output","Name",driver,50);
			driver.findElementByAccessibilityId("optObfuscationLevel1").click();
			Thread.sleep(1000);

			// Select 'Enable Intake/Output' checkbox
			driver.findElementByName("Enable Intake/Output").click();
			Thread.sleep(1000);

			// Select time from 'Behavior Management'
			WebElement nightStartTextField = driver.findElementByAccessibilityId("nightFrom");
			WebElement increaseButtonNightStartTextField = nightStartTextField.findElement(By.name("More"));
			WebElement decreaseButtonNightStartTextField = nightStartTextField.findElement(By.name("Less"));

			// Increase time
			increaseButtonNightStartTextField.click();
			Thread.sleep(1000);
			increaseButtonNightStartTextField.click();
			Thread.sleep(1000);

			// Decrease time
			decreaseButtonNightStartTextField.click();
			Thread.sleep(1000);
			decreaseButtonNightStartTextField.click();
			Thread.sleep(1000);

			// Enter text manually
			Thread.sleep(1000);
			nightStartTextField.sendKeys("8");
			nightStartTextField.sendKeys(Keys.ARROW_RIGHT);
			Thread.sleep(1000);
			nightStartTextField.sendKeys("40");
			nightStartTextField.sendKeys(Keys.ARROW_RIGHT);
			Thread.sleep(1000);
			nightStartTextField.sendKeys("PM");

			// Edit time in 'Bowel Health'
			Thread.sleep(1000);
			WebElement bowelHealthTimeField = driver.findElementByAccessibilityId("udBowelAlarms");
			WebElement editBoxBowelHealthTimeField = bowelHealthTimeField.findElement(By.xpath("//Edit"));
			editBoxBowelHealthTimeField.click();
			Thread.sleep(1000);
			editBoxBowelHealthTimeField.clear();
			Thread.sleep(1000);
			editBoxBowelHealthTimeField.sendKeys("40");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	@Test(enabled = false)
	public void careSuite4() throws InterruptedException, MalformedURLException {
		try {
			//Clicking on 'Inventory'
			waitForElement2("Inventory","Name",driver,20); 
			driver.findElementByName("Inventory").click();
			
			//Selecting 'Cycle Fill'
			desktopSession = createDesktopSession();
			waitForElement2("InventoryDropDown","Name",desktopSession,20); 
			WebElement inventoryDropDown=desktopSession.findElementByName("InventoryDropDown");
			WebElement cycleFillOptionInventoryDropDown=inventoryDropDown.findElement(By.name("Cycle Fill"));
			cycleFillOptionInventoryDropDown.click();
					
			//Clicking on 'Yes' button of 'Warning' window
			waitForElement2("MessageBoxYesNo","AccessId",driver,20);
			WebElement warningWindow=driver.findElementByAccessibilityId("MessageBoxYesNo");
			WebElement yesButtonWarningWindow=warningWindow.findElement(By.name("Yes"));
			yesButtonWarningWindow.click();
			
			//Clicking on 'OK' button of 'Retrieve Previous Cycle Fill' window
			waitForElement2("RetrieveMedCheckCycleFillDialog","AccessId",driver,20);
			WebElement retrievePreviousCycleFillWindow=driver.findElementByAccessibilityId("RetrieveMedCheckCycleFillDialog");
			WebElement okButtonRetrievePreviousCycleFillWindow=retrievePreviousCycleFillWindow.findElement(By.name("OK"));
			okButtonRetrievePreviousCycleFillWindow.click();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	@Test(enabled = true)
	public void careSuite5() {
		try {
			
			//Clicking on 'Home' menu
			waitForElement2("Home   ","Name",driver,20); 
			driver.findElementByName("Home   ").click();
			
			
			//Clicking on 'Yes' button of 'Close Confirmation' window if present
			WebElement closeConfirmationWindow = null;
			try
			{
				closeConfirmationWindow=driver.findElementByAccessibilityId("MessageBoxOKCancel");
				WebElement yesButtonCloseConfirmationWindow=closeConfirmationWindow.findElement(By.name("&Yes"));
				//WebElement yesButtonCloseConfirmationWindow=driver.findElement(By.name("&Yes"));
				yesButtonCloseConfirmationWindow.click();
			}
			catch (Exception e)
			{
				//e.printStackTrace();
			}
				
			//Workaround to get focus back to application window
			driver.findElementByName("Pass Meds").click();
			
			//Clicking on 'Dashboard' button
			waitForElement2("btnDashboard","AccessId",driver,20); 
			driver.findElementByAccessibilityId("btnDashboard").click();
			
			//Reading data from table
			Thread.sleep(15000);
			waitForElement2("Grid","AccessId",driver,20);
			WebElement table=driver.findElementByAccessibilityId("Grid");
			List<WebElement> rows=table.findElements(By.xpath("//*[starts-with(@Name,'Row')]"));
			System.out.println("No of rows = "+rows.size());
			System.out.println("Complete row value using getText() ="+rows.get(1).getText());
			System.out.println("Complete row value using Value.Value ="+rows.get(1).getAttribute("Value.Value"));
			List<WebElement> columns=rows.get(1).findElements(By.xpath("//*[@LocalizedControlType='item']"));
			System.out.println("No of columns = "+columns.size());
			for (int i=0;i<columns.size();i++)
			{
				System.out.println("Using getText() : "+columns.get(i).getText());
				System.out.println("Using Value.Value : "+columns.get(i).getAttribute("Value.Value"));
			}
		
			
			//Click on a table cell
			WebElement column1=columns.get(0);
			column1.click();
			
			//Closing the window
			desktopSession = createDesktopSession();
			waitForElement2("Remaining meds","Name",desktopSession,20);
			WebElement remainingMedsWindow=desktopSession.findElementByName("Remaining meds");
			remainingMedsWindow.click();
			Actions actions = new Actions(desktopSession);
	        actions.keyDown(Keys.ALT);
	        actions.sendKeys(Keys.F4);
	        actions.keyUp(Keys.ALT);
	        actions.perform();
			
			//Click on a link present in a table cell
			column1=columns.get(2);
			column1.click();
			waitForElement2("Remaining meds","Name",desktopSession,20);
			remainingMedsWindow=desktopSession.findElementByName("Remaining meds");
			remainingMedsWindow.click();
			actions = new Actions(desktopSession);
	        actions.keyDown(Keys.ALT);
	        actions.sendKeys(Keys.F4);
	        actions.keyUp(Keys.ALT);
	        actions.perform();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test(enabled = true)
	public void careSuite6() {
		try {
			//Clicking on 'Pass Meds' menu
			waitForElement2("Pass Meds","Name",driver,20); 
			driver.findElementByName("Pass Meds").click();
			
			//Clicking on 'AM' option
			desktopSession = createDesktopSession();
			waitForElement2("Pass MedsDropDown","Name",desktopSession,20);
			WebElement passMedsDropdown=desktopSession.findElementByName("Pass MedsDropDown");
			WebElement amOptionPassMedsDropdown=passMedsDropdown.findElement(By.name("AM"));
			amOptionPassMedsDropdown.click();
			
			//Selecting a resident
			waitForElement2("MedPassPatientButton","AccessId",driver,20);
			WebElement resident=driver.findElementByName("ASHLEY AINSWORTH");
			resident.click();
			
			//Selecting a med
			waitForElement2("PickListItemButton","AccessId",driver,20);
			List<WebElement> medItems=driver.findElementsByAccessibilityId("PickListItemButton");
			int j = 0;
			for (int i=0;i<medItems.size();i++)
			{
				if (medItems.get(i).findElement(By.xpath("//*[@AutomationId='lblTitle']")).getText().trim().equals("ASPIRIN E.C. 325  MG TAB"))
				//if (medItems.get(i).findElement(By.xpath("//*[@AutomationId='lblTitle']")).getText().trim().equals("Test"))	
				{
					medItems.get(i).click();
					j=i;
					break;
				}
			}
			
			//Validate the tick mark
			WebElement tickMark=medItems.get(j).findElement(By.xpath("//*[@AutomationId='CheckPictureBox']"));
			System.out.println("Dispaying tick mark :" +tickMark.isDisplayed());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test(enabled = true)
	public void careSuite7() {
		try {
			//Clicking on 'Reports' menu
			waitForElement2("Reports","Name",driver,20); 
			driver.findElementByName("Reports").click();
			
			//Clicking on 'Physician's Orders' option
			desktopSession = createDesktopSession();
			waitForElement2("ReportsDropDown","Name",desktopSession,20);
			WebElement reportsDropdown=desktopSession.findElementByName("ReportsDropDown");
			WebElement physiciansOrdersReportsDropdown=reportsDropdown.findElement(By.name("Physician's Orders"));
			physiciansOrdersReportsDropdown.click();
			
			//Clicking on Patient dropdown 
			waitForElement2("ReportViewer","AccessId",driver,60);
			WebElement physiciansOrdersWindow=driver.findElementByAccessibilityId("ReportViewer");
			WebElement  patientsDropdown=physiciansOrdersWindow.findElement(By.name("patientGuid"));
			WebElement openButtonPatientsDropdown=patientsDropdown.findElement(By.name("Open"));
			openButtonPatientsDropdown.click();
								
			//Selecting an option
			desktopSession = createDesktopSession();
			waitForElement2("DropDown","Name",desktopSession,20);
			WebElement dropDownPatients=desktopSession.findElementByName("DropDown");
			WebElement ashleyCheckbox =dropDownPatients.findElement(By.name("AINSWORTH, ASHLEY (ASHLEY)"));
			WebElement ethelCheckbox =dropDownPatients.findElement(By.name("ANDERSON, ETHEL (ROMA)"));
			ashleyCheckbox.click();
			ethelCheckbox.click();
			
			//Click on 'View' report button
			WebElement viewReportButton =physiciansOrdersWindow.findElement(By.name("viewReport"));
			viewReportButton.click();
			
			/*//Validate 'Loading' icon
			waitForElement2("LblLoading","AccessId",driver,20);
			WebElement loadingIcon =physiciansOrdersWindow.findElement(By.name("Loading..."));
			System.out.println("Displaying Loading Icon :" +loadingIcon.isDisplayed());*/
			
			//Closing the window
			physiciansOrdersWindow.click();
			Actions actions = new Actions(driver);
	        actions.keyDown(Keys.ALT);
	        actions.sendKeys(Keys.F4);
	        actions.keyUp(Keys.ALT);
	        actions.perform();
			
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
	public WindowsDriver<WebElement> createDesktopSession() {
		WindowsDriver<WebElement> desktopSession = null;
		try {
			DesiredCapabilities wpfCapabilities = new DesiredCapabilities();
			wpfCapabilities.setCapability("app", "Root");
			desktopSession = new WindowsDriver<WebElement>(new URL("http://127.0.0.1:4723"), wpfCapabilities);
			// Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desktopSession;
	}

	
	
	public void waitForElement2(String element,String locatorType,WindowsDriver<WebElement> driver, int time) {
		try
		{
			WebDriverWait wait = new WebDriverWait(driver,time); 
			if(locatorType.equalsIgnoreCase("Name"))
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(element)));
			
			//AutomationID
			if(locatorType.equalsIgnoreCase("AccessId"))
			wait.until(ExpectedConditions.visibilityOfElementLocated(MobileBy.AccessibilityId(element)));
			
			if(locatorType.equalsIgnoreCase("ClassName"))
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(element)));
			
			if(locatorType.equalsIgnoreCase("Xpath"))
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
			
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

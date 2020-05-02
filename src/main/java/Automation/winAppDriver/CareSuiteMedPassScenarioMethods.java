package Automation.winAppDriver;

import java.net.URL;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.windows.WindowsDriver;

public class CareSuiteMedPassScenarioMethods {
	// Class variables
		WindowsDriver<WebElement> driver;
		WindowsDriver<WebElement> desktopSession;
		String residentName="ASHLEY AINSWORTH";
		//String residentNameFormat2="AINSWORTH, ASHLEY";
		String residentNameFormat2="ANDERSON, ETHEL";
		String medicationName;
	
	
		//Navigate to Residents -> Manage Orders
		public void navigateToResidentsManageOrders(){
			try {
				// Clicking on 'Resident' menu
				waitForElement2("Residents","Name",driver,20); 
				driver.findElementByName("Residents").click();
							
				// Creating a 'Manage Orders' option using desktop session object
				desktopSession = createDesktopSession();
				waitForElement2("Manage Orders","Name",desktopSession,20); 
				desktopSession.findElementByName("Manage Orders").click();
				desktopSession.quit();
			}
			catch (Exception e)
			{
				
			}
		}
		
		
		//Creating a PRN Medication
		public void createPRNMedication(){
			try {
				String randomString=generateRandomAlphaString(5);
				String medicatinNamelocal="Test Medication "+randomString;
				medicationName=medicatinNamelocal;
				String instructions="Test Instructions "+randomString;
				String prescriber="test";
				String minDosage="2.00";
				String maxDosage="5.00";
				String witnessReason="Test reason "+randomString;
				
				//Clicking on the resident using application session object
				waitForElement2("ListViewSubItem-0","AccessId",driver,20); 
				WebElement residentsPane=driver.findElementByAccessibilityId("lvPatients");
				WebElement selectResidentResidentsPane=residentsPane.findElement(By.name(residentNameFormat2));
				selectResidentResidentsPane.click();
				
		    	// Clicking on 'Add' dropdown
				waitForElement2("btnAddDropdown","Name",driver,20); 
				//System.out.println("Add button is enabled : " + driver.findElementByName("btnAddDropdown").isEnabled());
				driver.findElementByName("btnAddDropdown").click();
				
				// Selecting 'Medication' option
				desktopSession = createDesktopSession();
				waitForElement2("Medication","Name",desktopSession,20); 
				desktopSession.findElementByName("Medication").click();
				desktopSession.quit();
				
				//Enter Medication name
				waitForElement2("txtDrugNameLookup","AccessId",driver,20); 
				WebElement medicationNameParentTextbox=driver.findElementByAccessibilityId("txtDrugNameLookup");
				WebElement medicationNameTextbox=medicationNameParentTextbox.findElement(By.xpath("//*[@AutomationId='txtEntry']"));
				medicationNameTextbox.sendKeys(medicationName);
				
				// Selecting option from 'From' dropdown
				waitForElement2("Given by:","Name",driver,20);
				WebElement fromDropdown = driver.findElementByAccessibilityId("unitOfMeasureListRadComboBox");
				fromDropdown.click();
				WebElement editBoxFromDropdown = fromDropdown.findElement(By.xpath("//Edit"));
				while (true) {
					editBoxFromDropdown.sendKeys(Keys.DOWN);
					System.out.println(editBoxFromDropdown.getAttribute("Value.Value"));
					if (editBoxFromDropdown.getAttribute("Value.Value").equalsIgnoreCase("POWDER"))
						break;

				}

				
				// Select time from dropdown
				WebElement timeDropdown = driver.findElementByAccessibilityId("cboTimeOfDay");
				WebElement openButtonTimeDropdown = timeDropdown.findElement(By.name("Open"));
				openButtonTimeDropdown.click();
				desktopSession = createDesktopSession();
				WebElement timeListDropdown = desktopSession.findElementByClassName("ComboLBox");
				WebElement optionTimeListDropdown = timeListDropdown.findElement(By.name("12:00 AM"));
				optionTimeListDropdown.click();
				desktopSession.quit();
				
				//Selecting Prescriber
				WebElement prescriberParentTextbox=driver.findElementByAccessibilityId("ctlPrescriberName");
				WebElement prescriberTextbox=prescriberParentTextbox.findElement(By.xpath("//*[@AutomationId='txtEntry']"));
				prescriberTextbox.sendKeys(prescriber);
				desktopSession = createDesktopSession();
				waitForElement2("pnlDropDown","AccessId",desktopSession,20);
				WebElement prescriberDropdown=desktopSession.findElementByAccessibilityId("pnlDropDown");
				WebElement optionPrescriberDropdown=prescriberDropdown.findElement(By.name("test, test"));
				optionPrescriberDropdown.click();
				
				//Entering Instructions
				WebElement instructionsTextbox = driver.findElementByAccessibilityId("instructionsTextBox");
				instructionsTextbox.sendKeys(instructions);
				
				//Clicking on 'PRN' button
				WebElement prnButton = driver.findElementByAccessibilityId("optPRN");
				prnButton.click();
				
				//Entering Min dosage
				waitForElement2("numMinDosage","AccessId",driver,20); 
				WebElement numMinDosageParentTextbox=driver.findElementByAccessibilityId("numMinDosage");
				WebElement numMinDosageTextbox=numMinDosageParentTextbox.findElement(By.xpath("//Edit"));
				numMinDosageTextbox.clear();
				Thread.sleep(1000);
				numMinDosageTextbox.sendKeys(minDosage);
				
				//Entering Max dosage
				WebElement numMaxDosageParentTextbox=driver.findElementByAccessibilityId("numMaxDosage");
				WebElement numMaxDosageTextbox=numMaxDosageParentTextbox.findElement(By.xpath("//Edit"));
				numMaxDosageTextbox.clear();
				Thread.sleep(1000);
				numMaxDosageTextbox.sendKeys(maxDosage);
				
				//Clicking on 'Save' button
				WebElement saveButton = driver.findElementByAccessibilityId("btnSave");
				saveButton.click();
				
				/*//Enter witness details and click on 'OK' button
				waitForElement2("Reason required","Name",driver,20); 
				WebElement reasonRequiredWindow=driver.findElementByName("Reason required");
				WebElement textboxReasonRequiredWindow=reasonRequiredWindow.findElement(By.xpath("//*[@AutomationId='txtData']"));
				textboxReasonRequiredWindow.sendKeys(witnessReason);
				WebElement okButtonReasonRequiredWindow=reasonRequiredWindow.findElement(By.xpath("//*[@AutomationId='OKButton']"));
				okButtonReasonRequiredWindow.click();*/
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		
		//Navigate to Pass Meds -> PRN
		public void navigateToPassMedsPRN(){
			try {
				//Clicking on 'Pass Meds' menu
				waitForElement2("Pass Meds","Name",driver,20); 
				driver.findElementByName("Pass Meds").click();
				
				
				//Clicking on 'PRN' option
				desktopSession = createDesktopSession();
				waitForElement2("Pass MedsDropDown","Name",desktopSession,20);
				WebElement passMedsDropdown=desktopSession.findElementByName("Pass MedsDropDown");
				WebElement prnOptionPassMedsDropdown=passMedsDropdown.findElement(By.name("PRN"));
				prnOptionPassMedsDropdown.click();
			}
			catch (Exception e)
			{
				
			}
		}
		
		
		//Selecting the Resident
		public void selectingResident(){
			try {
				//Selecting a resident
				waitForElement2("MedPassPatientButton","AccessId",driver,20);
				WebElement resident=driver.findElementByName(residentName);
				resident.click();
			}
			catch (Exception e)
			{
				
			}
		}
		
		
		//Selecting the Medication
		public void selectingMedication(){
			try {
				//Selecting a med
				waitForElement2("PickListItemButton","AccessId",driver,20);
				List<WebElement> medItems=driver.findElementsByAccessibilityId("PickListItemButton");
				int j = 0;
				for (int i=0;i<medItems.size();i++)
				{
					if (medItems.get(i).findElement(By.xpath("//*[@AutomationId='lblTitle']")).getText().trim().equals(medicationName))
					{
						medItems.get(i).click();
						j=i;
						break;
					}
				}
				
				//Validate the tick mark
				WebElement tickMark=medItems.get(j).findElement(By.xpath("//*[@AutomationId='CheckPictureBox']"));
				System.out.println("Dispaying tick mark :" +tickMark.isDisplayed());
			}
			catch (Exception e)
			{
				
			}
		}
		
		
		//Enter the details and complete
		public void enterDetailsAndComplete(){
			try {
				String amount="1.00";
				
				//Click on 'Next' button
				waitForElement2("Next  >","Name",driver,60);
				WebElement nextButton=driver.findElementByName("Next  >");
				nextButton.click();
				
				//Enter amount
				waitForElement2("edtAmount","AccessId",driver,60);
				WebElement amountTextField=driver.findElementByAccessibilityId("edtAmount");
				amountTextField.sendKeys(amount);
				
				//Select purpose
				WebElement purposeDropdownOpenButton = driver.findElementByAccessibilityId("cbReason");
				Actions act= new Actions(driver);
				act.moveToElement(purposeDropdownOpenButton, 345,15).click().build().perform();
				desktopSession = createDesktopSession();
				//waitForElement2("List","TagName",desktopSession,20);
				WebElement purposeDropdown = desktopSession.findElementByTagName("List");
				act= new Actions(desktopSession);
				act.moveToElement(purposeDropdown, 5,5).click().build().perform();
				
				//Click on 'Record All' button
				WebElement recordAllButton=driver.findElementByAccessibilityId("RecordButton");
				recordAllButton.click();
				
				//Enter details in 'Confirm Med Pass' window and click 'OK' button
				waitForElement2("Confirm Med Pass","Name",driver,20);
				WebElement confirmMedPassWindow=driver.findElementByName("Confirm Med Pass");
				//WebElement textBoxConfirmMedPassWindow=confirmMedPassWindow.findElement(By.name("Cancel, or click OK to give it anyway."));
				WebElement textBoxConfirmMedPassWindow=confirmMedPassWindow.findElement(By.xpath("//*[@AutomationId='txtNotes']"));
				textBoxConfirmMedPassWindow.sendKeys("Test");
				WebElement okButtonConfirmMedPassWindow=confirmMedPassWindow.findElement(By.xpath("//*[@AutomationId='btnSave']"));
				okButtonConfirmMedPassWindow.click();
				
				//Click 'OK' button in 'Confirm Med Pass' window
				Thread.sleep(2000);
				waitForElement2("MedPassConfirmationDialog","AccessId",driver,20);
				WebElement confirmMedPassWindow2=driver.findElementByAccessibilityId("MedPassConfirmationDialog");
				WebElement okButtonConfirmMedPassWindow2=confirmMedPassWindow2.findElement(By.xpath("//*[@AutomationId='btnOK']"));
				okButtonConfirmMedPassWindow2.click();
			}
			catch (Exception e)
			{
				
			}
		}
		
		
		//Selecting the Medication history
		public void selectingMedication1(){
			try {
				//Selecting a med
				waitForElement2("PickListItemButton","AccessId",driver,20);
				List<WebElement> medItems=driver.findElementsByAccessibilityId("PickListItemButton");
				int j = 0;
				for (int i=0;i<medItems.size();i++)
				{
					if (medItems.get(i).findElement(By.xpath("//*[@AutomationId='lblTitle']")).getText().trim().contains(medicationName))
					{
						medItems.get(i).click();
						j=i;
						break;
					}
				}
				
				//Clicking 'History' button
				WebElement historyButton=medItems.get(j).findElement(By.xpath("//*[@AutomationId='btnHistory']"));
				historyButton.click();
				
				//Selecting 'Time Range' in 'Administration History' window
				waitForElement2("HistoryFrm","AccessId",driver,20);
				WebElement administrationHistoryWindow=driver.findElementByAccessibilityId("HistoryFrm");
				WebElement timeRangeDropdownAdministrationHistoryWindow=administrationHistoryWindow.findElement(By.xpath("//*[@AutomationId='cboTimeRange']"));
				WebElement openButtonTimeRangeDropdownAdministrationHistoryWindow=timeRangeDropdownAdministrationHistoryWindow.findElement(By.name("Open"));
				openButtonTimeRangeDropdownAdministrationHistoryWindow.click();
				desktopSession = createDesktopSession();
				//waitForElement2("List","TagName",desktopSession,20);
				WebElement timeRangeList=desktopSession.findElementByClassName("ComboLBox");
				WebElement optionTimeRangeList=timeRangeList.findElement(By.name("Last 15 days"));
				optionTimeRangeList.click();
				
				//Click on 'Close' button
				WebElement closeButton=driver.findElementByAccessibilityId("btnCancel");
				closeButton.click();
				
			}
			catch (Exception e)
			{
				
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
				
				if(locatorType.equalsIgnoreCase("TagName"))
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(element)));
				
				
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}



	public String generateRandomAlphaString(final int length)
	{
		String strRet=null;
		try
		{
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder(20);
		Random random = new Random();
		for (int i = 0; i < length; i++)
		{
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		strRet= sb.toString();
		}
		catch (Exception e)
		{
			
		}
		return strRet;
	}
		

}

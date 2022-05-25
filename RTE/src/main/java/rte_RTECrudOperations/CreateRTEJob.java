package rte_RTECrudOperations;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import rte_BasePackage.BaseInit;

public class CreateRTEJob extends BaseInit {

	@Test
	public void rteCreateJob() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		Actions act = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		logs.info("======================RTE Create Route Work Test Start==================");
		msg.append("======================RTE Create Route Work Test Start==================" + "\n");

		// --Go to Tools tab
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_tools")));
		WebElement Tools = isElementPresent("Tools_id");
		act.moveToElement(Tools).click().perform();
		logs.info("Clicked on Tools");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"OpenCloseClass dropdown open\"]//ul")));

		// --Click on RouteList
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_RouteWorkList")));
		isElementPresent("RouteList_linkText").click();
		logs.info("Clicked on RouteList");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Click on plus sign
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title=\"Create Route Work\"]")));
		isElementPresent("RLCreateR_xpath").click();
		logs.info("Clicked on Create Route Work");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("scrollMainScreen")));

		// --Click on Cancel button
		WebElement Cancel = isElementPresent("RLRWCancel_id");
		js.executeScript("arguments[0].scrollIntoView();", Cancel);
		Cancel.click();
		logs.info("Clicked on Cancel");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Click on plus sign
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@title=\"Create Route Work\"]")));
		isElementPresent("RLCreateR_xpath").click();
		logs.info("Clicked on Create Route Work");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("scrollMainScreen")));

		// --Enter Route Work Details

		// --Check Route WorkID is enable/disable
		try {
			WebElement RWIDRDOnly = isElementPresent("RLRRLRWIDROnly_xpathWID_id");
			wait.until(ExpectedConditions.visibilityOf(RWIDRDOnly));
			logs.info("RouteWorkID is disabled==PASS");
			msg.append("RouteWorkID is disabled==PASS");

		} catch (Exception ReadOnly) {
			logs.info("RouteWorkID is Enable==FAIL");
			msg.append("RouteWorkID is Enable==FAIL");

		}

		// --RW Name
		isElementPresent("RLRWName_id").clear();
		isElementPresent("RLRWName_id").sendKeys("Automation Manual RTE Job");
		logs.info("Entered Route Work Name");

		// --Customer
		isElementPresent("RLRWCust_id").clear();
		isElementPresent("RLRWCust_id").sendKeys("950655");
		logs.info("Entered Customer");

		// --Reference 2
		isElementPresent("RLRWRef2_id").clear();
		isElementPresent("RLRWRef2_id").sendKeys("AutoRef2");
		logs.info("Entered Reference 2");

		// --Reference 4
		isElementPresent("RLRWRef4_id").clear();
		isElementPresent("RLRWRef4_id").sendKeys("AutoRef4");
		logs.info("Entered Reference 4");

		// --RW Manifest
		isElementPresent("RLRWManifest_id").clear();
		isElementPresent("RLRWManifest_id").sendKeys("Ravina.prajapati@samyak.com");
		logs.info("Entered RW Manifest");

		// --Declared Value
		isElementPresent("RLRWDeclaredV_id").clear();
		isElementPresent("RLRWDeclaredV_id").sendKeys("10");
		logs.info("Entered Declare Value");

		// --Flat Rate
		isElementPresent("RLRWFlatRate_id").clear();
		isElementPresent("RLRWFlatRate_id").sendKeys("25");
		logs.info("Entered Flat Rate");

		// --Enter Start Date
		WebElement StartDate = isElementPresent("RLRWStartDate_id");
		StartDate.clear();
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		logs.info(dateFormat.format(date));
		StartDate.sendKeys(dateFormat.format(date));
		StartDate.sendKeys(Keys.TAB);
		logs.info("Entered Start Date");

		// --Enter End Date
		WebElement EndDate = isElementPresent("RLRWEndDate_id");
		EndDate.clear();
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 8000);
		String newDate = dateFormat.format(cal.getTime());
		logs.info(newDate);
		EndDate.sendKeys(newDate);
		EndDate.sendKeys(Keys.TAB);
		logs.info("Entered Start Date");

		// --Enter First Generation Date
		WebElement FGenDate = isElementPresent("RLRWFirstGDate_id");
		FGenDate.clear();
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		cal = Calendar.getInstance();
		cal.setTime(new Date());
		newDate = dateFormat.format(cal.getTime());
		logs.info(newDate);
		FGenDate.sendKeys(newDate);
		FGenDate.sendKeys(Keys.TAB);
		logs.info("Entered Start Date");

		// First Generation Time Hr
		Select FGHr = new Select(isElementPresent("RLRWGTimeHr_id"));
		FGHr.selectByVisibleText("11");
		logs.info("Selected First Generation Hour");

		// First Generation Time Minutes
		Select FGMin = new Select(isElementPresent("RLRWGTimeMin_id"));
		FGMin.selectByVisibleText("00");
		logs.info("Selected First Generation Minutes");

		// First Generation Time AM/PM
		Select FGAmPm = new Select(isElementPresent("RLRWGAmPm_id"));
		FGAmPm.selectByVisibleText("PM");
		logs.info("Selected First Generation AM/PM");

		// --No. Of Packages
		isElementPresent("RLRWNoOfPkg_id").clear();
		isElementPresent("RLRWNoOfPkg_id").sendKeys("1");
		logs.info("Entered No. of Packages");

		// --Commodity
		isElementPresent("RLRWCommodity_id").clear();
		isElementPresent("RLRWCommodity_id").sendKeys("Box");
		logs.info("Entered Commodity");

		// --Length
		isElementPresent("RLRWLength_id").clear();
		isElementPresent("RLRWLength_id").sendKeys("2");
		logs.info("Entered Length");

		// --Width
		isElementPresent("RLRWWidth_id").clear();
		isElementPresent("RLRWWidth_id").sendKeys("2");
		logs.info("Entered Width");

		// --Height
		isElementPresent("RLRWHeight_id").clear();
		isElementPresent("RLRWHeight_id").sendKeys("2");
		logs.info("Entered Height");

		// --Weight
		isElementPresent("RLRWWeight_id").clear();
		isElementPresent("RLRWWeight_id").sendKeys("5");
		logs.info("Entered Weight");

		// Ready Time Hr
		Select RTHr = new Select(isElementPresent("RLRWReadyTimeHr_id"));
		RTHr.selectByVisibleText("11");
		logs.info("Selected Ready Time Hour");

		// Ready Time Minutes
		Select RTMin = new Select(isElementPresent("RLRWRTMin_id"));
		RTMin.selectByVisibleText("00");
		logs.info("Selected Ready Time Minutes");

		// Ready Time AM/PM
		Select RTAmPm = new Select(isElementPresent("RLRWRTAmPm_id"));
		RTAmPm.selectByVisibleText("PM");
		logs.info("Selected Ready Time AM/PM");

		// Scheduled End Time Hr
		Select SETHr = new Select(isElementPresent("RLRWschedEndTimeHr_id"));
		SETHr.selectByVisibleText("10");
		logs.info(" Scheduled End Time Hour");

		// Scheduled End Minutes
		Select SETMin = new Select(isElementPresent("RLRWSchedETMin_id"));
		SETMin.selectByVisibleText("45");
		logs.info("Selected  Scheduled End Time Minutes");

		// Scheduled End AM/PM
		Select SETAmPm = new Select(isElementPresent("RLRWSchedAmPm_id"));
		SETAmPm.selectByVisibleText("PM");
		logs.info("Selected  Scheduled End Time AM/PM");

		// --Move to Return Undeliverable Shipments To Section
		WebElement RUShip = isElementPresent("RLRWReturnsection_xpath");
		js.executeScript("arguments[0].scrollIntoView();", RUShip);

		// Company Name
		isElementPresent("RLRWUnDComp_id").clear();
		isElementPresent("RLRWUnDComp_id").sendKeys("Automation return Pvt. Ltd.");
		logs.info("Entered Company Name");

		// Contact Name
		isElementPresent("RLRWUnDContact_id").clear();
		isElementPresent("RLRWUnDContact_id").sendKeys("Ravina Prajapati");
		logs.info("Entered Contact Name");

		// AddressLine 1
		isElementPresent("RLRWUnDAdd1_id").clear();
		isElementPresent("RLRWUnDAdd1_id").sendKeys("1 Cooper Plaza");
		logs.info("Entered AddressLine 1");

		// ZipCode
		isElementPresent("RLRWUnDZip_id").clear();
		isElementPresent("RLRWUnDZip_id").sendKeys("77054");
		logs.info("Entered ZipCode");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// Phone Number
		isElementPresent("RLRWUnDPhone_id").clear();
		isElementPresent("RLRWUnDPhone_id").sendKeys("9856320147");
		logs.info("Entered Phone Number");

		// Delivery Instruction
		isElementPresent("RLRWUnDDelInst_id").clear();
		isElementPresent("RLRWUnDDelInst_id").sendKeys("Undelivered shipment through Automation Script");
		logs.info("Entered Delivery Instruction");

		// --Add/Edit/Delete shipment
		rteEditDeleteShipment();

	}

	public void rteAddShipment() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// Actions act = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// --Move to Shipment Details Section
		WebElement AddSHipBTN = isElementPresent("RLRWAddShip_id");
		js.executeScript("arguments[0].scrollIntoView();", AddSHipBTN);

		// --From Section

		// Stop Sequence
		isElementPresent("RLRWSDFromStSeq_id").clear();
		isElementPresent("RLRWSDFromStSeq_id").sendKeys("1");
		isElementPresent("RLRWSDFromStSeq_id").sendKeys(Keys.TAB);
		logs.info("Entered From Stop Sequence");

		// Company Name
		isElementPresent("RLRWSDFrComp_id").clear();
		isElementPresent("RLRWSDFrComp_id").sendKeys("Automation From pvt ltd");
		logs.info("Entered From Company Name");

		// Contact Name
		isElementPresent("RLRWSDFrContName_id").clear();
		isElementPresent("RLRWSDFrContName_id").sendKeys("Ravina Oza");
		logs.info("Entered From Contact Name");

		// AddressLine 1
		isElementPresent("RLRWSDFrAddLine1_id").clear();
		isElementPresent("RLRWSDFrAddLine1_id").sendKeys("14,Sheeley");
		logs.info("Entered From AddressLine 1");

		// ZipCode
		isElementPresent("RLRWSDFrZip_id").clear();
		isElementPresent("RLRWSDFrZip_id").sendKeys("60406");
		isElementPresent("RLRWSDFrZip_id").sendKeys(Keys.TAB);
		logs.info("Entered From ZipCode");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// Phone Number
		isElementPresent("RLRWSDFrPhone_id").clear();
		isElementPresent("RLRWSDFrPhone_id").sendKeys("8596741235");
		logs.info("Entered From Phone Number");

		// PickUp Instruction
		isElementPresent("RLRWSDFrPInst_id").clear();
		isElementPresent("RLRWSDFrPInst_id").sendKeys("Pickup instruction written by Automation script");
		logs.info("Entered PickUp Instruction");

		// --To Section

		// Stop Sequence
		isElementPresent("RLRWSDToStSeq_id").clear();
		isElementPresent("RLRWSDToStSeq_id").sendKeys("2");
		isElementPresent("RLRWSDToStSeq_id").sendKeys(Keys.TAB);
		logs.info("Entered To Stop Sequence");

		// Company Name
		isElementPresent("RLRWSDToComp_id").clear();
		isElementPresent("RLRWSDToComp_id").sendKeys("Automation To pvt ltd");
		logs.info("Entered To Company Name");

		// Contact Name
		isElementPresent("RLRWSDToContName_id").clear();
		isElementPresent("RLRWSDToContName_id").sendKeys("Ravina Oza");
		logs.info("Entered To Contact Name");

		// AddressLine 1
		isElementPresent("RLRWSDToAddLine1_id").clear();
		isElementPresent("RLRWSDToAddLine1_id").sendKeys("14,Sheeley, To Address");
		logs.info("Entered To AddressLine 1");

		// ZipCode
		isElementPresent("RLRWSDToZip_id").clear();
		isElementPresent("RLRWSDToZip_id").sendKeys("60606");
		isElementPresent("RLRWSDToZip_id").sendKeys(Keys.TAB);
		logs.info("Entered To ZipCode");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// Phone Number
		isElementPresent("RLRWSDToPhone_id").clear();
		isElementPresent("RLRWSDToPhone_id").sendKeys("8596741235");
		logs.info("Entered To Phone Number");

		// PickUp Instruction
		isElementPresent("RLRWSDToPInst_id").clear();
		isElementPresent("RLRWSDToPInst_id").sendKeys("Delivery instruction written by Automation script");
		logs.info("Entered Delivery Instruction");

		// ---Package Information
		//
		// --No. Of Packages
		isElementPresent("RLRWPIPakg_id").clear();
		isElementPresent("RLRWPIPakg_id").sendKeys("1");
		logs.info("Entered No. of Packages");

		// --Commodity
		isElementPresent("RLRWPIComm_id").clear();
		isElementPresent("RLRWPIComm_id").sendKeys("Box");
		logs.info("Entered Commodity");

		// --Length
		isElementPresent("RLRWPILen_id").clear();
		isElementPresent("RLRWPILen_id").sendKeys("2");
		logs.info("Entered Length");

		// --Width
		isElementPresent("RLRWPIWid_id").clear();
		isElementPresent("RLRWPIWid_id").sendKeys("2");
		logs.info("Entered Width");

		// --Height
		isElementPresent("RLRWPIHt_id").clear();
		isElementPresent("RLRWPIHt_id").sendKeys("2");
		logs.info("Entered Height");

		// --Weight
		isElementPresent("RLRWPIWeight_id").clear();
		isElementPresent("RLRWPIWeight_id").sendKeys("5");
		logs.info("Entered Weight");

		// Ready Time Hr
		Select PIRTHr = new Select(isElementPresent("RLRWPIRTimeHr_id"));
		PIRTHr.selectByVisibleText("11");
		logs.info("Selected Ready Time Hour");

		// Ready Time Minutes
		Select PIRTMin = new Select(isElementPresent("RLRWPIRTimeMin_id"));
		PIRTMin.selectByVisibleText("00");
		logs.info("Selected Ready Time Minutes");

		// Ready Time AM/PM
		Select PIRTAmPm = new Select(isElementPresent("RLRWPIRTimeAmPm_id"));
		PIRTAmPm.selectByVisibleText("PM");
		logs.info("Selected Ready Time AM/PM");

		// --Reference 1
		isElementPresent("RLRWPIRef1_id").clear();
		isElementPresent("RLRWPIRef1_id").sendKeys("AutoRef1");
		logs.info("Entered Reference 1");

		// --Reference 3
		isElementPresent("RLRWPIRef3_id").clear();
		isElementPresent("RLRWPIRef3_id").sendKeys("AutoRef3");
		logs.info("Entered Reference 3");

		// --Click on Add Shipment
		isElementPresent("RLRWAddShip_id").click();
		logs.info("Clicked on Add Shipment");

	}

	public void rteEditDeleteShipment() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
//		/Actions act = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// --Add shipment
		rteAddShipment();

		// try {
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
				By.xpath("//*[@class=\"dx-datagrid-content\"]//tbody//tr[contains(@class,'dx-data-row')]")));
		logs.info("Shipment is added successfully");
		getScreenshot(driver, "RWAddShipment");

		WebElement Edit = isElementPresent("RLRWEditShip_id");
		js.executeScript("arguments[0].scrollIntoView();", Edit);

		// --Click on Edit button
		Edit.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@role=\"spinbutton\"]")));
		isElementPresent("RLRWEditPUSTop_xpath").clear();
		isElementPresent("RLRWEditPUSTop_xpath").sendKeys("1");
		logs.info("Update PU stop");

		// --Click on Update button
		isElementPresent("RLRWEditSHipUpdateAll_id").click();
		logs.info("Clicked on Update All button");

		// --Click on Edit button
		Edit.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@role=\"spinbutton\"]")));
		isElementPresent("RLRWEditPUSTop_xpath").clear();
		isElementPresent("RLRWEditPUSTop_xpath").sendKeys("1");
		logs.info("Update PU stop");

		// --Click on Cancel button
		isElementPresent("RLRWEShipCancel_id").click();
		logs.info("Clicked on Cancel button");

		// --Click on Edit button of Shipment Row
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hrfActEdit")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("hrfActEdit")));
		WebElement EditShip = isElementPresent("RLRWShipEdit_id");
		EditShip.click();
		logs.info("Clicked on Edit Shipment button");

		WebElement UpShip = isElementPresent("RLRWAddShip_id");
		wait.until(ExpectedConditions.visibilityOf(UpShip));
		UpShip.click();
		logs.info("Clicked on Update Shipment button");

		// --Delete button with No
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hrfActDelete")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("hrfActDelete")));
		WebElement DeleteShip = isElementPresent("RLRWShipDelete_id");
		DeleteShip.click();

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));

		String DeleteMsg = isElementPresent("RLRWDeleteMsg_xpath").getText();
		logs.info("Confirmation message is displayed==" + DeleteMsg);

		WebElement DeleteNo = isElementPresent("RLRWDeleteNo_xpath");
		DeleteNo.click();
		logs.info("Clicked on No button of Confirmation message");

		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					By.xpath("//*[@class=\"dx-datagrid-content\"]//tbody//tr[contains(@class,'dx-data-row')]")));
			logs.info("Shipment is not deleted on NO button==PASS");

			// --Delete button with Yes
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hrfActDelete")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("hrfActDelete")));
			DeleteShip = isElementPresent("RLRWShipDelete_id");
			DeleteShip.click();

			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));

			DeleteMsg = isElementPresent("RLRWDeleteMsg_xpath").getText();
			logs.info("Confirmation message is displayed==" + DeleteMsg);

			WebElement DeleteYes = isElementPresent("RLRWDeleteYes_xpath");
			DeleteYes.click();
			logs.info("Clicked on YES button of Confirmation message");

			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
						By.xpath("//*[@class=\"dx-datagrid-content\"]//tbody//tr[contains(@class,'dx-data-row')]")));
				logs.info("Shipment is not deleted on YES button==FAIL");
			} catch (Exception Delete) {
				logs.info("Shipment is deleted on YES button==PASS");

				// --Add shipment again
				rteAddShipment();

				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By
							.xpath("//*[@class=\"dx-datagrid-content\"]//tbody//tr[contains(@class,'dx-data-row')]")));
					logs.info("Shipment is added successfully");

				} catch (Exception Shipment) {
					logs.info("Shipment is not added successfully");

				}

				// --Click on Save as Draft
				WebElement SaveASDraft = isElementPresent("RLRWSaveAsDraft_id");
				js.executeScript("arguments[0].scrollIntoView();", SaveASDraft);
				SaveASDraft.click();
				logs.info("Clicked on SaveAsDraft");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("success")));

					String SUccMsg = isElementPresent("RLRWSuccMsg_id").getText();
					logs.info("Message is displayed==" + SUccMsg);
					logs.info("RTE Job is created successfully.");

					// --Get the RouteWorkID
					String inLine = SUccMsg;
					String[] lineSplits = inLine.split(" ");
					for (String Detail : lineSplits) {
						if (Detail.contains("RT")) {
							Detail.trim();
							System.out.println("RoutWorkID is==" + Detail);
							logs.info("RoutWorkID is==" + Detail);
							setData("RTECreation", 3, 1, Detail);
							logs.info("Stored RoutWorkID in excel");

						}
					}
				} catch (Exception RTEEx) {
					logs.info("RTE Job is not created successfully.");

				}
			}

		} catch (Exception Delete) {
			logs.info("Shipment is deleted on NO button==FAIL");

			try {
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
						By.xpath("//*[@class=\"dx-datagrid-content\"]//tbody//tr[contains(@class,'dx-data-row')]")));
				logs.info("Shipment is added successfully");

			} catch (Exception Shipment) {
				logs.info("Shipment is not added successfully");

			}
		}

		/*
		 * } catch (Exception AddShip) {
		 * logs.info("Shipment is not added successfully"); getScreenshot(driver,
		 * "RWAddShipment_FAIL"); }
		 */
	}

}

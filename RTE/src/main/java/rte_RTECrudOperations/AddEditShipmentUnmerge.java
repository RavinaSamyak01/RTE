package rte_RTECrudOperations;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rte_BasePackage.BaseInit;

public class AddEditShipmentUnmerge extends BaseInit {

	public String addeditShipmentUnmerge() throws EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Actions act = new Actions(driver);
		String Result = null;

		int totalRow = getTotalRow("LocJob");
		for (int row = 1; row < totalRow; row++) {

			logs.info("=========RTE Add/Edit Stop Sequence Test Start============");
			msg.append("=========RTE Add/Edit Stop Sequence Test Start===========" + "\n");

			// --Total Number of Shipment Stop
			List<WebElement> SDetails = driver.findElements(
					By.xpath("//*[@id=\"scrollRoute\"]//div[contains(@ng-repeat,'ShipmentDetailList ')]"));
			int TotalShipment = SDetails.size();
			logs.info("Total Shipment Stop==" + TotalShipment);

			// --Move to add stop sequence
			WebElement AddStop = isElementPresent("TLAddStopSeq_xpath");
			js.executeScript("arguments[0].scrollIntoView();", AddStop);

			// --Click on Add Stop Sequence
			isElementPresent("TLAddStopSeq_xpath").click();
			logs.info("Clicked on Add Stop Sequence");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"RouteShipmentform\"]")));
			getScreenshot(driver, "AddStopSequence");

			// --PickUp Stop
			isElementPresent("ASPicStop_name").clear();
			isElementPresent("ASPicStop_name").sendKeys("2");
			isElementPresent("ASPicStop_name").sendKeys(Keys.TAB);
			logs.info("Enter Pickup Stop");
			getScreenshot(driver, "PickUpStopSeqData");

			// --Delivery Stop
			isElementPresent("ASDelStop_name").clear();
			isElementPresent("ASDelStop_name").sendKeys("3");
			isElementPresent("ASDelStop_name").sendKeys(Keys.TAB);
			isElementPresent("ASDelStop_name").sendKeys(Keys.TAB);
			logs.info("Enter Delivery Stop");

			// --Add Company
			isElementPresent("ASDAdd_id").clear();
			isElementPresent("ASDAdd_id").sendKeys("Henry Ford");
			logs.info("Enter Address");

			// --Add Address
			isElementPresent("ASDCompany_id").clear();
			isElementPresent("ASDCompany_id").sendKeys("14101 Seeley Ave.");
			logs.info("Enter Company");

			// --Add Zipcode
			isElementPresent("ASDZipCode_id").clear();
			isElementPresent("ASDZipCode_id").sendKeys("60406");
			logs.info("Enter ZipCode");

			// --Get ZoneID
			String ZOneID = isElementPresent("ASCZoneID_xpath").getText();
			logs.info("ZoneID of is==" + ZOneID);
			if (ZOneID.equalsIgnoreCase("EDT")) {
				ZOneID = "America/New_York";
			} else if (ZOneID.equalsIgnoreCase("CDT")) {
				ZOneID = "CST";
			} else if (ZOneID.equalsIgnoreCase("PDT")) {
				ZOneID = "PST";
			}

			// --Enter SCH Date
			WebElement SCHDate = isElementPresent("ASDSCHDate_id");
			SCHDate.clear();
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
			logs.info(dateFormat.format(date));
			SCHDate.sendKeys(dateFormat.format(date));
			SCHDate.sendKeys(Keys.TAB);
			logs.info("Entered SCH Date");

			// --Enter SCHTime
			WebElement SCHTime = isElementPresent("ASDSCHTime_id");
			SCHTime.clear();
			date = new Date();
			dateFormat = new SimpleDateFormat("HH:mm");
			dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
			logs.info(dateFormat.format(date));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtDelSCHACTTime")));
			SCHTime.sendKeys(dateFormat.format(date));
			logs.info("Entered SCH Time");

			// --Enter QDT Date
			WebElement QDTDate = isElementPresent("ASDQDDate_id");
			QDTDate.clear();
			date = new Date();
			dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
			logs.info(dateFormat.format(date));
			QDTDate.sendKeys(dateFormat.format(date));
			QDTDate.sendKeys(Keys.TAB);
			logs.info("Entered SCH Date");

			// --Enter QDT Time
			WebElement QDTTime = isElementPresent("ASDQDTime_id");
			QDTTime.clear();
			date = new Date();
			dateFormat = new SimpleDateFormat("HH:mm");
			dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
			logs.info(dateFormat.format(date));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtDelqtTime")));
			QDTTime.sendKeys(dateFormat.format(date));
			logs.info("Entered SCH Time");

			// --Enter Package Information
			// --Add Weight
			isElementPresent("ASDPweight_id").clear();
			isElementPresent("ASDPweight_id").sendKeys("2");
			logs.info("Enter Weight");

			// --Add Length
			isElementPresent("ASDPLength_id").clear();
			isElementPresent("ASDPLength_id").sendKeys("1");
			logs.info("Enter Length");

			// --Add Width
			isElementPresent("ASDPWidth_id").clear();
			isElementPresent("ASDPWidth_id").sendKeys("1");
			logs.info("Enter Width");

			// --Add Height
			isElementPresent("ASDPHeight_id").clear();
			isElementPresent("ASDPHeight_id").sendKeys("1");
			logs.info("Enter Height");

			// --Click on Save
			isElementPresent("ASSave_id").click();
			logs.info("Click on Save");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
				String ErrorMsg = isElementPresent("Error_id").getText();
				if (ErrorMsg.contains("Delivery Quoted time cannot be less than Pickup Quoted time.")) {
					logs.info("Validation is displayed==" + ErrorMsg);

					// --Enter PU Date
					WebElement PUDate = isElementPresent("ASDPUDate_id");
					PUDate.clear();
					date = new Date();
					dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
					Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
					cal.add(Calendar.DATE, -1);
					logs.info(dateFormat.format(cal.getTime()));
					PUDate.sendKeys(dateFormat.format(cal.getTime()));
					PUDate.sendKeys(Keys.TAB);
					logs.info("Entered PickUp Date");

					// --Enter PU Time
					WebElement PUTime = isElementPresent("ASDPUpTime_id");
					PUTime.clear();
					date = new Date();
					dateFormat = new SimpleDateFormat("HH:mm");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
					cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
					cal.add(Calendar.MINUTE, -60);
					logs.info(dateFormat.format(cal.getTime()));
					wait.until(ExpectedConditions.elementToBeClickable(By.id("txtPUSCHACTTime")));
					PUTime.sendKeys(dateFormat.format(cal.getTime()));
					logs.info("Entered Actual PickUp Time");

					// --Click on Save
					isElementPresent("ASSave_id").click();
					logs.info("Click on Save");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			} catch (Exception Time) {
				logs.info("Validation is not displayed, QDT Date and time is as per expected");

			}

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@ng-message=\"required\"]")));
				String ValMsg = isElementPresent("ASRequMsg_xpath").getText();
				if (ValMsg.contains("Required")) {
					logs.info("Validation is displayed==" + ValMsg);

					// --Enter pickupPhone
					isElementPresent("ASDPUPhone_id").clear();
					isElementPresent("ASDPUPhone_id").sendKeys("1234567899");
					logs.info("Enter PickUp Phone");

					// --Click on Save
					isElementPresent("ASSave_id").click();
					logs.info("Click on Save");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}
			} catch (Exception Phone) {
				logs.info("Validation is not displayed, everything is as per expected");

			}

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorid")));
				String ErrorMsg = isElementPresent("Error_id").getText();
				if (ErrorMsg.contains("Delivery Quoted time cannot be less than Pickup Quoted time.")) {
					logs.info("Validation is displayed==" + ErrorMsg);

					// --Enter PU Date
					WebElement PUDate = isElementPresent("ASDPUDate_id");
					PUDate.clear();
					date = new Date();
					dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
					Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
					cal.add(Calendar.DATE, -1);
					logs.info(dateFormat.format(cal.getTime()));
					PUDate.sendKeys(dateFormat.format(cal.getTime()));
					PUDate.sendKeys(Keys.TAB);
					logs.info("Entered PickUp Date");

					// --Enter PU Time
					WebElement PUTime = isElementPresent("ASDPUpTime_id");
					PUTime.clear();
					date = new Date();
					dateFormat = new SimpleDateFormat("HH:mm");
					dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
					cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
					cal.add(Calendar.MINUTE, -60);
					logs.info(dateFormat.format(cal.getTime()));
					wait.until(ExpectedConditions.elementToBeClickable(By.id("txtPUSCHACTTime")));
					PUTime.sendKeys(dateFormat.format(cal.getTime()));
					logs.info("Entered Actual PickUp Time");

					// --Click on Save
					isElementPresent("ASSave_id").click();
					logs.info("Click on Save");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			} catch (Exception Time) {
				logs.info("Validation is not displayed, QDT Date and time is as per expected");

			}
			// --TotalNo OF shipment Stop
			SDetails = driver.findElements(
					By.xpath("//*[@id=\"scrollRoute\"]//div[contains(@ng-repeat,'ShipmentDetailList ')]"));
			int TotalShipmentAfter = SDetails.size();
			logs.info("Total Shipment Stop after new added==" + TotalShipmentAfter);

			// --CHeck shipment is added or not
			if (TotalShipment != TotalShipmentAfter) {
				logs.info("New Stop added successfully");

			} else {
				logs.info("New Stop is not added");

			}

			// --Click on Edit Stop Sequence
			WebElement EditSS = isElementPresent("TLEditStopSeq_xpath");
			js.executeScript("arguments[0].scrollIntoView();", EditSS);
			js.executeScript("arguments[0].click();", EditSS);
			/*
			 * EditSS = isElementPresent("TLEditStopSeq_xpath"); EditSS.click();
			 */
			logs.info("Clicked on Edit Stop Sequence");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			getScreenshot(driver, "EditStopSequence");

			SDetails = driver.findElements(
					By.xpath("//*[@id=\"scrollRoute\"]//div[contains(@ng-repeat,'ShipmentDetailList ')]"));
			TotalShipment = SDetails.size();

			// --set sequence 3 and 4 instead of 2 and 3

			for (int ship = 0; ship < TotalShipment; ship++) {

				if (ship == 1) {
					SDetails.get(ship).findElement(By.id("txtPUStop")).clear();
					SDetails.get(ship).findElement(By.id("txtPUStop")).sendKeys("3");
					logs.info("Edit PUStop");

					// --Clear and Enter DelStop
					SDetails.get(ship).findElement(By.id("txtDelStop")).clear();
					SDetails.get(ship).findElement(By.id("txtDelStop")).sendKeys("4");
					logs.info("Edit DelStop");

					// --Click on Save changes
					isElementPresent("TLSaveChanges_id").click();
					logs.info("Clicked on Save changes");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					break;
				}

			}

			// --set sequence 3,4 and 1,2
			// --Click on Edit Stop Sequence
			EditSS = isElementPresent("TLEditStopSeq_xpath");
			js.executeScript("arguments[0].scrollIntoView();", EditSS);
			js.executeScript("arguments[0].click();", EditSS);

			logs.info("Clicked on Edit Stop Sequence");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			getScreenshot(driver, "EditStopSequence");

			SDetails = driver.findElements(
					By.xpath("//*[@id=\"scrollRoute\"]//div[contains(@ng-repeat,'ShipmentDetailList ')]"));
			TotalShipment = SDetails.size();

			for (int ship = 0; ship < TotalShipment; ship++) {

				if (ship == 0) {
					// --Clear and Enter PUStop
					SDetails.get(ship).findElement(By.id("txtPUStop")).clear();
					SDetails.get(ship).findElement(By.id("txtPUStop")).sendKeys("3");
					logs.info("Edit PUStop");

					// --Clear and Enter DelStop
					SDetails.get(ship).findElement(By.id("txtDelStop")).clear();
					SDetails.get(ship).findElement(By.id("txtDelStop")).sendKeys("4");
					logs.info("Edit DelStop");
				} else if (ship == 1) {
					SDetails.get(ship).findElement(By.id("txtPUStop")).clear();
					SDetails.get(ship).findElement(By.id("txtPUStop")).sendKeys("1");
					logs.info("Edit PUStop");

					// --Clear and Enter DelStop
					SDetails.get(ship).findElement(By.id("txtDelStop")).clear();
					SDetails.get(ship).findElement(By.id("txtDelStop")).sendKeys("2");
					logs.info("Edit DelStop");
				}

			}

			// --Click on Save changes
			isElementPresent("TLSaveChanges_id").click();
			logs.info("Clicked on Save changes");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			logs.info("=========RTE Add/Edit Stop Sequence Test End============");
			msg.append("=========RTE Add/Edit Stop Sequence Test End===========" + "\n");

			// --UNmerge
			logs.info("=========RTE UnMerge Test Start============");
			msg.append("=========RTE UnMerge Test Start===========" + "\n");

			// --Click on UnMerge link
			try {
				WebElement UnMerge = isElementPresent("TLSUnMerge_xpath");
				js.executeScript("arguments[0].scrollIntoView();", UnMerge);
				UnMerge.click();
				logs.info("Clicked on UnMerge linktext");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			} catch (Exception Umerge) {
				WebElement UnMerge = isElementPresent("TLEJUnmerge_xpath");
				js.executeScript("arguments[0].scrollIntoView();", UnMerge);
				UnMerge.click();
				logs.info("Clicked on UnMerge linktext");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			}
			try {
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"ngdialog-content\"]")));
				getScreenshot(driver, "UnMergeDialogue");
				String UnMergeMsg = isElementPresent("TLUnMergeMsg_xpath").getText();
				logs.info("UnMerge Message==" + UnMergeMsg);

				// --Click on OK button
				isElementPresent("TLUnMergeOK_id").click();
				logs.info("Clicked on OK button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				wait.until(
						ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ngdialog-content\"]")));

			} catch (Exception UnMerge) {
				logs.info("Able to UnMerge");
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"UnmergeShipmentForm\"]")));
				getScreenshot(driver, "UnMerge");

				// --By Default Create New LOC is selected

				// --Click on Save
				isElementPresent("TLUnMergeSave_id").click();
				logs.info("Clicked on Save button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMsgRTE")));
					String SuccMsg = isElementPresent("TLUNMSuccMSG_id").getText();
					logs.info("Message==" + SuccMsg);

					if (SuccMsg.contains("is unmerged successfully to LOC Service")) {
						logs.info("LOC service is created and shipment is unmerged");
						msg.append("LOC service is created and shipment is unmerged" + "\n");

						// --get the pickup id from message
						String inLine = SuccMsg;
						String[] lineSplits = inLine.split("\\#");
						String[] lineDetails = lineSplits[1].split(" ");
						String PickUpID = lineDetails[1].trim();
						logs.info("PickUpID is==" + PickUpID);
						msg.append("PickUpID is==" + PickUpID + "\n");

						setData("LocJob", row, 0, PickUpID);

						// --CLick on Cancel
						isElementPresent("TLUnMergeCancel_id").click();
						logs.info("Clicked on Cancel button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						Result = "PASS";

					}

				} catch (Exception e) {
					logs.info("LOC service is not created and shipment is not unmerged");
					Result = "FAIL";

				}

				logs.info("=========RTE UnMerge Test End============");
				msg.append("=========RTE UnMerge Test End===========" + "\n");
			}

		}

		// --start search loc job and update

		return Result;

	}

}

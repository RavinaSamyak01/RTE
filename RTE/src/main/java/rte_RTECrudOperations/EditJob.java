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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import rte_BasePackage.BaseInit;

public class EditJob extends BaseInit {

	@Test
	public void editJob() throws IOException, InterruptedException, EncryptedDocumentException, InvalidFormatException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// Actions act = new Actions(driver);

		logs.info("======================RTE Edit Job Test Start==================");
		msg.append("======================RTE Edit Job Test Start==================" + "\n");

		// --Go to Edit Job
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idEditOrder")));
		isElementPresent("TLEditJob_id").click();
		logs.info("Clicked on Edit Job tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("scrollboxprocessRW")));
		getScreenshot(driver, "EditJOB");

		// --Get RWTrackingNO
		String RWTrackNO = getData("SearchRTE", 2, 0);
		setData("Rate", 1, 0, RWTrackNO);
		setData("RouteDetail", 1, 0, RWTrackNO);

		// --Get PickUPID
		String PickupID = getData("SearchRTE", 2, 2);
		setData("Rate", 1, 1, PickupID);
		setData("RouteDetail", 1, 1, PickupID);

		// --View Rate
		viewRate();

		// --View Memo
		viewMemo();

		// --Print Label
		printLabel();

		// --Route/Shipment Details
		routeShipmentDetails();

		// --MAP
		rteMap();

		// --Add/Edit Shipment
		adEditStopSequence();

		// --Click on UnMerge
		ShipmentDetails SD = new ShipmentDetails();
		SD.rteUnMerge();

		// --Click on Memo
		SD.addViewMemo();

		// --Click on Upload
		SD.upload();

		// --Click on QC
		SD.rteQC();

		// --Add/Delete Charges
		addDeleteChargesRecal();

		// --Move to Job STatus tab
		// --Go to Edit Job
		wait.until(ExpectedConditions.elementToBeClickable(By.id("idJobOverview")));
		isElementPresent("TLJobSTatus_id").click();
		logs.info("Clicked on Job Status tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
	}

	public void viewMemo() throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Actions act = new Actions(driver);
		logs.info("=========RTE View Memo Test Start============");
		msg.append("=========RTE View Memo Test Start===========" + "\n");

		// --Click on View memo
		isElementPresent("HMemo_id").click();
		logs.info("Clicked on View Memo");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("MemoPopup")));
		getScreenshot(driver, "ViewMemo");

		// --Total count of the Memo
		String MemoCount = isElementPresent("HMemoCount_xpath").getText();
		logs.info("Total count of the Memo is==" + MemoCount);

		// --Click on All Links
		List<WebElement> MemoLinks = driver
				.findElements(By.xpath("//*[@class=\"pull-right\"][@ng-hide=\"IsQuarantineMemo\"]//a"));

		int TotalNoOfLinks = MemoLinks.size();
		logs.info("Total No of links==" + TotalNoOfLinks);
		for (int link = 0; link < TotalNoOfLinks; link++) {
			// --Get the linkName
			String LinkName = MemoLinks.get(link).getText();
			logs.info("Name of the Link is==" + LinkName);

			// --CLick on link
			MemoLinks.get(link).click();
			Thread.sleep(2000);

		}

		// --CLick on All link
		isElementPresent("TLESMAll_id").click();
		logs.info("Clicked on All link");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// -Click on Audit History tab
		WebElement AuditHistory = isElementPresent("HMAuditHsTab_id");
		js.executeScript("arguments[0].click();", AuditHistory);
		logs.info("Clicked on Audit History tab");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("idAuditReportList")));
		getScreenshot(driver, "Memo_AuditHistory");

		// --Close popup
		isElementPresent("ClosePopUp_xpath").click();
		logs.info("Clicked on Close button of Memo Popup");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		logs.info("=========RTE View Memo Test End============");
		msg.append("=========RTE View Memo Test End===========" + "\n");

	}

	public void viewRate()
			throws InterruptedException, IOException, EncryptedDocumentException, InvalidFormatException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		// Actions act = new Actions(driver);
		logs.info("=========RTE View Rate Test Start============");
		msg.append("=========RTE View Rate Test Start===========" + "\n");

		// --Click on View Rate
		isElementPresent("HVRate_id").click();
		logs.info("Clicked on View Rate");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("gridCustomerRateId")));
		getScreenshot(driver, "ViewRate");

		// --Get the Rate details and stored in Rate sheet
		List<WebElement> RateDetails = driver
				.findElements(By.xpath("//*[@class=\"dx-datagrid-content\"]//tr[contains(@class,'dx-data-row')]"));

		int TotalNoOfRates = RateDetails.size();
		logs.info("Total No of Rate==" + TotalNoOfRates);
		for (int Rate = 0; Rate < TotalNoOfRates; Rate++) {

			int RateRow = Rate + 1;
			// --Get the ChargeName
			String ChargeName = RateDetails.get(Rate).findElement(By.xpath("td[contains(@aria-label,'Charge Name,')]"))
					.getText();
			logs.info("Name of the Charge is==" + ChargeName);
			setData("Rate", RateRow, 2, ChargeName);

			// --Get the Amount
			String Amount = RateDetails.get(Rate).findElement(By.xpath("td[contains(@aria-label,'Amount')]")).getText();
			logs.info("Amount is==" + Amount);
			setData("Rate", RateRow, 3, Amount);

			// --Get the Note
			String Note = RateDetails.get(Rate).findElement(By.xpath("td[contains(@aria-label,'Note')]")).getText();
			logs.info("Note is==" + Note);
			setData("Rate", RateRow, 4, Note);

			try {
				// --Get the Scheduler
				String Scheduler = RateDetails.get(Rate).findElement(By.xpath("td[contains(@aria-label,'Scheduler')]"))
						.getText();
				logs.info("Name of the Scheduler is==" + Scheduler);
				setData("Rate", RateRow, 5, Scheduler);

			} catch (Exception UserScheduler) {
				// --Get the User
				String User = RateDetails.get(Rate).findElement(By.xpath("td[contains(@aria-label,'User')]")).getText();
				logs.info("Name of the Scheduler is==" + User);
				setData("Rate", RateRow, 5, User);

			}

			// --Get the RateProgramID
			String RateProgramID = RateDetails.get(Rate)
					.findElement(By.xpath("td[contains(@aria-label,'RateProgramId')]")).getText();
			logs.info("Rate Program ID is==" + RateProgramID);
			setData("Rate", RateRow, 6, RateProgramID);

		}

		// --Get the Total Amount
		String TotalAmount = isElementPresent("HVRDTotalAm_xpath").getText();
		logs.info("Total Amount is==" + TotalAmount);
		setData("Rate", 1, 7, TotalAmount);

		// --Get the Amount details and stored in Rate sheet
		List<WebElement> TotalCharges = driver
				.findElements(By.xpath("//*[contains(@class,'row form-group')]/div[@class=\"row form-group\"]"));

		int TotalCharge = TotalCharges.size();
		logs.info("Total No of charges==" + TotalCharge);
		for (int Charge = 0; Charge < TotalCharge; Charge++) {

			int ChargeCol = Charge + 8;

			// --Get the Label
			String Label = TotalCharges.get(Charge).findElement(By.xpath("label[1]")).getText();
			logs.info("Label is==" + Label);
			setData("Rate", 0, ChargeCol, Label);

			// --Get the Label value
			String LabelValue = TotalCharges.get(Charge).findElement(By.xpath("label[2]")).getText();
			logs.info("Label Value is==" + LabelValue);
			setData("Rate", 1, ChargeCol, LabelValue);

		}

		// --Close the Rate PopUp
		isElementPresent("Close_id").click();
		logs.info("Clicked on Close button of Rate");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		logs.info("=========RTE View Rate Test End============");
		msg.append("=========RTE View Rate Test End===========" + "\n");

	}

	public void printLabel()
			throws InterruptedException, IOException, EncryptedDocumentException, InvalidFormatException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		// Actions act = new Actions(driver);
		logs.info("=========RTE Print Label Test Start============");
		msg.append("=========RTE Print Label Test Start===========" + "\n");

		// --Click on Print Label
		isElementPresent("HPrintLabel_id").click();
		logs.info("Clicked on Print Label");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		Thread.sleep(2000);

		// --Print new window
		String WindowHandlebefore = driver.getWindowHandle();
		String WinID = WindowHandlebefore;
		for (String windHandle : driver.getWindowHandles()) {
			String NewWindID = windHandle;
			if (!WinID.equals(NewWindID)) {
				driver.switchTo().window(windHandle);
				logs.info("Switched to Print window");
				Thread.sleep(5000);
				getScreenshot(driver, "PrintLabelWindow");
			}

		}
		driver.close();
		logs.info("Closed Print window");

		driver.switchTo().window(WindowHandlebefore);
		logs.info("Switched to main window");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		logs.info("=========RTE Print Label Test End============");
		msg.append("=========RTE Print Label Test End===========" + "\n");

	}

	public void routeShipmentDetails()
			throws InterruptedException, IOException, EncryptedDocumentException, InvalidFormatException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions act = new Actions(driver);
		logs.info("=========RTE Route/Shipment Details Test Start============");
		msg.append("=========RTE Route/Shipment Details Test Start===========" + "\n");

		// --Moved to Route Details
		WebElement RouteDetails = isElementPresent("TLRouteDetails_xpath");
		act.moveToElement(RouteDetails).build().perform();
		js.executeScript("arguments[0].scrollIntoView();", RouteDetails);
		logs.info("Moved to Route Details");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOf(RouteDetails));
		getScreenshot(driver, "RouteDetails");

		// --Get Route Details

		List<WebElement> RSDetails = driver.findElements(By.id("scrollRoute"));

		for (int Detail = 0; Detail < RSDetails.size(); Detail++) {

			if (Detail == 0) {
				// --Get Route Details

				List<WebElement> RDetails = RSDetails.get(Detail)
						.findElements(By.xpath("div[contains(@ng-repeat,'RouteDetailList ')]"));

				int TotalRoute = RDetails.size();
				logs.info("Total Route==" + TotalRoute);
				for (int Route = 0; Route < TotalRoute; Route++) {
					int RouteRow = Route + 1;

					// --Get Stop No
					String StopNo = RDetails.get(Route).findElement(By.id("lblStopNumber")).getText();
					logs.info("Stop No is==" + StopNo);
					setData("RouteDetail", RouteRow, 2, StopNo);

					// --Get PU/DEL
					String PUDEL = RDetails.get(Route).findElement(By.id("lblPUDUPieces")).getText();
					logs.info("PU/DEL No is==" + PUDEL);
					setData("RouteDetail", RouteRow, 3, PUDEL);

					// --Get Company
					String Company = RDetails.get(Route).findElement(By.id("lblCompanyName")).getText();
					logs.info("Company is==" + Company);
					setData("RouteDetail", RouteRow, 4, Company);

					// --Get Address
					String Address = RDetails.get(Route).findElement(By.id("lblAddress")).getText();
					logs.info("Address is==" + Address);
					setData("RouteDetail", RouteRow, 5, Address);

					// --Get Instructions
					String Instructions = RDetails.get(Route).findElement(By.id("lblInstruction")).getText();
					logs.info("Instructions is==" + Instructions);
					setData("RouteDetail", RouteRow, 6, Instructions);

					// --Get Miles
					String Miles = RDetails.get(Route).findElement(By.id("lbldistance")).getText();
					logs.info("Miles is==" + Miles);
					setData("RouteDetail", RouteRow, 7, Miles);

					// --Get Est.Minutes
					String EstMin = RDetails.get(Route).findElement(By.id("lblMinutes")).getText();
					logs.info("Estimated Minutes is==" + EstMin);
					setData("RouteDetail", RouteRow, 8, EstMin);

					// --Get ETA/POD
					String ETAPOD = RDetails.get(Route).findElement(By.id("lbleta")).getText();
					logs.info("ETA/POD is==" + ETAPOD);
					setData("RouteDetail", RouteRow, 9, ETAPOD);

					// --Get TimeZone
					String TimeZone = RDetails.get(Route).findElement(By.id("lblTimeZone")).getText();
					logs.info("TimeZone is==" + TimeZone);
					setData("RouteDetail", RouteRow, 10, TimeZone);

				}
			} else {

				// --Moved to Route Details
				WebElement ShipDetails = isElementPresent("TLShipMentDetails_xpath");
				act.moveToElement(ShipDetails).build().perform();
				js.executeScript("arguments[0].scrollIntoView();", ShipDetails);
				logs.info("Moved to Shipment Details");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				wait.until(ExpectedConditions.visibilityOf(ShipDetails));
				getScreenshot(driver, "ShipDetails");

				// --Get Shipment Details

				List<WebElement> SDetails = RSDetails.get(Detail)
						.findElements(By.xpath("div[contains(@ng-repeat,'ShipmentDetailList')]"));

				int TotalShipment = SDetails.size();
				logs.info("Total Shipment==" + TotalShipment);
				for (int Shipment = 0; Shipment < TotalShipment; Shipment++) {
					int ShipmentRow = Shipment + 1;

					// --Get Shipment No
					String ShipmentNo = SDetails.get(Shipment).findElement(By.id("lblShipmentTrackNo")).getText();
					logs.info("Shipment No is==" + ShipmentNo);
					setData("ShipmentDetails", ShipmentRow, 1, ShipmentNo);

					// --Get status
					String status = SDetails.get(Shipment).findElement(By.id("lblStatusValue")).getText();
					logs.info("status is==" + status);
					setData("ShipmentDetails", ShipmentRow, 2, status);

					// --Get PickUP
					String PickUP = SDetails.get(Shipment).findElement(By.id("lblPickupIdValue")).getText();
					logs.info("PickUP No is==" + PickUP);
					setData("ShipmentDetails", ShipmentRow, 0, PickUP);

					// --Get PUStop
					String PUStop = SDetails.get(Shipment).findElement(By.id("lblPuStopValue")).getText();
					logs.info("PUStop is==" + PUStop);
					setData("ShipmentDetails", ShipmentRow, 3, PUStop);

					// --Get DelStop
					String DelStop = SDetails.get(Shipment).findElement(By.id("lblDelStopValue")).getText();
					logs.info("DelStop is==" + DelStop);
					setData("ShipmentDetails", ShipmentRow, 4, DelStop);

					// --Get Pcs
					String Pcs = SDetails.get(Shipment).findElement(By.id("lblPcsValue")).getText();
					logs.info("No of PCs are==" + Pcs);
					setData("ShipmentDetails", ShipmentRow, 5, Pcs);
				}
			}

		}

		logs.info("=========RTE Route/Shipment Details Test End============");
		msg.append("=========RTE Route/Shipment Details Test End===========" + "\n");

	}

	public void adEditStopSequence() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Actions act = new Actions(driver);
		logs.info("=========RTE Add/Edit Stop Sequence Test Start============");
		msg.append("=========RTE Add/Edit Stop Sequence Test Start===========" + "\n");

		// --Total Number of Shipment Stop
		List<WebElement> SDetails = driver
				.findElements(By.xpath("//*[@id=\"scrollRoute\"]//div[contains(@ng-repeat,'ShipmentDetailList ')]"));
		int TotalShipment = SDetails.size();
		logs.info("Total Shipment Stop==" + TotalShipment);

		// --Move to add stop sequence
		WebElement AddStop = isElementPresent("TLAddStopSeq_xpath");
		js.executeScript("arguments[0].scrollIntoView();", AddStop);

		// --Click on Add Stop Sequence
		isElementPresent("TLAddStopSeq_xpath").click();
		logs.info("Clicked on Add Stop Sequence");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"RouteShipmentform\"]")));
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
		}else if (ZOneID.equalsIgnoreCase("PDT")) {
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
		// --TotalNo OF shipment Stop
		SDetails = driver
				.findElements(By.xpath("//*[@id=\"scrollRoute\"]//div[contains(@ng-repeat,'ShipmentDetailList ')]"));
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

		SDetails = driver
				.findElements(By.xpath("//*[@id=\"scrollRoute\"]//div[contains(@ng-repeat,'ShipmentDetailList ')]"));
		TotalShipment = SDetails.size();

		for (int ship = 0; ship < TotalShipment; ship++) {

			if (ship == 0) {
				// --Clear and Enter PUStop
				SDetails.get(ship).findElement(By.id("txtPUStop")).clear();
				SDetails.get(ship).findElement(By.id("txtPUStop")).sendKeys("2");
				logs.info("Edit PUStop");

				// --Clear and Enter DelStop
				SDetails.get(ship).findElement(By.id("txtDelStop")).clear();
				SDetails.get(ship).findElement(By.id("txtDelStop")).sendKeys("3");
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

	}

	public void addDeleteChargesRecal() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions act = new Actions(driver);
		logs.info("=========RTE Customer Charges Test Start============");
		msg.append("=========RTE Customer Charges Test Start===========" + "\n");

		// --Moved to Customer Charges
		WebElement CustCharges = isElementPresent("TLCustCharges_id");
		js.executeScript("arguments[0].scrollIntoView();", CustCharges);
		logs.info("Moved to Customer Charges");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		getScreenshot(driver, "CustomerCharges");

		// get the total Rate
		WebElement ActRate = isElementPresent("TLActRate_xpath");
		act.moveToElement(ActRate).build().perform();
		String BRate = isElementPresent("TLActRate_xpath").getText();
		logs.info("Actual Rate before Recalculate==" + BRate);

		List<WebElement> TotaChargesDiv = driver
				.findElements(By.xpath("//*[@id=\"scrollCustomer\"]//div[contains(@ng-repeat,'RateDetailList' )]"));

		int TotalCharges = TotaChargesDiv.size();
		logs.info("Total existing charges==" + TotalCharges);

		// --Select Charge type
		Select CCombo = new Select(isElementPresent("CChargeCombo_id"));
		CCombo.selectByVisibleText("ADV - ADVANCE FEES");
		Thread.sleep(2000);
		logs.info("Selected Charge Type");

		// --Add Charges
		isElementPresent("ChargeSpan_xpath").click();
		isElementPresent("CCharge_id").clear();
		isElementPresent("CCharge_id").click();
		isElementPresent("CCharge_id").sendKeys(Keys.CONTROL, "a");
		String Charge = "25";
		isElementPresent("CCharge_id").sendKeys(Charge);
		js.executeScript("document.getElementById('txtRealCostchargeamount').setAttribute('value', '25')");
		logs.info("Entered Charges");

		// --Click on Add Charge
		isElementPresent("CCAddCharge_id").click();
		logs.info("Clicked on Add Charge");
		Thread.sleep(2000);

		// --Click on Save changes
		isElementPresent("TLSaveChanges_id").click();
		logs.info("Clicked on Save changes");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		TotaChargesDiv = driver
				.findElements(By.xpath("//*[@id=\"scrollCustomer\"]//div[contains(@ng-repeat,'RateDetailList' )]"));

		int TotalChargesAf = TotaChargesDiv.size();
		logs.info("Total charges after added new charges==" + TotalChargesAf);

		if (TotalChargesAf != TotalCharges) {
			logs.info("New charge is added");

		} else {
			logs.info("New charge is not added");

		}

		// --ReCalculate

		// --Click on ReCalculate
		WebElement ReCalc = isElementPresent("TLRecal_xpath");
		act.moveToElement(ReCalc).build().perform();
		ReCalc.click();
		logs.info("Clicked on ReCalculate");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// get the total Rate
		ActRate = isElementPresent("TLActRate_xpath");
		act.moveToElement(ActRate).build().perform();
		String ARate = isElementPresent("TLActRate_xpath").getText();
		logs.info("Actual Rate after Recalculate==" + ARate);

		if (!BRate.equalsIgnoreCase(ARate)) {
			logs.info("Rate is updated");

		} else {
			logs.info("Rate is not updated");
		}
		// --Delete added charge
		// TotaChargesDiv.get(TotalChargesAf -
		// 1).findElement(By.id("imgMinus")).click();
		isElementPresent("CCDelete_id").click();
		logs.info("Clicked on Delete button of added charge");

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
		// --Get Dialogue message
		String Message = isElementPresent("DelConfMes_xpath").getText();
		logs.info("Message of the Dialogue is==" + Message);

		// --CLick on Ok Button
		isElementPresent("DelConfYes_xpath").click();
		logs.info("Click on Yes button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		TotaChargesDiv = driver
				.findElements(By.xpath("//*[@id=\"scrollCustomer\"]//div[contains(@ng-repeat,'RateDetailList' )]"));

		int TotalChargesAfD = TotaChargesDiv.size();
		logs.info("Total charges after deleted added charge==" + TotalChargesAfD);

		if (TotalChargesAf != TotalChargesAfD) {
			logs.info("Charge is deleted");

		} else {
			logs.info("Charge is not deleted");

		}
		logs.info("=========RTE Customer Charges Test End============");
		msg.append("=========RTE Customer Charges Test End===========" + "\n");

	}

	public void rteMap() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Actions act = new Actions(driver);
		logs.info("=========RTE Map Test Start============");
		msg.append("=========RTE Map Test Start===========" + "\n");

		List<WebElement> SDetails = driver.findElements(By.xpath("//div[contains(@ng-repeat,'ShipmentDetailList')]"));
		int TotalShipment = SDetails.size();
		logs.info("Total Shipment==" + TotalShipment);

		// --Moved to Map
		WebElement Map = isElementPresent("MaForm_id");
		js.executeScript("arguments[0].scrollIntoView();", Map);
		logs.info("Moved to Map form");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		getScreenshot(driver, "Map");

		// --Map Details
		String Maptext = isElementPresent("MapDetails_xpath").getText();
		logs.info("Map Details==" + Maptext);

		// --Total No of stops
		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id=\"mapRw\"]//div[@role=\"button\"]")));
		List<WebElement> Stops = driver.findElements(By.xpath("//*[@id=\"mapRw\"]//div[@role=\"button\"]"));
		int TotalStops = Stops.size();
		logs.info("Total stops==" + TotalStops);

		if (TotalShipment == 1) {
			if (TotalStops == 2) {
				logs.info("Stops in the map is as per the shipment");
				logs.info("Shipment==" + TotalShipment);
				logs.info("Stops==" + TotalStops);
			} else {
				logs.info("Stops in the map is not as per the shipment");
				logs.info("Shipment==" + TotalShipment);
				logs.info("Stops==" + TotalStops);
			}

		} else if (TotalShipment > 1) {
			if (TotalStops == TotalShipment + 1) {
				logs.info("Stops in the map is as per the shipment");
				logs.info("Shipment==" + TotalShipment);
				logs.info("Stops==" + TotalStops);
			} else {
				logs.info("Stops in the map is not as per the shipment");
				logs.info("Shipment==" + TotalShipment);
				logs.info("Stops==" + TotalStops);
			}
		} else {
			logs.info("Shipment is not exist");
			logs.info("Shipment==" + TotalShipment);
			logs.info("Stops==" + TotalStops);
		}

		// Click on stops one by one
		for (int map = 0; map < TotalStops; map++) {
			WebElement Stop = Stops.get(map);
			js.executeScript("arguments[0].click();", Stop);
			logs.info("Clicked on stop");
			getScreenshot(driver, "MapStopdetails_" + map);

		}

		Stops = driver.findElements(By.xpath("//*[@id=\"mapRw\"]//div[@role=\"button\"]"));
		TotalStops = Stops.size();
		logs.info("Total stops==" + TotalStops);

		// --Click on Full screen
		WebElement FullScreen = isElementPresent("MapFullS_xpath");
		js.executeScript("arguments[0].scrollIntoView();", FullScreen);
		FullScreen.click();
		logs.info("Clicked on Full screen of Map");
		Thread.sleep(2000);
		getScreenshot(driver, "MapFullScreen");

		// --Exit full screen
		// --Click on Full screen
		FullScreen = isElementPresent("MapFullS_xpath");
		js.executeScript("arguments[0].scrollIntoView();", FullScreen);
		FullScreen.click();
		logs.info("Clicked on Full screen of Map");
		Thread.sleep(2000);

		// --Click on Satelite
		WebElement Satelite = isElementPresent("MapSatelte_xpath");
		js.executeScript("arguments[0].scrollIntoView();", Satelite);
		js.executeScript("arguments[0].click();", Satelite);
		logs.info("Clicked on Satelite");
		Thread.sleep(2000);

		// --Click on Full screen
		FullScreen = isElementPresent("MapFullS_xpath");
		js.executeScript("arguments[0].scrollIntoView();", FullScreen);
		FullScreen.click();
		logs.info("Clicked on Full screen of Map Satelite");
		Thread.sleep(2000);
		getScreenshot(driver, "MapSateliteFullScreen");

		// --Exit full screen
		// --Click on Full screen
		FullScreen = isElementPresent("MapFullS_xpath");
		js.executeScript("arguments[0].scrollIntoView();", FullScreen);
		FullScreen.click();
		logs.info("Clicked on Full screen of Map Satelite");
		Thread.sleep(2000);

		logs.info("=========RTE Map Test End============");
		msg.append("=========RTE Map Test End===========" + "\n");

	}

}

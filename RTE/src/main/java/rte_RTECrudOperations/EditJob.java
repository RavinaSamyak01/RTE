package rte_RTECrudOperations;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rte_BasePackage.BaseInit;

public class EditJob extends BaseInit {

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

		// --Route/Shipment Details
		routeShipmentDetails();

		// --View Memo
		viewMemo();

		// --View Rate
		viewRate();

		// --Print Label
		printLabel();

		// --Route/Shipment Details
		routeShipmentDetails();
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

			// --Get the Scheduler
			String Scheduler = RateDetails.get(Rate).findElement(By.xpath("td[contains(@aria-label,'Scheduler')]"))
					.getText();
			logs.info("Name of the Scheduler is==" + Scheduler);
			setData("Rate", RateRow, 5, Scheduler);

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
				logs.info("Total Route==" + TotalShipment);
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

}

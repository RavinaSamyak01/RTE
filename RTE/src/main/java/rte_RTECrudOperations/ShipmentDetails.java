package rte_RTECrudOperations;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import rte_BasePackage.BaseInit;

public class ShipmentDetails extends BaseInit {

	public void rteShipmentDetails() throws InterruptedException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		Actions act = new Actions(driver);

		logs.info("==========RTE Shipment Details Test Start==========");
		msg.append("==========RTE Shipment Details Test Start==========" + "\n");

		// --Click on Shipment No.
		try {
			WebElement Shipment = isElementPresent("TLEdShip_id");
			act.moveToElement(Shipment).build().perform();
			Thread.sleep(2000);
			if (Shipment.isDisplayed()) {
				logs.info("Shipment is exist in the RTE Job");

				act.moveToElement(Shipment).click().perform();
				logs.info("Clicked on ShipmentNO==" + Shipment.getText());
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"RouteShipmentform\"]")));
				getScreenshot(driver, "ShipmentDetails");

				// --Enter PickUp Instruction
				WebElement PUInst = isElementPresent("TLESPicIns_id");
				act.moveToElement(PUInst).click().perform();
				Thread.sleep(2000);
				PUInst.clear();
				PUInst.sendKeys("PickUp instruction for Automation Testing");
				logs.info("Entered Pickup Instruction");

				// --Enter Deliver Instruction
				WebElement DelInst = isElementPresent("TLESDelIns_id");
				act.moveToElement(DelInst).click().perform();
				Thread.sleep(2000);
				DelInst.clear();
				DelInst.sendKeys("Delivery instruction for Automation Testing");
				logs.info("Entered Delivery Instruction");

				// --Save button
				isElementPresent("TLESSave_id").click();
				logs.info("Clicked on Save button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {
					wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@ng-message=\"required\"]")));
					String ValMsg = isElementPresent("ASRequMsg_xpath").getText();
					if (ValMsg.contains("Required")) {
						logs.info("Validation is displayed==" + ValMsg);

						// --Enter pickupPhone
						isElementPresent("ASDPUPhone_id").clear();
						isElementPresent("ASDPUPhone_id").sendKeys("1234567899");
						logs.info("Enter PickUp Phone");

						// --Save button
						isElementPresent("TLESSave_id").click();
						logs.info("Clicked on Save button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					}
				} catch (Exception Phone) {
					logs.info("Validation is not displayed, everything is as per expected");

				}
				// --Click on Upload
				upload();

				// --Click on Memo
				addViewMemo();

				// --Click on QC
				rteQC();

				// --Click on UnMerge
				rteUnMerge();

				// --

			}

		} catch (

		Exception Shipment) {
			logs.info("Shipment is not exist in the RTE Job");

		}

		logs.info("==========RTE Shipment Details Test End==========");
		msg.append("==========RTE Shipment Details Test End==========" + "\n");

	}

	public void addViewMemo() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions act = new Actions(driver);
		logs.info("=====RTE Add/View Memo Test Start=====");
		msg.append("=====RTE Add/View Memo Test Start=====" + "\n");

		try {
			// --Click on Add/View memo
			WebElement TLEVMemo = isElementPresent("TLESViewAddMemo_id");
			act.moveToElement(TLEVMemo).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(TLEVMemo));
			js.executeScript("arguments[0].click();", TLEVMemo);
			logs.info("Clicked on Add/View Memo");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("MemoPopup")));
			getScreenshot(driver, "AddViewMemo");
		} catch (Exception Memo) {
			// --Click on Add/View memo
			WebElement TLSMemo = isElementPresent("TLSMemo_xpath");
			wait.until(ExpectedConditions.elementToBeClickable(TLSMemo));
			js.executeScript("arguments[0].click();", TLSMemo);
			logs.info("Clicked on Memo");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("MemoPopup")));
			getScreenshot(driver, "Memo");

		}

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

		// --Click on All Buttons
		List<WebElement> MemoTabs = driver.findElements(By.xpath("//*[@id=\"nonhover\"]/div//button"));

		int TotalNoOfTabs = MemoTabs.size() - 1;
		logs.info("Total No of Tabs==" + TotalNoOfTabs);
		for (int Tab = 0; Tab < TotalNoOfTabs; Tab++) {
			// --Get the linkName
			String TabName = MemoTabs.get(Tab).getText();
			logs.info("Name of the Tab is==" + TabName);

			// --CLick on link
			MemoTabs.get(Tab).click();
			Thread.sleep(2000);

		}
		// --CLick on All link
		isElementPresent("TLESMAll_id").click();
		logs.info("Clicked on All link");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Enter Memo
		isElementPresent("TLESMemo_xpath").sendKeys("Creating Memo for Automation Testing");
		logs.info("Entered Memo");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// -CHeck the checkbox of visible to Customer
		WebElement CustView = isElementPresent("TLESMvCust_id");
		js.executeScript("arguments[0].click();", CustView);
		logs.info("Checked the checkbox of Make Visible to Customer");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Close popup
		isElementPresent("TLESMemoClose_id").click();
		logs.info("Clicked on Close button of Memo Popup");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		logs.info("=====RTE Add/View Memo Test End=====");
		msg.append("=====RTE Add/View Memo Test End=====" + "\n");

	}

	public void upload() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions act = new Actions(driver);
		logs.info("=====RTE Upload Test Start=====");
		msg.append("=====RTE Upload Test Start=====" + "\n");

		try {
			// --Click on Upload link
			isElementPresent("TLESUpload_id").click();
			WebElement TLEVMemo = isElementPresent("TLESUpload_id");
			act.moveToElement(TLEVMemo).build().perform();
			wait.until(ExpectedConditions.elementToBeClickable(TLEVMemo));
			js.executeScript("arguments[0].click();", TLEVMemo);
			logs.info("Clicked on Upload linktext");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			wait.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"DocDetailsForm\"]")));
			getScreenshot(driver, "ShipEditUpload");
		} catch (Exception Upload) {
			try {
				WebElement UploadLink = isElementPresent("TLSUpload_xpath");
				wait.until(ExpectedConditions.elementToBeClickable(UploadLink));
				act.moveToElement(UploadLink).build().perform();
				act.moveToElement(UploadLink).click().perform();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"DocDetailsForm\"]")));
				getScreenshot(driver, "Upload");
			} catch (Exception click) {
				WebElement UploadLink = isElementPresent("TLSUpload_xpath");
				wait.until(ExpectedConditions.elementToBeClickable(UploadLink));
				js.executeScript("arguments[0].click();", UploadLink);
				logs.info("Clicked on Upload linktext");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"DocDetailsForm\"]")));
				getScreenshot(driver, "Upload");
			}

		}
		// --CHeck if doc is added
		try {
			WebElement DocExist = isElementPresent("DocExist_xpath");
			wait.until(ExpectedConditions.visibilityOf(DocExist));
			logs.info("File is already exist");

			// --Click on Delete
			WebElement DelUpload = isElementPresent("DelUploadFile_id");
			js.executeScript("arguments[0].click();", DelUpload);

			logs.info("Clicked on Delete button of existing document");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
			// --Get Dialogue message
			String Message = isElementPresent("DelConfMes_xpath").getText();
			logs.info("Message of the Dialogue is==" + Message);

			// --CLick on Ok Button
			WebElement Yes = isElementPresent("DelConfYes_xpath");
			js.executeScript("arguments[0].click();", Yes);
			logs.info("Click on Yes button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		} catch (Exception doc) {
			logs.info("File is not exist");

		}
		// --Click on Plus sign
		isElementPresent("AddUpload_id").click();
		logs.info("Click on plus sign");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtDocName")));

		// --Enter Doc name
		isElementPresent("DocName_id").clear();
		isElementPresent("DocName_id").sendKeys("AutoDocument");
		logs.info("Enter doc name");

		// --Enter Doc Date
		WebElement DocDate = isElementPresent("DocDate_id");
		DocDate.clear();
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		logs.info(dateFormat.format(date));
		DocDate.sendKeys(dateFormat.format(date));
		DocDate.sendKeys(Keys.TAB);
		logs.info("Entered Doc Date");

		// --Select Doc Type
		Select DocType = new Select(isElementPresent("DocType_id"));
		DocType.selectByIndex(4);
		Thread.sleep(2000);
		logs.info("Select DocType");

		// --select Customer viewable checkbox
		WebElement CustView = isElementPresent("CUstView_id");
		js.executeScript("arguments[0].click();", CustView);
		logs.info("Click on Customer Viewable checkbox");
		Thread.sleep(2000);

		// --select Agent viewable checkbox
		WebElement AgentView = isElementPresent("AgentView_id");
		js.executeScript("arguments[0].click();", AgentView);
		logs.info("Click on Agent Viewable checkbox");
		Thread.sleep(2000);

		// --Click on Upload File
		WebElement UploadFile = isElementPresent("UploadFile_id");
		act.moveToElement(UploadFile).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(UploadFile));
		js.executeScript("arguments[0].click();", UploadFile);
		logs.info("Click on Upload file");
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"uploadfileForm\"]")));

		// --Select File
		Thread.sleep(2000);
		String Fpath = "C:\\Users\\rprajapati\\git\\RTE\\RTE\\src\\main\\resources\\Job Upload Doc STG.xls";
		WebElement InFile = isElementPresent("SelectFile_id");
		InFile.sendKeys(Fpath);
		logs.info("Select the file");
		Thread.sleep(2000);

		// --Click on Upload btn
		WebElement UploadBTN = isElementPresent("Uploadbtn_id");
		js.executeScript("arguments[0].click();", UploadBTN);
		logs.info("Clicked on Upload button");

		Thread.sleep(2000);
		try {
			String ErrorMsg = isElementPresent("RenameFile_xpath").getText();
			if (ErrorMsg.contains("already exists.Your file was saved as")) {
				logs.info("File already exist in the system");
			}
		} catch (Exception e) {
			logs.info("File is uploaded successfully");
		}

		// --CLick on Save&Close
		WebElement SaveClose = isElementPresent("UploadSaveClose_id");
		js.executeScript("arguments[0].click();", SaveClose);
		logs.info("Click on Save&Close button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		logs.info("=====RTE Upload Test End=====");
		msg.append("=====RTE Upload Test End=====" + "\n");
	}

	public void viewNotification() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions act = new Actions(driver);
		logs.info("=====RTE View Notification Test Start=====");
		msg.append("=====RTE View Notification Test Start=====" + "\n");

		// --Click on View Notification link
		WebElement VNotif = isElementPresent("VNotify_id");
		act.moveToElement(VNotif).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(VNotif));
		js.executeScript("arguments[0].click();", VNotif);
		logs.info("Clicked on View Notification linktext");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"modal-dialog modal-sm\"]")));
		getScreenshot(driver, "ViewNotification");

		try {
			WebElement View = isElementPresent("VNotiFView_id");
			wait.until(ExpectedConditions.visibilityOf(View));
			logs.info("Notification is exist");

			// --Click on View Notification
			isElementPresent("VNotiFView_id").click();
			logs.info("Clicked on View linktext");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			wait.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"ViewContentform\"]")));
			getScreenshot(driver, "VNotificationContent");

			// --Select Email radio button
			WebElement Email = isElementPresent("VNotiConEmail_id");
			js.executeScript("arguments[0].click();", Email);
			logs.info("Clicked on Email radio button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Email Input
			WebElement EmailInp = isElementPresent("VNotiConEmailInp_id");
			wait.until(ExpectedConditions.visibilityOf(EmailInp));
			EmailInp.clear();
			EmailInp.sendKeys("Ravina.prajapati@samyak.com");

			// --Send button
			isElementPresent("VNotiConSend_id").click();
			logs.info("Clicked on Send button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Click on Print button
			isElementPresent("VNotiConPrint_id").click();
			logs.info("Clicked on Print button");
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
					getScreenshot(driver, "NotificationPrint");
				}

			}
			driver.close();
			logs.info("Closed Print window");

			driver.switchTo().window(WindowHandlebefore);
			logs.info("Switched to main window");

		} catch (Exception View) {
			logs.info("Notification is not exist");

		}

		// --Click on Close button of Content Popup
		WebElement ContentClose = isElementPresent("VNotiConClose_xpath");
		js.executeScript("arguments[0].click();", ContentClose);
		logs.info("Clicked on Close button");
		Thread.sleep(2000);

		// --Click on Close button of Notification popup
		WebElement NotClose = isElementPresent("VNotifiClose_xpath");
		js.executeScript("arguments[0].click();", NotClose);
		logs.info("Clicked on Close button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		logs.info("=====RTE View Notification Test End=====");
		msg.append("=====RTE View Notification Test End=====" + "\n");
	}

	public void rteNotify() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions act = new Actions(driver);
		logs.info("=====RTE Notify Test Start=====");
		msg.append("=====RTE Notify Test Start=====" + "\n");

		// --Click on Notify link
		WebElement VNotif = isElementPresent("Notify_id");
		act.moveToElement(VNotif).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(VNotif));
		js.executeScript("arguments[0].click();", VNotif);
		logs.info("Clicked on Notify linktext");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("hlkaddNotify")));
		getScreenshot(driver, "Notify");

		// --Add notify
		WebElement AddNotify = isElementPresent("AddNotify_xpath");
		js.executeScript("arguments[0].click();", AddNotify);
		logs.info("Clicked on Add Notify");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"Notifydtl\"]")));

		// --Select How
		Select SHow = new Select(isElementPresent("NotHow_id"));
		SHow.selectByIndex(1);
		Thread.sleep(2000);
		logs.info("Selected Email as How");

		// --Enter Via
		isElementPresent("NOtVia_id").clear();
		isElementPresent("NOtVia_id").sendKeys("ravina.prajapati@samyak.com");
		logs.info("Enter Notify Via");

		// --Removed Request Notification
		List<WebElement> ReqNotList = driver.findElements(By.xpath("//*[@id=\"lstRequested\"]//option"));
		int TotalReqNot = ReqNotList.size();
		logs.info("Total No of Request Notifications are==" + TotalReqNot);

		if (TotalReqNot > 1) {

			Select ReqNot = new Select(isElementPresent("RequestNot_id"));
			ReqNot.selectByIndex(0);
			Thread.sleep(2000);

			// --Click on Left button
			isElementPresent("NotLeft_id").click();
			logs.info("Clicked on Left button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		} else {
			Select ReqNot = new Select(isElementPresent("RequestNot_id"));
			ReqNot.selectByIndex(0);
			Thread.sleep(2000);

			// --Click on Left button
			isElementPresent("NotLeft_id").click();
			logs.info("Clicked on Left button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		}

		ReqNotList = driver.findElements(By.xpath("//*[@id=\"lstRequested\"]//option"));
		int TotalReqNotAfter = ReqNotList.size();
		logs.info("Total No of Request Notifications are==" + TotalReqNotAfter);

		if (TotalReqNotAfter != TotalReqNot) {
			logs.info("Left Arrow is working");
		} else {
			logs.info("Left Arrow is not working");

		}

		// --Add AVailable Notification
		Select AvNot = new Select(isElementPresent("AvailbleNot_id"));
		AvNot.selectByVisibleText("CREATE ORDER");
		Thread.sleep(2000);

		// --Click on Right button
		isElementPresent("NotRight_id").click();
		logs.info("Clicked on Right button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		ReqNotList = driver.findElements(By.xpath("//*[@id=\"lstRequested\"]//option"));
		TotalReqNot = ReqNotList.size();
		logs.info("Total No of Request Notifications are==" + TotalReqNot);

		if (TotalReqNotAfter != TotalReqNot) {
			logs.info("Right Arrow is working");
		} else {
			logs.info("Right Arrow is not working");

		}

		ReqNotList = driver.findElements(By.xpath("//*[@id=\"lstRequested\"]//option"));
		TotalReqNot = ReqNotList.size();

		if (TotalReqNot > 1) {

			// --Check the index before change the sequence
			Select ReqNot = new Select(isElementPresent("RequestNot_id"));
			ReqNot.selectByIndex(0);
			Thread.sleep(2000);

			String BeforeInd = ReqNot.getFirstSelectedOption().getText();
			logs.info("Selected value==" + BeforeInd);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			Select ReqNot1 = new Select(isElementPresent("RequestNot_id"));
			ReqNot1.selectByIndex(1);
			Thread.sleep(2000);

			// --Click on Up arrow
			isElementPresent("NotUp_id").click();
			logs.info("Clicked on Up Arrow");

			// --Check the index after change the sequence
			ReqNot = new Select(isElementPresent("RequestNot_id"));
			ReqNot.selectByIndex(0);
			Thread.sleep(2000);

			String AftInd = ReqNot.getFirstSelectedOption().getText();
			logs.info("Selected value==" + AftInd);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			if (BeforeInd.equalsIgnoreCase(AftInd)) {
				logs.info("Sequence of the element is not updated");
				logs.info("Up arrow is not working");

			} else {
				logs.info("Sequence of the element is updated");
				logs.info("Up arrow is working");
			}

			// --Click on Down arrow
			isElementPresent("NotDown_id").click();
			logs.info("Clicked on Down Arrow");

		} else {
			logs.info("There is only 1 record in Request Notification");

		}

		// --Click on Save button
		isElementPresent("NotifySave_id").click();
		logs.info("Clicked on Save button");

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@ng-form=\"Notifydtl\"]")));

		// --Get Total No of Notification
		List<WebElement> TotalNot = driver.findElements(By.xpath("//*[@id=\"scrollbox13\"]/div"));
		int TotalNotify = TotalNot.size();
		logs.info("Total No of Notifications are==" + TotalNotify);

		// --Edit the last added Notify
		String LastEdit = "//*[@id=\"scrollbox13\"]/div[" + TotalNotify + "]//a[@id=\"hlkNotifydtl\"]";
		driver.findElement(By.xpath(LastEdit)).click();
		logs.info("Clicked on Edit button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"Notifydtl\"]")));

		// --Click on Cancel button
		isElementPresent("NotifyCanc_id").click();
		logs.info("Clicked on Cancel button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@ng-form=\"Notifydtl\"]")));

		// --Resend Original QDT
		isElementPresent("NotifyResetOQDT_id").click();
		logs.info("Clicked on Resend Original QDT button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
			String SuccessMsg = isElementPresent("SuccessMsg_id").getText();
			logs.info("Message==" + SuccessMsg);
		} catch (Exception Success) {
			logs.info("Notification is not send");

		}

		// --Delete Notify
		TotalNot = driver.findElements(By.xpath("//*[@id=\"scrollbox13\"]/div"));
		TotalNotify = TotalNot.size();
		logs.info("Total No of Notifications are==" + TotalNotify);

		// --Delete the last added Notify
		String LastDelete = "//*[@id=\"scrollbox13\"]/div[" + TotalNotify + "]//a[@id=\"hlkDelNotify\"]";
		driver.findElement(By.xpath(LastDelete)).click();
		logs.info("Clicked on Delete button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --CLick on OK button
		isElementPresent("NotifyOK_id").click();
		logs.info("Clicked on OK button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		logs.info("=====RTE Notify Test End=====");
		msg.append("=====RTE Notify Test End=====" + "\n");

	}

	public void rteQC() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions act = new Actions(driver);
		logs.info("=====RTE QC Test Start=====");
		msg.append("=====RTE QC Test Start=====" + "\n");

		// --Click on QC link
		WebElement TLSQC = isElementPresent("TLSQC_xpath");
		act.moveToElement(TLSQC).build().perform();
		wait.until(ExpectedConditions.elementToBeClickable(TLSQC));
		js.executeScript("arguments[0].click();", TLSQC);
		logs.info("Clicked on QC linktext");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("issueJob")));
		getScreenshot(driver, "QC");

		// --Click on Add New QC
		isElementPresent("TLQCAddNew_id").click();
		logs.info("Clicked on Add New QC");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("NewIssue")));
		getScreenshot(driver, "NewQC");

		// --Select Type
		WebElement CustQC = isElementPresent("TLQCCust_xpath");
		js.executeScript("arguments[0].click();", CustQC);
		logs.info("Selected Customer as a Type");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Select Category
		WebElement InvAdd = isElementPresent("TLQCInvAdd_xpath");
		act.moveToElement(InvAdd).build().perform();
		js.executeScript("arguments[0].click();", InvAdd);
		logs.info("Selected Invalid Address as a Category");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Enter Description
		WebElement IssueDesc = isElementPresent("TLQCIssDesc_id");
		act.moveToElement(IssueDesc).build().perform();
		IssueDesc.clear();
		IssueDesc.sendKeys("Issue is created for Automation Testing");
		logs.info("Entered issue description");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Click on Save
		WebElement QCSave = isElementPresent("TLQCSave_id");
		act.moveToElement(QCSave).build().perform();
		js.executeScript("arguments[0].click();", QCSave);
		logs.info("Clicked on Save button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(@ng-repeat,'IssueList ')]")));
		getScreenshot(driver, "AddedQC");

		// --Enter Confirm Note
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idobjresolution0")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idobjresolution0")));
			isElementPresent("TLQCConfirmNote0_id").clear();
			isElementPresent("TLQCConfirmNote0_id").sendKeys("Issue Confirmed");
			logs.info("Entered Confirm Note");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			isElementPresent("TLQCConfirmNote0_id").sendKeys(Keys.TAB);
			isElementPresent("TLQCConfirmNote0_id").sendKeys(Keys.TAB);
		} catch (Exception ID) {

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idobjresolution1")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("idobjresolution1")));
			isElementPresent("TLQCConfirmNote1_id").clear();
			isElementPresent("TLQCConfirmNote1_id").sendKeys("Issue Confirmed");
			logs.info("Entered Confirm Note");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			isElementPresent("TLQCConfirmNote1_id").sendKeys(Keys.TAB);
			isElementPresent("TLQCConfirmNote1_id").sendKeys(Keys.TAB);

		}

		// --Select status
		Select QCStatus = new Select(isElementPresent("TLQCStatus_xpath"));
		QCStatus.selectByVisibleText("Confirmed");
		logs.info("Selected status");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Click on Update
		isElementPresent("TLQCUpdate_id").click();
		logs.info("Clicked on Update button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		try {
			WebElement SuccMsg = isElementPresent("TLQCSuccMsg_id");
			wait.until(ExpectedConditions.visibilityOf(SuccMsg));
			String SuccessMsg = SuccMsg.getText();
			logs.info("QC is updated==" + SuccessMsg);

		} catch (Exception Success) {
			logs.info("QC is not updated");

		}

		// --Click on Exit without Save
		isElementPresent("TLQCExitWSave_id").click();
		logs.info("Clicked on Exit W/O Save button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		logs.info("=====RTE QC Test End=====");
		msg.append("=====RTE QC Test End=====" + "\n");
	}

	public void rteUnMerge() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// Actions act = new Actions(driver);
		logs.info("=====RTE UnMerge Test Start=====");
		msg.append("=====RTE UnMerge Test Start=====" + "\n");

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
			wait.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"ngdialog-content\"]")));
			getScreenshot(driver, "UnMergeDialogue");
			String UnMergeMsg = isElementPresent("TLUnMergeMsg_xpath").getText();
			logs.info("UnMerge Message==" + UnMergeMsg);

			// --Click on OK button
			isElementPresent("TLUnMergeOK_id").click();
			logs.info("Clicked on OK button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@class=\"ngdialog-content\"]")));

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

					// --get the pickup id from message
					String inLine = SuccMsg;
					String[] lineSplits = inLine.split("\\#");
					String[] lineDetails = lineSplits[1].split(" ");
					String PickUpID = lineDetails[1].trim();
					logs.info("PickUpID is==" + PickUpID);

					// --Get LOC pickup id from excel
					String ExLOCPickupID = getData("LocJob", 1, 0);
					logs.info("LOC PickUpID of 1st row is==" + ExLOCPickupID);

					String ExLOC2PickupID = getData("LocJob", 2, 0);
					logs.info("LOC PickUpID of 2nd row is==" + ExLOC2PickupID);

					if (ExLOCPickupID.equalsIgnoreCase(PickUpID)) {

					} else {
						setData("LocJob", 1, 0, PickUpID);

					}

					if (ExLOC2PickupID.equalsIgnoreCase(PickUpID)) {

					} else {
						setData("LocJob", 2, 0, PickUpID);

					}

					// --CLick on Cancel
					isElementPresent("TLUnMergeCancel_id").click();
					logs.info("Clicked on Cancel button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			} catch (Exception e) {
				logs.info("LOC service is not created and shipment is not unmerged");

			}

			logs.info("=====RTE UnMerge Test End=====");
			msg.append("=====RTE UnMerge Test End=====" + "\n");
		}
	}

}

package rte_RTECrudOperations;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import rte_BasePackage.BaseInit;

public class ShipmentDetails extends BaseInit {

	public void rteShipmentDetails() {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		Actions act = new Actions(driver);

		logs.info("======================RTE Shipment Details Test Start==================");
		msg.append("======================RTE Shipment Details Test Start==================" + "\n");

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

				// --Add/view Memo
				addViewMemo();

				// --Upload
				upload();

				// --Notification
				viewNotification();

				// --Notify
				rteNotify();

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

			}

		} catch (Exception Shipment) {
			logs.info("Shipment is not exist in the RTE Job");

		}

		logs.info("======================RTE Shipment Details Test End==================");
		msg.append("======================RTE Shipment Details Test End==================" + "\n");

	}

	public void addViewMemo() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// Actions act = new Actions(driver);
		logs.info("=========RTE Add/View Memo Test Start============");
		msg.append("=========RTE Add/View Memo Test Start===========" + "\n");

		// --Click on Add/View memo
		isElementPresent("TLESViewAddMemo_id").click();
		logs.info("Clicked on Add/View Memo");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("MemoPopup")));
		getScreenshot(driver, "AddViewMemo");

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
		isElementPresent("TLESMemo_id").sendKeys("Creating Memo for Automation Testing");
		logs.info("Entered Memo");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// -CHeck the checkbox of visible to Customer
		isElementPresent("TLESMvCust_id").click();
		logs.info("Checked the checkbox of Make Visible to Customer");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Close popup
		isElementPresent("TLESMemoClose_id").click();
		logs.info("Clicked on Close button of Memo Popup");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		logs.info("=========RTE Add/View Memo Test End============");
		msg.append("=========RTE Add/View Memo Test End===========" + "\n");

	}

	public void upload() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// Actions act = new Actions(driver);
		logs.info("=========RTE Upload Test Start============");
		msg.append("=========RTE Upload Test Start===========" + "\n");

		// --Click on Upload link
		isElementPresent("TLESUpload_id").click();
		logs.info("Clicked on Upload linktext");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"DocDetailsForm\"]")));
		getScreenshot(driver, "Upload");

		// --CHeck if doc is added
		try {
			WebElement DocExist = isElementPresent("DocExist_xpath");
			wait.until(ExpectedConditions.visibilityOf(DocExist));
			logs.info("File is already exist");

			// --Click on Delete
			isElementPresent("DelUploadFile_id").click();
			logs.info("Clicked on Delete button of existing document");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("modal-dialog")));
			// --Get Dialogue message
			String Message = isElementPresent("DelConfMes_xpath").getText();
			logs.info("Message of the Dialogue is==" + Message);

			// --CLick on Ok Button
			isElementPresent("DelConfYes_xpath").click();
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
		isElementPresent("DocDate_id").sendKeys("AutoDocument");
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
		isElementPresent("CUstView_id").click();
		logs.info("Click on Customer Viewable checkbox");
		Thread.sleep(2000);

		// --select Agent viewable checkbox
		isElementPresent("AgentView_id").click();
		logs.info("Click on Agent Viewable checkbox");
		Thread.sleep(2000);

		// --Click on Upload File
		isElementPresent("UploadFile_id").click();
		logs.info("Click on Upload file");
		Thread.sleep(2000);

		// --Select File
		String Fpath = "C:\\Users\\rprajapati\\git\\NetAgent\\NetAgentProcess\\Job Upload Doc STG.xls";
		WebElement InFile = isElementPresent("SelectFile_id");
		InFile.sendKeys(Fpath);
		logs.info("Select the file");
		Thread.sleep(2000);

		// --Click on Upload btn
		isElementPresent("Uploadbtn_id").click();
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
		isElementPresent("UploadSaveClose_id").click();
		logs.info("Click on Save&Close button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		logs.info("=========RTE Upload Test End============");
		msg.append("=========RTE Upload Test End===========" + "\n");
	}

	public void viewNotification() throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// Actions act = new Actions(driver);
		logs.info("=========RTE View Notification Test Start============");
		msg.append("=========RTE View Notification Test Start===========" + "\n");

		// --Click on Upload link
		isElementPresent("VNotify_id").click();
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
			isElementPresent("VNotiConEmail_id").click();
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
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			// --Print new window
			String WindowHandlebefore = driver.getWindowHandle();
			for (String windHandle : driver.getWindowHandles()) {
				if (!windHandle.equalsIgnoreCase(WindowHandlebefore)) {
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
		isElementPresent("VNotiConClose_id").click();
		logs.info("Clicked on Close button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Click on Close button of Notification popup
		isElementPresent("VNotiConClose_id").click();
		logs.info("Clicked on Close button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		logs.info("=========RTE View Notification Test End============");
		msg.append("=========RTE View Notification Test End===========" + "\n");
	}

	public void rteNotify() throws IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		// Actions act = new Actions(driver);
		logs.info("=========RTE Notify Test Start============");
		msg.append("=========RTE Notify Test Start===========" + "\n");

		// --Click on Upload link
		isElementPresent("Notify_id").click();
		logs.info("Clicked on Notify linktext");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("hlkaddNotify")));
		getScreenshot(driver, "Notify");

		// --Add notify
		isElementPresent("AddNotify_id").click();
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
			isElementPresent("AvailAllNot_xpath").sendKeys(Keys.CONTROL, "a");

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

		// --Add AVailable Notification
		Select AvNot = new Select(isElementPresent("AvailbleNot_id"));
		AvNot.selectByVisibleText("CREATE ORDER");
		Thread.sleep(2000);

		// --Click on Right button
		isElementPresent("NotRight_id").click();
		logs.info("Clicked on Right button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		AvNot = new Select(isElementPresent("AvailbleNot_id"));
		AvNot.selectByVisibleText("DELIVERED");
		Thread.sleep(2000);

		// --Click on Left button
		isElementPresent("NotRight_id").click();
		logs.info("Clicked on Right button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		ReqNotList = driver.findElements(By.xpath("//*[@id=\"lstRequested\"]//option"));
		TotalReqNot = ReqNotList.size();
		logs.info("Total No of Request Notifications are==" + TotalReqNot);

		// --Check the index before change the sequence
		Select ReqNot = new Select(isElementPresent("RequestNot_id"));
		ReqNot.selectByIndex(0);
		Thread.sleep(2000);

		String BeforeInd = ReqNot.getFirstSelectedOption().getText();
		logs.info("Selected value==" + BeforeInd);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

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

		// --Delete Notify
		TotalNot = driver.findElements(By.xpath("//*[@id=\"scrollbox13\"]/div"));
		TotalNotify = TotalNot.size();
		logs.info("Total No of Notifications are==" + TotalNotify);

		// --Edit the last added Notify
		String LastDelete = "//*[@id=\"scrollbox13\"]/div[" + TotalNotify + "]//a[@id=\"hlkDelNotify\"]";
		driver.findElement(By.xpath(LastDelete)).click();
		logs.info("Clicked on Delete button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

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

		// --CLick on OK button
		isElementPresent("NotifyOK_id").click();
		logs.info("Clicked on OK button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		logs.info("=========RTE Notify Test End============");
		msg.append("=========RTE Notify Test End===========" + "\n");

	}

}

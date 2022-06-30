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
import org.testng.annotations.Test;

import rte_BasePackage.BaseInit;

public class SearchLOCJob extends BaseInit {

	@Test
	public String searchcreatedLOCJob() throws EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions act = new Actions(driver);

		String Result = null;
		logs.info("=====RTE Search LOC job Test Start=====");
		msg.append("=====RTE Search LOC job Test Start=====" + "\n");

		// --Get pickedupID

		try {
			// --Go To Operations
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_operations")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("a_operations")));
			isElementPresent("OperationsTab_id").click();
			logs.info("Clicked on Operations");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"OpenCloseClass dropdown open\"]//ul")));

		} catch (Exception operation) {
			try {

				// --Go To Operations
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_operations")));
				WebElement Operations = isElementPresent("OperationsTab_id");
				act.moveToElement(Operations).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(Operations));
				act.moveToElement(Operations).click().perform();
				logs.info("Clicked on Operations");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
						By.xpath("//*[@class=\"OpenCloseClass dropdown open\"]//ul")));

			} catch (Exception ope) {
				// --Go To Operations
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_operations")));
				WebElement Operations = isElementPresent("OperationsTab_id");
				act.moveToElement(Operations).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(Operations));
				js.executeScript("arguments[0].click();", Operations);
				logs.info("Clicked on Operations");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
						By.xpath("//*[@class=\"OpenCloseClass dropdown open\"]//ul")));

			}
		}

		// --Go to TaskLog
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_TaskLog")));
		WebElement TaskLog = isElementPresent("TaskLog_id");
		act.moveToElement(TaskLog).build().perform();
		js.executeScript("arguments[0].click();", TaskLog);
		logs.info("Clicked on TaskLog");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		int totalRow = getTotalRow("LocJob");
		for (int row = 1; row < totalRow; row++) {
			// --Enter pickUpID
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtContains")));
			String PickUpID = getData("LocJob", row, 0);
			isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
			logs.info("Entered PickUpID in basic search");
			msg.append("PickUpID is===" + PickUpID + "\n");

			// --Click on Search
			wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
			isElementPresent("TLGlobSearch_id").click();
			logs.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			try {

				logs.info("Data is exist with search parameter");
				logs.info("LOC job is created with Unmerged shipment");
				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("ActiveShipmentId")));
					getScreenshot(driver, "JobEditor_TCACK");

					// --Job Status
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
					String jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);
					msg.append("Job status is==" + jobStatus + "\n");

					// --Click on Edit job tab
					WebElement EditJob = isElementPresent("TLEditJobtab_id");
					wait.until(ExpectedConditions.elementToBeClickable(EditJob));
					js.executeScript("arguments[0].click();", EditJob);
					logs.info("Clicked on Edit Job tab");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Enter caller name
					WebElement CallerName = isElementPresent("TLEJCallerName_id");
					js.executeScript("arguments[0].scrollIntoView();", CallerName);
					act.moveToElement(CallerName).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(CallerName));
					CallerName.sendKeys("Pickup Caller");
					logs.info("Enter Caller Name");

					// --Enter caller phone
					WebElement CallerPH = isElementPresent("TLEJCallPhone_id");
					act.moveToElement(CallerPH).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(CallerPH));
					CallerPH.sendKeys("8527419635");
					logs.info("Enter Caller Phone");

					// --Enter Del Att name
					WebElement delAName = isElementPresent("TLEJDelAtt_id");
					act.moveToElement(delAName).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(delAName));
					delAName.sendKeys("Deliver Caller");
					logs.info("Enter Deliver Caller Name");

					// --Enter Del phone
					WebElement DelPH = isElementPresent("TLEJDelPhone_id");
					act.moveToElement(DelPH).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(DelPH));
					DelPH.sendKeys("7418529635");
					logs.info("Enter Delivery Phone");
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));

					// --Enter Commodity
					WebElement Commo = isElementPresent("TLEJCommodity_id");
					act.moveToElement(Commo).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(Commo));
					Commo.clear();
					Commo.sendKeys("BOX");
					logs.info("Enter Commodity");

					// --Click on Save changes
					WebElement SaveChanges = isElementPresent("TLEJSaveChanges_id");
					act.moveToElement(SaveChanges).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
					act.moveToElement(SaveChanges).click().perform();
					logs.info("Clicked on Save Changes");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Zoom Out
					js.executeScript("document.body.style.zoom='80%';");
					Thread.sleep(2000);

					// --Click on Save&EXit
					WebElement SaveExit = isElementPresent("TLEJSaveExit_xpath");
					wait.until(ExpectedConditions.elementToBeClickable(SaveExit));
					js.executeScript("arguments[0].click();", SaveExit);
					logs.info("Clicked on Save&Exit");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Zoom IN
					js.executeScript("document.body.style.zoom='100%';");
					Thread.sleep(2000);

					Result = "PASS";

				} catch (Exception Multiple) {
					try {
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("idOperationTasklogGrd")));
						List<WebElement> jobs = driver.findElements(
								By.xpath("//td[contains(@aria-label,'Column Pickup #')]//label[@id=\"lblDateTime\"]"));
						int totaljobs = jobs.size();
						for (int job = 0; job < totaljobs; job++) {

							PickUpID = getData("LocJob", row, 0);
							logs.info("Entered PickupID is==" + PickUpID);
							msg.append("PickupID is==" + PickUpID + "\n");

							String PickUPId = jobs.get(job).getText();
							logs.info("PickupID is==" + PickUPId);

							if (PickUPId.contains(PickUpID)) {
								logs.info("Searched job is exist");

								// --Click on the job
								jobs.get(job).click();
								logs.info("Clicked on searched Job");

								// --Job Status
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
								getScreenshot(driver, "JobEditor_TCACK");
								String jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);
								msg.append("Job status is==" + jobStatus + "\n");

								// --Click on Edit job tab
								WebElement EditJob = isElementPresent("TLEditJobtab_id");
								wait.until(ExpectedConditions.elementToBeClickable(EditJob));
								js.executeScript("arguments[0].click();", EditJob);
								logs.info("Clicked on Edit Job tab");

								// --Enter caller name
								WebElement CallerName = isElementPresent("TLEJCallerName_id");
								act.moveToElement(CallerName).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(CallerName));
								CallerName.sendKeys("Pickup Caller");
								logs.info("Enter Caller Name");

								// --Enter caller phone
								WebElement CallerPH = isElementPresent("TLEJCallPhone_id");
								act.moveToElement(CallerPH).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(CallerPH));
								CallerPH.sendKeys("8527419635");
								logs.info("Enter Caller Phone");

								// --Enter Del Att name
								WebElement delAName = isElementPresent("TLEJDelAtt_id");
								act.moveToElement(delAName).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(delAName));
								delAName.sendKeys("Deliver Caller");
								logs.info("Enter Deliver Caller Name");

								// --Enter Del phone
								WebElement DelPH = isElementPresent("TLEJDelPhone_id");
								act.moveToElement(DelPH).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(DelPH));
								DelPH.sendKeys("7418529635");
								logs.info("Enter Delivery Phone");
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));

								// --Enter Commodity
								WebElement Commo = isElementPresent("TLEJCommodity_id");
								act.moveToElement(Commo).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Commo));
								Commo.clear();
								Commo.sendKeys("BOX");
								logs.info("Enter Commodity");

								// --Click on Save changes
								WebElement SaveChanges = isElementPresent("TLEJSaveChanges_id");
								act.moveToElement(SaveChanges).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
								act.moveToElement(SaveChanges).click().perform();
								logs.info("Clicked on Save Changes");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Zoom Out
								js.executeScript("document.body.style.zoom='80%';");
								Thread.sleep(2000);

								// --Click on Save&EXit
								WebElement SaveExit = isElementPresent("TLEJSaveExit_xpath");
								wait.until(ExpectedConditions.elementToBeClickable(SaveExit));
								js.executeScript("arguments[0].click();", SaveExit);
								logs.info("Clicked on Save&Exit");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Zoom IN
								js.executeScript("document.body.style.zoom='100%';");
								Thread.sleep(2000);

								Result = "PASS";

							} else {
								logs.info("Searched job is not exist");
								Result = "FAIL";

							}
						}
					} catch (Exception e) {
						wait.until(ExpectedConditions
								.visibilityOfAllElementsLocatedBy(By.xpath("//*[@data-info=\"TaskDetails\"]")));
						List<WebElement> jobs = driver.findElements(By.xpath("//*[@data-info=\"TaskDetails\"]//tasks"));
						int totaljobs = jobs.size();
						for (int job = 0; job < totaljobs; job++) {

							PickUpID = getData("LocJob", row, 0);
							logs.info("Entered PickupID is==" + PickUpID);
							msg.append("PickupID is==" + PickUpID + "\n");

							String PickUPId = jobs.get(job).getAttribute("id");
							logs.info("PickupID is==" + PickUPId);

							if (PickUPId.contains(PickUpID)) {
								logs.info("Searched job is exist");

								// --Click on the job
								jobs.get(job).click();
								logs.info("Clicked on searched Job");

								// --Job Status
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
								getScreenshot(driver, "JobEditor_TCACK");
								String jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);
								msg.append("Job status is==" + jobStatus + "\n");

								// --Click on Edit job tab
								WebElement EditJob = isElementPresent("TLEditJobtab_id");
								wait.until(ExpectedConditions.elementToBeClickable(EditJob));
								js.executeScript("arguments[0].click();", EditJob);
								logs.info("Clicked on Edit Job tab");

								// --Enter caller name
								WebElement CallerName = isElementPresent("TLEJCallerName_id");
								act.moveToElement(CallerName).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(CallerName));
								CallerName.sendKeys("Pickup Caller");
								logs.info("Enter Caller Name");

								// --Enter caller phone
								WebElement CallerPH = isElementPresent("TLEJCallPhone_id");
								act.moveToElement(CallerPH).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(CallerPH));
								CallerPH.sendKeys("8527419635");
								logs.info("Enter Caller Phone");

								// --Enter Del Att name
								WebElement delAName = isElementPresent("TLEJDelAtt_id");
								act.moveToElement(delAName).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(delAName));
								delAName.sendKeys("Deliver Caller");
								logs.info("Enter Deliver Caller Name");

								// --Enter Del phone
								WebElement DelPH = isElementPresent("TLEJDelPhone_id");
								act.moveToElement(DelPH).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(DelPH));
								DelPH.sendKeys("7418529635");
								logs.info("Enter Delivery Phone");
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));

								// --Enter Commodity
								WebElement Commo = isElementPresent("TLEJCommodity_id");
								act.moveToElement(Commo).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(Commo));
								Commo.clear();
								Commo.sendKeys("BOX");
								logs.info("Enter Commodity");

								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSaveChanges")));

								// --Click on Save changes
								WebElement SaveChanges = isElementPresent("TLEJSaveChanges_id");
								act.moveToElement(SaveChanges).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(SaveChanges));
								act.moveToElement(SaveChanges).click().perform();
								logs.info("Clicked on Save Changes");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
								// --Zoom Out
								js.executeScript("document.body.style.zoom='80%';");
								Thread.sleep(2000);

								// --Click on Save&EXit
								WebElement SaveExit = isElementPresent("TLEJSaveExit_xpath");
								wait.until(ExpectedConditions.elementToBeClickable(SaveExit));
								js.executeScript("arguments[0].click();", SaveExit);
								logs.info("Clicked on Save&Exit");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Zoom IN
								js.executeScript("document.body.style.zoom='100%';");
								Thread.sleep(2000);
								Result = "PASS";

								break;
							} else {
								logs.info("Searched job is not exist");
								Result = "FAIL";

							}
						}
					}
				}

			} catch (Exception NoData1) {

				try {
					WebElement NoData = isElementPresent("NoData_className");
					wait.until(ExpectedConditions.visibilityOf(NoData));
					if (NoData.isDisplayed()) {
						logs.info("There is no Data with Search parameter");

					}
					Result = "FAIL";
				} catch (Exception e) {
					Result = "FAIL";

				}

			}
		}
		logs.info("=====RTE Search LOC job Test End=====");
		msg.append("=====RTE Search LOC job Test End=====" + "\n");

		return Result;

	}

}

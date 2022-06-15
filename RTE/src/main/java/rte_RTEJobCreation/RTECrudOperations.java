package rte_RTEJobCreation;

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
import rte_RTECrudOperations.EditDriver;
import rte_RTECrudOperations.EditJob;
import rte_RTECrudOperations.SearchLOCJob;
import rte_RTECrudOperations.ShipmentDetails;

public class RTECrudOperations extends BaseInit {

	@Test
	public void rteCrudOperations()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 50);
		Actions act = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String Result = null;
		String SLResult = null;
		logs.info("======================RTE Crud Operations Test Start==================");
		msg.append("======================RTE Order Operations Test Start==================" + "\n");

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
		isElementPresent("TaskLog_id").click();
		logs.info("Clicked on TaskLog");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Enter pickUpID
		wait.until(ExpectedConditions.elementToBeClickable(By.id("txtContains")));
		String PickUpID = getData("SearchRTE", 3, 2);
		isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
		logs.info("PickUpID==" + PickUpID);
		logs.info("Entered PickUpID in basic search");

		// --Click on Search
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
		isElementPresent("TLGlobSearch_id").click();
		logs.info("Click on Search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		try {
			WebElement NoData = isElementPresent("NoData_className");
			wait.until(ExpectedConditions.visibilityOf(NoData));
			if (NoData.isDisplayed()) {
				logs.info("There is no Data with Search parameter");

			}
			Result = "FAIL";
		} catch (Exception NoData1) {

			try {
				logs.info("Data is exist with search parameter");
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
				getScreenshot(driver, "JobEditor_TCACK");

				try {
					WebElement JobStatusACt = isElementPresent("TLJobstatusAct_xpath");
					if (JobStatusACt.isDisplayed()) {
						logs.info("Job status tab is already Opened");
					}

				} catch (Exception JobStatus) {
					try {
						WebElement JobStatusDis = isElementPresent("TLJobstatusDsb_xpath");
						if (JobStatusDis.isDisplayed()) {
							logs.info("Job status tab is Disabled");
						}
					} catch (Exception JobStatusDisabled) {
						logs.info("Job status tab is not Opened");
						// --Go to Job Status Tab
						wait.until(ExpectedConditions.elementToBeClickable(By.id("idJobOverview")));
						isElementPresent("TLJobSTatus_id").click();
						logs.info("Clicked on Job Status Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					}

				}
				// --Job Status
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
				String jobStatus = isElementPresent("TLStageLable_id").getText();
				logs.info("Job status is==" + jobStatus);

				if (jobStatus.contains("TC ACK")) {
					logs.info("It is TC ACK stage");
					getScreenshot(driver, "JobEditor_TCACK");

					// --Shipment Details
					ShipmentDetails shipDetails = new ShipmentDetails();
					shipDetails.rteShipmentDetails();

					// --Edit Job Tab
					EditJob Ejob = new EditJob();
					Result = Ejob.editJob();
					logs.info("Result of UnMerge method is==" + Result);

					// --Click on Acknowledge button
					WebElement TCACK = isElementPresent("TLAcknoldge_id");
					act.moveToElement(TCACK).build().perform();
					wait.until(ExpectedConditions.elementToBeClickable(By.id("GreyTick")));
					act.moveToElement(TCACK).click().perform();
					logs.info("Clicked on Acknowledge button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {
						WebElement PickUPSection = isElementPresent("TLAlertstages_id");
						wait.until(ExpectedConditions.visibilityOf(PickUPSection));
						getScreenshot(driver, "JobEditor_RDYFORDSP");
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);

						// --Call Edit Driver
						EditDriver EDriver = new EditDriver();
						EDriver.rteEditDriver();

						// --Click on SendPuAlert button
						WebElement SenPUALert = isElementPresent("TLSendPUAl_id");
						act.moveToElement(SenPUALert).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
						act.moveToElement(SenPUALert).click().perform();
						logs.info("Clicked on Send PU Alert button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
							String ValMsg = isElementPresent("TLAlValidation_id").getText();
							logs.info("Validation is displayed==" + ValMsg);

							// --Enter SpokeWith
							wait.until(ExpectedConditions.elementToBeClickable(By.id("txtSpokeWith")));
							isElementPresent("TLSpokeWith_id").sendKeys("RV");
							logs.info("Entered Spoke With");

							// --Click on SendPuAlert button
							wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
							isElementPresent("TLSendPUAl_id").click();
							logs.info("Clicked on Send PU Alert button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						} catch (Exception NoVal) {
							logs.info("Spoke With validation is not displayed");

						}

					} catch (Exception NoPickupS) {
						logs.info("There is no PickUp Driver section");
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);

					}
				} else if (jobStatus.contains("RDY FOR DSP")) {
					try {
						WebElement PickUPSection = isElementPresent("TLAlertstages_id");
						wait.until(ExpectedConditions.visibilityOf(PickUPSection));
						getScreenshot(driver, "JobEditor_RDYFORDSP");
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);

						// --Call Edit Driver
						EditDriver EDriver = new EditDriver();
						EDriver.rteEditDriver();

						// --Click on SendPuAlert button
						WebElement SenPUALert = isElementPresent("TLSendPUAl_id");
						act.moveToElement(SenPUALert).build().perform();
						wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
						act.moveToElement(SenPUALert).click().perform();
						logs.info("Clicked on Send PU Alert button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
							String ValMsg = isElementPresent("TLAlValidation_id").getText();
							logs.info("Validation is displayed==" + ValMsg);

							// --Enter SpokeWith
							wait.until(ExpectedConditions.elementToBeClickable(By.id("txtSpokeWith")));
							isElementPresent("TLSpokeWith_id").sendKeys("RV");
							logs.info("Entered Spoke With");

							// --Click on SendPuAlert button
							wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
							isElementPresent("TLSendPUAl_id").click();
							logs.info("Clicked on Send PU Alert button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						} catch (Exception NoVal) {
							logs.info("Spoke With validation is not displayed");

						}

					} catch (Exception NoPickupS) {
						logs.info("There is no PickUp Driver section");
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);

					}
				} else {
					logs.info("Unknown Stage found");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);
				}

			} catch (Exception e) {
				wait.until(ExpectedConditions
						.visibilityOfAllElementsLocatedBy(By.xpath("//*[@data-info=\"TaskDetails\"]")));
				List<WebElement> jobs = driver.findElements(By.xpath("//*[@data-info=\"TaskDetails\"]//tasks"));
				int totaljobs = jobs.size();
				for (int job = 0; job < totaljobs; job++) {

					PickUpID = getData("SearchRTE", 3, 2);
					logs.info("Entered PickupID is==" + PickUpID);

					String PickUPId = jobs.get(job).getAttribute("id");
					logs.info("PickupID is==" + PickUPId);

					if (PickUPId.contains(PickUpID)) {
						logs.info("Searched job is exist");

						// --Click on the job
						jobs.get(job).click();
						logs.info("Clicked on searched Job");

						logs.info("Data is exist with search parameter");
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
						getScreenshot(driver, "JobEditor_TCACK");

						try {
							WebElement JobStatusACt = isElementPresent("TLJobstatusAct_xpath");
							if (JobStatusACt.isDisplayed()) {
								logs.info("Job status tab is already Opened");
							}

						} catch (Exception JobStatus) {
							try {
								WebElement JobStatusDis = isElementPresent("TLJobstatusDsb_xpath");
								if (JobStatusDis.isDisplayed()) {
									logs.info("Job status tab is Disabled");
								}
							} catch (Exception JobStatusDisabled) {
								logs.info("Job status tab is not Opened");
								// --Go to Job Status Tab
								wait.until(ExpectedConditions.elementToBeClickable(By.id("idJobOverview")));
								isElementPresent("TLJobSTatus_id").click();
								logs.info("Clicked on Job Status Tab");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							}

						}
						// --Job Status
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
						String jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);

						if (jobStatus.contains("TC ACK")) {
							logs.info("It is TC ACK stage");
							getScreenshot(driver, "JobEditor_TCACK");

							// --Shipment Details
							ShipmentDetails shipDetails = new ShipmentDetails();
							shipDetails.rteShipmentDetails();

							// --Edit Job Tab
							EditJob Ejob = new EditJob();
							Result = Ejob.editJob();
							logs.info("Result of UnMerge method is==" + Result);

							// --Click on Acknowledge button
							WebElement TCACK = isElementPresent("TLAcknoldge_id");
							act.moveToElement(TCACK).build().perform();
							wait.until(ExpectedConditions.elementToBeClickable(By.id("GreyTick")));
							act.moveToElement(TCACK).click().perform();
							logs.info("Clicked on Acknowledge button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							try {
								WebElement PickUPSection = isElementPresent("TLAlertstages_id");
								wait.until(ExpectedConditions.visibilityOf(PickUPSection));
								getScreenshot(driver, "JobEditor_RDYFORDSP");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

								// --Call Edit Driver
								EditDriver EDriver = new EditDriver();
								EDriver.rteEditDriver();

								// --Click on SendPuAlert button
								WebElement SenPUALert = isElementPresent("TLSendPUAl_id");
								act.moveToElement(SenPUALert).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
								act.moveToElement(SenPUALert).click().perform();
								logs.info("Clicked on Send PU Alert button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.id("idValidationforMain")));
									String ValMsg = isElementPresent("TLAlValidation_id").getText();
									logs.info("Validation is displayed==" + ValMsg);

									// --Enter SpokeWith
									wait.until(ExpectedConditions.elementToBeClickable(By.id("txtSpokeWith")));
									isElementPresent("TLSpokeWith_id").sendKeys("RV");
									logs.info("Entered Spoke With");

									// --Click on SendPuAlert button
									wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
									isElementPresent("TLSendPUAl_id").click();
									logs.info("Clicked on Send PU Alert button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								} catch (Exception NoVal) {
									logs.info("Spoke With validation is not displayed");

								}

							} catch (Exception NoPickupS) {
								logs.info("There is no PickUp Driver section");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

							}
						} else if (jobStatus.contains("RDY FOR DSP")) {
							try {
								WebElement PickUPSection = isElementPresent("TLAlertstages_id");
								wait.until(ExpectedConditions.visibilityOf(PickUPSection));
								getScreenshot(driver, "JobEditor_RDYFORDSP");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

								// --Call Edit Driver
								EditDriver EDriver = new EditDriver();
								EDriver.rteEditDriver();

								// --Click on SendPuAlert button
								WebElement SenPUALert = isElementPresent("TLSendPUAl_id");
								act.moveToElement(SenPUALert).build().perform();
								wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
								act.moveToElement(SenPUALert).click().perform();
								logs.info("Clicked on Send PU Alert button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.id("idValidationforMain")));
									String ValMsg = isElementPresent("TLAlValidation_id").getText();
									logs.info("Validation is displayed==" + ValMsg);

									// --Enter SpokeWith
									wait.until(ExpectedConditions.elementToBeClickable(By.id("txtSpokeWith")));
									isElementPresent("TLSpokeWith_id").sendKeys("RV");
									logs.info("Entered Spoke With");

									// --Click on SendPuAlert button
									wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
									isElementPresent("TLSendPUAl_id").click();
									logs.info("Clicked on Send PU Alert button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								} catch (Exception NoVal) {
									logs.info("Spoke With validation is not displayed");

								}

							} catch (Exception NoPickupS) {
								logs.info("There is no PickUp Driver section");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

							}
						} else {
							logs.info("Unknown Stage found");
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);
						}

					} else {
						logs.info("Searched job is not exist");

					}
				}
			}
		}

		logs.info("======================RTE Crud Operations Test End==================");
		msg.append("======================RTE Order Operations Test End==================" + "\n");

		// --run search LOC job
		// --start search loc job and update
		if (Result == null) {
			logs.info("Crud operation is not working");

		} else if (Result.equalsIgnoreCase("PASS")) {
			logs.info("Search LOC job method is run because UnMerge is working");

			// --Call search LOC job method
			SearchLOCJob SLJob = new SearchLOCJob();
			SLResult = SLJob.searchcreatedLOCJob();
			logs.info("Result of Search LOC job method is==" + SLResult);

		} else if (Result.equalsIgnoreCase("FAIL")) {
			logs.info("Search LOC job method is not run because Crud Operation is not run");
			logs.info("Result of Search LOC job method is==" + SLResult);

		} else {
			logs.info("Search LOC job method is not run because UnMerge is not working");
			logs.info("Result of Search LOC job method is==" + SLResult);

		}

		// --Run Create and Merge Job with RTE
		if (SLResult.equalsIgnoreCase("PASS")) {
			logs.info("Create Merge RTE with LOC method is run because Search LOC job method is working");

			// --Call search LOC job method
			CreateMergeRTEwithLOC CMRL = new CreateMergeRTEwithLOC();
			CMRL.createMergeRTEWithLOCJob();

		} else {
			logs.info("Create Merge RTE with LOC method is not run because Search LOC job method is not working");

		}

	}

}

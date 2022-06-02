package rte_RTEJobCreation;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import rte_BasePackage.BaseInit;

public class RTEJobSearch extends BaseInit {

	@Test
	public void rteJobSearch() throws IOException, EncryptedDocumentException, InvalidFormatException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		Actions act = new Actions(driver);
		// JavascriptExecutor js = (JavascriptExecutor) driver;

		logs.info("======================RTE Job Search Test start==================");
		msg.append("======================RTE Job Search Test start==================" + "\n");

		// --Go To Operations
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_operations")));
		WebElement Operations = isElementPresent("OperationsTab_id");
		act.moveToElement(Operations).click().perform();
		logs.info("Clicked on Operations");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"OpenCloseClass dropdown open\"]//ul")));

		// --Go to TaskLog
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_TaskLog")));
		isElementPresent("TaskLog_id").click();
		logs.info("Clicked on TaskLog");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		getScreenshot(driver, "TaskLog");

		// --Go to Search All Job
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkOrderSearch")));
		WebElement SearchAllJob = isElementPresent("TLSearchAllJob_id");
		act.moveToElement(SearchAllJob).build().perform();
		act.moveToElement(SearchAllJob).click().perform();
		logs.info("Clicked on SearchAllJobs");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("AdvancesSearch")));

		int TotalRow = getTotalRow("RTECreation");
		logs.info("Total Rows==" + TotalRow);

		for (int row = 1; row < 7; row++) {

			String Scenario = getData("RTECreation", row, 3);
			// --Reset button
			isElementPresent("RLReset_id").click();
			logs.info("Clicked on Reset button");

			// --Set Origin FRom Date
			WebElement OFromDate = isElementPresent("TLOrderRFrom_id");
			OFromDate.clear();
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			logs.info(dateFormat.format(date));
			OFromDate.sendKeys(dateFormat.format(date));
			OFromDate.sendKeys(Keys.TAB);
			logs.info("Entered Origin From Date");

			// --Set Origin To Date
			WebElement OToDate = isElementPresent("TLOrderRTo_id");
			OToDate.clear();
			date = new Date();
			dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			logs.info(dateFormat.format(date));
			OToDate.sendKeys(dateFormat.format(date));
			OToDate.sendKeys(Keys.TAB);
			logs.info("Entered Origin From Date");

			// --RouteTrackingNo
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtRouteTrackingNum")));
			String RouteTrackingNo = getData("RTECreation", row, 2);
			logs.info("Route Tracking No==" + RouteTrackingNo);
			isElementPresent("TLSARoutTrackNo_id").sendKeys(RouteTrackingNo);
			logs.info("Entered RouteTrackingID");

			// --Click on Search
			wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
			isElementPresent("RLSearch_id").click();
			logs.info("Click on Search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			try {
				WebElement NoData = isElementPresent("NoData_className");
				wait.until(ExpectedConditions.visibilityOf(NoData));
				if (NoData.isDisplayed()) {
					logs.info("There is no Data with Search TrackingNO");

				}

			} catch (Exception NoData) {
				logs.info("Data is exist with search TrackingNO");

				// --Stored all the records of the table
				List<WebElement> Records = driver
						.findElements(By.xpath("//*[contains(@class,'dx-datagrid-table-content')]//tbody//tr"));
				int RecordNo = Records.size() - 1;
				logs.info("Total No of records are==" + RecordNo);

				for (int RTE = 0; RTE < RecordNo; RTE++) {
					String JobID = "lblJobIdValue_" + RTE;
					String PickUpID = "lblPickupIdValue_" + RTE;
					String BOLNO = "lblBOLNumValue_" + RTE;

					// --Moved to JobID
					WebElement BOLNODiv = driver.findElement(By.id(BOLNO));
					act.moveToElement(BOLNODiv).build().perform();

					String JobIDValue = driver.findElement(By.id(JobID)).getText();
					String PickUpIDValue = driver.findElement(By.id(PickUpID)).getText();
					String BOLNoValue = driver.findElement(By.id(BOLNO)).getText();

					int RT = RTE + 1;
					Scenario = getData("RTECreation", row, 3);
					if (Scenario.equalsIgnoreCase("One To One") && row == 1) {
						logs.info("JobID is==" + JobIDValue);
						setData("SearchRTE", 1, 1, JobIDValue);
						String JobIDVal = getData("SearchRTE", RT, 1);
						logs.info("JobIDVal In Excel is==" + JobIDVal);

						logs.info("PickUpID is==" + PickUpIDValue);
						setData("SearchRTE", 1, 2, PickUpIDValue);
						String PickupIDVal = getData("SearchRTE", RT, 2);
						logs.info("PickupIDVal In Excel is==" + PickupIDVal);

						logs.info("BOLNo is==" + BOLNoValue);
						setData("SearchRTE", 1, 3, BOLNoValue);
						String BOLNOVal = getData("SearchRTE", RT, 3);
						logs.info("BOLNOVal In Excel is==" + BOLNOVal);

					} else if (Scenario.equalsIgnoreCase("One To One") && row == 2) {
						logs.info("JobID is==" + JobIDValue);
						setData("SearchRTE", 2, 1, JobIDValue);
						String JobIDVal = getData("SearchRTE", RT, 1);
						logs.info("JobIDVal In Excel is==" + JobIDVal);

						logs.info("PickUpID is==" + PickUpIDValue);
						setData("SearchRTE", 2, 2, PickUpIDValue);
						String PickupIDVal = getData("SearchRTE", RT, 2);
						logs.info("PickupIDVal In Excel is==" + PickupIDVal);

						logs.info("BOLNo is==" + BOLNoValue);
						setData("SearchRTE", 2, 3, BOLNoValue);
						String BOLNOVal = getData("SearchRTE", RT, 3);
						logs.info("BOLNOVal In Excel is==" + BOLNOVal);

					} else if (Scenario.equalsIgnoreCase("One To One") && row == 3) {
						logs.info("JobID is==" + JobIDValue);
						setData("SearchRTE", 3, 1, JobIDValue);

						logs.info("PickUpID is==" + PickUpIDValue);
						setData("SearchRTE", 3, 2, PickUpIDValue);

						logs.info("BOLNo is==" + BOLNoValue);
						setData("SearchRTE", 3, 3, BOLNoValue);

					} else if (Scenario.equalsIgnoreCase("One To One") && row == 7) {
						logs.info("JobID is==" + JobIDValue);
						setData("SearchRTE", 4, 1, JobIDValue);

						logs.info("PickUpID is==" + PickUpIDValue);
						setData("SearchRTE", 4, 2, PickUpIDValue);

						logs.info("BOLNo is==" + BOLNoValue);
						setData("SearchRTE", 4, 3, BOLNoValue);

					} else if (Scenario.equalsIgnoreCase("One To Many")) {
						logs.info("JobID is==" + JobIDValue);
						setData("OneToMany", RT, 1, JobIDValue);

						logs.info("PickUpID is==" + PickUpIDValue);
						setData("OneToMany", RT, 2, PickUpIDValue);

						logs.info("BOLNo is==" + BOLNoValue);
						setData("OneToMany", RT, 3, BOLNoValue);

					} else if (Scenario.equalsIgnoreCase("Many to One")) {
						logs.info("JobID is==" + JobIDValue);
						setData("ManyToOne", RT, 1, JobIDValue);

						logs.info("PickUpID is==" + PickUpIDValue);
						setData("ManyToOne", RT, 2, PickUpIDValue);

						logs.info("BOLNo is==" + BOLNoValue);
						setData("ManyToOne", RT, 3, BOLNoValue);

					} else if (Scenario.equalsIgnoreCase("Many To Many")) {
						logs.info("JobID is==" + JobIDValue);
						setData("ManyToMany", RT, 1, JobIDValue);

						logs.info("PickUpID is==" + PickUpIDValue);
						setData("ManyToMany", RT, 2, PickUpIDValue);

						logs.info("BOLNo is==" + BOLNoValue);
						setData("ManyToMany", RT, 3, BOLNoValue);

					}

					// --Not working in jenkins because of window size
					/*
					 * // ---Select Record WebElement Job = driver.findElement(By.id(JobID));
					 * act.moveToElement(Job).click().perform(); logs.info("Clicked on Record");
					 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")
					 * ));
					 * 
					 * wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(
					 * "RouteWorkFlow"))); getScreenshot(driver, "JobEditor_RWTrackingID");
					 * 
					 * // --Exit Without Save isElementPresent("TLEXWSave_id").click();
					 * logs.info("Clicked on Exit without Save");
					 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")
					 * ));
					 */

					// --Go to TaskManager tab
					wait.until(ExpectedConditions.elementToBeClickable(By.id("hlkTaskManager")));
					isElementPresent("TaskManager_id").click();
					logs.info("Click on Task Manager Tab");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Enter pickUpID

					if (Scenario.equalsIgnoreCase("One To One")) {
						PickUpID = getData("SearchRTE", RT, 2);

					} else if (Scenario.equalsIgnoreCase("One To Many")) {
						PickUpID = getData("OneToMany", RT, 2);

					} else if (Scenario.equalsIgnoreCase("Many to One")) {
						PickUpID = getData("ManyToOne", RT, 2);

					} else if (Scenario.equalsIgnoreCase("Many To Many")) {
						PickUpID = getData("ManyToMany", RT, 2);

					}
					System.out.println("PickedUp id==" + PickUpID);
					wait.until(ExpectedConditions.elementToBeClickable(By.id("txtContains")));
					isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
					logs.info("Entered PickUpID in basic search");

					// --Click on Search
					wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
					isElementPresent("TLGlobSearch_id").click();
					logs.info("Click on Search button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {
						WebElement NoData1 = isElementPresent("NoData_className");
						wait.until(ExpectedConditions.visibilityOf(NoData1));
						if (NoData1.isDisplayed()) {
							logs.info("There is no Data with Search PickUpID");

						}

					} catch (Exception NoDataEx) {
						logs.info("Data is exist with search PickUpID");

						try {
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
							getScreenshot(driver, "JobEditor_PickUP");

							// --Exit Without Save
							isElementPresent("TLEXWSave_id").click();
							logs.info("Clicked on Exit without Save");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Go to Search All Job
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkOrderSearch")));
							isElementPresent("TLSearchAllJob_id").click();
							logs.info("Clicked on SearchAllJobs");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("AdvancesSearch")));

						} catch (Exception Multiple) {
							wait.until(ExpectedConditions
									.visibilityOfAllElementsLocatedBy(By.id("idOperationTasklogGrd")));
							List<WebElement> jobs = driver.findElements(By.xpath(
									"//td[contains(@aria-label,'Column Pickup #')]//label[@id=\"lblDateTime\"]"));
							int totaljobs = jobs.size();
							for (int job = 0; job < totaljobs; job++) {

								PickUpID = getData("SearchRTE", row, 2);
								logs.info("Entered PickupID is==" + PickUpID);

								String PickUPId = jobs.get(job).getText();
								logs.info("PickupID is==" + PickUPId);

								if (PickUPId.contains(PickUpID)) {
									logs.info("Searched job is exist");
									/*
									 * // --Click on the job jobs.get(job).click();
									 * logs.info("Clicked on searched Job");
									 * 
									 * // --Job Status wait.until(ExpectedConditions
									 * .visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
									 * getScreenshot(driver, "JobEditor_PickUP");
									 * 
									 * // --Exit Without Save isElementPresent("TLEXWSave_id").click();
									 * logs.info("Clicked on Exit without Save");
									 * wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")
									 * ));
									 * 
									 */
									// --Go to Search All Job
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkOrderSearch")));
									isElementPresent("TLSearchAllJob_id").click();
									logs.info("Clicked on SearchAllJobs");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.id("AdvancesSearch")));

									break;
								} else {
									logs.info("Searched job is not exist");

								}
							}

						}
					}

				}

			}

		}
		logs.info("======================RTE Job Search Test End==================");
		msg.append("======================RTE Job Search Test End==================" + "\n");

	}
}

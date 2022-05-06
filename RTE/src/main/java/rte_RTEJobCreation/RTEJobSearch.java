package rte_RTEJobCreation;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rte_BasePackage.BaseInit;

public class RTEJobSearch extends BaseInit {

	public void rteJobSearch() throws IOException, EncryptedDocumentException, InvalidFormatException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		Actions act = new Actions(driver);

		logs.info("======================RTE Job Search Test start==================");
		msg.append("======================RTE Job Search Test start==================");

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
		isElementPresent("TLSearchAllJob_id").click();
		logs.info("Clicked on SearchAllJobs");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("AdvancesSearch")));

		// --Reset button
		isElementPresent("RLReset_id").click();
		logs.info("Clicked on Reset button");

		// --RouteTrackingNo
		wait.until(ExpectedConditions.elementToBeClickable(By.id("txtRouteTrackingNum")));
		String RouteTrackingNo = getData("RTECreation", 1, 2);
		setData("Search RTE", 0, 1, RouteTrackingNo);
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
				logs.info("There is no Data with Search parameter");

			}

		} catch (Exception NoData) {
			logs.info("Data is exist with search parameter");

			// --Stored all the records of the table
			List<WebElement> Records = driver
					.findElements(By.xpath("//*[contains(@class,'dx-datagrid-table-content')]//tbody//tr"));
			int RecordNo = Records.size() - 1;
			logs.info("Total No of records are==" + RecordNo);

			for (int RTE = 0; RTE < Records.size() - 1; RTE++) {
				String JobID = "lblJobIdValue_" + RTE;
				String PickUpID = "lblPickupIdValue_" + RTE;
				String BOLNO = "lblBOLNumValue_" + RTE;

				logs.info("JobID is==" + JobID);
				setData("Search RTE", 1, 1, JobID);

				logs.info("PickUpID is==" + PickUpID);
				setData("Search RTE", 1, 2, PickUpID);

				logs.info("BOLNo is==" + BOLNO);
				setData("Search RTE", 1, 3, BOLNO);

				// ---Select Record
				driver.findElement(By.id(JobID)).click();
				logs.info("Clicked on Record");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
				getScreenshot(driver, "JobEditor_RWTrackingID");

			
				// --Exit Without Save
				isElementPresent("TLEXWSave_id").click();
				logs.info("Clicked on Exit without Save");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Enter pickUpID
				wait.until(ExpectedConditions.elementToBeClickable(By.id("txtContains")));
				PickUpID = getData("Search RTE", 1, 2);
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
						logs.info("There is no Data with Search parameter");

					}

				} catch (Exception NoDataEx) {
					logs.info("Data is exist with search parameter");
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
					getScreenshot(driver, "JobEditor_PickUP");

					// --Exit Without Save
					isElementPresent("TLEXWSave_id").click();
					logs.info("Clicked on Exit without Save");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				}

			}

		}
		logs.info("======================RTE Job Search Test End==================");
		msg.append("======================RTE Job Search Test End==================");

	}

}

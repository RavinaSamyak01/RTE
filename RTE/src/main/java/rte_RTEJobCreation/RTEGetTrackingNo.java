package rte_RTEJobCreation;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import rte_BasePackage.BaseInit;

public class RTEGetTrackingNo extends BaseInit {

	@Test
	public void getRTETrackingNo() throws IOException, EncryptedDocumentException, InvalidFormatException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		Actions act = new Actions(driver);

		logs.info("======================RTE get RWTrackingNo Test start==================");
		msg.append("======================RTE get RWTrackingNo Test start==================" + "\n");

		// --Search RTE Job in RouteList
		// --Go to RouteList
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

		getScreenshot(driver, "RouteList");

		int TotalRow = getTotalRow("RTECreation");
		logs.info("Total Rows==" + TotalRow);

		for (int row = 1; row < TotalRow; row++) {

			// --Enter RoutWorkID
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtRouteWorkId")));
			String RoutWID = getData("RTECreation", row, 1);
			msg.append("RouteWorkID is==" + RoutWID + "\n");
			isElementPresent("RLRWIDInput_id").clear();
			isElementPresent("RLRWIDInput_id").sendKeys(RoutWID);
			logs.info("Entered RoutWorkID");

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
			} catch (Exception NoData1) {

				logs.info("Data is exist with search parameter");
				String RWTrackingNo = isElementPresent("RLRWTrackingNo_xpath").getText();

				if (RWTrackingNo.isEmpty()) {
					logs.info("RWTrackingNo is still not generated");

				} else {
					logs.info("RWTrackingNo is generated");
					RWTrackingNo = isElementPresent("RLRWTrackingNo_xpath").getText();
					logs.info("RWTrackingNo is ==" + RWTrackingNo);
					msg.append("RWTrackingNo is ==" + RWTrackingNo + "\n");

					String Scenario = getData("RTECreation", row, 3);

					if (row == 1 && Scenario.equalsIgnoreCase("One To One")) {
						setData("RTECreation", row, 2, RWTrackingNo);
						setData("SearchRTE", 1, 0, RWTrackingNo);
						logs.info("Inserted RWTrackingNo in Excel");
					} else if (row == 2 && Scenario.equalsIgnoreCase("One To One")) {
						setData("RTECreation", row, 2, RWTrackingNo);
						setData("SearchRTE", 2, 0, RWTrackingNo);
						logs.info("Inserted RWTrackingNo in Excel");
					} else if (row == 3 && Scenario.equalsIgnoreCase("One To One")) {
						setData("RTECreation", row, 2, RWTrackingNo);
						setData("SearchRTE", 3, 0, RWTrackingNo);
						logs.info("Inserted RWTrackingNo in Excel");
					} else if (row == 7 && Scenario.equalsIgnoreCase("One To One")) {
						setData("RTECreation", row, 2, RWTrackingNo);
						setData("SearchRTE", 4, 0, RWTrackingNo);
						logs.info("Inserted RWTrackingNo in Excel");
					} else if (row == 8 && Scenario.equalsIgnoreCase("One To One")) {
						setData("RTECreation", row, 2, RWTrackingNo);
						setData("SearchRTE", 5, 0, RWTrackingNo);
						logs.info("Inserted RWTrackingNo in Excel");
					} else if (Scenario.equalsIgnoreCase("One To Many")) {
						setData("RTECreation", row, 2, RWTrackingNo);
						setData("OneToMany", 1, 0, RWTrackingNo);
						logs.info("Inserted RWTrackingNo in Excel");

					} else if (Scenario.equalsIgnoreCase("Many to One")) {
						setData("RTECreation", row, 2, RWTrackingNo);
						setData("ManyToOne", 1, 0, RWTrackingNo);
						logs.info("Inserted RWTrackingNo in Excel");

					} else if (Scenario.equalsIgnoreCase("Many To Many")) {
						setData("RTECreation", row, 2, RWTrackingNo);
						setData("ManyToMany", 1, 0, RWTrackingNo);
						logs.info("Inserted RWTrackingNo in Excel");

					}

				}

			}
		}

		logs.info("======================RTE get RWTrackingNo Test End==================");
		msg.append("======================RTE get RWTrackingNo Test End==================" + "\n");
	}

}

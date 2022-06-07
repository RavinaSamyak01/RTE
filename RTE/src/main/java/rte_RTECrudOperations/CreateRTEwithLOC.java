package rte_RTECrudOperations;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import rte_BasePackage.BaseInit;

public class CreateRTEwithLOC extends BaseInit {

	@Test
	public void createRTEWithLOCJob() throws EncryptedDocumentException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		Actions act = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		logs.info("======================Create RTE with LOC Job Test Start==================");
		msg.append("======================Create RTE with LOC Job Test Start==================" + "\n");

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

		// --Enter pickUpID
		wait.until(ExpectedConditions.elementToBeClickable(By.id("txtContains")));
		// --get the pickupId of LOC job which is created by Unmerge
		String PickUpID = getData("LocJob", 1, 0);
		isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
		logs.info("PickUpID==" + PickUpID);
		logs.info("Entered PickUpID in basic search");

		// --Click on Color code
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//*[@id=\"ul5\"]//a[@ng-click=\"parentclick(l,'YELLOW','YELLOW')\"]")));
		isElementPresent("TLYellowClr_xpath").click();
		logs.info("Click on Yellow color Linktext");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		try {
			WebElement NoData = isElementPresent("TLNORecord_xpath");
			wait.until(ExpectedConditions.visibilityOf(NoData));
			if (NoData.isDisplayed()) {
				logs.info("There is no Data with Search parameter");

			}

		} catch (Exception NoData) {
			logs.info("Data is exist with search parameter");
			wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"flex-container\"]//tasks")));
			getScreenshot(driver, "TaskLogWithLOCJob");

			wait.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@data-info=\"TaskDetails\"]")));
			List<WebElement> jobs = driver.findElements(By.xpath("//*[@data-info=\"TaskDetails\"]//tasks"));
			int totaljobs = jobs.size();
			for (int job = 0; job < totaljobs; job++) {

				PickUpID = getData("LocJob", 1, 0);
				logs.info("Entered PickupID is==" + PickUpID);

				String PickUPId = jobs.get(job).getAttribute("id");
				logs.info("PickupID is==" + PickUPId);

				if (PickUPId.contains(PickUpID)) {
					logs.info("Searched job is exist");

					// --Select the checkbox
					WebElement chkBx = jobs.get(job).findElement(By.tagName("input"));
					// wait.until(ExpectedConditions.elementToBeClickable(chkBx));
					js.executeScript("arguments[0].click();", chkBx);
					logs.info("Selected the Loc job");

					// --Click on Create RTE button
					WebElement CreateRTE = isElementPresent("TLCreateRTE_id");
					wait.until(ExpectedConditions.elementToBeClickable(CreateRTE));
					act.moveToElement(CreateRTE).build().perform();
					act.moveToElement(CreateRTE).click().perform();
					logs.info("Clicked on Create RTE job");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Create RTE form
					wait.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"CreateRTEForm\"]")));
					getScreenshot(driver, "TLCreateRTEForm");

					// --RW Name
					isElementPresent("TLRWName_id").clear();
					isElementPresent("TLRWName_id").sendKeys("Automation Merge RTE W LOC");
					logs.info("Entered Route Work Name");

					// --Declared Value
					isElementPresent("TLRWDecValue_id").clear();
					isElementPresent("TLRWDecValue_id").sendKeys("10");
					logs.info("Entered Declare Value");

					// --Reference 2
					isElementPresent("TLRWRef2_id").clear();
					isElementPresent("TLRWRef2_id").sendKeys("AutoRef2");
					logs.info("Entered Reference 2");

					// --Reference 4
					isElementPresent("TLRWRef4_id").clear();
					isElementPresent("TLRWRef4_id").sendKeys("AutoRef4");
					logs.info("Entered Reference 4");

					// --RW Manifest
					isElementPresent("TLRWManifest_id").clear();
					isElementPresent("TLRWManifest_id").sendKeys("Ravina.prajapati@samyak.com");
					logs.info("Entered RW Manifest");

					// --Enter Ready Date
					WebElement ReadyDate = isElementPresent("TLRWReadyDate_id");
					ReadyDate.clear();
					Date date = new Date();
					DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					logs.info(dateFormat.format(date));
					ReadyDate.sendKeys(dateFormat.format(date));
					ReadyDate.sendKeys(Keys.TAB);
					logs.info("Entered Ready Date");

					// --Enter Ready Time
					WebElement ReadyTime = isElementPresent("TLRWRTime_id");
					ReadyTime.clear();
					date = new Date();
					dateFormat = new SimpleDateFormat("HH:mm");
					logs.info(dateFormat.format(date));
					ReadyTime.sendKeys(dateFormat.format(date));
					logs.info("Entered Ready Time");

					// --Enter End Date
					WebElement EndDate = isElementPresent("TLRWSEndDate_id");
					EndDate.clear();
					dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					cal.add(Calendar.DATE, 2);
					String newDate = dateFormat.format(cal.getTime());
					logs.info(newDate);
					EndDate.sendKeys(newDate);
					EndDate.sendKeys(Keys.TAB);
					logs.info("Entered Start Date");

					// --Enter End Time
					WebElement EndTime = isElementPresent("TLRWSEndTime_id");
					EndTime.clear();
					date = new Date();
					dateFormat = new SimpleDateFormat("HH:mm");
					logs.info(dateFormat.format(date));
					EndTime.sendKeys(dateFormat.format(date));
					logs.info("Entered End Time");

					// Contact Name
					isElementPresent("TLRWUCName_id").clear();
					isElementPresent("TLRWUCName_id").sendKeys("Ravina Prajapati");
					logs.info("Entered Contact Name");

					// Company Name
					isElementPresent("TLRWUComName_id").clear();
					isElementPresent("TLRWUComName_id").sendKeys("Automation return Pvt. Ltd.");
					logs.info("Entered Company Name");

					// AddressLine 1
					isElementPresent("TLRWUnAdd_id").clear();
					isElementPresent("TLRWUnAdd_id").sendKeys("1 Cooper Plaza");
					logs.info("Entered AddressLine 1");

					// ZipCode
					isElementPresent("TLRWUnZpCde_id").clear();
					isElementPresent("TLRWUnZpCde_id").sendKeys("77054");
					isElementPresent("TLRWUnZpCde_id").sendKeys(Keys.TAB);
					logs.info("Entered ZipCode");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// Phone Number
					isElementPresent("TLRWUnPhone_id").clear();
					isElementPresent("TLRWUnPhone_id").sendKeys("9856320147");
					logs.info("Entered Phone Number");

					// --clicked on Create Route button
					WebElement CreateRoute = isElementPresent("TLRWCreateRoute_id");
					wait.until(ExpectedConditions.elementToBeClickable(CreateRoute));
					act.moveToElement(CreateRoute).build().perform();
					act.moveToElement(CreateRoute).click().perform();

					logs.info("Clicked on Create Route button of RTE form");

					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("success")));

					String SUccMsg = isElementPresent("TLRWSucc_id").getText();
					logs.info("Message is displayed==" + SUccMsg);
					logs.info("RTE Job is created successfully.");

					// --Get the RouteWorkID
					String inLine = SUccMsg;
					String[] lineSplits = inLine.split(" ");
					for (String Detail : lineSplits) {
						if (Detail.contains("RT")) {
							Detail.trim();
							System.out.println("RoutWorkID is==" + Detail);
							logs.info("RoutWorkID is==" + Detail);
							setData("RTECreation", 8, 1, Detail);
							logs.info("Stored RoutWorkID in excel");

						}
					}

					// --clicked on cancel button
					WebElement CancelRTE = isElementPresent("TLRWCancelRW_id");
					wait.until(ExpectedConditions.elementToBeClickable(CancelRTE));
					act.moveToElement(CancelRTE).click().perform();
					logs.info("Clicked on Cancel button of RTE form");

					break;
				} else {
					logs.info("Searched job is not exist");

				}
			}

		}

		logs.info("======================Create RTE with LOC Job Test End==================");
		msg.append("======================Create RTE with LOC Job Test End==================" + "\n");
	}

}

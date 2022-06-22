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

public class RTEJobCreation extends BaseInit {

	@Test
	public void rteJobCreation()
			throws InterruptedException, EncryptedDocumentException, InvalidFormatException, IOException {

		WebDriverWait wait = new WebDriverWait(driver, 30);
		Actions act = new Actions(driver);
		// JavascriptExecutor js = (JavascriptExecutor) driver;

		// --------
		logs.info("======================RTE Job Creation Test start==================");
		msg.append("======================RTE Job Creation Test start==================");

		// --Go to Tools tab
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_tools")));
		WebElement Tools = isElementPresent("Tools_id");
		act.moveToElement(Tools).click().perform();
		logs.info("Clicked on Tools");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"OpenCloseClass dropdown open\"]//ul")));

		// --Click on Import Route
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_ImportRoute")));
		isElementPresent("ImportRoute_id").click();
		logs.info("Clicked on Import Route");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		getScreenshot(driver, "ImportRoute");
		for (int i = 2; i < 8; i++) {
			// --Upload RTE job File
			ExpectedConditions.visibilityOfElementLocated(By.id("btnBrowse"));
			isElementPresent("IRBrowse_id").click();
			logs.info("Clicked on Browse button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			wait.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@ng-form=\"uploadfileForm\"]")));

			String FileName = getData("RTECreation", i, 0);
			logs.info("FileName==" + FileName);
			String Fpath = "C:\\Users\\rprajapati\\git\\RTE\\RTE\\src\\main\\resources\\" + FileName + ".xlsx";
			WebElement InFile = isElementPresent("IRSelectFile_id");
			InFile.sendKeys(Fpath);
			logs.info("Send file to input file");
			Thread.sleep(2000);
			getScreenshot(driver, "ImRSelectFile");

			// --Click on Upload btn
			isElementPresent("IRUpload_id").click();
			logs.info("Click on Upload button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			Thread.sleep(2000);

			// --CLick on Continue button
			WebElement Continue = isElementPresent("IRContinue_id");
			wait.until(ExpectedConditions.elementToBeClickable(Continue));
			act.moveToElement(Continue).build().perform();
			act.moveToElement(Continue).click().perform();

			logs.info("Click on Continue button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
			String SucMsg = isElementPresent("SuccessMsg_id").getText();
			logs.info("File is Uploaded==" + SucMsg);

			// --Get the RouteWorkID
			String inLine = SucMsg;
			String[] lineSplits = inLine.split("\\.");
			String[] lineDetails = lineSplits[1].split(" ");
			for (String Detail : lineDetails) {
				if (Detail.contains("RT")) {
					Detail.split(".");
					System.out.println("RoutWorkID is==" + Detail);
					logs.info("RoutWorkID is==" + Detail);
					setData("RTECreation", i, 1, Detail);
					logs.info("Stored RoutWorkID in excel");

				}
			}
		}

		logs.info("======================RTE Job Creation Test End==================");
		msg.append("======================RTE Job Creation Test End==================");

	}

}

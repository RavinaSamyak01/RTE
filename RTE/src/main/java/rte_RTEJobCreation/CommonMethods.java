package rte_RTEJobCreation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rte_BasePackage.BaseInit;

public class CommonMethods extends BaseInit {

	public void searchByPUID(String Case) throws IOException {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		Actions act = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// --Click on Search
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnGlobalSearch")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
		isElementPresent("TLGlobSearch_id").click();
		logs.info("Click on Search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
			logs.info("Search button click event is working");

		} catch (Exception SIssue) {
			logs.info("Search button click event is not working by click method");
			logs.error(SIssue);
			getScreenshot(driver, Case + "_SIssue_Error");
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
				wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
				WebElement GSearch = isElementPresent("TLGlobSearch_id");
				act.moveToElement(GSearch).build().perform();
				act.moveToElement(GSearch).click().perform();
				logs.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
				logs.info("Search button click event is working by action class");

			} catch (Exception SIssue2) {
				getScreenshot(driver, Case + "_SIssue2_Error");
				logs.info("Search button click event is working by Action class");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
				WebElement GSearch = isElementPresent("TLGlobSearch_id");
				act.moveToElement(GSearch).build().perform();
				wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
				js.executeScript("arguments[0].click();", GSearch);
				logs.info("Click on Search button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
				logs.info("Search button click event is working by JavaScript");
			}

		}
	}

	public String getTimeAsTZone(String timeZone) {

		System.out.println("ZoneID of is==" + timeZone);
		logs.info("ZoneID of is==" + timeZone);
		if (timeZone.equalsIgnoreCase("EDT")) {
			timeZone = "America/New_York";
		} else if (timeZone.equalsIgnoreCase("CDT")) {
			timeZone = "CST";
		} else if (timeZone.equalsIgnoreCase("PDT")) {
			timeZone = "PST";
		}

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		logs.info(dateFormat.format(date));
		String Time = dateFormat.format(date);
		System.out.println("Time==" + Time);
		return Time;

	}
	
	public String getExtraTimeAsTZone(String timeZone) {

		System.out.println("ZoneID of is==" + timeZone);
		logs.info("ZoneID of is==" + timeZone);
		if (timeZone.equalsIgnoreCase("EDT")) {
			timeZone = "America/New_York";
		} else if (timeZone.equalsIgnoreCase("CDT")) {
			timeZone = "CST";
		} else if (timeZone.equalsIgnoreCase("PDT")) {
			timeZone = "PST";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
		cal.add(Calendar.MINUTE, 1);
		logs.info(dateFormat.format(cal.getTime()));
		String Time = dateFormat.format(cal.getTime());
		System.out.println("New Time==" + Time);
		return Time;

	}
	
	public  String getDateAsTZone(String timeZone) {

		System.out.println("ZoneID of is==" + timeZone);
		logs.info("ZoneID of is==" + timeZone);
		if (timeZone.equalsIgnoreCase("EDT")) {
			timeZone = "America/New_York";
		} else if (timeZone.equalsIgnoreCase("CDT")) {
			timeZone = "CST";
		} else if (timeZone.equalsIgnoreCase("PDT")) {
			timeZone = "PST";
		}

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		logs.info(dateFormat.format(date));
		String Date = dateFormat.format(date);
		System.out.println("Date==" + Date);
		return Date;

	}


}

package rte_RTEJobCreation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rte_BasePackage.BaseInit;

public class RTEJobCreation extends BaseInit {

	public void rteJobCreation() {

		WebDriverWait wait = new WebDriverWait(driver, 50);
		Actions act = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// --------
		logs.info("======================RTE Job Creation Test start==================");
		msg.append("======================RTE Job Creation Test start==================");

		// --Go to Tools tab
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_tools")));
		WebElement Tools = driver.findElement(By.id("a_tools"));
		act.moveToElement(Tools).click().perform();
		logs.info("Clicked on Tools");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"OpenCloseClass dropdown open\"]//ul")));

		// --Click on Import Route
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("a_ImportRoute")));
		driver.findElement(By.id("a_ImportRoute")).click();
		logs.info("Clicked on Import Route");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

	}

}

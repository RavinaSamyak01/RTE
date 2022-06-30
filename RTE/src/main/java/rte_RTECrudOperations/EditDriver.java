package rte_RTECrudOperations;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import rte_BasePackage.BaseInit;

public class EditDriver extends BaseInit {

	public void rteEditDriver() throws IOException {

		WebDriverWait wait = new WebDriverWait(driver, 50);
		Actions act = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		logs.info("=====RTE Edit Driver Test Start=====");
		msg.append("=====RTE Edit Driver Test Start=====" + "\n");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkedit")));
		isElementPresent("PDEdit_id").click();
		logs.info("Clicked on Edit button of Driver");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
		wait.until(
				ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"modal-dialog modal-md\"]")));
		getScreenshot(driver, "DriverSearch");

		// --Search driver by AGent Key
		wait.until(ExpectedConditions.elementToBeClickable(By.id("txtAgentKey")));
		isElementPresent("PDAgentKey_id").clear();
		isElementPresent("PDAgentKey_id").sendKeys("AUTOMATION");
		logs.info("Enter Agent Key");

		// --Click on Search
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSearch")));
		WebElement SearchBTN = isElementPresent("PDSearch_id");
		js.executeScript("arguments[0].click();", SearchBTN);
		logs.info("Clicked on search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Get the List of Agent
		List<WebElement> AgentsKey = driver
				.findElements(By.xpath("//*[@id=\"scrollboxEditAgent\"]//label[contains(@ng-bind,'AgentKey')]"));

		int TotalAgentKeys = AgentsKey.size();
		logs.info("Total Agent==" + TotalAgentKeys);

		for (int agentkey = 0; agentkey < TotalAgentKeys; agentkey++) {
			WebElement AgKey = AgentsKey.get(agentkey);
			act.moveToElement(AgKey).build().perform();
			String AgentKeyValue = AgentsKey.get(agentkey).getText();
			if (AgentKeyValue.equalsIgnoreCase("AUTOMATION")) {
				logs.info("Searched Agent is exist");

			} else {
				logs.info("There is no Agent with search parameter");

			}
		}

		// --Search by checkboxes
		List<WebElement> SCheckboxes = driver
				.findElements(By.xpath("//*[@class=\"row form-group\"]//input[@type=\"checkbox\"]"));

		int TotalChbs = SCheckboxes.size();
		logs.info("Total Checkboxes==" + TotalChbs);

		for (int Schb = 0; Schb < TotalChbs; Schb++) {

			List<WebElement> CheckBXSName = driver
					.findElements(By.xpath("//*[@class=\"row form-group\"]//label[contains(@for,\"chkb\")]"));

			String ChbName = null;
			for (int CHBName = Schb; CHBName < CheckBXSName.size();) {

				ChbName = CheckBXSName.get(CHBName).getText();
				logs.info("Name of the checkbox is== " + ChbName);
				break;
			}

			WebElement SCheckbox = SCheckboxes.get(Schb);
			js.executeScript("arguments[0].click();", SCheckbox);
			logs.info("Clicked on " + ChbName);

			SearchBTN = isElementPresent("PDSearch_id");
			js.executeScript("arguments[0].click();", SearchBTN);
			logs.info("Clicked on search button");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationPopup")));
				String ValMsg = isElementPresent("PDNoRecord_id").getText();
				logs.info("Message is Displayed==" + ValMsg);

			} catch (Exception NoRecord) {
				logs.info("Agent(s) is/are available with the search parameters");

			}

			// --Uncheck the checkbox
			SCheckbox = SCheckboxes.get(Schb);
			js.executeScript("arguments[0].click();", SCheckbox);
			logs.info("Clicked on " + ChbName);

		}

		// --Search driver by AgentID
		wait.until(ExpectedConditions.elementToBeClickable(By.id("txtCourierId")));
		isElementPresent("PDCourierID_id").clear();
		isElementPresent("PDCourierID_id").sendKeys("34769");
		logs.info("Enter CourierID");

		// --Click on Search
		SearchBTN = isElementPresent("PDSearch_id");
		js.executeScript("arguments[0].click();", SearchBTN);
		logs.info("Clicked on search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		// --Get the List of Agent
		List<WebElement> Agents = driver
				.findElements(By.xpath("//*[@id=\"scrollboxEditAgent\"]//a[contains(@id,'AgentId')]"));

		int TotalAgent = Agents.size();
		logs.info("Total Agent==" + TotalAgent);
		String AgentValue = null;
		for (int agent = 0; agent < TotalAgent; agent++) {
			WebElement AgentID = Agents.get(agent);
			String AgentIDV = Agents.get(agent).getText();
			act.moveToElement(AgentID).build().perform();
			AgentValue = AgentIDV;
			if (AgentIDV.equalsIgnoreCase("34769")) {
				logs.info("Searched Agent is exist");

				// --Select the Agent
				act.moveToElement(AgentID).build().perform();
				act.moveToElement(AgentID).click().perform();
				logs.info("Select the AgentID");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlkedit")));
				AgentValue = AgentIDV;

			} else {
				logs.info("There is no Agent with search parameter");

			}

		}

		// --Compare the Searched Agent and selected Agent
		String AgeValue = isElementPresent("PDAgentValue_id").getText();
		logs.info("Selected agent is==" + AgeValue);

		if (AgeValue.equalsIgnoreCase(AgentValue)) {
			logs.info("Selected Agent is displayed in Driver section");

		} else {
			logs.info("Selected Agent is not displayed in Driver section");

		}

		// --Get the vehicle details
		List<WebElement> DVehicles = driver.findElements(
				By.xpath("//*[contains(@class,'form-group')]//a[contains(@ng-repeat,'vehicle in task')]"));

		int TotalVehicle = DVehicles.size();
		logs.info("Total Vehicles==" + TotalVehicle);

		for (int veh = 0; veh < TotalVehicle; veh++) {
			String VehName = DVehicles.get(veh).findElement(By.tagName("i")).getAttribute("title");
			logs.info("Name of the Vehicle==" + VehName);

			String Vehicleclass = DVehicles.get(veh).findElement(By.tagName("i")).getAttribute("class");

			if (Vehicleclass.contains("Red")) {
				logs.info(VehName + " is selected");

			}
		}

		// --Selection of driver

		// --Select the driver name
		Select DriverName = new Select(isElementPresent("PDNameDrp_id"));
		DriverName.selectByIndex(1);
		logs.info("Driver is selected");

		// --Enter contact Number
		isElementPresent("PDContact_id").clear();
		isElementPresent("PDContact_id").sendKeys("1234567899");
		logs.info("Enter driver contact number");

		// --Select Toll Concession
		Select TollCOn = new Select(isElementPresent("PDTollCon_id"));
		TollCOn.selectByIndex(1);
		logs.info("Toll Concession is selected");

		logs.info("=====RTE Edit Driver Test End=====");
		msg.append("=====RTE Edit Driver Test End=====" + "\n");

	}

}

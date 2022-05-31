package rte_RTEJobCreation;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import rte_BasePackage.BaseInit;

public class RTEOneToManyOrderProcess extends BaseInit {

	@Test
	public void rteOneToManyOrderProcess() throws EncryptedDocumentException, InvalidFormatException, IOException {

		WebDriverWait wait = new WebDriverWait(driver, 50);
		Actions act = new Actions(driver);

		logs.info("======================RTE One To Many Order Processing Test Start==================");
		msg.append("======================RTE One To Many Order Processing Test Start==================" + "\n");

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
		String PickUpID = getData("OneToMany", 1, 2);
		isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
		logs.info("PickUpID==" + PickUpID);
		logs.info("Entered PickUpID in basic search");

		// --Click on Search
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
		isElementPresent("TLGlobSearch_id").click();
		logs.info("Click on Search button");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
			logs.info("Data is exist with search parameter");
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

				// --Click on Acknowledge button
				wait.until(ExpectedConditions.elementToBeClickable(By.id("GreyTick")));
				isElementPresent("TLAcknoldge_id").click();
				logs.info("Clicked on Acknowledge button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {
					WebElement PickUPSection = isElementPresent("TLAlertstages_id");
					wait.until(ExpectedConditions.visibilityOf(PickUPSection));
					getScreenshot(driver, "JobEditor_RDYFORDSP");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);

					// --Click on SendPuAlert button
					wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
					isElementPresent("TLSendPUAl_id").click();
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
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
						PickUpID = getData("OneToMany", 1, 2);
						isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
						logs.info("Entered PickUpID in basic search");

						// --Click on Search
						wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
						isElementPresent("TLGlobSearch_id").click();
						logs.info("Click on Search button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						try {

							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
							logs.info("Data is exist with search parameter");
							getScreenshot(driver, "JobEditor_PUDRVCNF");

							// --Go to Job Status Tab
							wait.until(ExpectedConditions.elementToBeClickable(By.id("idJobOverview")));
							isElementPresent("TLJobSTatus_id").click();
							logs.info("Clicked on Job Status Tab");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Job Status
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);

							// --Click on Confirm PU Alert
							isElementPresent("TLSendPUAl_id").click();
							logs.info("Clicked on Confirm PU Alert");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
								String ValMsg = isElementPresent("TLAlValidation_id").getText();
								logs.info("Validation is displayed==" + ValMsg);

								// --Enter SpokeWith
								wait.until(ExpectedConditions.elementToBeClickable(By.id("txtSpokeWith")));
								isElementPresent("TLSpokeWith_id").sendKeys("RV");
								logs.info("Entered Spoke With");

								// --Click on Confirm PU Alert
								isElementPresent("TLSendPUAl_id").click();
								logs.info("Clicked on Confirm PU Alert");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							} catch (Exception NoVal) {
								logs.info("Spoke With validation is not displayed");

							}

							// --PICKUP@STOP 1 OF 2 stage
							try {
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("routetable")));
								getScreenshot(driver, "JobEditor_PickUP");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);
								// --Click on ConfirmPU button
								wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
								isElementPresent("TLCOnfPU_id").click();
								logs.info("Clicked on Confirm PU button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.id("idValidationforMain")));
									String ValMsg = isElementPresent("TLAlValidation_id").getText();
									logs.info("Validation is displayed==" + ValMsg);

									// --Stored list of pickup
									List<WebElement> PickupPoints = driver.findElements(By
											.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
									int TotalPickup = PickupPoints.size();
									logs.info("Total Pickup points is/are==" + TotalPickup);

									for (int pu = 0; pu < TotalPickup; pu++) {

										WebElement ZoneID = PickupPoints.get(pu)
												.findElement(By.xpath("//td[@ng-bind=\"shipmentdtls.ActpuTz\"]"));
										String PUDate = "txtActpuDate_" + pu;
										String PUTime = "txtActPuTime_" + pu;

										// --Get ZoneID
										String ZOneID = ZoneID.getText();
										logs.info("ZoneID of is==" + ZOneID);
										if (ZOneID.equalsIgnoreCase("EDT")) {
											ZOneID = "America/New_York";
										} else if (ZOneID.equalsIgnoreCase("CDT")) {
											ZOneID = "CST";
										}
										// --PickUp Date
										WebElement PickUpDate = driver.findElement(By.id(PUDate));
										PickUpDate.clear();
										Date date = new Date();
										DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logs.info(dateFormat.format(date));
										PickUpDate.sendKeys(dateFormat.format(date));
										PickUpDate.sendKeys(Keys.TAB);
										logs.info("Entered Actual Pickup Date");

										// --Enter Act.PickUp Time
										WebElement PickUpTime = driver.findElement(By.id(PUTime));
										PickUpTime.clear();
										date = new Date();
										dateFormat = new SimpleDateFormat("HH:mm");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logs.info(dateFormat.format(date));
										wait.until(ExpectedConditions.elementToBeClickable(By.id(PUTime)));
										PickUpTime.sendKeys(dateFormat.format(date));
										logs.info("Entered Actual Pickup Time");

									}

									// --Click on ConfirmPU button
									isElementPresent("TLCOnfPU_id").click();
									logs.info("Clicked on Confirm PU button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								} catch (Exception NoVal) {
									logs.info("Validation for Act.Pickup Time is not displayed");

								}

								// --DELIVER@STOP 2 OF 2 stage
								try {
									wait.until(
											ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("routetable")));
									getScreenshot(driver, "JobEditor_Deliver");
									jobStatus = isElementPresent("TLStageLable_id").getText();
									logs.info("Job status is==" + jobStatus);
									// --Click on ConfirmPU button
									wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
									isElementPresent("TLConfDEL_id").click();
									logs.info("Clicked on Confirm DEL button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.id("idValidationforMain")));
										String ValMsg = isElementPresent("TLAlValidation_id").getText();
										logs.info("Validation is displayed==" + ValMsg);

										// --Stored list of pickup
										List<WebElement> DelPoints = driver.findElements(By.xpath(
												"//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
										int TotalDel = DelPoints.size();
										logs.info("Total Delivery points is/are==" + TotalDel);

										for (int Del = 0; Del < TotalDel; Del++) {

											WebElement ZoneID = DelPoints.get(Del)
													.findElement(By.xpath("//td[@ng-bind=\"shipmentdtls.ActdlTz\"]"));
											String DeliveryDate = "txtActdlDate_" + Del;
											String DeliveryTime = "txtActDlTime_" + Del;

											// --Get ZoneID
											String ZOneID = ZoneID.getText();
											logs.info("ZoneID of is==" + ZOneID);
											if (ZOneID.equalsIgnoreCase("EDT")) {
												ZOneID = "America/New_York";
											} else if (ZOneID.equalsIgnoreCase("CDT")) {
												ZOneID = "CST";
											}

											// --Delivery Date
											WebElement DelDate = driver.findElement(By.id(DeliveryDate));
											DelDate.clear();
											Date date = new Date();
											DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
											dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
											logs.info(dateFormat.format(date));
											DelDate.sendKeys(dateFormat.format(date));
											DelDate.sendKeys(Keys.TAB);
											logs.info("Entered Actual Delivery Date");

											// --Enter Act.DEL Time
											WebElement DelTime = driver.findElement(By.id(DeliveryTime));
											DelTime.clear();
											date = new Date();
											dateFormat = new SimpleDateFormat("HH:mm");
											dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
											logs.info(dateFormat.format(date));
											wait.until(ExpectedConditions.elementToBeClickable(By.id(DeliveryTime)));
											DelTime.sendKeys(dateFormat.format(date));
											logs.info("Entered Actual Delivery Time");

											// --Enter Signature
											WebElement Sign = DelPoints.get(Del).findElement(By.id("txtsign"));
											wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
											Sign.sendKeys("RV");
											logs.info("Entered Signature");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											// --Click on Confirm Del button
											isElementPresent("TLConfDEL_id").click();
											logs.info("Clicked on Confirm DEL button");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											// Rebind the list
											wait.until(
													ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
											jobStatus = isElementPresent("TLStageLable_id").getText();
											logs.info("Job status is==" + jobStatus);
											DelPoints = driver.findElements(By.xpath(
													"//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
										}

									} catch (Exception NoValSign) {
										logs.info("Validation for Act.Del Time and Signature is not displayed");

									}

									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.id("idValidationforMain")));
										String ValMsg = isElementPresent("TLAlValidation_id").getText();
										logs.info("Validation is displayed==" + ValMsg);

										List<WebElement> DelPoints = driver.findElements(By.xpath(
												"//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
										int TotalDel = DelPoints.size();
										logs.info("Total Delivery points is/are==" + TotalDel);

										for (int Del = 0; Del < TotalDel; Del++) {

											System.out.println("value of del==" + Del);
											if (jobStatus.contains("DELIVER@STOP 3 OF")) {
												Del = 1;
											} else if (jobStatus.contains("DELIVER@STOP 4 OF")) {
												Del = 2;

											} else if (jobStatus.contains("DELIVER@STOP 5 OF")) {
												Del = 3;

											} else if (jobStatus.contains("DELIVER@STOP 6 OF")) {
												Del = 4;

											} else if (jobStatus.contains("DELIVER@STOP 7 OF")) {
												Del = 5;

											}
											System.out.println("value of del==" + Del);

											WebElement ZoneID = DelPoints.get(Del)
													.findElement(By.xpath("//td[@ng-bind=\"shipmentdtls.ActdlTz\"]"));
											String DeliveryDate = "txtActdlDate_" + Del;
											String DeliveryTime = "txtActDlTime_" + Del;

											// --Get ZoneID
											String ZOneID = ZoneID.getText();
											logs.info("ZoneID of is==" + ZOneID);
											if (ZOneID.equalsIgnoreCase("EDT")) {
												ZOneID = "America/New_York";
											} else if (ZOneID.equalsIgnoreCase("CDT")) {
												ZOneID = "CST";
											}

											// --Delivery Date
											WebElement DelDate = driver.findElement(By.id(DeliveryDate));
											DelDate.clear();
											Date date = new Date();
											DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
											dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
											logs.info(dateFormat.format(date));
											DelDate.sendKeys(dateFormat.format(date));
											DelDate.sendKeys(Keys.TAB);
											logs.info("Entered Actual Delivery Date");

											// --Enter Act.DEL Time
											WebElement DelTime = driver.findElement(By.id(DeliveryTime));
											DelTime.clear();
											date = new Date();
											dateFormat = new SimpleDateFormat("HH:mm");
											dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
											Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
											cal.add(Calendar.MINUTE, 1);
											logs.info(dateFormat.format(cal.getTime()));
											wait.until(ExpectedConditions.elementToBeClickable(By.id(DeliveryTime)));
											DelTime.sendKeys(dateFormat.format(cal.getTime()));
											logs.info("Entered Actual Delivery Time");

											// --Click on Confirm Del button
											isElementPresent("TLConfDEL_id").click();
											logs.info("Clicked on Confirm DEL button");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));
										}
									} catch (Exception ActTimeGDelTime) {
										logs.info("Validation is not displayed="
												+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

									}

									try {
										wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
										PickUpID = getData("OneToMany", 1, 2);
										isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
										logs.info("Entered PickUpID in basic search");

										// --Click on Search
										wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
										isElementPresent("TLGlobSearch_id").click();
										logs.info("Click on Search button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										try {
											WebElement NoData2 = isElementPresent("NoData_className");
											wait.until(ExpectedConditions.visibilityOf(NoData2));
											if (NoData2.isDisplayed()) {
												logs.info("There is no Data with Search parameter");

											}

										} catch (Exception NoData2) {
											logs.info("Data is exist with search parameter");
											wait.until(ExpectedConditions
													.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
											getScreenshot(driver, "JobEditor_Delivered");
											jobStatus = isElementPresent("TLStageLable_id").getText();
											logs.info("Job status is==" + jobStatus);

											if (jobStatus.contains("DELIVERED")) {
												logs.info("Job is Delivered successfully");

											} else {
												logs.info("Job is not Delivered successfully");
												jobStatus = isElementPresent("TLStageLable_id").getText();
												logs.info("Job status is==" + jobStatus);

											}

										}

										//

									} catch (Exception Deliverstage) {
										logs.info("job is not moved to delivered stage");
										jobStatus = isElementPresent("TLStageLable_id").getText();
										logs.info("Job status is==" + jobStatus);
									}

								} catch (Exception Deliver) {
									logs.info("Stage is not Deliver");
									jobStatus = isElementPresent("TLStageLable_id").getText();
									logs.info("Job status is==" + jobStatus);
								}
							} catch (Exception PickUp) {
								logs.info("Stage is not PickUP");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);
							}

						} catch (Exception NoDataEX) {

							WebElement NoData1 = isElementPresent("NoData_className");
							wait.until(ExpectedConditions.visibilityOf(NoData1));
							if (NoData1.isDisplayed()) {
								logs.info("There is no Data with Search parameter");

							}
						}

					} catch (Exception Somethingw) {
						logs.info("Job is not moved to PU DRV CONF stage");

					}
				} catch (Exception NoPickupS) {
					logs.info("There is no PickUp Driver section");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);

				}
			} else if (jobStatus.contains("RDY FOR DSP")) {
				WebElement PickUPSection = isElementPresent("TLAlertstages_id");
				wait.until(ExpectedConditions.visibilityOf(PickUPSection));
				getScreenshot(driver, "JobEditor_RDYFORDSP");
				jobStatus = isElementPresent("TLStageLable_id").getText();
				logs.info("Job status is==" + jobStatus);

				// --Click on SendPuAlert button
				wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
				isElementPresent("TLSendPUAl_id").click();
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
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
					PickUpID = getData("OneToMany", 1, 2);
					isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
					logs.info("Entered PickUpID in basic search");

					// --Click on Search
					wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
					isElementPresent("TLGlobSearch_id").click();
					logs.info("Click on Search button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {

						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
						logs.info("Data is exist with search parameter");
						getScreenshot(driver, "JobEditor_PUDRVCNF");

						// --Go to Job Status Tab
						wait.until(ExpectedConditions.elementToBeClickable(By.id("idJobOverview")));
						isElementPresent("TLJobSTatus_id").click();
						logs.info("Clicked on Job Status Tab");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Job Status
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);

						// --Click on Confirm PU Alert
						isElementPresent("TLSendPUAl_id").click();
						logs.info("Clicked on Confirm PU Alert");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
							String ValMsg = isElementPresent("TLAlValidation_id").getText();
							logs.info("Validation is displayed==" + ValMsg);

							// --Enter SpokeWith
							wait.until(ExpectedConditions.elementToBeClickable(By.id("txtSpokeWith")));
							isElementPresent("TLSpokeWith_id").sendKeys("RV");
							logs.info("Entered Spoke With");

							// --Click on Confirm PU Alert
							isElementPresent("TLSendPUAl_id").click();
							logs.info("Clicked on Confirm PU Alert");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						} catch (Exception NoVal) {
							logs.info("Spoke With validation is not displayed");

						}

						// --PICKUP@STOP 1 OF 2 stage
						try {
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("routetable")));
							getScreenshot(driver, "JobEditor_PickUP");
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);
							// --Click on ConfirmPU button
							wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
							isElementPresent("TLCOnfPU_id").click();
							logs.info("Clicked on Confirm PU button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
								String ValMsg = isElementPresent("TLAlValidation_id").getText();
								logs.info("Validation is displayed==" + ValMsg);

								// --Stored list of pickup
								List<WebElement> PickupPoints = driver.findElements(
										By.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
								int TotalPickup = PickupPoints.size();
								logs.info("Total Pickup points is/are==" + TotalPickup);

								for (int pu = 0; pu < TotalPickup; pu++) {

									WebElement ZoneID = PickupPoints.get(pu)
											.findElement(By.xpath("//td[@ng-bind=\"shipmentdtls.ActpuTz\"]"));
									String PUDate = "txtActpuDate_" + pu;
									String PUTime = "txtActPuTime_" + pu;

									// --Get ZoneID
									String ZOneID = ZoneID.getText();
									logs.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									}
									// --PickUp Date
									WebElement PickUpDate = driver.findElement(By.id(PUDate));
									PickUpDate.clear();
									Date date = new Date();
									DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logs.info(dateFormat.format(date));
									PickUpDate.sendKeys(dateFormat.format(date));
									PickUpDate.sendKeys(Keys.TAB);
									logs.info("Entered Actual Pickup Date");

									// --Enter Act.PickUp Time
									WebElement PickUpTime = driver.findElement(By.id(PUTime));
									PickUpTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logs.info(dateFormat.format(date));
									wait.until(ExpectedConditions.elementToBeClickable(By.id(PUTime)));
									PickUpTime.sendKeys(dateFormat.format(date));
									logs.info("Entered Actual Pickup Time");

								}

								// --Click on ConfirmPU button
								isElementPresent("TLCOnfPU_id").click();
								logs.info("Clicked on Confirm PU button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							} catch (Exception NoVal) {
								logs.info("Validation for Act.Pickup Time is not displayed");

							}

							// --DELIVER@STOP 2 OF 2 stage
							try {
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("routetable")));
								getScreenshot(driver, "JobEditor_Deliver");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);
								// --Click on ConfirmPU button
								wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
								isElementPresent("TLConfDEL_id").click();
								logs.info("Clicked on Confirm DEL button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.id("idValidationforMain")));
									String ValMsg = isElementPresent("TLAlValidation_id").getText();
									logs.info("Validation is displayed==" + ValMsg);

									// --Stored list of pickup
									List<WebElement> DelPoints = driver.findElements(By
											.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
									int TotalDel = DelPoints.size();
									logs.info("Total Delivery points is/are==" + TotalDel);

									for (int Del = 0; Del < TotalDel; Del++) {

										WebElement ZoneID = DelPoints.get(Del)
												.findElement(By.xpath("//td[@ng-bind=\"shipmentdtls.ActdlTz\"]"));
										String DeliveryDate = "txtActdlDate_" + Del;
										String DeliveryTime = "txtActDlTime_" + Del;

										// --Get ZoneID
										String ZOneID = ZoneID.getText();
										logs.info("ZoneID of is==" + ZOneID);
										if (ZOneID.equalsIgnoreCase("EDT")) {
											ZOneID = "America/New_York";
										} else if (ZOneID.equalsIgnoreCase("CDT")) {
											ZOneID = "CST";
										}

										// --Delivery Date
										WebElement DelDate = driver.findElement(By.id(DeliveryDate));
										DelDate.clear();
										Date date = new Date();
										DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logs.info(dateFormat.format(date));
										DelDate.sendKeys(dateFormat.format(date));
										DelDate.sendKeys(Keys.TAB);
										logs.info("Entered Actual Delivery Date");

										// --Enter Act.DEL Time
										WebElement DelTime = driver.findElement(By.id(DeliveryTime));
										DelTime.clear();
										date = new Date();
										dateFormat = new SimpleDateFormat("HH:mm");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logs.info(dateFormat.format(date));
										wait.until(ExpectedConditions.elementToBeClickable(By.id(DeliveryTime)));
										DelTime.sendKeys(dateFormat.format(date));
										logs.info("Entered Actual Delivery Time");

										// --Enter Signature
										WebElement Sign = DelPoints.get(Del).findElement(By.id("txtsign"));
										wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
										Sign.sendKeys("RV");
										logs.info("Entered Signature");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										// --Click on Confirm Del button
										isElementPresent("TLConfDEL_id").click();
										logs.info("Clicked on Confirm DEL button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										// Rebind the list
										wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
										jobStatus = isElementPresent("TLStageLable_id").getText();
										logs.info("Job status is==" + jobStatus);
										DelPoints = driver.findElements(By.xpath(
												"//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
									}

								} catch (Exception NoValSign) {
									logs.info("Validation for Act.Del Time and Signature is not displayed");

								}

								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.id("idValidationforMain")));
									String ValMsg = isElementPresent("TLAlValidation_id").getText();
									logs.info("Validation is displayed==" + ValMsg);

									List<WebElement> DelPoints = driver.findElements(By
											.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
									int TotalDel = DelPoints.size();
									logs.info("Total Delivery points is/are==" + TotalDel);

									for (int Del = 0; Del < TotalDel; Del++) {

										System.out.println("value of del==" + Del);
										if (jobStatus.contains("DELIVER@STOP 3 OF")) {
											Del = 1;
										} else if (jobStatus.contains("DELIVER@STOP 4 OF")) {
											Del = 2;

										} else if (jobStatus.contains("DELIVER@STOP 5 OF")) {
											Del = 3;

										} else if (jobStatus.contains("DELIVER@STOP 6 OF")) {
											Del = 4;

										} else if (jobStatus.contains("DELIVER@STOP 7 OF")) {
											Del = 5;

										}
										System.out.println("value of del==" + Del);

										WebElement ZoneID = DelPoints.get(Del)
												.findElement(By.xpath("//td[@ng-bind=\"shipmentdtls.ActdlTz\"]"));
										String DeliveryDate = "txtActdlDate_" + Del;
										String DeliveryTime = "txtActDlTime_" + Del;

										// --Get ZoneID
										String ZOneID = ZoneID.getText();
										logs.info("ZoneID of is==" + ZOneID);
										if (ZOneID.equalsIgnoreCase("EDT")) {
											ZOneID = "America/New_York";
										} else if (ZOneID.equalsIgnoreCase("CDT")) {
											ZOneID = "CST";
										}

										// --Delivery Date
										WebElement DelDate = driver.findElement(By.id(DeliveryDate));
										DelDate.clear();
										Date date = new Date();
										DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logs.info(dateFormat.format(date));
										DelDate.sendKeys(dateFormat.format(date));
										DelDate.sendKeys(Keys.TAB);
										logs.info("Entered Actual Delivery Date");

										// --Enter Act.DEL Time
										WebElement DelTime = driver.findElement(By.id(DeliveryTime));
										DelTime.clear();
										date = new Date();
										dateFormat = new SimpleDateFormat("HH:mm");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
										cal.add(Calendar.MINUTE, 1);
										logs.info(dateFormat.format(cal.getTime()));
										wait.until(ExpectedConditions.elementToBeClickable(By.id(DeliveryTime)));
										DelTime.sendKeys(dateFormat.format(cal.getTime()));
										logs.info("Entered Actual Delivery Time");

										// --Click on Confirm Del button
										isElementPresent("TLConfDEL_id").click();
										logs.info("Clicked on Confirm DEL button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
									}
								} catch (Exception ActTimeGDelTime) {
									logs.info("Validation is not displayed="
											+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

								}

								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
									PickUpID = getData("OneToMany", 1, 2);
									isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
									logs.info("Entered PickUpID in basic search");

									// --Click on Search
									wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
									isElementPresent("TLGlobSearch_id").click();
									logs.info("Click on Search button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									try {
										WebElement NoData2 = isElementPresent("NoData_className");
										wait.until(ExpectedConditions.visibilityOf(NoData2));
										if (NoData2.isDisplayed()) {
											logs.info("There is no Data with Search parameter");

										}

									} catch (Exception NoData2) {
										logs.info("Data is exist with search parameter");
										wait.until(ExpectedConditions
												.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
										getScreenshot(driver, "JobEditor_Delivered");
										jobStatus = isElementPresent("TLStageLable_id").getText();
										logs.info("Job status is==" + jobStatus);

										if (jobStatus.contains("DELIVERED")) {
											logs.info("Job is Delivered successfully");

										} else {
											logs.info("Job is not Delivered successfully");
											jobStatus = isElementPresent("TLStageLable_id").getText();
											logs.info("Job status is==" + jobStatus);

										}

									}

									//

								} catch (Exception Deliverstage) {
									logs.info("job is not moved to delivered stage");
									jobStatus = isElementPresent("TLStageLable_id").getText();
									logs.info("Job status is==" + jobStatus);
								}

							} catch (Exception Deliver) {
								logs.info("Stage is not Deliver");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);
							}
						} catch (Exception PickUp) {
							logs.info("Stage is not PickUP");
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);
						}

					} catch (Exception NoDataEX) {

						WebElement NoData1 = isElementPresent("NoData_className");
						wait.until(ExpectedConditions.visibilityOf(NoData1));
						if (NoData1.isDisplayed()) {
							logs.info("There is no Data with Search parameter");

						}
					}

				} catch (Exception Somethingw) {
					logs.info("Job is not moved to PU DRV CONF stage");

				}
			} else if (jobStatus.contains("PU DRV CONF")) {

				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
				logs.info("Data is exist with search parameter");
				getScreenshot(driver, "JobEditor_PUDRVCNF");

				// --Go to Job Status Tab
				wait.until(ExpectedConditions.elementToBeClickable(By.id("idJobOverview")));
				isElementPresent("TLJobSTatus_id").click();
				logs.info("Clicked on Job Status Tab");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				// --Job Status
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
				jobStatus = isElementPresent("TLStageLable_id").getText();
				logs.info("Job status is==" + jobStatus);

				// --Click on Confirm PU Alert
				isElementPresent("TLSendPUAl_id").click();
				logs.info("Clicked on Confirm PU Alert");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
					String ValMsg = isElementPresent("TLAlValidation_id").getText();
					logs.info("Validation is displayed==" + ValMsg);

					// --Enter SpokeWith
					wait.until(ExpectedConditions.elementToBeClickable(By.id("txtSpokeWith")));
					isElementPresent("TLSpokeWith_id").sendKeys("RV");
					logs.info("Entered Spoke With");

					// --Click on Confirm PU Alert
					isElementPresent("TLSendPUAl_id").click();
					logs.info("Clicked on Confirm PU Alert");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				} catch (Exception NoVal) {
					logs.info("Spoke With validation is not displayed");

				}

				// --PICKUP@STOP 1 OF 2 stage
				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("routetable")));
					getScreenshot(driver, "JobEditor_PickUP");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);
					// --Click on ConfirmPU button
					wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
					isElementPresent("TLCOnfPU_id").click();
					logs.info("Clicked on Confirm PU button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
						String ValMsg = isElementPresent("TLAlValidation_id").getText();
						logs.info("Validation is displayed==" + ValMsg);

						// --Stored list of pickup
						List<WebElement> PickupPoints = driver.findElements(
								By.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
						int TotalPickup = PickupPoints.size();
						logs.info("Total Pickup points is/are==" + TotalPickup);

						for (int pu = 0; pu < TotalPickup; pu++) {

							WebElement ZoneID = PickupPoints.get(pu)
									.findElement(By.xpath("//td[@ng-bind=\"shipmentdtls.ActpuTz\"]"));
							String PUDate = "txtActpuDate_" + pu;
							String PUTime = "txtActPuTime_" + pu;

							// --Get ZoneID
							String ZOneID = ZoneID.getText();
							logs.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}
							// --PickUp Date
							WebElement PickUpDate = driver.findElement(By.id(PUDate));
							PickUpDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							PickUpDate.sendKeys(dateFormat.format(date));
							PickUpDate.sendKeys(Keys.TAB);
							logs.info("Entered Actual Pickup Date");

							// --Enter Act.PickUp Time
							WebElement PickUpTime = driver.findElement(By.id(PUTime));
							PickUpTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							wait.until(ExpectedConditions.elementToBeClickable(By.id(PUTime)));
							PickUpTime.sendKeys(dateFormat.format(date));
							logs.info("Entered Actual Pickup Time");

						}

						// --Click on ConfirmPU button
						isElementPresent("TLCOnfPU_id").click();
						logs.info("Clicked on Confirm PU button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					} catch (Exception NoVal) {
						logs.info("Validation for Act.Pickup Time is not displayed");

					}

					// --DELIVER@STOP 2 OF 2 stage
					try {
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("routetable")));
						getScreenshot(driver, "JobEditor_Deliver");
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);
						// --Click on ConfirmPU button
						wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
						isElementPresent("TLConfDEL_id").click();
						logs.info("Clicked on Confirm DEL button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
							String ValMsg = isElementPresent("TLAlValidation_id").getText();
							logs.info("Validation is displayed==" + ValMsg);

							// --Stored list of pickup
							List<WebElement> DelPoints = driver.findElements(
									By.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
							int TotalDel = DelPoints.size();
							logs.info("Total Delivery points is/are==" + TotalDel);

							for (int Del = 0; Del < TotalDel; Del++) {

								WebElement ZoneID = DelPoints.get(Del)
										.findElement(By.xpath("//td[@ng-bind=\"shipmentdtls.ActdlTz\"]"));
								String DeliveryDate = "txtActdlDate_" + Del;
								String DeliveryTime = "txtActDlTime_" + Del;

								// --Get ZoneID
								String ZOneID = ZoneID.getText();
								logs.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}

								// --Delivery Date
								WebElement DelDate = driver.findElement(By.id(DeliveryDate));
								DelDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logs.info(dateFormat.format(date));
								DelDate.sendKeys(dateFormat.format(date));
								DelDate.sendKeys(Keys.TAB);
								logs.info("Entered Actual Delivery Date");

								// --Enter Act.DEL Time
								WebElement DelTime = driver.findElement(By.id(DeliveryTime));
								DelTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logs.info(dateFormat.format(date));
								wait.until(ExpectedConditions.elementToBeClickable(By.id(DeliveryTime)));
								DelTime.sendKeys(dateFormat.format(date));
								logs.info("Entered Actual Delivery Time");

								// --Enter Signature
								WebElement Sign = DelPoints.get(Del).findElement(By.id("txtsign"));
								wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
								Sign.sendKeys("RV");
								logs.info("Entered Signature");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Click on Confirm Del button
								isElementPresent("TLConfDEL_id").click();
								logs.info("Clicked on Confirm DEL button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// Rebind the list
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);
								DelPoints = driver.findElements(
										By.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
							}

						} catch (Exception NoValSign) {
							logs.info("Validation for Act.Del Time and Signature is not displayed");

						}

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
							String ValMsg = isElementPresent("TLAlValidation_id").getText();
							logs.info("Validation is displayed==" + ValMsg);

							List<WebElement> DelPoints = driver.findElements(
									By.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
							int TotalDel = DelPoints.size();
							logs.info("Total Delivery points is/are==" + TotalDel);

							for (int Del = 0; Del < TotalDel; Del++) {

								System.out.println("value of del==" + Del);
								if (jobStatus.contains("DELIVER@STOP 3 OF")) {
									Del = 1;
								} else if (jobStatus.contains("DELIVER@STOP 4 OF")) {
									Del = 2;

								} else if (jobStatus.contains("DELIVER@STOP 5 OF")) {
									Del = 3;

								} else if (jobStatus.contains("DELIVER@STOP 6 OF")) {
									Del = 4;

								} else if (jobStatus.contains("DELIVER@STOP 7 OF")) {
									Del = 5;

								}
								System.out.println("value of del==" + Del);

								WebElement ZoneID = DelPoints.get(Del)
										.findElement(By.xpath("//td[@ng-bind=\"shipmentdtls.ActdlTz\"]"));
								String DeliveryDate = "txtActdlDate_" + Del;
								String DeliveryTime = "txtActDlTime_" + Del;

								// --Get ZoneID
								String ZOneID = ZoneID.getText();
								logs.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}

								// --Delivery Date
								WebElement DelDate = driver.findElement(By.id(DeliveryDate));
								DelDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logs.info(dateFormat.format(date));
								DelDate.sendKeys(dateFormat.format(date));
								DelDate.sendKeys(Keys.TAB);
								logs.info("Entered Actual Delivery Date");

								// --Enter Act.DEL Time
								WebElement DelTime = driver.findElement(By.id(DeliveryTime));
								DelTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
								cal.add(Calendar.MINUTE, 1);
								logs.info(dateFormat.format(cal.getTime()));
								wait.until(ExpectedConditions.elementToBeClickable(By.id(DeliveryTime)));
								DelTime.sendKeys(dateFormat.format(cal.getTime()));
								logs.info("Entered Actual Delivery Time");

								// --Click on Confirm Del button
								isElementPresent("TLConfDEL_id").click();
								logs.info("Clicked on Confirm DEL button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							}
						} catch (Exception ActTimeGDelTime) {
							logs.info("Validation is not displayed="
									+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

						}

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
							PickUpID = getData("OneToMany", 1, 2);
							isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
							logs.info("Entered PickUpID in basic search");

							// --Click on Search
							wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
							isElementPresent("TLGlobSearch_id").click();
							logs.info("Click on Search button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							try {
								WebElement NoData2 = isElementPresent("NoData_className");
								wait.until(ExpectedConditions.visibilityOf(NoData2));
								if (NoData2.isDisplayed()) {
									logs.info("There is no Data with Search parameter");

								}

							} catch (Exception NoData2) {
								logs.info("Data is exist with search parameter");
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
								getScreenshot(driver, "JobEditor_Delivered");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

								if (jobStatus.contains("DELIVERED")) {
									logs.info("Job is Delivered successfully");

								} else {
									logs.info("Job is not Delivered successfully");
									jobStatus = isElementPresent("TLStageLable_id").getText();
									logs.info("Job status is==" + jobStatus);

								}

							}

							//

						} catch (Exception Deliverstage) {
							logs.info("job is not moved to delivered stage");
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);
						}

					} catch (Exception Deliver) {
						logs.info("Stage is not Deliver");
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);
					}
				} catch (Exception PickUp) {
					logs.info("Stage is not PickUP");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);
				}

			} else if (jobStatus.contains("PICKUP")) {

				// --PICKUP@STOP 1 OF 2 stage
				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("routetable")));
					getScreenshot(driver, "JobEditor_PickUP");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);
					// --Click on ConfirmPU button
					wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
					isElementPresent("TLCOnfPU_id").click();
					logs.info("Clicked on Confirm PU button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
						String ValMsg = isElementPresent("TLAlValidation_id").getText();
						logs.info("Validation is displayed==" + ValMsg);

						// --Stored list of pickup
						List<WebElement> PickupPoints = driver.findElements(
								By.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
						int TotalPickup = PickupPoints.size();
						logs.info("Total Pickup points is/are==" + TotalPickup);

						for (int pu = 0; pu < TotalPickup; pu++) {

							WebElement ZoneID = PickupPoints.get(pu)
									.findElement(By.xpath("//td[@ng-bind=\"shipmentdtls.ActpuTz\"]"));
							String PUDate = "txtActpuDate_" + pu;
							String PUTime = "txtActPuTime_" + pu;

							// --Get ZoneID
							String ZOneID = ZoneID.getText();
							logs.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}
							// --PickUp Date
							WebElement PickUpDate = driver.findElement(By.id(PUDate));
							PickUpDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							PickUpDate.sendKeys(dateFormat.format(date));
							PickUpDate.sendKeys(Keys.TAB);
							logs.info("Entered Actual Pickup Date");

							// --Enter Act.PickUp Time
							WebElement PickUpTime = driver.findElement(By.id(PUTime));
							PickUpTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							wait.until(ExpectedConditions.elementToBeClickable(By.id(PUTime)));
							PickUpTime.sendKeys(dateFormat.format(date));
							logs.info("Entered Actual Pickup Time");

						}

						// --Click on ConfirmPU button
						isElementPresent("TLCOnfPU_id").click();
						logs.info("Clicked on Confirm PU button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					} catch (Exception NoVal) {
						logs.info("Validation for Act.Pickup Time is not displayed");

					}

					// --DELIVER@STOP 2 OF 2 stage
					try {
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("routetable")));
						getScreenshot(driver, "JobEditor_Deliver");
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);
						// --Click on ConfirmPU button
						wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
						isElementPresent("TLConfDEL_id").click();
						logs.info("Clicked on Confirm DEL button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
							String ValMsg = isElementPresent("TLAlValidation_id").getText();
							logs.info("Validation is displayed==" + ValMsg);

							// --Stored list of pickup
							List<WebElement> DelPoints = driver.findElements(
									By.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
							int TotalDel = DelPoints.size();
							logs.info("Total Delivery points is/are==" + TotalDel);

							for (int Del = 0; Del < TotalDel; Del++) {

								WebElement ZoneID = DelPoints.get(Del)
										.findElement(By.xpath("//td[@ng-bind=\"shipmentdtls.ActdlTz\"]"));
								String DeliveryDate = "txtActdlDate_" + Del;
								String DeliveryTime = "txtActDlTime_" + Del;

								// --Get ZoneID
								String ZOneID = ZoneID.getText();
								logs.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}

								// --Delivery Date
								WebElement DelDate = driver.findElement(By.id(DeliveryDate));
								DelDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logs.info(dateFormat.format(date));
								DelDate.sendKeys(dateFormat.format(date));
								DelDate.sendKeys(Keys.TAB);
								logs.info("Entered Actual Delivery Date");

								// --Enter Act.DEL Time
								WebElement DelTime = driver.findElement(By.id(DeliveryTime));
								DelTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logs.info(dateFormat.format(date));
								wait.until(ExpectedConditions.elementToBeClickable(By.id(DeliveryTime)));
								DelTime.sendKeys(dateFormat.format(date));
								logs.info("Entered Actual Delivery Time");

								// --Enter Signature
								WebElement Sign = DelPoints.get(Del).findElement(By.id("txtsign"));
								wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
								Sign.sendKeys("RV");
								logs.info("Entered Signature");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Click on Confirm Del button
								isElementPresent("TLConfDEL_id").click();
								logs.info("Clicked on Confirm DEL button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// Rebind the list
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);
								DelPoints = driver.findElements(
										By.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
							}

						} catch (Exception NoValSign) {
							logs.info("Validation for Act.Del Time and Signature is not displayed");

						}

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
							String ValMsg = isElementPresent("TLAlValidation_id").getText();
							logs.info("Validation is displayed==" + ValMsg);

							List<WebElement> DelPoints = driver.findElements(
									By.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
							int TotalDel = DelPoints.size();
							logs.info("Total Delivery points is/are==" + TotalDel);

							for (int Del = 0; Del < TotalDel; Del++) {

								System.out.println("value of del==" + Del);
								if (jobStatus.contains("DELIVER@STOP 3 OF")) {
									Del = 1;
								} else if (jobStatus.contains("DELIVER@STOP 4 OF")) {
									Del = 2;

								} else if (jobStatus.contains("DELIVER@STOP 5 OF")) {
									Del = 3;

								} else if (jobStatus.contains("DELIVER@STOP 6 OF")) {
									Del = 4;

								} else if (jobStatus.contains("DELIVER@STOP 7 OF")) {
									Del = 5;

								}
								System.out.println("value of del==" + Del);

								WebElement ZoneID = DelPoints.get(Del)
										.findElement(By.xpath("//td[@ng-bind=\"shipmentdtls.ActdlTz\"]"));
								String DeliveryDate = "txtActdlDate_" + Del;
								String DeliveryTime = "txtActDlTime_" + Del;

								// --Get ZoneID
								String ZOneID = ZoneID.getText();
								logs.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								}

								// --Delivery Date
								WebElement DelDate = driver.findElement(By.id(DeliveryDate));
								DelDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logs.info(dateFormat.format(date));
								DelDate.sendKeys(dateFormat.format(date));
								DelDate.sendKeys(Keys.TAB);
								logs.info("Entered Actual Delivery Date");

								// --Enter Act.DEL Time
								WebElement DelTime = driver.findElement(By.id(DeliveryTime));
								DelTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
								cal.add(Calendar.MINUTE, 1);
								logs.info(dateFormat.format(cal.getTime()));
								wait.until(ExpectedConditions.elementToBeClickable(By.id(DeliveryTime)));
								DelTime.sendKeys(dateFormat.format(cal.getTime()));
								logs.info("Entered Actual Delivery Time");

								// --Click on Confirm Del button
								isElementPresent("TLConfDEL_id").click();
								logs.info("Clicked on Confirm DEL button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							}
						} catch (Exception ActTimeGDelTime) {
							logs.info("Validation is not displayed="
									+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

						}

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
							PickUpID = getData("OneToMany", 1, 2);
							isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
							logs.info("Entered PickUpID in basic search");

							// --Click on Search
							wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
							isElementPresent("TLGlobSearch_id").click();
							logs.info("Click on Search button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							try {
								WebElement NoData2 = isElementPresent("NoData_className");
								wait.until(ExpectedConditions.visibilityOf(NoData2));
								if (NoData2.isDisplayed()) {
									logs.info("There is no Data with Search parameter");

								}

							} catch (Exception NoData2) {
								logs.info("Data is exist with search parameter");
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
								getScreenshot(driver, "JobEditor_Delivered");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

								if (jobStatus.contains("DELIVERED")) {
									logs.info("Job is Delivered successfully");

								} else {
									logs.info("Job is not Delivered successfully");
									jobStatus = isElementPresent("TLStageLable_id").getText();
									logs.info("Job status is==" + jobStatus);

								}

							}

							//

						} catch (Exception Deliverstage) {
							logs.info("job is not moved to delivered stage");
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);
						}

					} catch (Exception Deliver) {
						logs.info("Stage is not Deliver");
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);
					}
				} catch (Exception PickUp) {
					logs.info("Stage is not PickUP");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);
				}

			} else if (jobStatus.contains("DELIVER")) {

				// --DELIVER@STOP 2 OF 2 stage
				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("routetable")));
					getScreenshot(driver, "JobEditor_Deliver");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);
					// --Click on ConfirmPU button
					wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
					isElementPresent("TLConfDEL_id").click();
					logs.info("Clicked on Confirm DEL button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
						String ValMsg = isElementPresent("TLAlValidation_id").getText();
						logs.info("Validation is displayed==" + ValMsg);

						// --Stored list of pickup
						List<WebElement> DelPoints = driver.findElements(
								By.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
						int TotalDel = DelPoints.size();
						logs.info("Total Delivery points is/are==" + TotalDel);

						for (int Del = 0; Del < TotalDel; Del++) {

							WebElement ZoneID = DelPoints.get(Del)
									.findElement(By.xpath("//td[@ng-bind=\"shipmentdtls.ActdlTz\"]"));
							String DeliveryDate = "txtActdlDate_" + Del;
							String DeliveryTime = "txtActDlTime_" + Del;

							// --Get ZoneID
							String ZOneID = ZoneID.getText();
							logs.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}

							// --Delivery Date
							WebElement DelDate = driver.findElement(By.id(DeliveryDate));
							DelDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							DelDate.sendKeys(dateFormat.format(date));
							DelDate.sendKeys(Keys.TAB);
							logs.info("Entered Actual Delivery Date");

							// --Enter Act.DEL Time
							WebElement DelTime = driver.findElement(By.id(DeliveryTime));
							DelTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							wait.until(ExpectedConditions.elementToBeClickable(By.id(DeliveryTime)));
							DelTime.sendKeys(dateFormat.format(date));
							logs.info("Entered Actual Delivery Time");

							// --Enter Signature
							WebElement Sign = DelPoints.get(Del).findElement(By.id("txtsign"));
							wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
							Sign.sendKeys("RV");
							logs.info("Entered Signature");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Click on Confirm Del button
							isElementPresent("TLConfDEL_id").click();
							logs.info("Clicked on Confirm DEL button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// Rebind the list
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);
							DelPoints = driver.findElements(
									By.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
						}

					} catch (Exception NoValSign) {
						logs.info("Validation for Act.Del Time and Signature is not displayed");

					}

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
						String ValMsg = isElementPresent("TLAlValidation_id").getText();
						logs.info("Validation is displayed==" + ValMsg);

						List<WebElement> DelPoints = driver.findElements(
								By.xpath("//*[@id=\"scrollShip\"]//tr[contains(@ng-click,'setClickedRow')]"));
						int TotalDel = DelPoints.size();
						logs.info("Total Delivery points is/are==" + TotalDel);

						for (int Del = 0; Del < TotalDel; Del++) {

							System.out.println("value of del==" + Del);
							if (jobStatus.contains("DELIVER@STOP 3 OF")) {
								Del = 1;
							} else if (jobStatus.contains("DELIVER@STOP 4 OF")) {
								Del = 2;

							} else if (jobStatus.contains("DELIVER@STOP 5 OF")) {
								Del = 3;

							} else if (jobStatus.contains("DELIVER@STOP 6 OF")) {
								Del = 4;

							} else if (jobStatus.contains("DELIVER@STOP 7 OF")) {
								Del = 5;

							}
							System.out.println("value of del==" + Del);

							WebElement ZoneID = DelPoints.get(Del)
									.findElement(By.xpath("//td[@ng-bind=\"shipmentdtls.ActdlTz\"]"));
							String DeliveryDate = "txtActdlDate_" + Del;
							String DeliveryTime = "txtActDlTime_" + Del;

							// --Get ZoneID
							String ZOneID = ZoneID.getText();
							logs.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							}

							// --Delivery Date
							WebElement DelDate = driver.findElement(By.id(DeliveryDate));
							DelDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							DelDate.sendKeys(dateFormat.format(date));
							DelDate.sendKeys(Keys.TAB);
							logs.info("Entered Actual Delivery Date");

							// --Enter Act.DEL Time
							WebElement DelTime = driver.findElement(By.id(DeliveryTime));
							DelTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
							cal.add(Calendar.MINUTE, 1);
							logs.info(dateFormat.format(cal.getTime()));
							wait.until(ExpectedConditions.elementToBeClickable(By.id(DeliveryTime)));
							DelTime.sendKeys(dateFormat.format(cal.getTime()));
							logs.info("Entered Actual Delivery Time");

							// --Click on Confirm Del button
							isElementPresent("TLConfDEL_id").click();
							logs.info("Clicked on Confirm DEL button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
						}
					} catch (Exception ActTimeGDelTime) {
						logs.info("Validation is not displayed="
								+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

					}

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
						PickUpID = getData("OneToMany", 1, 2);
						isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
						logs.info("Entered PickUpID in basic search");

						// --Click on Search
						wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
						isElementPresent("TLGlobSearch_id").click();
						logs.info("Click on Search button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						try {
							WebElement NoData2 = isElementPresent("NoData_className");
							wait.until(ExpectedConditions.visibilityOf(NoData2));
							if (NoData2.isDisplayed()) {
								logs.info("There is no Data with Search parameter");

							}

						} catch (Exception NoData2) {
							logs.info("Data is exist with search parameter");
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
							getScreenshot(driver, "JobEditor_Delivered");
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);

							if (jobStatus.contains("DELIVERED")) {
								logs.info("Job is Delivered successfully");

							} else {
								logs.info("Job is not Delivered successfully");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

							}

						}

						//

					} catch (Exception Deliverstage) {
						logs.info("job is not moved to delivered stage");
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);
					}

				} catch (Exception Deliver) {
					logs.info("Stage is not Deliver");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);
				}

			} else if (jobStatus.contains("DELIVERED")) {
				logs.info("Job is on Delivered stage");
				jobStatus = isElementPresent("TLStageLable_id").getText();
				logs.info("Job status is==" + jobStatus);
			} else {

				logs.info("Unknown Stage found");
				jobStatus = isElementPresent("TLStageLable_id").getText();
				logs.info("Job status is==" + jobStatus);
			}

		} catch (Exception NoDataex) {
			WebElement NoData = isElementPresent("NoData_className");
			wait.until(ExpectedConditions.visibilityOf(NoData));
			if (NoData.isDisplayed()) {
				logs.info("There is no Data with Search parameter");

			}
		}

		logs.info("======================RTE One To Many Order Processing Test End==================");
		msg.append("======================RTE One To Many Order Processing Test End==================" + "\n");

	}

}

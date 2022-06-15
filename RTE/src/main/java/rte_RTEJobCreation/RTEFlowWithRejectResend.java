package rte_RTEJobCreation;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

public class RTEFlowWithRejectResend extends BaseInit {

	@Test
	public void rteRejectResendScan() throws EncryptedDocumentException, InvalidFormatException, IOException {

		WebDriverWait wait = new WebDriverWait(driver, 50);
		Actions act = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

		logs.info("======================RTE Reject/Resend/CallBack/PendingDelivery Test Start==================");
		msg.append(
				"======================RTE Reject/Resend/CallBack/PendingDelivery Test Start==================" + "\n");

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
		String PickUpID = getData("SearchRTE", 2, 2);
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

		} catch (Exception NoData1) {
			logs.info("Data is exist with search parameter");
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
			getScreenshot(driver, "NegFlow_TCACK");

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
				getScreenshot(driver, "NegFlow_TCACK");

				// --Click on Acknowledge button
				wait.until(ExpectedConditions.elementToBeClickable(By.id("GreyTick")));
				isElementPresent("TLAcknoldge_id").click();
				logs.info("Clicked on Acknowledge button");
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

				try {
					WebElement PickUPSection = isElementPresent("TLAlertstages_id");
					wait.until(ExpectedConditions.visibilityOf(PickUPSection));
					getScreenshot(driver, "NegFlow_RDYFORDSP");
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
						PickUpID = getData("SearchRTE", 2, 2);
						isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
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
						} catch (Exception NoData11) {

							logs.info("Data is exist with search parameter");
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
							getScreenshot(driver, "NegFlow_PUDRVCNF");

							// --Go to Job Status Tab
							wait.until(ExpectedConditions.elementToBeClickable(By.id("idJobOverview")));
							isElementPresent("TLJobSTatus_id").click();
							logs.info("Clicked on Job Status Tab");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Job Status
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);

							// --Click on ReSendAlert
							isElementPresent("TLResendAl_id").click();
							logs.info("Clicked on Resend Alert");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Job Status
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);

							if (jobStatus.equalsIgnoreCase("PU DRV CONF")) {
								logs.info("Resend Alert is send");

							} else {
								logs.info("Resend Alert is not send");

							}

							// --Click on Reject Alert
							isElementPresent("TLRejectAl_id").click();
							logs.info("Clicked on Reject Alert");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtMemo")));
							logs.info("Memo text area is displayed on Reject Alert button");

							// --Click on Reject Alert for validation message
							isElementPresent("TLRejectAl_id").click();
							logs.info("Clicked on Reject Alert");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.xpath("//*[@ng-message=\"memoRequired\"]")));

								String valMesg = isElementPresent("TLMVal_xpath").getText();
								logs.info("Validation is displayed=" + valMesg);

								// --Enter Memo
								wait.until(ExpectedConditions.elementToBeClickable(By.id("txtMemo")));
								isElementPresent("TLRAMemo_id").clear();
								isElementPresent("TLRAMemo_id").sendKeys("Reject alert by automation script");

								// --Reject button
								isElementPresent("TLRejectAl_id").click();
								logs.info("Clicked on Reject Alert");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Job Status
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblDeclinedMsg")));
								String DeclineMsg = isElementPresent("TLDecMsg_id").getText();
								logs.info("decline message is==" + DeclineMsg);

								if (jobStatus.equalsIgnoreCase("RDY FOR DSP")
										&& DeclineMsg.contains("has declined alert")) {
									logs.info("Reject alert is send==PASS");

									// --Click on send PU alert
									wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
									isElementPresent("TLSendPUAl_id").click();
									logs.info("Clicked on Send PU Alert button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									try {
										wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
										PickUpID = getData("SearchRTE", 2, 2);
										isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
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
										} catch (Exception NoData) {

											logs.info("Data is exist with search parameter");
											wait.until(ExpectedConditions
													.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));

											// --Click on Confirm PU Alert
											isElementPresent("TLSendPUAl_id").click();
											logs.info("Clicked on Confirm PU Alert");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											try {
												wait.until(ExpectedConditions
														.visibilityOfElementLocated(By.id("idValidationforMain")));
												String ValMsg = isElementPresent("TLAlValidation_id").getText();
												logs.info("Validation is displayed==" + ValMsg);

												// --Enter SpokeWith
												wait.until(
														ExpectedConditions.elementToBeClickable(By.id("txtSpokeWith")));
												isElementPresent("TLSpokeWith_id").sendKeys("RV");
												logs.info("Entered Spoke With");

												// --Click on Confirm PU Alert
												isElementPresent("TLSendPUAl_id").click();
												logs.info("Clicked on Confirm PU Alert");
												wait.until(ExpectedConditions
														.invisibilityOfElementLocated(By.id("loaderDiv")));

											} catch (Exception NoVal) {
												logs.info("Spoke With validation is not displayed==FAIL");

											}

										}
									} catch (Exception e) {
										logs.info("Job is not moved to PU DRV CONF stage after Reject alert scenario");

									}
								} else {
									logs.info("Reject Alert is not send==FAIL");

								}

							} catch (Exception val) {
								logs.info("Validation is not displayed for memo field==FAIL");
								// ----This case is not confirmed yet
								// --Click on Confirm PU Alert
								isElementPresent("TLSendPUAl_id").click();
								logs.info("Clicked on Confirm PU Alert");
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

									// --Click on Confirm PU Alert
									isElementPresent("TLSendPUAl_id").click();
									logs.info("Clicked on Confirm PU Alert");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								} catch (Exception NoVal) {
									logs.info("Spoke With validation is not displayed");

								}
							}

							// --PICKUP@STOP 1 OF 2 stage
							try {
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("routetable")));
								getScreenshot(driver, "NegFlow_PickUP");
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

									// --Get ZoneID
									String ZOneID = isElementPresent("TLACPTZone_xpath").getText();
									logs.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									} else if (ZOneID.equalsIgnoreCase("PDT")) {
										ZOneID = "PST";
									}
									// --PickUp Date
									WebElement PickUpDate = isElementPresent("TLActPuDate_id");
									PickUpDate.clear();
									Date date = new Date();
									DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logs.info(dateFormat.format(date));
									PickUpDate.sendKeys(dateFormat.format(date));
									PickUpDate.sendKeys(Keys.TAB);
									logs.info("Entered Actual Pickup Date");

									// --Enter Act.PickUp Time
									WebElement PickUpTime = isElementPresent("TLActPUpTime_id");
									PickUpTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logs.info(dateFormat.format(date));
									wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActPuTime_0")));
									PickUpTime.sendKeys(dateFormat.format(date));
									logs.info("Entered Actual Pickup Time");

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
									getScreenshot(driver, "NegFlow_Deliver");
									jobStatus = isElementPresent("TLStageLable_id").getText();
									logs.info("Job status is==" + jobStatus);
									// --Click on ConfirmDEL button
									wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
									isElementPresent("TLConfDEL_id").click();
									logs.info("Clicked on Confirm DEL button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.id("idValidationforMain")));
										String ValMsg = isElementPresent("TLAlValidation_id").getText();
										logs.info("Validation is displayed==" + ValMsg);

										// --Get ZoneID
										String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
										logs.info("ZoneID of is==" + ZOneID);
										if (ZOneID.equalsIgnoreCase("EDT")) {
											ZOneID = "America/New_York";
										} else if (ZOneID.equalsIgnoreCase("CDT")) {
											ZOneID = "CST";
										} else if (ZOneID.equalsIgnoreCase("PDT")) {
											ZOneID = "PST";
										}

										// --Delivery Date
										WebElement DelDate = isElementPresent("TLActDelDate_id");
										DelDate.clear();
										Date date = new Date();
										DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logs.info(dateFormat.format(date));
										DelDate.sendKeys(dateFormat.format(date));
										DelDate.sendKeys(Keys.TAB);
										logs.info("Entered Actual Delivery Date");

										// --Enter Act.DEL Time
										WebElement DelTime = isElementPresent("TLActDelTime_id");
										DelTime.clear();
										date = new Date();
										dateFormat = new SimpleDateFormat("HH:mm");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logs.info(dateFormat.format(date));
										wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
										DelTime.sendKeys(dateFormat.format(date));
										logs.info("Entered Actual Delivery Time");

										// --Enter Signature
										wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
										isElementPresent("TLSign_id").clear();
										isElementPresent("TLSign_id").sendKeys("RV");
										logs.info("Entered Signature");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										// --Click on ReRoute
										WebElement ReRoute = isElementPresent("TLReRouteCB_id");
										js.executeScript("arguments[0].click();", ReRoute);
										logs.info("Clicked on Re Route Checkbox");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										// --Click on Confirm Del button
										isElementPresent("TLConfDEL_id").click();
										logs.info("Clicked on Confirm DEL button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									} catch (Exception NoValSign) {
										logs.info("Validation for Act.Del Time and Signature is not displayed");

									}

									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.id("idValidationforMain")));
										String ValMsg = isElementPresent("TLAlValidation_id").getText();
										logs.info("Validation is displayed==" + ValMsg);

										// --Get ZoneID
										String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
										logs.info("ZoneID of is==" + ZOneID);
										if (ZOneID.equalsIgnoreCase("EDT")) {
											ZOneID = "America/New_York";
										} else if (ZOneID.equalsIgnoreCase("CDT")) {
											ZOneID = "CST";
										} else if (ZOneID.equalsIgnoreCase("PDT")) {
											ZOneID = "PST";
										}

										// --Delivery Date
										WebElement DelDate = isElementPresent("TLActDelDate_id");
										DelDate.clear();
										Date date = new Date();
										DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logs.info(dateFormat.format(date));
										DelDate.sendKeys(dateFormat.format(date));
										DelDate.sendKeys(Keys.TAB);
										logs.info("Entered Actual Delivery Date");

										// --Enter Act.DEL Time
										WebElement DelTime = isElementPresent("TLActDelTime_id");
										DelTime.clear();
										date = new Date();
										dateFormat = new SimpleDateFormat("HH:mm");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
										cal.add(Calendar.MINUTE, 1);
										logs.info(dateFormat.format(cal.getTime()));
										wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
										DelTime.sendKeys(dateFormat.format(cal.getTime()));
										logs.info("Entered Actual Delivery Time");

										// --Click on Confirm Del button
										isElementPresent("TLConfDEL_id").click();
										logs.info("Clicked on Confirm DEL button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									} catch (Exception ActTimeGDelTime) {
										logs.info("Validation is not displayed="
												+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

									}

									// --PENDING DELIVERY

									try {
										wait.until(ExpectedConditions
												.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
										wait.until(ExpectedConditions.visibilityOfElementLocated(
												By.xpath("//label[@id=\"lblStages\"][text()='Pending Delivery']")));
										getScreenshot(driver, "NegFlow_PendingDel");
										jobStatus = isElementPresent("TLStageLable_id").getText();
										logs.info("Job status is==" + jobStatus);

										if (jobStatus.contains("PENDING DELIVERY")) {
											logs.info("Job is moved to PENDING DELIVERY==PASS");

										}

										// --Click on ConfirmDEL button
										wait.until(
												ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
										isElementPresent("TLConfDEL_id").click();
										logs.info("Clicked on Confirm DEL button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										try {
											wait.until(ExpectedConditions
													.visibilityOfElementLocated(By.id("idValidationforMain")));
											String ValMsg = isElementPresent("TLAlValidation_id").getText();
											logs.info("Validation is displayed==" + ValMsg);

											// --Get ZoneID
											String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
											logs.info("ZoneID of is==" + ZOneID);
											if (ZOneID.equalsIgnoreCase("EDT")) {
												ZOneID = "America/New_York";
											} else if (ZOneID.equalsIgnoreCase("CDT")) {
												ZOneID = "CST";
											} else if (ZOneID.equalsIgnoreCase("PDT")) {
												ZOneID = "PST";
											}

											// --Delivery Date
											WebElement DelDate = isElementPresent("TLActDelDate_id");
											DelDate.clear();
											Date date = new Date();
											DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
											dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
											logs.info(dateFormat.format(date));
											DelDate.sendKeys(dateFormat.format(date));
											DelDate.sendKeys(Keys.TAB);
											logs.info("Entered Actual Delivery Date");

											// --Enter Act.DEL Time
											WebElement DelTime = isElementPresent("TLActDelTime_id");
											DelTime.clear();
											date = new Date();
											dateFormat = new SimpleDateFormat("HH:mm");
											dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
											logs.info(dateFormat.format(date));
											wait.until(
													ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
											DelTime.sendKeys(dateFormat.format(date));
											logs.info("Entered Actual Delivery Time");

											// --Enter Signature
											wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
											isElementPresent("TLSign_id").clear();
											isElementPresent("TLSign_id").sendKeys("RV");
											logs.info("Entered Signature");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											// --Click on Confirm Del button
											isElementPresent("TLConfDEL_id").click();
											logs.info("Clicked on Confirm DEL button");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											try {
												wait.until(ExpectedConditions
														.visibilityOfElementLocated(By.id("idValidationforMain")));
												ValMsg = isElementPresent("TLAlValidation_id").getText();
												logs.info("Validation is displayed==" + ValMsg);

												// --Get ZoneID
												ZOneID = isElementPresent("TLACDELZone_xpath").getText();
												logs.info("ZoneID of is==" + ZOneID);
												if (ZOneID.equalsIgnoreCase("EDT")) {
													ZOneID = "America/New_York";
												} else if (ZOneID.equalsIgnoreCase("CDT")) {
													ZOneID = "CST";
												} else if (ZOneID.equalsIgnoreCase("PDT")) {
													ZOneID = "PST";
												}

												// --Delivery Date
												DelDate = isElementPresent("TLActDelDate_id");
												DelDate.clear();
												date = new Date();
												dateFormat = new SimpleDateFormat("MM/dd/yyyy");
												dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
												logs.info(dateFormat.format(date));
												DelDate.sendKeys(dateFormat.format(date));
												DelDate.sendKeys(Keys.TAB);
												logs.info("Entered Actual Delivery Date");

												// --Enter Act.DEL Time
												DelTime = isElementPresent("TLActDelTime_id");
												DelTime.clear();
												date = new Date();
												dateFormat = new SimpleDateFormat("HH:mm");
												dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
												Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
												cal.add(Calendar.MINUTE, 1);
												logs.info(dateFormat.format(cal.getTime()));
												wait.until(ExpectedConditions
														.elementToBeClickable(By.id("txtActDlTime_0")));
												DelTime.sendKeys(dateFormat.format(cal.getTime()));
												logs.info("Entered Actual Delivery Time");

												// --Click on Confirm Del button
												isElementPresent("TLConfDEL_id").click();
												logs.info("Clicked on Confirm DEL button");
												wait.until(ExpectedConditions
														.invisibilityOfElementLocated(By.id("loaderDiv")));

											} catch (Exception ActTimeGDelTime) {
												logs.info("Validation is not displayed="
														+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

											}
										} catch (Exception NoValSign) {
											logs.info("Validation for Act.Del Time and Signature is not displayed");

										}
										try {
											wait.until(ExpectedConditions
													.visibilityOfElementLocated(By.id("txtContains")));
											PickUpID = getData("SearchRTE", 2, 2);
											isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
											logs.info("Entered PickUpID in basic search");

											// --Click on Search
											wait.until(
													ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
											isElementPresent("TLGlobSearch_id").click();
											logs.info("Click on Search button");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											try {
												WebElement NoData2 = isElementPresent("NoData_className");
												wait.until(ExpectedConditions.visibilityOf(NoData2));
												if (NoData2.isDisplayed()) {
													logs.info("There is no Data with Search parameter");

												}
											} catch (Exception NoData) {

												logs.info("Data is exist with search parameter");
												wait.until(ExpectedConditions
														.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
												getScreenshot(driver, "NegFlow_Delivered");
												jobStatus = isElementPresent("TLStageLable_id").getText();
												logs.info("Job status is==" + jobStatus);

												if (jobStatus.contains("DELIVERED")) {
													logs.info("Job is Delivered successfully");

													// --End Route

													// --Click on End Route
													WebElement EndR = isElementPresent("TLEndRoute_id");
													wait.until(ExpectedConditions.visibilityOf(EndR));
													act.moveToElement(EndR).build().perform();
													act.moveToElement(EndR).click().perform();
													logs.info("Clicked on End Route");
													wait.until(ExpectedConditions
															.invisibilityOfElementLocated(By.id("loaderDiv")));

													try {
														wait.until(ExpectedConditions
																.visibilityOfAllElementsLocatedBy(By.id("errorid")));

														String Val = isElementPresent("Error_id").getText();
														logs.info("Validation is displayed==" + Val);

														if (Val.contains("Route End Date")
																&& Val.contains("Route End Time")) {
															logs.info(
																	"Validation is displayed for Route End Date and Time==PASS");

															// --Enter Route End Date
															// --Get ZoneID
															String ZOneID = isElementPresent("TLERZone_xpath")
																	.getText();
															logs.info("ZoneID of is==" + ZOneID);
															if (ZOneID.equalsIgnoreCase("EDT")) {
																ZOneID = "America/New_York";
															} else if (ZOneID.equalsIgnoreCase("CDT")) {
																ZOneID = "CST";
															} else if (ZOneID.equalsIgnoreCase("PDT")) {
																ZOneID = "PST";
															}

															// --Route End Date
															WebElement ERDate = isElementPresent("TLERDate_id");
															ERDate.clear();
															Date date = new Date();
															DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
															dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
															logs.info(dateFormat.format(date));
															ERDate.sendKeys(dateFormat.format(date));
															ERDate.sendKeys(Keys.TAB);
															logs.info("Entered Actual Route End Date");

															// --Route End Time
															WebElement ERTime = isElementPresent("TLERTime_id");
															ERTime.clear();
															date = new Date();
															dateFormat = new SimpleDateFormat("HH:mm");
															dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
															Calendar cal = Calendar
																	.getInstance(TimeZone.getTimeZone(ZOneID));
															cal.add(Calendar.MINUTE, 1);
															logs.info(dateFormat.format(cal.getTime()));
															wait.until(ExpectedConditions
																	.elementToBeClickable(By.id("txtActualTime")));
															ERTime.sendKeys(dateFormat.format(cal.getTime()));
															logs.info("Entered Actual Route End Time");

															// --Click on End Route
															EndR = isElementPresent("TLEndRoute_id");
															wait.until(ExpectedConditions.visibilityOf(EndR));
															act.moveToElement(EndR).build().perform();
															act.moveToElement(EndR).click().perform();
															logs.info("Clicked on End Route");
															wait.until(ExpectedConditions
																	.invisibilityOfElementLocated(By.id("loaderDiv")));

														} else {
															logs.info(
																	"Validation is not displayed for Route End Date and Time==FAIL");

															WebElement EWSave = isElementPresent("TLQCExitWSave_id");
															wait.until(ExpectedConditions.visibilityOf(EWSave));
															act.moveToElement(EWSave).build().perform();
															act.moveToElement(EWSave).click().perform();
															logs.info("Clicked on Exit Without Save");
														}

													} catch (Exception EndRoute) {
														logs.info(
																"Validation is not displayed for Route End Date and Time==FAIL");

													}
													// --Verify Customer Bill

													try {
														wait.until(ExpectedConditions
																.visibilityOfElementLocated(By.id("txtContains")));
														PickUpID = getData("SearchRTE", 1, 2);
														isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
														logs.info("Entered PickUpID in basic search");

														// --Click on Search
														wait.until(ExpectedConditions
																.elementToBeClickable(By.id("btnGlobalSearch")));
														isElementPresent("TLGlobSearch_id").click();
														logs.info("Click on Search button");
														wait.until(ExpectedConditions
																.invisibilityOfElementLocated(By.id("loaderDiv")));

														try {
															WebElement NoDataV = isElementPresent("NoData_className");
															wait.until(ExpectedConditions.visibilityOf(NoDataV));
															if (NoDataV.isDisplayed()) {
																logs.info("There is no Data with Search parameter");

															}

														} catch (Exception NoDatae) {
															logs.info("Data is exist with search parameter");
															wait.until(
																	ExpectedConditions.visibilityOfAllElementsLocatedBy(
																			By.id("RouteWorkFlow")));
															getScreenshot(driver, "JobEditor_Delivered");
															jobStatus = isElementPresent("TLStageLable_id").getText();
															logs.info("Job status is==" + jobStatus);

															if (jobStatus.contains("VERIFY CUSTOMER BILL")) {
																logs.info("Job is moved to Verify Customer Bill stage");
																getScreenshot(driver, "JobEditor_VerifyCustBill");

																// --Verify

																// --Click on Verify button
																WebElement Verify = isElementPresent("TLVerify_id");
																wait.until(ExpectedConditions.visibilityOf(Verify));
																act.moveToElement(Verify).build().perform();
																act.moveToElement(Verify).click().perform();
																logs.info("Clicked on Verify button");
																wait.until(
																		ExpectedConditions.invisibilityOfElementLocated(
																				By.id("loaderDiv")));

																// --Verified

																try {
																	wait.until(ExpectedConditions
																			.visibilityOfElementLocated(
																					By.id("txtContains")));
																	PickUpID = getData("SearchRTE", 1, 2);
																	isElementPresent("TLBasicSearch_id")
																			.sendKeys(PickUpID);
																	logs.info("Entered PickUpID in basic search");

																	// --Click on Search
																	wait.until(ExpectedConditions.elementToBeClickable(
																			By.id("btnGlobalSearch")));
																	isElementPresent("TLGlobSearch_id").click();
																	logs.info("Click on Search button");
																	wait.until(ExpectedConditions
																			.invisibilityOfElementLocated(
																					By.id("loaderDiv")));

																	try {
																		WebElement NoDataV = isElementPresent(
																				"NoData_className");
																		wait.until(ExpectedConditions
																				.visibilityOf(NoDataV));
																		if (NoDataV.isDisplayed()) {
																			logs.info(
																					"There is no Data with Search parameter");

																		}

																	} catch (Exception NoDataee) {
																		logs.info(
																				"Data is exist with search parameter");
																		wait.until(ExpectedConditions
																				.visibilityOfAllElementsLocatedBy(
																						By.id("RouteWorkFlow")));
																		getScreenshot(driver, "JobEditor_Delivered");
																		jobStatus = isElementPresent("TLStageLable_id")
																				.getText();
																		logs.info("Job status is==" + jobStatus);

																		if (jobStatus.contains("VERIFIED")) {
																			logs.info("Job is moved to VERIFIED stage");
																			getScreenshot(driver, "JobEditor_Verified");
																			WebElement EWSave = isElementPresent(
																					"TLQCExitWSave_id");
																			wait.until(ExpectedConditions
																					.visibilityOf(EWSave));
																			act.moveToElement(EWSave).build().perform();
																			act.moveToElement(EWSave).click().perform();
																			logs.info("Clicked on Exit Without Save");
																		} else {
																			logs.info(
																					"Job is not moved to VERIFIED stage");
																			jobStatus = isElementPresent(
																					"TLStageLable_id").getText();
																			logs.info("Job status is==" + jobStatus);

																			WebElement EWSave = isElementPresent(
																					"TLQCExitWSave_id");
																			wait.until(ExpectedConditions
																					.visibilityOf(EWSave));
																			act.moveToElement(EWSave).build().perform();
																			act.moveToElement(EWSave).click().perform();
																			logs.info("Clicked on Exit Without Save");

																		}

																	}

																	//

																} catch (Exception VerifyCBill) {
																	logs.info("job is not moved to VERIFIED stage");
																	jobStatus = isElementPresent("TLStageLable_id")
																			.getText();
																	logs.info("Job status is==" + jobStatus);

																	WebElement EWSave = isElementPresent(
																			"TLQCExitWSave_id");
																	wait.until(ExpectedConditions.visibilityOf(EWSave));
																	act.moveToElement(EWSave).build().perform();
																	act.moveToElement(EWSave).click().perform();
																	logs.info("Clicked on Exit Without Save");

																}

															} else {
																logs.info(
																		"Job is not moved to Verify Customer Bill stage");
																jobStatus = isElementPresent("TLStageLable_id")
																		.getText();
																logs.info("Job status is==" + jobStatus);

																WebElement EWSave = isElementPresent(
																		"TLQCExitWSave_id");
																wait.until(ExpectedConditions.visibilityOf(EWSave));
																act.moveToElement(EWSave).build().perform();
																act.moveToElement(EWSave).click().perform();
																logs.info("Clicked on Exit Without Save");

															}

														}

														//

													} catch (Exception VerifyCBill) {
														logs.info("job is not moved to Verify Customer Bill stage");
														jobStatus = isElementPresent("TLStageLable_id").getText();
														logs.info("Job status is==" + jobStatus);
													}

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

									} catch (Exception e) {
										logs.info("Job is moved to PENDING DELIVERY==FAIL");
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
				try {
					WebElement PickUPSection = isElementPresent("TLAlertstages_id");
					wait.until(ExpectedConditions.visibilityOf(PickUPSection));
					getScreenshot(driver, "NegFlow_RDYFORDSP");
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
						PickUpID = getData("SearchRTE", 2, 2);
						isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
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
						} catch (Exception NoData11) {

							logs.info("Data is exist with search parameter");
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
							getScreenshot(driver, "NegFlow_PUDRVCNF");

							// --Go to Job Status Tab
							wait.until(ExpectedConditions.elementToBeClickable(By.id("idJobOverview")));
							isElementPresent("TLJobSTatus_id").click();
							logs.info("Clicked on Job Status Tab");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Job Status
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);

							// --Click on ReSendAlert
							isElementPresent("TLResendAl_id").click();
							logs.info("Clicked on Resend Alert");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Job Status
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);

							if (jobStatus.equalsIgnoreCase("PU DRV CONF")) {
								logs.info("Resend Alert is send");

							} else {
								logs.info("Resend Alert is not send");

							}

							// --Click on Reject Alert
							isElementPresent("TLRejectAl_id").click();
							logs.info("Clicked on Reject Alert");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtMemo")));
							logs.info("Memo text area is displayed on Reject Alert button");

							// --Click on Reject Alert for validation message
							isElementPresent("TLRejectAl_id").click();
							logs.info("Clicked on Reject Alert");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.xpath("//*[@ng-message=\"memoRequired\"]")));

								String valMesg = isElementPresent("TLMVal_xpath").getText();
								logs.info("Validation is displayed=" + valMesg);

								// --Enter Memo
								wait.until(ExpectedConditions.elementToBeClickable(By.id("txtMemo")));
								isElementPresent("TLRAMemo_id").clear();
								isElementPresent("TLRAMemo_id").sendKeys("Reject alert by automation script");

								// --Reject button
								isElementPresent("TLRejectAl_id").click();
								logs.info("Clicked on Reject Alert");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Job Status
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblDeclinedMsg")));
								String DeclineMsg = isElementPresent("TLDecMsg_id").getText();
								logs.info("decline message is==" + DeclineMsg);

								if (jobStatus.equalsIgnoreCase("RDY FOR DSP")
										&& DeclineMsg.contains("has declined alert")) {
									logs.info("Reject alert is send==PASS");

									// --Click on send PU alert
									wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
									isElementPresent("TLSendPUAl_id").click();
									logs.info("Clicked on Send PU Alert button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									try {
										wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
										PickUpID = getData("SearchRTE", 2, 2);
										isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
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
										} catch (Exception NoData) {

											logs.info("Data is exist with search parameter");
											wait.until(ExpectedConditions
													.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));

											// --Click on Confirm PU Alert
											isElementPresent("TLSendPUAl_id").click();
											logs.info("Clicked on Confirm PU Alert");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											try {
												wait.until(ExpectedConditions
														.visibilityOfElementLocated(By.id("idValidationforMain")));
												String ValMsg = isElementPresent("TLAlValidation_id").getText();
												logs.info("Validation is displayed==" + ValMsg);

												// --Enter SpokeWith
												wait.until(
														ExpectedConditions.elementToBeClickable(By.id("txtSpokeWith")));
												isElementPresent("TLSpokeWith_id").sendKeys("RV");
												logs.info("Entered Spoke With");

												// --Click on Confirm PU Alert
												isElementPresent("TLSendPUAl_id").click();
												logs.info("Clicked on Confirm PU Alert");
												wait.until(ExpectedConditions
														.invisibilityOfElementLocated(By.id("loaderDiv")));

											} catch (Exception NoVal) {
												logs.info("Spoke With validation is not displayed==FAIL");

											}

										}
									} catch (Exception e) {
										logs.info("Job is not moved to PU DRV CONF stage after Reject alert scenario");

									}
								} else {
									logs.info("Reject Alert is not send==FAIL");

								}

							} catch (Exception val) {
								logs.info("Validation is not displayed for memo field==FAIL");
								// ----This case is not confirmed yet
								// --Click on Confirm PU Alert
								isElementPresent("TLSendPUAl_id").click();
								logs.info("Clicked on Confirm PU Alert");
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

									// --Click on Confirm PU Alert
									isElementPresent("TLSendPUAl_id").click();
									logs.info("Clicked on Confirm PU Alert");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								} catch (Exception NoVal) {
									logs.info("Spoke With validation is not displayed");

								}
							}

							// --PICKUP@STOP 1 OF 2 stage
							try {
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("routetable")));
								getScreenshot(driver, "NegFlow_PickUP");
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

									// --Get ZoneID
									String ZOneID = isElementPresent("TLACPTZone_xpath").getText();
									logs.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									} else if (ZOneID.equalsIgnoreCase("PDT")) {
										ZOneID = "PST";
									}
									// --PickUp Date
									WebElement PickUpDate = isElementPresent("TLActPuDate_id");
									PickUpDate.clear();
									Date date = new Date();
									DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logs.info(dateFormat.format(date));
									PickUpDate.sendKeys(dateFormat.format(date));
									PickUpDate.sendKeys(Keys.TAB);
									logs.info("Entered Actual Pickup Date");

									// --Enter Act.PickUp Time
									WebElement PickUpTime = isElementPresent("TLActPUpTime_id");
									PickUpTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logs.info(dateFormat.format(date));
									wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActPuTime_0")));
									PickUpTime.sendKeys(dateFormat.format(date));
									logs.info("Entered Actual Pickup Time");

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
									getScreenshot(driver, "NegFlow_Deliver");
									jobStatus = isElementPresent("TLStageLable_id").getText();
									logs.info("Job status is==" + jobStatus);
									// --Click on ConfirmDEL button
									wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
									isElementPresent("TLConfDEL_id").click();
									logs.info("Clicked on Confirm DEL button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.id("idValidationforMain")));
										String ValMsg = isElementPresent("TLAlValidation_id").getText();
										logs.info("Validation is displayed==" + ValMsg);

										// --Get ZoneID
										String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
										logs.info("ZoneID of is==" + ZOneID);
										if (ZOneID.equalsIgnoreCase("EDT")) {
											ZOneID = "America/New_York";
										} else if (ZOneID.equalsIgnoreCase("CDT")) {
											ZOneID = "CST";
										} else if (ZOneID.equalsIgnoreCase("PDT")) {
											ZOneID = "PST";
										}

										// --Delivery Date
										WebElement DelDate = isElementPresent("TLActDelDate_id");
										DelDate.clear();
										Date date = new Date();
										DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logs.info(dateFormat.format(date));
										DelDate.sendKeys(dateFormat.format(date));
										DelDate.sendKeys(Keys.TAB);
										logs.info("Entered Actual Delivery Date");

										// --Enter Act.DEL Time
										WebElement DelTime = isElementPresent("TLActDelTime_id");
										DelTime.clear();
										date = new Date();
										dateFormat = new SimpleDateFormat("HH:mm");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logs.info(dateFormat.format(date));
										wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
										DelTime.sendKeys(dateFormat.format(date));
										logs.info("Entered Actual Delivery Time");

										// --Enter Signature
										wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
										isElementPresent("TLSign_id").clear();
										isElementPresent("TLSign_id").sendKeys("RV");
										logs.info("Entered Signature");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										// --Click on ReRoute
										WebElement ReRoute = isElementPresent("TLReRouteCB_id");
										js.executeScript("arguments[0].click();", ReRoute);
										logs.info("Clicked on Re Route Checkbox");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										// --Click on Confirm Del button
										isElementPresent("TLConfDEL_id").click();
										logs.info("Clicked on Confirm DEL button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									} catch (Exception NoValSign) {
										logs.info("Validation for Act.Del Time and Signature is not displayed");

									}

									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.id("idValidationforMain")));
										String ValMsg = isElementPresent("TLAlValidation_id").getText();
										logs.info("Validation is displayed==" + ValMsg);

										// --Get ZoneID
										String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
										logs.info("ZoneID of is==" + ZOneID);
										if (ZOneID.equalsIgnoreCase("EDT")) {
											ZOneID = "America/New_York";
										} else if (ZOneID.equalsIgnoreCase("CDT")) {
											ZOneID = "CST";
										} else if (ZOneID.equalsIgnoreCase("PDT")) {
											ZOneID = "PST";
										}

										// --Delivery Date
										WebElement DelDate = isElementPresent("TLActDelDate_id");
										DelDate.clear();
										Date date = new Date();
										DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logs.info(dateFormat.format(date));
										DelDate.sendKeys(dateFormat.format(date));
										DelDate.sendKeys(Keys.TAB);
										logs.info("Entered Actual Delivery Date");

										// --Enter Act.DEL Time
										WebElement DelTime = isElementPresent("TLActDelTime_id");
										DelTime.clear();
										date = new Date();
										dateFormat = new SimpleDateFormat("HH:mm");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
										cal.add(Calendar.MINUTE, 1);
										logs.info(dateFormat.format(cal.getTime()));
										wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
										DelTime.sendKeys(dateFormat.format(cal.getTime()));
										logs.info("Entered Actual Delivery Time");

										// --Click on Confirm Del button
										isElementPresent("TLConfDEL_id").click();
										logs.info("Clicked on Confirm DEL button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									} catch (Exception ActTimeGDelTime) {
										logs.info("Validation is not displayed="
												+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

									}

									// --PENDING DELIVERY

									try {
										wait.until(ExpectedConditions
												.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
										wait.until(ExpectedConditions.visibilityOfElementLocated(
												By.xpath("//label[@id=\"lblStages\"][text()='Pending Delivery']")));
										getScreenshot(driver, "NegFlow_PendingDel");
										jobStatus = isElementPresent("TLStageLable_id").getText();
										logs.info("Job status is==" + jobStatus);

										if (jobStatus.contains("PENDING DELIVERY")) {
											logs.info("Job is moved to PENDING DELIVERY==PASS");

										}

										// --Click on ConfirmDEL button
										wait.until(
												ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
										isElementPresent("TLConfDEL_id").click();
										logs.info("Clicked on Confirm DEL button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										try {
											wait.until(ExpectedConditions
													.visibilityOfElementLocated(By.id("idValidationforMain")));
											String ValMsg = isElementPresent("TLAlValidation_id").getText();
											logs.info("Validation is displayed==" + ValMsg);

											// --Get ZoneID
											String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
											logs.info("ZoneID of is==" + ZOneID);
											if (ZOneID.equalsIgnoreCase("EDT")) {
												ZOneID = "America/New_York";
											} else if (ZOneID.equalsIgnoreCase("CDT")) {
												ZOneID = "CST";
											} else if (ZOneID.equalsIgnoreCase("PDT")) {
												ZOneID = "PST";
											}

											// --Delivery Date
											WebElement DelDate = isElementPresent("TLActDelDate_id");
											DelDate.clear();
											Date date = new Date();
											DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
											dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
											logs.info(dateFormat.format(date));
											DelDate.sendKeys(dateFormat.format(date));
											DelDate.sendKeys(Keys.TAB);
											logs.info("Entered Actual Delivery Date");

											// --Enter Act.DEL Time
											WebElement DelTime = isElementPresent("TLActDelTime_id");
											DelTime.clear();
											date = new Date();
											dateFormat = new SimpleDateFormat("HH:mm");
											dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
											logs.info(dateFormat.format(date));
											wait.until(
													ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
											DelTime.sendKeys(dateFormat.format(date));
											logs.info("Entered Actual Delivery Time");

											// --Enter Signature
											wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
											isElementPresent("TLSign_id").clear();
											isElementPresent("TLSign_id").sendKeys("RV");
											logs.info("Entered Signature");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											// --Click on Confirm Del button
											isElementPresent("TLConfDEL_id").click();
											logs.info("Clicked on Confirm DEL button");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											try {
												wait.until(ExpectedConditions
														.visibilityOfElementLocated(By.id("idValidationforMain")));
												ValMsg = isElementPresent("TLAlValidation_id").getText();
												logs.info("Validation is displayed==" + ValMsg);

												// --Get ZoneID
												ZOneID = isElementPresent("TLACDELZone_xpath").getText();
												logs.info("ZoneID of is==" + ZOneID);
												if (ZOneID.equalsIgnoreCase("EDT")) {
													ZOneID = "America/New_York";
												} else if (ZOneID.equalsIgnoreCase("CDT")) {
													ZOneID = "CST";
												} else if (ZOneID.equalsIgnoreCase("PDT")) {
													ZOneID = "PST";
												}

												// --Delivery Date
												DelDate = isElementPresent("TLActDelDate_id");
												DelDate.clear();
												date = new Date();
												dateFormat = new SimpleDateFormat("MM/dd/yyyy");
												dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
												logs.info(dateFormat.format(date));
												DelDate.sendKeys(dateFormat.format(date));
												DelDate.sendKeys(Keys.TAB);
												logs.info("Entered Actual Delivery Date");

												// --Enter Act.DEL Time
												DelTime = isElementPresent("TLActDelTime_id");
												DelTime.clear();
												date = new Date();
												dateFormat = new SimpleDateFormat("HH:mm");
												dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
												Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
												cal.add(Calendar.MINUTE, 1);
												logs.info(dateFormat.format(cal.getTime()));
												wait.until(ExpectedConditions
														.elementToBeClickable(By.id("txtActDlTime_0")));
												DelTime.sendKeys(dateFormat.format(cal.getTime()));
												logs.info("Entered Actual Delivery Time");

												// --Click on Confirm Del button
												isElementPresent("TLConfDEL_id").click();
												logs.info("Clicked on Confirm DEL button");
												wait.until(ExpectedConditions
														.invisibilityOfElementLocated(By.id("loaderDiv")));

											} catch (Exception ActTimeGDelTime) {
												logs.info("Validation is not displayed="
														+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

											}
										} catch (Exception NoValSign) {
											logs.info("Validation for Act.Del Time and Signature is not displayed");

										}
										try {
											wait.until(ExpectedConditions
													.visibilityOfElementLocated(By.id("txtContains")));
											PickUpID = getData("SearchRTE", 2, 2);
											isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
											logs.info("Entered PickUpID in basic search");

											// --Click on Search
											wait.until(
													ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
											isElementPresent("TLGlobSearch_id").click();
											logs.info("Click on Search button");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											try {
												WebElement NoData2 = isElementPresent("NoData_className");
												wait.until(ExpectedConditions.visibilityOf(NoData2));
												if (NoData2.isDisplayed()) {
													logs.info("There is no Data with Search parameter");

												}
											} catch (Exception NoData) {

												logs.info("Data is exist with search parameter");
												wait.until(ExpectedConditions
														.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
												getScreenshot(driver, "NegFlow_Delivered");
												jobStatus = isElementPresent("TLStageLable_id").getText();
												logs.info("Job status is==" + jobStatus);

												if (jobStatus.contains("DELIVERED")) {
													logs.info("Job is Delivered successfully");

													// --End Route

													// --Click on End Route
													WebElement EndR = isElementPresent("TLEndRoute_id");
													wait.until(ExpectedConditions.visibilityOf(EndR));
													act.moveToElement(EndR).build().perform();
													act.moveToElement(EndR).click().perform();
													logs.info("Clicked on End Route");
													wait.until(ExpectedConditions
															.invisibilityOfElementLocated(By.id("loaderDiv")));

													try {
														wait.until(ExpectedConditions
																.visibilityOfAllElementsLocatedBy(By.id("errorid")));

														String Val = isElementPresent("Error_id").getText();
														logs.info("Validation is displayed==" + Val);

														if (Val.contains("Route End Date")
																&& Val.contains("Route End Time")) {
															logs.info(
																	"Validation is displayed for Route End Date and Time==PASS");

															// --Enter Route End Date
															// --Get ZoneID
															String ZOneID = isElementPresent("TLERZone_xpath")
																	.getText();
															logs.info("ZoneID of is==" + ZOneID);
															if (ZOneID.equalsIgnoreCase("EDT")) {
																ZOneID = "America/New_York";
															} else if (ZOneID.equalsIgnoreCase("CDT")) {
																ZOneID = "CST";
															} else if (ZOneID.equalsIgnoreCase("PDT")) {
																ZOneID = "PST";
															}

															// --Route End Date
															WebElement ERDate = isElementPresent("TLERDate_id");
															ERDate.clear();
															Date date = new Date();
															DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
															dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
															logs.info(dateFormat.format(date));
															ERDate.sendKeys(dateFormat.format(date));
															ERDate.sendKeys(Keys.TAB);
															logs.info("Entered Actual Route End Date");

															// --Route End Time
															WebElement ERTime = isElementPresent("TLERTime_id");
															ERTime.clear();
															date = new Date();
															dateFormat = new SimpleDateFormat("HH:mm");
															dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
															Calendar cal = Calendar
																	.getInstance(TimeZone.getTimeZone(ZOneID));
															cal.add(Calendar.MINUTE, 1);
															logs.info(dateFormat.format(cal.getTime()));
															wait.until(ExpectedConditions
																	.elementToBeClickable(By.id("txtActualTime")));
															ERTime.sendKeys(dateFormat.format(cal.getTime()));
															logs.info("Entered Actual Route End Time");

															// --Click on End Route
															EndR = isElementPresent("TLEndRoute_id");
															wait.until(ExpectedConditions.visibilityOf(EndR));
															act.moveToElement(EndR).build().perform();
															act.moveToElement(EndR).click().perform();
															logs.info("Clicked on End Route");
															wait.until(ExpectedConditions
																	.invisibilityOfElementLocated(By.id("loaderDiv")));

														} else {
															logs.info(
																	"Validation is not displayed for Route End Date and Time==FAIL");

															WebElement EWSave = isElementPresent("TLQCExitWSave_id");
															wait.until(ExpectedConditions.visibilityOf(EWSave));
															act.moveToElement(EWSave).build().perform();
															act.moveToElement(EWSave).click().perform();
															logs.info("Clicked on Exit Without Save");
														}

													} catch (Exception EndRoute) {
														logs.info(
																"Validation is not displayed for Route End Date and Time==FAIL");

													}
													// --Verify Customer Bill

													try {
														wait.until(ExpectedConditions
																.visibilityOfElementLocated(By.id("txtContains")));
														PickUpID = getData("SearchRTE", 1, 2);
														isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
														logs.info("Entered PickUpID in basic search");

														// --Click on Search
														wait.until(ExpectedConditions
																.elementToBeClickable(By.id("btnGlobalSearch")));
														isElementPresent("TLGlobSearch_id").click();
														logs.info("Click on Search button");
														wait.until(ExpectedConditions
																.invisibilityOfElementLocated(By.id("loaderDiv")));

														try {
															WebElement NoDataV = isElementPresent("NoData_className");
															wait.until(ExpectedConditions.visibilityOf(NoDataV));
															if (NoDataV.isDisplayed()) {
																logs.info("There is no Data with Search parameter");

															}

														} catch (Exception NoDatae) {
															logs.info("Data is exist with search parameter");
															wait.until(
																	ExpectedConditions.visibilityOfAllElementsLocatedBy(
																			By.id("RouteWorkFlow")));
															getScreenshot(driver, "JobEditor_Delivered");
															jobStatus = isElementPresent("TLStageLable_id").getText();
															logs.info("Job status is==" + jobStatus);

															if (jobStatus.contains("VERIFY CUSTOMER BILL")) {
																logs.info("Job is moved to Verify Customer Bill stage");
																getScreenshot(driver, "JobEditor_VerifyCustBill");

																// --Verify

																// --Click on Verify button
																WebElement Verify = isElementPresent("TLVerify_id");
																wait.until(ExpectedConditions.visibilityOf(Verify));
																act.moveToElement(Verify).build().perform();
																act.moveToElement(Verify).click().perform();
																logs.info("Clicked on Verify button");
																wait.until(
																		ExpectedConditions.invisibilityOfElementLocated(
																				By.id("loaderDiv")));

																// --Verified

																try {
																	wait.until(ExpectedConditions
																			.visibilityOfElementLocated(
																					By.id("txtContains")));
																	PickUpID = getData("SearchRTE", 1, 2);
																	isElementPresent("TLBasicSearch_id")
																			.sendKeys(PickUpID);
																	logs.info("Entered PickUpID in basic search");

																	// --Click on Search
																	wait.until(ExpectedConditions.elementToBeClickable(
																			By.id("btnGlobalSearch")));
																	isElementPresent("TLGlobSearch_id").click();
																	logs.info("Click on Search button");
																	wait.until(ExpectedConditions
																			.invisibilityOfElementLocated(
																					By.id("loaderDiv")));

																	try {
																		WebElement NoDataV = isElementPresent(
																				"NoData_className");
																		wait.until(ExpectedConditions
																				.visibilityOf(NoDataV));
																		if (NoDataV.isDisplayed()) {
																			logs.info(
																					"There is no Data with Search parameter");

																		}

																	} catch (Exception NoDataee) {
																		logs.info(
																				"Data is exist with search parameter");
																		wait.until(ExpectedConditions
																				.visibilityOfAllElementsLocatedBy(
																						By.id("RouteWorkFlow")));
																		getScreenshot(driver, "JobEditor_Delivered");
																		jobStatus = isElementPresent("TLStageLable_id")
																				.getText();
																		logs.info("Job status is==" + jobStatus);

																		if (jobStatus.contains("VERIFIED")) {
																			logs.info("Job is moved to VERIFIED stage");
																			getScreenshot(driver, "JobEditor_Verified");
																			WebElement EWSave = isElementPresent(
																					"TLQCExitWSave_id");
																			wait.until(ExpectedConditions
																					.visibilityOf(EWSave));
																			act.moveToElement(EWSave).build().perform();
																			act.moveToElement(EWSave).click().perform();
																			logs.info("Clicked on Exit Without Save");
																		} else {
																			logs.info(
																					"Job is not moved to VERIFIED stage");
																			jobStatus = isElementPresent(
																					"TLStageLable_id").getText();
																			logs.info("Job status is==" + jobStatus);

																			WebElement EWSave = isElementPresent(
																					"TLQCExitWSave_id");
																			wait.until(ExpectedConditions
																					.visibilityOf(EWSave));
																			act.moveToElement(EWSave).build().perform();
																			act.moveToElement(EWSave).click().perform();
																			logs.info("Clicked on Exit Without Save");

																		}

																	}

																	//

																} catch (Exception VerifyCBill) {
																	logs.info("job is not moved to VERIFIED stage");
																	jobStatus = isElementPresent("TLStageLable_id")
																			.getText();
																	logs.info("Job status is==" + jobStatus);

																	WebElement EWSave = isElementPresent(
																			"TLQCExitWSave_id");
																	wait.until(ExpectedConditions.visibilityOf(EWSave));
																	act.moveToElement(EWSave).build().perform();
																	act.moveToElement(EWSave).click().perform();
																	logs.info("Clicked on Exit Without Save");

																}

															} else {
																logs.info(
																		"Job is not moved to Verify Customer Bill stage");
																jobStatus = isElementPresent("TLStageLable_id")
																		.getText();
																logs.info("Job status is==" + jobStatus);

																WebElement EWSave = isElementPresent(
																		"TLQCExitWSave_id");
																wait.until(ExpectedConditions.visibilityOf(EWSave));
																act.moveToElement(EWSave).build().perform();
																act.moveToElement(EWSave).click().perform();
																logs.info("Clicked on Exit Without Save");

															}

														}

														//

													} catch (Exception VerifyCBill) {
														logs.info("job is not moved to Verify Customer Bill stage");
														jobStatus = isElementPresent("TLStageLable_id").getText();
														logs.info("Job status is==" + jobStatus);
													}

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

									} catch (Exception e) {
										logs.info("Job is moved to PENDING DELIVERY==FAIL");
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

						}

					} catch (Exception Somethingw) {
						logs.info("Job is not moved to PU DRV CONF stage");

					}
				} catch (Exception NoPickupS) {
					logs.info("There is no PickUp Driver section");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);

				}

			} else if (jobStatus.contains("PU DRV CONF")) {
				try {

					logs.info("Data is exist with search parameter");
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
					getScreenshot(driver, "NegFlow_PUDRVCNF");

					// --Go to Job Status Tab
					wait.until(ExpectedConditions.elementToBeClickable(By.id("idJobOverview")));
					isElementPresent("TLJobSTatus_id").click();
					logs.info("Clicked on Job Status Tab");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Job Status
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);

					// --Click on ReSendAlert
					isElementPresent("TLResendAl_id").click();
					logs.info("Clicked on Resend Alert");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Job Status
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);

					if (jobStatus.equalsIgnoreCase("PU DRV CONF")) {
						logs.info("Resend Alert is send");

					} else {
						logs.info("Resend Alert is not send");

					}

					// --Click on Reject Alert
					isElementPresent("TLRejectAl_id").click();
					logs.info("Clicked on Reject Alert");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtMemo")));
					logs.info("Memo text area is displayed on Reject Alert button");

					// --Click on Reject Alert for validation message
					isElementPresent("TLRejectAl_id").click();
					logs.info("Clicked on Reject Alert");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//*[@ng-message=\"memoRequired\"]")));

						String valMesg = isElementPresent("TLMVal_xpath").getText();
						logs.info("Validation is displayed=" + valMesg);

						// --Enter Memo
						wait.until(ExpectedConditions.elementToBeClickable(By.id("txtMemo")));
						isElementPresent("TLRAMemo_id").clear();
						isElementPresent("TLRAMemo_id").sendKeys("Reject alert by automation script");

						// --Reject button
						isElementPresent("TLRejectAl_id").click();
						logs.info("Clicked on Reject Alert");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Job Status
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);

						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblDeclinedMsg")));
						String DeclineMsg = isElementPresent("TLDecMsg_id").getText();
						logs.info("decline message is==" + DeclineMsg);

						if (jobStatus.equalsIgnoreCase("RDY FOR DSP") && DeclineMsg.contains("has declined alert")) {
							logs.info("Reject alert is send==PASS");

							// --Click on send PU alert
							wait.until(ExpectedConditions.elementToBeClickable(By.id("WhiteTickAlert")));
							isElementPresent("TLSendPUAl_id").click();
							logs.info("Clicked on Send PU Alert button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
								PickUpID = getData("SearchRTE", 2, 2);
								isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
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
								} catch (Exception NoData) {

									logs.info("Data is exist with search parameter");
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));

									// --Click on Confirm PU Alert
									isElementPresent("TLSendPUAl_id").click();
									logs.info("Clicked on Confirm PU Alert");
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

										// --Click on Confirm PU Alert
										isElementPresent("TLSendPUAl_id").click();
										logs.info("Clicked on Confirm PU Alert");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									} catch (Exception NoVal) {
										logs.info("Spoke With validation is not displayed==FAIL");

									}

								}
							} catch (Exception e) {
								logs.info("Job is not moved to PU DRV CONF stage after Reject alert scenario");

							}
						} else {
							logs.info("Reject Alert is not send==FAIL");

						}

					} catch (Exception val) {
						logs.info("Validation is not displayed for memo field==FAIL");
						// ----This case is not confirmed yet
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
					}

					// --PICKUP@STOP 1 OF 2 stage
					try {
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("routetable")));
						getScreenshot(driver, "NegFlow_PickUP");
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

							// --Get ZoneID
							String ZOneID = isElementPresent("TLACPTZone_xpath").getText();
							logs.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							} else if (ZOneID.equalsIgnoreCase("PDT")) {
								ZOneID = "PST";
							}
							// --PickUp Date
							WebElement PickUpDate = isElementPresent("TLActPuDate_id");
							PickUpDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							PickUpDate.sendKeys(dateFormat.format(date));
							PickUpDate.sendKeys(Keys.TAB);
							logs.info("Entered Actual Pickup Date");

							// --Enter Act.PickUp Time
							WebElement PickUpTime = isElementPresent("TLActPUpTime_id");
							PickUpTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActPuTime_0")));
							PickUpTime.sendKeys(dateFormat.format(date));
							logs.info("Entered Actual Pickup Time");

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
							getScreenshot(driver, "NegFlow_Deliver");
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);
							// --Click on ConfirmDEL button
							wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
							isElementPresent("TLConfDEL_id").click();
							logs.info("Clicked on Confirm DEL button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
								String ValMsg = isElementPresent("TLAlValidation_id").getText();
								logs.info("Validation is displayed==" + ValMsg);

								// --Get ZoneID
								String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
								logs.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								} else if (ZOneID.equalsIgnoreCase("PDT")) {
									ZOneID = "PST";
								}

								// --Delivery Date
								WebElement DelDate = isElementPresent("TLActDelDate_id");
								DelDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logs.info(dateFormat.format(date));
								DelDate.sendKeys(dateFormat.format(date));
								DelDate.sendKeys(Keys.TAB);
								logs.info("Entered Actual Delivery Date");

								// --Enter Act.DEL Time
								WebElement DelTime = isElementPresent("TLActDelTime_id");
								DelTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logs.info(dateFormat.format(date));
								wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
								DelTime.sendKeys(dateFormat.format(date));
								logs.info("Entered Actual Delivery Time");

								// --Enter Signature
								wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
								isElementPresent("TLSign_id").clear();
								isElementPresent("TLSign_id").sendKeys("RV");
								logs.info("Entered Signature");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Click on ReRoute
								WebElement ReRoute = isElementPresent("TLReRouteCB_id");
								js.executeScript("arguments[0].click();", ReRoute);
								logs.info("Clicked on Re Route Checkbox");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Click on Confirm Del button
								isElementPresent("TLConfDEL_id").click();
								logs.info("Clicked on Confirm DEL button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							} catch (Exception NoValSign) {
								logs.info("Validation for Act.Del Time and Signature is not displayed");

							}

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
								String ValMsg = isElementPresent("TLAlValidation_id").getText();
								logs.info("Validation is displayed==" + ValMsg);

								// --Get ZoneID
								String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
								logs.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								} else if (ZOneID.equalsIgnoreCase("PDT")) {
									ZOneID = "PST";
								}

								// --Delivery Date
								WebElement DelDate = isElementPresent("TLActDelDate_id");
								DelDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logs.info(dateFormat.format(date));
								DelDate.sendKeys(dateFormat.format(date));
								DelDate.sendKeys(Keys.TAB);
								logs.info("Entered Actual Delivery Date");

								// --Enter Act.DEL Time
								WebElement DelTime = isElementPresent("TLActDelTime_id");
								DelTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
								cal.add(Calendar.MINUTE, 1);
								logs.info(dateFormat.format(cal.getTime()));
								wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
								DelTime.sendKeys(dateFormat.format(cal.getTime()));
								logs.info("Entered Actual Delivery Time");

								// --Click on Confirm Del button
								isElementPresent("TLConfDEL_id").click();
								logs.info("Clicked on Confirm DEL button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							} catch (Exception ActTimeGDelTime) {
								logs.info("Validation is not displayed="
										+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

							}

							// --PENDING DELIVERY

							try {
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
								wait.until(ExpectedConditions.visibilityOfElementLocated(
										By.xpath("//label[@id=\"lblStages\"][text()='Pending Delivery']")));
								getScreenshot(driver, "NegFlow_PendingDel");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

								if (jobStatus.contains("PENDING DELIVERY")) {
									logs.info("Job is moved to PENDING DELIVERY==PASS");

								}

								// --Click on ConfirmDEL button
								wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
								isElementPresent("TLConfDEL_id").click();
								logs.info("Clicked on Confirm DEL button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.id("idValidationforMain")));
									String ValMsg = isElementPresent("TLAlValidation_id").getText();
									logs.info("Validation is displayed==" + ValMsg);

									// --Get ZoneID
									String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
									logs.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									} else if (ZOneID.equalsIgnoreCase("PDT")) {
										ZOneID = "PST";
									}

									// --Delivery Date
									WebElement DelDate = isElementPresent("TLActDelDate_id");
									DelDate.clear();
									Date date = new Date();
									DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logs.info(dateFormat.format(date));
									DelDate.sendKeys(dateFormat.format(date));
									DelDate.sendKeys(Keys.TAB);
									logs.info("Entered Actual Delivery Date");

									// --Enter Act.DEL Time
									WebElement DelTime = isElementPresent("TLActDelTime_id");
									DelTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logs.info(dateFormat.format(date));
									wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
									DelTime.sendKeys(dateFormat.format(date));
									logs.info("Entered Actual Delivery Time");

									// --Enter Signature
									wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
									isElementPresent("TLSign_id").clear();
									isElementPresent("TLSign_id").sendKeys("RV");
									logs.info("Entered Signature");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									// --Click on Confirm Del button
									isElementPresent("TLConfDEL_id").click();
									logs.info("Clicked on Confirm DEL button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									try {
										wait.until(ExpectedConditions
												.visibilityOfElementLocated(By.id("idValidationforMain")));
										ValMsg = isElementPresent("TLAlValidation_id").getText();
										logs.info("Validation is displayed==" + ValMsg);

										// --Get ZoneID
										ZOneID = isElementPresent("TLACDELZone_xpath").getText();
										logs.info("ZoneID of is==" + ZOneID);
										if (ZOneID.equalsIgnoreCase("EDT")) {
											ZOneID = "America/New_York";
										} else if (ZOneID.equalsIgnoreCase("CDT")) {
											ZOneID = "CST";
										} else if (ZOneID.equalsIgnoreCase("PDT")) {
											ZOneID = "PST";
										}

										// --Delivery Date
										DelDate = isElementPresent("TLActDelDate_id");
										DelDate.clear();
										date = new Date();
										dateFormat = new SimpleDateFormat("MM/dd/yyyy");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logs.info(dateFormat.format(date));
										DelDate.sendKeys(dateFormat.format(date));
										DelDate.sendKeys(Keys.TAB);
										logs.info("Entered Actual Delivery Date");

										// --Enter Act.DEL Time
										DelTime = isElementPresent("TLActDelTime_id");
										DelTime.clear();
										date = new Date();
										dateFormat = new SimpleDateFormat("HH:mm");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
										cal.add(Calendar.MINUTE, 1);
										logs.info(dateFormat.format(cal.getTime()));
										wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
										DelTime.sendKeys(dateFormat.format(cal.getTime()));
										logs.info("Entered Actual Delivery Time");

										// --Click on Confirm Del button
										isElementPresent("TLConfDEL_id").click();
										logs.info("Clicked on Confirm DEL button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									} catch (Exception ActTimeGDelTime) {
										logs.info("Validation is not displayed="
												+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

									}
								} catch (Exception NoValSign) {
									logs.info("Validation for Act.Del Time and Signature is not displayed");

								}
								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
									PickUpID = getData("SearchRTE", 2, 2);
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
									} catch (Exception NoData) {

										logs.info("Data is exist with search parameter");
										wait.until(ExpectedConditions
												.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
										getScreenshot(driver, "NegFlow_Delivered");
										jobStatus = isElementPresent("TLStageLable_id").getText();
										logs.info("Job status is==" + jobStatus);

										if (jobStatus.contains("DELIVERED")) {
											logs.info("Job is Delivered successfully");

											// --End Route

											// --Click on End Route
											WebElement EndR = isElementPresent("TLEndRoute_id");
											wait.until(ExpectedConditions.visibilityOf(EndR));
											act.moveToElement(EndR).build().perform();
											act.moveToElement(EndR).click().perform();
											logs.info("Clicked on End Route");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											try {
												wait.until(ExpectedConditions
														.visibilityOfAllElementsLocatedBy(By.id("errorid")));

												String Val = isElementPresent("Error_id").getText();
												logs.info("Validation is displayed==" + Val);

												if (Val.contains("Route End Date") && Val.contains("Route End Time")) {
													logs.info(
															"Validation is displayed for Route End Date and Time==PASS");

													// --Enter Route End Date
													// --Get ZoneID
													String ZOneID = isElementPresent("TLERZone_xpath").getText();
													logs.info("ZoneID of is==" + ZOneID);
													if (ZOneID.equalsIgnoreCase("EDT")) {
														ZOneID = "America/New_York";
													} else if (ZOneID.equalsIgnoreCase("CDT")) {
														ZOneID = "CST";
													} else if (ZOneID.equalsIgnoreCase("PDT")) {
														ZOneID = "PST";
													}

													// --Route End Date
													WebElement ERDate = isElementPresent("TLERDate_id");
													ERDate.clear();
													Date date = new Date();
													DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
													dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
													logs.info(dateFormat.format(date));
													ERDate.sendKeys(dateFormat.format(date));
													ERDate.sendKeys(Keys.TAB);
													logs.info("Entered Actual Route End Date");

													// --Route End Time
													WebElement ERTime = isElementPresent("TLERTime_id");
													ERTime.clear();
													date = new Date();
													dateFormat = new SimpleDateFormat("HH:mm");
													dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
													Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
													cal.add(Calendar.MINUTE, 1);
													logs.info(dateFormat.format(cal.getTime()));
													wait.until(ExpectedConditions
															.elementToBeClickable(By.id("txtActualTime")));
													ERTime.sendKeys(dateFormat.format(cal.getTime()));
													logs.info("Entered Actual Route End Time");

													// --Click on End Route
													EndR = isElementPresent("TLEndRoute_id");
													wait.until(ExpectedConditions.visibilityOf(EndR));
													act.moveToElement(EndR).build().perform();
													act.moveToElement(EndR).click().perform();
													logs.info("Clicked on End Route");
													wait.until(ExpectedConditions
															.invisibilityOfElementLocated(By.id("loaderDiv")));

												} else {
													logs.info(
															"Validation is not displayed for Route End Date and Time==FAIL");

													WebElement EWSave = isElementPresent("TLQCExitWSave_id");
													wait.until(ExpectedConditions.visibilityOf(EWSave));
													act.moveToElement(EWSave).build().perform();
													act.moveToElement(EWSave).click().perform();
													logs.info("Clicked on Exit Without Save");
												}

											} catch (Exception EndRoute) {
												logs.info(
														"Validation is not displayed for Route End Date and Time==FAIL");

											}
											// --Verify Customer Bill

											try {
												wait.until(ExpectedConditions
														.visibilityOfElementLocated(By.id("txtContains")));
												PickUpID = getData("SearchRTE", 1, 2);
												isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
												logs.info("Entered PickUpID in basic search");

												// --Click on Search
												wait.until(ExpectedConditions
														.elementToBeClickable(By.id("btnGlobalSearch")));
												isElementPresent("TLGlobSearch_id").click();
												logs.info("Click on Search button");
												wait.until(ExpectedConditions
														.invisibilityOfElementLocated(By.id("loaderDiv")));

												try {
													WebElement NoDataV = isElementPresent("NoData_className");
													wait.until(ExpectedConditions.visibilityOf(NoDataV));
													if (NoDataV.isDisplayed()) {
														logs.info("There is no Data with Search parameter");

													}

												} catch (Exception NoDatae) {
													logs.info("Data is exist with search parameter");
													wait.until(ExpectedConditions
															.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
													getScreenshot(driver, "JobEditor_Delivered");
													jobStatus = isElementPresent("TLStageLable_id").getText();
													logs.info("Job status is==" + jobStatus);

													if (jobStatus.contains("VERIFY CUSTOMER BILL")) {
														logs.info("Job is moved to Verify Customer Bill stage");
														getScreenshot(driver, "JobEditor_VerifyCustBill");

														// --Verify

														// --Click on Verify button
														WebElement Verify = isElementPresent("TLVerify_id");
														wait.until(ExpectedConditions.visibilityOf(Verify));
														act.moveToElement(Verify).build().perform();
														act.moveToElement(Verify).click().perform();
														logs.info("Clicked on Verify button");
														wait.until(ExpectedConditions
																.invisibilityOfElementLocated(By.id("loaderDiv")));

														// --Verified

														try {
															wait.until(ExpectedConditions
																	.visibilityOfElementLocated(By.id("txtContains")));
															PickUpID = getData("SearchRTE", 1, 2);
															isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
															logs.info("Entered PickUpID in basic search");

															// --Click on Search
															wait.until(ExpectedConditions
																	.elementToBeClickable(By.id("btnGlobalSearch")));
															isElementPresent("TLGlobSearch_id").click();
															logs.info("Click on Search button");
															wait.until(ExpectedConditions
																	.invisibilityOfElementLocated(By.id("loaderDiv")));

															try {
																WebElement NoDataV = isElementPresent(
																		"NoData_className");
																wait.until(ExpectedConditions.visibilityOf(NoDataV));
																if (NoDataV.isDisplayed()) {
																	logs.info("There is no Data with Search parameter");

																}

															} catch (Exception NoDataee) {
																logs.info("Data is exist with search parameter");
																wait.until(ExpectedConditions
																		.visibilityOfAllElementsLocatedBy(
																				By.id("RouteWorkFlow")));
																getScreenshot(driver, "JobEditor_Delivered");
																jobStatus = isElementPresent("TLStageLable_id")
																		.getText();
																logs.info("Job status is==" + jobStatus);

																if (jobStatus.contains("VERIFIED")) {
																	logs.info("Job is moved to VERIFIED stage");
																	getScreenshot(driver, "JobEditor_Verified");
																	WebElement EWSave = isElementPresent(
																			"TLQCExitWSave_id");
																	wait.until(ExpectedConditions.visibilityOf(EWSave));
																	act.moveToElement(EWSave).build().perform();
																	act.moveToElement(EWSave).click().perform();
																	logs.info("Clicked on Exit Without Save");
																} else {
																	logs.info("Job is not moved to VERIFIED stage");
																	jobStatus = isElementPresent("TLStageLable_id")
																			.getText();
																	logs.info("Job status is==" + jobStatus);

																	WebElement EWSave = isElementPresent(
																			"TLQCExitWSave_id");
																	wait.until(ExpectedConditions.visibilityOf(EWSave));
																	act.moveToElement(EWSave).build().perform();
																	act.moveToElement(EWSave).click().perform();
																	logs.info("Clicked on Exit Without Save");

																}

															}

															//

														} catch (Exception VerifyCBill) {
															logs.info("job is not moved to VERIFIED stage");
															jobStatus = isElementPresent("TLStageLable_id").getText();
															logs.info("Job status is==" + jobStatus);

															WebElement EWSave = isElementPresent("TLQCExitWSave_id");
															wait.until(ExpectedConditions.visibilityOf(EWSave));
															act.moveToElement(EWSave).build().perform();
															act.moveToElement(EWSave).click().perform();
															logs.info("Clicked on Exit Without Save");

														}

													} else {
														logs.info("Job is not moved to Verify Customer Bill stage");
														jobStatus = isElementPresent("TLStageLable_id").getText();
														logs.info("Job status is==" + jobStatus);

														WebElement EWSave = isElementPresent("TLQCExitWSave_id");
														wait.until(ExpectedConditions.visibilityOf(EWSave));
														act.moveToElement(EWSave).build().perform();
														act.moveToElement(EWSave).click().perform();
														logs.info("Clicked on Exit Without Save");

													}

												}

												//

											} catch (Exception VerifyCBill) {
												logs.info("job is not moved to Verify Customer Bill stage");
												jobStatus = isElementPresent("TLStageLable_id").getText();
												logs.info("Job status is==" + jobStatus);
											}

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

							} catch (Exception e) {
								logs.info("Job is moved to PENDING DELIVERY==FAIL");
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

				} catch (Exception Somethingw) {
					logs.info("Job is not moved to PU DRV CONF stage");

				}
			} else if (jobStatus.contains("PICKUP")) {

				// --PICKUP@STOP 1 OF 2 stage
				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("routetable")));
					getScreenshot(driver, "NegFlow_PickUP");
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

						// --Get ZoneID
						String ZOneID = isElementPresent("TLACPTZone_xpath").getText();
						logs.info("ZoneID of is==" + ZOneID);
						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						} else if (ZOneID.equalsIgnoreCase("PDT")) {
							ZOneID = "PST";
						}
						// --PickUp Date
						WebElement PickUpDate = isElementPresent("TLActPuDate_id");
						PickUpDate.clear();
						Date date = new Date();
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logs.info(dateFormat.format(date));
						PickUpDate.sendKeys(dateFormat.format(date));
						PickUpDate.sendKeys(Keys.TAB);
						logs.info("Entered Actual Pickup Date");

						// --Enter Act.PickUp Time
						WebElement PickUpTime = isElementPresent("TLActPUpTime_id");
						PickUpTime.clear();
						date = new Date();
						dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logs.info(dateFormat.format(date));
						wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActPuTime_0")));
						PickUpTime.sendKeys(dateFormat.format(date));
						logs.info("Entered Actual Pickup Time");

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
						getScreenshot(driver, "NegFlow_Deliver");
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);
						// --Click on ConfirmDEL button
						wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
						isElementPresent("TLConfDEL_id").click();
						logs.info("Clicked on Confirm DEL button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
							String ValMsg = isElementPresent("TLAlValidation_id").getText();
							logs.info("Validation is displayed==" + ValMsg);

							// --Get ZoneID
							String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
							logs.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							} else if (ZOneID.equalsIgnoreCase("PDT")) {
								ZOneID = "PST";
							}

							// --Delivery Date
							WebElement DelDate = isElementPresent("TLActDelDate_id");
							DelDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							DelDate.sendKeys(dateFormat.format(date));
							DelDate.sendKeys(Keys.TAB);
							logs.info("Entered Actual Delivery Date");

							// --Enter Act.DEL Time
							WebElement DelTime = isElementPresent("TLActDelTime_id");
							DelTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
							DelTime.sendKeys(dateFormat.format(date));
							logs.info("Entered Actual Delivery Time");

							// --Enter Signature
							wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
							isElementPresent("TLSign_id").clear();
							isElementPresent("TLSign_id").sendKeys("RV");
							logs.info("Entered Signature");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Click on ReRoute
							WebElement ReRoute = isElementPresent("TLReRouteCB_id");
							js.executeScript("arguments[0].click();", ReRoute);
							logs.info("Clicked on Re Route Checkbox");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Click on Confirm Del button
							isElementPresent("TLConfDEL_id").click();
							logs.info("Clicked on Confirm DEL button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						} catch (Exception NoValSign) {
							logs.info("Validation for Act.Del Time and Signature is not displayed");

						}

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
							String ValMsg = isElementPresent("TLAlValidation_id").getText();
							logs.info("Validation is displayed==" + ValMsg);

							// --Get ZoneID
							String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
							logs.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							} else if (ZOneID.equalsIgnoreCase("PDT")) {
								ZOneID = "PST";
							}

							// --Delivery Date
							WebElement DelDate = isElementPresent("TLActDelDate_id");
							DelDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							DelDate.sendKeys(dateFormat.format(date));
							DelDate.sendKeys(Keys.TAB);
							logs.info("Entered Actual Delivery Date");

							// --Enter Act.DEL Time
							WebElement DelTime = isElementPresent("TLActDelTime_id");
							DelTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
							cal.add(Calendar.MINUTE, 1);
							logs.info(dateFormat.format(cal.getTime()));
							wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
							DelTime.sendKeys(dateFormat.format(cal.getTime()));
							logs.info("Entered Actual Delivery Time");

							// --Click on Confirm Del button
							isElementPresent("TLConfDEL_id").click();
							logs.info("Clicked on Confirm DEL button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						} catch (Exception ActTimeGDelTime) {
							logs.info("Validation is not displayed="
									+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

						}

						// --PENDING DELIVERY

						try {
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
							wait.until(ExpectedConditions.visibilityOfElementLocated(
									By.xpath("//label[@id=\"lblStages\"][text()='Pending Delivery']")));
							getScreenshot(driver, "NegFlow_PendingDel");
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);

							if (jobStatus.contains("PENDING DELIVERY")) {
								logs.info("Job is moved to PENDING DELIVERY==PASS");

							}

							// --Click on ConfirmDEL button
							wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
							isElementPresent("TLConfDEL_id").click();
							logs.info("Clicked on Confirm DEL button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
								String ValMsg = isElementPresent("TLAlValidation_id").getText();
								logs.info("Validation is displayed==" + ValMsg);

								// --Get ZoneID
								String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
								logs.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								} else if (ZOneID.equalsIgnoreCase("PDT")) {
									ZOneID = "PST";
								}

								// --Delivery Date
								WebElement DelDate = isElementPresent("TLActDelDate_id");
								DelDate.clear();
								Date date = new Date();
								DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logs.info(dateFormat.format(date));
								DelDate.sendKeys(dateFormat.format(date));
								DelDate.sendKeys(Keys.TAB);
								logs.info("Entered Actual Delivery Date");

								// --Enter Act.DEL Time
								WebElement DelTime = isElementPresent("TLActDelTime_id");
								DelTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logs.info(dateFormat.format(date));
								wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
								DelTime.sendKeys(dateFormat.format(date));
								logs.info("Entered Actual Delivery Time");

								// --Enter Signature
								wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
								isElementPresent("TLSign_id").clear();
								isElementPresent("TLSign_id").sendKeys("RV");
								logs.info("Entered Signature");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Click on Confirm Del button
								isElementPresent("TLConfDEL_id").click();
								logs.info("Clicked on Confirm DEL button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								try {
									wait.until(ExpectedConditions
											.visibilityOfElementLocated(By.id("idValidationforMain")));
									ValMsg = isElementPresent("TLAlValidation_id").getText();
									logs.info("Validation is displayed==" + ValMsg);

									// --Get ZoneID
									ZOneID = isElementPresent("TLACDELZone_xpath").getText();
									logs.info("ZoneID of is==" + ZOneID);
									if (ZOneID.equalsIgnoreCase("EDT")) {
										ZOneID = "America/New_York";
									} else if (ZOneID.equalsIgnoreCase("CDT")) {
										ZOneID = "CST";
									} else if (ZOneID.equalsIgnoreCase("PDT")) {
										ZOneID = "PST";
									}

									// --Delivery Date
									DelDate = isElementPresent("TLActDelDate_id");
									DelDate.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("MM/dd/yyyy");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									logs.info(dateFormat.format(date));
									DelDate.sendKeys(dateFormat.format(date));
									DelDate.sendKeys(Keys.TAB);
									logs.info("Entered Actual Delivery Date");

									// --Enter Act.DEL Time
									DelTime = isElementPresent("TLActDelTime_id");
									DelTime.clear();
									date = new Date();
									dateFormat = new SimpleDateFormat("HH:mm");
									dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
									Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
									cal.add(Calendar.MINUTE, 1);
									logs.info(dateFormat.format(cal.getTime()));
									wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
									DelTime.sendKeys(dateFormat.format(cal.getTime()));
									logs.info("Entered Actual Delivery Time");

									// --Click on Confirm Del button
									isElementPresent("TLConfDEL_id").click();
									logs.info("Clicked on Confirm DEL button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								} catch (Exception ActTimeGDelTime) {
									logs.info("Validation is not displayed="
											+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

								}
							} catch (Exception NoValSign) {
								logs.info("Validation for Act.Del Time and Signature is not displayed");

							}
							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
								PickUpID = getData("SearchRTE", 2, 2);
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
								} catch (Exception NoData) {

									logs.info("Data is exist with search parameter");
									wait.until(ExpectedConditions
											.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
									getScreenshot(driver, "NegFlow_Delivered");
									jobStatus = isElementPresent("TLStageLable_id").getText();
									logs.info("Job status is==" + jobStatus);

									if (jobStatus.contains("DELIVERED")) {
										logs.info("Job is Delivered successfully");

										// --End Route

										// --Click on End Route
										WebElement EndR = isElementPresent("TLEndRoute_id");
										wait.until(ExpectedConditions.visibilityOf(EndR));
										act.moveToElement(EndR).build().perform();
										act.moveToElement(EndR).click().perform();
										logs.info("Clicked on End Route");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										try {
											wait.until(ExpectedConditions
													.visibilityOfAllElementsLocatedBy(By.id("errorid")));

											String Val = isElementPresent("Error_id").getText();
											logs.info("Validation is displayed==" + Val);

											if (Val.contains("Route End Date") && Val.contains("Route End Time")) {
												logs.info("Validation is displayed for Route End Date and Time==PASS");

												// --Enter Route End Date
												// --Get ZoneID
												String ZOneID = isElementPresent("TLERZone_xpath").getText();
												logs.info("ZoneID of is==" + ZOneID);
												if (ZOneID.equalsIgnoreCase("EDT")) {
													ZOneID = "America/New_York";
												} else if (ZOneID.equalsIgnoreCase("CDT")) {
													ZOneID = "CST";
												} else if (ZOneID.equalsIgnoreCase("PDT")) {
													ZOneID = "PST";
												}

												// --Route End Date
												WebElement ERDate = isElementPresent("TLERDate_id");
												ERDate.clear();
												Date date = new Date();
												DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
												dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
												logs.info(dateFormat.format(date));
												ERDate.sendKeys(dateFormat.format(date));
												ERDate.sendKeys(Keys.TAB);
												logs.info("Entered Actual Route End Date");

												// --Route End Time
												WebElement ERTime = isElementPresent("TLERTime_id");
												ERTime.clear();
												date = new Date();
												dateFormat = new SimpleDateFormat("HH:mm");
												dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
												Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
												cal.add(Calendar.MINUTE, 1);
												logs.info(dateFormat.format(cal.getTime()));
												wait.until(ExpectedConditions
														.elementToBeClickable(By.id("txtActualTime")));
												ERTime.sendKeys(dateFormat.format(cal.getTime()));
												logs.info("Entered Actual Route End Time");

												// --Click on End Route
												EndR = isElementPresent("TLEndRoute_id");
												wait.until(ExpectedConditions.visibilityOf(EndR));
												act.moveToElement(EndR).build().perform();
												act.moveToElement(EndR).click().perform();
												logs.info("Clicked on End Route");
												wait.until(ExpectedConditions
														.invisibilityOfElementLocated(By.id("loaderDiv")));

											} else {
												logs.info(
														"Validation is not displayed for Route End Date and Time==FAIL");

												WebElement EWSave = isElementPresent("TLQCExitWSave_id");
												wait.until(ExpectedConditions.visibilityOf(EWSave));
												act.moveToElement(EWSave).build().perform();
												act.moveToElement(EWSave).click().perform();
												logs.info("Clicked on Exit Without Save");
											}

										} catch (Exception EndRoute) {
											logs.info("Validation is not displayed for Route End Date and Time==FAIL");

										}
										// --Verify Customer Bill

										try {
											wait.until(ExpectedConditions
													.visibilityOfElementLocated(By.id("txtContains")));
											PickUpID = getData("SearchRTE", 1, 2);
											isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
											logs.info("Entered PickUpID in basic search");

											// --Click on Search
											wait.until(
													ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
											isElementPresent("TLGlobSearch_id").click();
											logs.info("Click on Search button");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											try {
												WebElement NoDataV = isElementPresent("NoData_className");
												wait.until(ExpectedConditions.visibilityOf(NoDataV));
												if (NoDataV.isDisplayed()) {
													logs.info("There is no Data with Search parameter");

												}

											} catch (Exception NoDatae) {
												logs.info("Data is exist with search parameter");
												wait.until(ExpectedConditions
														.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
												getScreenshot(driver, "JobEditor_Delivered");
												jobStatus = isElementPresent("TLStageLable_id").getText();
												logs.info("Job status is==" + jobStatus);

												if (jobStatus.contains("VERIFY CUSTOMER BILL")) {
													logs.info("Job is moved to Verify Customer Bill stage");
													getScreenshot(driver, "JobEditor_VerifyCustBill");

													// --Verify

													// --Click on Verify button
													WebElement Verify = isElementPresent("TLVerify_id");
													wait.until(ExpectedConditions.visibilityOf(Verify));
													act.moveToElement(Verify).build().perform();
													act.moveToElement(Verify).click().perform();
													logs.info("Clicked on Verify button");
													wait.until(ExpectedConditions
															.invisibilityOfElementLocated(By.id("loaderDiv")));

													// --Verified

													try {
														wait.until(ExpectedConditions
																.visibilityOfElementLocated(By.id("txtContains")));
														PickUpID = getData("SearchRTE", 1, 2);
														isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
														logs.info("Entered PickUpID in basic search");

														// --Click on Search
														wait.until(ExpectedConditions
																.elementToBeClickable(By.id("btnGlobalSearch")));
														isElementPresent("TLGlobSearch_id").click();
														logs.info("Click on Search button");
														wait.until(ExpectedConditions
																.invisibilityOfElementLocated(By.id("loaderDiv")));

														try {
															WebElement NoDataV = isElementPresent("NoData_className");
															wait.until(ExpectedConditions.visibilityOf(NoDataV));
															if (NoDataV.isDisplayed()) {
																logs.info("There is no Data with Search parameter");

															}

														} catch (Exception NoDataee) {
															logs.info("Data is exist with search parameter");
															wait.until(
																	ExpectedConditions.visibilityOfAllElementsLocatedBy(
																			By.id("RouteWorkFlow")));
															getScreenshot(driver, "JobEditor_Delivered");
															jobStatus = isElementPresent("TLStageLable_id").getText();
															logs.info("Job status is==" + jobStatus);

															if (jobStatus.contains("VERIFIED")) {
																logs.info("Job is moved to VERIFIED stage");
																getScreenshot(driver, "JobEditor_Verified");
																WebElement EWSave = isElementPresent(
																		"TLQCExitWSave_id");
																wait.until(ExpectedConditions.visibilityOf(EWSave));
																act.moveToElement(EWSave).build().perform();
																act.moveToElement(EWSave).click().perform();
																logs.info("Clicked on Exit Without Save");
															} else {
																logs.info("Job is not moved to VERIFIED stage");
																jobStatus = isElementPresent("TLStageLable_id")
																		.getText();
																logs.info("Job status is==" + jobStatus);

																WebElement EWSave = isElementPresent(
																		"TLQCExitWSave_id");
																wait.until(ExpectedConditions.visibilityOf(EWSave));
																act.moveToElement(EWSave).build().perform();
																act.moveToElement(EWSave).click().perform();
																logs.info("Clicked on Exit Without Save");

															}

														}

														//

													} catch (Exception VerifyCBill) {
														logs.info("job is not moved to VERIFIED stage");
														jobStatus = isElementPresent("TLStageLable_id").getText();
														logs.info("Job status is==" + jobStatus);

														WebElement EWSave = isElementPresent("TLQCExitWSave_id");
														wait.until(ExpectedConditions.visibilityOf(EWSave));
														act.moveToElement(EWSave).build().perform();
														act.moveToElement(EWSave).click().perform();
														logs.info("Clicked on Exit Without Save");

													}

												} else {
													logs.info("Job is not moved to Verify Customer Bill stage");
													jobStatus = isElementPresent("TLStageLable_id").getText();
													logs.info("Job status is==" + jobStatus);

													WebElement EWSave = isElementPresent("TLQCExitWSave_id");
													wait.until(ExpectedConditions.visibilityOf(EWSave));
													act.moveToElement(EWSave).build().perform();
													act.moveToElement(EWSave).click().perform();
													logs.info("Clicked on Exit Without Save");

												}

											}

											//

										} catch (Exception VerifyCBill) {
											logs.info("job is not moved to Verify Customer Bill stage");
											jobStatus = isElementPresent("TLStageLable_id").getText();
											logs.info("Job status is==" + jobStatus);
										}

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

						} catch (Exception e) {
							logs.info("Job is moved to PENDING DELIVERY==FAIL");
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

			} else if (jobStatus.equalsIgnoreCase("DELIVER@")) {

				// --DELIVER@STOP 2 OF 2 stage
				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("routetable")));
					getScreenshot(driver, "NegFlow_Deliver");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);
					// --Click on ConfirmDEL button
					wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
					isElementPresent("TLConfDEL_id").click();
					logs.info("Clicked on Confirm DEL button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
						String ValMsg = isElementPresent("TLAlValidation_id").getText();
						logs.info("Validation is displayed==" + ValMsg);

						// --Get ZoneID
						String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
						logs.info("ZoneID of is==" + ZOneID);
						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						} else if (ZOneID.equalsIgnoreCase("PDT")) {
							ZOneID = "PST";
						}

						// --Delivery Date
						WebElement DelDate = isElementPresent("TLActDelDate_id");
						DelDate.clear();
						Date date = new Date();
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logs.info(dateFormat.format(date));
						DelDate.sendKeys(dateFormat.format(date));
						DelDate.sendKeys(Keys.TAB);
						logs.info("Entered Actual Delivery Date");

						// --Enter Act.DEL Time
						WebElement DelTime = isElementPresent("TLActDelTime_id");
						DelTime.clear();
						date = new Date();
						dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logs.info(dateFormat.format(date));
						wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
						DelTime.sendKeys(dateFormat.format(date));
						logs.info("Entered Actual Delivery Time");

						// --Enter Signature
						wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
						isElementPresent("TLSign_id").clear();
						isElementPresent("TLSign_id").sendKeys("RV");
						logs.info("Entered Signature");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Click on ReRoute
						WebElement ReRoute = isElementPresent("TLReRouteCB_id");
						js.executeScript("arguments[0].click();", ReRoute);
						logs.info("Clicked on Re Route Checkbox");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Click on Confirm Del button
						isElementPresent("TLConfDEL_id").click();
						logs.info("Clicked on Confirm DEL button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					} catch (Exception NoValSign) {
						logs.info("Validation for Act.Del Time and Signature is not displayed");

					}

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
						String ValMsg = isElementPresent("TLAlValidation_id").getText();
						logs.info("Validation is displayed==" + ValMsg);

						// --Get ZoneID
						String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
						logs.info("ZoneID of is==" + ZOneID);
						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						} else if (ZOneID.equalsIgnoreCase("PDT")) {
							ZOneID = "PST";
						}

						// --Delivery Date
						WebElement DelDate = isElementPresent("TLActDelDate_id");
						DelDate.clear();
						Date date = new Date();
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logs.info(dateFormat.format(date));
						DelDate.sendKeys(dateFormat.format(date));
						DelDate.sendKeys(Keys.TAB);
						logs.info("Entered Actual Delivery Date");

						// --Enter Act.DEL Time
						WebElement DelTime = isElementPresent("TLActDelTime_id");
						DelTime.clear();
						date = new Date();
						dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
						cal.add(Calendar.MINUTE, 1);
						logs.info(dateFormat.format(cal.getTime()));
						wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
						DelTime.sendKeys(dateFormat.format(cal.getTime()));
						logs.info("Entered Actual Delivery Time");

						// --Click on Confirm Del button
						isElementPresent("TLConfDEL_id").click();
						logs.info("Clicked on Confirm DEL button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					} catch (Exception ActTimeGDelTime) {
						logs.info("Validation is not displayed="
								+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

					}

					// --PENDING DELIVERY

					try {
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
						wait.until(ExpectedConditions.visibilityOfElementLocated(
								By.xpath("//label[@id=\"lblStages\"][text()='Pending Delivery']")));
						getScreenshot(driver, "NegFlow_PendingDel");
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);

						if (jobStatus.contains("PENDING DELIVERY")) {
							logs.info("Job is moved to PENDING DELIVERY==PASS");

						}

						// --Click on ConfirmDEL button
						wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
						isElementPresent("TLConfDEL_id").click();
						logs.info("Clicked on Confirm DEL button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
							String ValMsg = isElementPresent("TLAlValidation_id").getText();
							logs.info("Validation is displayed==" + ValMsg);

							// --Get ZoneID
							String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
							logs.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							} else if (ZOneID.equalsIgnoreCase("PDT")) {
								ZOneID = "PST";
							}

							// --Delivery Date
							WebElement DelDate = isElementPresent("TLActDelDate_id");
							DelDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							DelDate.sendKeys(dateFormat.format(date));
							DelDate.sendKeys(Keys.TAB);
							logs.info("Entered Actual Delivery Date");

							// --Enter Act.DEL Time
							WebElement DelTime = isElementPresent("TLActDelTime_id");
							DelTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
							DelTime.sendKeys(dateFormat.format(date));
							logs.info("Entered Actual Delivery Time");

							// --Enter Signature
							wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
							isElementPresent("TLSign_id").clear();
							isElementPresent("TLSign_id").sendKeys("RV");
							logs.info("Entered Signature");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							// --Click on Confirm Del button
							isElementPresent("TLConfDEL_id").click();
							logs.info("Clicked on Confirm DEL button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							try {
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
								ValMsg = isElementPresent("TLAlValidation_id").getText();
								logs.info("Validation is displayed==" + ValMsg);

								// --Get ZoneID
								ZOneID = isElementPresent("TLACDELZone_xpath").getText();
								logs.info("ZoneID of is==" + ZOneID);
								if (ZOneID.equalsIgnoreCase("EDT")) {
									ZOneID = "America/New_York";
								} else if (ZOneID.equalsIgnoreCase("CDT")) {
									ZOneID = "CST";
								} else if (ZOneID.equalsIgnoreCase("PDT")) {
									ZOneID = "PST";
								}

								// --Delivery Date
								DelDate = isElementPresent("TLActDelDate_id");
								DelDate.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("MM/dd/yyyy");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								logs.info(dateFormat.format(date));
								DelDate.sendKeys(dateFormat.format(date));
								DelDate.sendKeys(Keys.TAB);
								logs.info("Entered Actual Delivery Date");

								// --Enter Act.DEL Time
								DelTime = isElementPresent("TLActDelTime_id");
								DelTime.clear();
								date = new Date();
								dateFormat = new SimpleDateFormat("HH:mm");
								dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
								Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
								cal.add(Calendar.MINUTE, 1);
								logs.info(dateFormat.format(cal.getTime()));
								wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
								DelTime.sendKeys(dateFormat.format(cal.getTime()));
								logs.info("Entered Actual Delivery Time");

								// --Click on Confirm Del button
								isElementPresent("TLConfDEL_id").click();
								logs.info("Clicked on Confirm DEL button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

							} catch (Exception ActTimeGDelTime) {
								logs.info("Validation is not displayed="
										+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

							}
						} catch (Exception NoValSign) {
							logs.info("Validation for Act.Del Time and Signature is not displayed");

						}
						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
							PickUpID = getData("SearchRTE", 2, 2);
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
							} catch (Exception NoData) {

								logs.info("Data is exist with search parameter");
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
								getScreenshot(driver, "NegFlow_Delivered");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

								if (jobStatus.contains("DELIVERED")) {
									logs.info("Job is Delivered successfully");

									// --End Route

									// --Click on End Route
									WebElement EndR = isElementPresent("TLEndRoute_id");
									wait.until(ExpectedConditions.visibilityOf(EndR));
									act.moveToElement(EndR).build().perform();
									act.moveToElement(EndR).click().perform();
									logs.info("Clicked on End Route");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									try {
										wait.until(
												ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));

										String Val = isElementPresent("Error_id").getText();
										logs.info("Validation is displayed==" + Val);

										if (Val.contains("Route End Date") && Val.contains("Route End Time")) {
											logs.info("Validation is displayed for Route End Date and Time==PASS");

											// --Enter Route End Date
											// --Get ZoneID
											String ZOneID = isElementPresent("TLERZone_xpath").getText();
											logs.info("ZoneID of is==" + ZOneID);
											if (ZOneID.equalsIgnoreCase("EDT")) {
												ZOneID = "America/New_York";
											} else if (ZOneID.equalsIgnoreCase("CDT")) {
												ZOneID = "CST";
											} else if (ZOneID.equalsIgnoreCase("PDT")) {
												ZOneID = "PST";
											}

											// --Route End Date
											WebElement ERDate = isElementPresent("TLERDate_id");
											ERDate.clear();
											Date date = new Date();
											DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
											dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
											logs.info(dateFormat.format(date));
											ERDate.sendKeys(dateFormat.format(date));
											ERDate.sendKeys(Keys.TAB);
											logs.info("Entered Actual Route End Date");

											// --Route End Time
											WebElement ERTime = isElementPresent("TLERTime_id");
											ERTime.clear();
											date = new Date();
											dateFormat = new SimpleDateFormat("HH:mm");
											dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
											Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
											cal.add(Calendar.MINUTE, 1);
											logs.info(dateFormat.format(cal.getTime()));
											wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActualTime")));
											ERTime.sendKeys(dateFormat.format(cal.getTime()));
											logs.info("Entered Actual Route End Time");

											// --Click on End Route
											EndR = isElementPresent("TLEndRoute_id");
											wait.until(ExpectedConditions.visibilityOf(EndR));
											act.moveToElement(EndR).build().perform();
											act.moveToElement(EndR).click().perform();
											logs.info("Clicked on End Route");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

										} else {
											logs.info("Validation is not displayed for Route End Date and Time==FAIL");

											WebElement EWSave = isElementPresent("TLQCExitWSave_id");
											wait.until(ExpectedConditions.visibilityOf(EWSave));
											act.moveToElement(EWSave).build().perform();
											act.moveToElement(EWSave).click().perform();
											logs.info("Clicked on Exit Without Save");
										}

									} catch (Exception EndRoute) {
										logs.info("Validation is not displayed for Route End Date and Time==FAIL");

									}
									// --Verify Customer Bill

									try {
										wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
										PickUpID = getData("SearchRTE", 1, 2);
										isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
										logs.info("Entered PickUpID in basic search");

										// --Click on Search
										wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
										isElementPresent("TLGlobSearch_id").click();
										logs.info("Click on Search button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										try {
											WebElement NoDataV = isElementPresent("NoData_className");
											wait.until(ExpectedConditions.visibilityOf(NoDataV));
											if (NoDataV.isDisplayed()) {
												logs.info("There is no Data with Search parameter");

											}

										} catch (Exception NoDatae) {
											logs.info("Data is exist with search parameter");
											wait.until(ExpectedConditions
													.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
											getScreenshot(driver, "JobEditor_Delivered");
											jobStatus = isElementPresent("TLStageLable_id").getText();
											logs.info("Job status is==" + jobStatus);

											if (jobStatus.contains("VERIFY CUSTOMER BILL")) {
												logs.info("Job is moved to Verify Customer Bill stage");
												getScreenshot(driver, "JobEditor_VerifyCustBill");

												// --Verify

												// --Click on Verify button
												WebElement Verify = isElementPresent("TLVerify_id");
												wait.until(ExpectedConditions.visibilityOf(Verify));
												act.moveToElement(Verify).build().perform();
												act.moveToElement(Verify).click().perform();
												logs.info("Clicked on Verify button");
												wait.until(ExpectedConditions
														.invisibilityOfElementLocated(By.id("loaderDiv")));

												// --Verified

												try {
													wait.until(ExpectedConditions
															.visibilityOfElementLocated(By.id("txtContains")));
													PickUpID = getData("SearchRTE", 1, 2);
													isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
													logs.info("Entered PickUpID in basic search");

													// --Click on Search
													wait.until(ExpectedConditions
															.elementToBeClickable(By.id("btnGlobalSearch")));
													isElementPresent("TLGlobSearch_id").click();
													logs.info("Click on Search button");
													wait.until(ExpectedConditions
															.invisibilityOfElementLocated(By.id("loaderDiv")));

													try {
														WebElement NoDataV = isElementPresent("NoData_className");
														wait.until(ExpectedConditions.visibilityOf(NoDataV));
														if (NoDataV.isDisplayed()) {
															logs.info("There is no Data with Search parameter");

														}

													} catch (Exception NoDataee) {
														logs.info("Data is exist with search parameter");
														wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
																By.id("RouteWorkFlow")));
														getScreenshot(driver, "JobEditor_Delivered");
														jobStatus = isElementPresent("TLStageLable_id").getText();
														logs.info("Job status is==" + jobStatus);

														if (jobStatus.contains("VERIFIED")) {
															logs.info("Job is moved to VERIFIED stage");
															getScreenshot(driver, "JobEditor_Verified");
															WebElement EWSave = isElementPresent("TLQCExitWSave_id");
															wait.until(ExpectedConditions.visibilityOf(EWSave));
															act.moveToElement(EWSave).build().perform();
															act.moveToElement(EWSave).click().perform();
															logs.info("Clicked on Exit Without Save");
														} else {
															logs.info("Job is not moved to VERIFIED stage");
															jobStatus = isElementPresent("TLStageLable_id").getText();
															logs.info("Job status is==" + jobStatus);

															WebElement EWSave = isElementPresent("TLQCExitWSave_id");
															wait.until(ExpectedConditions.visibilityOf(EWSave));
															act.moveToElement(EWSave).build().perform();
															act.moveToElement(EWSave).click().perform();
															logs.info("Clicked on Exit Without Save");

														}

													}

													//

												} catch (Exception VerifyCBill) {
													logs.info("job is not moved to VERIFIED stage");
													jobStatus = isElementPresent("TLStageLable_id").getText();
													logs.info("Job status is==" + jobStatus);

													WebElement EWSave = isElementPresent("TLQCExitWSave_id");
													wait.until(ExpectedConditions.visibilityOf(EWSave));
													act.moveToElement(EWSave).build().perform();
													act.moveToElement(EWSave).click().perform();
													logs.info("Clicked on Exit Without Save");

												}

											} else {
												logs.info("Job is not moved to Verify Customer Bill stage");
												jobStatus = isElementPresent("TLStageLable_id").getText();
												logs.info("Job status is==" + jobStatus);

												WebElement EWSave = isElementPresent("TLQCExitWSave_id");
												wait.until(ExpectedConditions.visibilityOf(EWSave));
												act.moveToElement(EWSave).build().perform();
												act.moveToElement(EWSave).click().perform();
												logs.info("Clicked on Exit Without Save");

											}

										}

										//

									} catch (Exception VerifyCBill) {
										logs.info("job is not moved to Verify Customer Bill stage");
										jobStatus = isElementPresent("TLStageLable_id").getText();
										logs.info("Job status is==" + jobStatus);
									}

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

					} catch (Exception e) {
						logs.info("Job is moved to PENDING DELIVERY==FAIL");
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);

					}

				} catch (Exception Deliver) {
					logs.info("Stage is not Deliver");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);
				}

			} else if (jobStatus.contains("PENDING DELIVERY")) {
				try {
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
					wait.until(ExpectedConditions.visibilityOfElementLocated(
							By.xpath("//label[@id=\"lblStages\"][text()='Pending Delivery']")));
					getScreenshot(driver, "NegFlow_PendingDel");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);

					if (jobStatus.contains("PENDING DELIVERY")) {
						logs.info("Job is moved to PENDING DELIVERY==PASS");

					}

					// --Click on ConfirmDEL button
					wait.until(ExpectedConditions.elementToBeClickable(By.id("PUDLStopsWhiteTick")));
					isElementPresent("TLConfDEL_id").click();
					logs.info("Clicked on Confirm DEL button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
						String ValMsg = isElementPresent("TLAlValidation_id").getText();
						logs.info("Validation is displayed==" + ValMsg);

						// --Get ZoneID
						String ZOneID = isElementPresent("TLACDELZone_xpath").getText();
						logs.info("ZoneID of is==" + ZOneID);
						if (ZOneID.equalsIgnoreCase("EDT")) {
							ZOneID = "America/New_York";
						} else if (ZOneID.equalsIgnoreCase("CDT")) {
							ZOneID = "CST";
						} else if (ZOneID.equalsIgnoreCase("PDT")) {
							ZOneID = "PST";
						}
						// --Delivery Date
						WebElement DelDate = isElementPresent("TLActDelDate_id");
						DelDate.clear();
						Date date = new Date();
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logs.info(dateFormat.format(date));
						DelDate.sendKeys(dateFormat.format(date));
						DelDate.sendKeys(Keys.TAB);
						logs.info("Entered Actual Delivery Date");

						// --Enter Act.DEL Time
						WebElement DelTime = isElementPresent("TLActDelTime_id");
						DelTime.clear();
						date = new Date();
						dateFormat = new SimpleDateFormat("HH:mm");
						dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
						logs.info(dateFormat.format(date));
						wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
						DelTime.sendKeys(dateFormat.format(date));
						logs.info("Entered Actual Delivery Time");

						// --Enter Signature
						wait.until(ExpectedConditions.elementToBeClickable(By.id("txtsign")));
						isElementPresent("TLSign_id").clear();
						isElementPresent("TLSign_id").sendKeys("RV");
						logs.info("Entered Signature");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						// --Click on Confirm Del button
						isElementPresent("TLConfDEL_id").click();
						logs.info("Clicked on Confirm DEL button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						try {
							wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("idValidationforMain")));
							ValMsg = isElementPresent("TLAlValidation_id").getText();
							logs.info("Validation is displayed==" + ValMsg);

							// --Get ZoneID
							ZOneID = isElementPresent("TLACDELZone_xpath").getText();
							logs.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							} else if (ZOneID.equalsIgnoreCase("PDT")) {
								ZOneID = "PST";
							}

							// --Delivery Date
							DelDate = isElementPresent("TLActDelDate_id");
							DelDate.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							DelDate.sendKeys(dateFormat.format(date));
							DelDate.sendKeys(Keys.TAB);
							logs.info("Entered Actual Delivery Date");

							// --Enter Act.DEL Time
							DelTime = isElementPresent("TLActDelTime_id");
							DelTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
							cal.add(Calendar.MINUTE, 1);
							logs.info(dateFormat.format(cal.getTime()));
							wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActDlTime_0")));
							DelTime.sendKeys(dateFormat.format(cal.getTime()));
							logs.info("Entered Actual Delivery Time");

							// --Click on Confirm Del button
							isElementPresent("TLConfDEL_id").click();
							logs.info("Clicked on Confirm DEL button");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						} catch (Exception ActTimeGDelTime) {
							logs.info("Validation is not displayed="
									+ "Actual Delivery Datetime cannot be less than or equal to Actual Pickup Datetime.");

						}
					} catch (Exception NoValSign) {
						logs.info("Validation for Act.Del Time and Signature is not displayed");

					}
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
						PickUpID = getData("SearchRTE", 2, 2);
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
						} catch (Exception NoData) {

							logs.info("Data is exist with search parameter");
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
							getScreenshot(driver, "NegFlow_Delivered");
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);

							if (jobStatus.contains("DELIVERED")) {
								logs.info("Job is Delivered successfully");

								// --End Route

								// --Click on End Route
								WebElement EndR = isElementPresent("TLEndRoute_id");
								wait.until(ExpectedConditions.visibilityOf(EndR));
								act.moveToElement(EndR).build().perform();
								act.moveToElement(EndR).click().perform();
								logs.info("Clicked on End Route");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								try {
									wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));

									String Val = isElementPresent("Error_id").getText();
									logs.info("Validation is displayed==" + Val);

									if (Val.contains("Route End Date") && Val.contains("Route End Time")) {
										logs.info("Validation is displayed for Route End Date and Time==PASS");

										// --Enter Route End Date
										// --Get ZoneID
										String ZOneID = isElementPresent("TLERZone_xpath").getText();
										logs.info("ZoneID of is==" + ZOneID);
										if (ZOneID.equalsIgnoreCase("EDT")) {
											ZOneID = "America/New_York";
										} else if (ZOneID.equalsIgnoreCase("CDT")) {
											ZOneID = "CST";
										} else if (ZOneID.equalsIgnoreCase("PDT")) {
											ZOneID = "PST";
										}

										// --Route End Date
										WebElement ERDate = isElementPresent("TLERDate_id");
										ERDate.clear();
										Date date = new Date();
										DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										logs.info(dateFormat.format(date));
										ERDate.sendKeys(dateFormat.format(date));
										ERDate.sendKeys(Keys.TAB);
										logs.info("Entered Actual Route End Date");

										// --Route End Time
										WebElement ERTime = isElementPresent("TLERTime_id");
										ERTime.clear();
										date = new Date();
										dateFormat = new SimpleDateFormat("HH:mm");
										dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
										Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
										cal.add(Calendar.MINUTE, 1);
										logs.info(dateFormat.format(cal.getTime()));
										wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActualTime")));
										ERTime.sendKeys(dateFormat.format(cal.getTime()));
										logs.info("Entered Actual Route End Time");

										// --Click on End Route
										EndR = isElementPresent("TLEndRoute_id");
										wait.until(ExpectedConditions.visibilityOf(EndR));
										act.moveToElement(EndR).build().perform();
										act.moveToElement(EndR).click().perform();
										logs.info("Clicked on End Route");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									} else {
										logs.info("Validation is not displayed for Route End Date and Time==FAIL");

										WebElement EWSave = isElementPresent("TLQCExitWSave_id");
										wait.until(ExpectedConditions.visibilityOf(EWSave));
										act.moveToElement(EWSave).build().perform();
										act.moveToElement(EWSave).click().perform();
										logs.info("Clicked on Exit Without Save");
									}

								} catch (Exception EndRoute) {
									logs.info("Validation is not displayed for Route End Date and Time==FAIL");

								}
								// --Verify Customer Bill

								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
									PickUpID = getData("SearchRTE", 1, 2);
									isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
									logs.info("Entered PickUpID in basic search");

									// --Click on Search
									wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
									isElementPresent("TLGlobSearch_id").click();
									logs.info("Click on Search button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									try {
										WebElement NoDataV = isElementPresent("NoData_className");
										wait.until(ExpectedConditions.visibilityOf(NoDataV));
										if (NoDataV.isDisplayed()) {
											logs.info("There is no Data with Search parameter");

										}

									} catch (Exception NoDatae) {
										logs.info("Data is exist with search parameter");
										wait.until(ExpectedConditions
												.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
										getScreenshot(driver, "JobEditor_Delivered");
										jobStatus = isElementPresent("TLStageLable_id").getText();
										logs.info("Job status is==" + jobStatus);

										if (jobStatus.contains("VERIFY CUSTOMER BILL")) {
											logs.info("Job is moved to Verify Customer Bill stage");
											getScreenshot(driver, "JobEditor_VerifyCustBill");

											// --Verify

											// --Click on Verify button
											WebElement Verify = isElementPresent("TLVerify_id");
											wait.until(ExpectedConditions.visibilityOf(Verify));
											act.moveToElement(Verify).build().perform();
											act.moveToElement(Verify).click().perform();
											logs.info("Clicked on Verify button");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											// --Verified

											try {
												wait.until(ExpectedConditions
														.visibilityOfElementLocated(By.id("txtContains")));
												PickUpID = getData("SearchRTE", 1, 2);
												isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
												logs.info("Entered PickUpID in basic search");

												// --Click on Search
												wait.until(ExpectedConditions
														.elementToBeClickable(By.id("btnGlobalSearch")));
												isElementPresent("TLGlobSearch_id").click();
												logs.info("Click on Search button");
												wait.until(ExpectedConditions
														.invisibilityOfElementLocated(By.id("loaderDiv")));

												try {
													WebElement NoDataV = isElementPresent("NoData_className");
													wait.until(ExpectedConditions.visibilityOf(NoDataV));
													if (NoDataV.isDisplayed()) {
														logs.info("There is no Data with Search parameter");

													}

												} catch (Exception NoDataee) {
													logs.info("Data is exist with search parameter");
													wait.until(ExpectedConditions
															.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
													getScreenshot(driver, "JobEditor_Delivered");
													jobStatus = isElementPresent("TLStageLable_id").getText();
													logs.info("Job status is==" + jobStatus);

													if (jobStatus.contains("VERIFIED")) {
														logs.info("Job is moved to VERIFIED stage");
														getScreenshot(driver, "JobEditor_Verified");
														WebElement EWSave = isElementPresent("TLQCExitWSave_id");
														wait.until(ExpectedConditions.visibilityOf(EWSave));
														act.moveToElement(EWSave).build().perform();
														act.moveToElement(EWSave).click().perform();
														logs.info("Clicked on Exit Without Save");
													} else {
														logs.info("Job is not moved to VERIFIED stage");
														jobStatus = isElementPresent("TLStageLable_id").getText();
														logs.info("Job status is==" + jobStatus);

														WebElement EWSave = isElementPresent("TLQCExitWSave_id");
														wait.until(ExpectedConditions.visibilityOf(EWSave));
														act.moveToElement(EWSave).build().perform();
														act.moveToElement(EWSave).click().perform();
														logs.info("Clicked on Exit Without Save");

													}

												}

												//

											} catch (Exception VerifyCBill) {
												logs.info("job is not moved to VERIFIED stage");
												jobStatus = isElementPresent("TLStageLable_id").getText();
												logs.info("Job status is==" + jobStatus);

												WebElement EWSave = isElementPresent("TLQCExitWSave_id");
												wait.until(ExpectedConditions.visibilityOf(EWSave));
												act.moveToElement(EWSave).build().perform();
												act.moveToElement(EWSave).click().perform();
												logs.info("Clicked on Exit Without Save");

											}

										} else {
											logs.info("Job is not moved to Verify Customer Bill stage");
											jobStatus = isElementPresent("TLStageLable_id").getText();
											logs.info("Job status is==" + jobStatus);

											WebElement EWSave = isElementPresent("TLQCExitWSave_id");
											wait.until(ExpectedConditions.visibilityOf(EWSave));
											act.moveToElement(EWSave).build().perform();
											act.moveToElement(EWSave).click().perform();
											logs.info("Clicked on Exit Without Save");

										}

									}

									//

								} catch (Exception VerifyCBill) {
									logs.info("job is not moved to Verify Customer Bill stage");
									jobStatus = isElementPresent("TLStageLable_id").getText();
									logs.info("Job status is==" + jobStatus);
								}

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

				} catch (Exception e) {
					logs.info("Job is moved to PENDING DELIVERY==FAIL");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);

				}
			} else if (jobStatus.contains("DELIVERED")) {
				logs.info("Data is exist with search parameter");
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
				getScreenshot(driver, "JobEditor_Delivered");
				jobStatus = isElementPresent("TLStageLable_id").getText();
				logs.info("Job status is==" + jobStatus);

				if (jobStatus.contains("DELIVERED")) {
					logs.info("Job is Delivered successfully");

					// --End Route

					// --Click on End Route
					WebElement EndR = isElementPresent("TLEndRoute_id");
					wait.until(ExpectedConditions.visibilityOf(EndR));
					act.moveToElement(EndR).build().perform();
					act.moveToElement(EndR).click().perform();
					logs.info("Clicked on End Route");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {
						wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("errorid")));

						String Val = isElementPresent("Error_id").getText();
						logs.info("Validation is displayed==" + Val);

						if (Val.contains("Route End Date") && Val.contains("Route End Time")) {
							logs.info("Validation is displayed for Route End Date and Time==PASS");

							// --Enter Route End Date
							// --Get ZoneID
							String ZOneID = isElementPresent("TLERZone_xpath").getText();
							logs.info("ZoneID of is==" + ZOneID);
							if (ZOneID.equalsIgnoreCase("EDT")) {
								ZOneID = "America/New_York";
							} else if (ZOneID.equalsIgnoreCase("CDT")) {
								ZOneID = "CST";
							} else if (ZOneID.equalsIgnoreCase("PDT")) {
								ZOneID = "PST";
							}

							// --Route End Date
							WebElement ERDate = isElementPresent("TLERDate_id");
							ERDate.clear();
							Date date = new Date();
							DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							logs.info(dateFormat.format(date));
							ERDate.sendKeys(dateFormat.format(date));
							ERDate.sendKeys(Keys.TAB);
							logs.info("Entered Actual Route End Date");

							// --Route End Time
							WebElement ERTime = isElementPresent("TLERTime_id");
							ERTime.clear();
							date = new Date();
							dateFormat = new SimpleDateFormat("HH:mm");
							dateFormat.setTimeZone(TimeZone.getTimeZone(ZOneID));
							Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(ZOneID));
							cal.add(Calendar.MINUTE, 1);
							logs.info(dateFormat.format(cal.getTime()));
							wait.until(ExpectedConditions.elementToBeClickable(By.id("txtActualTime")));
							ERTime.sendKeys(dateFormat.format(cal.getTime()));
							logs.info("Entered Actual Route End Time");

							// --Click on End Route
							EndR = isElementPresent("TLEndRoute_id");
							wait.until(ExpectedConditions.visibilityOf(EndR));
							act.moveToElement(EndR).build().perform();
							act.moveToElement(EndR).click().perform();
							logs.info("Clicked on End Route");
							wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						} else {
							logs.info("Validation is not displayed for Route End Date and Time==FAIL");

							WebElement EWSave = isElementPresent("TLQCExitWSave_id");
							wait.until(ExpectedConditions.visibilityOf(EWSave));
							act.moveToElement(EWSave).build().perform();
							act.moveToElement(EWSave).click().perform();
							logs.info("Clicked on Exit Without Save");
						}

					} catch (Exception EndRoute) {
						logs.info("Validation is not displayed for Route End Date and Time==FAIL");

					}
					// --Verify Customer Bill

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
						PickUpID = getData("SearchRTE", 1, 2);
						isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
						logs.info("Entered PickUpID in basic search");

						// --Click on Search
						wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
						isElementPresent("TLGlobSearch_id").click();
						logs.info("Click on Search button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						try {
							WebElement NoDataV = isElementPresent("NoData_className");
							wait.until(ExpectedConditions.visibilityOf(NoDataV));
							if (NoDataV.isDisplayed()) {
								logs.info("There is no Data with Search parameter");

							}

						} catch (Exception NoDatae) {
							logs.info("Data is exist with search parameter");
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
							getScreenshot(driver, "JobEditor_Delivered");
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);

							if (jobStatus.contains("VERIFY CUSTOMER BILL")) {
								logs.info("Job is moved to Verify Customer Bill stage");
								getScreenshot(driver, "JobEditor_VerifyCustBill");

								// --Verify

								// --Click on Verify button
								WebElement Verify = isElementPresent("TLVerify_id");
								wait.until(ExpectedConditions.visibilityOf(Verify));
								act.moveToElement(Verify).build().perform();
								act.moveToElement(Verify).click().perform();
								logs.info("Clicked on Verify button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Verified

								try {
									wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
									PickUpID = getData("SearchRTE", 1, 2);
									isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
									logs.info("Entered PickUpID in basic search");

									// --Click on Search
									wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
									isElementPresent("TLGlobSearch_id").click();
									logs.info("Click on Search button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									try {
										WebElement NoDataV = isElementPresent("NoData_className");
										wait.until(ExpectedConditions.visibilityOf(NoDataV));
										if (NoDataV.isDisplayed()) {
											logs.info("There is no Data with Search parameter");

										}

									} catch (Exception NoDataee) {
										logs.info("Data is exist with search parameter");
										wait.until(ExpectedConditions
												.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
										getScreenshot(driver, "JobEditor_Delivered");
										jobStatus = isElementPresent("TLStageLable_id").getText();
										logs.info("Job status is==" + jobStatus);

										if (jobStatus.contains("VERIFIED")) {
											logs.info("Job is moved to VERIFIED stage");
											getScreenshot(driver, "JobEditor_Verified");
											WebElement EWSave = isElementPresent("TLQCExitWSave_id");
											wait.until(ExpectedConditions.visibilityOf(EWSave));
											act.moveToElement(EWSave).build().perform();
											act.moveToElement(EWSave).click().perform();
											logs.info("Clicked on Exit Without Save");
										} else {
											logs.info("Job is not moved to VERIFIED stage");
											jobStatus = isElementPresent("TLStageLable_id").getText();
											logs.info("Job status is==" + jobStatus);

											WebElement EWSave = isElementPresent("TLQCExitWSave_id");
											wait.until(ExpectedConditions.visibilityOf(EWSave));
											act.moveToElement(EWSave).build().perform();
											act.moveToElement(EWSave).click().perform();
											logs.info("Clicked on Exit Without Save");

										}

									}

									//

								} catch (Exception VerifyCBill) {
									logs.info("job is not moved to VERIFIED stage");
									jobStatus = isElementPresent("TLStageLable_id").getText();
									logs.info("Job status is==" + jobStatus);

									WebElement EWSave = isElementPresent("TLQCExitWSave_id");
									wait.until(ExpectedConditions.visibilityOf(EWSave));
									act.moveToElement(EWSave).build().perform();
									act.moveToElement(EWSave).click().perform();
									logs.info("Clicked on Exit Without Save");

								}

							} else {
								logs.info("Job is not moved to Verify Customer Bill stage");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

								WebElement EWSave = isElementPresent("TLQCExitWSave_id");
								wait.until(ExpectedConditions.visibilityOf(EWSave));
								act.moveToElement(EWSave).build().perform();
								act.moveToElement(EWSave).click().perform();
								logs.info("Clicked on Exit Without Save");

							}

						}

						//

					} catch (Exception VerifyCBill) {
						logs.info("job is not moved to Verify Customer Bill stage");
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);
					}

				} else {
					logs.info("Job is not Delivered successfully");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);

				}

			} else if (jobStatus.contains("VERIFY CUSTOMER BILL")) {
				logs.info("Data is exist with search parameter");
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
				getScreenshot(driver, "JobEditor_Delivered");
				jobStatus = isElementPresent("TLStageLable_id").getText();
				logs.info("Job status is==" + jobStatus);

				if (jobStatus.contains("VERIFY CUSTOMER BILL")) {
					logs.info("Job is moved to Verify Customer Bill stage");
					getScreenshot(driver, "JobEditor_VerifyCustBill");

					// --Verify

					// --Click on Verify button
					WebElement Verify = isElementPresent("TLVerify_id");
					wait.until(ExpectedConditions.visibilityOf(Verify));
					act.moveToElement(Verify).build().perform();
					act.moveToElement(Verify).click().perform();
					logs.info("Clicked on Verify button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					// --Verified

					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtContains")));
						PickUpID = getData("SearchRTE", 1, 2);
						isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
						logs.info("Entered PickUpID in basic search");

						// --Click on Search
						wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
						isElementPresent("TLGlobSearch_id").click();
						logs.info("Click on Search button");
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

						try {
							WebElement NoDataV = isElementPresent("NoData_className");
							wait.until(ExpectedConditions.visibilityOf(NoDataV));
							if (NoDataV.isDisplayed()) {
								logs.info("There is no Data with Search parameter");

							}

						} catch (Exception NoDataee) {
							logs.info("Data is exist with search parameter");
							wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
							getScreenshot(driver, "JobEditor_Delivered");
							jobStatus = isElementPresent("TLStageLable_id").getText();
							logs.info("Job status is==" + jobStatus);

							if (jobStatus.contains("VERIFIED")) {
								logs.info("Job is moved to VERIFIED stage");
								getScreenshot(driver, "JobEditor_Verified");

								WebElement EWSave = isElementPresent("TLQCExitWSave_id");
								wait.until(ExpectedConditions.visibilityOf(EWSave));
								act.moveToElement(EWSave).build().perform();
								act.moveToElement(EWSave).click().perform();
								logs.info("Clicked on Exit Without Save");

							} else {
								logs.info("Job is not moved to VERIFIED stage");
								jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

								WebElement EWSave = isElementPresent("TLQCExitWSave_id");
								wait.until(ExpectedConditions.visibilityOf(EWSave));
								act.moveToElement(EWSave).build().perform();
								act.moveToElement(EWSave).click().perform();
								logs.info("Clicked on Exit Without Save");

							}

						}

						//

					} catch (Exception VerifyCBill) {
						logs.info("job is not moved to VERIFIED stage");
						jobStatus = isElementPresent("TLStageLable_id").getText();
						logs.info("Job status is==" + jobStatus);

						WebElement EWSave = isElementPresent("TLQCExitWSave_id");
						wait.until(ExpectedConditions.visibilityOf(EWSave));
						act.moveToElement(EWSave).build().perform();
						act.moveToElement(EWSave).click().perform();
						logs.info("Clicked on Exit Without Save");

					}

				} else {
					logs.info("Job is not moved to Verify Customer Bill stage");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);

					WebElement EWSave = isElementPresent("TLQCExitWSave_id");
					wait.until(ExpectedConditions.visibilityOf(EWSave));
					act.moveToElement(EWSave).build().perform();
					act.moveToElement(EWSave).click().perform();
					logs.info("Clicked on Exit Without Save");

				}

			} else if (jobStatus.contains("VERIFIED")) {
				// --Verified

				logs.info("Data is exist with search parameter");
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
				getScreenshot(driver, "JobEditor_Delivered");
				jobStatus = isElementPresent("TLStageLable_id").getText();
				logs.info("Job status is==" + jobStatus);

				if (jobStatus.contains("VERIFIED")) {
					logs.info("Job is moved to VERIFIED stage");
					getScreenshot(driver, "JobEditor_Verified");
					WebElement EWSave = isElementPresent("TLQCExitWSave_id");
					wait.until(ExpectedConditions.visibilityOf(EWSave));
					act.moveToElement(EWSave).build().perform();
					act.moveToElement(EWSave).click().perform();
					logs.info("Clicked on Exit Without Save");
				} else {
					logs.info("Job is not moved to VERIFIED stage");
					jobStatus = isElementPresent("TLStageLable_id").getText();
					logs.info("Job status is==" + jobStatus);

					WebElement EWSave = isElementPresent("TLQCExitWSave_id");
					wait.until(ExpectedConditions.visibilityOf(EWSave));
					act.moveToElement(EWSave).build().perform();
					act.moveToElement(EWSave).click().perform();
					logs.info("Clicked on Exit Without Save");

				}

				//

			} else {

				logs.info("Unknown Stage found");
				jobStatus = isElementPresent("TLStageLable_id").getText();
				logs.info("Job status is==" + jobStatus);
			}

		}

		logs.info("======================RTE Reject/Resend/CallBack/PendingDelivery Test End==================");
		msg.append(
				"======================RTE Reject/Resend/CallBack/PendingDelivery Test End==================" + "\n");

	}

}

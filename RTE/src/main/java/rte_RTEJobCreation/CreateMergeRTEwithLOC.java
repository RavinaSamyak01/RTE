package rte_RTEJobCreation;

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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import rte_BasePackage.BaseInit;

public class CreateMergeRTEwithLOC extends BaseInit {

	@Test
	public void createMergeRTEWithLOCJob()
			throws EncryptedDocumentException, InvalidFormatException, IOException, InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		Actions act = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;

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

		int totalRow = getTotalRow("LocJob");
		for (int row = 1; row < totalRow; row++) {

			// --Enter pickUpID
			wait.until(ExpectedConditions.elementToBeClickable(By.id("txtContains")));
			// --get the pickupId of LOC job which is created by Unmerge
			String PickUpID = getData("LocJob", row, 0);
			isElementPresent("TLBasicSearch_id").clear();
			isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
			logs.info("PickUpID==" + PickUpID);
			logs.info("Entered PickUpID in basic search");

			// --Click on Color code
			wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//a[@ng-click=\"parentclick(l,'YELLOW','YELLOW')\"]")));
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
				try {
					logs.info("Data is exist with search parameter");
					wait.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"flex-container\"]//tasks")));
					getScreenshot(driver, "TaskLogWithLOCJob");

					wait.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(By.xpath("//*[@data-info=\"TaskDetails\"]")));
					List<WebElement> jobs = driver.findElements(By.xpath("//*[@data-info=\"TaskDetails\"]//tasks"));
					int totaljobs = jobs.size();
					for (int job = 0; job < totaljobs; job++) {

						PickUpID = getData("LocJob", row, 0);
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

							try {
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
										By.xpath("//*[@class=\"ngdialog-content\"]")));
								getScreenshot(driver, "UnMergeDialogue");
								String UnMergeMsg = isElementPresent("TLUnmerge_className").getText();
								logs.info("UnMerge Message==" + UnMergeMsg);

								// --Click on OK button
								isElementPresent("TLUnMergeOK_id").click();
								logs.info("Clicked on OK button");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								wait.until(ExpectedConditions
										.invisibilityOfElementLocated(By.xpath("//*[@class=\"ngdialog-content\"]")));

							} catch (Exception UnMerge) {
								logs.info("Able to UnMerge");
								getScreenshot(driver, "RTEJobwithLOC");

								if (row == 1) {
									logs.info(
											"======================Create RTE with LOC Job Test Start==================");
									msg.append(
											"======================Create RTE with LOC Job Test Start=================="
													+ "\n");

									msg.append("PickUpID==" + PickUpID + "\n");

									// --Create RTE form
									wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
											By.xpath("//*[@ng-form=\"CreateRTEForm\"]")));
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
									js.executeScript("arguments[0].click();", CreateRoute);

									logs.info("Clicked on Create Route button of RTE form");

									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									try {
										wait.until(
												ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("success")));

										String SUccMsg = isElementPresent("TLRWSucc_id").getText();
										logs.info("Message is displayed==" + SUccMsg);
										logs.info("RTE Job is created successfully.");
										msg.append("RTE Job is created successfully." + "/n");

										// --Get the RouteWorkID
										String inLine = SUccMsg;
										String[] lineSplits = inLine.split(" ");
										for (String Detail : lineSplits) {
											if (Detail.contains("RT")) {
												final String RWID = Detail.split("\\.")[0];
												RWID.trim();
												System.out.println("RoutWorkID is==" + RWID);
												logs.info("RoutWorkID is==" + RWID);
												msg.append("RoutWorkID is==" + RWID + "/n");
												setData("RTECreation", 9, 1, RWID);
												logs.info("Stored RoutWorkID in excel");

											}

										}
									} catch (Exception e) {
										logs.info("RTE Job is not created successfully,Something went wrong==FAIL");
										getScreenshot(driver, "RTEjobNotCreated");
									}

									// --clicked on cancel button
									WebElement CancelRTE = isElementPresent("TLRWCancelRW_id");
									wait.until(ExpectedConditions.elementToBeClickable(CancelRTE));
									act.moveToElement(CancelRTE).click().perform();
									logs.info("Clicked on Cancel button of RTE form");

									break;

								} else if (row == 2) {
									logs.info(
											"======================Merge with Exiting RTE Test Start==================");
									msg.append(
											"======================Merge with Exiting RTE Test Start=================="
													+ "\n");
									msg.append("PickUpID==" + PickUpID + "\n");

									// --select Merge with Exiting RTE radio button
									WebElement MWER = isElementPresent("TLRWMERTE_id");
									js.executeScript("arguments[0].click();", MWER);
									logs.info("Selected Merge with Exiting RTE radio button");

									WebElement RList = isElementPresent("TLRWListDrp_id");
									wait.until(ExpectedConditions.visibilityOf(RList));

									// --Select RTE
									Select RlistValue = new Select(RList);

									// -get the TrackingNo
									String RWTrackNo = getData("RTECreation", 7, 2);
									logs.info("TrackingNo of RTE job is==" + RWTrackNo);

									// --Select the RTE which is created on current date
									RList = isElementPresent("TLRWListDrp_id");
									act.moveToElement(RList).build().perform();
									act.moveToElement(RList).click().perform();
									Thread.sleep(2000);

									List<WebElement> optionElements = driver
											.findElements(By.xpath("//*[@id=\"drpRouteList\"]//option"));
									int TotalOpts = optionElements.size();
									logs.info("Total No of RTE job are==" + TotalOpts);

									for (int optionElement = 0; optionElement < TotalOpts; optionElement++) {

										String Optiontext = optionElements.get(optionElement).getText();

										if (Optiontext.contains(RWTrackNo)) {
											String optionIndex = optionElements.get(optionElement)
													.getAttribute("index");
											int OpIndex = Integer.parseInt(optionIndex);
											logs.info("Index of RTE job is==" + OpIndex);
											Thread.sleep(2000);
											RlistValue = new Select(RList);
											RlistValue.selectByIndex(OpIndex);
											Thread.sleep(2000);
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));
											logs.info("Selected RTE Job");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));
											getScreenshot(driver, "MergeRTEwithLOC_" + RWTrackNo);
											break;
										}
									}

									// --Click on Merge Route button
									WebElement CreateRoute = isElementPresent("TLRWCreateRoute_id");
									wait.until(ExpectedConditions.elementToBeClickable(CreateRoute));
									act.moveToElement(CreateRoute).build().perform();
									js.executeScript("arguments[0].click();", CreateRoute);
									logs.info("Clicked on Merge Route button of RTE form");

									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
									try {
										wait.until(
												ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("success")));

										String SUccMsg = isElementPresent("TLRWSucc_id").getText();
										logs.info("Message is displayed==" + SUccMsg);
										logs.info("LOC Job is Merged successfully in RTE.");

										// --Get the RouteWorkID
										String inLine = SUccMsg;
										String[] lineSplits = inLine.split(" ");

										for (String Detail : lineSplits) {
											if (Detail.contains("RT")) {
												Detail.trim();
												System.out.println("RoutWorkID is==" + Detail);
												logs.info("RoutWorkID is==" + Detail);
												msg.append("RoutWorkID is==" + Detail + "\n");

												if (Detail.equalsIgnoreCase(RWTrackNo)) {
													logs.info("LOC job is merged with correct RTE");
													msg.append("LOC Job is Merged successfully in RTE." + "\n");

												} else {
													logs.info("LOC job is not merged with correct RTE");

												}

											} else if (Detail.contains(".")) {
												final String PUID = Detail.split("\\.")[0];

												System.out.println("Pickup ID is==" + PUID);
												logs.info("Pickup ID is==" + PUID);
												msg.append("Pickup ID is==" + PUID + "\n");

											}

										}
									} catch (Exception e) {
										logs.error(e);
										getScreenshot(driver, "e_MRTELOCError");
										logs.info("LOC Job is not merged successfully,Something went wrong==FAIL");
										getScreenshot(driver, "RTEjobNotCreated");
									}

									// --clicked on cancel button
									WebElement CancelRTE = isElementPresent("TLRWCancelRW_id");
									wait.until(ExpectedConditions.elementToBeClickable(CancelRTE));
									act.moveToElement(CancelRTE).click().perform();
									logs.info("Clicked on Cancel button of RTE form");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									break;

								}

							}

						} else {
							logs.info("Searched job is not exist");

						}
					}
				} catch (Exception e) {
					logs.error(e);
					getScreenshot(driver, "e_MRTELOCError");
					logs.info("Data is exist with search parameter");
					wait.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(@class,'TasklogJobs ')]")));
					getScreenshot(driver, "TaskLogWithLOCJob");

					wait.until(ExpectedConditions
							.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class=\"dx-datagrid-content\"]")));
					List<WebElement> jobs = driver.findElements(By.xpath("//*[@class=\"dx-datagrid-content\"]//tr"));
					// td[contains(@aria-label,'Column Pickup')]//label

					int totaljobs = jobs.size();
					for (int job = 0; job < totaljobs; job++) {

						PickUpID = getData("LocJob", row, 0);
						logs.info("Entered PickupID is==" + PickUpID);

						try {
							String PickUPId = jobs.get(job)
									.findElement(By.xpath("td[contains(@aria-label,'Column Pickup')]//label")).getText()
									.trim();
							logs.info("PickupID is==" + PickUPId);

							if (PickUPId.contains(PickUpID)) {
								logs.info("Searched job is exist");

								// --Select the checkbox
								WebElement chkBx = jobs.get(job).findElement(By.xpath("td/div[@role=\"checkbox\"]"));
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

								try {
									wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
											By.xpath("//*[@class=\"ngdialog-content\"]")));
									getScreenshot(driver, "UnMergeDialogue");
									String UnMergeMsg = isElementPresent("TLUnmerge_className").getText();
									logs.info("UnMerge Message==" + UnMergeMsg);

									// --Click on OK button
									isElementPresent("TLUnMergeOK_id").click();
									logs.info("Clicked on OK button");
									wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

									wait.until(ExpectedConditions.invisibilityOfElementLocated(
											By.xpath("//*[@class=\"ngdialog-content\"]")));

								} catch (Exception UnMerge) {
									logs.info("Able to UnMerge");
									getScreenshot(driver, "RTEJobwithLOC");

									if (row == 1) {
										logs.info(
												"======================Create RTE with LOC Job Test Start==================");
										msg.append(
												"======================Create RTE with LOC Job Test Start=================="
														+ "\n");

										msg.append("PickupID is==" + PickUPId + "\n");

										// --Create RTE form
										wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
												By.xpath("//*[@ng-form=\"CreateRTEForm\"]")));
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
										js.executeScript("arguments[0].click();", CreateRoute);

										logs.info("Clicked on Create Route button of RTE form");

										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										try {
											wait.until(ExpectedConditions
													.visibilityOfAllElementsLocatedBy(By.id("success")));

											String SUccMsg = isElementPresent("TLRWSucc_id").getText();
											logs.info("Message is displayed==" + SUccMsg);
											logs.info("RTE Job is created successfully.");
											msg.append("RTE Job is created successfully." + "\n");

											// --Get the RouteWorkID
											String inLine = SUccMsg;
											String[] lineSplits = inLine.split(" ");
											for (String Detail : lineSplits) {
												if (Detail.contains("RT")) {
													final String RWID = Detail.split("\\.")[0];
													RWID.trim();
													System.out.println("RoutWorkID is==" + RWID);
													logs.info("RoutWorkID is==" + RWID);
													msg.append("RoutWorkID is==" + RWID + "\n");
													setData("RTECreation", 9, 1, RWID);
													logs.info("Stored RoutWorkID in excel");

												}

											}
										} catch (Exception en) {
											logs.info("RTE Job is not created successfully,Something went wrong==FAIL");
											getScreenshot(driver, "RTEjobNotCreated");
										}

										// --clicked on cancel button
										WebElement CancelRTE = isElementPresent("TLRWCancelRW_id");
										wait.until(ExpectedConditions.elementToBeClickable(CancelRTE));
										act.moveToElement(CancelRTE).click().perform();
										logs.info("Clicked on Cancel button of RTE form");

										break;

									} else if (row == 2) {
										logs.info(
												"======================Merge with Exiting RTE Test Start==================");
										msg.append(
												"======================Merge with Exiting RTE Test Start=================="
														+ "\n");
										msg.append("PickupID is==" + PickUPId + "\n");

										// --select Merge with Exiting RTE radio button
										WebElement MWER = isElementPresent("TLRWMERTE_id");
										js.executeScript("arguments[0].click();", MWER);
										logs.info("Selected Merge with Exiting RTE radio button");

										WebElement RList = isElementPresent("TLRWListDrp_id");
										wait.until(ExpectedConditions.visibilityOf(RList));

										// --Select RTE
										Select RlistValue = new Select(RList);

										// -get the TrackingNo
										String RWTrackNo = getData("RTECreation", 7, 2);
										logs.info("TrackingNo of RTE job is==" + RWTrackNo);

										// --Select the RTE which is created on current date
										RList = isElementPresent("TLRWListDrp_id");
										act.moveToElement(RList).build().perform();
										act.moveToElement(RList).click().perform();
										Thread.sleep(2000);

										List<WebElement> optionElements = driver
												.findElements(By.xpath("//*[@id=\"drpRouteList\"]//option"));
										int TotalOpts = optionElements.size();
										logs.info("Total No of RTE job are==" + TotalOpts);

										for (int optionElement = 0; optionElement < TotalOpts; optionElement++) {

											String Optiontext = optionElements.get(optionElement).getText();

											if (Optiontext.contains(RWTrackNo)) {
												String optionIndex = optionElements.get(optionElement)
														.getAttribute("index");
												int OpIndex = Integer.parseInt(optionIndex);
												logs.info("Index of RTE job is==" + OpIndex);
												Thread.sleep(2000);
												RlistValue = new Select(RList);
												RlistValue.selectByIndex(OpIndex);
												Thread.sleep(5000);
												wait.until(ExpectedConditions
														.invisibilityOfElementLocated(By.id("loaderDiv")));
												logs.info("Selected RTE Job");
												wait.until(ExpectedConditions
														.invisibilityOfElementLocated(By.id("loaderDiv")));
												getScreenshot(driver, "MergeRTEwithLOC_" + RWTrackNo);
												break;
											}
										}

										// --Click on Merge Route button
										WebElement CreateRoute = isElementPresent("TLRWCreateRoute_id");
										wait.until(ExpectedConditions.elementToBeClickable(CreateRoute));
										act.moveToElement(CreateRoute).build().perform();
										js.executeScript("arguments[0].click();", CreateRoute);
										logs.info("Clicked on Merge Route button of RTE form");

										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));
										try {
											wait.until(ExpectedConditions
													.visibilityOfAllElementsLocatedBy(By.id("success")));

											String SUccMsg = isElementPresent("TLRWSucc_id").getText();
											logs.info("Message is displayed==" + SUccMsg);
											logs.info("LOC Job is Merged successfully in RTE.");

											// --Get the RouteWorkID
											String inLine = SUccMsg;
											String[] lineSplits = inLine.split(" ");

											for (String Detail : lineSplits) {
												if (Detail.contains("RT")) {
													Detail.trim();
													System.out.println("RoutWorkID is==" + Detail);
													logs.info("RoutWorkID is==" + Detail);
													if (Detail.equalsIgnoreCase(RWTrackNo)) {
														logs.info("LOC job is merged with correct RTE");
														msg.append("LOC Job is Merged successfully in RTE." + "\n");

													} else {
														logs.info("LOC job is not merged with correct RTE");

													}

												} else if (Detail.contains(".")) {
													final String PUID = Detail.split("\\.")[0];

													System.out.println("Pickup ID is==" + PUID);
													logs.info("Pickup ID is==" + PUID);
													msg.append("Pickup ID is==" + PUID + "\n");

												}

											}
										} catch (Exception enn) {
											logs.error(enn);
											getScreenshot(driver, "enn_MRTELOCError");
											logs.info("LOC Job is not merged successfully,Something went wrong==FAIL");
											getScreenshot(driver, "RTEjobNotCreated");
										}

										// --clicked on cancel button
										WebElement CancelRTE = isElementPresent("TLRWCancelRW_id");
										wait.until(ExpectedConditions.elementToBeClickable(CancelRTE));
										act.moveToElement(CancelRTE).click().perform();
										logs.info("Clicked on Cancel button of RTE form");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										break;

									}

								}

							} else {
								logs.info("Searched job is not exist");

							}
						} catch (Exception staleElement) {

							List<WebElement> jobsNew = driver
									.findElements(By.xpath("//*[@class=\"dx-datagrid-content\"]//tr"));
							// td[contains(@aria-label,'Column Pickup')]//label

							int totaljobsnew = jobsNew.size();
							for (int jobnew = job; jobnew < totaljobsnew;) {
								System.out.println("jobnew==" + jobnew);
								PickUpID = getData("LocJob", row, 0);
								logs.info("Entered PickupID is==" + PickUpID);

								String PickUPId = jobsNew.get(jobnew)
										.findElement(By.xpath("td[contains(@aria-label,'Column Pickup')]//label"))
										.getText().trim();
								logs.info("PickupID is==" + PickUPId);

								if (PickUPId.contains(PickUpID)) {
									logs.info("Searched job is exist");

									// --Select the checkbox
									WebElement chkBx = jobsNew.get(jobnew)
											.findElement(By.xpath("td/div[@role=\"checkbox\"]"));
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

									try {
										wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
												By.xpath("//*[@class=\"ngdialog-content\"]")));
										getScreenshot(driver, "UnMergeDialogue");
										String UnMergeMsg = isElementPresent("TLUnmerge_className").getText();
										logs.info("UnMerge Message==" + UnMergeMsg);

										// --Click on OK button
										isElementPresent("TLUnMergeOK_id").click();
										logs.info("Clicked on OK button");
										wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

										wait.until(ExpectedConditions.invisibilityOfElementLocated(
												By.xpath("//*[@class=\"ngdialog-content\"]")));

									} catch (Exception UnMerge) {
										logs.info("Able to UnMerge");
										getScreenshot(driver, "RTEJobwithLOC");

										if (row == 1) {
											logs.info(
													"======================Create RTE with LOC Job Test Start==================");
											msg.append(
													"======================Create RTE with LOC Job Test Start=================="
															+ "\n");

											// --Create RTE form
											wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
													By.xpath("//*[@ng-form=\"CreateRTEForm\"]")));
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
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											// Phone Number
											isElementPresent("TLRWUnPhone_id").clear();
											isElementPresent("TLRWUnPhone_id").sendKeys("9856320147");
											logs.info("Entered Phone Number");

											// --clicked on Create Route button
											WebElement CreateRoute = isElementPresent("TLRWCreateRoute_id");
											wait.until(ExpectedConditions.elementToBeClickable(CreateRoute));
											act.moveToElement(CreateRoute).build().perform();
											js.executeScript("arguments[0].click();", CreateRoute);

											logs.info("Clicked on Create Route button of RTE form");

											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											try {
												wait.until(ExpectedConditions
														.visibilityOfAllElementsLocatedBy(By.id("success")));

												String SUccMsg = isElementPresent("TLRWSucc_id").getText();
												logs.info("Message is displayed==" + SUccMsg);
												logs.info("RTE Job is created successfully.");

												// --Get the RouteWorkID
												String inLine = SUccMsg;
												String[] lineSplits = inLine.split(" ");
												for (String Detail : lineSplits) {
													if (Detail.contains("RT")) {
														final String RWID = Detail.split("\\.")[0];
														RWID.trim();
														System.out.println("RoutWorkID is==" + RWID);
														logs.info("RoutWorkID is==" + RWID);
														setData("RTECreation", 9, 1, RWID);
														logs.info("Stored RoutWorkID in excel");

													}

												}
											} catch (Exception en) {
												logs.error(en);
												getScreenshot(driver, "en_MRTELOCError");
												logs.info(
														"RTE Job is not created successfully,Something went wrong==FAIL");
												getScreenshot(driver, "RTEjobNotCreated");
											}

											// --clicked on cancel button
											WebElement CancelRTE = isElementPresent("TLRWCancelRW_id");
											wait.until(ExpectedConditions.elementToBeClickable(CancelRTE));
											act.moveToElement(CancelRTE).click().perform();
											logs.info("Clicked on Cancel button of RTE form");

											break;

										} else if (row == 2) {
											logs.info(
													"======================Merge with Exiting RTE Test Start==================");
											msg.append(
													"======================Merge with Exiting RTE Test Start=================="
															+ "\n");

											// --select Merge with Exiting RTE radio button
											WebElement MWER = isElementPresent("TLRWMERTE_id");
											js.executeScript("arguments[0].click();", MWER);
											logs.info("Selected Merge with Exiting RTE radio button");

											WebElement RList = isElementPresent("TLRWListDrp_id");
											wait.until(ExpectedConditions.visibilityOf(RList));

											// --Select RTE
											Select RlistValue = new Select(RList);

											// -get the TrackingNo
											String RWTrackNo = getData("RTECreation", 7, 2);
											logs.info("TrackingNo of RTE job is==" + RWTrackNo);

											// --Select the RTE which is created on current date
											RList = isElementPresent("TLRWListDrp_id");
											act.moveToElement(RList).build().perform();
											act.moveToElement(RList).click().perform();
											Thread.sleep(2000);

											List<WebElement> optionElements = driver
													.findElements(By.xpath("//*[@id=\"drpRouteList\"]//option"));
											int TotalOpts = optionElements.size();
											logs.info("Total No of RTE job are==" + TotalOpts);

											for (int optionElement = 0; optionElement < TotalOpts; optionElement++) {

												String Optiontext = optionElements.get(optionElement).getText();

												if (Optiontext.contains(RWTrackNo)) {
													String optionIndex = optionElements.get(optionElement)
															.getAttribute("index");
													int OpIndex = Integer.parseInt(optionIndex);
													logs.info("Index of RTE job is==" + OpIndex);
													Thread.sleep(2000);
													RlistValue = new Select(RList);
													RlistValue.selectByIndex(OpIndex);
													Thread.sleep(2000);
													wait.until(ExpectedConditions
															.invisibilityOfElementLocated(By.id("loaderDiv")));
													logs.info("Selected RTE Job");
													wait.until(ExpectedConditions
															.invisibilityOfElementLocated(By.id("loaderDiv")));
													getScreenshot(driver, "MergeRTEwithLOC_" + RWTrackNo);
													break;
												}
											}

											// --Click on Merge Route button
											WebElement CreateRoute = isElementPresent("TLRWCreateRoute_id");
											wait.until(ExpectedConditions.elementToBeClickable(CreateRoute));
											act.moveToElement(CreateRoute).build().perform();
											js.executeScript("arguments[0].click();", CreateRoute);
											logs.info("Clicked on Merge Route button of RTE form");

											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));
											try {
												wait.until(ExpectedConditions
														.visibilityOfAllElementsLocatedBy(By.id("success")));

												String SUccMsg = isElementPresent("TLRWSucc_id").getText();
												logs.info("Message is displayed==" + SUccMsg);
												logs.info("RTE Job is merged successfully.");

												// --Get the RouteWorkID
												String inLine = SUccMsg;
												String[] lineSplits = inLine.split(" ");

												for (String Detail : lineSplits) {
													if (Detail.contains("RT")) {
														Detail.trim();
														System.out.println("RoutWorkID is==" + Detail);
														logs.info("RoutWorkID is==" + Detail);
														if (Detail.equalsIgnoreCase(RWTrackNo)) {
															logs.info("LOC job is merged with correct RTE");

														} else {
															logs.info("LOC job is not merged with correct RTE");

														}

													} else if (Detail.contains(".")) {
														final String PUID = Detail.split("\\.")[0];

														System.out.println("Pickup ID is==" + PUID);
														logs.info("Pickup ID is==" + PUID);

													}

												}
											} catch (Exception enn) {
												logs.error(enn);
												getScreenshot(driver, "enn_MRTELOCError");
												logs.info(
														"LOC Job is not merged successfully,Something went wrong==FAIL");
												getScreenshot(driver, "RTEjobNotCreated");
											}

											// --clicked on cancel button
											WebElement CancelRTE = isElementPresent("TLRWCancelRW_id");
											wait.until(ExpectedConditions.elementToBeClickable(CancelRTE));
											act.moveToElement(CancelRTE).click().perform();
											logs.info("Clicked on Cancel button of RTE form");
											wait.until(ExpectedConditions
													.invisibilityOfElementLocated(By.id("loaderDiv")));

											break;

										}

									}

								} else {
									logs.info("Searched job is not exist");
									break;

								}

							}
						}

					}

					// --Enter pickUpID
					wait.until(ExpectedConditions.elementToBeClickable(By.id("txtContains")));
					// --get the pickupId of LOC job which is created by Unmerge
					PickUpID = getData("LocJob", row, 0);
					isElementPresent("TLBasicSearch_id").clear();
					isElementPresent("TLBasicSearch_id").sendKeys(PickUpID);
					logs.info("PickUpID==" + PickUpID);
					logs.info("Entered PickUpID in basic search");

					// --Click on Search
					wait.until(ExpectedConditions.elementToBeClickable(By.id("btnGlobalSearch")));
					WebElement GlobSearch = isElementPresent("TLGlobSearch_id");
					act.moveToElement(GlobSearch).click().perform();
					logs.info("Click on Search button");
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

					try {
						WebElement NoData1 = isElementPresent("TLNORecord_xpath");
						wait.until(ExpectedConditions.visibilityOf(NoData1));
						if (NoData1.isDisplayed()) {
							logs.info("There is no Data with Search parameter");

						}
						if (row == 1) {

							logs.info("======================Create RTE with LOC Job Test End==================");
							msg.append(
									"======================Create RTE with LOC Job Test End==================" + "\n");

						}
						if (row == 2) {

							logs.info("======================Merge RTE with LOC Job Test End==================");
							msg.append(
									"======================Merge RTE with LOC Job Test End==================" + "\n");

						}

					} catch (Exception NoDataa) {
						try {
							if (row == 1) {
								// --check LOC job is converted in RTE or not

								// --If RTE job is open
								logs.info("Data is exist with search parameter");
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
								getScreenshot(driver, "RTEJobWLOC_TCACK");

								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
								String jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

								logs.info("LOC job is converted in RTE successfully.");

								// --Zoom Out
								js.executeScript("document.body.style.zoom='80%';");
								Thread.sleep(2000);

								// --Click on Save&EXit
								WebElement SaveExit = isElementPresent("TLEJSaveExit_xpath");
								wait.until(ExpectedConditions.elementToBeClickable(SaveExit));
								js.executeScript("arguments[0].click();", SaveExit);
								logs.info("Clicked on Save&Exit");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Zoom IN
								js.executeScript("document.body.style.zoom='100%';");
								Thread.sleep(2000);

								logs.info("======================Create RTE with LOC Job Test End==================");
								msg.append("======================Create RTE with LOC Job Test End=================="
										+ "\n");

							} else if (row == 2) {

								// --If RTE job is open
								logs.info("Data is exist with search parameter");
								wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("RouteWorkFlow")));
								getScreenshot(driver, "RTEMergeWLOC_TCACK");

								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
								String jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

								// --Check the merged Pickup ID in shipment details
								// --Moved to shipment details
								WebElement SHipDetails = isElementPresent("TLShipMentDetails_xpath");
								act.moveToElement(SHipDetails).build().perform();

								List<WebElement> SDetails = driver
										.findElements(By.xpath("//div[contains(@ng-repeat,'ShipmentDetailList')]"));

								int TotalShipment = SDetails.size();
								logs.info("Total Shipment==" + TotalShipment);
								for (int Shipment = 1; Shipment < TotalShipment;) {

									// --Get Pickup from excel
									String APUID = getData("LocJob", 2, 0);
									logs.info("PickUP No stored in Excel is==" + APUID);

									// --Get PickUP
									WebElement PUP = SDetails.get(Shipment).findElement(By.id("lblPickupIdValue"));
									act.moveToElement(PUP).build().perform();
									String PickUP = PUP.getText();
									logs.info("PickUP No is==" + PickUP);

									if (PickUP.equalsIgnoreCase(APUID)) {
										logs.info("LOC job is Merge in RTE successfully.");
										// --set the PickUP ID in excel
										PickUP = PUP.getText();
										setData("CompareCharges", 1, 1, PickUP);

										WebElement ShipCharges = isElementPresent("TLEShCharges_id");
										act.moveToElement(ShipCharges).build().perform();
										String Charges = ShipCharges.getText().trim();
										logs.info("Shipment Charges After Merge Loc Job===" + Charges);
										getScreenshot(driver, "LocJobAddCharges");

										// --set data in excel
										setData("CompareCharges", 1, 3, Charges);

									} else {
										logs.info("LOC job is not merge in RTE successfully.");

									}
									break;

								}
								// --Zoom Out
								js.executeScript("document.body.style.zoom='80%';");
								Thread.sleep(2000);

								// --Click on Save&EXit
								WebElement SaveExit = isElementPresent("TLEJSaveExit_xpath");
								wait.until(ExpectedConditions.elementToBeClickable(SaveExit));
								js.executeScript("arguments[0].click();", SaveExit);
								logs.info("Clicked on Save&Exit");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Zoom IN
								js.executeScript("document.body.style.zoom='100%';");
								Thread.sleep(2000);

								logs.info("======================Merge RTE with LOC Job Test End==================");
								msg.append("======================Merge RTE with LOC Job Test End=================="
										+ "\n");

							}

						} catch (Exception ee) {

							// ---If direct open the LOC job
							try {

								// --check LOC job is converted in RTE or not

								wait.until(
										ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("ActiveShipmentId")));
								getScreenshot(driver, "RTEJobWLOC_TCACK");
								logs.info("LOC job is not converted in RTE successfully.");

								// --Job Status
								wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lblStages")));
								String jobStatus = isElementPresent("TLStageLable_id").getText();
								logs.info("Job status is==" + jobStatus);

								// --Zoom Out
								js.executeScript("document.body.style.zoom='80%';");
								Thread.sleep(2000);

								// --Click on Save&EXit
								WebElement SaveExit = isElementPresent("TLEJSaveExit_xpath");
								wait.until(ExpectedConditions.elementToBeClickable(SaveExit));
								js.executeScript("arguments[0].click();", SaveExit);
								logs.info("Clicked on Save&Exit");
								wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loaderDiv")));

								// --Zoom IN
								js.executeScript("document.body.style.zoom='100%';");
								Thread.sleep(2000);
								if (row == 1) {
									logs.info(
											"======================Create RTE with LOC Job Test End==================");
									msg.append(
											"======================Create RTE with LOC Job Test End=================="
													+ "\n");

								} else if (row == 2) {
									logs.info(
											"======================Merge RTE with LOC Job Test End==================");
									msg.append("======================Merge RTE with LOC Job Test End=================="
											+ "\n");

								}

							} catch (Exception TL) {

								try {
									// --If multiple LOC job
									wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
											By.xpath("//*[@data-info=\"TaskDetails\"]")));
									jobs = driver.findElements(By.xpath("//*[@data-info=\"TaskDetails\"]//tasks"));
									totaljobs = jobs.size();
									for (int job = 0; job < totaljobs; job++) {

										PickUpID = getData("LocJob", row, 0);
										logs.info("Entered PickupID is==" + PickUpID);

										String PickUPId = jobs.get(job).getAttribute("id");
										logs.info("PickupID is==" + PickUPId);

										if (PickUPId.contains(PickUpID)) {
											logs.info("Searched job is exist");

											// --Click on the job
											jobs.get(job).click();
											logs.info("Clicked on searched Job");

											if (row == 1) {
												// --check LOC job is converted in RTE or not

												// --Job Status
												wait.until(ExpectedConditions
														.visibilityOfElementLocated(By.id("lblStages")));
												getScreenshot(driver, "RTEJobWLOC_TCACK");
												logs.info("LOC job is not converted in RTE successfully.");
												String jobStatus = isElementPresent("TLStageLable_id").getText();
												logs.info("Job status is==" + jobStatus);
												// --Zoom Out
												js.executeScript("document.body.style.zoom='80%';");
												Thread.sleep(2000);

												// --Click on Save&EXit
												WebElement SaveExit = isElementPresent("TLEJSaveExit_xpath");
												wait.until(ExpectedConditions.elementToBeClickable(SaveExit));
												js.executeScript("arguments[0].click();", SaveExit);
												logs.info("Clicked on Save&Exit");
												wait.until(ExpectedConditions
														.invisibilityOfElementLocated(By.id("loaderDiv")));

												// --Zoom IN
												js.executeScript("document.body.style.zoom='100%';");
												Thread.sleep(2000);
												if (row == 1) {
													logs.info(
															"======================Create RTE with LOC Job Test End==================");
													msg.append(
															"======================Create RTE with LOC Job Test End=================="
																	+ "\n");

												} else if (row == 2) {
													logs.info(
															"======================Merge RTE with LOC Job Test End==================");
													msg.append(
															"======================Merge RTE with LOC Job Test End=================="
																	+ "\n");

												}

												break;

											}

										}
									}
								} catch (Exception Viewchange) {
									wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
											By.xpath("//*[contains(@class,'TasklogJobs ')]")));
									getScreenshot(driver, "TaskLogWithLOCJob");

									wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
											By.xpath("//*[@class=\"dx-datagrid-content\"]")));
									jobs = driver.findElements(
											By.xpath("//td[contains(@aria-label,'Column Pickup')]//label"));
									totaljobs = jobs.size();
									for (int job = 0; job < totaljobs; job++) {

										PickUpID = getData("LocJob", row, 0);
										logs.info("Entered PickupID is==" + PickUpID);

										String PickUPId = jobs.get(job).getText().trim();
										logs.info("PickupID is==" + PickUPId);

										if (PickUPId.contains(PickUpID)) {
											logs.info("Searched job is exist");

											// --Click on the job
											jobs.get(job).click();
											logs.info("Clicked on searched Job");

											if (row == 1) {
												// --check LOC job is converted in RTE or not

												// --Job Status
												wait.until(ExpectedConditions
														.visibilityOfElementLocated(By.id("lblStages")));
												getScreenshot(driver, "RTEJobWLOC_TCACK");
												logs.info("LOC job is not converted in RTE successfully.");
												String jobStatus = isElementPresent("TLStageLable_id").getText();
												logs.info("Job status is==" + jobStatus);

												// --Zoom Out
												js.executeScript("document.body.style.zoom='80%';");
												Thread.sleep(2000);

												// --Click on Save&EXit
												WebElement SaveExit = isElementPresent("TLEJSaveExit_xpath");
												wait.until(ExpectedConditions.elementToBeClickable(SaveExit));
												js.executeScript("arguments[0].click();", SaveExit);
												logs.info("Clicked on Save&Exit");
												wait.until(ExpectedConditions
														.invisibilityOfElementLocated(By.id("loaderDiv")));

												// --Zoom IN
												js.executeScript("document.body.style.zoom='100%';");
												Thread.sleep(2000);
												if (row == 1) {
													logs.info(
															"======================Create RTE with LOC Job Test End==================");
													msg.append(
															"======================Create RTE with LOC Job Test End=================="
																	+ "\n");

												} else if (row == 2) {
													logs.info(
															"======================Merge RTE with LOC Job Test End==================");
													msg.append(
															"======================Merge RTE with LOC Job Test End=================="
																	+ "\n");

												}

												break;

											}

										}
									}

								}
							}

						}

					}

				}

			}
		}
	}
}

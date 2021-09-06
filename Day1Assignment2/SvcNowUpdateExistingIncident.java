/**
 * 
 */
package week5.Day1Assignment2;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

/**
 * @author Ramesh
 * 
 *         This class contains the test case for "Update Existing Incident"
 */
public class SvcNowUpdateExistingIncident extends SvcNowCommonClass {

	@Test
	public void tsUpdateIncident() throws InterruptedException, IOException {

		/*
		 * Step 1: Search for the existing incident and click on the incident
		 */

		System.out.println("Updating an Existing Incident");
		SvcNowUpdateExistingIncident objUpdInc = new SvcNowUpdateExistingIncident();

		String strSrchIncNbr = "INC0010008";
		System.out.println("Searching for the Incident - " + strSrchIncNbr + " - in order to update it");

		// Step 1a: Click on "Incidents" under Service Desk
		driver.findElement(By.xpath("(//div[text()='Incidents'])[2]")).click();
		Thread.sleep(1000);

		// Step 1b: Switch to the right-side frame
		driver.switchTo().frame("gsft_main");

		// Step 1c: Enter the Incident number in the search box, and hit Enter key
		driver.findElement(By.xpath("(//label[text()='Search'])[2]/following-sibling::input")).sendKeys(strSrchIncNbr,
				Keys.ENTER);
		System.out.println("Updation of incident starting.....");
		Thread.sleep(1000);

		// Step 2: Verify the incident is existing, and if yes, then update it

		int incFoundIndPre = objUpdInc.searchIncidentNbr(driver, strSrchIncNbr);

		if (incFoundIndPre == 1) {

			// Step 2a: Get the page title (of the incidents screen)
			String strPage2Title = driver.getTitle();

			// Step 2b: Update the incidents with State as In Progress
			WebElement elemIncState = driver.findElement(By.xpath("//select[@id='incident.state']"));
			Select drpDnIncSt = new Select(elemIncState);
			drpDnIncSt.selectByValue("2");
			System.out.println("State updated");
			Thread.sleep(1000);

			// Step 2c: Update the incidents with Urgency as High
			WebElement elemIncUrgency = driver.findElement(By.xpath("//select[@id='incident.urgency']"));
			Select drpDnIncUrg = new Select(elemIncUrgency);
			drpDnIncUrg.selectByValue("1");
			System.out.println("Urgency updated");
			Thread.sleep(1000);

			// Step 2d: Update the description box
			driver.findElement(By.xpath("//textarea[@id='incident.description']"))
					.sendKeys("Updated the incident now!!!");
			System.out.println("Description updated");
			Thread.sleep(1000);

			// Step 2e: Click the update button
			driver.findElement(By.xpath("//button[@id='sysverb_update']")).click();
			System.out.println("Incident " + strSrchIncNbr + " has been updated successfully");
			Thread.sleep(2000);

			// Step 2f: If the page title is the same, update the worklog, and click update
			// again
			if (driver.getTitle().equals(strPage2Title)) {
				WebElement active = driver.switchTo().activeElement();
				active.sendKeys("Incident updated by Ramesh. Worklist updated now.");

				driver.findElement(By.xpath("//button[@id='sysverb_update']")).click();
				System.out.println("Incident " + strSrchIncNbr + " has been updated successfully");
			}

		}

		/*
		 * Step 3: Verify if the updates have been successful
		 */

		System.out.println("Verifying if the incident update is successful.....");

		int incFoundIndPost = objUpdInc.searchIncidentNbr(driver, strSrchIncNbr);

		if (incFoundIndPost == 1) {

			// Step 3b: Get the incident number, Incident urgency and Incident State and
			// validate if they have been updated correctly
			String strCurrIncNbr = driver.findElement(By.xpath("//input[@id='incident.number']")).getAttribute("value");
			System.out.println("Current incident number is : " + strCurrIncNbr);
			Thread.sleep(1000);

			if (strCurrIncNbr.equals(strSrchIncNbr)) {

				System.out.println("The current incident number is the same as the one assigned : " + strCurrIncNbr);

				WebElement elemCurrIncUrgency = driver.findElement(By.xpath("//select[@id='incident.urgency']"));
				Select drpDnCurrIncUrg = new Select(elemCurrIncUrgency);
				String currIncUrg = drpDnCurrIncUrg.getFirstSelectedOption().getText();

				WebElement elemCurrIncState = driver.findElement(By.xpath("//select[@id='incident.state']"));
				Select drpDnCurrIncSt = new Select(elemCurrIncState);
				String currIncSt = drpDnCurrIncSt.getFirstSelectedOption().getText();

				System.out.println(
						"Current incident urgency : " + currIncUrg + "  ||  Current incident state : " + currIncSt);

				if ((currIncUrg.equals("1 - High")) && (currIncSt.equals("In Progress"))) {
					System.out.println("The incident has been UPDATED successfully!!!!");
				} else {
					System.out.println("ERROR!!!! The incident has NOT been updated correctly!!!!");
				}
			}

		}

	}
}

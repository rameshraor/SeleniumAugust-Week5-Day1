/**
 * 
 */
package week5.Day1Assignment2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
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
		System.out.println("Searching for the Incident - INC0010008 - in order to update it");
		String strSrchIncNbr = "INC0010008";

		// Step 1a: Click on "Incidents" under Service Desk
		driver.findElement(By.xpath("(//div[text()='Incidents'])[2]")).click();
		Thread.sleep(1000);

		// Step 1b: Switch to the right-side frame
		WebElement frame2 = driver.findElement(By.xpath("//iframe[@id='gsft_main']"));
		driver.switchTo().frame(frame2);

		// Step 1c: Enter the Incident number in the search box, and hit Enter key
		driver.findElement(By.xpath("(//label[text()='Search'])[2]/following-sibling::input")).sendKeys(strSrchIncNbr,
				Keys.ENTER);
		System.out.println("Updation of incident starting.....");
		Thread.sleep(1000);

		// Step 2: Verify the incident is existing, and if yes, then update it

		// Step 2a: Locate the incident number in the search results, and check if it is
		// the expected incident number
		WebElement elemSrchResInc = driver.findElement(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a"));
		
		if (elemSrchResInc.isDisplayed()) {
			
			System.out.println("Able to locate the incident in the screen");
			String actIncNbr = elemSrchResInc.getAttribute("aria-label");

			if (actIncNbr.contains(strSrchIncNbr)) {

				// Step 2b: The searched incident number is shown in the results
				// Click on it, for update
				elemSrchResInc.click();

				// Step 2c: Update the incidents with State as In Progress
				WebElement elemIncState = driver.findElement(By.xpath("//select[@id='incident.state']"));
				Select drpDnIncSt = new Select(elemIncState);
				drpDnIncSt.selectByValue("2");
				System.out.println("State updated");
				Thread.sleep(2000);
				
				// Step 2d: Update the incidents with Urgency as High
				WebElement elemIncUrgency = driver.findElement(By.xpath("//select[@id='incident.urgency']"));
				Select drpDnIncUrg = new Select(elemIncUrgency);
				drpDnIncUrg.selectByValue("1");
				System.out.println("Urgency updated");
				Thread.sleep(2000);

				// Step 2e: Update the description box
				driver.findElement(By.xpath("//textarea[@id='incident.description']"))
						.sendKeys("Updated the incident now!!!");
				System.out.println("Description updated");
				Thread.sleep(2000);

				// Step 2f: Update the incident with Work Notes
				// Scroll up the screen - till "Filters" so that the web elements can be
				// captured
				WebElement elemScrl = driver.findElement(By.xpath("//span[@id='status.incident.short_description']"));
				driver.executeScript("arguments[0].scrollIntoView();", elemScrl);
				System.out.println("scrolled down");
				Thread.sleep(2000);

				WebElement elemWrkNts = driver.findElement(By.xpath("//textarea[@id='activity-stream-textarea']"));
				elemWrkNts.click();
				Thread.sleep(2000);
				elemWrkNts.sendKeys("This is the worknotes for the updates to this incident");
				System.out.println("Work notes updated");
				Thread.sleep(5000);

				// Step 2g: Click the update button
				driver.findElement(By.xpath("//button[@id='sysverb_update']")).click();
				System.out.println("Incident " + strSrchIncNbr + " has been updated successfully");
				Thread.sleep(2000);

			} else
				System.out.println("Incident is not created successfully. Expected : " + strSrchIncNbr
						+ " but Actual displayed here : " + actIncNbr);

		} else {
			System.out.println("No results displayed for the incident number!!!");
		}

		/*
		 * Step 3: Verify if the updates have been successful
		 */

		System.out.println("Verifying if the incident update is successful.....");

		// Step 3a: Click on the 1st incident in the results table
		driver.findElement(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a")).click();
		Thread.sleep(1000);

		// Step 3b: Get the incident number, Incident urgency and Incident State and
		// validate if they have been updated correctly
		String strCurrIncNbr = driver.findElement(By.xpath("//input[@id='incident.number']")).getText();
		System.out.println("Current incident number is : " + strCurrIncNbr);

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
				System.out.println("The incident has been updated successfully!!!!");
			} else {
				System.out.println("ERROR!!!! The incident has NOT been updated correctly!!!!");
			}

		} else {
			System.out.println("Something wrong...... this is not the same incident number that was updated");
		}

	}

}

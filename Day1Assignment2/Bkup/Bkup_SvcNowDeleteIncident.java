/**
 * 
 */
package week5.Day1Assignment2.Bkup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

/**
 * @author Ramesh
 * 
 *         This class contains the test case for "Update Existing Incident"
 */
public class Bkup_SvcNowDeleteIncident extends Bkup_SvcNowCommonClass {

	@Test
	public void tsCreateNew() throws InterruptedException, IOException {

		/*
		 * Step 1: Search for the existing incident and click on the incident
		 */

		System.out.println("Deleting an Existing Incident");
		System.out.println("Searching for the Incident - INC0000003 - in order to delete it");
		String strSrchIncNbr = "INC0000003";

		// Step 1a: Click on "Incidents" under Service Desk
		driver.findElement(By.xpath("(//div[text()='Incidents'])[2]")).click();
		Thread.sleep(1000);

		// Step 1b: Switch to the right-side frame
		WebElement frame2 = driver.findElement(By.xpath("//iframe[@id='gsft_main']"));
		driver.switchTo().frame(frame2);

		// Step 1c: Enter the Incident number in the search box, and hit Enter key
		driver.findElement(By.xpath("(//label[text()='Search'])[2]/following-sibling::input")).sendKeys(strSrchIncNbr,
				Keys.ENTER);
		Thread.sleep(1000);

		// Step 2: Verify the incident is created successful

		// Step 2a: Locate the incident number in the search results, and check if it is
		// the expected incident number
		WebElement elemSrchResInc = driver.findElement(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a"));
		if (elemSrchResInc.isDisplayed()) {
			String actIncNbr = elemSrchResInc.getAttribute("aria-label");

			if (actIncNbr.contains(strSrchIncNbr)) {

				// Step 2b: The searched incident number is shown in the results
				// Click on it, for Deleting it (in the next screen)
				elemSrchResInc.click();

				// Step 2c: Click the update button
				driver.findElement(By.xpath("//button[@id='sysverb_delete']")).click();
				System.out.println("Incident " + strSrchIncNbr + " has been deleted successfully");

			} else
				System.out.println("Incident is not created successfully. Expected : " + strSrchIncNbr
						+ " but Actual displayed here : " + actIncNbr);

		} else {
			System.out.println("No results displayed for the incident number!!!");
		}

	}

}

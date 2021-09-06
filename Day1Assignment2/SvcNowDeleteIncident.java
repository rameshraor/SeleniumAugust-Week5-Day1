/**
 * 
 */
package week5.Day1Assignment2;

import java.io.IOException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

/**
 * @author Ramesh
 * 
 *         This class contains the test case for "Update Existing Incident"
 */
public class SvcNowDeleteIncident extends SvcNowCommonClass {

	@Test
	public void tsCreateNew() throws InterruptedException, IOException {

		/*
		 * Step 1: Search for the existing incident and click on the incident
		 */

		System.out.println("Deleting an Existing Incident");
		SvcNowDeleteIncident objDelInc = new SvcNowDeleteIncident();
		
		String strSrchIncNbr = "INC0000027";
		System.out.println("Searching for the Incident - " + strSrchIncNbr + " - in order to delete it");
		
		// Step 1a: Click on "Incidents" under Service Desk
		driver.findElement(By.xpath("(//div[text()='Incidents'])[2]")).click();
		Thread.sleep(1000);

		// Step 1b: Switch to the right-side frame
		driver.switchTo().frame("gsft_main");

		// Step 1c: Enter the Incident number in the search box, and hit Enter key
		driver.findElement(By.xpath("(//label[text()='Search'])[2]/following-sibling::input")).sendKeys(strSrchIncNbr,
				Keys.ENTER);
		System.out.println("Deletion of incident starting.....");
		Thread.sleep(1000);
		
		// Step 2: Verify the incident is created successful

		int incFoundIndPre = objDelInc.searchIncidentNbr(driver, strSrchIncNbr);
		
		if (incFoundIndPre == 1) {

				// Step 2a: Click the update button
				driver.findElement(By.xpath("//button[@id='sysverb_delete']")).click();
				Thread.sleep(2000);
				
				driver.findElement(By.xpath("//button[@id='ok_button']")).click();

				System.out.println("Incident " + strSrchIncNbr + " has been deleted successfully");

		}
		
		/*
		 * Step 3: Verify if the Delete has been successful
		 */
		
		System.out.println("Verifying if the incident Delete is successful.....");

		int incFoundIndPost = objDelInc.searchIncidentNbr(driver, strSrchIncNbr);

		if (incFoundIndPost == 0) {
			
			System.out.println("The action DELETE INCIDENT for the Incident " + strSrchIncNbr
					+ " is successful!!!.");
			
		}

	}

}

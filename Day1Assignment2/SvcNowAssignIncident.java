/**
 * 
 */
package week5.Day1Assignment2;

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
 *         This class contains the test case for "Assign Incident"
 */
public class SvcNowAssignIncident extends SvcNowCommonClass {

	@Test
	public void tsCAssignIncident() throws InterruptedException, IOException {

		/*
		 * Step 1: click on open and Search for the existing incident and click on the incident
		 */

		System.out.println("Assigning an Existing Incident");
		SvcNowAssignIncident objAssnInc = new SvcNowAssignIncident();
		
		String strSrchIncNbr = "INC0010007";
		System.out.println("Searching for the Incident - " + strSrchIncNbr + " - in order to Assign it");
		
		// Step 1a: Click on "Open" in the left side bar
		driver.findElement(By.xpath("(//div[text()='Open'])[1]")).click();
		Thread.sleep(1000);

		// Step 1b: Switch to the right-side frame
		driver.switchTo().frame("gsft_main");

		// Step 1c: Enter the Incident number in the search box, and hit Enter key
		driver.findElement(By.xpath("(//label[text()='Search'])[2]/following-sibling::input")).sendKeys(strSrchIncNbr,
				Keys.ENTER);
		System.out.println("Assignment of incident starting.....");
		Thread.sleep(1000);

		// Step 2: Verify the incident is existing, and if yes, then update it

		int incFoundIndPre = objAssnInc.searchIncidentNbr(driver, strSrchIncNbr);

		if (incFoundIndPre == 1) {

			// Step 2a: Get the page title (of the incidents screen)
						String strPage2Title = driver.getTitle();
						
				// Step 2b: Click the assignment grouo
				driver.findElement(By.xpath("//button[@id='lookup.incident.assignment_group']")).click();
				Thread.sleep(1000);

				// Step 2d: Handle windows (because the earlier action opens a pop-up window)
				List<String> windowHandlesList1 = objAssnInc.handleWindows(driver);
				
				// Step 2e: Enter "Software" in the search box, and hit Enter
				driver.findElement(By.xpath("//label[text()='Search']//following-sibling::input")).sendKeys("Software",
						Keys.ENTER);
				Thread.sleep(1000);

				// Step 2e: Click the "Software" group to select
				driver.findElement(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a")).click();
				Thread.sleep(1000);

				// Step 2f: Switch to the parent window, and switch to the right-side frame
				driver.switchTo().window(windowHandlesList1.get(0));
				driver.switchTo().frame("gsft_main");

				// Step 2g: Update the incident with Work Notes
				// Scroll up the screen - till "Filters" so that the web elements can be
				// captured
				WebElement elemScrl = driver.findElement(By.xpath("//span[@id='status.incident.short_description']"));
				driver.executeScript("arguments[0].scrollIntoView();", elemScrl);

				driver.findElement(By.xpath("//textarea[@id='activity-stream-textarea']"))
						.sendKeys("This is the worknotes for the incident assignment to the Software Group");
				Thread.sleep(1000);

				// Step 1g: Click the update button
				driver.findElement(By.xpath("//button[@id='sysverb_update']")).click();
				Thread.sleep(1000);

				System.out.println("Incident " + strSrchIncNbr + " has been ASSIGNED successfully");

			} 

	}

}

/**
 * 
 */
package week5.Day1Assignment2;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

/**
 * @author Ramesh
 * 
 *         This class contains the test case for "Create Incident"
 */
public class SvcNowCreateIncident extends SvcNowCommonClass {

	@Test
	public void tsCreateNew() throws InterruptedException, IOException {

		/*
		 * Step 1: Click on Create new option and fill the mandatory fields
		 */

		System.out.println("Creating New Incident");
		SvcNowCreateIncident objCrInc = new SvcNowCreateIncident();

		// Step 1a: Click on Create new option
		driver.findElement(By.xpath("(//div[text()='Create New'])[1]")).click();
		Thread.sleep(1000);

		// Step 1b: Switch to the right-side frame (in which the "New" button is placed
		driver.switchTo().frame("gsft_main");

		// Step 1c: Click on the lens icon against "caller"
		driver.findElement(By.xpath("//button[@id='lookup.incident.caller_id']")).click();

		// Step 1d: Handle windows (because the earlier action opens a pop-up window)
		List<String> windowHandlesList1 = objCrInc.handleWindows(driver);

		// Step 1e: Select "name" in the drop down options
		WebElement elemSrchCritDrpDn = driver
				.findElement(By.xpath("//select[@class='form-control default-focus-outline']"));
		Select drpDnSrchCrit = new Select(elemSrchCritDrpDn);
		drpDnSrchCrit.selectByVisibleText("First name");
		Thread.sleep(1000);

		// Step 1f: Enter "Ram" in the search box, and hit Enter
		driver.findElement(By.xpath("(//label[@class='sr-only'])[2]/following-sibling::input")).sendKeys("Ram",
				Keys.ENTER);
		Thread.sleep(1000);

		// Step 1g: Select the first entry in the table (in the pop-up window), by
		// clicking the same
		driver.findElement(By.xpath("//tbody[@class='list2_body']/tr[2]/td[3]/a")).click();

		// Step 1h: Switch to the parent window, and switch to the right-side frame
		driver.switchTo().window(windowHandlesList1.get(0));
		driver.switchTo().frame("gsft_main");

		// Step 1i: Enter the short description
		driver.findElement(By.xpath("//input[@id='incident.short_description']"))
				.sendKeys("This incident is opened to track the call drops in the customer calls");

		// Step 1j: Read the incident number and save it as a variable
		String incNumber = driver.findElement(By.xpath("//input[@name='incident.number']")).getAttribute("value");
		System.out.println("The new incident Number that has been opened is : " + incNumber);
		Thread.sleep(1000);

		// Step 1k: click submit button at the top
		driver.findElement(By.xpath("//button[@id='sysverb_insert']")).click();
				
		
		/*
		 * Step 2: Verify the newly created incident (copy the incident number and paste
		 * it in search field and enter then verify the instance number created or not)
		 */
		
		System.out.println("Verifying if the New Incident has been created successfully....");

		objCrInc.validateIncidentNbr(driver, incNumber, "CREATE INCIDENT");
		
	}

}

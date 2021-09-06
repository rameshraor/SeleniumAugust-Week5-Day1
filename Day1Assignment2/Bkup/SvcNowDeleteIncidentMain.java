/**
 * 
 */
package week5.Day1Assignment2.Bkup;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author Ramesh
 * 
 *         This class contains the test case for "Update Existing Incident"
 */
public class SvcNowDeleteIncidentMain {

	public static void main(String[] args) throws InterruptedException, IOException {

		/*
		 * Step 1: Search for the existing incident and click on the incident
		 */

		System.out.println("Deleting an Existing Incident");
		SvcNowDeleteIncidentMain objDelInc = new SvcNowDeleteIncidentMain();
		
		ChromeDriver driver = objDelInc.LogIntoSvcNow();
		
		String strSrchIncNbr = "INC0000025";
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
				
				/*
				Alert alert = driver.switchTo().alert();
				Thread.sleep(2000);
				alert.accept();
				Thread.sleep(2000);
				*/
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
	
	public ChromeDriver LogIntoSvcNow() throws InterruptedException {

		// Step 0a: Launch the browser
		System.out.println("Launching the Chrome browser");

		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://dev108845.service-now.com"); // Ramesh's instance of the Service Now application
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		// Step 0b: Switch into the frame
		System.out.println("Logging into the Service Now application");

		WebElement frame1 = driver.findElement(By.xpath("//iframe[@id='gsft_main']"));
		driver.switchTo().frame(frame1);

		driver.findElement(By.xpath("//input[@id='user_name']")).sendKeys("admin");
		driver.findElement(By.xpath("//input[@id='user_password']")).sendKeys("P@ssword1");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		// Step 0c: The below statement will take the control back to the main page -
		// even though we have moved to another page
		driver.switchTo().defaultContent();

		// Step 0d: Search for 'Incident' in the filter navigator, and hit enter key
		System.out.println("Entering 'Incident' in the Filter navigator, and hitting Enter");

		driver.findElement(By.xpath("//input[@id='filter']")).sendKeys("Incident", Keys.ENTER);
		Thread.sleep(1000);
		
		return driver;

	}
	
	public int searchIncidentNbr(ChromeDriver driver, String srchIncNumber) throws InterruptedException {

		// Step 2a: Locate the incident number in the search results, and check if it is
		// the expected incident number
		WebElement elemSrchResInc = driver.findElement(By.xpath("//tbody[@class='list2_body']/tr/td[3]/a"));
		int incFoundInd = 0;

		if (elemSrchResInc.isDisplayed()) {

			System.out.println("Able to locate the incident in the screen");
			String actIncNbr = elemSrchResInc.getAttribute("aria-label");

			if (actIncNbr.contains(srchIncNumber)) {
				incFoundInd = 1;
				// Step 2b: The searched incident number is shown in the results  -->  Click on it, for update
				elemSrchResInc.click();
				Thread.sleep(1000);

			} else {
				incFoundInd = 2;
				System.out.println("Incident search NOT successful!!!  Expected : " + srchIncNumber
						+ " but Actual displayed here : " + actIncNbr);
			}

		} else {
			incFoundInd = 0;
			System.out.println("No results displayed for the incident number!!!");
		}
		
		return incFoundInd;
	}


}

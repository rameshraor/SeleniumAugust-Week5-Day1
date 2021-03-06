/**
 * 
 */
package week5.Day1Assignment2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author Ramesh
 * 
 *         This is the common class for the Service Now testing - Has Login
 *         method, as the before-method
 * 
 */
public class SvcNowCommonClass {

	public ChromeDriver driver;

	@BeforeClass
	public void LogIntoSvcNow() throws InterruptedException {

		// Step 0a: Launch the browser
		System.out.println("Launching the Chrome browser");

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
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

	}

	@AfterClass
	public void closeBrowser() {

		System.out.println("Executed the test case.... closing the browser!");
		driver.close();

	}

	public List<String> handleWindows(ChromeDriver driver) {

		Set<String> windowHandlesSet1 = driver.getWindowHandles();
		List<String> windowHandlesList1 = new ArrayList<String>(windowHandlesSet1);
		driver.switchTo().window(windowHandlesList1.get(1));

		return windowHandlesList1;

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

	public void validateIncidentNbr(ChromeDriver driver, String incNumber, String action)
			throws InterruptedException, IOException {

		// Step 2a - Search the same incident number in the next search screen as below.
		driver.findElement(By.xpath("(//label[text()='Search'])[2]/following-sibling::input")).sendKeys(incNumber,
				Keys.ENTER);
		Thread.sleep(1000);

		// Step 2b: Verify the incident search and the action are successful
		if (driver.findElement(By.xpath("//a[contains(@aria-label,'Open record')]")).isDisplayed()) {

			String actIncNbr = driver.findElement(By.xpath("//a[contains(@aria-label,'Open record')]"))
					.getAttribute("aria-label");

			if (actIncNbr.contains(incNumber)) {

				System.out.println("The action " + action + " for the Incident " + incNumber + " is successful!!!");

			} else {

				System.out.println("The action " + action + " for the Incident " + incNumber
						+ " is NOT successful!!!. Expected : " + incNumber + " but Actual displayed here : " + actIncNbr);
			}

		} else {

			System.out.println("No results displayed for the incident number!!!");

		}

	}

}

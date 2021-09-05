/**
 * 
 */
package week5.Day1Assignment2;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author Ramesh
 * 
 * This is the common class for the Service Now testing
 * 		-	Has Login method, as the before-method
 * 
 */
public class SvcNowCommonClass {
	
	public ChromeDriver driver;
	
	@BeforeMethod
	public void LogIntoSvcNow() throws InterruptedException {
		
		//Step 0a: Launch the browser
		System.out.println("Launching the Chrome browser");
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://dev108845.service-now.com");					// Ramesh's instance of the Service Now application
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
	
	@AfterMethod
	public void closeBrowser() {
		
		System.out.println("Executed the test case.... closing the browser!");
		driver.close();
		
	}

}

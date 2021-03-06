/**
 * 
 */
package week5.Day1Assignment1;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author Ramesh
 * 
 * This is the base class - with pre-condition and post-condition
 */
public class BaseClass {
	
	public ChromeDriver driver;
	
	@BeforeMethod
	public void preCondition() {
		
		System.out.println("In preCondition() method");
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://leaftaps.com/opentaps/");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.id("username")).sendKeys("DemoSalesManager");
		driver.findElement(By.id("password")).sendKeys("crmsfa");
		driver.findElement(By.className("decorativeSubmit")).click();
		driver.findElement(By.linkText("CRM/SFA")).click();
		driver.findElement(By.linkText("Leads")).click();
		
	}
	
	@AfterMethod
	public void postCondition() {
		
		System.out.println("In postCondition() method");
		driver.close();
		
	}

}

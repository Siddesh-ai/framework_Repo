package webelements_programs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class p3 {

	public static void main(String[] args) {
		/* sets the path for chromedriver.exe */
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		/* launches the chrome browser */
		WebDriver driver = new ChromeDriver();

		/* Maximize the browser window */
		driver.manage().window().maximize();

		/* fetches the web app under test and waits until it is loaded */
		driver.get("https://app.elorus.com/login/");

		/* identifies the textbox with id= id_email */
		driver.findElement(By.id("id_email")).sendKeys("selenium108@gmail.com");

		/* identifies the username with id= id_email */
		driver.findElement(By.id("id_password")).sendKeys("Selenium003");

		/* identifies the login button and clicks */
		driver.findElement(By.xpath("//input[@value='SIGN IN']")).click();

	}

}

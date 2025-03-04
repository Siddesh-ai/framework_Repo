package practice.orgtest;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;
import java.util.logging.FileHandler;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class CreateOrganizationWithIndustriesTest {

	public static void main(String[] args) throws Throwable {

		// read comman data from properties file
		FileInputStream fis = new FileInputStream("./src/main/resources/property_file/Commandata.properties");
		Properties pObj = new Properties();
		pObj.load(fis);

		String BROWSER = pObj.getProperty("browser");
		String URL = pObj.getProperty("url");
		String USERNAME = pObj.getProperty("username");
		String PASSWORD = pObj.getProperty("password");

		// generate the ramdom number
		Random random = new Random();
		int randomint = random.nextInt(10000);

		// Read testScript data from Excel file
		FileInputStream fis1 = new FileInputStream("./src/main/resources/property_file/testScriptData.xlsx");
		Workbook Wb = WorkbookFactory.create(fis1);
		Sheet sh = Wb.getSheet("org");
		Row row = sh.getRow(4);
		String orgName = row.getCell(2).toString() + randomint;
		String industry = row.getCell(3).toString();
		String type = row.getCell(4).toString();
		Wb.close();

		WebDriver driver = null;

		if (BROWSER.equals("Chrome")) {
			driver = new ChromeDriver();
		} else if (BROWSER.equals("FireFox")) {
			driver = new FirefoxDriver();
		} else if (BROWSER.equals("Edge")) {
			driver = new EdgeDriver();
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();

		// Step 1 : login to app
		driver.get(URL);

		driver.findElement(By.name("user_name")).sendKeys(USERNAME);
		driver.findElement(By.name("user_password")).sendKeys(PASSWORD);
		driver.findElement(By.id("submitButton")).click();

		// Step 2 : navigate to organization module
		driver.findElement(By.linkText("Organizations")).click();

		// Step 3 : Click on "create Organization" Button
		driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();

		// Step 4 : enter all the details & create new organizations
		driver.findElement(By.name("accountname")).sendKeys(orgName);

		WebElement wbsele1 = driver.findElement(By.name("industry"));
		File srcFile = wbsele1.getScreenshotAs(OutputType.FILE);
		File destFile = new File("./SeleniumCRMGUIFramework/ScreenShots");
	    org.openqa.selenium.io.FileHandler.copy(srcFile, destFile);
		Select sel1 = new Select(wbsele1);
		sel1.selectByVisibleText(industry);

		WebElement wbsel2 = driver.findElement(By.name("accounttype"));
		Select sel2 = new Select(wbsel2);
		sel2.selectByVisibleText(type);

		driver.findElement(By.xpath("(//input[@title='Save [Alt+S]'])[1]")).click();

		// verify the industries info
		String actIndustries = driver.findElement(By.id("dtlview_Industry")).getText();
		if (actIndustries.equals(industry)) {
			System.out.println(industry + "  information is created===PASS");
		} else {
			System.out.println(industry + "  information is not created===Fail");

		}

		// verify the type info
		String actType = driver.findElement(By.id("dtlview_Type")).getText();
		if (actType.equals(type)) {
			System.out.println(type + "  information is created===PASS");
		} else {
			System.out.println(type + "  information is not created===Fail");

		}

		driver.quit();
	}
}

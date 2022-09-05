package sudhanshu.grampower;

import java.time.Duration;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class GramPowerAutomation {

	static WebDriver driver;
	@BeforeTest()
	public void setup() {		
		String baseUrl= "https://data.grampower.com/hes/";
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get(baseUrl);
	}
	
	@Test(priority=1)
	public void Login() {
		driver.findElement(By.name("username")).sendKeys("sudhanshu");
		driver.findElement(By.name("password")).sendKeys("grampower");
		driver.findElement(By.xpath("//input[@value='LOG IN']")).click();
	}
	
	@Test(priority=2)
	public void NavigatetoMap() {
		driver.findElement(By.xpath("//button[@id=\"onesignal-slidedown-allow-button\"]/following-sibling::button")).click();
		driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"retail_dashboard\"]/iframe")));
		driver.findElement(By.id("div_site_count")).click();
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("//div[@id='page']/section[1]//div/section/div/div[5]/div[1]/div[2]")).click();
	}
	
	@Test(priority=3)
	public void searchConsumer() throws InterruptedException {
		driver.findElement(By.xpath("//input[@id='search_tb']")).sendKeys("ROFANANDAA708");
		driver.findElement(By.id("search_bt")).click();
		Thread.sleep(5000);
	}
	
	@Test(priority=4)
	public void ValidateConsumerInfo() {
		String assetSeq= "REALESTATE/REALESTATE FEEDER/DT-11174/C1/P1/ROFANANDAA708";
		String consumerName ="A_708";
		String scNo ="ROFANANDAA708";
		String meterIP="5.0.133.86";
		String meterSno= "212049";
		String ctRatio ="1";
		String supplyType= "1-Ph";
		String meterType= "DLMS";
		String sTime= "2021-10-26 14:38:49";
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()=' REALESTATE/REALESTATE FEEDER/DT-11174/C1/P1/ROFANANDAA708']")).getText(),assetSeq);	
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()=' A_708']")).getText(),consumerName);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()=' ROFANANDAA708']")).getText(),scNo);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()=' 5.0.133.86']")).getText(),meterIP);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()=' 212049']")).getText(),meterSno);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()=' 1']")).getText(),ctRatio);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()=' 1-Ph']")).getText(),supplyType);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()=' DLMS']")).getText(),meterType);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()=' 2021-10-26 14:38:49 ']")).getText(),sTime);
		}
	
	@Test(priority=5)
	public void ValidateMeter() throws InterruptedException {
		driver.findElement(By.xpath("//a[@id='info']")).click();
		Thread.sleep(2000);
		System.out.println("*********Fetching Data From Meter Tab**************");
		System.out.println("Current Reading kWh: "+driver.findElement(By.xpath("//span[@id='current_reading']")).getText());
		System.out.println("Current Reading kVAh: "+driver.findElement(By.xpath("//span[@id='current_reading_kvah']")).getText());
		System.out.println("Current Reading kVArh: "+driver.findElement(By.xpath("//span[@id='current_reading_kvarh']")).getText());
		System.out.println("Consumed Energy: "+driver.findElement(By.xpath("//label[@id='consumed_energy']")).getText());
		System.out.println("Instantaneous Load: "+driver.findElement(By.xpath("//span[@id='connected_load']")).getText());
		System.out.println("Last Data Point: "+driver.findElement(By.xpath("//span[@id='ldp1']")).getText());
		System.out.println("Tamper: "+driver.findElement(By.xpath("//span[@id='tamper1']")).getText());
		System.out.println("***********************");
	}
	
	@Test(priority=6)
	public void ValidateCommand() throws InterruptedException {
		driver.findElement(By.xpath("//a[@id='id_load_command']")).click();
		Thread.sleep(5000);
		String cmd= "Select a command-";
		Assert.assertEquals(driver.findElement(By.xpath("(//h4[contains(text(),'Select a command')])[1]")).getText(),cmd);
		
	}
	
	@Test(priority=7)
	public void ValidateRealtime() throws InterruptedException {
		driver.findElement(By.xpath("//a[@id='rt']")).click();
		Thread.sleep(2000);
		String txt= "Showing energy consumption from";
		if(driver.findElement(By.id("realtime_title")).getText().contains(txt)) {
			System.out.println("Text Matched");
		}
		
	}
	
	@Test(priority=8)
	public void ValidateMore() throws InterruptedException {
		driver.findElement(By.xpath("//a[@id='alerts']")).click();
		String alertMessage= "No alerts found !";
		Thread.sleep(2000);
		Assert.assertEquals(driver.findElement(By.xpath("//div[contains(text(),'No alerts found !')]")).getText(),alertMessage);
		driver.findElement(By.xpath("//a[contains(text(),'Uptime')]")).click();
		driver.findElement(By.xpath("(//a[text()='Images'])[1]")).click();
	}
	
	
	@AfterTest
	public void close() {
		driver.quit();
	}
	
}



package QABootcam_MAVEN.OrangeHRM.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {
	private WebDriver driver;
	private By adminMenu = By.xpath("(//a[contains(@class,'oxd-main-menu-item')])[1]");
	private By jobMenu = By.xpath("(//li[contains(@class,'topbar')])[2]");
	private By organizationMenu = By.xpath("(//li[contains(@class,'topbar')])[3]");
	private By workShiftMenu = By.xpath("//a[text()='Work Shifts']");
	private By locationsMenu = By.xpath("//a[text()='Locations']");
	private By generalInfoMenu = By.xpath("//a[text()='General Information']");
	
	public MainPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void goToWorkShiftPage() {
		driver.findElement(adminMenu).click();
		driver.findElement(jobMenu).click();
		driver.findElement(workShiftMenu).click();
	}
	
	public void goToLocationsPage() {
		driver.findElement(adminMenu).click();
		driver.findElement(organizationMenu).click();
		driver.findElement(locationsMenu).click();
	}
	
	public void goToGeneralInfoPage() {
		driver.findElement(adminMenu).click();
		driver.findElement(organizationMenu).click();
		driver.findElement(generalInfoMenu).click();
	}

}

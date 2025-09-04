package QABootcam_MAVEN.OrangeHRM.Pages;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LocationsPage {
	private WebDriver driver;
	private By pageHeader = By.xpath("//h5[text()='Locations']");
	private By arrowButton = By.xpath("(//button[@class='oxd-icon-button'])[2]");
	private By addButton = By.xpath("(//button[contains(@class,'button--secondary')])[2]");
	private By nameFieldR = By.xpath("(//input[@placeholder='Type here ...'])[1]");
	private By cityField = By.xpath("(//input[@placeholder='Type here ...'])[2]");
	private By stateField = By.xpath("(//input[@placeholder='Type here ...'])[3]");
	private By zipCodeField = By.xpath("(//input[@placeholder='Type here ...'])[4]");
	private By countryDropDownR = By.xpath("//div[contains(@class,'oxd-select-text--active')]");
	private By phoneField = By.xpath("(//input[@placeholder='Type here ...'])[5]");
	private By faxField = By.xpath("(//input[@placeholder='Type here ...'])[6]");
	private By addressField = By.xpath("(//textarea[@placeholder='Type here ...'])[1]");
	private By noteField = By.xpath("(//textarea[@placeholder='Type here ...'])[2]");
	private By saveButton = By.xpath("//button[@type='submit']");
	private By deleteIcon = By.xpath("(//button[contains(@class,'oxd-icon-button')])[4]");
	private By deleteButton = By.xpath("(//button[contains(@class,'label-danger')])[1]");
	private By delete2Button = By.xpath("(//button[contains(@class,'label-danger')])[2]");
	private By allCheckBox = By.xpath("(//span[contains(@class,'oxd-checkbox-input')])[1]");
	private By editIcon = By.xpath("(//button[contains(@class,'oxd-icon-button')])[5]");
	private By resetButton = By.xpath("//button[text()=' Reset ']");
	private By nameSearchField = By.xpath("//label[text()='Name']/../following-sibling::div//input");
	

	public LocationsPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void clickAddButton() {
		driver.findElement(addButton).click();
		
	}
	
	public void clickSaveButton() {
		driver.findElement(saveButton).sendKeys(Keys.RETURN);
		
	}
	
	public void clickDeleteButton() {
		driver.findElement(deleteIcon).click();
		driver.findElement(deleteButton).click();
	}
	
	public void clickDeleteAll() throws InterruptedException {
		driver.findElement(allCheckBox).click();
		Thread.sleep(3000);
		driver.findElement(deleteButton).click();
		driver.findElement(delete2Button).click();

	}
	
	public void resetButton() throws InterruptedException {
		driver.findElement(nameSearchField).sendKeys("Bethlehem");
		Thread.sleep(3000);
		driver.findElement(resetButton).click();
		Thread.sleep(3000);

	}
	
	public void clickEditButton(String name) {
		driver.findElement(editIcon).click();
		//driver.findElement(By.xpath("(//input[contains(@class,'input--active')])[2]")).click();
		//driver.findElement(By.xpath("(//input[contains(@class,'input--active')])[2]")).clear();
		for(int counter = 0; counter < 50; counter++) {
			driver.findElement(By.xpath("(//input[contains(@class,'input--active')])[2]")).sendKeys(Keys.BACK_SPACE);
		}
		driver.findElement(By.xpath("(//input[contains(@class,'input--active')])[2]")).sendKeys(name);
		
	}
	
	public void addLocations(String name) {
		driver.findElement(nameFieldR).sendKeys(name);
		driver.findElement(cityField).sendKeys("Bethlehem");
		driver.findElement(stateField).sendKeys("Beit Sahour");
		driver.findElement(zipCodeField).sendKeys("00970");
		driver.findElement(countryDropDownR).click();
		List<WebElement> options = driver.findElements(By.xpath("//div[@role='listbox']"));
		Random rand = new Random();
		options.get(rand.nextInt(options.size())).click();
		driver.findElement(phoneField).sendKeys("0598621006");
		driver.findElement(faxField).sendKeys("022748613");
		driver.findElement(addressField).sendKeys("Beit Sahour, Souk Al Shaab");
		driver.findElement(noteField).sendKeys("Note Note Note");
		
	}
	
	public boolean isAddButtonEnabled() {
		return driver.findElement(addButton).isEnabled();
		
	}
	
	public boolean isLocationsPageDisplayed() {
        return driver.findElement(pageHeader).isDisplayed();
        
    }
	
	public boolean isArrowButtonDisplayed() throws InterruptedException {
		driver.findElement(arrowButton).click();
		Thread.sleep(2000);
		driver.findElement(arrowButton).click();
		Thread.sleep(2000);
        return driver.findElement(arrowButton).isDisplayed();
        
    }
	
	

}

package QABootcam_MAVEN.OrangeHRM.Pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WorkShiftPage {
	private WebDriver driver;
	private By addButton = By.xpath("(//button[@type='button'])[4]");
	private By shiftName = By.xpath("(//input[contains(@class,'input')])[2]");
	private By fromHours = By.xpath("(//input[contains(@class,'oxd-input')])[3]");
	private By toHours = By.xpath("(//input[contains(@class,'oxd-input')])[4]");
	private By saveButton = By.xpath("//button[contains(@class,'oxd-button--secondary')]");
	private By workShiftTable = By.xpath("//div[@class='oxd-table-body']");
	private By errorMessage = By.xpath("//span[contains(@class,'error-message')]");
	private By editButton = By.xpath("(//button[contains(@class,'oxd-icon-button')])[4]");
	private By deleteIcon = By.xpath("(//button[contains(@class,'oxd-icon-button')])[3]");
	private By deleteButton = By.xpath("//button[contains(@class,'label-danger')]");
	private By rowTableWSName = By.cssSelector(".oxd-table-cell:nth-child(2)");
	private By cancelButton = By.xpath("//button[contains(@class,'ghost')]");
	private By checkBox = By.xpath("(//span[contains(@class,'oxd-checkbox-input')])[2]");
	private By deleteCheckBox = By.xpath("(//button[contains(@class,'label-danger')])[2]");
	private By allCheckBoxes = By.xpath("(//i[contains(@class,'bi-check')])[1]");
	private By deleteAll = By.xpath("(//button[contains(@class,'label-danger')])[1]");

	public WorkShiftPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void addButtonWS() {
		driver.findElement(addButton).click();
		
	}
	
	public void editButtonWS(String name) {
		driver.findElement(editButton).click();
		driver.findElement(shiftName).click();
		driver.findElement(shiftName).clear();
		driver.findElement(shiftName).sendKeys(name);
		
	}
	
	public void deleteButtonWS() {
		driver.findElement(deleteIcon).click();
		driver.findElement(deleteButton).click();
		
	}
	
	public void cancelButtonWS() {
		driver.findElement(cancelButton).sendKeys(Keys.RETURN);
		
	}
	
	public void addWorkShift(String name, String from, String to) {
		driver.findElement(shiftName).sendKeys(name);
		driver.findElement(fromHours).clear();
		driver.findElement(fromHours).sendKeys(from);
		driver.findElement(toHours).clear();
		driver.findElement(toHours).sendKeys(to);
		
	}
	
	public void saveButtonWS() {
		driver.findElement(saveButton).sendKeys(Keys.RETURN);
		
	}
	
	public void selectCheckBox() {
		driver.findElement(checkBox).click();
		driver.findElement(deleteIcon).click();
		driver.findElement(deleteCheckBox).click();
		
	}
	
	public void selectAllCheckBoxes() throws InterruptedException {
		Thread.sleep(2000);
		driver.findElement(allCheckBoxes).click();
		Thread.sleep(2000);
		driver.findElement(deleteAll).click();
		Thread.sleep(2000);
		driver.findElement(deleteCheckBox).click();
		Thread.sleep(5000);
		
	}
	
	public boolean isShiftAdded(String shift_name) {
        WebElement table = driver.findElement(workShiftTable);
        return table.getText().contains(shift_name);
        
    }
	
	public int isShiftDeletedBefore() {
	    List<WebElement> beforeShifts = driver.findElements(By.xpath("//div[@class='oxd-table-card']"));
	    int countBefore = beforeShifts.size();
	    List <String> shNames = new ArrayList<>();
	    for(WebElement row : beforeShifts) {
	    	String shiftName = row.findElement(rowTableWSName).getText();
	    	shNames.add(shiftName);
	    }
	    return countBefore;
	    
	}
	
	public int isShiftDeletedAfter() {
		List<WebElement> rowsAfter = driver.findElements(By.cssSelector("div.oxd-table-card"));
		int rowCountAfter = rowsAfter.size();
		List <String> shNames = new ArrayList<>();
	    for(WebElement row : rowsAfter) {
	    	String shiftName = row.findElement(rowTableWSName).getText();
	    	shNames.add(shiftName);
	    }
	    return rowCountAfter;
	    
	}
	
	public boolean isCancelWorked() {
		return driver.findElement(cancelButton).isDisplayed();
	}
	
	public String getHoursPerDay(String shiftName) {
	    WebElement row = driver.findElement(By.xpath("//div[text()='" + shiftName + "']/ancestor::div[@class='oxd-table-card']"));
	    WebElement hoursCell = row.findElement(By.xpath("(//div[contains(@class,'oxd-table-cell')])[5]"));
	    return hoursCell.getText();
	    
	}
	
	public String requiredMessage() {
		String validationMsg = driver.findElement(errorMessage).getText();
		return validationMsg;
		
	}

}

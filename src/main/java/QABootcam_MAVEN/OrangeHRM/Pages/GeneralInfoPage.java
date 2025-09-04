package automationProjectOrangeHRM.Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GeneralInfoPage {
	private WebDriver driver;
	private By pageHeader = By.xpath("//h6[text()='General Information']");
	private By orgNameFieldD = By.xpath("(//input[@disabled])[1]");
    private By regNumberFieldD = By.xpath("(//input[@disabled])[2]");
    private By taxIdFieldD = By.xpath("(//input[@disabled])[3]");
    private By saveButton = By.xpath("//button[@type='submit']");
    private By editButton = By.xpath("//input[@type='checkbox']");
    private By orgNameF = By.xpath("(//input[contains(@class,'oxd-input')])[2]");
    private By regNumberF = By.xpath("(//input[contains(@class,'oxd-input')])[3]");
    private By taxIdF = By.xpath("(//input[contains(@class,'oxd-input')])[4]");
    private By requiredMessage = By.xpath("//span[contains(@class,'oxd-input-field-error-message')]");

	public GeneralInfoPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getPageHeader() {
        return driver.findElement(pageHeader).getText();
    }
	
	public void clickEdit() {
	    driver.findElement(editButton).click();
	}
	
	public void enterOrgDetails(String orgName, String regNumber, String taxId) {
	    driver.findElement(orgNameF).clear();
	    driver.findElement(orgNameF).sendKeys(orgName);

	    driver.findElement(regNumberF).clear();
	    driver.findElement(regNumberF).sendKeys(regNumber);
	    
	    driver.findElement(taxIdF).clear();
	    driver.findElement(taxIdF).sendKeys(taxId);
	    
	}
	
	public void clickSaveButton() {
		driver.findElement(saveButton).click();
		
	}
	
	public void clearOrgName() {
		for(int number = 0; number < 50; number++)
			driver.findElement(orgNameF).sendKeys(Keys.BACK_SPACE);

	}
	
	public boolean isFieldDisabled(By locator) {
        String disabled = driver.findElement(locator).getAttribute("disabled");
        return disabled != null && disabled.equals("true");
    }
	
	public boolean isSaveButtonDisabled() {
		List<WebElement> saveBtn = driver.findElements(saveButton);
	    return !saveBtn.isEmpty() && saveBtn.get(0).isDisplayed();
	}
	
	public boolean isSaveButtonEnabled() {
	    WebElement saveBtn = driver.findElement(saveButton);
	    return saveBtn.isDisplayed() && saveBtn.isEnabled();
	}
	
	public boolean isOrgNameDisabled() {
        return isFieldDisabled(orgNameFieldD);
    }

    public boolean isRegNumberDisabled() {
        return isFieldDisabled(regNumberFieldD);
    }

    public boolean isTaxIdDisabled() {
        return isFieldDisabled(taxIdFieldD);
    }
    
    public String getValidationError() {
        return driver.findElement(requiredMessage).getText();
    }

}

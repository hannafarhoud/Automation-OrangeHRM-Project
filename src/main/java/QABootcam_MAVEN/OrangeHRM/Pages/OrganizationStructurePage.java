package QABootcam_MAVEN.OrangeHRM.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrganizationStructurePage {

	private WebDriver driver;
	private WebDriverWait wait;

	public OrganizationStructurePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	private By adminMenu = By.xpath("//span[text()='Admin']");
	private By orgDropdown = By.xpath("//nav//li[3]/span");
	private By structureLink = By.xpath("//a[text()='Structure']");
	private By header = By.xpath("//h6[text()='Organization Structure']");
	private By editToggle = By.cssSelector("span.oxd-switch-input");
	private By addBtn = By.cssSelector("button.oxd-button.org-structure-add");
	private By saveBtn = By.cssSelector("button.oxd-button.oxd-button--secondary.orangehrm-left-space");
	private By cancelBtn = By.cssSelector("button.oxd-button.oxd-button--ghost");
	private By unitIdField = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
	private By nameField = By.xpath("(//input[@class='oxd-input oxd-input--active'])[3]");
	private By descField = By.cssSelector("textarea.oxd-textarea.oxd-textarea--active");

	public void navigateToStructurePage() {
		wait.until(ExpectedConditions.elementToBeClickable(adminMenu)).click();
		wait.until(ExpectedConditions.elementToBeClickable(orgDropdown)).click();
		wait.until(ExpectedConditions.elementToBeClickable(structureLink)).click();
	}

	public boolean isStructurePageDisplayed() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(header)).isDisplayed();
	}

	public void toggleEditMode() {
		WebElement toggle = wait.until(ExpectedConditions.elementToBeClickable(editToggle));
		toggle.click();
	}

	public boolean isEditEnabled() {
		try {
			WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(addBtn));
			return addButton.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void addUnit(String name, String id, String desc) {
		wait.until(ExpectedConditions.elementToBeClickable(addBtn)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
		wait.until(ExpectedConditions.visibilityOfElementLocated(unitIdField)).sendKeys(id);
		wait.until(ExpectedConditions.visibilityOfElementLocated(descField)).sendKeys(desc);
		wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
	}

	public void addSubUnit(String parentUnitFullName, String subUnitName) {
		By overlay = By.cssSelector("div.oxd-dialog-container-default");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));

		WebElement parentRow = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[contains(@class,'org-structure-card')]//div[text()='"
						+ parentUnitFullName + "']/ancestor::div[contains(@class,'org-structure-card')]")));

		WebElement plusBtn = parentRow.findElement(By.xpath(".//button[i[contains(@class,'bi-plus')]]"));
		wait.until(ExpectedConditions.elementToBeClickable(plusBtn)).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(subUnitName);
		wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();

	}

	public boolean isUnitPresent(String unitFullName) {
		try {
			List<WebElement> units = driver.findElements(
					By.xpath("//div[contains(@class,'org-structure-card')]//div[text()='" + unitFullName + "']"));
			return units.size() > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public void expandUnit(String unitFullName) {
		By overlay = By.cssSelector("div.oxd-dialog-container-default");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(overlay));

		WebElement parentLi = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//li[contains(@class,'--parent')][.//div[text()='" + unitFullName + "']])[2]")));

		WebElement toggleBtn = parentLi
				.findElement(By.xpath(".//span[contains(@class,'oxd-tree-node-toggle')]/button"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", toggleBtn);

		String liClass = parentLi.getAttribute("class");
		if (!liClass.contains("--open")) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", toggleBtn);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}

	public void clickAddUnit() {
		wait.until(ExpectedConditions.elementToBeClickable(addBtn)).click();
	}

	public void clickAddSubUnit(String parentUnitFullName) {
		WebElement parentRow = driver.findElement(By.xpath(
				"//div[text()='" + parentUnitFullName + "']/ancestor::div[contains(@class,'org-structure-card')]"));
		parentRow.findElement(By.xpath(".//button[i[contains(@class,'bi-plus')]]")).click();
	}

	public void enterUnitId(String id) {
		driver.findElement(unitIdField).sendKeys(id);
	}

	public void enterUnitDescription(String desc) {
		driver.findElement(descField).sendKeys(desc);
	}

	public void clickSave() {
		driver.findElement(saveBtn).click();
	}

	public void clickCancel() {
		waitForLoaderToDisappear();
		driver.findElement(cancelBtn).click();
	}

	public void clearNameField() {
		WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(nameField));
		nameInput.click();

		String currentValue = nameInput.getAttribute("value");
		if (currentValue != null && !currentValue.isEmpty()) {
			int length = currentValue.length();
			for (int counter = 0; counter < length; counter++) {
				nameInput.sendKeys(Keys.BACK_SPACE);
			}
		}
	}

	public void editUnit(String unitFullName) {
		WebElement unitRow = driver.findElement(
				By.xpath("//div[text()='" + unitFullName + "']/ancestor::div[contains(@class,'org-structure-card')]"));
		WebElement editBtn = unitRow
				.findElement(By.cssSelector("button.oxd-icon-button.org-action-icon i.bi-pencil-fill"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", editBtn.findElement(By.xpath("./..")));
	}

	public boolean isNameFieldErrorDisplayed() {
		try {
			return driver.findElement(By.xpath("//span[text()='Required']")).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void waitForLoaderToDisappear() {
		By loader = By.cssSelector("div.oxd-form-loader");
		wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));
	}

	public void clearAndEnterName(String newName) {
		WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(nameField));
		nameInput.click();

		String currentValue = nameInput.getAttribute("value");
		if (currentValue != null && !currentValue.isEmpty()) {
			int length = currentValue.length();
			for (int counter = 0; counter < length; counter++) {
				nameInput.sendKeys(Keys.BACK_SPACE);
			}
		}

		nameInput.sendKeys(newName);
	}

	private By confirmDeleteBtn = By
			.cssSelector("button.oxd-button.oxd-button--medium.oxd-button--label-danger.orangehrm-button-margin");

	public void deleteUnit(String unitFullName) {
		WebElement unitRow = driver.findElement(
				By.xpath("//div[text()='" + unitFullName + "']/ancestor::div[contains(@class,'org-structure-card')]"));
		WebElement deleteBtn = unitRow.findElement(By.xpath(".//button[i[contains(@class,'bi-trash')]]"));
		wait.until(ExpectedConditions.elementToBeClickable(deleteBtn)).click();

		wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteBtn)).click();

		wait.until(ExpectedConditions.invisibilityOf(unitRow));
	}

	public boolean isUnitAbsent(String unitFullName) {
		List<WebElement> units = driver.findElements(
				By.xpath("//div[contains(@class,'org-structure-card')]//div[contains(text(),'" + unitFullName + "')]"));
		return units.isEmpty();
	}

}
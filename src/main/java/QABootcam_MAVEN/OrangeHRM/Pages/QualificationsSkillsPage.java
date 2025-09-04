package QABootcam_MAVEN.OrangeHRM.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.Keys;

public class QualificationsSkillsPage {

	private WebDriver driver;
	private WebDriverWait wait;

	public QualificationsSkillsPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	private By adminTab = By.xpath("//span[text()='Admin']");
	private By qualificationsMenu = By.xpath("//nav//li[4]/span");
	private By skillsOption = By.xpath("//a[text()='Skills']");
	private By skillsHeader = By.xpath("//h6[text()='Skills']");

	private By addButton = By.xpath("//h6[text()='Skills']/following::button[./i[contains(@class,'bi-plus')]][1]");
	private By nameInput = By.xpath("//label[text()='Name']/following::input[1]");
	private By descriptionInput = By.xpath("//textarea[@placeholder='Type description here']");
	private By saveButton = By.xpath("//button[.=' Save ']");
	private By cancelButton = By.xpath("//button[.=' Cancel ']");

	private By skillsTableRows = By.xpath("//div[@class='oxd-table-body']//div[contains(@class,'oxd-table-card')]");
	private By nameError = By.xpath("//span[text()='Required']");

	private By deleteButton = By.xpath(".//button[i[contains(@class,'bi-trash')]]"); 
	private By yesDeleteButton = By.xpath("//button[.=' Yes, Delete ']");

	private By checkbox = By.xpath(".//i[contains(@class,'bi-check')]");
	private By deleteSelectedButton = By.xpath("//button[contains(., 'Delete Selected')]");

	private By editButton = By.xpath(".//button[i[contains(@class,'bi-pencil-fill')]]"); 
	private By editNameInput = By.xpath("//label[text()='Name']/following::input[1]");
	private By editDescriptionInput = By.xpath("//textarea[contains(@class,'oxd-textarea--active')]");
	private By saveEditButton = By.xpath("//button[.=' Save ']");
	private By cancelEditButton = By.xpath("//button[.=' Cancel ']");

	public void navigateToSkillsPage() {
		wait.until(ExpectedConditions.elementToBeClickable(adminTab)).click();
		wait.until(ExpectedConditions.elementToBeClickable(qualificationsMenu)).click();
		wait.until(ExpectedConditions.elementToBeClickable(skillsOption)).click();
	}

	public boolean isSkillsPageDisplayed() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(skillsHeader)).isDisplayed();
	}

	public void clickAddButton() {
		wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
	}

	public void enterSkillName(String name) {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput));
		element.clear();
		element.sendKeys(name);
	}

	public void enterSkillDescription(String description) {
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(descriptionInput));
		element.clear();
		element.sendKeys(description);
	}

	public void clickSaveButton() {
		wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
	}

	public void clickCancelButton() {
		wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
	}

	public boolean isSkillPresent(String skillName) {
		int attempts = 0;
	    while (attempts < 3) {
	        try {
	            List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(skillsTableRows));
	            boolean found = rows.stream().anyMatch(row -> {
	                String rowName = row.getText().split("\n")[0].trim(); 
	                return rowName.equals(skillName);
	            });
	            return found;
	        } catch (Exception e) {
	            attempts++;
	            try { Thread.sleep(1000); } catch (InterruptedException ex) {}
	        }
	    }
	    return false;
	}

	public boolean isNameErrorDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(nameError)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void deleteSkill(String skillName) {
		List<WebElement> rows = driver.findElements(skillsTableRows);
		for (WebElement row : rows) {
			if (row.getText().contains(skillName)) {
				row.findElement(deleteButton).click();
				wait.until(ExpectedConditions.elementToBeClickable(yesDeleteButton)).click();
				wait.until(ExpectedConditions.invisibilityOf(row));
				break;
			}
		}
	}

	public void selectSkills(String... skillNames) {
		List<WebElement> rows = driver.findElements(skillsTableRows);
		for (String skillName : skillNames) {
			for (WebElement row : rows) {
				if (row.getText().contains(skillName)) {
					row.findElement(checkbox).click();
					break;
				}
			}
		}
	}

	public void clickDeleteSelected() {
		WebElement deleteBtn = wait.until(ExpectedConditions.presenceOfElementLocated(deleteSelectedButton));
		wait.until(ExpectedConditions.elementToBeClickable(deleteBtn));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn); 
		wait.until(ExpectedConditions.elementToBeClickable(yesDeleteButton)).click();
	}

	public void clickEditSkill(String skillName) {
		List<WebElement> rows = driver.findElements(skillsTableRows);
		for (WebElement row : rows) {
			if (row.getText().contains(skillName)) {
				row.findElement(editButton).click();
				break;
			}
		}
	}

	public void enterSkillNameEdit(String name) {
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(editNameInput));
		element.click();

		String currentValue = element.getAttribute("value");
		if (currentValue != null && !currentValue.isEmpty()) {
			int length = currentValue.length();
			for (int counter = 0; counter < length; counter++) {
				element.sendKeys(Keys.BACK_SPACE);
			}
		}

		element.sendKeys(name);
	}

	public void enterSkillDescriptionEdit(String description) {
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(editDescriptionInput));
		element.click();

		String currentValue = element.getAttribute("value");
		if (currentValue != null && !currentValue.isEmpty()) {
			int length = currentValue.length();
			for (int counter = 0; counter < length; counter++) {
				element.sendKeys(Keys.BACK_SPACE);
			}
		}

		element.sendKeys(description);
	}

	public void clickSaveEditButton() {
		wait.until(ExpectedConditions.elementToBeClickable(saveEditButton)).click();
	}

	public void clickCancelEditButton() {
		wait.until(ExpectedConditions.elementToBeClickable(cancelEditButton)).click();
	}
}

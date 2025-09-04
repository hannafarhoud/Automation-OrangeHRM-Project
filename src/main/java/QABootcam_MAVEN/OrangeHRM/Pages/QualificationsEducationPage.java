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

public class QualificationsEducationPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public QualificationsEducationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    private By adminTab = By.xpath("//span[text()='Admin']");
    private By qualificationsMenu = By.xpath("//nav//li[4]/span");
    private By educationOption = By.xpath("//a[text()='Education']");
    private By educationHeader = By.xpath("//h6[text()='Education']");
    private By addButton = By.xpath("//h6[text()='Education']/following::button[./i[contains(@class,'bi-plus')]][1]");
    private By levelInput = By.xpath("//label[text()='Level']/following::input[1]");
    private By saveButton = By.xpath("//button[.=' Save ']");
    private By cancelButton = By.xpath("//button[.=' Cancel ']");
    private By educationTableRows = By.xpath("//div[@class='oxd-table-body']//div[contains(@class,'oxd-table-card')]");
    private By levelError = By.xpath("//span[text()='Required']");
    private By deleteButton = By.xpath(".//button[i[contains(@class,'bi-trash')]]"); 
    private By yesDeleteButton = By.xpath("//button[.=' Yes, Delete ']");
    private By checkbox = By.xpath(".//i[contains(@class,'bi-check')]");
    private By deleteSelectedButton = By.xpath("//button[contains(., 'Delete Selected')]");
    private By editButton = By.xpath(".//button[i[contains(@class,'bi-pencil-fill')]]"); 
    private By editLevelInput = By.xpath("//label[text()='Level']/following::input[1]");
    private By saveEditButton = By.xpath("//button[.=' Save ']");
    private By cancelEditButton = By.xpath("//button[.=' Cancel ']");

    public void navigateToEducationPage() {
        wait.until(ExpectedConditions.elementToBeClickable(adminTab)).click();
        wait.until(ExpectedConditions.elementToBeClickable(qualificationsMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(educationOption)).click();
    }

    public boolean isEducationPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(educationHeader)).isDisplayed();
    }

    public void clickAddButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    public void enterEducationLevel(String level) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(levelInput));
        element.clear();
        element.sendKeys(level);
    }

    public void clickSaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void clickCancelButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
    }

    public boolean isEducationPresent(String level) {
        try {
            List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(educationTableRows));
            for (WebElement row : rows) {
                String cellText = row.getText().trim();
                if (cellText.equals(level)) {  
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isLevelErrorDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(levelError)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteEducation(String level) {
        List<WebElement> rows = driver.findElements(educationTableRows);
        for (WebElement row : rows) {
            if (row.getText().contains(level)) {
                row.findElement(deleteButton).click();
                wait.until(ExpectedConditions.elementToBeClickable(yesDeleteButton)).click();
                wait.until(ExpectedConditions.invisibilityOf(row));
                break;
            }
        }
    }

    public void selectEducation(String... levels) {
        List<WebElement> rows = driver.findElements(educationTableRows);
        for (String level : levels) {
            for (WebElement row : rows) {
                if (row.getText().contains(level)) {
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

    public void clickEditEducation(String level) {
        List<WebElement> rows = driver.findElements(educationTableRows);
        for (WebElement row : rows) {
            if (row.getText().contains(level)) {
                row.findElement(editButton).click();
                break;
            }
        }
    }


    public void enterEducationLevelEdit(String newLevel) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(editLevelInput));
        element.click();

        String currentValue = element.getAttribute("value");
        if (currentValue != null && !currentValue.isEmpty()) {
            int length = currentValue.length();
            for (int counter = 0; counter < length; counter++) {
                element.sendKeys(Keys.BACK_SPACE);
            }
        }

        element.sendKeys(newLevel);
    }


    public void clickSaveEditButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveEditButton)).click();
    }

    public void clickCancelEditButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelEditButton)).click();
    }
}

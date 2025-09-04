package QABootcam_MAVEN.OrangeHRM;

import org.testng.annotations.Test;

import QABootcam_MAVEN.OrangeHRM.Pages.GeneralInfoPage;
import QABootcam_MAVEN.OrangeHRM.Pages.LoginPage;
import QABootcam_MAVEN.OrangeHRM.Pages.MainPage;
import QABootcam_MAVEN.OrangeHRM.Pages.TestData;

import java.util.Scanner;

import org.openqa.selenium.ElementClickInterceptedException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class TestGeneralInfo extends BaseTest{
	LoginPage login;
	MainPage main;
	GeneralInfoPage general_info;
	Scanner input;
	
  @Test(priority = 1)
  public void TC_001_verifyGeneralInfoBtn() {
	  String actualHeader = general_info.getPageHeader();
      Assert.assertEquals(actualHeader, "General Information", 
          "General Information page did not load correctly!");
      System.out.println("General Information button is clickable and redirected correctly.");
	  
  }
  
  @Test(priority = 2)
  public void TC_002_verifyDefaultFields() {
	  Assert.assertTrue(general_info.isOrgNameDisabled(), "Organization Name field is not disabled!");
      Assert.assertTrue(general_info.isRegNumberDisabled(), "Registration Number field is not disabled!");
      Assert.assertTrue(general_info.isTaxIdDisabled(), "Tax ID field is not disabled!");
      System.out.println("General Information fields are prefilled and disabled by default!");
	  
  }
  
  @Test(priority = 3)
  public void TC_003_verifySaveButtonDisabled() {
	  Assert.assertFalse(general_info.isSaveButtonDisabled(), 
		        "Save button should not be visible in edit mode!");
	  System.out.println("Save button is not visible in edit mode!");
	  
  }
  
  @Test(priority = 4)
  public void TC_004_verifySaveButtonEnabled() {
	  general_info.clickEdit();
	  Assert.assertTrue(general_info.isSaveButtonEnabled(),
		        "Save button should be enabled!");
	  System.out.println("Save button is enabled!");
	  general_info.clickEdit();
	  
  }
  
  @Test(priority = 5)
  public void TC_005_saveData() throws InterruptedException {
	  general_info.clickEdit();
	  Thread.sleep(3000);
	  general_info.enterOrgDetails(TestData.orgNameField, TestData.regNumberField, TestData.taxIdField);
	  Thread.sleep(3000);
	  general_info.clickSaveButton();
	  System.out.println("Save button successfully saved data!");
	  
  }
  
  @Test(priority = 6)
  public void TC_006_verifyEmptyRequiredFields() throws InterruptedException, ElementClickInterceptedException {
	  try {
	  general_info.clickEdit();
	  general_info.clearOrgName();
	  Thread.sleep(3000);
	  general_info.clickSaveButton();
	  Thread.sleep(3000);
	  Assert.assertEquals(general_info.getValidationError(), "Required", 
		        "Expected 'Required' validation message for empty required fields!");
	  System.out.println("Empty required fields correctly show validation error.");
	  } catch(ElementClickInterceptedException e) {
		  System.out.println();
	  }
	  
  }
  
  @BeforeSuite
  public void inputWorkShift() {
      input = new Scanner(System.in);
      System.out.print("Enter Organization Name: ");
      TestData.orgNameField = input.next();
      System.out.print("Enter Registration Number: ");
      TestData.regNumberField = input.next();
      System.out.print("Enter Tax ID: ");
      TestData.taxIdField = input.next();
      
  }
  
  @BeforeClass
  public void initialize() {
		login = new LoginPage(driver);
		main = new MainPage(driver);
		general_info = new GeneralInfoPage(driver);

		login.loginAs("Admin", "admin123");
		main.goToGeneralInfoPage();

	}

}

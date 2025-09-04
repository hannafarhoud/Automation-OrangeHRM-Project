package QABootcam_MAVEN.OrangeHRM;

import org.testng.annotations.Test;

import QABootcam_MAVEN.OrangeHRM.Pages.LocationsPage;
import QABootcam_MAVEN.OrangeHRM.Pages.LoginPage;
import QABootcam_MAVEN.OrangeHRM.Pages.MainPage;
import QABootcam_MAVEN.OrangeHRM.Pages.TestData;

import java.util.Scanner;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class TestLocations extends BaseTest {
	LoginPage login;
	MainPage main;
	LocationsPage location;
	Scanner input;

	@Test(priority = 1)
	public void TC_001_verifyLocationsPage() {
		Assert.assertTrue(location.isLocationsPageDisplayed(), "Locations page is not displayed!");
		System.out.println("Locations button is clickable and redirects to the correct page!");

	}

	@Test(priority = 2)
	public void TC_002_verifyArrowButton() throws InterruptedException {
		Assert.assertTrue(location.isArrowButtonDisplayed(), "Arrow Button is not displayed!");
		System.out.println("Arrow button is displayed!");

	}

	@Test(priority = 3)
	public void TC_003_verifyAddButton() {
		Assert.assertTrue(location.isAddButtonEnabled(), "Add Button is not enabled!");
		System.out.println("Add button is enabled!");

	}

	@Test(priority = 4)
	public void TC_004_addValues() throws InterruptedException {
		location.clickAddButton();
		Thread.sleep(2000);
		location.addLocations(TestData.locationNameField);
		Thread.sleep(2000);
		location.clickSaveButton();
		Thread.sleep(2000);

	}
	
	@Test(priority = 5)
	public void TC_005_deleteValue() throws InterruptedException {
		location.clickDeleteButton();
		Thread.sleep(2000);
		
	}
	
	@Test(priority = 7)
	public void TC_006_deleteAllValues() throws InterruptedException {
		location.clickDeleteAll();
		
	}
	
	@Test(priority = 6)
	public void TC_007_editValue() throws InterruptedException {
		location.clickEditButton(TestData.locationNameEdit);
		Thread.sleep(2000);
		location.clickSaveButton();
		
	}
	
	@Test(priority = 8)
	public void TC_008_clickResetButton() throws InterruptedException {
		location.resetButton();
		
	}
	
	@BeforeSuite
	  public void inputWorkShift() {
	      input = new Scanner(System.in);
	      System.out.print("Enter Location Name: ");
	      TestData.locationNameField = input.next();
	      System.out.print("Enter Edited Location Name: ");
	      TestData.locationNameEdit = input.next();
	      
	  }

	@BeforeClass
	public void initialize() {
		login = new LoginPage(driver);
		main = new MainPage(driver);
		location = new LocationsPage(driver);

		login.loginAs("Admin", "admin123");
		main.goToLocationsPage();

	}

}

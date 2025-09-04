package QABootcam_MAVEN.OrangeHRM;

import org.testng.annotations.Test;

import QABootcam_MAVEN.OrangeHRM.Pages.LoginPage;
import QABootcam_MAVEN.OrangeHRM.Pages.MainPage;
import QABootcam_MAVEN.OrangeHRM.Pages.TestData;
import QABootcam_MAVEN.OrangeHRM.Pages.WorkShiftPage;

import java.util.Scanner;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class TestWorkShift extends BaseTest {
	LoginPage login;
	MainPage main;
	WorkShiftPage work_shift;
	Scanner input;

	@Test(priority = 1)
	public void TC_002_addWorkShift() {
		work_shift.addButtonWS();
		work_shift.addWorkShift(TestData.shiftNameField, "12:00 PM", "03:00 PM");
		work_shift.saveButtonWS();
		Assert.assertTrue(work_shift.isShiftAdded(TestData.shiftNameField), "Shift not added!");
		System.out.println("Shift Added Successfully!");

	}

	@Test(priority = 6)
	public void TC_003_addWorkShiftEmpty() throws InterruptedException {
		work_shift.addButtonWS();
		work_shift.addWorkShift("", "12:00 PM", "03:00 PM");
		work_shift.saveButtonWS();
		Assert.assertEquals(work_shift.requiredMessage(), "Required",
				"Expected 'Required' message for empty Shift Name!");
		System.out.println("Shift Name is Null!");
		Thread.sleep(2000);
		Assert.assertTrue(work_shift.isCancelWorked(), "Cancel button is not worked!");
		work_shift.cancelButtonWS();
		System.out.println("Cancel Button Worked!");

	}

	@Test(priority = 3)
	public void TC_004_editWorkShift() {
		work_shift.editButtonWS(TestData.shiftNameEdit);
		work_shift.saveButtonWS();
		Assert.assertTrue(work_shift.isShiftAdded(TestData.shiftNameEdit), "Shift not edited!");
		System.out.println("Shift Edited Successfully!");

	}

	@Test(priority = 4)
	public void TC_005_deleteWorkShift() {
		int beforeCount = work_shift.isShiftDeletedBefore();
		work_shift.deleteButtonWS();
		int afterCount = work_shift.isShiftDeletedAfter();
		Assert.assertEquals(afterCount, beforeCount - 1, "Shift not deleted!");
		System.out.println("Shift Deleted Successfully!");

	}

	@Test(priority = 7)
	public void TC_006_selectCheckBox() {
		int beforeCount = work_shift.isShiftDeletedBefore();
		work_shift.selectCheckBox();
		int afterCount = work_shift.isShiftDeletedAfter();
		Assert.assertEquals(afterCount, beforeCount - 1, "Shift not deleted!");
		System.out.println("Selected Shift Deleted Successfully!");

	}

	@Test(priority = 8)
	public void TC_007_deleteAll() throws InterruptedException {
		work_shift.selectAllCheckBoxes();

	}

	@Test(priority = 2)
	public void TC_001_verifyWorkingHours() {
		work_shift.addButtonWS();
		work_shift.addWorkShift(TestData.shiftNameEdit, "12:00 PM", "03:00 PM");
		work_shift.saveButtonWS();
		String actualHours = work_shift.getHoursPerDay(TestData.shiftNameEdit);
		//String expectedHours = "3.00" OR "8.00";
		String expectedHours1 = "3.00";
	    String expectedHours2 = "8.00";
	    Assert.assertTrue(
	            actualHours.equals(expectedHours1) || actualHours.equals(expectedHours2),
	            "Working Hours calculation is incorrect! Expected " + expectedHours1 + " or " + expectedHours2 + 
	            " but got: " + actualHours);
	    System.out.println("Working Hours calculation verified: " + actualHours + " hrs");

	}

	@Test(priority = 5)
	public void TC_003_verifyCancelButton() throws InterruptedException {
		work_shift.addButtonWS();
		work_shift.addWorkShift("", "12:00 PM", "03:00 PM");
		work_shift.saveButtonWS();
		Thread.sleep(2000);
		Assert.assertTrue(work_shift.isCancelWorked(), "Cancel button is not worked!");
		work_shift.cancelButtonWS();
		System.out.println("Cancel Button Worked!");

	}

	@BeforeSuite
	public void inputWorkShift() {
		input = new Scanner(System.in);
		System.out.print("Enter Work Shift Name: ");
		TestData.shiftNameField = input.next();
		System.out.print("Edit Work Shift Name: ");
		TestData.shiftNameEdit = input.next();
	}

	@BeforeClass
	public void initialize() {
		login = new LoginPage(driver);
		main = new MainPage(driver);
		work_shift = new WorkShiftPage(driver);

		login.loginAs("Admin", "admin123");
		main.goToWorkShiftPage();

	}

}

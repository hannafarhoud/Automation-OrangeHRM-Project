package QABootcam_MAVEN.OrangeHRM;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import QABootcam_MAVEN.OrangeHRM.Pages.LoginPage;
import QABootcam_MAVEN.OrangeHRM.Pages.OrganizationStructurePage;

public class OrganizationStructureTest extends BaseTest {

	private OrganizationStructurePage structurePage;
	private LoginPage loginPage;

	@BeforeClass
	public void setupTestData() {
		loginPage = new LoginPage(driver);
		loginPage.loginAs("Admin", "admin123");
		structurePage = new OrganizationStructurePage(driver);
		structurePage.navigateToStructurePage();
	}

	@Test(priority = 1)
	public void TC_STR_001_verifyStructurePageNavigation() {
		Assert.assertTrue(structurePage.isStructurePageDisplayed(), "structure page  not display");
	}

	@Test(priority = 2)
	public void TC_STR_002_verifyEditToggle() {
		structurePage.toggleEditMode();
		Assert.assertTrue(structurePage.isEditEnabled(), "edit mode  not enabled");

	}

	@Test(priority = 3)
	public void TC_STR_003_addMainUnit() {
		structurePage.addUnit("Main Unit", "MU001", "main unit description");

		String mainUnitFullName = "MU001: Main Unit";
		Assert.assertTrue(structurePage.isUnitPresent(mainUnitFullName), "main unit not add");
	}

	@Test(priority = 4)
	public void TC_STR_004_addSubUnits() {
		String parentUnitFullName = "MU001: Main Unit";

		structurePage.addSubUnit(parentUnitFullName, "Sub Unit 1");
		structurePage.addSubUnit(parentUnitFullName, "Sub Unit 2");
		structurePage.addSubUnit(parentUnitFullName, "Sub Unit 3");

		structurePage.expandUnit(parentUnitFullName);

		Assert.assertTrue(structurePage.isUnitPresent("Sub Unit 1"), "sub Unit 1 not add");
		Assert.assertTrue(structurePage.isUnitPresent("Sub Unit 2"), "sub Unit 2 not add");
		Assert.assertTrue(structurePage.isUnitPresent("Sub Unit 3"), "sub Unit 3 not add");
	}

	@Test(priority = 5)
	public void TC_STR_005_verifyNameFieldCannotBeEmpty_AddMainUnit() {
		String parentUnitFullName = "MU001: Main Unit";

		structurePage.clickAddUnit();
		structurePage.enterUnitId("MU002");
		structurePage.enterUnitDescription("description for empty name");
		structurePage.clickSave();
		Assert.assertTrue(structurePage.isNameFieldErrorDisplayed(),
				"name field msg error not displayed for add");

		structurePage.clickCancel();
		structurePage.expandUnit(parentUnitFullName);
	}

	@Test(priority = 6)
	public void TC_STR_007_verifyNameFieldCannotBeEmpty_EditMainUnit() {
		String mainUnitFullName = "MU001: Main Unit";
		structurePage.editUnit(mainUnitFullName);
		structurePage.clearNameField();
		structurePage.clickSave();
		Assert.assertTrue(structurePage.isNameFieldErrorDisplayed(),
				"name field error msg not displayed for edit");

		structurePage.clickCancel();
	}

	@Test(priority = 7)
	public void TC_STR_008_verifyAdminCanEditUnit() {
		String originalUnit = "MU001: Main Unit";
		String updatedName = "Main Unit - Edited";
		structurePage.editUnit(originalUnit);
		structurePage.clearAndEnterName(updatedName);
		structurePage.clickSave();

		Assert.assertTrue(structurePage.isUnitPresent("MU001: " + updatedName), "edited unit name  not updated");
	}

	@Test(priority = 8)
	public void TC_STR_009_verifyDeleteSubUnit() {
		String parentUnit = "MU001: Main Unit - Edited";
		String subUnitToDelete = "Sub Unit 3";

		structurePage.expandUnit(parentUnit);
		structurePage.deleteUnit(subUnitToDelete);

		Assert.assertTrue(structurePage.isUnitAbsent(subUnitToDelete), "sub unit not deleted");
	}

	@Test(priority = 9)
	public void TC_STR_010_verifyDeleteMainUnit() {
		String mainUnitToDelete = "MU001: Main Unit - Edited";

		structurePage.deleteUnit(mainUnitToDelete);

		Assert.assertTrue(structurePage.isUnitAbsent(mainUnitToDelete), "main unit not deleted");
	}

}
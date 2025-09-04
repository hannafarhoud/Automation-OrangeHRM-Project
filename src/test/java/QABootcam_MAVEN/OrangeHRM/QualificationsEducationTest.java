package QABootcam_MAVEN.OrangeHRM;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import QABootcam_MAVEN.OrangeHRM.Pages.LoginPage;
import QABootcam_MAVEN.OrangeHRM.Pages.QualificationsEducationPage;

public class QualificationsEducationTest extends BaseTest {

    private QualificationsEducationPage educationPage;
    private LoginPage loginPage;

    @DataProvider(name = "initialEducation")
    public Object[][] initialEducation() {
        return new Object[][] {
                {"Bachelor"},
                {"Master"},
                {"PhD2"},
                {"Diploma"},
                {"High School"},
                {"Associate"}
        };
    }

    @DataProvider(name = "editEducationData")
    public Object[][] editEducationData() {
        return new Object[][] {
                {"Bachelor", "Bachelor Updated"},
                {"Master", "Master Updated"}
        };
    }


    @BeforeClass
    public void setupTestData() {
        loginPage = new LoginPage(driver);
        loginPage.loginAs("Admin", "admin123");
        educationPage = new QualificationsEducationPage(driver);
        educationPage.navigateToEducationPage();

        for (Object[] edu : initialEducation()) {
            String level = (String) edu[0];
            if (!educationPage.isEducationPresent(level)) {
                educationPage.clickAddButton();
                educationPage.enterEducationLevel(level);
                educationPage.clickSaveButton();
            }
        }
    }

    @Test(priority = 1)
    public void TC_EDU_001_verifyEducationButtonRedirect() {
        Assert.assertTrue(educationPage.isEducationPageDisplayed(), "Education page not displaye");
    }

    @Test(priority = 2, dataProvider = "initialEducation")
    public void TC_EDU_002_addNewEducation(String level) {
        if (!educationPage.isEducationPresent(level)) {
            educationPage.navigateToEducationPage();
            educationPage.clickAddButton();
            educationPage.enterEducationLevel(level);
            educationPage.clickSaveButton();
        }
        Assert.assertTrue(educationPage.isEducationPresent(level), "New education not add");
    }

    @Test(priority = 3)
    public void TC_EDU_003_verifyEmptyLevelNotAccepted() {
        educationPage.navigateToEducationPage();
        educationPage.clickAddButton();
        educationPage.enterEducationLevel(""); 
        educationPage.clickSaveButton();

        Assert.assertTrue(educationPage.isLevelErrorDisplayed(), "Level field accept empty value");
        educationPage.clickCancelButton();
    }

    @Test(priority = 4)
    public void TC_EDU_004_verifyCancelButtonWorks() {
        educationPage.navigateToEducationPage();
        educationPage.clickAddButton();
        educationPage.enterEducationLevel("LevelCancel");
        educationPage.clickCancelButton();

        Assert.assertTrue(educationPage.isEducationPageDisplayed(), "Cancel btn not return to edu page");
    }

    @Test(priority = 5)
    public void TC_EDU_005_verifyDeleteEducation() {
        String levelToDelete = "Associate"; 
        educationPage.navigateToEducationPage();
        educationPage.deleteEducation(levelToDelete);

        Assert.assertFalse(educationPage.isEducationPresent(levelToDelete), "Education level not deleted");
    }

    @Test(priority = 6)
    public void TC_EDU_006_verifyDeleteMultipleEducation() {
        String level1 = "Diploma";
        String level2 = "High School";

        educationPage.navigateToEducationPage();
        educationPage.selectEducation(level1, level2);
        educationPage.clickDeleteSelected();

        Assert.assertFalse(educationPage.isEducationPresent(level1), "Edu1 not delete");
        Assert.assertFalse(educationPage.isEducationPresent(level2), "Edu2 not delete");
    }

    @Test(priority = 7, dataProvider = "editEducationData")
    public void TC_EDU_007_verifyEditEducation(String existingLevel, String newLevel) {
        educationPage.navigateToEducationPage();
        educationPage.clickEditEducation(existingLevel);
        educationPage.enterEducationLevelEdit(newLevel);
        educationPage.clickSaveEditButton();

        Assert.assertTrue(educationPage.isEducationPresent(newLevel), "Edited education not updated");
        Assert.assertFalse(educationPage.isEducationPresent(existingLevel), "Old education level is present");
    }

    @Test(priority = 8)
    public void TC_EDU_008_verifyCancelEditEducation() {
        String existingLevel = "PhD2";
        String tempLevel = "Cancel Edit";

        educationPage.navigateToEducationPage();
        educationPage.clickEditEducation(existingLevel);
        educationPage.enterEducationLevelEdit(tempLevel);
        educationPage.clickCancelEditButton();

        Assert.assertTrue(educationPage.isEducationPresent(existingLevel), "Original education level is changed");
        Assert.assertFalse(educationPage.isEducationPresent(tempLevel), "Cancell edit is applied");
    }
}

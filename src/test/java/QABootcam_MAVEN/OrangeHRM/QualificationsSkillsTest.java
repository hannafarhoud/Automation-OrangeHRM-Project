package QABootcam_MAVEN.OrangeHRM;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import QABootcam_MAVEN.OrangeHRM.Pages.LoginPage;
import QABootcam_MAVEN.OrangeHRM.Pages.QualificationsSkillsPage;

public class QualificationsSkillsTest extends BaseTest {

    private QualificationsSkillsPage skillsPage;
    private LoginPage loginPage;

    @DataProvider(name = "initialSkills")
    public Object[][] initialSkills() {
        return new Object[][] {
                {"skill_test_1", "Auto test1"},
                {"skill_test_2", "Auto test2"},
                {"skill_test_3", "Auto test3"},
                {"skill_test_4", "Auto test4"},
                {"skill_test_5", "Auto test5"},
                {"skill_test_6", "Auto test6"}
        };
    }

    @DataProvider(name = "editSkillData")
    public Object[][] editSkillData() {
        return new Object[][] {
                {"skill_test_1", "skill_test_1_Edit", "Auto test1 Updated"},
                {"skill_test_2", "skill_test_2_Edit", "Auto test2 Updated"}
        };
    }


    @BeforeClass
    public void setupTestData() {
        loginPage = new LoginPage(driver);
        loginPage.loginAs("Admin", "admin123");
        skillsPage = new QualificationsSkillsPage(driver);
        skillsPage.navigateToSkillsPage();

        for (Object[] skill : initialSkills()) {
            String name = (String) skill[0];
            String description = (String) skill[1];
            if (!skillsPage.isSkillPresent(name)) {
                skillsPage.clickAddButton();
                skillsPage.enterSkillName(name);
                skillsPage.enterSkillDescription(description);
                skillsPage.clickSaveButton();
            }
        }
    }

    @Test(priority = 1)
    public void TC_SKILLS_001_verifySkillsButtonRedirect() {
        Assert.assertTrue(skillsPage.isSkillsPageDisplayed(), "skills page not display");
    }

    @Test(priority = 2, dataProvider = "initialSkills")
    public void TC_SKILLS_002_addNewSkill(String skillName, String skillDescription) {
        if (!skillsPage.isSkillPresent(skillName)) {
            skillsPage.navigateToSkillsPage();
            skillsPage.clickAddButton();
            skillsPage.enterSkillName(skillName);
            skillsPage.enterSkillDescription(skillDescription);
            skillsPage.clickSaveButton();
        }
        Assert.assertTrue(skillsPage.isSkillPresent(skillName), "new skill not added to list");
    }


    @Test(priority = 3)
    public void TC_SKILLS_003_verifyEmptyNameNotAccepted() {
        skillsPage.navigateToSkillsPage();
        skillsPage.clickAddButton();
        skillsPage.enterSkillName(""); 
        skillsPage.enterSkillDescription("Description");
        skillsPage.clickSaveButton();

        Assert.assertTrue(skillsPage.isNameErrorDisplayed(), "name field accept empty value");
        skillsPage.clickCancelButton();
    }

    @Test(priority = 4)
    public void TC_SKILLS_004_verifyCancelButtonWorks() {
        skillsPage.navigateToSkillsPage();
        skillsPage.clickAddButton();
        skillsPage.enterSkillName("SkillCancel");
        skillsPage.enterSkillDescription("Cancel description");
        skillsPage.clickCancelButton();

        Assert.assertTrue(skillsPage.isSkillsPageDisplayed(), "cancel btn not return to skills page");
    }

    @Test(priority = 5)
    public void TC_SKILLS_005_verifyDeleteSkill() {
        String skillToDelete = "skill_test_6"; 
        skillsPage.navigateToSkillsPage();
        skillsPage.deleteSkill(skillToDelete);

        Assert.assertFalse(skillsPage.isSkillPresent(skillToDelete), "Skill not deleted");
    }

    @Test(priority = 6)
    public void TC_SKILLS_006_verifyDeleteMultipleSkills() {
        String skill1 = "skill_test_4";
        String skill2 = "skill_test_5";

        skillsPage.navigateToSkillsPage();
        skillsPage.selectSkills(skill1, skill2);
        skillsPage.clickDeleteSelected();

        Assert.assertFalse(skillsPage.isSkillPresent(skill1), "Skill1 not deleted");
        Assert.assertFalse(skillsPage.isSkillPresent(skill2), "Skill2 not deleted");
    }

    @Test(priority = 7, dataProvider = "editSkillData")
    public void TC_SKILLS_007_verifyEditSkill(String existingSkill, String newSkillName, String newSkillDescription) {
        skillsPage.navigateToSkillsPage();
        skillsPage.clickEditSkill(existingSkill);
        skillsPage.enterSkillNameEdit(newSkillName); 
        skillsPage.enterSkillDescriptionEdit(newSkillDescription);
        skillsPage.clickSaveEditButton();

        Assert.assertTrue(skillsPage.isSkillPresent(newSkillName), "edited skill name not updat");
        Assert.assertFalse(skillsPage.isSkillPresent(existingSkill), "old skill name is present");
    }

    @Test(priority = 8)
    public void TC_SKILLS_008_verifyCancelEditSkill() {
        String existingSkill = "skill_test_1_Edit";
        String tempName = "Name cancel";
        String tempDescription = " cancel description";

        skillsPage.navigateToSkillsPage();
        skillsPage.clickEditSkill(existingSkill);
        skillsPage.enterSkillNameEdit(tempName);
        skillsPage.enterSkillDescriptionEdit(tempDescription);
        skillsPage.clickCancelEditButton();

        Assert.assertTrue(skillsPage.isSkillPresent(existingSkill), "original skill changed after cancel");
        Assert.assertFalse(skillsPage.isSkillPresent(tempName), "cancelled skill edit applied");
    }

}

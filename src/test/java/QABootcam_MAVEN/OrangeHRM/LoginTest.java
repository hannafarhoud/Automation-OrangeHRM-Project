package QABootcam_MAVEN.OrangeHRM;

import org.testng.annotations.Test;
import QABootcam_MAVEN.OrangeHRM.BaseTest;
import QABootcam_MAVEN.OrangeHRM.Pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs("Admin", "admin123");

    }
}

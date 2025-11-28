import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.relevantcodes.extentreports.LogStatus;
import java.time.Duration;

public class tc1 extends orangrHRMReporter {

    @Test(priority = 1)
    public void loginTest() {
        try {
            logger.log(LogStatus.INFO, "Starting Login Test");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
           
            WebElement usernameField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("username"))
            );
            usernameField.sendKeys("Admin");
            logger.log(LogStatus.INFO, "Entered username: Admin");
            
            
            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys("admin");
            logger.log(LogStatus.INFO, "Entered password");
            
           
            WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));
            loginButton.click();
            logger.log(LogStatus.INFO, "Clicked login button");
            
            
            wait.until(ExpectedConditions.urlContains("/dashboard"));
            
            
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("dashboard"), "Login failed - Dashboard not loaded");
            
            logger.log(LogStatus.PASS, "Login Test Passed - Successfully logged in");
            captureScreenshot("loginTest_success");
            
        } catch (Exception e) {
            logger.log(LogStatus.FAIL, "Login Test Failed: " + e.getMessage());
            captureScreenshot("loginTest_failed");
            Assert.fail("Login test failed: " + e.getMessage());
        }
    }
}

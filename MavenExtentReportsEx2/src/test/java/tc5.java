import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.relevantcodes.extentreports.LogStatus;
import java.time.Duration;

public class tc5 extends orangrHRMReporter {

    @Test(priority = 5)
    public void logoutTest() {
        try {
            logger.log(LogStatus.INFO, "Starting Logout Test");
            
          
            performLogin();
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            
            WebElement userDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//p[@class='oxd-userdropdown-name']")
                )
            );
            userDropdown.click();
            logger.log(LogStatus.INFO, "Clicked on user dropdown");
            
            Thread.sleep(1000);
            
           
            WebElement logoutLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Logout']"))
            );
            logoutLink.click();
            logger.log(LogStatus.INFO, "Clicked Logout");
            
            
            wait.until(ExpectedConditions.urlContains("/auth/login"));
            
            
            WebElement usernameField = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.name("username"))
            );
            
            Assert.assertTrue(usernameField.isDisplayed(), "Logout failed - Login page not displayed");
            
            logger.log(LogStatus.PASS, "Logout Test Passed - Successfully logged out");
            captureScreenshot("logoutTest_success");
            
        } catch (Exception e) {
            logger.log(LogStatus.FAIL, "Logout Test Failed: " + e.getMessage());
            captureScreenshot("logoutTest_failed");
            Assert.fail("Logout test failed: " + e.getMessage());
        }
    }
    
    private void performLogin() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        WebElement usernameField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.name("username"))
        );
        usernameField.sendKeys("Admin");
        
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        Thread.sleep(2000);
    }
}

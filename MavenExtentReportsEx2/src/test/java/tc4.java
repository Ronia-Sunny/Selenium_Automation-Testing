import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.relevantcodes.extentreports.LogStatus;
import java.time.Duration;

public class tc4 extends orangrHRMReporter {

    @Test(priority = 4)
    public void updateEmployeeTest() {
        try {
            logger.log(LogStatus.INFO, "Starting Update Employee Test");
            
           
            performLogin();
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            
            WebElement pimMenu = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='PIM']"))
            );
            pimMenu.click();
            logger.log(LogStatus.INFO, "Clicked on PIM menu");
            
            Thread.sleep(2000);
            
           
            WebElement editIcon = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("(//i[@class='oxd-icon bi-pencil-fill'])[1]")
                )
            );
            editIcon.click();
            logger.log(LogStatus.INFO, "Clicked edit icon for first employee");
            
            Thread.sleep(2000);
            
          
            WebElement licenseField = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("(//input[@class='oxd-input oxd-input--active'])[3]")
                )
            );
            licenseField.clear();
            licenseField.sendKeys("DL-" + System.currentTimeMillis());
            logger.log(LogStatus.INFO, "Updated driver license number");
            
           
            WebElement saveButton = driver.findElement(
                By.xpath("//button[@type='submit']")
            );
            saveButton.click();
            logger.log(LogStatus.INFO, "Clicked Save button");
            
            Thread.sleep(3000);
            
            
            try {
                WebElement successMessage = driver.findElement(
                    By.xpath("//*[contains(text(),'Success')]")
                );
                Assert.assertTrue(successMessage.isDisplayed(), "Update not successful");
            } catch (Exception e) {
               
                Assert.assertTrue(driver.getCurrentUrl().contains("viewPersonalDetails"), 
                                "Update failed");
            }
            
            logger.log(LogStatus.PASS, "Update Employee Test Passed");
            captureScreenshot("updateEmployeeTest_success");
            
        } catch (Exception e) {
            logger.log(LogStatus.FAIL, "Update Employee Test Failed: " + e.getMessage());
            captureScreenshot("updateEmployeeTest_failed");
            Assert.fail("Update Employee test failed: " + e.getMessage());
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
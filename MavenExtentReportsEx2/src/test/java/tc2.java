import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.relevantcodes.extentreports.LogStatus;
import java.time.Duration;

public class tc2 extends orangrHRMReporter {

    @Test(priority = 2)
    public void addEmployeeTest() {
        try {
            logger.log(LogStatus.INFO, "Starting Add Employee Test");
            
           
            performLogin();
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            
            WebElement pimMenu = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='PIM']"))
            );
            pimMenu.click();
            logger.log(LogStatus.INFO, "Clicked on PIM menu");
            
            Thread.sleep(2000); 
            
           
            WebElement addEmployeeLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Add Employee']"))
            );
            addEmployeeLink.click();
            logger.log(LogStatus.INFO, "Clicked on Add Employee");
            
            
            wait.until(ExpectedConditions.urlContains("/addEmployee"));
            
           
            WebElement firstNameField = driver.findElement(By.name("firstName"));
            firstNameField.sendKeys("John");
            
            WebElement middleNameField = driver.findElement(By.name("middleName"));
            middleNameField.sendKeys("M");
            
            WebElement lastNameField = driver.findElement(By.name("lastName"));
            lastNameField.sendKeys("Doe");
            
            logger.log(LogStatus.INFO, "Entered employee details: John M Doe");
            
            
            WebElement saveButton = driver.findElement(By.xpath("//button[@type='submit']"));
            saveButton.click();
            logger.log(LogStatus.INFO, "Clicked Save button");
            
            
            Thread.sleep(3000);
            
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("viewPersonalDetails") || 
                            currentUrl.contains("empNumber"), 
                            "Employee not added successfully");
            
            logger.log(LogStatus.PASS, "Add Employee Test Passed");
            captureScreenshot("addEmployeeTest_success");
            
        } catch (Exception e) {
            logger.log(LogStatus.FAIL, "Add Employee Test Failed: " + e.getMessage());
            captureScreenshot("addEmployeeTest_failed");
            Assert.fail("Add Employee test failed: " + e.getMessage());
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
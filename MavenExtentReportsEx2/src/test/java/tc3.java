import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.relevantcodes.extentreports.LogStatus;
import java.time.Duration;
import java.util.List;

public class tc3 extends orangrHRMReporter {

    @Test(priority = 3)
    public void searchEmployeeTest() {
        try {
            logger.log(LogStatus.INFO, "Starting Search Employee Test");
            
            
            performLogin();
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            
            WebElement pimMenu = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='PIM']"))
            );
            pimMenu.click();
            logger.log(LogStatus.INFO, "Clicked on PIM menu");
            
            Thread.sleep(2000);
            
            
            WebElement employeeListLink = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Employee List']"))
            );
            employeeListLink.click();
            logger.log(LogStatus.INFO, "Clicked on Employee List");
            
            Thread.sleep(2000);
            
            
            WebElement employeeNameField = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@placeholder='Type for hints...']")
                )
            );
            employeeNameField.sendKeys("Peter");
            logger.log(LogStatus.INFO, "Entered search term: Peter");
            
            Thread.sleep(2000); 
            
           
            WebElement searchButton = driver.findElement(By.xpath("//button[@type='submit']"));
            searchButton.click();
            logger.log(LogStatus.INFO, "Clicked Search button");
            
            Thread.sleep(3000); 
            
           
            List<WebElement> searchResults = driver.findElements(
                By.xpath("//div[@class='oxd-table-card']")
            );
            
            Assert.assertTrue(searchResults.size() > 0, "No search results found");
            
            logger.log(LogStatus.PASS, "Search Employee Test Passed - Found " + searchResults.size() + " results");
            captureScreenshot("searchEmployeeTest_success");
            
        } catch (Exception e) {
            logger.log(LogStatus.FAIL, "Search Employee Test Failed: " + e.getMessage());
            captureScreenshot("searchEmployeeTest_failed");
            Assert.fail("Search Employee test failed: " + e.getMessage());
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
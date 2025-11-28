import org.testng.ITestResult;
import org.testng.annotations.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.text.SimpleDateFormat;
import java.util.Date;

public class orangrHRMReporter {

    public static ExtentReports extent;
    public static ExtentTest logger;
    WebDriver driver;

    @BeforeSuite
    public void beforeSuite() {
        extent = new ExtentReports("C:\\Selenium Demo\\MavenextentreportsEx2\\TestReports\\Reports.html", true);
        extent.addSystemInfo("Environment", "QA");
        extent.addSystemInfo("Browser", "Microsoft Edge");
        extent.addSystemInfo("User", System.getProperty("user.name"));
    }

    @BeforeMethod
    public void setUp(Method method) {
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/");
        
        logger = extent.startTest(method.getName(), "Test: " + method.getName());
        logger.log(LogStatus.INFO, "Browser launched and navigated to OrangeHRM");
    }

    @AfterMethod

    public void tearDown(ITestResult result) {
        
        if (result.getStatus() == ITestResult.FAILURE) {
            captureScreenshot(result.getName());
        }

        if (driver != null) {
            driver.quit();
            logger.log(LogStatus.INFO, "Browser closed");
        }
        extent.endTest(logger);
    }

    @AfterSuite
    public void afterSuite() {
        extent.flush();
        extent.close();
    }

    public void captureScreenshot(String testName) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotFileName = testName + "_" + timestamp + ".png";
            
            File screenshotDir = new File("screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(screenshotDir, screenshotFileName);
            FileUtils.copyFile(screenshotFile, destFile);
            
            String screenshotPath = destFile.getAbsolutePath();
            logger.log(LogStatus.INFO, "Screenshot: " + logger.addScreenCapture(screenshotPath));
            
        } catch (IOException e) {
            logger.log(LogStatus.FAIL, "Failed to capture screenshot: " + e.getMessage());
        }
    }
}

import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeSuite;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;



public class Driver {
    private static final Integer DEFAULT_SECONDS_WAIT = 10;

    // Holds the WebDriver instance
    public static WebDriver driver;

    // Initialize a driver instance of required browser
    // Since this does not have a significance in the application's business domain, the BeforeSuite hook is used to instantiate the driver
    @BeforeSuite
    public void initializeDriver(){
        driver = DriverFactory.getDriver();
        driver.manage().timeouts().implicitlyWait(DEFAULT_SECONDS_WAIT, TimeUnit.SECONDS);
    }

    // Close the driver instance
    @AfterSuite
    public void closeDriver(){
        driver.close();
    }

}


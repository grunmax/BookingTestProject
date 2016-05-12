import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class DriverFactory {

    // Use webdriver path from default.properties
    private static void SetDriver (String driverPath) {
        String path = System.getenv(driverPath);
        if (path != null) {
            System.setProperty(driverPath, path);
        }
    }

    // Get a new WebDriver Instance.
    // There are various implementations for this depending on browser. The required browser can be set as an environment variable.
    // Refer http://getgauge.io/documentation/user/current/managing_environments/README.html
    public static WebDriver getDriver() {
        SetDriver("webdriver.edge.driver");
        SetDriver("webdriver.ie.driver");
        SetDriver("webdriver.chrome.driver");

        String browser = System.getenv("BROWSER");
        if (browser == null) {
            return new FirefoxDriver();
        }
        switch (browser)
        {
            case "IE":
                return new InternetExplorerDriver();
            case "EDGE":
                return new EdgeDriver();
            case "CHROME":
                return new ChromeDriver();
            default:
                return new FirefoxDriver();
        }
    }
}

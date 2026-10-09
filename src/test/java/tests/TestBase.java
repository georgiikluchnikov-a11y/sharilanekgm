package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

/**
 * Базовый класс тестов: поднимает браузер, настраивает ожидания
 * и гарантированно закрывает драйвер после каждого теста.
 */
public class TestBase {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        System.setProperty("webdriver.chrome.silentOutput", "true");
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        if (isHeadless()) {
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);
        if (!isHeadless()) {
            driver.manage().window().maximize();
        }
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /** Запуск без графического интерфейса: mvn test -Dheadless=true */
    protected static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty("headless", "false"))
                || Boolean.parseBoolean(System.getenv("HEADLESS"));
    }
}

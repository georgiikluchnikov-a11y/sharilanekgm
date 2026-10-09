package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Первый шаг регистрации Sharelane: ввод Zip Code.
 * https://www.sharelane.com/cgi-bin/register.py
 */
public class ZipCodePage extends BasePage {

    private static final String URL = "https://www.sharelane.com/cgi-bin/register.py";

    private final By zipCodeInput = By.name("zip_code");
    private final By continueButton = By.cssSelector("input[value='Continue']");
    private final By errorMessage = By.className("error_message");

    public ZipCodePage(WebDriver driver) {
        super(driver);
    }

    /** Открывает страницу с повторной попыткой при медленном ответе сайта. */
    public void open() {
        RuntimeException last = null;
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                driver.get(URL);
                visible(zipCodeInput);
                return;
            } catch (RuntimeException error) {
                last = error;
            }
        }
        throw last;
    }

    public void enterZipCode(String zipCode) {
        type(zipCodeInput, zipCode);
    }

    /** Нажимает Continue. При корректном Zip Code сайт открывает форму регистрации. */
    public void clickContinue() {
        clickWithRetry(continueButton);
    }

    public String getErrorMessageText() {
        return visible(errorMessage).getText();
    }

    public boolean isErrorMessageVisible() {
        return !driver.findElements(errorMessage).isEmpty();
    }
}

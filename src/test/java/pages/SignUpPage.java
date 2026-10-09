package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Второй шаг регистрации Sharelane: заполнение формы и создание аккаунта.
 */
public class SignUpPage extends BasePage {

    private final By firstNameInput = By.name("first_name");
    private final By lastNameInput = By.name("last_name");
    private final By emailInput = By.name("email");
    private final By passwordInput = By.name("password1");
    private final By confirmPasswordInput = By.name("password2");
    private final By registerButton = By.cssSelector("input[value='Register']");
    private final By confirmationMessage = By.className("confirmation_message");

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        return visible(firstNameInput).isDisplayed();
    }

    public void fillForm(String firstName, String lastName, String email, String password, String confirmPassword) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(emailInput, email);
        type(passwordInput, password);
        type(confirmPasswordInput, confirmPassword);
    }

    public void clickRegister() {
        clickWithRetry(registerButton);
    }

    public String getConfirmationText() {
        return visible(confirmationMessage).getText().trim();
    }
}

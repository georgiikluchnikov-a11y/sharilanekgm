package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SignUpPage;
import pages.ZipCodePage;

public class RegistrationTest extends TestBase {

    @Test(description = "Позитивный тест: ввод корректного Zip Code (5 цифр)")
    public void testValidZipCode() {
        ZipCodePage zipCodePage = new ZipCodePage(driver);
        zipCodePage.open();
        zipCodePage.enterZipCode("12345");
        zipCodePage.clickContinue();

        SignUpPage signUpPage = new SignUpPage(driver);
        Assert.assertTrue(signUpPage.isPageLoaded(), "Форма регистрации не отобразилась");
    }

    @Test(description = "Негативный тест: ввод 4 цифр в Zip Code")
    public void testShortZipCode() {
        ZipCodePage zipCodePage = new ZipCodePage(driver);
        zipCodePage.open();
        zipCodePage.enterZipCode("1234");
        zipCodePage.clickContinue();

        Assert.assertEquals(zipCodePage.getErrorMessageText(), "Oops, error on page. ZIP code should have 5 digits");
    }

    @Test(description = "Негативный тест: ввод буквенных символов в Zip Code")
    public void testLettersInZipCode() {
        ZipCodePage zipCodePage = new ZipCodePage(driver);
        zipCodePage.open();
        zipCodePage.enterZipCode("abcde");
        zipCodePage.clickContinue();

        Assert.assertEquals(zipCodePage.getErrorMessageText(), "Oops, error on page. ZIP code should have 5 digits");
    }

    @Test(description = "Позитивный тест: успешная регистрация пользователя")
    public void testSuccessfulRegistration() {
        ZipCodePage zipCodePage = new ZipCodePage(driver);
        zipCodePage.open();
        zipCodePage.enterZipCode("12345");
        zipCodePage.clickContinue();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.fillForm("John", "Doe", "test" + System.currentTimeMillis() + "@example.com", "Password123", "Password123");
        signUpPage.clickRegister();

        Assert.assertEquals(signUpPage.getConfirmationText(), "Account is created!");
    }
}
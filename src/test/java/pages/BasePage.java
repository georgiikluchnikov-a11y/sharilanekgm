package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Общая часть Page Object: явные ожидания, безопасный клик с повтором
 * и поддержка headless-режима.
 */
public abstract class BasePage {

    protected static final Duration TIMEOUT = Duration.ofSeconds(20);

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void type(By locator, String text) {
        WebElement element = visible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Клик по элементу с повторными попытками: сначала обычный клик, затем
     * клик через JavaScript (помогает, если страница медленно отвечает).
     */
    protected void clickWithRetry(By locator) {
        RuntimeException last = null;
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                clickable(locator).click();
                return;
            } catch (RuntimeException error) {
                last = error;
                try {
                    WebElement element = visible(locator);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    return;
                } catch (RuntimeException jsError) {
                    last = jsError;
                }
            }
        }
        throw last;
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }
}

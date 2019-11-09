package com.imalittletester;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waiter {
    private WebDriver driver;

    public Waiter(WebDriver driver) {
        this.driver = driver;
    }

    public void get(String url) {
        driver.get(url);
        waitForPageLoadComplete();
    }

    public void waitForPageLoadComplete() {
        Wait<WebDriver> wait = new WebDriverWait(driver, 30);
        wait.until(driver1 -> String
                .valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState"))
                .equals("complete"));
    }

    public void waitForElementTextEqualsString(WebElement element, String expectedString) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        ExpectedCondition<Boolean> elementTextEqualsString = arg0 -> element.getText().equals(expectedString);
        wait.until(elementTextEqualsString);
    }

    public void waitForElementToBeDisplayed(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ExpectedCondition<Boolean> elementIsDisplayed = arg0 -> element.isDisplayed();
        wait.until(elementIsDisplayed);
    }
}

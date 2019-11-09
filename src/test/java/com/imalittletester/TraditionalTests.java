package com.imalittletester;

import com.imalittletester.objects.Amount;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(PER_CLASS)
class TraditionalTests extends BaseClass {
    private final SoftAssertions softAssertions = new SoftAssertions();

    @BeforeAll
    void beforeAll() {
        driver = browserGetter.getChromeDriver();
        waiter = new Waiter(driver);
        page = PageFactory.initElements(driver, LoginPage.class);
    }

    @BeforeEach
    void beforeEach() {
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    void afterAll() {
        driver.quit();
    }

    @Order(1)
    @Test
    void loginPageUiElements() {
        waiter.get(V2_APP_URL);
        softAssertions.assertThat(page.getLogoImageSource()).isEqualTo("https://demo.applitools" +
                ".com/img/logo-big.png");
        softAssertions.assertThat(page.getHeaderText()).isEqualTo("Login Form");

        softAssertions.assertThat(page.getForLabelByIndex(0)).isEqualTo("Username");
        softAssertions.assertThat(page.getForLabelByIndex(1)).isEqualTo("Password");
        softAssertions.assertThat(page.getUsernameField().getAttribute("placeholder")).isEqualTo("Enter your username");
        softAssertions.assertThat(page.getPasswordField().getAttribute("placeholder")).isEqualTo("Enter your password");

        softAssertions.assertThat(page.getFieldIcons().size()).isEqualTo(2);

        softAssertions.assertThat(page.getLoginButton().getText()).isEqualTo("Log In");

        softAssertions.assertThat(page.getRememberMeLabel().getText()).isEqualTo("Remember Me");

        softAssertions.assertThat(page.getSocialMediaButtonsUrls()).isEqualTo(Arrays.asList("https://demo.applitools" +
                        ".com/img/social-icons/twitter.png", "https://demo.applitools.com/img/social-icons/facebook.png",
                "https://demo.applitools.com/img/social-icons/linkedin.png"));

        softAssertions.assertAll();

        page.getRememberMeCheckbox().isDisplayed();
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource({",,Both Username and Password must be present", "email@example.com,,Password must be present",
            ",password,Username must be present", "email@example.com,password,"})
    void login(String username, String password, String expectedError) {
        waiter.get(V2_APP_URL);
        if (username != null)
            page.getUsernameField().sendKeys(username);
        if (password != null)
            page.getPasswordField().sendKeys(password);
        page.getLoginButton().click();
        if (expectedError != null)
            waiter.waitForElementTextEqualsString(page.getErrorMessage(), expectedError);
        else waiter.waitForElementToBeDisplayed(page.getLoggedInCustomerSection());
    }

    @Order(3)
    @Test
    void sortTable() {
        waiter.get(V2_APP_URL);
        login();
        List<Amount> expectedInitialListOfAmounts = expectedInitialListOfAmounts();
        //compare list of exepected amount data to data read from the webpage
        assertEquals(expectedInitialListOfAmounts, getAmountsFromSite());

        //sort the list of expected amount data and compare it to data read from the webpage after clicking the
        // Amount button
        expectedInitialListOfAmounts.sort(Comparator.comparingDouble(Amount::getAmount));
        page.getAmountSortButton().click();
        assertEquals(expectedInitialListOfAmounts, getAmountsFromSite());
    }

    @Order(4)
    @Test
    void canvasChart() throws IOException, InterruptedException {
        waiter.get(V2_APP_URL);
        login();
        page.getShowExpensesChart().click();
        //move to the left and lower in order to see the entire chart
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(400,200)");
        //since i don't have access to the canvas element from Selenium, hard code a sleep here. Oops!
        //just to make sure the height of the bars are the same each time a screenshot is made
        nap();
        //take a screenshot of the chart
        File screenshotFile = new File("./src/test/resources/actualExpensesChart.png");
        File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotAs, screenshotFile);
        //compare the chart to an 'expected' file
        softAssertions.assertThat(FileUtils.contentEquals(new File("./src/test/resources/expectedExpensesChart.png"),
                screenshotFile)).isTrue();

        //now add data for next year
        page.getShowDataForNextYear().click();
        nap();
        screenshotFile = new File("./src/test/resources/actualExpensesChartNextYear.png");
        screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotAs, screenshotFile);
        softAssertions.assertThat(FileUtils.contentEquals(new File("./src/test/resources" +
                        "/expectedExpensesChartNextYear.png"),
                screenshotFile)).isTrue();
        softAssertions.assertAll();

    }

    @Order(5)
    @Test
    void ad() {
        waiter.get(V2_APP_URL + "?showAd=true");
        login();
        softAssertions.assertThat(page.getFirstAdImage().getAttribute("src")).isEqualTo("https://demo.applitools" +
                ".com/img/flashSale.gif");
        softAssertions.assertThat(page.getSecondAdImage().getAttribute("src")).isEqualTo("https://demo.applitools" +
                ".com/img/flashSale2.gif");
        softAssertions.assertAll();
    }

    private void nap() throws InterruptedException {
        Thread.sleep(2000);
    }

    private List<Amount> getAmountsFromSite() {
        List<Amount> listOfAmountsFromSite = new ArrayList<>();
        for (WebElement element : page.getTableRows()) {
            listOfAmountsFromSite.add(new Amount(element.findElements(By.cssSelector("td"))));
        }
        return listOfAmountsFromSite;
    }

    private void login() {
        page.getUsernameField().sendKeys(RandomStringUtils.randomAlphanumeric(10));
        page.getPasswordField().sendKeys(RandomStringUtils.randomAlphanumeric(10));
        page.getLoginButton().click();
        waiter.waitForElementToBeDisplayed(page.getLoggedInCustomerSection());
    }

    private List<Amount> expectedInitialListOfAmounts() {
        String green = "green";
        String red = "red";
        String yellow = "yellow";
        String pending = "Pending";
        String complete = "Complete";
        String blue = "blue";
        Color amountGreenColor = Color.fromString("rgba(36, 179, 20, 1)");
        Color amountRedColor = Color.fromString("rgba(230, 82, 82, 1)");
        return Arrays.asList(new Amount(green, complete, "Today", "1:52am", "https://demo" +
                        ".applitools.com/img/company1.png", "Starbucks coffee", "Restaurant / Cafe", green, 1250.0,
                        amountGreenColor), new Amount(red, "Declined", "Jan 19th", "3:22pm", "https://demo.applitools" +
                        ".com/img/company2.png", "Stripe Payment Processing", "Finance", red, 952.23, amountGreenColor)
                , new Amount(yellow, pending, "Yesterday", "7:45am", "https://demo.applitools.com/img/company3.png"
                        , "MailChimp Services", "Software", yellow, -320.0, amountRedColor), new Amount(yellow,
                        pending, "Jan 23rd", "2:7pm", "https://demo.applitools.com/img/company6.png", "Shopify product",
                        "Shopping", blue, 17.99, amountGreenColor), new Amount(green, complete, "Jan 7th", "9:51am",
                        "https://demo.applitools.com/img/company4.png", "Ebay Marketplace", "Ecommerce", red, -244.0,
                        amountRedColor), new Amount(yellow, pending, "Jan 9th", "7:45pm", "https://demo.applitools" +
                        ".com/img/company7.png", "Templates Inc", "Business", blue, 340.0, amountGreenColor));
    }
}

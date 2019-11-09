package com.imalittletester;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.support.PageFactory;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(PER_CLASS)
class VisualAITests extends BaseClass {
    private final ClassicRunner runner = new ClassicRunner();
    private final Eyes eyes = new Eyes(runner);
    private static final BatchInfo batch = new BatchInfo("AI tests");


    @BeforeAll
    void beforeAll() {
        driver = browserGetter.getChromeDriver();
        waiter = new Waiter(driver);
        page = PageFactory.initElements(driver, LoginPage.class);
        eyes.setApiKey("TJYu0lTHsKwcwHEMSR2TRjId4g3cH19IQr1017IYXiimg110");
        eyes.setBatch(batch);
        eyes.setForceFullPageScreenshot(true);
    }

    @BeforeEach
    void beforeEach() {
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    void afterAll() {
        driver.quit();
        TestResultsSummary allTestResults = runner.getAllTestResults();
        System.out.println(allTestResults);
    }

    @Order(1)
    @Test
    void loginPageUiElements() {
        waiter.get(V2_APP_URL);
        eyes.open(driver, "Demo App", "Login Windows");
        eyes.checkWindow("Login Window");
        eyes.closeAsync();
    }

    @Order(2)
    @ParameterizedTest
    @CsvSource({",,Both Username and Password must be present", "email@example.com,,Password must be present",
            ",password,Username must be present", "email@example.com,password,Success"})
    void login(String username, String password, String situation) {
        eyes.open(driver, "Demo App", situation);
        waiter.get(V2_APP_URL);
        if (username != null)
            page.getUsernameField().sendKeys(username);
        if (password != null)
            page.getPasswordField().sendKeys(password);
        page.getLoginButton().click();
        eyes.checkWindow(situation);
        eyes.closeAsync();
    }

    @Order(3)
    @Test
    void sortTable() {
        waiter.get(V2_APP_URL);
        login();
        eyes.open(driver, "Demo App", "Sort table");
        eyes.checkWindow("Before sorting");
        page.getAmountSortButton().click();
        eyes.checkWindow("After sorting");
        eyes.closeAsync();
    }

    @Order(4)
    @Test
    void canvasChart() throws InterruptedException {
        waiter.get(V2_APP_URL);
        login();
        page.getShowExpensesChart().click();
        eyes.open(driver, "Demo App", "Canvas");
        eyes.checkWindow("Show expenses");
        page.getShowDataForNextYear().click();
        nap();
        eyes.checkWindow("Show expenses - next year");
        eyes.closeAsync();
    }

    @Order(5)
    @Test
    void ad() {
        waiter.get(V2_APP_URL + "?showAd=true");
        login();
        eyes.open(driver, "Demo App", "Ads");
        eyes.checkWindow("Ads");
        eyes.closeAsync();
    }

    private void login() {
        page.getUsernameField().sendKeys(RandomStringUtils.randomAlphanumeric(10));
        page.getPasswordField().sendKeys(RandomStringUtils.randomAlphanumeric(10));
        page.getLoginButton().click();
        waiter.waitForElementToBeDisplayed(page.getLoggedInCustomerSection());
        waiter.waitForPageLoadComplete();
    }

    private void nap() throws InterruptedException {
        Thread.sleep(2000);
    }

}

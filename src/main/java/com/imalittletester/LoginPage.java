package com.imalittletester;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class LoginPage {

    @FindBy(css = ".logo-w img") private WebElement logoImage;
    @FindBy(css = ".auth-header") private WebElement headerTextElement;
    @FindBy(css = "[for]") private List<WebElement> forLabels;

    @FindBy(css = "#username") private WebElement usernameField;
    @FindBy(css = "#password") private WebElement passwordField;

    @FindBy(css = ".pre-icon") private List<WebElement> fieldIcons;

    @FindBy(css = "#log-in") private WebElement loginButton;

    @FindBy(css = ".form-check-label") private WebElement rememberMeLabel;
    @FindBy(css = "[type='checkbox']") private WebElement rememberMeCheckbox;

    @FindBy(css = ".buttons-w img") private List<WebElement> socialMediaButtons;

    @FindBy(css = ".alert-warning") private WebElement alert;

    @FindBy(css = ".logged-user-w") private WebElement loggedInCustomerSection;

    @FindBy(css = "#amount") private WebElement amountSortButton;

    @FindBy(css = "tbody tr") private List<WebElement> tableRows;

    @FindBy(css = "#showExpensesChart") private WebElement showExpensesChart;

    @FindBy(css = "#addDataset") private WebElement showDataForNextYear;

    @FindBy(css = "#flashSale img") private WebElement firstAdImage;
    @FindBy(css = "#flashSale2 img") private WebElement secondAdImage;

    public String getLogoImageSource() {
        return logoImage.getAttribute("src");
    }

    public String getHeaderText() {
        return headerTextElement.getText();
    }

    public String getForLabelByIndex(int i) {
        return forLabels.get(i).getText();
    }

    public WebElement getUsernameField() {
        return usernameField;
    }

    public WebElement getPasswordField() {
        return passwordField;
    }

    public List<WebElement> getFieldIcons() {
        return fieldIcons;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }

    public WebElement getRememberMeLabel() {
        return rememberMeLabel;
    }

    public WebElement getRememberMeCheckbox() {
        return rememberMeCheckbox;
    }

    public List<String> getSocialMediaButtonsUrls() {
        List<String> buttonUrls = new ArrayList<>();
        for (WebElement socialMediaButton : socialMediaButtons)
            buttonUrls.add(socialMediaButton.getAttribute("src"));
        return buttonUrls;
    }

    public WebElement getErrorMessage() {
        return alert;
    }

    public WebElement getLoggedInCustomerSection() {
        return loggedInCustomerSection;
    }

    public WebElement getAmountSortButton() {
        return amountSortButton;
    }

    public List<WebElement> getTableRows() {
        return tableRows;
    }

    public WebElement getShowExpensesChart() {
        return showExpensesChart;
    }

    public WebElement getShowDataForNextYear() {
        return showDataForNextYear;
    }

    public WebElement getFirstAdImage() {
        return firstAdImage;
    }

    public WebElement getSecondAdImage() {
        return secondAdImage;
    }
}

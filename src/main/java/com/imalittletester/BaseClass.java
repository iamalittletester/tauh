package com.imalittletester;

import org.openqa.selenium.WebDriver;

public class BaseClass {
    protected WebDriver driver;
    protected final BrowserGetter browserGetter = new BrowserGetter();
    protected Waiter waiter;
    protected LoginPage page;

    protected final String V1_APP_URL = "https://demo.applitools.com/hackathon.html";
    protected final String V2_APP_URL = "https://demo.applitools.com/hackathonV2.html";
}

package com.imalittletester;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.lang.System.setProperty;
import static org.apache.commons.lang3.SystemUtils.*;

public class BrowserGetter {

    public WebDriver getChromeDriver() {
        if (!IS_OS_WINDOWS && !IS_OS_LINUX && !IS_OS_MAC) {
            throw new RuntimeException("Could not initialize browser due to unknown operating system!");
        }
        if (IS_OS_WINDOWS) {
            setProperty("webdriver.chrome.driver", "src/test/resources/browserBinaries/chromedriver.exe");
        }
        if (IS_OS_LINUX) {
            setProperty("webdriver.chrome.driver", "src/test/resources/browserBinaries/chromedriver");
        }
        if (IS_OS_MAC) {
            setProperty("webdriver.chrome.driver", "src/test/resources/browserBinaries/chromedriverMac");
        }

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }
}

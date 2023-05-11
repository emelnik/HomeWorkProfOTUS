package driver.impl;

import exceptions.DriverTypeNotSupported;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeWebDriver implements IDiver {

  @Override
  public WebDriver newDriver() throws DriverTypeNotSupported {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--no-sandbox");
    chromeOptions.addArguments("--no-first-run");
    chromeOptions.addArguments("--homepage=about:blank");
    chromeOptions.addArguments("--ignore-certificate-errors");

    downloadLocalWebdriver(DriverManagerType.CHROME);
    return new ChromeDriver(chromeOptions);
  }
}

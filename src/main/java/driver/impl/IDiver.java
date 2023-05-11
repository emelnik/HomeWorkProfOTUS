package driver.impl;

import exceptions.DriverTypeNotSupported;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.Config;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.WebDriver;

public interface IDiver {

  public WebDriver newDriver() throws DriverTypeNotSupported;

  default void downloadLocalWebdriver(DriverManagerType driverType) throws DriverTypeNotSupported {

    Config wdmConfig = WebDriverManager.globalConfig();
    wdmConfig.setAvoidBrowserDetection(true);

    String browserVersion = System.getProperty("browser.version", "");

    if (!browserVersion.isEmpty()) {
      switch (driverType) {
        case CHROME:
          wdmConfig.setChromeDriverVersion(browserVersion);
          break;
        default:
          throw new DriverTypeNotSupported(driverType);
      }
    }

    WebDriverManager.getInstance(driverType).setup();
  }
}

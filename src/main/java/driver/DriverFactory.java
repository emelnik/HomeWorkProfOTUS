package driver;

import driver.impl.ChromeWebDriver;
import exceptions.DriverTypeNotSupported;
import org.openqa.selenium.WebDriver;

import java.util.Locale;

public class DriverFactory implements IDriverFactory {

  private String browserType = System.getProperty("browser").toLowerCase(Locale.ROOT);

  @Override
  public WebDriver getDriver() throws DriverTypeNotSupported {

    switch (this.browserType) {
      case "chrome": {
        return new ChromeWebDriver().newDriver();
      }
      default: {
        try {
          throw new DriverTypeNotSupported(this.browserType);
        } catch (DriverTypeNotSupported ex) {
          ex.printStackTrace();
          return null;
        }
      }
    }
  }
}

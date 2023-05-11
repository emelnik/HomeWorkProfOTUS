package components;

import org.openqa.selenium.WebDriver;
import pageobject.AbsPageObject;

public abstract class AbsComponent<T> extends AbsPageObject<T> {

  public AbsComponent(WebDriver driver) {
    super(driver);
  }

}

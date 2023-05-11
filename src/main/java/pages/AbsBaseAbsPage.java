package pages;

import annotations.Path;
import org.openqa.selenium.WebDriver;
import pageobject.AbsPageObject;

public abstract class AbsBaseAbsPage<T> extends AbsPageObject<T> {

  public AbsBaseAbsPage(WebDriver driver) {
    super(driver);
  }

  private String baseUrl = System.getProperty("webdriver.base.url", "https://otus.ru");

  private String getPath() {

    Path path = getClass().getAnnotation(Path.class);
    if (path != null) {
      return path.value();
    }

    return "";
  }

  public T open() {
    driver.get(baseUrl + getPath());

    return (T) this;
  }

}

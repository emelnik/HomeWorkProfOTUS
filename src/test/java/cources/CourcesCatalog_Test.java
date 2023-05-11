package cources;

import annotations.Driver;
import extensions.UIExtensions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import pages.MainAbsPage;

@ExtendWith(UIExtensions.class)
public class CourcesCatalog_Test {

  @Driver
  private WebDriver driver;

  @Test
  public void checkClickPopularCoursesOnMainPage() {
    new MainAbsPage(driver).open().checkClickPopularCourses();
  }

  @Test
  public void filterCoursesName(){
    new MainAbsPage(driver).open().filterNameCourses("Бизнес-анализ и системный");
  }

  @Test
  public void coursesEarlyLastItem(){
    new MainAbsPage(driver).open().earlyLastCoursesItem(true);
  }

  @Test
  public void moveToCoursesMouse(){
    new MainAbsPage(driver).open().moveActionCursor();
  }

}

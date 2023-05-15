package pages;

import annotations.Path;
import data.Month;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Path("/")
public class MainAbsPage extends AbsBaseAbsPage<MainAbsPage> {

  public MainAbsPage(WebDriver driver) {
    super(driver);
  }

  @FindBy(css = ".lessons > a")
  private List<WebElement> listElement;


  @FindBy(css = "[class='lessons'] .lessons__new-item-start, [class='lessons'] .lessons__new-item-bottom > .lessons__new-item-time")
  private List<WebElement> lessonsItems;

  public void checkClickPopularCourses() {
    JavascriptExecutor js = (JavascriptExecutor) driver;

    List<WebElement> webElements = driver.findElements(By
        .xpath("//div[text()='Популярные курсы']/following-sibling::div/a[contains(@href, 'lessons')]"));

    WebElement coursesCart = webElements.get(ThreadLocalRandom.current().nextInt(0, 2));

    js.executeScript("arguments[0].style.outline = '5px solid #ff0000';", coursesCart);

    js.executeScript("arguments[0].removeAttribute(\"style\")", coursesCart);

    js.executeScript("window.scrollBy(0,600)");

    coursesCart.click();

  }

  public void filterNameCourses(String coursesName) {

    String coursesTitle;
    JavascriptExecutor js = (JavascriptExecutor) driver;

    List<WebElement> list = listElement.stream()
        .filter(webElement -> webElement
            .findElement(By.cssSelector(".lessons__new-item-title"))
            .getText().equals(coursesName))
        .collect(Collectors.toList());

    if (!list.isEmpty()) {

      for (WebElement webElement : list) {

        js.executeScript("arguments[0].scrollIntoView();", webElement);

        webElement.click();

        coursesTitle = driver.findElement(By.cssSelector("h1")).getText();

        Assertions.assertEquals(coursesName, coursesTitle);

      }
    } else {

      Assertions.assertFalse(list.isEmpty());

    }
  }

  public void earlyLastCoursesItem(boolean isEarly) {

    Map<WebElement, LocalDate> elementByDate = new HashMap<>();

    if (isEarly) {
      lessonsItems
          .stream()
          .filter((WebElement element) -> !element.getText().equals("О дате старта будет объявлено позже"))
          .map((WebElement element) -> {
                String dateStr = element.getText().replaceAll("^С ", "");
                Pattern pattern = Pattern.compile("(\\d+|^\\s+)\\s+([а-я]+).*");
                Matcher matcher = pattern.matcher(dateStr);

                if (matcher.find()) {
                  int dayInMonth = Integer.parseInt(matcher.group(1));
                  String month = String.valueOf(Month.getMonthNumber(matcher.group(2)));
                  int year = LocalDate.now().getYear();

                  dateStr = dayInMonth + " " + month + " " + year;

                  elementByDate.put(element, LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("d M yyyy")));
                }

                return elementByDate;
                }
          ).collect(Collectors.toList());

      Optional<Map.Entry<WebElement, LocalDate>> listEarlyWebElement = elementByDate.entrySet().stream()
          .reduce((p1, p2) ->
          {
            LocalDate date1 = p1.getValue();
            LocalDate date2 = p2.getValue();
            return date2.isAfter(date1) || date2.isEqual(date1) ? p1 : p2;
          });

      WebElement cartCoursesElement = listEarlyWebElement
          .get()
          .getKey()
          .findElement(By.xpath(".//ancestor::a"));

      String titleCourses = cartCoursesElement
          .findElement(By.cssSelector(".lessons__new-item-title"))
          .getText();

      cartCoursesElement.click();

      String titlePageCourses = driver
          .findElement(By.cssSelector(".course-header2__info-text > .course-header2__title"))
          .getText();

      Assertions.assertTrue(titlePageCourses.contains(titleCourses));

    } else {
      lessonsItems.stream()
          .filter((WebElement element) -> !element.getText().equals("О дате старта будет объявлено позже"))
          .map((WebElement element) -> {
                String dateStr = element.getText().replaceAll("^С ", "");
                Pattern pattern = Pattern.compile("(\\d+|^\\s+)\\s+([а-я]+).*");
                Matcher matcher = pattern.matcher(dateStr);

                if (matcher.find()) {
                  int dayInMonth = Integer.parseInt(matcher.group(1));
                  String month = String.valueOf(Month.getMonthNumber(matcher.group(2)));
                  int year = LocalDate.now().getYear();

                  dateStr = dayInMonth + " " + month + " " + year;

                  elementByDate.put(element, LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("d M yyyy")));
                }

                return elementByDate;
                }
          ).collect(Collectors.toList());

      Optional<Map.Entry<WebElement, LocalDate>> listEarlyWebElement = elementByDate.entrySet().stream()
          .reduce((p1, p2) ->
              {
                LocalDate date1 = p1.getValue();
                LocalDate date2 = p2.getValue();
                return date1.isAfter(date2) || date2.isEqual(date1) ? p1 : p2;
              }
          );

      WebElement cartCoursesElement = listEarlyWebElement
          .get()
          .getKey()
          .findElement(By.xpath(".//ancestor::a"));

      String titleCourses = cartCoursesElement
          .findElement(By.cssSelector(".lessons__new-item-title"))
          .getText();

      cartCoursesElement.click();

      String titlePageCourses = driver
          .findElement(By.cssSelector("h1"))
          .getText();

      Assertions.assertTrue(titlePageCourses.contains(titleCourses));

    }
  }

  public void moveActionCursor() {

    WebElement moveMouseElement = listElement.get(1);

    String titleCourses = moveMouseElement
        .findElement(By.cssSelector(".lessons__new-item-title"))
        .getText();

    JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
    javascriptExecutor.executeScript("window.scrollBy(0,250)");

    this.actions
        .moveToElement(moveMouseElement)
        .click()
        .build()
        .perform();

    String titlePageCourses = driver.findElement(By.cssSelector(".course-header2__title")).getText();

    Assertions.assertEquals(titleCourses, titlePageCourses);

  }
}



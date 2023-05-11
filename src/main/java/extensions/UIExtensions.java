package extensions;

import annotations.Driver;
import driver.DriverFactory;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashSet;
import java.util.Set;

public class UIExtensions implements BeforeEachCallback, AfterEachCallback {

  private WebDriver driver = null;

  private Set<Field> getAnnotatedFields(Class<? extends Annotation> annotation, ExtensionContext extensionContext) {
    Set<Field> setField = new HashSet<>();
    Class<?> testClass = extensionContext.getTestClass().get();
    while (testClass != null) {
      for (Field field : testClass.getDeclaredFields()) {
        if (field.isAnnotationPresent(annotation)) {
          setField.add(field);
        }
      }
      testClass = testClass.getSuperclass();
    }
    return setField;
  }

  @Override
  public void beforeEach(ExtensionContext extensionContext) throws Exception {

    driver = new DriverFactory().getDriver();
    Set<Field> fields = getAnnotatedFields(Driver.class, extensionContext);

    for (Field field : fields) {
      if (field.getType().getName().equals(WebDriver.class.getName())) {
        AccessController.doPrivileged((PrivilegedAction<Void>)
            () -> {
              try {
                field.setAccessible(true);
                field.set(extensionContext.getTestInstance().get(), driver);
              } catch (IllegalAccessException e) {
                throw new Error(String.format("Could not access or set webdriver in field: $s - is this field public?", field));
              }
              return null;
            }
        );
      }
    }


  }

  @Override
  public void afterEach(ExtensionContext extensionContext) throws Exception {

    if (driver != null) {
      driver.close();
      driver.quit();
    }
  }
}

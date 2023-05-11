package pages;

import annotations.Path;
import annotations.UrlTemplate;
import annotations.Urls;
import org.openqa.selenium.WebDriver;

@Path("/courses")
@Urls(
    @UrlTemplate(name = "category", value = "?categories=$1")
)
public class CourcesCatalog extends AbsBaseAbsPage<CourcesCatalog> {

  public CourcesCatalog(WebDriver driver) {
    super(driver);
  }


}

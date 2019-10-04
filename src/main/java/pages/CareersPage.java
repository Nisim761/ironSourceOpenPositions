package pages;

import Utils.WebDriverHandler;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CareersPage {

  // members
  private WebDriverHandler _driverHandler;

  // locators of elements
  private final String pageUrl = "https://company.ironsrc.com/careers/";
  private String _location = "//a[contains(@href,'careers-***')]";
  private String _domainsDropDownList = "//label[@for='cat-filter']";
  private String _domain = "//div[@class='career-filters']//li[text()='***']";
  private String _domainErrorMessage = "//*[contains(text(),'We don't have an open position like that right now.')]";
  private String _selectedDomain = "//span[@class='active-cat']";
  private String _seeMorePositions = "//button[@class='sc_btn']//*[contains(text(),'See more positions')]";
  private String _openPositionsLabel = "//div[contains(text(),'open positions')]";
  private String _careersListItem = "//div[@class='container career-item']";
  private String _placeholderRegex = "***";


  public CareersPage(WebDriverHandler driverHandler) {

    _driverHandler = driverHandler;
  }

  public void loadPage() {

    _driverHandler.navigateToUrl(pageUrl);
  }

  public void clickLocation(String location) {

    String updatedLocation = _location.replace(_placeholderRegex,location);
    _driverHandler.seleniumClick(updatedLocation);
  }

  public boolean validateLocation(String location) {

    String currentUrl = _driverHandler.getUrl();
    return currentUrl.toLowerCase().contains(location.toLowerCase());
  }

  public void clickDomain(String domain) {

    // expand the drop-down list
    _driverHandler.seleniumClick(_domainsDropDownList);

    // select the desired option
    String updatedDomain = _domain.replace(_placeholderRegex, domain);
    _driverHandler.javaScriptExecutorClick(updatedDomain);
    _driverHandler.actionsObjectClick(updatedDomain);

    // sometime the drop-down list stays open, collapse it
    _driverHandler.seleniumClick(_openPositionsLabel);

    // sometimes the list of open positions is empty. a simple refresh will populate it
    while (_driverHandler.seleniumCheckForPresenceWithWait(_domainErrorMessage,5)) {
      _driverHandler.refreshWebpage();
    }
  }

  public boolean validateDomain(String domain) {

    String currentDomain = _driverHandler.getElementText(_selectedDomain);
    return currentDomain.toLowerCase().contains(domain.toLowerCase());
  }

  public void clickSeeMorePositions() {

    _driverHandler.javaScriptExecutorClick(_seeMorePositions);
  }

  public void makeAllPositionsVisible() {

    while (_driverHandler.seleniumCheckForPresenceWithWait(_seeMorePositions,5)) {
      clickSeeMorePositions();
    }
  }

  public int getNumberOfOpenPositionsFromLabel() {

    String labelText = _driverHandler.getElementText(_openPositionsLabel);
    return Integer.parseInt(labelText.split(" ")[0]);
  }

  public int getNumberOfItemsInPositionsList() {

    List<WebElement> openPositionsItems = _driverHandler.getListOfItems(_careersListItem);
    return openPositionsItems.size();
  }
}

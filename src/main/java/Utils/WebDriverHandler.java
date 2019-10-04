package Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class WebDriverHandler {

  // members
  private WebDriver _driver;
  private JavascriptExecutor _jsExecutor;
  private Actions _actions;


  public WebDriverHandler(EWebDriverType browser) throws Exception {

    if (null != _driver) {
      _driver.quit();
    }

    switch (browser) {
      case Chrome:
        createChromeDriver();
        break;
      case Firefox:
        createFirefoxDriver();
        break;
      case Safari:
        createSafariDriver();
        break;
        default:
          throw new Exception("Browser type is not supported");
    }

    _jsExecutor = (JavascriptExecutor) _driver;
    _actions = new Actions(_driver);
  }

  public void createChromeDriver() {

    _driver = new ChromeDriver();
    _driver.manage().window().maximize();
  }

  // TODO - implement
  public void createFirefoxDriver() {
    throw new NotImplementedException();
  }

  // TODO - implement
  public void createSafariDriver() {
    throw new NotImplementedException();
  }

  public WebDriver getWebDriver() { return _driver; }

  public JavascriptExecutor getJSExecutor() { return _jsExecutor; }

  public Actions getActionsObject() { return _actions; }

  public void close() {

    if (null != _driver) {
      _driver.quit();
      _driver = null;
      _jsExecutor = null;
      _actions = null;
    }
  }

  public void navigateToUrl(String url) {

    _driver.get(url);
  }

  public String getUrl() {

    return _driver.getCurrentUrl();
  }

  public void refreshWebpage() {

    _driver.navigate().refresh();
  }

  public void seleniumClick(String locator) {

    WebElement element = _driver.findElement(By.xpath(locator));
    element.click();
  }

  public List<WebElement> getListOfItems(String locator) {

    return _driver.findElements(By.xpath(locator));
  }

  public void seleniumWaitAndClick(String locator, int seconds) {

    WebDriverWait wait = new WebDriverWait(_driver, seconds);
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
    seleniumClick(locator);
  }

  public String getElementText(String locator) {

    WebElement element = _driver.findElement(By.xpath(locator));
    return element.getText();
  }

  public boolean seleniumCheckForPresenceWithWait(String locator, int seconds) {

    boolean isFound = true;
    try {
      WebDriverWait wait = new WebDriverWait(_driver, seconds);
      wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    } catch (Exception e) {
      isFound = false;
    }
    return isFound;
  }

  public void javaScriptExecutorClick(String locator) {

    WebElement element = _driver.findElement(By.xpath(locator));
    _jsExecutor.executeScript("arguments[0].click();", element);
  }

  public void actionsObjectClick(String locator) {

    WebElement element = _driver.findElement(By.xpath(locator));
    _actions.moveToElement(element).click(element).build().perform();
  }
}

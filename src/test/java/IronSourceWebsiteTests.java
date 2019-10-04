import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Utils.EWebDriverType;
import Utils.WebDriverHandler;

import pages.CareersPage;

public class IronSourceWebsiteTests {

  // members
  private WebDriverHandler _webDriverHandler;

  public IronSourceWebsiteTests() {}

  @Parameters({ "browser" })
  @BeforeTest
  public void initialize(String browser) throws Exception {

    try {
      _webDriverHandler = new WebDriverHandler(EWebDriverType.valueOf(browser));
    } catch (Exception e) {
      throw new Exception("Could not create webdriver");
    }
  }

  @Parameters({ "location", "domain" })
  @Test
  public void validateNumberOfOpenPositions(String location, String domain) {

    String testName = "Validate open positions counter";

    try {
      System.out.println("Executing test: " + testName);

      CareersPage careersPage = new CareersPage(_webDriverHandler);

      // navigate to "careers" page
      careersPage.loadPage();

      // select location
      careersPage.clickLocation(location);

      // validate location
      if (! careersPage.validateLocation(location)) {
        throw new Exception("Failed to select required location");
      }

      // select domain
      careersPage.clickDomain(domain);

      // validate domain
      if (! careersPage.validateDomain(domain)) {
        throw new Exception("Failed to select required domain");
      }

      // make all open positions visible
      careersPage.makeAllPositionsVisible();

      // compare number of position in label to actual number of items in list
      int counterFromLabel = careersPage.getNumberOfOpenPositionsFromLabel();
      int numberOfItemsInList = careersPage.getNumberOfItemsInPositionsList();
      boolean compare = (counterFromLabel == numberOfItemsInList);

      String message = "Number of open positions match the counter";
      if (! compare) {
        message = message.replace("match","do not match");
      }

      Assert.assertTrue(compare, message);

    } catch (Exception e) {
      Assert.assertTrue(false, "Test: " + testName + " failed.\nReason: " + e.getMessage());
    }
  }

  @AfterTest
  public void teardown() {

    _webDriverHandler.close();
  }
}

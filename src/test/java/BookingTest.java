/**
 * Created by Max Maku on 11.05.2016.
 */
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import static org.junit.Assert.assertEquals;
import java.util.Calendar;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BookingTest {

    private final static Logger log = LoggerFactory.getLogger(BookingTest.class);


    private static final Integer MINWAIT = 1000;
    private static final Integer SOMEWAIT = 2000;
    private static final String MISSMATCH_DATE_EXEPTION = "Mismatch date";
    private static final String WRONG_SEARCH_RESULT_EXEPTION = "Wrong search results";

    //Language
    private static final By LANG_US_CLASS = By.className("lang_en-us");
    private static final By LANGUAGE_BUTTON_CSS = By.cssSelector("#user_form .user_center_nav .uc_language a.popover_trigger");
    private static final String LANG_ATTRIB_DATA_NAME = "data-title";
    private static final String LANG_ATTRIB_DATA_US = "ZOVLAFFHJCdCEaXCfAOBRUDTbfZZbHe";

    //Currency
    private static final By CURRENCY_BUTTON_CSS = By.cssSelector("#user_form .user_center_nav .uc_currency a.popover_trigger");
    private static final By CURRENCY_BUTTON_IMG_CSS = By.cssSelector("#user_form .user_center_nav .uc_language a.popover_trigger img");

    private static final By CURRENCY_USD_CLASS = By.className("currency_USD");

    //Destination
    private static final By DESTINATION_ID = By.id("ss");
    private static final By DESTINATION_AUTOCOMPLETE_CLASS = By.className("c-autocomplete__item");

    // Calendar
    private static final By CALENDARS_CLASS = By.className("b-datepicker");
    private static final By CALENDARS_FURTHER_CLASS = By.className("c2-button-further");
    private static final By CALENDARS_MONTH_CLASS = By.className("c2-month-header-monthname");
    private static final String GET_DAYS_XPATH = "//table[@class='c2-month-table'][descendant::th[@class='c2-month-header-monthname'][text()='%s']]//span[@class='c2-day-inner'][normalize-space(text())!='']";

    // Traveling for
    private static final By TRAVELING_FOR_CLASS = By.className("b-booker-type__input_business-booker");

    //Rooms
    private static final By ROOMS_NAME = By.name("no_rooms");

    //Adults
    private static final By ADULTS_NAME = By.name("group_adults");

    // Search button
    private static final By SEARCH_BUTTON_XPATH = By.xpath("//button[@class='sb-searchbox__button  ']");
    private static final By UP_XPATH = By.xpath("./..");
    private static final By SEARCH_BUTTON_CSS = By.cssSelector(".sb-searchbox__button");
    private static final By SEARCH_BUTTON_CLASS = By.className("b-searchbox-button_legacy");
    private static final By SEARCH_TEXT_CLASS = By.className("b-button__text");

    // Search result page
    private static final By SEARCH_RESULT_LINK_CLASS = By.className("district_link");

    @Step("Before step 1")
    public void beforeStep1 () {
        log.info("Test started:");
    }

    @Step("Navigate to <http://www.booking.com>")
    public void navigateTo(String url) throws InterruptedException {
        Driver.driver.get(url);
        Thread.sleep(MINWAIT);
        log.info("main page opened");
    }

    @Step("Set EN-US language with check hash")
    public void chooseENUSLanguage() throws InterruptedException {
        WebElement b = Driver.driver.findElement(CURRENCY_BUTTON_IMG_CSS);
        if (b.getAttribute("src").toLowerCase().contains("/us/")) {
            log.info("EN-US skipped");
            return;
        }
        WebElement buttonLang = Driver.driver.findElement(LANGUAGE_BUTTON_CSS);
        buttonLang.click();
        WebElement buttonUS = Driver.driver.findElement(LANG_US_CLASS);
        buttonUS.click();
        WebElement result = Driver.driver.findElement(LANGUAGE_BUTTON_CSS);
        String hash =  Util.GetLanguageHash(result.getAttribute(LANG_ATTRIB_DATA_NAME));
        assertEquals(hash, LANG_ATTRIB_DATA_US);
        log.info("EN-US set");
    }

    @Step("Choose USD currency with title has <US$> as result")
    public void chooseUSDCurrency(String resultString) {
        WebElement buttonCurrency = Driver.driver.findElement(CURRENCY_BUTTON_CSS);
        if (buttonCurrency.getText().equals(resultString)) {
            log.info("USD skipped");
            return;
        }
        buttonCurrency.click();
        WebElement buttonUSD = Driver.driver.findElement(CURRENCY_USD_CLASS);
        buttonUSD.click();
        WebElement result = Driver.driver.findElement(CURRENCY_BUTTON_CSS);
        assertEquals(resultString, result.getText());
        log.info("USD set");
    }

    @Step("Set destination to <New York City>")
    public void setDestination(String destination) throws InterruptedException {
        WebElement edit = Driver.driver.findElement(DESTINATION_ID);
        edit.sendKeys(destination);
        Thread.sleep(MINWAIT); // need for get autocomplete data
        WebElement autocompleteFirst = Driver.driver.findElement(DESTINATION_AUTOCOMPLETE_CLASS);
        autocompleteFirst.click();
        log.info(destination + " set");
    }

    @Step ("Set Check-In date to < 3> of <September 2016>")
    public void setCheckInDate(String dayText, String monthText) throws InterruptedException {
        List<WebElement> pickers = Driver.driver.findElements(CALENDARS_CLASS);
        WebElement picker = pickers.get(0);
        picker.click();
        WebElement nextMonthButton = Driver.driver.findElement(CALENDARS_FURTHER_CLASS);
        // our month will be in left side of calendars
        for (int i = 0; i < 8 - Calendar.getInstance().get(Calendar.MONTH); i++) {
            nextMonthButton.click();
        }
        List<WebElement> months = Driver.driver.findElements(CALENDARS_MONTH_CLASS);
        WebElement month = Util.GetFirstWebElementByText(months, monthText);
        if (month == null) {
            throw new InterruptedException(MISSMATCH_DATE_EXEPTION);
        }
        String xPath = String.format(GET_DAYS_XPATH, monthText);
        List<WebElement> days = Driver.driver.findElements(By.xpath(xPath));
        WebElement day = Util.GetFirstWebElementByText(days, dayText);
        day.click();
        Thread.sleep(MINWAIT); //wait for js setup right part of calendars
        log.info(String.format("Check-In date %s %s set", dayText, monthText));
    }

    @Step ("Set Check-Out date to < 7> of <September 2016>")
    public void setCheckOutDate(String dayText, String monthText) throws InterruptedException {
        List<WebElement> pickers = Driver.driver.findElements(CALENDARS_CLASS);
        WebElement picker = pickers.get(1);
        picker.click();
        List<WebElement> months = Driver.driver.findElements(CALENDARS_MONTH_CLASS);
        WebElement month = Util.GetFirstWebElementByText(months, monthText);
        if (month == null) {
            throw new InterruptedException(MISSMATCH_DATE_EXEPTION);
        }
        String xPath = String.format(GET_DAYS_XPATH, monthText);
        List<WebElement> days = Driver.driver.findElements(By.xpath(xPath));
        WebElement day = Util.GetFirstWebElementByText(days, dayText);
        day.click();
        log.info(String.format("Check-Out date %s %s set", dayText, monthText));
    }

    @Step("Choose Traveling for Work")
    public void chooseTravelWork() {
        WebElement radioWork = Driver.driver.findElement(TRAVELING_FOR_CLASS);
        radioWork.click();
        log.info("Traveling set");
    }

    @Step("Set Rooms to <1>")
    public void chooseRooms(String rooms) {
        Select comboRooms = new Select(Driver.driver.findElement(ROOMS_NAME));
        comboRooms.selectByValue(rooms);
        log.info(String.format("Rooms set to %s", rooms));
    }

    @Step("Set Adults to <1>")
    public void chooseAdults(String adults) {
        Select comboAdults = new Select(Driver.driver.findElement(ADULTS_NAME));
        comboAdults.selectByValue(adults);
        log.info(String.format("Adults set to %s", adults));
    }

    @Step("Do search")
    public void doSearch() throws InterruptedException {
        //WebElement button = Driver.driver.findElement(SEARCH_BUTTON_CLASS);
        WebElement search = Driver.driver.findElement(SEARCH_TEXT_CLASS);
        WebElement button = search.findElement(UP_XPATH);
        button.click();
        Thread.sleep(SOMEWAIT);
        log.info("Do search");
    }

    @Step("Check search for <New York City>")
    public void checkSearch(String address) throws Exception {
        List<WebElement> links = Driver.driver.findElements(SEARCH_RESULT_LINK_CLASS);
        if (!Util.ContainsWebElementsText(links, address)) {
            log.error("Failure search");
            throw new InterruptedException(WRONG_SEARCH_RESULT_EXEPTION);
        } else {
            Util.TakeScreenshot();
            log.info("Search results Ok!");
        }
    }

    @Step("Tear down step 1")
    public void tearDown1 () {
        log.info("Test finished.");
    }
}

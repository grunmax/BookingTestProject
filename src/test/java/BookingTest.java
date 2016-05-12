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


public class BookingTest {

    private static final Integer MINWAIT = 2000;
    private static final String MISSMATCH_DATE_EXEPTION = "Mismatch date";
    private static final String WRONG_SEARCH_RESULT_EXEPTION = "Wrong search results";

    @Step("Navigate to <http://www.booking.com>")
    public void navigateTo(String url) throws InterruptedException {
        Driver.driver.get(url);
        Thread.sleep(MINWAIT);
    }

    @Step("Choose EN-US language")
    public void chooseENUSLanguage() {
        WebElement buttonLang = Driver.driver.findElement(By.cssSelector(BookingConstants.LANGUAGE_BUTTON_CSS));
        buttonLang.click();
        WebElement buttonUS = Driver.driver.findElement(By.className(BookingConstants.LANG_US_CLASS));
        buttonUS.click();
    }

    @Step("Check EN-US language")
    public void verifyENUSLanguage() {
        WebElement button = Driver.driver.findElement(By.cssSelector(BookingConstants.LANGUAGE_BUTTON_CSS));
        String hash =  Util.GetLanguageHash(button.getAttribute(BookingConstants.LANG_ATTRIB_DATA_NAME));
        assertEquals(hash, BookingConstants.LANG_ATTRIB_DATA_US);
    }

    @Step("Choose USD currency")
    public void chooseUSDCurrency() {
        WebElement buttonCurrency = Driver.driver.findElement(By.cssSelector(BookingConstants.CURRENCY_BUTTON_CSS));
        buttonCurrency.click();
        WebElement buttonUSD = Driver.driver.findElement(By.className(BookingConstants.CURRENCY_USD_CLASS));
        buttonUSD.click();
    }

    @Step("The currency title should show <US$> as result")
    public void verifyUSDCurrency(String resultString) {
        WebElement result = Driver.driver.findElement(By.cssSelector(BookingConstants.CURRENCY_BUTTON_CSS));
        assertEquals(resultString, result.getText());
    }

    @Step("Set destination to <New York City>")
    public void setDestination(String destination) throws InterruptedException {
        WebElement edit = Driver.driver.findElement(By.id(BookingConstants.DESTINATION_ID));
        edit.sendKeys(destination);
        Thread.sleep(MINWAIT);
        WebElement autocompleteFirst = Driver.driver.findElement(By.className(BookingConstants.DESTINATION_AUTOCOMPLETE_CLASS));
        autocompleteFirst.click();
    }

    @Step ("Set Check-In date to < 3> of <September 2016>")
    public void setCheckInDate(String dayText, String monthText) throws InterruptedException {
        List<WebElement> pickers = Driver.driver.findElements(By.className(BookingConstants.CALENDARS_CLASS));
        WebElement picker = pickers.get(0);
        picker.click();
        WebElement nextMonthButton = Driver.driver.findElement(By.className(BookingConstants.CALENDARS_FURTHER_CLASS));
        // our month will be in left side of calendars
        for (int i = 0; i < 8 - Calendar.getInstance().get(Calendar.MONTH); i++) {
            nextMonthButton.click();
        }
        List<WebElement> months = Driver.driver.findElements(By.className(BookingConstants.CALENDARS_MONTH_CLASS));
        WebElement month = Util.GetFirstWebElementByText(months, monthText);
        if (month == null) {
            throw new InterruptedException(MISSMATCH_DATE_EXEPTION);
        }
        String xPath = String.format(BookingConstants.GET_DAYS_XPATH, monthText);
        List<WebElement> days = Driver.driver.findElements(By.xpath(xPath));
        WebElement day = Util.GetFirstWebElementByText(days, dayText);
        day.click();
        Thread.sleep(MINWAIT); //wait for setup right part of calendars
    }

    @Step ("Set Check-Out date to < 7> of <September 2016>")
    public void setCheckOutDate(String dayText, String monthText) throws InterruptedException {
        List<WebElement> pickers = Driver.driver.findElements(By.className(BookingConstants.CALENDARS_CLASS));
        WebElement picker = pickers.get(1);
        picker.click();
        List<WebElement> months = Driver.driver.findElements(By.className(BookingConstants.CALENDARS_MONTH_CLASS));
        WebElement month = Util.GetFirstWebElementByText(months, monthText);
        if (month == null) {
            throw new InterruptedException(MISSMATCH_DATE_EXEPTION);
        }
        String xPath = String.format(BookingConstants.GET_DAYS_XPATH, monthText);
        List<WebElement> days = Driver.driver.findElements(By.xpath(xPath));
        WebElement day = Util.GetFirstWebElementByText(days, dayText);
        day.click();
    }

    @Step("Choose Traveling for Work")
    public void chooseTravelWork() {
        WebElement radioWork = Driver.driver.findElement(By.className("b-booker-type__input_business-booker"));
        radioWork.click();
    }

    @Step("Set Rooms to <1>")
    public void chooseRooms(String rooms) {
        Select comboRooms = new Select(Driver.driver.findElement(By.name (BookingConstants.ROOMS_NAME)));
        comboRooms.selectByValue(rooms);
    }

    @Step("Set Adults to <1>")
    public void chooseAdults(String adults) {
        Select comboAdults = new Select(Driver.driver.findElement(By.name (BookingConstants.ADULTS_NAME)));
        comboAdults.selectByValue(adults);
    }

    @Step("Do search")
    public void doSearch() throws InterruptedException {
        WebElement button = Driver.driver.findElement(By.xpath(BookingConstants.SEARCH_BUTTON_XPATH));
        button.click();
        Thread.sleep(MINWAIT);
    }

    @Step("Check search for <New York City>")
    public void checkSearch(String address) throws InterruptedException {
        List<WebElement> links = Driver.driver.findElements(By.className(BookingConstants.SEARCH_RESULT_LINK_CLASS));
        if (!Util.ContainsWebElementsText(links, address)) {
            throw new InterruptedException(WRONG_SEARCH_RESULT_EXEPTION);
        } else {
            System.out.println("Search results Ok!");
        }
    }
}

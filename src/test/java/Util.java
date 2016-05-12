/**
 * Created by Max Maku on 11.05.2016.
 */
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.openqa.selenium.WebElement;

import java.util.Collection;
import java.util.List;
import java.util.regex.*;

public class Util {
    public static String GetLanguageHash (String data_title) {
        Pattern p = Pattern.compile("(?<=\\bdata-hash=\")[^\"]*");
        Matcher m = p.matcher(data_title);
        if (m.find()) {
            return  m.group(0);
        } else {
            return "";
        }
    }

    public static Collection<WebElement>  GetVisibleElements (List<WebElement> elements) {
        Predicate<WebElement> predicate = new Predicate<WebElement>() {
            public boolean apply(WebElement element) {
                return element.isDisplayed();
            }
        };
        return Collections2.filter(elements, predicate);
    }

    public static Boolean ContainsWebElementsText (List<WebElement> elements, final String elementText) {
        Predicate<WebElement> predicate = new Predicate<WebElement>() {
            public boolean apply(WebElement element) {
                return element.getText().contains(elementText);
            }
        };
        Collection<WebElement> filtered = Collections2.filter(elements, predicate);
        Integer filteredCount = filtered.size();
        return filteredCount == elements.size();
    }

    public static WebElement GetFirstWebElementByText (List<WebElement> elements, final String elementText) {
        Predicate<WebElement> predicate = new Predicate<WebElement>() {
            public boolean apply(WebElement element) {
                return element.getText().equals(elementText);
            }
        };
        Collection<WebElement> filtered = Collections2.filter(elements, predicate);
        if (!filtered.isEmpty()) {
            return filtered.iterator().next();
        }
        return null;
    }

}

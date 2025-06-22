package report.markup;

import java.util.List;

public class MarkupHelperPlus {
    public static String test(List<String> expectedString, List<String> actualString){
        CustomMarkup customMarkup = new CustomMarkup(expectedString,actualString);
        return customMarkup.getMarkup();
    }
}

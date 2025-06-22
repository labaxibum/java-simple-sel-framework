package report.markup;

import com.aventstack.extentreports.markuputils.Markup;

import java.util.List;

public class CustomMarkup implements Markup {
    final List<String> expectedString;
    final List<String> actualString;
    StringBuilder sb = new StringBuilder();

    public CustomMarkup(List<String> expectedString, List<String> actualString) {
        this.expectedString = expectedString;
        this.actualString = actualString;
    }

    public String getMarkup() {
        sb.append("<table style=\"border: 3px solid black;\">");
        sb.append("<tr>");
        sb.append("<td style=\"border: 3px solid black;\">Redundant From Expected</td>");
        sb.append("<td style=\"border: 3px solid black;\">Redundant From Actual</td>");
        getTDTable(expectedString, actualString);
        sb.append("</table>");
        return sb.toString();
    }

    private void getTDTable(List<String> expectedString, List<String> actualString) {
        int i = 0;
        int length = Math.max(expectedString.size(), actualString.size());
        while (i < length) {
            String exp;
            if(i < expectedString.size()){
                exp = expectedString.get(i);
            }
            else {
                exp = "";
            }

            String act;
            if (i < actualString.size()) {
                act = actualString.get(i);
            }
            else {
                act ="";
            }
//            String exp = (i < expectedString.size()) ? expectedString.get(i) : "";
//            String act = (i < actualString.size()) ? actualString.get(i) : "";
            sb.append("<tr><td style=\"border: 3px solid red;\">" + exp + "</td><td style=\"border: 3px solid green;\">" + act + "</td></tr>");
            i++;

        }
    }
}

package utilities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class StringFormatter {

    public static String bigNumber(long number) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(number);
    }

    public static String firstLeftSecondRightAlign(int totalLength, String first, String second, boolean sideSpace) {
        if (first.length() + second.length() >= totalLength)
            return first + second;

        String format = "%" + (totalLength - first.length()) + "s";
        String result = String.format(first + format, second);
        if (sideSpace)
            return " " + result + " ";
        else
            return result;
    }
}

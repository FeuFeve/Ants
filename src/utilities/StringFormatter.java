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

    public static String smallNumber(double number, int significantDigits) {
        if (number > 10)
            return String.valueOf(Math.round(number));
        if (number > 1)
            return String.valueOf(Math.round(number * (significantDigits - 1)) * (significantDigits - 1));

        StringBuilder toReturn = new StringBuilder("0.");
        int foundDigits = 0;
        while (foundDigits < significantDigits) {
            number *= 10;
            if (number > 1) {
                foundDigits++;
                if (foundDigits == significantDigits)
                    toReturn.append(Math.round(number));
                else {
                    int digit = (int) number;
                    toReturn.append(digit);
                    number -= digit;
                }
            }
            else {
                toReturn.append("0");
            }
        }
        return String.valueOf(toReturn);
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

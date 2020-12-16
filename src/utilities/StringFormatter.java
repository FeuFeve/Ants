package utilities;

public class StringFormatter {

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

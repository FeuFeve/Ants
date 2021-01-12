package utilities;

public class NumberFormatter {

    /*
     * Exemples:
     * Input: "100"     | Output: 100
     * Input: "100m"    | Output: 100_000_000
     * Input: ""        | Output: 0
     * Input: "100mm"   | Output: 0
     */
    public static long getQuantityInLong(String number) {
        long quantity;
        if (number.isEmpty())
            return 0;

        try {
            quantity = Long.parseLong(number);
            // THE NUMBER IS WELL FORMATTED
            return quantity;
        }
        catch (NumberFormatException e1) {
            // THE NUMBER MIGHT HAVE A LETTER AT THE END
            String multiplier = number.substring(number.length() - 1);
            number = number.substring(0, number.length() - 1);
            try {
                quantity = Long.parseLong(number);
                // THE NUMBER HAVE A LETTER AT THE END
                switch (multiplier) {
                    case "k":
                    case "K":
                        return quantity * 1000;
                    case "m":
                    case "M":
                        return quantity * 1_000_000;
                    case "g":
                    case "G":
                        return quantity * 1_000_000_000;
                    default:
                        return quantity;
                }
            } catch (NumberFormatException e2) {
                // THE NUMBER IS INCORRECTLY FORMATTED
                return 0;
            }
        }
    }

    public static String getQuantityInString(long number) {
        long g = number / 1_000_000_000;
        long m = (number % 1_000_000_000) / 1_000_000;
        if (g > 0)
            return g + "." + m + "G";
        long k = (number % 1_000_000) / 1000;
        if  (m > 0)
            return m + "." + k + "M";
        return StringFormatter.bigNumber(number);
    }
}

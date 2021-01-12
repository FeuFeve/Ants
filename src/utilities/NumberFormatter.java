package utilities;

public class NumberFormatter {

    public static long getQuantity(String number) {
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
}

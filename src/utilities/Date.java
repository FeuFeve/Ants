package utilities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

public class Date {

    static int year, month, day, hour, minute, second, millis;


    private static void update() {
        LocalDateTime now = LocalDateTime.now();
        year = now.getYear();
        month = now.getMonthValue();
        day = now.getDayOfMonth();
        hour = now.getHour();
        minute = now.getMinute();
        second = now.getSecond();
        millis = now.get(ChronoField.MILLI_OF_SECOND);
    }

    public static String getRealDate() {
        update();
        return String.format("%d-%02d-%02d", year, month, day);
    }

    public static String getRealDateAndTime() {
        update();
        return String.format("%d-%02d-%02d %02d:%02d:%02d", year, month, day, hour, minute, second);
    }

    public static String getRealDateAndTimeMs() {
        update();
        return String.format("%d-%02d-%02d %02d:%02d:%02d.%03d", year, month, day, hour, minute, second, millis);
    }

    public static String getRealTime() {
        update();
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public static String getRealTimeMs() {
        update();
        return String.format("%02d:%02d:%02d.%03d", hour, minute, second, millis);
    }

    public static LocalDateTime getCurrentDatePlus(long secondsToAdd) {
        return LocalDateTime.now().plusSeconds(secondsToAdd);
    }

    public static LocalDateTime getDatePlus(LocalDateTime ldt, long secondsToAdd) {
        return ldt.plusSeconds(secondsToAdd);
    }

    public static LocalDateTime getDateWithMultiplier(LocalDateTime beginLdt, LocalDateTime endLdt, double multiplier) {
        long beginTimeInSeconds = beginLdt.toEpochSecond(ZoneOffset.UTC);
        long endTimeInSeconds = endLdt.toEpochSecond(ZoneOffset.UTC);

        long timeDelta = endTimeInSeconds - beginTimeInSeconds;
        long newTimeDelta = Math.round(timeDelta * multiplier);

        long newEndTimeInSeconds = beginTimeInSeconds + newTimeDelta;

        return LocalDateTime.ofEpochSecond(newEndTimeInSeconds, 0, ZoneOffset.UTC);
    }
}

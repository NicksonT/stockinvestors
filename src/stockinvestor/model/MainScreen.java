package stockinvestor.model;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


public class MainScreen {


    public static String getTime(String TimeZone) {
        DateTimeZone zone = DateTimeZone.forID(TimeZone);
        DateTime dt = new DateTime(zone);
        int hour = dt.getHourOfDay();
        int min = dt.getMinuteOfHour();
        int sec = dt.getSecondOfMinute();
        String CurrentTime = String.format("%02d:%02d:%02d ", hour, min, sec);
        return CurrentTime;

    }



}

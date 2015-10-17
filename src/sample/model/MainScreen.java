package sample.model;

import java.util.Date;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


public class MainScreen {

    private static User mainUser;

    public static String getTime(String TimeZone) {
        DateTimeZone zone = DateTimeZone.forID(TimeZone);
        DateTime dt = new DateTime(zone);
        ;
        int hour = dt.getHourOfDay();
        int min = dt.getMinuteOfHour();
        int sec = dt.getSecondOfMinute();
        String CurrentTime = String.format("%02d:%02d:%02d ", hour, min, sec);
        return CurrentTime;

    }

    public static void createUser(String username) {
        User newUser = new User(username);
        mainUser = newUser;
    }

    public static User getUser() {
        return mainUser;
    }

}

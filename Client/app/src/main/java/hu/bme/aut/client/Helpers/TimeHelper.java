package hu.bme.aut.physicexperiment.Helpers;

import hu.bme.aut.physicexperiment.Model.Time;

public class TimeHelper {
    final static String regex = ":";


    public static Time GetTimeFromString(String time) {
        String[] timeParts = time.split(regex);
        return new Time(Integer.parseInt(timeParts[0]), Integer.parseInt(timeParts[1]), Integer.parseInt(timeParts[2]));
    }

    public static long GetMilllisecondFromTime(Time time) {
        return time.getSec() * 1000
                + time.getMin() * 1000 * 60
                + time.getHour() * 1000 * 60 * 60;
    }
}

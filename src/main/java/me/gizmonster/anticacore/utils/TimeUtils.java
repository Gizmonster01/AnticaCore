package me.gizmonster.anticacore.utils;

import me.gizmonster.anticacore.AnticaCorePlugin;
import me.gizmonster.anticacore.enums.WeekDay;
import org.bukkit.Bukkit;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


public class TimeUtils {

    private AnticaCorePlugin plugin;
    public Calendar calendar;

    public TimeUtils(AnticaCorePlugin plugin) {
        this.plugin = plugin;
        calendar = Calendar.getInstance(TimeZone.getTimeZone("EST"), Locale.US);
        checkWeekday();
    }

    public WeekDay today;

    public void checkWeekday() {
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch(day - 1) {
            case 0:
                today = WeekDay.SUNDAY;
                break;
            case 1:
                today = WeekDay.MONDAY;
                break;
            case 2:
                today = WeekDay.TUESDAY;
                break;
            case 3:
                today = WeekDay.WEDNESDAY;
                break;
            case 4:
                today = WeekDay.THURSDAY;
                break;
            case 5:
                today = WeekDay.FRIDAY;
                break;
            case 6:
                today = WeekDay.SATURDAY;
                break;
        }
    }
}

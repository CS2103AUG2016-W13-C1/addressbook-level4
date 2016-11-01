//@@author A0093896H
package seedu.todo.commons.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

import com.joestelmach.natty.*;

import seedu.todo.model.task.TaskDate;

/**
 * Helper functions for anything with regards to date and time.
 */
public class DateTimeUtil {


    public static final int DEFAULT_ON_HOUR = 0;
    public static final int DEFAULT_ON_MINUTE = 0;        
    public static final int DEFAULT_BY_HOUR = 23;
    public static final int DEFAULT_BY_MINUTE = 59;
    
    public static boolean isEmptyDateTimeString(String dateTimeString) {
        return (dateTimeString == null || dateTimeString.equals("") || dateTimeString.equals(" "));
    }

    /**
     * Attempts to parse a String into LocalDateTime.
     * 
     * If the String does not contains date information, the default date used is the current date.
     * 
     * If the String does not contains time information, the default time used is the 0000hrs if
     * it is an onDate and 2359 if it is a byDate.
     * 
     * @param dateTimeString
     * @param onOrBy
     * @return LocalDateTime
     */
    public static LocalDateTime parseDateTimeString(String dateTimeString, String onOrBy) {
        Parser nattyParser = new Parser();
        List<DateGroup> groups = nattyParser.parse(dateTimeString);

        if (groups.size() == 0) {
            return null;
        } else {
            Map<String, List<ParseLocation>> m = groups.get(0).getParseLocations();
            Date date = groups.get(0).getDates().get(0);
            
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            
            LocalDateTime ldt;
            if (!m.keySet().contains("date")) {
                LocalDateTime now = LocalDateTime.now();
                ldt = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 
                        c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
            } else {
                if (!m.keySet().contains("explicit_time")) {
                    if (onOrBy.equals(TaskDate.TASK_DATE_BY)) {
                        ldt = LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, 
                                c.get(Calendar.DATE), DEFAULT_BY_HOUR, DEFAULT_BY_MINUTE);
                    } else {
                        ldt = LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, 
                                c.get(Calendar.DATE), DEFAULT_ON_HOUR, DEFAULT_ON_MINUTE);
                    }
                } else {
                    ldt = LocalDateTime.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, 
                            c.get(Calendar.DATE), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
                }
                
            }
            
            return ldt;
        }
    }

    /**
     * Checks whether date information is able to be parsed out 
     * from a String
     * 
     * @param dateTimeString
     * @return boolean
     */
    public static boolean containsDateField(String dateTimeString) {
        Parser nattyParser = new Parser();
        List<DateGroup> groups = nattyParser.parse(dateTimeString);
        
        if (groups.size() == 0) {
            return false;
        } else {
            DateGroup group = groups.get(0);
            Map<String, List<ParseLocation>> m = group.getParseLocations();
            return m.keySet().contains("date");
        }
    }
    
    /**
     * Checks whether time information is able to be parsed out 
     * from a String
     * 
     * @param dateTimeString
     * @return boolean
     */
    public static boolean containsTimeField(String dateTimeString) {
        Parser nattyParser = new Parser();
        List<DateGroup> groups = nattyParser.parse(dateTimeString);
        
        if (groups.size() == 0) {
            return false;
        } else {
            DateGroup group = groups.get(0);
            Map<String, List<ParseLocation>> m = group.getParseLocations();
            return m.keySet().contains("explicit_time");
        }
    }
    
    /**
     * Checks whether onDate is before byDate
     * 
     * @param onDate
     * @param byDate
     * @return boolean
     */
    public static boolean beforeOther(TaskDate onDate, TaskDate byDate) {
        if (onDate.getDate() == null || byDate.getDate() == null) {
            return true;
        } else if (onDate.getDate().equals(byDate.getDate())) {
            return onDate.getTime().isBefore(byDate.getTime());
        } else {
            return onDate.getDate().isBefore(byDate.getDate());
        }
    }
    
    /**
     * Combines LocalDate and LocalTime to LocalDateTime with default time being 2359
     * @param date
     * @param time
     * @return
     */
    public static LocalDateTime combineLocalDateAndTime(LocalDate date, LocalTime time) {
        assert date != null;
        if (time == null) {
            return LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59);
        } else {
            return LocalDateTime.of(date, time);
        }
    }
    
    
    public static String prettyPrintDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    public static String prettyPrintTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

}

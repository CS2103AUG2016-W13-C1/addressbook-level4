package seedu.todo.model.task;


import java.time.LocalTime;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a Task's from and till date.
 * Guarantees: mutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class TaskDate {

    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Tasks' dates and time need to follow predefined format.";
    
    private LocalDate date;
    private LocalTime time;
    
    /**
     * Validates given date and time string.
     *
     * @throws IllegalValueException if given date and time string is invalid.
     */
    public TaskDate(String dateTimeString) throws IllegalValueException {
        
        if (DateTimeUtil.isEmptyDateTimeString(dateTimeString)) {
        	this.date = null;
        	this.time = null;
        	
        } else {
            
            LocalDateTime ldt = DateTimeUtil.parseDateTimeString(dateTimeString);
            
            if (ldt == null) {
                throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
            } else {
                this.date = ldt.toLocalDate();
                this.time = ldt.toLocalTime();
            }
          
        }
    }

    
    public LocalDate getDate() {
        return this.date;
    }
    
    public LocalTime getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        
    	String dateString, timeString;
    	if (date == null) {
    		dateString = "";
    	} else {
    		dateString = DateTimeUtil.prettyPrintDate(date);
    	}
    	
    	if (time == null) {
    		timeString = "";
    	} else {
    		timeString = DateTimeUtil.prettyPrintTime(time);
    	}
    	
    	return dateString + " " + timeString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDate // instanceof handles nulls
                || DateTimeUtil.combineLocalDateAndTime(this.date, this.time)
                    .equals(DateTimeUtil.combineLocalDateAndTime(((TaskDate) other).date, ((TaskDate) other).time)));
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

}
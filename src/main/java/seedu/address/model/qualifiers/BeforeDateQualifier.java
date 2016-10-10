package seedu.address.model.qualifiers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.address.commons.util.DateTimeUtil;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.TaskDate;

public class BeforeDateQualifier implements Qualifier{
    private LocalDateTime datetime;

    public BeforeDateQualifier(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        LocalDate onDate = task.getOnDate().getDate(); 
        LocalTime onTime = task.getOnDate().getTime();
        
        LocalDate byDate = task.getByDate().getDate(); 
        LocalTime byTime = task.getByDate().getTime();
        
        boolean onBefore = false;
        boolean byBefore = false;
        
        if (onDate != null) {
            LocalDateTime onDateTime = DateTimeUtil.combineLocalDateAndTime(onDate, onTime);
            onBefore = onDateTime.isBefore(datetime);
        }
        
        if (byDate != null) {
            LocalDateTime onDateTime = DateTimeUtil.combineLocalDateAndTime(byDate, byTime);
            byBefore = onDateTime.isBefore(datetime);
        }
        
        return onBefore || byBefore;
            
    }

    @Override
    public String toString() {
        return "datetime=" + datetime.toString();
    }
}

//@@author A0093896H
package seedu.todo.logic.commands;

import java.time.LocalDate;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.util.DateTimeUtil;
import seedu.todo.model.task.*;
import seedu.todo.model.task.Recurrence.Frequency;

/**
 * Adds a task into DoDo-Bird
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the to do list.\n"
            + "Parameters: TASKNAME on STARTDATE by ENDDATE priority PRIORITY every RECURRENCE; DETAILS...\n"
            + "Example: " + COMMAND_WORD
            + " get groceries on 10/10/2016 by 11/10/2016 ; bread, fruits, cinnamon powder, red pepper";

    public static final String MESSAGE_SUCCESS = "New task added!\n%1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the to do list";
    
    public static final String MESSAGE_INVALID_DATE_RANGE = "You entered a starting date later "
            + "than the ending date.\n"
            + "Please ensure that the starting date is earlier than the ending date.";
    
    private final Task toAdd;
    
    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String detail, String onDateString, 
                      String byDateString, String priority, String freq)
                      throws IllegalValueException {
        
        if (priority == null) {
            priority = Priority.LOW;
        }
        
        if (freq == null) {
            freq = Frequency.NONE.name();
        }
        
        this.toAdd = new Task(
                new Name(name),
                new Detail(detail),
                new TaskDate(onDateString, TaskDate.TASK_DATE_ON),
                constructByDate(onDateString, byDateString),
                new Priority(priority),
                new Recurrence(Frequency.valueOf(freq.toUpperCase().trim()))
        );
    }
    
    
    /**
     * Executes the add command. The new task is added to the top of the list.
     * 
     * Informs the user if duplicated task is added.
     * Refer to {@link seedu.todo.model.task.ReadOnlyTask#isSameStateAs(ReadOnlyTask)} 
     * for equivalence testing.
     */
    @Override
    public CommandResult execute() {
        assert model != null;
        try {
            model.addTask(toAdd);
            model.updateFilteredListToShowAllNotCompleted();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            return new CommandResult(MESSAGE_DUPLICATE_TASK);
        }

    }
    
    
    /**
     * Constructs the byDate based on the onDate.
     * Validates that the byDate cannot be earlier than on date.
     */
    private TaskDate constructByDate(String onDateString, String byDateString) throws IllegalValueException {
        TaskDate onDate = new TaskDate(onDateString, TaskDate.TASK_DATE_ON);
        TaskDate byDate = new TaskDate(byDateString, TaskDate.TASK_DATE_BY);
        
        if (byDate.getDate() != null && !DateTimeUtil.containsDateField(byDateString)) {
            byDate.setDate(LocalDate.of(onDate.getDate().getYear(), 
                    onDate.getDate().getMonth(), onDate.getDate().getDayOfMonth()));
        }
        
        if (!DateTimeUtil.beforeOther(onDate, byDate)) {
            throw new IllegalValueException(MESSAGE_INVALID_DATE_RANGE);
        }
        
        return byDate;
    }

}

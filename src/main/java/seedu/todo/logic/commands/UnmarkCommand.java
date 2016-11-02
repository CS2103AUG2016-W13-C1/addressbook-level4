//@@author A0093896H
package seedu.todo.logic.commands;

import seedu.todo.commons.core.Messages;
import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.model.task.Completion;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Unmark a task identified using it's last displayed index from the to do list.
 */
public class UnmarkCommand extends Command {
    
    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Unmark Task: %1$s";

    public final int targetIndex;

    public UnmarkCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the unmark Command
     * 
     * Will return a message to inform the user if an invalid target index is used 
     * or the task specified cannot be found,
     */
    @Override
    public CommandResult execute() {
        
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex - 1);
        
        try {
            Task toMark = model.getTask(taskToMark);
            
            toMark.setCompletion(new Completion(false));
            
            model.updateTask(taskToMark, toMark);
            model.updateFilteredListToShowAllNotCompleted();
            model.updateTodayListToShowAll();
            model.updateWeekListToShowAll();
            
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be found";
        } 
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, taskToMark.getName()));
    }
}

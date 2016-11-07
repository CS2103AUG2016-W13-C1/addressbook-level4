package seedu.todo.logic.commands;

import seedu.todo.commons.core.Messages;
import seedu.todo.commons.core.UnmodifiableObservableList;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the to do list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    
    //@@author A0093896H
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task at Index: %1$d\nName : %2$s";
    //@@author
    
    public final int targetIndex;

    public DeleteCommand(int targetIndex) {
        this.targetIndex = targetIndex;
    }
    
    //@@author A0093896H
    /**
     * Executes the delete command.
     * 
     * Will inform the user if an invalid index is used or the task cannot be found. 
     */
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToDelete = lastShownList.get(targetIndex - 1);

        try {
            model.deleteTask(taskToDelete);
            model.refreshCurrentFilteredTaskList();
        } catch (TaskNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_TASK_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, targetIndex, taskToDelete));
    }

}

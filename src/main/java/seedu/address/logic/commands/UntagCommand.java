package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.UniqueTaskList.TaskNotFoundException;

/**
 * Tags a task identified using it's last displayed index from the to do list.
 * with tags
 */
public class UntagCommand extends Command{

    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Untags the task identified by the index number used in the last task listing. "
            + "Tag names must be unique\n"
            + "Parameters: INDEX TAGNAME [MORE TAGNAMES]\n"
            + "Example: " + COMMAND_WORD + " 1 birthday clique";

    public static final String MESSAGE_TAG_TASK_SUCCESS = "Untagged Task: %1$s";

    public final int targetIndex;
    public final UniqueTagList tags;
    
    public UntagCommand(int targetIndex, String tagNames) throws Exception {

        this.targetIndex = targetIndex;
        
        if (tagNames.isEmpty()) {
            throw new Exception();
        }
        
        tags = new UniqueTagList();
        for (String tagName : tagNames.trim().split(" ")) {
            tags.add(new Tag(tagName));
        }
        
    }
    
    
    @Override
    public CommandResult execute() {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getUnmodifiableFilteredTaskList();

        if (lastShownList.size() < targetIndex) {
            indicateAttemptToExecuteIncorrectCommand();
            return new CommandResult(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToUntag = lastShownList.get(targetIndex - 1);

        try {
            Task toUntag = model.getTask(taskToUntag);
            
            for (Tag tag : tags) {
                try {
                    toUntag.removeTag(tag);
                } catch (UniqueTagList.TagNotFoundException e) {}
            }
            model.updateTaskTags(taskToUntag, toUntag);
            model.updateFilteredListToShowAll();
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be found";
        }

        return new CommandResult(String.format(MESSAGE_TAG_TASK_SUCCESS, taskToUntag.getName()));
    }
    
    
    
}

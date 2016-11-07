package seedu.todo.model;

import javafx.collections.ObservableList;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.tag.UniqueTagList;
import seedu.todo.model.tag.UniqueTagList.DuplicateTagException;
import seedu.todo.model.task.ReadOnlyTask;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.UniqueTaskList;
import seedu.todo.model.task.UniqueTaskList.DuplicateTaskException;
import seedu.todo.model.task.UniqueTaskList.TaskNotFoundException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class DoDoBird implements ReadOnlyToDoList {
    
    //@@author A0093896H
    private final Stack<UniqueTaskList> tasksHistory;
    private final Stack<UniqueTagList> tagsHistory;
    //@@author
    
    public DoDoBird() {
        this(new UniqueTaskList(), new UniqueTagList());
    }

    /**
     * Tasks and Tags are copied into this ToDoList
     */
    public DoDoBird(ReadOnlyToDoList toBeCopied) {
        this(toBeCopied.getUniqueTaskList(), toBeCopied.getUniqueTagList());
    }
    
    //@@author A0093896H
    /**
     * Tasks and Tags are copied into this ToDoList
     */
    public DoDoBird(UniqueTaskList tasks, UniqueTagList tags) {
        tasksHistory = new Stack<>();
        tagsHistory = new Stack<>();
        resetData(tasks.getInternalList(), tags.getInternalList());
    }
    //@@author
    
    public static ReadOnlyToDoList getEmptyToDoList() {
        return new DoDoBird();
    }

    /*****************************
     * LIST OVERWRITE OPERATIONS *
     *****************************/
   
    @Override
    public List<ReadOnlyTask> getTaskList() {
        return Collections.unmodifiableList(tasksHistory.peek().getInternalList());
    }
    
    //@@author A0093896H
    @Override
    public UniqueTaskList getUniqueTaskList() {
        return this.tasksHistory.peek();
    }

    //@@author A0142421X
    @Override
    public List<Tag> getTagList() {
        return Collections.unmodifiableList(tagsHistory.peek().getInternalList());
    }
    
    @Override
    public UniqueTagList getUniqueTagList() {
        return this.tagsHistory.peek();
    }
    
    //@@author A0093896H

    public ObservableList<Task> getTasks() {
        return tasksHistory.peek().getInternalList();
    }
    
    public ObservableList<Tag> getTags() {
        return tagsHistory.peek().getInternalList();
    }
    
    //@@author
    public void resetData(ReadOnlyToDoList newData) {
        resetData(newData.getTaskList(), newData.getTagList());
    }
    
    public void resetData(Collection<? extends ReadOnlyTask> newTasks, Collection<Tag> newTags) {
        setTasks(newTasks.stream().map(Task::new).collect(Collectors.toList()));
        setTags(newTags);
        
        this.updateTasksRecurrence();
    }
    
    //@@author A0093896H
    public void setTasks(List<Task> tasks) {
        if (this.tasksHistory.isEmpty()) {
            Collections.reverse(tasks);
            UniqueTaskList topList = this.copyTaskList(tasks);
            this.tasksHistory.push(topList);
        } else {
            this.updateTaskHistoryStack();
            this.getTasks().setAll(tasks);
        }
    }
    
    //@@author A0142421X
    public void setTags(Collection<Tag> tags) {
        if (this.tagsHistory.isEmpty()) {
            UniqueTagList topList = this.copyTagList(tags);
            this.tagsHistory.push(topList);
        } else {
            this.updateTagHistoryStack();
            this.getTags().setAll(tags);
        }
        updateTagTopList();
    }
    //@@author

    /*************************
     * TASK-LEVEL OPERATIONS *
     *************************/
    
    public int getTaskIndex(ReadOnlyTask target) {
        return this.getTasks().indexOf(target);
    }
    
    public Task getTask(int index) {
        assert index >= 0;    
        return this.getTasks().get(index);
    }
    //@@author A0093896H
    /**
     * Adds a task to the to do list.
     *
     * @throws UniqueTaskList.DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(Task p) throws DuplicateTaskException {
        updateTaskHistoryStack();
        updateTagHistoryStack();

        try {
            this.getUniqueTaskList().add(p);
        } catch (DuplicateTaskException e) {
            undo();
            throw e;
        }
        
    }
    
    /**
     * Deletes a task to the to do list.
     * Also checks the deleted task's tags and updates {@link #tags} to remove or decrease
     * the tag count.
     *
     * @throws UniqueTaskList.TaskNotFoundException if the task cannot be found
     */
    public void deleteTask(ReadOnlyTask key) throws TaskNotFoundException {
        updateTaskHistoryStack();
        updateTagHistoryStack();
        
        try {
            this.getUniqueTaskList().remove(key);
        } catch (TaskNotFoundException e) {
            undo();
            throw e;
        }
        updateTagTopList();

    }
    
    /**
     * Updates a task to the to do list.
     */
    public void updateTask(ReadOnlyTask oldTask, ReadOnlyTask newTask) {
        updateTaskHistoryStack();
        updateTagHistoryStack();
         
        int index = this.getTaskIndex(oldTask);
            
        this.getTasks().get(index).setName(newTask.getName());
        this.getTasks().get(index).setDetail(newTask.getDetail());
        this.getTasks().get(index).setOnDate(newTask.getOnDate());
        this.getTasks().get(index).setByDate(newTask.getByDate());
        this.getTasks().get(index).setCompletion(newTask.getCompletion());
        this.getTasks().get(index).setPriority(newTask.getPriority());
        this.getTasks().get(index).setRecurrence(newTask.getRecurrence());
        
    }
    
    //@@author A0142421X
    /**
     * Add tags to a task.
     * 
     * Will not throw exception even if there is a duplicate tag, 
     * will instead move on to add the other tags.
     * 
     * @throws UniqueTaskList.TaskNotFoundException if the task cannot be found 
     */
    public void addTaskTags(ReadOnlyTask oldTask, UniqueTagList newTagList) throws TaskNotFoundException {
        updateTaskHistoryStack();
        updateTagHistoryStack();
        
        int index = this.getTaskIndex(oldTask);
        Task toTag = this.getTasks().get(index); 
        for (Tag t : newTagList.getInternalList()) {
            try {
                if (this.getTags().contains(t)) {
                    Tag oldTag = this.getTags().get(this.getTags().indexOf(t));
                    toTag.addTag(oldTag);
                } else {
                    toTag.addTag(t);
                }    
            } catch (DuplicateTagException e) {
                //tag already added - do nothing
            }
        }
        updateTagTopList();
    }
    
    /**
     * Deletes tags from a task.
     * 
     * @throws UniqueTaskList.TaskNotFoundException if the task cannot be found
     */
    public void deleteTaskTags(ReadOnlyTask oldTask, UniqueTagList tagList) throws TaskNotFoundException {
        updateTaskHistoryStack();
        updateTagHistoryStack();
        
        int index = this.getTaskIndex(oldTask);
        Task toUntag = this.getTasks().get(index);
        
        for (Tag tag : tagList.getInternalList()) {
            try {
                toUntag.removeTag(tag);
            } catch (UniqueTagList.TagNotFoundException e) {
              //if not found just skip over - do nothing
            }
        }
        updateTagTopList();
    }

    //@@author A0093896H
    /**
     * Updates the dates of a task based on the recurrence frequency.
     */
    public void updateTasksRecurrence() {
        for (Task t : this.getTasks()) {
            if (t.isRecurring()) { 
                LocalDate onDate = t.getOnDate().getDate();
                LocalDate byDate = t.getByDate().getDate();
                if ((onDate != null && onDate.isBefore(LocalDate.now()))
                     || byDate != null && byDate.isBefore(LocalDate.now())) {
                    t.getRecurrence().updateTaskDate(t);
                }
            }
        }
    }
    //@@author A0121643R
    /**
     * Pop the top most UniqueTaskList and UniqueTagList
     * Does not pop if there is only one state in history 
     */
    public boolean undo() {
        if (this.tasksHistory.size() > 1 && this.tagsHistory.size() > 1) {
            UniqueTaskList topTaskList = this.tasksHistory.pop();
            UniqueTaskList oldTaskList = this.tasksHistory.pop();
            topTaskList.getInternalList().setAll(oldTaskList.getInternalList());
            this.tasksHistory.push(topTaskList);
            
            UniqueTagList topTagList = this.tagsHistory.pop();
            UniqueTagList oldTagList = this.tagsHistory.pop();
            topTagList.getInternalList().setAll(oldTagList.getInternalList());
            this.tagsHistory.push(topTagList);
            
            updateTagTopList();
            return true;
        }
        return false;
    }
    //@@author
    
    /*************************
     *  TAG-LEVEL OPERATIONS *
     *************************/
    //@@author A0142421X
    /**
     * Add tag to TagHistory's top UniqueTagList
     */
    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        updateTagHistoryStack();
        UniqueTagList topList = this.getUniqueTagList();
        topList.add(t);
    }
    
    /**
     * Updates the top list in tagHistory with the correct tags
     * and the number of tasks with that tag
     */
    private void updateTagTopList() {
        UniqueTagList topList = this.getUniqueTagList();
        topList.getInternalList().clear();
        
        for (Task task : this.getTasks()) {
            for (Tag tag : task.getTags().getInternalList()) {
                try {
                    topList.add(tag);
                    tag.setCount(0);
                } catch (DuplicateTagException e) {
                    //if duplicate is encountered, do not add
                }
            }
        }
        
        for (Task task : this.getTasks()) {
            for (Tag tag : task.getTags().getInternalList()) {
                if (topList.contains(tag)) {
                    Tag inList = topList.getInternalList().get(topList.getInternalList().indexOf(tag));
                    inList.increaseCount();
                    if (inList != tag) {
                        tag.increaseCount();
                    }
                }
            }
        }
        
    }
    //@@author
    
    /******************
     *  UTIL METHODS  *
     ******************/
    //@@author A0093896H
    @Override
    public String toString() {
        return tasksHistory.peek().getInternalList().size() + " tasks, " 
                + tagsHistory.peek().getInternalList().size() +  " tags";
    }
    
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoDoBird // instanceof handles nulls
                && this.tasksHistory.peek().equals(((DoDoBird) other).tasksHistory.peek())
                && this.tagsHistory.peek().equals(((DoDoBird) other).tagsHistory.peek()));
    }
    
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasksHistory.peek(), tagsHistory.peek());
    }
    
    /**
     * Updates tasks history
     * Maintains the reference of the top UniqueTaskList
     * Call this method for add, delete, update
     */
    private void updateTaskHistoryStack() {
        UniqueTaskList topList = this.tasksHistory.pop();
        UniqueTaskList oldList = this.copyTaskList(topList.getInternalList());
        this.tasksHistory.push(oldList);
        this.tasksHistory.push(topList);
    }
    
    /**
     * Updates tags history
     * Maintains the reference of the top UniqueTagList
     * Call this method for add, delete, update
     */
    private void updateTagHistoryStack() {
        UniqueTagList topList = this.tagsHistory.pop();
        UniqueTagList oldList = this.copyTagList(topList.getInternalList());
        this.tagsHistory.push(oldList);
        this.tagsHistory.push(topList);
        
    }
    
    private UniqueTaskList copyTaskList(List<Task> old) {
        UniqueTaskList newList = new UniqueTaskList();
                
        for (int i = old.size() - 1; i >= 0; i--) {
            try {
                newList.add(new Task(old.get(i)));
            } catch (UniqueTaskList.DuplicateTaskException e) {}
        }
        return newList;
    }
    
 
    private UniqueTagList copyTagList(Collection<Tag> old) {
        UniqueTagList newList = new UniqueTagList();
        
        for (Tag t : old) {
            try {
                newList.add(t);
            } catch (UniqueTagList.DuplicateTagException e) {}
        }
        return newList;
    }
    
}

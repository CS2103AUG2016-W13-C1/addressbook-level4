package seedu.todo.testutil;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.model.tag.Tag;
import seedu.todo.model.task.*;

/**
 *
 */
public class TaskBuilder {

    private TestTask task;

    public TaskBuilder() {
        this.task = new TestTask();
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }
    
    public TaskBuilder withCompletion(boolean completed) {
        this.task.setCompletion(new Completion(completed));
        return this;
    }
    
    public TaskBuilder withRecurrence(String desc) throws IllegalValueException{
        this.task.setRecurrence(new Recurrence(desc));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        for (String tag: tags) {
            task.getTags().add(new Tag(tag));
        }
        return this;
    }

    public TaskBuilder withDetail(String detail) throws IllegalValueException {
        this.task.setDetail(new Detail(detail));
        return this;
    }

    public TaskBuilder withOnDate(String dateString) throws IllegalValueException {
        this.task.setOnDate(new TaskDate(dateString, TaskDate.TASK_DATE_ON));
        return this;
    }

    public TaskBuilder withByDate(String dateString) throws IllegalValueException {
        this.task.setByDate(new TaskDate(dateString, TaskDate.TASK_DATE_BY));
        return this;
    }

    public TestTask build() {
        return this.task;
    }

}

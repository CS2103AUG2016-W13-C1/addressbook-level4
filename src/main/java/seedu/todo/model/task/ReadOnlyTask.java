package seedu.todo.model.task;

import seedu.todo.model.tag.UniqueTagList;

/**
 * A read-only immutable interface for a Task in the todo application.
 * Implementations should guarantee: |name is not null, field values are validated.
 */
public interface ReadOnlyTask {

    Name getName();
    Detail getDetail();
    TaskDate getOnDate();
    TaskDate getByDate();
    boolean isDone();

    /**
     * The returned TagList is a deep copy of the internal TagList,
     * changes on the returned list will not affect the person's internal tags.
     */
    UniqueTagList getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDetail().equals(this.getDetail())
                && other.getOnDate().equals(this.getOnDate())
                && other.getByDate().equals(this.getByDate()));
    }

    /**
     * Formats the task as text, showing all task information.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Details: ")
                .append(getDetail())
                .append(" From: ")
                .append(getOnDate())
                .append(" Till: ")
                .append(getByDate())
                .append(" Tags: ");
        //getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns a string representation of this Task's tags
     */
    default String tagsString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = ", ";
        getTags().forEach(tag -> buffer.append(tag).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }

}
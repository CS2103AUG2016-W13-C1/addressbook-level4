//@@author A0093896H
package seedu.todo.model.task;


/**
 * Represents a Task's details in the to do application. 
 * Guarantees: mutable; no constraints.
 */
public class Detail {
    
    public String value;

    public Detail(String detail) {
        if (detail == null) {
            detail = "";
        }
        detail = detail.trim();
        this.value = detail;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Detail // instanceof handles nulls
                        && this.value.equals(((Detail) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

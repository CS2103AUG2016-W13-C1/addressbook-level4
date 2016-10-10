package seedu.address.model.qualifiers;

import seedu.address.model.task.ReadOnlyTask;

public class CompletedQualifier implements Qualifier{
    boolean wantsDone;
    
    public CompletedQualifier(boolean wantsDone){
        this.wantsDone = wantsDone;
    }
    
    @Override
    public boolean run(ReadOnlyTask task) {
        return this.wantsDone ? task.isDone() : !task.isDone();
    }

    @Override
    public String toString() {
        return this.wantsDone ? "done" : "not done";
    }
}

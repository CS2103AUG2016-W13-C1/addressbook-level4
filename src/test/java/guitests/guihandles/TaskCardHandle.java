package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.model.task.ReadOnlyTask;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String DETAIL_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getDetail() {
        return getTextFromLabel(DETAIL_FIELD_ID);
    }

    public String getPhone() {
        return getTextFromLabel(PHONE_FIELD_ID);
    }

    public String getEmail() {
        return getTextFromLabel(EMAIL_FIELD_ID);
    }

    public boolean isSamePerson(ReadOnlyTask person){
        return getFullName().equals(person.getName().fullName) && getDetail().equals(person.getDetail().value); //TODO: compare the rest
                
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getFullName().equals(handle.getFullName()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getDetail();
    }
}
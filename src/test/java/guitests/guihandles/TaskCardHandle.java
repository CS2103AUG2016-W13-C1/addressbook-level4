package guitests.guihandles;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.stage.Stage;
import seedu.todo.model.task.ReadOnlyTask;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class TaskCardHandle extends GuiHandle {
    private static final String TASK_NAME_FIELD_ID = "#name";
    private static final String DETAIL_FIELD_ID = "#details";
    private static final String ON_DATE_FIELD_ID = "#onDate";
    private static final String BY_DATE_FIELD_ID = "#byDate";
    private static final String TAG_FIELD_ID = "#tag";

    private Node node;

    public TaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node){
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }
    
    protected String getTextFromText(String fieldId) {
        return getTextFromText(fieldId, node);
    }

    public String getTaskName() {
        return getTextFromText(TASK_NAME_FIELD_ID);
    }

    public String getDetail() {
        return getTextFromText(DETAIL_FIELD_ID);
    }

    public String getOnDate() {
        return getTextFromText(ON_DATE_FIELD_ID);
    }

    public String getByDate() {
        return getTextFromText(BY_DATE_FIELD_ID);
    }
    
    public String getTag() {
        return getTextFromText(TAG_FIELD_ID);
    }

    public boolean isSameTask(ReadOnlyTask task){
        //TODO: compare the rest
        return getTaskName().equals(task.getName().fullName) && getDetail().equals(task.getDetail().value); 
                
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TaskCardHandle) {
            TaskCardHandle handle = (TaskCardHandle) obj;
            return getTaskName().equals(handle.getTaskName()); //TODO: compare the rest
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTaskName() + " " + getDetail();
    }
}

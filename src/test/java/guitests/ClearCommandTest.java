package guitests;

import org.junit.Test;

import seedu.todo.testutil.TypicalTestTasks;

import static org.junit.Assert.assertTrue;

public class ClearCommandTest extends ToDoListGuiTest {
	//@@author A0138967J    
    @Test
    public void clearCommandTest() {

        //verify a non-empty list can be cleared
        commandBox.runCommand(TypicalTestTasks.buyMilk.getAddCommand());
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand(td.buyMilk.getAddCommand());
        assertTrue(taskListPanel.isListMatching(td.buyMilk));
        commandBox.runCommand("delete 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }
    
    //@@author
    private void assertClearCommandSuccess() {
        commandBox.runCommand("clear");
        assertListSize(0);
    }
    
}

//@@author A0121643R
package seedu.todo.model.qualifiers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.transformation.FilteredList;
import seedu.todo.logic.commands.SearchCommand.SearchCompletedOption;
import seedu.todo.model.DoDoBird;
import seedu.todo.model.expressions.PredicateExpression;
import seedu.todo.model.task.Priority;
import seedu.todo.model.task.Task;
import seedu.todo.testutil.TestDataHelper;

public class PriorityQualifierTest {
    
    private TestDataHelper helper;
    private DoDoBird ddb;
    
    @Before
    public void setup() {
        helper = new TestDataHelper();
        ddb = new DoDoBird();
    }
    
    @Test
    public void PriorityQualifer_test() throws Exception {
    	    	        
    	Task taskLow = helper.generateFullTask(2);
    	
    	Task taskMid = helper.generateFullTaskPriorityMid(0);
    	
    	Task taskHigh = helper.generateFullTaskPriorityHigh(1);
    	
    	Priority priorityLow = taskLow.getPriority();
    	
    	Priority priorityMid = taskMid.getPriority();
    	
    	Priority priorityHigh = taskHigh.getPriority();
    	
    	assertEquals(priorityLow, new Priority("low"));
    	assertNotEquals(priorityLow.toString(), "LOW");
    	
    	assertEquals(priorityMid, new Priority("mid"));
    	assertNotEquals(priorityMid.toString(), "mID");
    	
    	assertEquals(priorityHigh, new Priority("high"));
    	assertNotEquals(priorityHigh.toString(), "HIGH");
    	
    }

    @Test
    public void priorityQualifer_test() throws Exception {
        FilteredList<Task> filteredTasks = new FilteredList<>(ddb.getTasks());
        Task toAdd = helper.generateFullTask(1);
        ddb.addTask(toAdd);
        
        filteredTasks.setPredicate((new PredicateExpression(
                new PriorityQualifier(new Priority(Priority.LOW), SearchCompletedOption.ALL))::satisfies));
        assertEquals(filteredTasks.size(), 1);
        
        toAdd.setPriority(new Priority(Priority.HIGH));
        
        filteredTasks.setPredicate((new PredicateExpression(
                new PriorityQualifier(new Priority(Priority.LOW), SearchCompletedOption.ALL))::satisfies));
        assertEquals(filteredTasks.size(), 0);
        
        filteredTasks.setPredicate((new PredicateExpression(
                new PriorityQualifier(new Priority(Priority.HIGH), SearchCompletedOption.ALL))::satisfies));
        assertEquals(filteredTasks.size(), 1);
        
    }
    
    
    
    
    
}

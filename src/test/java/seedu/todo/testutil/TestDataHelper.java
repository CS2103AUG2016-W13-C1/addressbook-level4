package seedu.todo.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.todo.model.Model;
import seedu.todo.model.ToDoList;
import seedu.todo.model.task.Detail;
import seedu.todo.model.task.Name;
import seedu.todo.model.task.Task;
import seedu.todo.model.task.TaskDate;

/**
 * A utility class to generate test data.
 */
public class TestDataHelper{

    /**
     * Generates a valid task using the given seed.
     * Running this function with the same parameter values guarantees the returned task will have the same state.
     * Each unique seed will generate a unique Task object.
     *
     * @param seed used to generate the task data field values
     */
    public Task generateTask(int seed) throws Exception {
        return new Task(
                new Name("Task " + seed),
                new Detail("House of " + seed),
                new TaskDate("2/3/2017 12:34 pm"),
                new TaskDate("2/3/2017 12:34 pm")
        );
    }

    /** Generates the correct add command based on the task given */
    public String generateAddCommand(Task p) {
        StringBuffer cmd = new StringBuffer();

        cmd.append("add ");
        cmd.append(p.getName().toString());
        cmd.append(" on ").append(p.getOnDate().toString());
        cmd.append(" by ").append(p.getByDate().toString());
        cmd.append(" ;").append(p.getDetail());
        
        return cmd.toString();
    }

    /**
     * Generates an ToDoList with auto-generated tasks.
     */
    public ToDoList generateToDoList(int numGenerated) throws Exception{
        ToDoList toDoList = new ToDoList();
        addToToDoList(toDoList, numGenerated);
        return toDoList;
    }

    /**
     * Generates an ToDoList based on the list of Tasks given.
     */
    public ToDoList generateToDoList(List<Task> tasks) throws Exception{
        ToDoList toDoList = new ToDoList();
        addToToDoList(toDoList, tasks);
        return toDoList;
    }

    /**
     * Adds auto-generated Task objects to the given ToDoList
     * @param toDoList The ToDoList to which the Tasks will be added
     */
    public void addToToDoList(ToDoList toDoList, int numGenerated) throws Exception{
        addToToDoList(toDoList, generateTaskList(numGenerated));
    }

    /**
     * Adds the given list of Tasks to the given ToDoList
     */
    public void addToToDoList(ToDoList toDoList, List<Task> tasksToAdd) throws Exception{
        for (Task p: tasksToAdd){
            toDoList.addTask(p);
        }
    }

    /**
     * Adds auto-generated Task objects to the given model
     * @param model The model to which the Tasks will be added
     */
    public void addToModel(Model model, int numGenerated) throws Exception{
        addToModel(model, generateTaskList(numGenerated));
    }

    /**
     * Adds the given list of Tasks to the given model
     */
    public void addToModel(Model model, List<Task> tasksToAdd) throws Exception{
        for (Task p: tasksToAdd){
            model.addTask(p);
        }
    }

    /**
     * Generates a list of Tasks based on the flags.
     */
    public List<Task> generateTaskList(int numGenerated) throws Exception{
        List<Task> tasks = new ArrayList<>();
        for (int i = 1; i <= numGenerated; i++){
            tasks.add(generateTask(i));
        }
        return tasks;
    }

    public List<Task> generateTaskList(Task... tasks) {
        return Arrays.asList(tasks);
    }

    /**
     * Generates a Task object with given name. Other fields will have some dummy values.
     */
    public Task generateTaskWithName(String name) throws Exception {
        return new Task(
                new Name(name),
                new Detail("1"),
                new TaskDate("5/3/2017 12:44 pm"),
                new TaskDate("5/3/2017 12:44 pm")
        );
    }
}
//@@author A0093896H
package seedu.todo.logic.commands;

import java.io.IOException;

import seedu.todo.commons.core.EventsCenter;
import seedu.todo.commons.events.model.SaveLocationChangedEvent;
import seedu.todo.commons.util.FileUtil;


/**
 * Change data storage location
 */
public class StoreCommand extends Command {

    public static final String COMMAND_WORD = "store";

    public static final String MESSAGE_SUCCESS = "Change storage location !";
    
    public static final String MESSAGE_SAVE_ERROR = "Unable to save to new location.";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes the storage location to the location specified by the user. "
            + "Parameters: NEW_STORAGE_LOCATION\n"
            + "Example: " + COMMAND_WORD + "data/dodobird.xml";

    private final String location;
    
    public StoreCommand(String newLocation) {
        this.location = newLocation;
    }
    
    /**
     * Executes the store command.
     * 
     * Checks if the newly specified location is valid and save to the location if so.
     * If the specified location is not valid, reset the location to default location. 
     */
    @Override
    public CommandResult execute() {
        String defaultLocation = config.getToDoListFilePath();
        String tempLocation = this.location + "/" + config.getToDoListName() + ".xml";
        
        if (FileUtil.isFilenameValid(tempLocation)) {
            try {
                config.updateToDoListFilePath(tempLocation);
                storage.updateToDoListFilePath(tempLocation, model.getToDoList());
                return new CommandResult(MESSAGE_SUCCESS);
                
            } catch (IOException e){
                config.setToDoListFilePath(defaultLocation);
                storage.setToDoListFilePath(defaultLocation);
                return new CommandResult(MESSAGE_SAVE_ERROR);
            }
        } else {
            return new CommandResult(MESSAGE_SAVE_ERROR);
        }
          
    }
        
   
}

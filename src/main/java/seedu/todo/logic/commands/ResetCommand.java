//@@author A0093896H
package seedu.todo.logic.commands;

import java.io.IOException;
import java.util.Optional;

import seedu.todo.commons.core.Config;
import seedu.todo.commons.exceptions.DataConversionException;
import seedu.todo.commons.util.ConfigUtil;

/**
 * Reset Config to default
 */
public class ResetCommand extends Command {

    public static final String COMMAND_WORD = "reset";

    public static final String MESSAGE_SUCCESS = "Reset User Config !";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reset the user config to default"
            + "Example: " + COMMAND_WORD;
    
    public static final String MESSAGE_SAVE_ERROR = "Unable to save config to new location.";
    
    public ResetCommand() {}
    
    
    /**
     * Executes the Reset Command.
     * 
     * Informs the user if unable to save config at default location.
     */
    @Override
    public CommandResult execute() {
        
        String configFilePathUsed = Config.DEFAULT_CONFIG_FILE;
        
        Config initializedConfig;
        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            initializedConfig = new Config();
        } 
        
        try {
            ConfigUtil.saveConfig(initializedConfig, Config.USER_CONFIG_FILE);
            config.setAppTitle(initializedConfig.getAppTitle());
            config.setLogLevel(initializedConfig.getLogLevel());
            config.setToDoListFilePath(initializedConfig.getToDoListFilePath());
            config.setToDoListName(initializedConfig.getToDoListName());
            config.setUserPrefsFilePath(initializedConfig.getUserPrefsFilePath());

            storage.setToDoListFilePath(config.getToDoListFilePath());
            storage.saveToDoList(model.getToDoList());
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (IOException e) {
            return new CommandResult(MESSAGE_SAVE_ERROR);
        }
          
    }
        
   
}

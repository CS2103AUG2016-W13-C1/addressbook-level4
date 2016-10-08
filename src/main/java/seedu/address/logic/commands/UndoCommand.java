package seedu.address.logic.commands;

public class UndoCommand extends Command {

	public static final String COMMAND_WORD = "undo";

	public static final String MESSAGE_SUCCESS = "Undo the previous command";
	

	public UndoCommand() {}

	@Override
	public CommandResult execute() {
		
		return new CommandResult(MESSAGE_SUCCESS);
	}
}

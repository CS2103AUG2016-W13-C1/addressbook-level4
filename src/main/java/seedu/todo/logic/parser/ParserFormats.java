package seedu.todo.logic.parser;

import java.util.regex.Pattern;


/**
 * Contains the various regex Patterns that ToDoListParser will use 
 * for the different commands.
 */
public class ParserFormats {
    
    /**
     * Used for initial separation of command word and args.
     */
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    
    //one or more keywords separated by whitespace
    public static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); 
    
    public static final Pattern ADD_TASK_ARGS_FORMAT_FT = Pattern.compile(
            "(?<name>[^/]+)" + " (on|from) (?<onDateTime>.+)" + " (by|to) (?<byDateTime>[^;]+)" + "(?: ?; ?(?<detail>.+))?", Pattern.CASE_INSENSITIVE);

    public static final Pattern ADD_TASK_ARGS_FORMAT_ON = Pattern
            .compile("(?<name>[^/]+) (on|from) (?<onDateTime>[^;]+)(?: ?; ?(?<detail>.+))?", Pattern.CASE_INSENSITIVE);

    public static final Pattern ADD_TASK_ARGS_FORMAT_BY = Pattern
            .compile("(?<name>[^/]+) (by|to) (?<byDateTime>[^;]+)(?: ?; ?(?<detail>.+))?", Pattern.CASE_INSENSITIVE);

    public static final Pattern ADD_TASK_ARGS_FORMAT_FLOAT = Pattern
            .compile("(?<name>[a-zA-Z_0-9 ]+)(?: ?; ?(?<detail>.+))?", Pattern.CASE_INSENSITIVE);
        
    public static final Pattern SEARCH_TASK_ARGS_FORMAT_ON = Pattern
            .compile("on (?<onDateTime>.+)", Pattern.CASE_INSENSITIVE);
    
    public static final Pattern SEARCH_TASK_ARGS_FORMAT_BEFORE = Pattern
            .compile("before (?<beforeDateTime>.+)", Pattern.CASE_INSENSITIVE);
    
    public static final Pattern SEARCH_TASK_ARGS_FORMAT_AFTER = Pattern
            .compile("after (?<afterDateTime>.+)", Pattern.CASE_INSENSITIVE);
    
    public static final Pattern SEARCH_TASK_ARGS_FORMAT_FT = Pattern
            .compile("from (?<fromDateTime>.+) to (?<tillDateTime>.+)", Pattern.CASE_INSENSITIVE);
    
    public static final Pattern UPDATE_TASK_ARGS_FORMAT = Pattern
            .compile("(?<name>[^/]*?)? ?((^| )((on|from) "
                    + "(?<onDateTime>[^(by|;)]+)?|by (?<byDateTime>[^;]+)))*?"
                    + "(?: ?;(?<detail>.+))?$", Pattern.CASE_INSENSITIVE);
    
}

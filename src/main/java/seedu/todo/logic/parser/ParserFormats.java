package seedu.todo.logic.parser;

import java.util.regex.Pattern;

/**
 * Contains the various regex Patterns that ToDoListParser will use 
 * for the different commands.
 */
public class ParserFormats {
    
    //@@author A0121643R-unused
    public static final int FIRST_INDEX = 0;
    public static final String PRIORITY_FORMAT = " priority (?<priority>[^;]+)";
    public static final String ON_DATE_FORMAT = " (on|from) (?<onDateTime>[^;]+)";
    public static final String BY_DATE_FORMAT = " (by|to) (?<byDateTime>[^;]+)";
    public static final String DETAIL_FORMAT = "(?: ?; ?(?<detail>.+))?";
    public static final String NAME_FORMAT = "(?<name>[^/;]+)";
    public static final String RECUR_FORMAT = " every (?<rec>[^;]+)";
            
    /**
     * Used for initial separation of command word and args.
     */
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)?");

    public static final Pattern TASK_INDEX_ARGS_FORMAT = Pattern.compile("(?<targetIndex>.+)");
    
    //one or more keywords separated by whitespace
    public static final Pattern KEYWORDS_ARGS_FORMAT = Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); 
    
    
    //@@author A0093896H 
    public static final Pattern ADD_TASK_ARGS_FORMAT = Pattern
            .compile("(?<name>[^/]+?) "
                      + "?((^| )(on (?<onDateTime>[^;]+?)?"
                      + "|by (?<byDateTime>[^;]+?)"
                      + "|priority (?<priority>[^;]+?)"
                      + "|every (?<rec>[^;]+?)))*?"
                      + "(?: ?;(?<detail>.+))?$", Pattern.CASE_INSENSITIVE);
    
    public static final Pattern UPDATE_TASK_ARGS_FORMAT = Pattern
            .compile("(?<name>[^/]*?)? "
                      + "?((^| )(on (?<onDateTime>[^;]+?)?"
                      + "|by (?<byDateTime>[^;]+?)"
                      + "|priority (?<priority>[^;]+?)"
                      + "|every (?<rec>[^;]+?)))*?"
                      + "(?: ?;(?<detail>.+))?$", Pattern.CASE_INSENSITIVE);
    
    
    //@@author A0121643R-unused
    public static final Pattern ADD_PRIORITY_FT = Pattern
            .compile(NAME_FORMAT + ON_DATE_FORMAT + BY_DATE_FORMAT 
                    + PRIORITY_FORMAT + DETAIL_FORMAT, Pattern.CASE_INSENSITIVE);

    public static final Pattern ADD_PRIORITY_ON = Pattern
            .compile(NAME_FORMAT + ON_DATE_FORMAT + PRIORITY_FORMAT 
                    + DETAIL_FORMAT, Pattern.CASE_INSENSITIVE);

    public static final Pattern ADD_PRIORITY_BY = Pattern
            .compile(NAME_FORMAT + BY_DATE_FORMAT + PRIORITY_FORMAT 
                    + DETAIL_FORMAT, Pattern.CASE_INSENSITIVE);
    
    public static final Pattern ADD_PRIORITY_FL = Pattern
            .compile("(?<name>[a-zA-Z_0-9 ]+)" + PRIORITY_FORMAT 
                    + "(?: ?; ?(?<detail>.+))?", Pattern.CASE_INSENSITIVE);
    
    //@@author A0093896H 
    public static final Pattern SEARCH_TASK_ARGS_FORMAT_ON = Pattern
            .compile("on (?<onDateTime>.+?)(?<comOpt> done|all|undone)?", Pattern.CASE_INSENSITIVE);
    
    public static final Pattern SEARCH_TASK_ARGS_FORMAT_BEFORE = Pattern
            .compile("before (?<beforeDateTime>.+?)(?<comOpt> done|all|undone)?", Pattern.CASE_INSENSITIVE);
    
    public static final Pattern SEARCH_TASK_ARGS_FORMAT_AFTER = Pattern
            .compile("after (?<afterDateTime>.+?)(?<comOpt> done|all|undone)?", Pattern.CASE_INSENSITIVE);
    
    public static final Pattern SEARCH_TASK_ARGS_FORMAT_FT = Pattern
            .compile("from (?<fromDateTime>.+?) to (?<tillDateTime>.+?)(?<comOpt> done|all|undone)?", 
                    Pattern.CASE_INSENSITIVE);
    
    public static final Pattern SEARCH_TASK_ARGS_FORMAT_TAG = Pattern
            .compile("tag (?<tags>.+?)(?<comOpt> done|all|undone)?", Pattern.CASE_INSENSITIVE);
    
    public static final Pattern SEARCH_KEYWORDS_ARGS_FORMAT = Pattern
            .compile("(?<keywords>.+?)(?<comOpt> done|all|undone)?"); 
    
    public static final Pattern SEARCH_TASK_ARGS_FORMAT_FLOATING = Pattern
            .compile("floating(?<comOpt> done| all| git sundone)?", Pattern.CASE_INSENSITIVE); 
    
    //@@author A0121643R   
    public static final Pattern SEARCH_PRIORITY = Pattern
            .compile("priority (?<priority>.+?)(?<comOpt> done|all|undone)?", Pattern.CASE_INSENSITIVE);
    
    
    
}

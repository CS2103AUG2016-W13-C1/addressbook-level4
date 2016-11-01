package seedu.todo.logic.parser;

import static seedu.todo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todo.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.todo.commons.exceptions.IllegalValueException;
import seedu.todo.commons.util.StringUtil;
import seedu.todo.logic.commands.*;
import seedu.todo.logic.commands.SearchCommand.SearchCompletedOption;
import seedu.todo.model.task.Priority;
import seedu.todo.model.task.Recurrence.Frequency;

import com.joestelmach.natty.*;

/**
 * Parses user input.
 */
public class ToDoListParser {
    
    //@@author A0093896H
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     */
    public Command parseCommand(String userInput) {
        final Matcher matcher = ParserFormats.BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return prepareAdd(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case DeleteCommand.COMMAND_WORD:
            return prepareDelete(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case MarkCommand.COMMAND_WORD:
            return prepareMark(arguments);

        case SearchCommand.COMMAND_WORD:
            return prepareSearch(arguments);

        case SeeCommand.COMMAND_WORD:
            return new SeeCommand();

        case TagCommand.COMMAND_WORD:
            return prepareTag(arguments);

        case UntagCommand.COMMAND_WORD:
            return prepareUntag(arguments);

        case UnmarkCommand.COMMAND_WORD:
            return prepareUnmark(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case UpdateCommand.COMMAND_WORD:
            return prepareUpdate(arguments);
            
        case StoreCommand.COMMAND_WORD:
            return prepareStore(arguments);
            
        case ResetCommand.COMMAND_WORD:
            return new ResetCommand();


        default:
            return new IncorrectCommand(MESSAGE_UNKNOWN_COMMAND);
        }
    }
    
    private String matchNameResult(Matcher matcher) {
        return matcher.group("name");
    }
    
    private String matchDetailResult(Matcher matcher) {
        return matcher.group("detail");
    }
    
    private String matchOnDateTimeResult(Matcher matcher) {
        return matcher.group("onDateTime");
    }
    
    private String matchByDateTimeResult(Matcher matcher) {
        return matcher.group("byDateTime");
    }
    
    private String matchPriorityResult(Matcher matcher) {
        return matcher.group("priority");
    }
    
    private String matchRecurrenceResult(Matcher matcher) {
        return matcher.group("rec");
    }
    
    private String matchTagsResult(Matcher matcher) {
        return matcher.group("tags");
    }
    
    

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    //@@author A0121643R 
    private Command prepareAdd(String args) {
        
        Pattern[] dataPatterns = {ParserFormats.ADD_PRIORITY_FT, ParserFormats.ADD_PRIORITY_ON, 
                ParserFormats.ADD_PRIORITY_BY, ParserFormats.ADD_PRIORITY_FL, 
                ParserFormats.ADD_TASK_ARGS_RECUR_FORMAT_FT, ParserFormats.ADD_TASK_ARGS_RECUR_FORMAT_BY, 
                ParserFormats.ADD_TASK_ARGS_RECUR_FORMAT_ON, ParserFormats.ADD_TASK_ARGS_FORMAT_FT, 
                ParserFormats.ADD_TASK_ARGS_FORMAT_BY, ParserFormats.ADD_TASK_ARGS_FORMAT_ON, 
                ParserFormats.ADD_TASK_ARGS_FORMAT_FLOAT};
       
        Matcher matcher;
        try {
            for (Pattern p : dataPatterns) {
                matcher = p.matcher(args.trim());
                if (matcher.matches()) {
                    if (p.equals(ParserFormats.ADD_TASK_ARGS_FORMAT_FT)) {
                        return new AddCommand(matchNameResult(matcher), 
                                matchDetailResult(matcher), matchOnDateTimeResult(matcher), 
                                matchByDateTimeResult(matcher), Priority.DEFAULT_PRIORITY, 
                                Frequency.NONE);
                        
                    } else if (p.equals(ParserFormats.ADD_TASK_ARGS_FORMAT_ON)) {
                        return new AddCommand(matchNameResult(matcher), 
                                matchDetailResult(matcher), matchOnDateTimeResult(matcher), 
                                null, Priority.DEFAULT_PRIORITY, 
                                Frequency.NONE);
                        
                    } else if (p.equals(ParserFormats.ADD_TASK_ARGS_FORMAT_BY)) {
                        return new AddCommand(matchNameResult(matcher), 
                                matchDetailResult(matcher), null,
                                matchByDateTimeResult(matcher), Priority.DEFAULT_PRIORITY, 
                                Frequency.NONE);
                        
                    } else if (p.equals(ParserFormats.ADD_TASK_ARGS_RECUR_FORMAT_FT)) {
                        return new AddCommand(matchNameResult(matcher), 
                                matchDetailResult(matcher), matchOnDateTimeResult(matcher), 
                                matchByDateTimeResult(matcher), Priority.DEFAULT_PRIORITY, 
                                Frequency.valueOf(matchRecurrenceResult(matcher).toUpperCase().trim()));
                        
                    } else if (p.equals(ParserFormats.ADD_TASK_ARGS_RECUR_FORMAT_BY)) {
                        return new AddCommand(matchNameResult(matcher), 
                                matchDetailResult(matcher), null, 
                                matchByDateTimeResult(matcher), Priority.DEFAULT_PRIORITY, 
                                Frequency.valueOf(matchRecurrenceResult(matcher).toUpperCase().trim()));
                        
                    } else if (p.equals(ParserFormats.ADD_TASK_ARGS_RECUR_FORMAT_ON)) {
                        return new AddCommand(matchNameResult(matcher), 
                                matchDetailResult(matcher), matchOnDateTimeResult(matcher), 
                                null, Priority.DEFAULT_PRIORITY, 
                                Frequency.valueOf(matchRecurrenceResult(matcher).toUpperCase().trim()));
                    
                    } else if (p.equals(ParserFormats.ADD_PRIORITY_FT)) {
                        return new AddCommand(matchNameResult(matcher), 
                                matchDetailResult(matcher), matchOnDateTimeResult(matcher), 
                                matchByDateTimeResult(matcher), matchPriorityResult(matcher), 
                                Frequency.NONE);
                         
                    } else if (p.equals(ParserFormats.ADD_PRIORITY_FL)) {
                        return new AddCommand(matchNameResult(matcher), 
                                matchDetailResult(matcher), null, 
                                null, matchPriorityResult(matcher), 
                                Frequency.NONE);
                        
                    } else if (p.equals(ParserFormats.ADD_PRIORITY_ON)) {
                        return new AddCommand(matchNameResult(matcher), 
                                matchDetailResult(matcher), matchOnDateTimeResult(matcher), 
                                null, matchPriorityResult(matcher), 
                                Frequency.NONE);  

                    } else if (p.equals(ParserFormats.ADD_PRIORITY_BY)) {
                        return new AddCommand(matchNameResult(matcher), 
                                matchDetailResult(matcher), null, 
                                matchByDateTimeResult(matcher), matchPriorityResult(matcher), 
                                Frequency.NONE);
                        
                    } else {
                        return new AddCommand(matchNameResult(matcher), 
                                matchDetailResult(matcher), 
                                null, null, 
                                Priority.DEFAULT_PRIORITY, Frequency.NONE);
                    }
                }
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        } 

        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
    //@@author A0121643R
    /**
     * Parses arguments in the context of the delete task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareDelete(String args) {

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return new DeleteCommand(index.get());
    }

    /**
     * Parses arguments in the context of the mark task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareMark(String args) {

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }

        return new MarkCommand(index.get());
    }

    /**
     * Parses arguments in the context of the unmark task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareUnmark(String args) {

        Optional<Integer> index = parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
        }

        return new UnmarkCommand(index.get());
    }

    /**
     * Parses arguments in the context of the tag task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareTag(String args) {
        try {
            String tempArgs = args.trim();
            String indexString = tempArgs.substring(0, 1);

            Optional<Integer> index = parseIndex(indexString);
            if (!index.isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
            }

            String tagNames = tempArgs.substring(1);

            return new TagCommand(index.get(), tagNames);
        } catch (Exception e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

    }

    /**
     * Parses arguments in the context of the untag task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareUntag(String args) {
        try {
            String tempArgs = args.trim();
            String indexString = tempArgs.substring(0, 1);

            Optional<Integer> index = parseIndex(indexString);
            if (!index.isPresent()) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
            }

            String tagNames = tempArgs.substring(1);

            return new UntagCommand(index.get(), tagNames);
        } catch (Exception e) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

    }

    /**
     * Returns the specified index in the {@code command} IF a positive unsigned
     * integer is given as the index. Returns an {@code Optional.empty()}
     * otherwise.
     */
    private Optional<Integer> parseIndex(String command) {
        final Matcher matcher = ParserFormats.TASK_INDEX_ARGS_FORMAT.matcher(command.trim());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        String index = matcher.group("targetIndex");
        if (!StringUtil.isUnsignedInteger(index)) {
            return Optional.empty();
        }
        return Optional.of(Integer.parseInt(index));

    }

    /**
     * Parses arguments in the context of the add task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareUpdate(String args) {
        
        String tempArgs = args.trim(); 
        
        if (tempArgs.length() < 1) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }
        String indexString = tempArgs.split(" ")[0];

        Optional<Integer> index = parseIndex(indexString);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        tempArgs = tempArgs.substring(indexString.length()).trim();

        Matcher matcher;
        matcher = ParserFormats.UPDATE_TASK_ARGS_FORMAT.matcher(tempArgs.trim());
        if (matcher.matches()) {
            return new UpdateCommand(index.get(), 
                    matchNameResult(matcher).trim(), 
                    matchOnDateTimeResult(matcher), 
                    matchByDateTimeResult(matcher), 
                    matchDetailResult(matcher), 
                    matchPriorityResult(matcher), 
                    matchRecurrenceResult(matcher));
        } else {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }  
        

    }
    //@@author A0121643R
    /**
     * Parses arguments in the context of the search task command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareSearch(String args) {
        Pattern[] dataPatterns = { ParserFormats.SEARCH_TASK_ARGS_FORMAT_ON,
                ParserFormats.SEARCH_TASK_ARGS_FORMAT_BEFORE, 
                ParserFormats.SEARCH_TASK_ARGS_FORMAT_AFTER,
                ParserFormats.SEARCH_TASK_ARGS_FORMAT_FT, 
                ParserFormats.KEYWORDS_ARGS_FORMAT,
                ParserFormats.SEARCH_TASK_ARGS_FORMAT_TAG,
                ParserFormats.SEARCH_PRIORITY };
        
        String tempArgs = args.trim(); 
       
        Matcher matcher;        
        for (Pattern p : dataPatterns) {
            matcher = p.matcher(tempArgs);
            
            if (matcher.matches()) {
                SearchCompletedOption option = SearchCompletedOption.DONE;
                System.out.println(matcher.groupCount());
                if (matcher.groupCount() >= 2) {
                    System.out.println(matcher.group(1).trim().toUpperCase());
                    option = SearchCompletedOption.valueOf(matcher.group(1).trim().toUpperCase());
                }                
                if (p.equals(ParserFormats.SEARCH_TASK_ARGS_FORMAT_ON)) {
                    return new SearchCommand(matchOnDateTimeResult(matcher),
                                             option,
                                             SearchCommand.SearchIndex.ON);
                    
                } else if (p.equals(ParserFormats.SEARCH_TASK_ARGS_FORMAT_BEFORE)) {
                    return new SearchCommand(matcher.group("beforeDateTime"),
                                             option,
                                             SearchCommand.SearchIndex.BEFORE);
                    
                } else if (p.equals(ParserFormats.SEARCH_TASK_ARGS_FORMAT_AFTER)) {
                    return new SearchCommand(matcher.group("afterDateTime"),
                                             option,
                                             SearchCommand.SearchIndex.AFTER);
                    
                } else if (p.equals(ParserFormats.SEARCH_TASK_ARGS_FORMAT_FT)) {
                    return new SearchCommand(matcher.group("fromDateTime") + SearchCommand.FT_CONCATENATER 
                                           + matcher.group("tillDateTime"),
                                             option,
                                             SearchCommand.SearchIndex.FT);
                    
                } else if (p.equals(ParserFormats.SEARCH_KEYWORDS_ARGS_FORMAT) && tempArgs.indexOf("tag") != 0
                        && tempArgs.indexOf("priority") != 0) {
                    return new SearchCommand(matcher.group("keywords"), 
                                             option,
                                             SearchCommand.SearchIndex.KEYWORD);
                } else if (p.equals(ParserFormats.SEARCH_PRIORITY)) {
                    return new SearchCommand(matchTagsResult(matcher), 
                                             option,
                                             SearchCommand.SearchIndex.TAG);
                } else if (p.equals(ParserFormats.SEARCH_TASK_ARGS_FORMAT_TAG)) {
                    return new SearchCommand(matchPriorityResult(matcher), 
                            option,
                            SearchCommand.SearchIndex.PRIORITY);
                }
            }
        }
        

        if (tempArgs.indexOf("done") == 0) {
            return new SearchCommand(tempArgs, SearchCompletedOption.ALL, SearchCommand.SearchIndex.DONE);
        }

        if (tempArgs.indexOf("undone") == 0) {
            return new SearchCommand(tempArgs, SearchCompletedOption.ALL, SearchCommand.SearchIndex.UNDONE);
        }
        
        return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));

    }
    
    /**
     * Parses arguments in the context of the store command.
     *
     * @param args full command args string
     * @return the prepared command
     */
    private Command prepareStore(String args) {
        args = args.trim();
        return new StoreCommand(args);
    }

}

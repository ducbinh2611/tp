package seedu.address.logic.parser.scheduleparsers;


import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.addressbookcommands.HelpCommand;
import seedu.address.logic.commands.schedulecommands.ScheduleAddCommand;
import seedu.address.logic.commands.schedulecommands.ScheduleDeleteCommand;
import seedu.address.logic.commands.schedulecommands.ScheduleListCommand;
import seedu.address.logic.commands.schedulecommands.ScheduleSearchCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

public class ScheduleParser implements Parser<Command> {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern
            .compile("((\\w+) (\\w+))(\\s.*)?");

    @Override
    public Command parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }
        final String commandWord = matcher.group(1);
        //temporary to solve the bug...
        final String arguments = matcher.group(4);


        switch (commandWord) {
        case ScheduleListCommand.COMMAND_WORD:
            return new ScheduleListCommand();
        case ScheduleAddCommand.COMMAND_WORD:
            return new ScheduleAddCommandParser().parse(arguments);
        case ScheduleDeleteCommand.COMMAND_WORD:
            return new ScheduleDeleteCommandParser().parse(arguments);
        case ScheduleSearchCommand.COMMAND_WORD:
            return new ScheduleSearchCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

    }
}

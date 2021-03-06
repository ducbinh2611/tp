package seedu.address.logic.parser.scheduleparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.schedulecommands.ScheduleDeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.parserutils.ParserUtil;

public class ScheduleDeleteCommandParser implements Parser<ScheduleDeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ScheduleDeleteCommand
     * and returns a ScheduleDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ScheduleDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}

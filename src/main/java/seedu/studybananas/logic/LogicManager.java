package seedu.studybananas.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.studybananas.commons.core.GuiSettings;
import seedu.studybananas.commons.core.LogsCenter;
import seedu.studybananas.logic.commands.Command;
import seedu.studybananas.logic.commands.CommandResult;
import seedu.studybananas.logic.commands.exceptions.CommandException;
import seedu.studybananas.logic.parser.StudyBananasParser;
import seedu.studybananas.logic.parser.exceptions.ParseException;
import seedu.studybananas.model.Model;
import seedu.studybananas.model.flashcard.FlashcardSet;
import seedu.studybananas.model.systemlevelmodel.ReadOnlyFlashcardBank;
import seedu.studybananas.model.systemlevelmodel.ReadOnlySchedule;
import seedu.studybananas.model.task.Task;
import seedu.studybananas.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final StudyBananasParser studyBananasParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        studyBananasParser = new StudyBananasParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command<? super Model> command = studyBananasParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveSchedule(model.getSchedule());
            storage.saveFlashcardBank(model.getFlashcardBank());
            storage.saveQuizRecords(model.getAllQuizRecords());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlySchedule getSchedule() {
        return model.getSchedule();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<Task> getUpcomingTaskList() {
        return model.getUpcomingTaskList();
    }

    @Override
    public Path getScheduleFilePath() {
        return model.getScheduleFilePath();
    }

    @Override
    public ReadOnlyFlashcardBank getFlashcardBank() {
        return model.getFlashcardBank();
    }

    @Override
    public ObservableList<FlashcardSet> getFilteredFlashcardSetList() {
        return model.getFilteredFlashcardSetList();
    }

    @Override
    public Path getFlashcardBankFilePath() {
        return model.getFlashcardBankFilePath();
    }

    @Override
    public FlashcardSet getFlashcardSetToView() {
        return model.getFlashcardSetToView();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
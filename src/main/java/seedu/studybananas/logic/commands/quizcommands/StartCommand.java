package seedu.studybananas.logic.commands.quizcommands;

import static java.util.Objects.requireNonNull;

import seedu.studybananas.commons.core.index.Index;
import seedu.studybananas.logic.commands.Command;
import seedu.studybananas.logic.commands.CommandResult;
import seedu.studybananas.logic.commands.exceptions.CommandException;
import seedu.studybananas.model.Model;
import seedu.studybananas.model.flashcard.FlashcardSet;
import seedu.studybananas.model.flashcard.Question;
import seedu.studybananas.model.quiz.Quiz;

//The abstraction has to be clarified.
public class StartCommand extends Command<Model> {

    public static final String COMMAND_WORD = "quiz flset:";

    public static final String MESSAGE_QUIZ_IN_PROGRESS = "A quiz is already in progress! "
            + "Key 'refresh' to see current question/answer. \n"
            + "To stop the current quiz, key 'cancel'.";

    public static final String MESSAGE_FLASHCARD_SET_NONEXISTENT = "Flashcard set does not exist";

    public static final String MESSAGE_FLASHCARD_SET_EMPTY = "Flashcard set is empty";

    private final int index;

    public StartCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasStarted()) {
            throw new CommandException(MESSAGE_QUIZ_IN_PROGRESS);
        }

        try {
            Index indexWrapper = Index.fromOneBased(index);
            FlashcardSet flashcardSet = model.getFlashcardSet(indexWrapper);
            Quiz quiz = new Quiz(this.index, flashcardSet);
            Question firstQuestion = model.start(quiz);

            QuizCommand.setStatus(Status.ON_QUESTION);
            QuizCommand.updateCommandResult(firstQuestion.toString());

            return new CommandResult(firstQuestion.toString());

        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_FLASHCARD_SET_NONEXISTENT);
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_FLASHCARD_SET_EMPTY);
        }
    }
}
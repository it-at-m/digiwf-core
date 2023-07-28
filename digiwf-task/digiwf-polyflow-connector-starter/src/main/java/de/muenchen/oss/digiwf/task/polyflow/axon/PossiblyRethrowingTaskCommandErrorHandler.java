package de.muenchen.oss.digiwf.task.polyflow.axon;

import io.holunda.polyflow.taskpool.sender.gateway.CommandErrorHandler;
import kotlin.Unit;
import org.axonframework.commandhandling.CommandResultMessage;

/**
 * A {@link CommandErrorHandler} that wraps another handler and can be configured to re-throw any errors
 * instead of calling that handler for some thread.
 */
public class PossiblyRethrowingTaskCommandErrorHandler implements CommandErrorHandler {

    private final CommandErrorHandler standardHandler;
    private static final ThreadLocal<Boolean> rethrow = ThreadLocal.withInitial(() -> false);

    public PossiblyRethrowingTaskCommandErrorHandler(CommandErrorHandler standardHandler) {
        this.standardHandler = standardHandler;
    }

    @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
    @Override
    public Unit apply(Object commandMessage, CommandResultMessage<?> commandResultMessage) {
        if (commandResultMessage.exceptionResult() != null && rethrow.get()) {
            if (commandResultMessage.exceptionResult() instanceof RuntimeException) {
                throw (RuntimeException) commandResultMessage.exceptionResult();
            }
            throw new RuntimeException(commandResultMessage.exceptionResult());
        }
        return standardHandler.apply(commandMessage, commandResultMessage);
    }

    /**
     * Run the specified block with modified error handling behavior. If the error handler is called anywhere inside the
     * block, the error is re-thrown instead of calling the standard handler. Checked exceptions will be wrapped
     * in a RuntimeException. The call to the error handler must occur in the same thread for this to work.
     *
     * @param block the block to run
     */
    public static void executeRethrowing(Runnable block) {
        try {
            rethrow.set(true);
            block.run();
        } finally {
            rethrow.set(false);
        }
    }
}

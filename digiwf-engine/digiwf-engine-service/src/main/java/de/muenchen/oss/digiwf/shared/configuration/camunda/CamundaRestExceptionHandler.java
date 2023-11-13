package de.muenchen.oss.digiwf.shared.configuration.camunda;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.rest.dto.ExceptionDto;
import org.camunda.bpm.engine.rest.exception.ExceptionHandlerHelper;
import org.camunda.bpm.engine.rest.exception.RestException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.camunda.commons.utils.StringUtil.getStackTrace;

/**
 * Changes the behaviour of camunda's {@link org.camunda.bpm.engine.rest.exception.RestExceptionHandler RestExceptionHandler}
 * to log only internal server errors on WARNING level and all other exceptions on FINE level.
 */
@Provider
@Slf4j
public class CamundaRestExceptionHandler extends org.camunda.bpm.engine.rest.exception.RestExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(CamundaRestExceptionHandler.class.getSimpleName());

    @Override
    public Response toResponse(RestException exception) {
        Response.Status responseStatus = ExceptionHandlerHelper.getInstance().getStatus(exception);
        ExceptionDto exceptionDto = ExceptionHandlerHelper.getInstance().fromException(exception);

        if (responseStatus == Response.Status.INTERNAL_SERVER_ERROR) {
            LOGGER.log(Level.WARNING, getStackTrace(exception));
        } else if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, getStackTrace(exception));
        }

        return Response
            .status(responseStatus)
            .entity(exceptionDto)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .build();
    }
}

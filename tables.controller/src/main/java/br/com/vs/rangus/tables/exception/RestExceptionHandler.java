package br.com.vs.rangus.tables.exception;

import br.com.vs.rangus.tables.error.Error;
import br.com.vs.rangus.tables.exceptions.MerchantNotFoundException;
import br.com.vs.rangus.tables.exceptions.TableNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception ex) {
        loggingError(ex);
        return buildResponseEntity(new Error(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    @ExceptionHandler({MerchantNotFoundException.class, TableNotFoundException.class})
    protected ResponseEntity<Object> handleCustomException(Exception ex) {
        loggingError(ex);
        return buildResponseEntity(new Error(HttpStatus.BAD_REQUEST, ex));
    }

    private void loggingError(Exception ex) {
        final StackTraceElement element = Arrays.stream(ex.getStackTrace()).findFirst().orElse(null);
        assert element != null;

        String className = element.getClassName();

        Logger logger = LoggerFactory.getLogger(className);
        logger.error(ex.getMessage(), ex);
    }

    private ResponseEntity<Object> buildResponseEntity(Error apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}

package controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public abstract class AbstractController {

    @ExceptionHandler
    public ResponseEntity<?> handleException(final Exception exception) {
        if (exception instanceof EntityNotFoundException) {
            return new ResponseEntity<>(exception.getMessage(), NOT_FOUND);
        }
        if (exception instanceof DataIntegrityViolationException) {
            return new ResponseEntity<>(exception.getMessage(), CONFLICT);
        }
        return new ResponseEntity<>(exception.getMessage(), INTERNAL_SERVER_ERROR);
    }
}

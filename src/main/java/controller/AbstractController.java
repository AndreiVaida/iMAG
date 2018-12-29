package controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Controller
public abstract class AbstractController {

    @ExceptionHandler
    public ResponseEntity<?> handleException(final Exception e) {
        if (e instanceof EntityNotFoundException) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
        if (e instanceof DataIntegrityViolationException) {
            return new ResponseEntity<>(e.getMessage(), CONFLICT);
        }
        if (e instanceof AuthenticationException) {
            return new ResponseEntity<>(e.getMessage(), UNAUTHORIZED);
        }
        return new ResponseEntity<>(e.getMessage(), INTERNAL_SERVER_ERROR);
    }
}

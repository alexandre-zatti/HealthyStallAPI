package br.edu.unochapeco.feirinha.handler;

import br.edu.unochapeco.feirinha.exception.PersonNotFoundException;
import br.edu.unochapeco.feirinha.exception.UniqueUsernameValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        var errorMap = new HashMap<String, String>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePersonNotFoundException(PersonNotFoundException ex){
        var errorMap = new HashMap<String, String>();

        errorMap.put("Error message: ", ex.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UniqueUsernameValidationException.class)
    public ResponseEntity<Map<String, String>> handleUniqueUsernameValidationException(UniqueUsernameValidationException ex){
        var errorMap = new HashMap<String, String>();

        errorMap.put("Error message: ", ex.getMessage());

        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}

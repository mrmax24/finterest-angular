package finterest.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .sorted((error1, error2) -> {
                    if (error1 instanceof FieldError && error2 instanceof FieldError) {
                        String field1 = ((FieldError) error1).getField();
                        String field2 = ((FieldError) error2).getField();
                        return compareFields(field1, field2);
                    } else {
                        return 0;
                    }
                })
                .map(this::getErrorMessage)
                .collect(Collectors.toList());

        String errorMessage = String.join(", ", errors);

        body.put("errors", errorMessage);
        return new ResponseEntity<>(body, headers, status);
    }

    private int compareFields(String field1, String field2) {
        if (field1.equals("login") && field2.equals("email")) {
            return -1;
        } else if (field1.equals("email") && field2.equals("login")) {
            return 1;
        } else if (field1.equals("login") && field2.equals("password")) {
            return -1;
        } else if (field1.equals("password") && field2.equals("login")) {
            return 1;
        } else if (field1.equals("email") && field2.equals("password")) {
            return -1;
        } else if (field1.equals("password") && field2.equals("email")) {
            return 1;
        }
        return 0;
    }

    private String getErrorMessage(ObjectError e) {
        if (e instanceof FieldError) {
            String field = ((FieldError) e).getField();
            return e.getDefaultMessage();
        }
        return e.getDefaultMessage();
    }
}

package ro.amihalcea.ecommerce_app.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.amihalcea.ecommerce_app.dto.ExceptionDTO;
import ro.amihalcea.ecommerce_app.exception.ProductNotFoundException;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@ControllerAdvice
public class CustomExceptionHandler {

    private final ObjectMapper mapper;

    @Autowired
    public CustomExceptionHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionDTO> handleRuntimeException(ProductNotFoundException ex) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDTO(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).toString(),
                        ex.getMessage()));
    }
}

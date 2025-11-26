package ro.amihalcea.ecommerce_app.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record ExceptionDTO(String dateTime, String message)
{}

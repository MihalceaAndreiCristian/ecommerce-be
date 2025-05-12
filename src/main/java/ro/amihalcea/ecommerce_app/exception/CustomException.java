package ro.amihalcea.ecommerce_app.exception;

public abstract class CustomException extends RuntimeException {
    public CustomException(String message, Object... arguments) {
        super(String.format(message,arguments));
    }
}

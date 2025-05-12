package ro.amihalcea.ecommerce_app.exception;

public class ProductNotFoundException extends CustomException{

    public ProductNotFoundException(String message, Object... arguments) {
        super(message,arguments);
    }



}

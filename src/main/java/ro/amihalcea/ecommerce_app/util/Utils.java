package ro.amihalcea.ecommerce_app.util;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class Utils {

    public static void putIfValid(Map<String, Object> map, String key, String value, Predicate<String> validator) {
        Optional
                .ofNullable(value)
                .filter(validator)
                .ifPresent(validValue -> map.put(key, validValue));
    }
}


package vn.rikkei.exam.restaurantreservation.util;

import vn.rikkei.exam.restaurantreservation.exception.BusinessException;

public class InputValidator {
    private InputValidator() {}

    public static void validate(String message) {
        if (message == null || message.isBlank()) {
            throw new BusinessException("Noi dung khong duoc rong");
        }
        if (message.length() > 2000) {
            throw new BusinessException("Noi dung qua dai (toi da 2000 ky tu)");
        }
    }
}

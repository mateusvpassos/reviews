package br.com.mateus.crud.endpoint.util;

import com.google.common.base.Strings;

public class StringValidator {
    public static void validateIfStringIsNullOrEmpty(String string, String name) {
        if (Strings.isNullOrEmpty(string)) {
            throw new IllegalArgumentException(name + " should not be null or empty");
        }
    }
}

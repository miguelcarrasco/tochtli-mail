package com.tlacaelelsoftware.tochtlimail.worker;

/**
 * Utility class
 */
public class StringUtils {
    private static boolean isNotEmpty(String string) {
        if (string == null | "".equals(string)) {
            return false;
        }

        return true;
    }

    public static void checkIsNotEmpty(String string) throws IllegalStateException{
        if(!isNotEmpty(string)){
           throw new IllegalStateException("should not be empty");
        }
    }
}

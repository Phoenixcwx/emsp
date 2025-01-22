package com.volvo.emspdemo.util;

public class Base36Util {

    private static final String BASE36_DIGITS = "0123456789abcdefghijklmnopqrstuvwxyz";

    /**
     * 将数字转换为 36 进制的字符串
     */
    public static String toBase36(long number) {
        if (number == 0) {
            return "0";
        }

        StringBuilder result = new StringBuilder();
        while (number > 0) {
            int remainder = (int) (number % 36);
            result.insert(0, BASE36_DIGITS.charAt(remainder));
            number /= 36;
        }

        return result.toString();
    }
}

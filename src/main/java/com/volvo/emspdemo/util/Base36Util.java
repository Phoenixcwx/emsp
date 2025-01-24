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

    /**
     * 将字符格式化为指定长度，不足部分用零填充
     *
     * @param input  需要格式化的字符
     * @param length 输出字符串的总长度
     * @return 格式化后的字符串
     */
    public static String formatString(String input, int length) {
        if (input == null) {
            throw new IllegalArgumentException("输入字符串不能为 null");
        }

        // 如果输入字符串长度大于等于指定长度，直接返回原字符串
        if (input.length() >= length) {
            return input;
        }

        // 计算需要填充的零的数量
        int zerosToAdd = length - input.length();

        // 生成填充的零
        String zeros = "0".repeat(zerosToAdd);

        // 返回填充后的字符串
        return zeros + input;
    }
}

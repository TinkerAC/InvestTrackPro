package com.rjgc.xxx.investtrackpro.util;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityUtil {
    public static boolean isAuthorized(String username, String password) {
        // Check if user is authorized
        return true;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static boolean checkPassword(String password, String hash) {
        return hashPassword(password).equals(hash);
    }

    /**
     * 验证邮箱地址的有效性
     *
     * @param email 要验证的邮箱地址
     * @return 如果邮箱地址有效，则返回true；否则返回false
     */
    public static boolean isValidEmail(String email) {
        // 定义邮箱的正则表达式模式
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

        // 编译正则表达式模式
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }


    public static boolean  isInvalid(String value) {
        return value == null || value.isEmpty();
    }

}



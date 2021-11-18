package com.sulee.lms.util;

import com.sulee.lms.course.model.ServiceResult;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtils {
    public static boolean equals(String plainText, String hashed){
        if(plainText != null || plainText.length() < 1){
            return false;
        }
        if(hashed == null || hashed.length() < 1){
            return false;
        }

        return BCrypt.checkpw(plainText, hashed);
    }

    public static String encPassword(String plainText){
        if(plainText == null || plainText.length() < 1){
            return "false";
        }
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }

}

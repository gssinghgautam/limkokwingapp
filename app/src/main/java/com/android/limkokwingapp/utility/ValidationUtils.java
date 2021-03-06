package com.android.limkokwingapp.utility;


import android.support.annotation.NonNull;

import java.util.regex.Pattern;

public class ValidationUtils {

    //Regex for login and signup password validation
    private static final String PASSWORD_REGEX = "[a-zA-Z0-9 ]*";

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    /**
     * Method to check whether the string is empty or not.
     *
     * @param string String to validate
     * @return boolean true if string is empty
     */
    public static boolean isValidString(String string) {
        return !string.isEmpty();
    }

    /**
     * Method to check valid Malaysian mobile number
     *
     * @param mobile Mobile number
     * @return boolean true if mobile number is valid
     */
    public static boolean isValidMobile(@NonNull String mobile) {
        return !mobile.isEmpty() && mobile.length() == 9;
    }

    /**
     * Method to check valid Gender
     *
     * @param gender Gender (Male or Female)
     * @return boolean true if gender is valid
     */
    public static boolean isValidGender(@NonNull String gender) {
        return !gender.isEmpty() && !gender.equalsIgnoreCase("Select Gender");
    }


    /**
     * Method to check valid password
     *
     * @param password - Password should be at least 8 characters with one special character (Eg: abcdefgh@)
     * @return boolean true if password is valid
     */
    public static boolean isValidPassword(@NonNull String password) {
        return !password.isEmpty() && password.length() >= 8 && !(Pattern.compile(PASSWORD_REGEX).matcher(password).matches());
    }


    /**
     * Method to check valid email
     *
     * @param email - Email id of the user
     * @return boolean true if email is valid
     */
    public static boolean isValidEmail(@NonNull String email) {
        return !email.isEmpty() && Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }


}

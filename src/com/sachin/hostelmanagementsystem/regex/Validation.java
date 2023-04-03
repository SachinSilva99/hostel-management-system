package com.sachin.hostelmanagementsystem.regex;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private final Pattern price = Pattern.compile("^([0-9]\\d*(\\.\\d*[0-9])?|0\\.\\d*[0-9]+)|\\d+(\\.\\d*[0-9])?$");
    private final Pattern qtyPattern = Pattern.compile("^[0-9]{1,50}$");
    private final Pattern namePattern = Pattern.compile("^[a-zA-Z]{1,50}$");
    private final Pattern address = Pattern.compile("([a-zA-Z|0-9|\\s+]{3,})");
    private final Pattern phoneNumberP = Pattern.compile("^[0|+94][77|76|71|70|78|74|75|][0-9]{8}$");
    private final Pattern room_type_id = Pattern.compile("^RM-\\d{4}$");
    private final Pattern student_id = Pattern.compile("^[A-Za-z]+-\\w+$");

    public boolean match(String field, Validates validate) {
        switch (validate) {
            case NAME: {
                Matcher matcher = namePattern.matcher(field);
                return matcher.matches();
            }
            case ADDRESS: {
                Matcher matcher = address.matcher(field);
                return matcher.matches();
            }
            case PHONE_NUMBER: {
                Matcher matcher = phoneNumberP.matcher(field);
                return matcher.matches();
            }
            case QTY: {
                Matcher matcher = qtyPattern.matcher(field);
                return matcher.matches();
            }
            case PRICE: {
                Matcher matcher = price.matcher(field);
                return matcher.matches();
            }
            case ROOM_TYPE_ID: {
                Matcher matcher = room_type_id.matcher(field);
                return matcher.matches();
            }

            case STUDENT_ID: {
                Matcher matcher = student_id.matcher(field);
                return matcher.matches();
            }
            default: {
                return false;
            }
        }
    }
}

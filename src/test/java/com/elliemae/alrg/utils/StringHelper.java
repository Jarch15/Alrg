package com.elliemae.alrg.utils;

import com.elliemae.core.Logger.EllieMaeLog.EllieMaeLogLevel;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;

public class StringHelper {

    public static Logger _log = Logger.getLogger(StringHelper.class);

    public static String alphabet_yyMMddHHmmss() {
        String d = CommonUtilityApplication.getCurrentDateTimeString("yyMMddHHmmss");
        return date_to_alphabet(d);
    }

    public static String alphabet_HHmmssMMddyy() {
        String d = CommonUtilityApplication.getCurrentDateTimeString("HHmmssMMddyy");
        return date_to_alphabet(d);
    }

    public static String alphabet_HHmmss() {
        String d = CommonUtilityApplication.getCurrentDateTimeString("HHmmss");
        return date_to_alphabet(d);
    }

    public static String date_to_alphabet(String d) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < d.length(); i++) {
            sb.append(intToChar(Integer.parseInt(d.charAt(i) + "") + 65 + 32));
        }
        return sb.toString();
    }

    public static String alphabetDate_to_date(String d) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < d.length(); i++) {
            sb.append(intToChar(d.charAt(i)) - 65 - 32);
        }
        return sb.toString();
    }

    public static char intToChar(int i) {
        return (char) i;
    }

    public static String generateRandomString(int length) {
        String generatedString = RandomStringUtils.randomAlphabetic(length);
        return generatedString;
    }

    public static String generateAlphanumericString(int length) {
        String randomString = RandomStringUtils.randomAlphanumeric(length);
        return randomString;
    }

    public static boolean isTextFoundInList(List<String> elementList, String text, String description, boolean ignoreCase, boolean exactMatch) {
        boolean itemPresent = false;
        text = text.trim();
        try {
            for (int i = 0; i <= elementList.size() - 1; i++) {
                String v = elementList.get(i).trim();
                if (exactMatch && !ignoreCase && v.equals(text)) {
                    itemPresent = true;
                } else if (exactMatch && ignoreCase && v.equalsIgnoreCase(text)) {
                    itemPresent = true;
                } else if (!exactMatch && !ignoreCase && v.contains(text)) {
                    itemPresent = true;
                } else if (!exactMatch && ignoreCase && v.toLowerCase().contains(text.toLowerCase())) {
                    itemPresent = true;
                }

                if (itemPresent) {
                    ALRGLogger.log(_log, text + "found in selected list_ " + description);
                    break;
                }
            }
        } catch (Exception e) {
            ALRGLogger.log(_log, "Data found in selected list_ " + description + " failed, exception " + e.getMessage(), EllieMaeLogLevel.reporter);

        }
        return itemPresent;
    }

    public static void main(String[] args) {

        StringHelper helper = new StringHelper();
        String sDate = helper.alphabet_yyMMddHHmmss();
        String nDate = helper.alphabetDate_to_date(sDate);
        System.out.println(sDate + "-->" + nDate);
    }
}

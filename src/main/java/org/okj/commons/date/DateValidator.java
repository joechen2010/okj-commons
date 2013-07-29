package org.okj.commons.date;


public class DateValidator {

    public boolean validateExiryDate(String expiryDate) {
        String today = DateUtils.toString(System.currentTimeMillis());

        int result = today.compareTo(expiryDate);

        return result <= 0;
    }
}

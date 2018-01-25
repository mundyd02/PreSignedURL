package uk.co.bbc.gdpr.utils;

import java.util.Date;

public class DateExpiration {

    private static final int HOUR_MILLISECONDS = 1000 * 60 * 60;;

    private Date expirationDate;

    public Date getExpirationDate() {
        return expirationDate;
    }

    public DateExpiration() {
        expirationDate = new Date();
        long milliSeconds = expirationDate.getTime();
        milliSeconds += HOUR_MILLISECONDS;
        expirationDate.setTime(milliSeconds);
    }
}

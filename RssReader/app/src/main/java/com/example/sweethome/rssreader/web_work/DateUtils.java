package com.example.sweethome.rssreader.web_work;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

final class DateUtils {
    private static final SimpleDateFormat[] sDateFormats;
    private static final String KEY_TIME_ZONE = "GMT";

    static {
        final String[] possibleDateFormats = {
                "dd MMM yyyy HH:mm:ss z",
                "EEE, dd MMM yyyy HH:mm:ss z",
                "EEE, dd MMM yyyy HH:mm zzzz",
                "yyyy-MM-dd'T'HH:mm:ssZ",
                "yyyy-MM-dd'T'HH:mm:ss.SSSzzzz",
                "yyyy-MM-dd'T'HH:mm:sszzzz",
                "yyyy-MM-dd'T'HH:mm:ss z",
                "yyyy-MM-dd'T'HH:mm:ssz",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd'T'HHmmss.SSSz",
                "yyyy-MM-dd"
        };

        sDateFormats = new SimpleDateFormat[possibleDateFormats.length];
        final TimeZone gmtTZ = TimeZone.getTimeZone(KEY_TIME_ZONE);

        for (int i = 0; i < possibleDateFormats.length; i++) {
            sDateFormats[i] = new SimpleDateFormat(possibleDateFormats[i], Locale.ENGLISH);
            sDateFormats[i].setTimeZone(gmtTZ);
        }
    }

    static String parseDate(final String publicationDate) {
        Date result = null;
        boolean wasChanged = false;
        String trimPublicationDate = publicationDate.trim();
        if (trimPublicationDate.length() > 10) {
            wasChanged = true;
            if ((trimPublicationDate.substring(trimPublicationDate.length() - 5).indexOf("+") == 0
                    || trimPublicationDate.substring(trimPublicationDate.length() - 5).indexOf("-") == 0)
                    && trimPublicationDate.substring(trimPublicationDate.length() - 5).indexOf(":") == 2) {
                String sign = trimPublicationDate.substring(trimPublicationDate.length() - 5, trimPublicationDate.length() - 4);
                trimPublicationDate = trimPublicationDate.substring(0, trimPublicationDate.length() - 5) + sign
                        + "0" + trimPublicationDate.substring(trimPublicationDate.length() - 4);
            }
            String dateEnd = trimPublicationDate.substring(trimPublicationDate.length() - 6);
            if ((dateEnd.indexOf("-") == 0 || dateEnd.indexOf("+") == 0) && dateEnd.indexOf(":") == 3) {
                if (!KEY_TIME_ZONE.equals(trimPublicationDate.substring(trimPublicationDate.length() - 9, trimPublicationDate.length() - 6))) {
                    String oldDate = trimPublicationDate;
                    String newEnd = dateEnd.substring(0, 3) + dateEnd.substring(4);
                    trimPublicationDate = oldDate.substring(0, oldDate.length() - 6) + newEnd;
                }
            }
        }
        int i = 0;
        while (i < sDateFormats.length) {
            try {
                if (wasChanged) {
                    result = sDateFormats[i].parse(trimPublicationDate);
                } else {
                    result = sDateFormats[i].parse(publicationDate);
                }
                break;
            } catch (java.text.ParseException eA) {
                i++;
            }
        }
        if (null == result) {
            return new Date(0).toString();
        } else {
            return result.toString();
        }
    }
}

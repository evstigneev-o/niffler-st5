package guru.qa.niffler.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String formatDate(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        return sdf.format(date);
    }
}

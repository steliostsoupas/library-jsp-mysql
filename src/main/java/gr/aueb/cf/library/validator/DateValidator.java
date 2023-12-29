package gr.aueb.cf.library.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidator {

    private static final String DATE_FORMAT = "dd/MM/yy";

    public static boolean isValidDateFormat(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return true;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);

        try {
            Date date = sdf.parse(dateStr);
            return dateStr.equals(sdf.format(date));
        } catch (ParseException e) {
            return false;
        }
    }

    public static String validateLoanAndReturnDates(String loanDate, String returnDate) {
        boolean isLoanDateValid = isValidDateFormat(loanDate);
        boolean isReturnDateValid = isValidDateFormat(returnDate);

        if (!isLoanDateValid && !isReturnDateValid) {
            return "Invalid dates format. Please use dd/MM/yy for both loan and return dates.";
        } else if (!isLoanDateValid) {
            return "Invalid loan date format. Please use dd/MM/yy.";
        } else if (!isReturnDateValid) {
            return "Invalid return date format. Please use dd/MM/yy.";
        }
        return null;
    }
}

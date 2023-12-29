package gr.aueb.cf.library.validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoanValidator {

    public static boolean validateInput(String bookId, String memberId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ((bookId == null || bookId.isBlank()) && (memberId == null || memberId.isBlank())) {
            request.setAttribute("error", "Book and Member cannot be empty");
            request.getRequestDispatcher("/library/loan/menu").forward(request, response);
            return false;
        }

        if (bookId == null || bookId.isBlank()) {
            request.setAttribute("error", "Book cannot be empty");
            request.getRequestDispatcher("/library/loan/menu").forward(request, response);
            return false;
        }

        if (memberId == null || memberId.isBlank()) {
            request.setAttribute("error", "Member cannot be empty");
            request.getRequestDispatcher("/library/loan/menu").forward(request, response);
            return false;
        }
        return true;
    }

    public static boolean validateUpdate(String bookId, String memberId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ((bookId == null || bookId.isBlank()) && (memberId == null || memberId.isBlank())) {
            request.setAttribute("error", "Book and Member cannot be empty");
            request.getRequestDispatcher("/library/static/templates/loan/loanupdate.jsp").forward(request, response);
            return false;
        }

        if (bookId == null || bookId.isBlank()) {
            request.setAttribute("error", "Book cannot be empty");
            request.getRequestDispatcher("/library/static/templates/loan/loanupdate.jsp").forward(request, response);
            return false;
        }

        if (memberId == null || memberId.isBlank()) {
            request.setAttribute("error", "Member cannot be empty");
            request.getRequestDispatcher("/library/static/templates/loan/loanupdate.jsp").forward(request, response);
            return false;
        }
        return true;
    }
}

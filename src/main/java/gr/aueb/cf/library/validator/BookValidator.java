package gr.aueb.cf.library.validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BookValidator {

    public static boolean validateInput(String title, String author, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ((title == null || title.isBlank()) && (author == null || author.isBlank())) {
            request.setAttribute("error", "Title and Author cannot be empty");
            request.getRequestDispatcher("/library/static/templates/book/booksmenu.jsp").forward(request, response);
            return false;
        } else if (title == null || title.isBlank()) {
            request.setAttribute("error", "Title cannot be empty");
            request.getRequestDispatcher("/library/static/templates/book/booksmenu.jsp").forward(request, response);
            return false;
        } else if (author == null || author.isBlank()) {
            request.setAttribute("error", "Author cannot be empty");
            request.getRequestDispatcher("/library/static/templates/book/booksmenu.jsp").forward(request, response);
            return false;
        }

        if ((title.length() < 3 || title.length() > 16) && (author.length() < 3 || author.length() > 16)) {
            request.setAttribute("error", "Title and Author size must be 3-16.");
            request.getRequestDispatcher("/library/static/templates/book/booksmenu.jsp").forward(request, response);
            return false;
        } else if ((title.length() < 3 || title.length() > 16)) {
            request.setAttribute("error", "Title size must be 3-16.");
            request.getRequestDispatcher("/library/static/templates/book/booksmenu.jsp").forward(request, response);
            return false;
        } else if ((author.length() < 3 || author.length() > 16)) {
            request.setAttribute("error", "Author size must be 3-16.");
            request.getRequestDispatcher("/library/static/templates/book/booksmenu.jsp").forward(request, response);
            return false;
        }

        return true;
    }

    public static boolean validateUpdate(String title, String author, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ((title == null || title.isBlank()) && (author == null || author.isBlank())) {
            request.setAttribute("error", "Title and Author cannot be empty");
            request.getRequestDispatcher("/library/static/templates/book/bookupdate.jsp").forward(request, response);
            return false;
        } else if (title == null || title.isBlank()) {
            request.setAttribute("error", "Title cannot be empty");
            request.getRequestDispatcher("/library/static/templates/book/bookupdate.jsp").forward(request, response);
            return false;
        } else if (author == null || author.isBlank()) {
            request.setAttribute("error", "Author cannot be empty");
            request.getRequestDispatcher("/library/static/templates/book/bookupdate.jsp").forward(request, response);
            return false;
        }

        if ((title.length() < 3 || title.length() > 16) && (author.length() < 3 || author.length() > 16)) {
            request.setAttribute("error", "Title and Author size must be 3-16.");
            request.getRequestDispatcher("/library/static/templates/book/bookupdate.jsp").forward(request, response);
            return false;
        } else if ((title.length() < 3 || title.length() > 16)) {
            request.setAttribute("error", "Title size must be 3-16.");
            request.getRequestDispatcher("/library/static/templates/book/bookupdate.jsp").forward(request, response);
            return false;
        } else if ((author.length() < 3 || author.length() > 16)) {
            request.setAttribute("error", "Author size must be 3-16.");
            request.getRequestDispatcher("/library/static/templates/book/bookupdate.jsp").forward(request, response);
            return false;
        }

        return true;
}
}

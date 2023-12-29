package gr.aueb.cf.library.validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberValidator {

    public static boolean validateInput(String firstname, String lastname, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ((firstname == null || firstname.isBlank()) && (lastname == null || lastname.isBlank())) {
            request.setAttribute("error", "Firstname and Lastname cannot be empty");
            request.getRequestDispatcher("/library/static/templates/member/membersmenu.jsp").forward(request, response);
            return false;
        } else if (firstname == null || firstname.isBlank()) {
            request.setAttribute("error", "Firstname cannot be empty");
            request.getRequestDispatcher("/library/static/templates/member/membersmenu.jsp").forward(request, response);
            return false;
        } else if (lastname == null || lastname.isBlank()) {
            request.setAttribute("error", "Lastname cannot be empty");
            request.getRequestDispatcher("/library/static/templates/member/membersmenu.jsp").forward(request, response);
            return false;
        }

        if ((firstname.length() < 3 || firstname.length() > 16) && (lastname.length() < 3 || lastname.length() > 16)) {
            request.setAttribute("error", "Firstname and Lastname size must be 3-16.");
            request.getRequestDispatcher("/library/static/templates/member/membersmenu.jsp").forward(request, response);
            return false;
        } else if ((firstname.length() < 3 || firstname.length() > 16)) {
            request.setAttribute("error", "Firstname size must be 3-16.");
            request.getRequestDispatcher("/library/static/templates/member/membersmenu.jsp").forward(request, response);
            return false;
        } else if ((lastname.length() < 3 || lastname.length() > 16)) {
            request.setAttribute("error", "Lastname size must be 3-16.");
            request.getRequestDispatcher("/library/static/templates/member/membersmenu.jsp").forward(request, response);
            return false;
        }

        return true;
    }

    public static boolean validateUpdate(String firstname, String lastname, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ((firstname == null || firstname.isBlank()) && (lastname == null || lastname.isBlank())) {
            request.setAttribute("error", "Firstname and Lastname cannot be empty");
            request.getRequestDispatcher("/library/static/templates/member/memberupdate.jsp").forward(request, response);
            return false;
        } else if (firstname == null || firstname.isBlank()) {
            request.setAttribute("error", "Firstname cannot be empty");
            request.getRequestDispatcher("/library/static/templates/member/memberupdate.jsp").forward(request, response);
            return false;
        } else if (lastname == null || lastname.isBlank()) {
            request.setAttribute("error", "Lastname cannot be empty");
            request.getRequestDispatcher("/library/static/templates/member/memberupdate.jsp").forward(request, response);
            return false;
        }

        if ((firstname.length() < 3 || firstname.length() > 16) && (lastname.length() < 3 || lastname.length() > 16)) {
            request.setAttribute("error", "Firstname and Lastname size must be 3-16.");
            request.getRequestDispatcher("/library/static/templates/member/memberupdate.jsp").forward(request, response);
            return false;
        } else if ((firstname.length() < 3 || firstname.length() > 16)) {
            request.setAttribute("error", "Firstname size must be 3-16.");
            request.getRequestDispatcher("/library/static/templates/member/memberupdate.jsp").forward(request, response);
            return false;
        } else if ((lastname.length() < 3 || lastname.length() > 16)) {
            request.setAttribute("error", "Lastname size must be 3-16.");
            request.getRequestDispatcher("/library/static/templates/member/memberupdate.jsp").forward(request, response);
            return false;
        }

        return true;
    }
}

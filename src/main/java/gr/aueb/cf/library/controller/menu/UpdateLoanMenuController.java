package gr.aueb.cf.library.controller.menu;


import gr.aueb.cf.library.dao.BookDAOImpl;
import gr.aueb.cf.library.dao.IBookDAO;
import gr.aueb.cf.library.dao.IMemberDAO;
import gr.aueb.cf.library.dao.MemberDAOImpl;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Member;
import gr.aueb.cf.library.service.BookServiceImpl;
import gr.aueb.cf.library.service.IBookService;
import gr.aueb.cf.library.service.IMemberService;
import gr.aueb.cf.library.service.MemberServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/library/updateloanmenu")
public class UpdateLoanMenuController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IBookDAO bookDAO = new BookDAOImpl();
    private final IBookService bookService = new BookServiceImpl(bookDAO);
    private final IMemberDAO memberDAO = new MemberDAOImpl();
    private final IMemberService memberService = new MemberServiceImpl(memberDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/library/static/templates/loan/loanupdate.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> books = bookService.getAllBooks();
        List<Member> members = memberService.getAllMembers();
        request.setAttribute("books", books);
        request.setAttribute("members", members);

        request.getRequestDispatcher("/library/static/templates/loan/loanupdate.jsp").forward(request, response);
    }
}

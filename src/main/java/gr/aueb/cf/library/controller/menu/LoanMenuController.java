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

@WebServlet(name = "LoanMenuController", value = "/library/loan/menu")
public class LoanMenuController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    IBookDAO bookDAO = new BookDAOImpl();
    IBookService bookService = new BookServiceImpl(bookDAO);
    IMemberDAO memberDAO = new MemberDAOImpl();
    IMemberService memberService = new MemberServiceImpl(memberDAO);

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> books = bookService.getAllBooks();
        List<Member> members = memberService.getAllMembers();
        request.setAttribute("books", books);
        request.setAttribute("members", members);
        request.getRequestDispatcher("/library/static/templates/loan/loansmenu.jsp").forward(request, response);
    }
}

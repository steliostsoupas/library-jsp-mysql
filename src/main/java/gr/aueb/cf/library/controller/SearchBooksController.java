package gr.aueb.cf.library.controller;

import gr.aueb.cf.library.dao.BookDAOImpl;
import gr.aueb.cf.library.dao.IBookDAO;
import gr.aueb.cf.library.dao.exceptions.BookDAOException;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.service.BookServiceImpl;
import gr.aueb.cf.library.service.IBookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchBooksController", value = "/library/searchbook")
public class SearchBooksController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    IBookDAO bookDAO = new BookDAOImpl();
    IBookService bookService = new BookServiceImpl(bookDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("isError", false);
        request.setAttribute("error", "");
        request.setAttribute("booksNotFound", false);
        request.getRequestDispatcher("/library/menu")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title").trim();

        String message;
        try {
            List<Book> books = bookService.getBooksByTitle(title);
            if (books.size() == 0) {
                request.setAttribute("booksNotFound", true);
                request.getRequestDispatcher("/library/static/templates/book/booksmenu.jsp")
                        .forward(request, response);
            }
            request.setAttribute("books", books);
            request.getRequestDispatcher("/library/static/templates/book/books.jsp").forward(request, response);
        } catch (BookDAOException e) {
            message = e.getMessage();
            request.setAttribute("isError", true);
            request.setAttribute("errorMessage", message);
        }
    }
}

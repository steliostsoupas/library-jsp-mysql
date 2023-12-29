package gr.aueb.cf.library.controller;

import gr.aueb.cf.library.dao.BookDAOImpl;
import gr.aueb.cf.library.dao.IBookDAO;
import gr.aueb.cf.library.dao.exceptions.BookDAOException;
import gr.aueb.cf.library.dto.BookInsertDTO;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.service.BookServiceImpl;
import gr.aueb.cf.library.service.IBookService;
import gr.aueb.cf.library.validator.BookValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/library/insertbook")
public class InsertBookController extends HttpServlet {
    private final IBookDAO bookDAO = new BookDAOImpl();
    private final IBookService bookService = new BookServiceImpl(bookDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/library/menu").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", "");

        String title = request.getParameter("title").trim();
        String author = request.getParameter("author").trim();

        BookInsertDTO bookInsertDTO = new BookInsertDTO();
        bookInsertDTO.setTitle(title);
        bookInsertDTO.setAuthor(author);

        if (!BookValidator.validateInput(title, author, request, response)) {
            return;
        }

        try {
            Book book = bookService.insertBook(bookInsertDTO);
            request.setAttribute("insertedBook", book);
            request.getRequestDispatcher("/library/static/templates/book/bookinserted.jsp").forward(request, response);
        } catch (BookDAOException e) {
            request.setAttribute("sqlError", true);
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("/library/menu").forward(request, response);

        }
    }
}

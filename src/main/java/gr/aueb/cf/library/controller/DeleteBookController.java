package gr.aueb.cf.library.controller;

import gr.aueb.cf.library.dao.BookDAOImpl;
import gr.aueb.cf.library.dao.ILoanDAO;
import gr.aueb.cf.library.dao.LoanDAOImpl;
import gr.aueb.cf.library.dao.MemberDAOImpl;
import gr.aueb.cf.library.dao.exceptions.BookDAOException;
import gr.aueb.cf.library.dao.exceptions.LoanDAOException;
import gr.aueb.cf.library.dto.BookDeleteDTO;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Loan;
import gr.aueb.cf.library.service.BookServiceImpl;
import gr.aueb.cf.library.service.IBookService;
import gr.aueb.cf.library.service.ILoanService;
import gr.aueb.cf.library.service.LoanServiceImpl;
import gr.aueb.cf.library.service.exceptions.BookNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/library/deletebook")
public class DeleteBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookDAOImpl bookDAO = new BookDAOImpl();
	IBookService bookServ = new BookServiceImpl(bookDAO);
	MemberDAOImpl memberDAO = new MemberDAOImpl();
	ILoanDAO loanDAO = new LoanDAOImpl(bookDAO, memberDAO);
	ILoanService loanServ = new LoanServiceImpl(loanDAO);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Book book = bookServ.getBookById(id);
		BookDeleteDTO bookDTO = new BookDeleteDTO
				(book.getId(), book.getTitle(), book.getAuthor(), book.isLoaned());


		try {
			List<Loan> allLoans = loanServ.getAllLoans();
			boolean isBookLoaned = allLoans.stream()
					.anyMatch(loan -> loan.getBook().getId() == id);
			if (isBookLoaned) {
				request.setAttribute("error", "Book with ID: " + id + " is currently loaned and cannot be deleted.");
				request.getRequestDispatcher("/library/static/templates/book/booksmenu.jsp").forward(request, response);
				return;
			}

			bookServ.deleteBook(id);
			request.setAttribute("bookDTO", bookDTO);
			request.getRequestDispatcher("/library/static/templates/book/bookdeleted.jsp")
					.forward(request, response);
		} catch (BookNotFoundException | BookDAOException e) {
			request.setAttribute("deleteAPIError", true);
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/library/static/templates/book/books.jsp")
					.forward(request, response);
		} catch (LoanDAOException e) {
			throw new RuntimeException(e);
		}
	}
}


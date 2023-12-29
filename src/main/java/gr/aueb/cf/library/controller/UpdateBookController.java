package gr.aueb.cf.library.controller;

import gr.aueb.cf.library.dao.BookDAOImpl;
import gr.aueb.cf.library.dao.ILoanDAO;
import gr.aueb.cf.library.dao.LoanDAOImpl;
import gr.aueb.cf.library.dao.MemberDAOImpl;
import gr.aueb.cf.library.dao.exceptions.BookDAOException;
import gr.aueb.cf.library.dao.exceptions.LoanDAOException;
import gr.aueb.cf.library.dto.BookUpdateDTO;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Loan;
import gr.aueb.cf.library.service.*;
import gr.aueb.cf.library.service.exceptions.BookNotFoundException;
import gr.aueb.cf.library.validator.BookValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/library/updatebook")
public class UpdateBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookDAOImpl bookDAO = new BookDAOImpl();
	IBookService bookServ = new BookServiceImpl(bookDAO);

	MemberDAOImpl memberDAO = new MemberDAOImpl();
	IMemberService memberServ = new MemberServiceImpl(memberDAO);

	ILoanDAO loanDAO = new LoanDAOImpl(bookDAO, memberDAO);
	ILoanService loanServ = new LoanServiceImpl(loanDAO);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/library/static/templates/book/bookupdate.jsp")
				.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String isLoanedInput = request.getParameter("isLoaned");
		boolean isLoaned = parseIsLoaned(isLoanedInput);

		if (!BookValidator.validateUpdate(title, author, request, response)) {
			return;
		}

		BookUpdateDTO newBookDTO = new BookUpdateDTO();
		newBookDTO.setId(id);
		newBookDTO.setTitle(title);
		newBookDTO.setAuthor(author);
		newBookDTO.setLoaned(isLoaned);
		request.setAttribute("insertedBook", newBookDTO);
		
		try {
			List<Loan> allLoans = loanServ.getAllLoans();
			boolean isBookLoaned = allLoans.stream()
					.anyMatch(loan -> loan.getBook().getId() == id);
			if (isBookLoaned) {
				request.setAttribute("error", "Book with ID: " + id + " is currently loaned and cannot be updated.");
				request.getRequestDispatcher("/library/static/templates/book/booksmenu.jsp").forward(request, response);
				return;
			}

			Book book =  bookServ.updateBook(newBookDTO);
			request.setAttribute("book", book);
			request.getRequestDispatcher("/library/static/templates/book/bookupdated.jsp")
					.forward(request, response);
		} catch (BookNotFoundException | BookDAOException e) {
			String message = e.getMessage();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/library/static/templates/book/bookupdated.jsp")
					.forward(request, response);

		} catch (LoanDAOException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean parseIsLoaned(String input) {
		return input != null && (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y"));
	}
}

package gr.aueb.cf.library.controller;

import gr.aueb.cf.library.dao.BookDAOImpl;
import gr.aueb.cf.library.dao.ILoanDAO;
import gr.aueb.cf.library.dao.LoanDAOImpl;
import gr.aueb.cf.library.dao.MemberDAOImpl;
import gr.aueb.cf.library.dao.exceptions.LoanDAOException;
import gr.aueb.cf.library.dto.LoanDeleteDTO;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Loan;
import gr.aueb.cf.library.service.BookServiceImpl;
import gr.aueb.cf.library.service.IBookService;
import gr.aueb.cf.library.service.ILoanService;
import gr.aueb.cf.library.service.LoanServiceImpl;
import gr.aueb.cf.library.service.exceptions.LoanNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/library/deleteloan")
public class DeleteLoanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ILoanService loanServ;
	private final IBookService bookService;

	public DeleteLoanController() {
		BookDAOImpl bookDAO = new BookDAOImpl();
		MemberDAOImpl memberDAO = new MemberDAOImpl();
		ILoanDAO loanDAO = new LoanDAOImpl(bookDAO, memberDAO);
		this.loanServ = new LoanServiceImpl(loanDAO);
		this.bookService = new BookServiceImpl(bookDAO);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Loan loan = loanServ.getLoanById(id);
		LoanDeleteDTO loanDTO = new LoanDeleteDTO(loan.getId(), loan.getBook(), loan.getMember());

		try {
			loanServ.deleteLoan(id);
			Book book = loan.getBook();
			book.setLoaned(false);
			bookService.updateBookLoanedStatus(book);
			request.setAttribute("loanDTO", loanDTO);
			request.getRequestDispatcher("/library/static/templates/loan/loandeleted.jsp")
					.forward(request, response);
		} catch (LoanNotFoundException | LoanDAOException e) {
			request.setAttribute("deleteAPIError", true);
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/library/static/templates/loan/loans.jsp")
					.forward(request, response);
		}
	}
}

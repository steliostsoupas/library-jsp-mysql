package gr.aueb.cf.library.controller;

import gr.aueb.cf.library.dao.*;
import gr.aueb.cf.library.dao.exceptions.LoanDAOException;
import gr.aueb.cf.library.dto.LoanUpdateDTO;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Loan;
import gr.aueb.cf.library.model.Member;
import gr.aueb.cf.library.service.*;
import gr.aueb.cf.library.service.exceptions.LoanNotFoundException;
import gr.aueb.cf.library.validator.LoanValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/library/updateloan")
public class UpdateLoanController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IBookDAO bookDAO = new BookDAOImpl();
	private final IBookService bookService = new BookServiceImpl(bookDAO);
	private final IMemberDAO memberDAO = new MemberDAOImpl();
	private final IMemberService memberService = new MemberServiceImpl(memberDAO);
	private final ILoanDAO loanDAO = new LoanDAOImpl(new BookDAOImpl(), new MemberDAOImpl());
	private final ILoanService loanService = new LoanServiceImpl(loanDAO);

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

		int id = Integer.parseInt(request.getParameter("id"));
		String bookidstr = request.getParameter("bookid");
		String memberidstr = request.getParameter("memberid");
		if (!LoanValidator.validateUpdate(bookidstr, memberidstr, request, response)) {
			return;
		}

		request.getRequestDispatcher("/library/static/templates/loan/loanupdate.jsp").forward(request, response);

		Member member = findMemberById(memberidstr, request, response);
		Book book = findBookById(bookidstr, request, response);

		if (member == null && book == null) {
			request.setAttribute("error", "Member and Book cannot be found");
			request.getRequestDispatcher("/library/static/templates/loan/loanupdate.jsp").forward(request, response);
		} else if (member == null) {
			request.setAttribute("error", "Member cannot be found");
			request.getRequestDispatcher("/library/static/templates/loan/loanupdate.jsp").forward(request, response);
		} else if (book == null) {
			request.setAttribute("error", "Book cannot be found");
			request.getRequestDispatcher("/library/static/templates/loan/loanupdate.jsp").forward(request, response);
		} else {
			LoanUpdateDTO newLoanDTO = new LoanUpdateDTO();
			newLoanDTO.setId(id);
			newLoanDTO.setBook(book);
			newLoanDTO.setMember(member);
			request.setAttribute("insertedLoan", newLoanDTO);
			try {
				Loan loan = loanService.updateLoan(newLoanDTO);
				request.setAttribute("loan", loan);
				request.getRequestDispatcher("/library/static/templates/loan/loanupdated.jsp").forward(request, response);
			} catch (LoanDAOException | LoanNotFoundException e) {
				request.setAttribute("sqlError", true);
				request.setAttribute("message", e.getMessage());
			}
		}
	}

	private Member findMemberById(String memberId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Member> members = new ArrayList<>(memberService.getAllMembers());
		try {
			for (Member member : members) {
				if (member.getId().toString().equals(memberId)) {
					return memberService.getMemberById(Integer.parseInt(memberId));
				}
			}
		} catch (NumberFormatException e) {
			request.getRequestDispatcher("/library/loan/menu").forward(request, response);
		}
		return null;
	}

	private Book findBookById(String bookId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Book> books = new ArrayList<>(bookService.getAllBooks());
		try {
			for (Book book : books) {
				if (book.getId().toString().equals(bookId)) {
					return bookService.getBookById(Integer.parseInt(bookId));
				}
			}
		} catch (NumberFormatException e) {
			request.getRequestDispatcher("/library/loan/menu").forward(request, response);
		}
		return null;
	}
}

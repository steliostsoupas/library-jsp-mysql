package gr.aueb.cf.library.controller;

import gr.aueb.cf.library.dao.BookDAOImpl;
import gr.aueb.cf.library.dao.ILoanDAO;
import gr.aueb.cf.library.dao.LoanDAOImpl;
import gr.aueb.cf.library.dao.MemberDAOImpl;
import gr.aueb.cf.library.dao.exceptions.LoanDAOException;
import gr.aueb.cf.library.dao.exceptions.MemberDAOException;
import gr.aueb.cf.library.dto.MemberDeleteDTO;
import gr.aueb.cf.library.model.Loan;
import gr.aueb.cf.library.model.Member;
import gr.aueb.cf.library.service.ILoanService;
import gr.aueb.cf.library.service.IMemberService;
import gr.aueb.cf.library.service.LoanServiceImpl;
import gr.aueb.cf.library.service.MemberServiceImpl;
import gr.aueb.cf.library.service.exceptions.MemberNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/library/deletemember")
public class DeleteMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookDAOImpl bookDAO = new BookDAOImpl();
	MemberDAOImpl memberDAO = new MemberDAOImpl();
	IMemberService memberServ = new MemberServiceImpl(memberDAO);
	ILoanDAO loanDAO = new LoanDAOImpl(bookDAO, memberDAO);
	ILoanService loanServ = new LoanServiceImpl(loanDAO);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Member member = memberServ.getMemberById(id);
		MemberDeleteDTO memberDTO = new MemberDeleteDTO
				(member.getId(), member.getFirstname(), member.getLastname());


		try {
			List<Loan> allLoans = loanServ.getAllLoans();
			boolean isMemberHaveLoan = allLoans.stream()
					.anyMatch(loan -> loan.getMember().getId() == id);
			if (isMemberHaveLoan) {
				request.setAttribute("error", "Member with ID: " + id + " is currently have a loan and cannot be deleted.");
				request.getRequestDispatcher("/library/static/templates/member/membersmenu.jsp").forward(request, response);
				return;
			}

			memberServ.deleteMember(id);
			request.setAttribute("memberDTO", memberDTO);
			request.getRequestDispatcher("/library/static/templates/member/memberdeleted.jsp")
					.forward(request, response);
		} catch (MemberNotFoundException | MemberDAOException e) {
			request.setAttribute("deleteAPIError", true);
			request.setAttribute("message", e.getMessage());
			request.getRequestDispatcher("/library/static/templates/member/members.jsp")
					.forward(request, response);
		} catch (LoanDAOException e) {
			throw new RuntimeException(e);
		}
	}
}

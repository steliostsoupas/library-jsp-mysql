package gr.aueb.cf.library.controller;

import gr.aueb.cf.library.dao.*;
import gr.aueb.cf.library.dao.exceptions.LoanDAOException;
import gr.aueb.cf.library.dao.exceptions.MemberDAOException;
import gr.aueb.cf.library.model.Loan;
import gr.aueb.cf.library.model.Member;
import gr.aueb.cf.library.service.ILoanService;
import gr.aueb.cf.library.service.IMemberService;
import gr.aueb.cf.library.service.LoanServiceImpl;
import gr.aueb.cf.library.service.MemberServiceImpl;
import gr.aueb.cf.library.service.exceptions.LoanNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SearchLoansController", value = "/library/searchloan")
public class SearchLoansController extends HttpServlet {
    private final IMemberDAO memberDAO = new MemberDAOImpl();
    private final IMemberService memberService = new MemberServiceImpl(memberDAO);
    private final ILoanDAO loanDAO = new LoanDAOImpl(new BookDAOImpl(), new MemberDAOImpl());
    private final ILoanService loanService = new LoanServiceImpl(loanDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("error", "");
        request.getRequestDispatcher("/library/menu")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("error", "");
        String memberLastName = request.getParameter("memberLastName").trim();
        try {
            List<Loan> allLoans = loanService.getAllLoans();
            if (allLoans.isEmpty()) {
                request.setAttribute("error", "No loans found");
                request.getRequestDispatcher("/library/loan/menu").forward(request, response);
            };

            List<Member> members = memberService.getMembersByLastname(memberLastName);
            if (!members.isEmpty()) {
                List<Loan> loans = getLoansByMembers(members);
                if (loans.isEmpty()) {
                    request.setAttribute("error", "No loans found by member's lastname.");
                    request.getRequestDispatcher("/library/loan/menu").forward(request, response);
                } else {
                    request.setAttribute("loans", loans);
                    request.getRequestDispatcher("/library/static/templates/loan/loans.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "No loans found by member's lastname.");
                request.getRequestDispatcher("/library/loan/menu")
                        .forward(request, response);
            }
        } catch (MemberDAOException | LoanDAOException | LoanNotFoundException e) {
            request.setAttribute("isError", true);
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/library/static/templates/menu.jsp").forward(request, response);
        }
    }

    private List<Loan> getLoansByMembers(List<Member> members) throws LoanDAOException, LoanNotFoundException {
        List<Loan> loans = new ArrayList<>();
        for (Member member : members) {
            int memberId = member.getId();
            List<Loan> memberLoans = loanService.getLoansByMemberId(memberId);
            loans.addAll(memberLoans);
        }
        return loans;
    }
}
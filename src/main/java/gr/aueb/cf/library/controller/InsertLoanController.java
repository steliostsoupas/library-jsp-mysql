package gr.aueb.cf.library.controller;

import gr.aueb.cf.library.dao.*;
import gr.aueb.cf.library.dao.exceptions.LoanDAOException;
import gr.aueb.cf.library.dto.LoanInsertDTO;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Loan;
import gr.aueb.cf.library.model.Member;
import gr.aueb.cf.library.service.*;
import gr.aueb.cf.library.validator.DateValidator;
import gr.aueb.cf.library.validator.LoanValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/library/insertloan")
public class InsertLoanController extends HttpServlet {
    private final IBookDAO bookDAO = new BookDAOImpl();
    private final IBookService bookService = new BookServiceImpl(bookDAO);
    private final IMemberDAO memberDAO = new MemberDAOImpl();
    private final IMemberService memberService = new MemberServiceImpl(memberDAO);
    private final ILoanDAO loanDAO = new LoanDAOImpl(new BookDAOImpl(), new MemberDAOImpl());
    private final ILoanService loanService = new LoanServiceImpl(loanDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/library/menu").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", "");

        List<Book> books = bookService.getAllBooks();
        request.setAttribute("books1", books);

        String bookidstr = request.getParameter("bookid");
        String memberidstr = request.getParameter("memberid");
        String loanDate = request.getParameter("loanDate");
        String returnDate = request.getParameter("returnDate");

        String dateValidationResult = DateValidator.validateLoanAndReturnDates(loanDate, returnDate);
        if (dateValidationResult != null) {
            request.setAttribute("error", dateValidationResult);
            request.getRequestDispatcher("/library/loan/menu").forward(request, response);
            return;
        }

        if (!LoanValidator.validateInput(bookidstr, memberidstr, request, response)) {
            return;
        }

        Member member = findMemberById(memberidstr, request, response);
        Book book = findBookById(bookidstr, request, response);

        if (member == null && book == null) {
            request.setAttribute("error", "Member and Book cannot be found");
        } else if (member == null) {
            request.setAttribute("error", "Member cannot be found");
        } else if (book == null) {
            request.setAttribute("error", "Book cannot be found");
        } else {
            LoanInsertDTO loanInsertDTO = new LoanInsertDTO();
            loanInsertDTO.setBook(book);
            loanInsertDTO.setMember(member);
            loanInsertDTO.setLoanDate(loanDate);
            loanInsertDTO.setReturnDate(returnDate);
            try {
                List<Loan> allLoans = loanService.getAllLoans();
                boolean isBookLoaned = allLoans.stream()
                        .anyMatch(loan -> loan.getBook().getId().equals(book.getId()));
                if (isBookLoaned) {
                    request.setAttribute("error", "Book with ID " + book.getId() + " is already loaned and cannot be borrowed again.");
                    request.getRequestDispatcher("/library/loan/menu").forward(request, response);
                    return;
                }
                Loan loan = loanService.insertLoan(loanInsertDTO);
                Book book1 = loanInsertDTO.getBook();
                book1.setLoaned(true);
                bookService.updateBookLoanedStatus(book1);
                request.setAttribute("insertedLoan", loan);
                request.getRequestDispatcher("/library/static/templates/loan/loaninserted.jsp").forward(request, response);
            } catch (LoanDAOException e) {
                request.setAttribute("sqlError", true);
                request.setAttribute("message", e.getMessage());
            }
        }
        request.getRequestDispatcher("/library/loan/menu").forward(request, response);
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
            request.getRequestDispatcher("/library/static/templates/loan/loansmenu.jsp").forward(request, response);
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

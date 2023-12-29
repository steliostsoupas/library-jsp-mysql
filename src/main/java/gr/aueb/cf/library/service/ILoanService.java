package gr.aueb.cf.library.service;

import gr.aueb.cf.library.dao.exceptions.LoanDAOException;
import gr.aueb.cf.library.dto.LoanInsertDTO;
import gr.aueb.cf.library.dto.LoanUpdateDTO;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Loan;
import gr.aueb.cf.library.model.Member;
import gr.aueb.cf.library.service.exceptions.LoanNotFoundException;

import java.util.List;

public interface ILoanService {
    Loan insertLoan(LoanInsertDTO dto) throws LoanDAOException;
    Loan updateLoan(LoanUpdateDTO dto) throws LoanDAOException, LoanNotFoundException;
    void deleteLoan(int id) throws LoanDAOException, LoanNotFoundException;
    List<Loan> getLoansByMemberLastname(List<Member> allmembers) throws LoanDAOException;
    List<Loan> getLoansByBookTitle(Book bookTitle) throws LoanDAOException;
    List<Loan> getLoansByMemberId(int id) throws LoanDAOException, LoanNotFoundException;
    Loan getLoanById(int id);
    List<Loan> getAllLoans() throws LoanDAOException;
}

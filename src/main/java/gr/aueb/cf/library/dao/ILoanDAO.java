package gr.aueb.cf.library.dao;

import gr.aueb.cf.library.dao.exceptions.LoanDAOException;
import gr.aueb.cf.library.model.Loan;

import java.util.List;

public interface ILoanDAO {
    Loan insert(Loan loan) throws LoanDAOException;
    Loan update(Loan loan) throws LoanDAOException;
    void delete(int id) throws LoanDAOException;
    List<Loan> getLoansByMemberId(int memberId) throws LoanDAOException;
    List<Loan> getAllLoans() throws LoanDAOException;
    Loan getLoanById(int id);
}

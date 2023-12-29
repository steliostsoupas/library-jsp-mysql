package gr.aueb.cf.library.service;

import gr.aueb.cf.library.dao.ILoanDAO;
import gr.aueb.cf.library.dao.exceptions.LoanDAOException;
import gr.aueb.cf.library.dto.LoanInsertDTO;
import gr.aueb.cf.library.dto.LoanUpdateDTO;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Loan;
import gr.aueb.cf.library.model.Member;
import gr.aueb.cf.library.service.exceptions.LoanNotFoundException;

import java.util.List;

public class LoanServiceImpl implements ILoanService {
    private final ILoanDAO loanDAO;

    public LoanServiceImpl(ILoanDAO loanDAO) {
        this.loanDAO = loanDAO;
    }

    @Override
    public Loan insertLoan(LoanInsertDTO dto) throws LoanDAOException {
        if (dto == null) return null;
        Loan loan;

        loan = map(dto);
        return loanDAO.insert(loan);
    }

    @Override
    public Loan updateLoan(LoanUpdateDTO dto) {
        if (dto == null) return null;
        Loan loan;

        try {
            loan = map(dto);
            if (loanDAO.getLoanById(loan.getId()) == null) {
                throw new LoanNotFoundException(loan);
            }

            return loanDAO.update(loan);
        } catch (LoanDAOException | LoanNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteLoan(int id) throws LoanDAOException, LoanNotFoundException {
        Loan loan;

        try {
            loan = loanDAO.getLoanById(id);
            if (loan == null) {
                throw new LoanNotFoundException("Loan with id " + id + " not found");
            }

            loanDAO.delete(id);
        } catch (LoanDAOException | LoanNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Loan> getLoansByBookTitle(Book bookTitle) throws LoanDAOException {
        return null;
    }

    @Override
    public List<Loan> getLoansByMemberId(int memberId) throws LoanDAOException, LoanNotFoundException {
        try {
            return loanDAO.getLoansByMemberId(memberId);
        } catch (LoanDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Loan getLoanById(int id) {
        return loanDAO.getLoanById(id);
    }

    @Override
    public List<Loan> getAllLoans() throws LoanDAOException {
        return loanDAO.getAllLoans();
    }

    @Override
    public List<Loan> getLoansByMemberLastname(List<Member> allmembers) throws LoanDAOException {
        return null;
    }

    private Loan map(LoanInsertDTO dto) {
        return new Loan(null, dto.getBook(), dto.getMember(), dto.getLoanDate(), dto.getReturnDate());
    }

    private Loan map(LoanUpdateDTO dto) {
        return new Loan(dto.getId(), dto.getBook(), dto.getMember());
    }
}

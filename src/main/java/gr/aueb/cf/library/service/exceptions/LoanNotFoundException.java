package gr.aueb.cf.library.service.exceptions;

import gr.aueb.cf.library.model.Loan;

public class LoanNotFoundException extends Exception {

    public LoanNotFoundException(Loan loan) {
        super("Loan with id " + loan.getId() + " does not exist");
    }

    public LoanNotFoundException(String s) {
        super(s);
    }
}

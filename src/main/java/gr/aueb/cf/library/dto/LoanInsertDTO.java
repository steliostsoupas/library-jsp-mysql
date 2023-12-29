package gr.aueb.cf.library.dto;

import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Member;

public class LoanInsertDTO {
    private Book book;
    private Member member;
    private String loanDate;
    private String returnDate;

    public LoanInsertDTO() {}

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}

package gr.aueb.cf.library.model;

import java.sql.Date;

public class Loan {
    private Integer id;
    private Book book;
    private Member member;
    private String loanDate;
    private String returnDate;

    public Loan() {}

    public Loan(Integer id) {
        this.id = id;
    }

    public Loan(Integer id, Book book, Member member) {
        this.id = id;
        this.book = book;
        this.member = member;
    }

    public Loan(Integer id, Book book, Member member, String loanDate, String returnDate) {
        this.id = id;
        this.book = book;
        this.member = member;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public void setLoanDate(Date loanDate) {
        this.loanDate = String.valueOf(loanDate);
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = String.valueOf(returnDate);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", book=" + book +
                ", member=" + member +
                '}';
    }
}

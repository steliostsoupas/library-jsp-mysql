package gr.aueb.cf.library.dto;

import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Member;

public class LoanUpdateDTO extends Base {
    private Book book;
    private Member member;

    public LoanUpdateDTO() {}

    public LoanUpdateDTO(int id, Book book, Member member) {
        this.setId(id);
        this.book = book;
        this.member = member;
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
}

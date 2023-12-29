package gr.aueb.cf.library.service.exceptions;

import gr.aueb.cf.library.model.Book;

public class BookNotFoundException extends Exception {

    public BookNotFoundException(Book book) {
        super("Book with id " + book.getId() + " does not exist");
    }

    public BookNotFoundException(String s) {
        super(s);
    }
}

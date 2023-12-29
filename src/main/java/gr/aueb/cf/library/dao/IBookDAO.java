package gr.aueb.cf.library.dao;

import gr.aueb.cf.library.dao.exceptions.BookDAOException;
import gr.aueb.cf.library.model.Book;

import java.util.List;

public interface IBookDAO {
    Book insert(Book book) throws BookDAOException;
    Book update(Book book) throws BookDAOException;
    void delete(int id) throws BookDAOException;
    Book getBookByTitle(String title) throws BookDAOException;
    List<Book> getBooksByTitle(String title) throws BookDAOException;
    Book getById(int id) throws BookDAOException;
    List<Book> getAllBooks();
    Boolean isLoaned(Book book);
    void updateBookLoanedStatus(Book book);
}


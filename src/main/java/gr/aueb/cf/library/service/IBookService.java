package gr.aueb.cf.library.service;

import gr.aueb.cf.library.dao.exceptions.BookDAOException;
import gr.aueb.cf.library.dto.BookInsertDTO;
import gr.aueb.cf.library.dto.BookUpdateDTO;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.service.exceptions.BookNotFoundException;

import java.util.List;

public interface IBookService {
    Book insertBook(BookInsertDTO dto) throws BookDAOException;
    Book updateBook(BookUpdateDTO dto) throws BookDAOException, BookNotFoundException;
    void deleteBook(int id) throws BookDAOException, BookNotFoundException;
    Book getBookByTitle(String title) throws BookDAOException;
    List<Book> getBooksByTitle(String title) throws BookDAOException;
    Book getBookById(int id);
    List<Book> getAllBooks();
    void updateBookLoanedStatus(Book book);
}

package gr.aueb.cf.library.service;

import gr.aueb.cf.library.dao.IBookDAO;
import gr.aueb.cf.library.dao.exceptions.BookDAOException;
import gr.aueb.cf.library.dto.BookInsertDTO;
import gr.aueb.cf.library.dto.BookUpdateDTO;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.service.exceptions.BookNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class BookServiceImpl implements IBookService {
    private final IBookDAO bookDAO;

    public BookServiceImpl(IBookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public Book insertBook(BookInsertDTO dto) throws BookDAOException {
        if (dto == null) return null;
        Book book;

        try {
            book = map(dto);
            System.out.println("Service returned book: " + book.getAuthor());
            return bookDAO.insert(book);
        } catch (BookDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Book updateBook(BookUpdateDTO dto) throws BookDAOException, BookNotFoundException {
        if (dto == null) return null;
        Book book;

        try {
            book = map(dto);
            if (bookDAO.getById(book.getId()) == null) {
                throw new BookNotFoundException(book);
            }

            return bookDAO.update(book);
        } catch (BookDAOException | BookNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void deleteBook(int id) throws BookDAOException, BookNotFoundException {
        Book book;

        try {
            book = bookDAO.getById(id);
            if (book == null) {
                throw new BookNotFoundException("Book with id " + id + " not found");
            } else if (book.isLoaned()) {
                //throw new BookDAOException("Book with id " + id + " is currently loaned and cannot be deleted.");
            }

            bookDAO.delete(id);
        } catch (BookDAOException | BookNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Book getBookByTitle(String title) throws BookDAOException {
        Book book;

        try {
            book = bookDAO.getBookByTitle(title);
            return book;
        } catch (BookDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Book> getBooksByTitle(String title) throws BookDAOException {
        List<Book> books;

        try {
            books = bookDAO.getBooksByTitle(title);
            return books;
        } catch (BookDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Book getBookById(int id) {
        Book book = null;
        try {
            book = bookDAO.getById(id);
            if (book == null) {
                throw new BookNotFoundException("Book with id " + id + " not found");
            }
        } catch (BookDAOException | BookNotFoundException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(bookDAO.getAllBooks());
    }

    @Override
    public void updateBookLoanedStatus(Book book) {
        bookDAO.updateBookLoanedStatus(book);
    }

    private Book map(BookInsertDTO dto) {
        return new Book(null,dto.getTitle(), dto.getAuthor());
    }

    private Book map(BookUpdateDTO dto) {
        return new Book(dto.getId(), dto.getTitle(), dto.getAuthor(), dto.isLoaned());
    }

}

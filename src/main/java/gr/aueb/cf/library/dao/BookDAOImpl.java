package gr.aueb.cf.library.dao;

import gr.aueb.cf.library.dao.exceptions.BookDAOException;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements IBookDAO {
    @Override
    public Book insert(Book book) throws BookDAOException {
        String sql = "INSERT INTO BOOKS (TITLE, AUTHOR) VALUES (?, ?)";

        try (Connection connection = DBUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            String title = book.getTitle();
            String author = book.getAuthor();

            ps.setString(1, title);
            ps.setString(2, author);


            DBUtil.beginTransaction();
            ps.executeUpdate();
            DBUtil.commitTransaction();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            int generatedId = 0;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }

            book.setId(generatedId);
            generatedKeys.close();

        } catch (SQLException e1) {
            DBUtil.rollbackTransaction();
            throw new BookDAOException("SQL Error in Book" + book + " insertion");
        }
        return book;
    }

    @Override
    public Book update(Book book) throws BookDAOException {
        String sql = "UPDATE BOOKS SET TITLE = ?, AUTHOR = ?, IS_LOANED = ? WHERE ID = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);) {
            int id = book.getId();
            String title = book.getTitle();
            String author = book.getAuthor();
            boolean isLoaned = book.isLoaned();

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setBoolean(3, isLoaned);
            ps.setInt(4, id);

            DBUtil.beginTransaction();
            ps.executeUpdate();
            DBUtil.commitTransaction();
            return book;

        } catch (SQLException e1) {
            //e1.printStackTrace();
            DBUtil.rollbackTransaction();
            throw new BookDAOException("SQL Error in Book " + book + " insertion");
        }
    }

    @Override
    public void delete(int id) throws BookDAOException {
        String sql = "DELETE FROM BOOKS WHERE ID = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            DBUtil.beginTransaction();
            ps.executeUpdate();
            DBUtil.commitTransaction();

        } catch (SQLException e) {
            //e.printStackTrace();
            DBUtil.rollbackTransaction();
            throw new BookDAOException("SQL Error in Book with id: " + id + " deletion");
        }
    }

    @Override
    public Book getBookByTitle(String title) throws BookDAOException {
        String sql = "SELECT * FROM BOOKS WHERE TITLE LIKE ?";
        Book book = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs;
            ps.setString(1, title + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                book = new Book(rs.getInt("ID"), rs.getString("TITLE"), rs.getString("AUTHOR"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return book;
    }

    @Override
    public List<Book> getBooksByTitle(String title) throws BookDAOException {
        String sql = "SELECT * FROM BOOKS WHERE TITLE LIKE ?";
        List<Book> books = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ) {
            ResultSet rs;
            ps.setString(1, title + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String bookTitle = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                boolean isLoaned = rs.getBoolean("IS_LOANED");

                Book book = new Book(id, bookTitle, author);
                book.setLoaned(isLoaned);
                books.add(book);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return books;
    }

    @Override
    public Book getById(int id) {
        String sql = "SELECT * FROM BOOKS WHERE ID = ?";
        Book book = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs;
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("ID");
                String title = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                boolean isLoaned = rs.getBoolean("IS_LOANED");

                book = new Book(bookId, title, author, isLoaned);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, title, author, IS_LOANED FROM Books";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                boolean isLoaned = rs.getBoolean("IS_LOANED");

                Book book = new Book(id, title, author);
                book.setLoaned(isLoaned);
                books.add(book);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return books;
    }

    @Override
    public Boolean isLoaned(Book book) {
        return book.isLoaned();
    }

    @Override
    public void updateBookLoanedStatus(Book book) {
        String updateStatusSQL = "UPDATE BOOKS SET is_Loaned = ? WHERE ID = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateStatusSQL)) {
            preparedStatement.setBoolean(1, book.isLoaned());
            preparedStatement.setInt(2, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

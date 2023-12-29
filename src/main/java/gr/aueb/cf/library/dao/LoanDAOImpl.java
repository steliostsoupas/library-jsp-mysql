package gr.aueb.cf.library.dao;

import gr.aueb.cf.library.dao.exceptions.LoanDAOException;
import gr.aueb.cf.library.model.Book;
import gr.aueb.cf.library.model.Loan;
import gr.aueb.cf.library.model.Member;
import gr.aueb.cf.library.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanDAOImpl implements ILoanDAO {
    private final BookDAOImpl bookDAO;
    private final MemberDAOImpl memberDAO;

    public LoanDAOImpl(BookDAOImpl bookDAO, MemberDAOImpl memberDAO) {
        this.bookDAO = bookDAO;
        this.memberDAO = memberDAO;
    }

    @Override
    public Loan insert(Loan loan) throws LoanDAOException {
        String sql = "INSERT INTO LOANS (BOOKID, MEMBERID, loanDate, returnDate) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            int bookId = loan.getBook().getId();
            int memberId = loan.getMember().getId();
            String loanDate = loan.getLoanDate();
            String returnDate = loan.getReturnDate();

            ps.setInt(1, bookId);
            ps.setInt(2, memberId);
            ps.setString(3, loanDate);
            ps.setString(4, returnDate);

            DBUtil.beginTransaction();
            ps.executeUpdate();
            DBUtil.commitTransaction();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            int generatedId = 0;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }

            loan.setId(generatedId);
            generatedKeys.close();

        } catch (SQLException e1) {
            DBUtil.rollbackTransaction();
            throw new LoanDAOException("SQL Error in Loan insertion: " + e1.getMessage());
        }
        return loan;
    }

    @Override
    public Loan update(Loan loan) throws LoanDAOException {
        String sql = "UPDATE LOANS SET BOOKID = ?, MEMBERID = ? WHERE ID = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            int id = loan.getId();
            int bookId = loan.getBook().getId();
            int memberId = loan.getMember().getId();

            ps.setInt(1, bookId);
            ps.setInt(2, memberId);
            ps.setInt(3, id);

            DBUtil.beginTransaction();
            ps.executeUpdate();
            DBUtil.commitTransaction();
            return loan;

        } catch (SQLException e1) {
            DBUtil.rollbackTransaction();
            throw new LoanDAOException("SQL Error in Loan update: " + e1.getMessage());
        }
    }

    @Override
    public void delete(int id) throws LoanDAOException {
        String sql = "DELETE FROM LOANS WHERE ID = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            DBUtil.beginTransaction();
            ps.executeUpdate();
            DBUtil.commitTransaction();

        } catch (SQLException e) {
            DBUtil.rollbackTransaction();
            throw new LoanDAOException("SQL Error in Loan deletion: " + e.getMessage());
        }
    }

    @Override
    public List<Loan> getLoansByMemberId(int memberId) throws LoanDAOException {
        String sql = "SELECT * FROM LOANS WHERE MEMBERID = ?";
        List<Loan> loans = new ArrayList<>();
        Loan loan;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs;
            ps.setInt(1, memberId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int loanId = rs.getInt("ID");
                int bookId = rs.getInt("BOOKID");
                int fetchedMemberId = rs.getInt("MEMBERID");
                String loanDateSQL = rs.getString("LOANDATE");
                String returnDateSQL = rs.getString("RETURNDATE");

                Book book = bookDAO.getById(bookId);
                Member member = memberDAO.getMemberById(fetchedMemberId);

                loan = new Loan(loanId, book, member, loanDateSQL, returnDateSQL);
                loans.add(loan);
            }
        } catch (SQLException e1) {
            throw new LoanDAOException("SQL Error in fetching Loans by Member ID: " + e1.getMessage());
        }
        return loans;
    }

    @Override
    public List<Loan> getAllLoans() throws LoanDAOException {
        String sql = "SELECT * FROM LOANS";
        List<Loan> loans = new ArrayList<>();
        Loan loan;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {
                int loanId = rs.getInt("ID");
                int bookId = rs.getInt("BOOKID");
                int fetchedMemberId = rs.getInt("MEMBERID");
                String loanDateSQL = rs.getString("LOANDATE");
                String returnDateSQL = rs.getString("RETURNDATE");

                Book book = bookDAO.getById(bookId);
                Member member = memberDAO.getMemberById(fetchedMemberId);

                loan = new Loan(loanId, book, member, loanDateSQL, returnDateSQL);
                loans.add(loan);
            }
        } catch (SQLException e1) {
            throw new LoanDAOException("SQL Error in fetching Loans: " + e1.getMessage());
        }
        return loans;
    }

    @Override
    public Loan getLoanById(int id) {
        String sql = "SELECT * FROM LOANS WHERE ID = ?";
        Loan loan = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs;
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                int loanId = rs.getInt("ID");
                int fetchedBookId = rs.getInt("BOOKID");
                int memberId = rs.getInt("MEMBERID");
                Book book = bookDAO.getById(fetchedBookId);
                Member member = memberDAO.getMemberById(memberId);
                String loanDate;
                if (rs.getString("LOANDATE") != null) {
                    loanDate = rs.getString("LOANDATE");
                } else {
                    loanDate = null;
                }
                String returnDate;
                String sqlReturnDate = rs.getString("RETURNDATE");
                if (sqlReturnDate != null) {
                    returnDate = sqlReturnDate;
                } else {
                    returnDate = null;
                }
                loan = new Loan(loanId, book, member, loanDate, returnDate);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return loan;
    }
}

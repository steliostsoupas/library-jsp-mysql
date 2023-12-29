package gr.aueb.cf.library.dao;

import gr.aueb.cf.library.dao.exceptions.MemberDAOException;
import gr.aueb.cf.library.model.Member;
import gr.aueb.cf.library.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAOImpl implements IMemberDAO {
    @Override
    public Member insert(Member member) throws MemberDAOException {
        String sql = "INSERT INTO MEMBERS (FIRSTNAME, LASTNAME) VALUES (?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            String firstname = member.getFirstname();
            String lastname = member.getLastname();

            ps.setString(1, firstname);
            ps.setString(2, lastname);

            DBUtil.beginTransaction();
            ps.executeUpdate();
            DBUtil.commitTransaction();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            int generatedId = 0;
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }

            member.setId(generatedId);
            generatedKeys.close();

        } catch (SQLException e1) {
            DBUtil.rollbackTransaction();
            throw new MemberDAOException("SQL Error in Member " + member + " insertion");
        }
        return member;
    }

    @Override
    public Member update(Member member) throws MemberDAOException {
        String sql = "UPDATE MEMBERS SET FIRSTNAME = ?, LASTNAME = ? WHERE ID = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            int id = member.getId();
            String firstname = member.getFirstname();
            String lastname = member.getLastname();

            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setInt(3, id);

            DBUtil.beginTransaction();
            ps.executeUpdate();
            DBUtil.commitTransaction();
            return member;

        } catch (SQLException e1) {
            DBUtil.rollbackTransaction();
            throw new MemberDAOException("SQL Error in Member " + member + " update");
        }
    }

    @Override
    public void delete(int id) throws MemberDAOException {
        String sql = "DELETE FROM MEMBERS WHERE ID = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            DBUtil.beginTransaction();
            ps.executeUpdate();
            DBUtil.commitTransaction();

        } catch (SQLException e) {
            DBUtil.rollbackTransaction();
            throw new MemberDAOException("SQL Error in Member with id: " + id + " deletion");
        }
    }

    @Override
    public Member getMemberByLastname(String lastname) throws MemberDAOException {
        String sql = "SELECT * FROM MEMBERS WHERE LASTNAME LIKE ?";
        Member member = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs;
            ps.setString(1, lastname + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                member = new Member(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return member;
    }

    @Override
    public List<Member> getByLastname(String lastname) throws MemberDAOException {
        String sql = "SELECT * FROM MEMBERS WHERE LASTNAME LIKE ?";
        List<Member> members = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs;
            ps.setString(1, lastname + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                Member member = new Member(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"));
                members.add(member);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return members;
    }

    @Override
    public Member getMemberById(int id) {
        String sql = "SELECT * FROM MEMBERS WHERE ID = ?";
        Member member = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs;
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                member = new Member(rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return member;
    }

    @Override
    public List<Member> getAllMembers()  {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT id, firstname, lastname FROM Members";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");

                Member member = new Member(id, firstname, lastname);
                members.add(member);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return members;
    }
}

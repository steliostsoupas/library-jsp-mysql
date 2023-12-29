package gr.aueb.cf.library.dao;

import gr.aueb.cf.library.dao.exceptions.MemberDAOException;
import gr.aueb.cf.library.model.Member;

import java.util.List;

public interface IMemberDAO {
    Member insert(Member member) throws MemberDAOException;
    Member update(Member member) throws MemberDAOException;
    void delete(int id) throws MemberDAOException;
    Member getMemberByLastname(String lastname) throws MemberDAOException;
    List<Member> getByLastname(String lastname) throws MemberDAOException;
    Member getMemberById(int id) throws MemberDAOException;
    List<Member> getAllMembers();
}

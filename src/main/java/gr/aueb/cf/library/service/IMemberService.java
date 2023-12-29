package gr.aueb.cf.library.service;

import gr.aueb.cf.library.dao.exceptions.MemberDAOException;
import gr.aueb.cf.library.dto.MemberInsertDTO;
import gr.aueb.cf.library.dto.MemberUpdateDTO;
import gr.aueb.cf.library.model.Member;
import gr.aueb.cf.library.service.exceptions.MemberNotFoundException;

import java.util.List;

public interface IMemberService {
    Member insertMember(MemberInsertDTO dto) throws MemberDAOException;
    Member updateMember(MemberUpdateDTO dto) throws MemberDAOException, MemberNotFoundException;
    void deleteMember(int id) throws MemberDAOException, MemberNotFoundException;
    Member getMemberByLastname(String lastname) throws MemberDAOException;
    List<Member> getMembersByLastname(String lastname) throws MemberDAOException;
    Member getMemberById(int id);
    List<Member> getAllMembers();
}

package gr.aueb.cf.library.service;

import gr.aueb.cf.library.dao.IMemberDAO;
import gr.aueb.cf.library.dao.exceptions.MemberDAOException;
import gr.aueb.cf.library.dto.MemberInsertDTO;
import gr.aueb.cf.library.dto.MemberUpdateDTO;
import gr.aueb.cf.library.model.Member;
import gr.aueb.cf.library.service.exceptions.MemberNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class MemberServiceImpl implements IMemberService {
    private final IMemberDAO memberDAO;

    public MemberServiceImpl(IMemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    public Member insertMember(MemberInsertDTO dto) throws MemberDAOException {
        if (dto == null) return null;
        Member member;

        try {
            member = map(dto);
            System.out.println("Service returned member: " + member.getLastname());
            return memberDAO.insert(member);
        } catch (MemberDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Member updateMember(MemberUpdateDTO dto) throws MemberDAOException, MemberNotFoundException {
        if (dto == null) return null;
        Member member;

        try {
            member = map(dto);
            if (memberDAO.getMemberById(member.getId()) == null) {
                throw new MemberNotFoundException(member);
            }

            return memberDAO.update(member);
        } catch (MemberDAOException | MemberNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void deleteMember(int id) throws MemberDAOException, MemberNotFoundException {
        Member member;

        try {
            member = memberDAO.getMemberById(id);
            if (member == null) {
                throw new MemberNotFoundException("Member with id " + id + " not found");
            }

            memberDAO.delete(id);
        } catch (MemberDAOException | MemberNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Member getMemberByLastname(String lastname) throws MemberDAOException {
        Member member;

        try {
            member = memberDAO.getMemberByLastname(lastname);
            return member;
        } catch (MemberDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Member> getMembersByLastname(String lastname) throws MemberDAOException {
        List<Member> members;

        try {
            members = memberDAO.getByLastname(lastname);
            return members;
        } catch (MemberDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Member getMemberById(int id) {
        Member member = null;
        try {
            member = memberDAO.getMemberById(id);
            if (member == null) {
                throw new MemberNotFoundException("Member with id " + id + " not found");
            }
        } catch (MemberDAOException | MemberNotFoundException e) {
            e.printStackTrace();
        }
        return member;
    }

    @Override
    public List<Member> getAllMembers() {
        return new ArrayList<>(memberDAO.getAllMembers());
    }

    private Member map(MemberInsertDTO dto) {
        return new Member(null, dto.getFirstname(), dto.getLastname());
    }

    private Member map(MemberUpdateDTO dto) {
        return new Member(dto.getId(), dto.getFirstname(), dto.getLastname());
    }
}

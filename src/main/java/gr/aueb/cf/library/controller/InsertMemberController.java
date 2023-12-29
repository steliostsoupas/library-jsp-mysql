package gr.aueb.cf.library.controller;

import gr.aueb.cf.library.dao.IMemberDAO;
import gr.aueb.cf.library.dao.MemberDAOImpl;
import gr.aueb.cf.library.dao.exceptions.MemberDAOException;
import gr.aueb.cf.library.dto.MemberInsertDTO;
import gr.aueb.cf.library.model.Member;
import gr.aueb.cf.library.service.IMemberService;
import gr.aueb.cf.library.service.MemberServiceImpl;
import gr.aueb.cf.library.validator.MemberValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/library/insertmember")
public class InsertMemberController extends HttpServlet {
    private final IMemberDAO memberDAO = new MemberDAOImpl();
    private final IMemberService memberService = new MemberServiceImpl(memberDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/library/menu").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", "");

        String firstname = request.getParameter("firstname").trim();
        String lastname = request.getParameter("lastname").trim();

        MemberInsertDTO memberInsertDTO = new MemberInsertDTO();
        memberInsertDTO.setFirstname(firstname);
        memberInsertDTO.setLastname(lastname);

        if (!MemberValidator.validateInput(firstname, lastname, request, response)) {
            return;
        }

        try {
            Member member = memberService.insertMember(memberInsertDTO);
            request.setAttribute("insertedMember", member);
            request.getRequestDispatcher("/library/static/templates/member/memberinserted.jsp").forward(request, response);
        } catch (MemberDAOException e) {
            request.setAttribute("sqlError", true);
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("/library/menu").forward(request, response);

        }
    }
}

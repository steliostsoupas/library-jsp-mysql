package gr.aueb.cf.library.controller;

import gr.aueb.cf.library.dao.IMemberDAO;
import gr.aueb.cf.library.dao.MemberDAOImpl;
import gr.aueb.cf.library.dao.exceptions.MemberDAOException;
import gr.aueb.cf.library.model.Member;
import gr.aueb.cf.library.service.IMemberService;
import gr.aueb.cf.library.service.MemberServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchMembersController", value = "/library/searchmember")
public class  SearchMembersController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    IMemberDAO memberDAO = new MemberDAOImpl();
    IMemberService memberService = new MemberServiceImpl(memberDAO);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("isError", false);
        request.setAttribute("error", "");
        request.setAttribute("membersNotFound", false);
        request.getRequestDispatcher("/library/menu")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lastname = request.getParameter("lastname").trim();
        String message;
        try {
            List<Member> members = memberService.getMembersByLastname(lastname);
            if (members.size() == 0) {
                request.setAttribute("membersNotFound", true);
                request.getRequestDispatcher("/library/static/templates/member/membersmenu.jsp")
                        .forward(request, response);
            }

            request.setAttribute("members", members);
            request.getRequestDispatcher("/library/static/templates/member/members.jsp").forward(request, response);


        } catch (MemberDAOException e) {
            message = e.getMessage();
            request.setAttribute("isError", true);
            request.setAttribute("errorMessage", message);
            request.getRequestDispatcher("/library/static/templates/menu.jsp").forward(request, response);
        }
    }
}

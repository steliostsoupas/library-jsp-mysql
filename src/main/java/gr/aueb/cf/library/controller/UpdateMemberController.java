package gr.aueb.cf.library.controller;

import gr.aueb.cf.library.dao.IMemberDAO;
import gr.aueb.cf.library.dao.MemberDAOImpl;
import gr.aueb.cf.library.dao.exceptions.MemberDAOException;
import gr.aueb.cf.library.dto.MemberUpdateDTO;
import gr.aueb.cf.library.model.Member;
import gr.aueb.cf.library.service.IMemberService;
import gr.aueb.cf.library.service.MemberServiceImpl;
import gr.aueb.cf.library.service.exceptions.MemberNotFoundException;
import gr.aueb.cf.library.validator.MemberValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/library/updatemember")
public class UpdateMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final IMemberDAO memberDAO = new MemberDAOImpl();
	private final IMemberService memberServ = new MemberServiceImpl(memberDAO);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/library/static/templates/member/memberupdate.jsp")
				.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");

		if (!MemberValidator.validateUpdate(firstname, lastname, request, response)) {
			return;
		}

		MemberUpdateDTO newMemberDTO = new MemberUpdateDTO();
		newMemberDTO.setId(id);
		newMemberDTO.setFirstname(firstname);
		newMemberDTO.setLastname(lastname);
		request.setAttribute("insertedMember", newMemberDTO);

		try {
			Member member = memberServ.updateMember(newMemberDTO);
			request.setAttribute("member", member);
			request.getRequestDispatcher("/library/static/templates/member/memberupdated.jsp")
					.forward(request, response);
		} catch (MemberNotFoundException | MemberDAOException e) {
			String message = e.getMessage();
			request.setAttribute("message", message);
			request.getRequestDispatcher("/library/static/templates/member/memberupdated.jsp")
					.forward(request, response);
		}
	}
}

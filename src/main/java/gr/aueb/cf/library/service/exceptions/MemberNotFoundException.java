package gr.aueb.cf.library.service.exceptions;

import gr.aueb.cf.library.model.Member;

public class MemberNotFoundException extends Exception {

    public MemberNotFoundException(Member member) {
        super("Member with id " + member.getId() + " does not exist");
    }

    public MemberNotFoundException(String s) {
        super(s);
    }
}

package com.todrepus.enrollmentsys.config.auth.dto;

import com.todrepus.enrollmentsys.domain.member.Member;
import lombok.Getter;

@Getter
public class SessionUser {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Member member){
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}

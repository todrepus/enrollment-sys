package com.todrepus.enrollmentsys.web.auth.dto;

import com.todrepus.enrollmentsys.domain.member.Member;
import com.todrepus.enrollmentsys.domain.member.Role;
import lombok.Data;

@Data
public class SimpleMember {
    private Long id;
    private Role role;

    public SimpleMember(Member member){
        if (member == null)
            return;
        id = member.getId();
        role = member.getRole();
    }

}

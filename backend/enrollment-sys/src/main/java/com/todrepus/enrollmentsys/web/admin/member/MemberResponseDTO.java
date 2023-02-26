package com.todrepus.enrollmentsys.web.admin.member;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.courseEnroll.CourseEnroll;
import com.todrepus.enrollmentsys.domain.member.Member;
import com.todrepus.enrollmentsys.domain.member.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class MemberResponseDTO {
    private String userId;
    private String name;
    private String password;
    private String phoneNumber;
    private Role role;

    public MemberResponseDTO(Member member){
        this.userId = member.getUserId();
        this.name = member.getName();
        this.password = member.getPassword();
        this.phoneNumber = member.getPhoneNumber();
        this.role = member.getRole();
    }
}

package com.todrepus.enrollmentsys.web.admin;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.member.Member;
import com.todrepus.enrollmentsys.domain.member.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class MemberResponseDTO {
    private Long id;
    private String userId;
    private String name;
    private String password;
    private Role role;
    private List<Course> courseList;

    public MemberResponseDTO(Member member){
        this.id = member.getId();
        this.userId = member.getUserId();
        this.name = member.getName();
        this.password = member.getPassword();
        this.role = member.getRole();
        this.courseList = member.getCourseSet().stream().toList();
    }
}

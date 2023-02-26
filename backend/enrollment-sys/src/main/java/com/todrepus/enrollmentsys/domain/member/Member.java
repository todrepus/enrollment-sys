package com.todrepus.enrollmentsys.domain.member;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.todrepus.enrollmentsys.domain.BaseTimeEntity;
import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.courseEnroll.CourseEnroll;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity(name="users")
@Inheritance(strategy = InheritanceType.JOINED)
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_id")
    private String userId;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(name="phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Member(String userId, String name, String password, String phoneNumber, Role role){
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Member(Member member){
        this.userId = member.userId;
        this.name = member.name;
        this.password = member.password;
        this.phoneNumber = member.phoneNumber;
        this.role = member.role;
    }

    public Member update(String name, String password, Role role){
        this.name = name;
        this.password = password;
        this.role = role;
        return this;
    }


    public String getRoleKey(){
        return this.role.getKey();
    }
}

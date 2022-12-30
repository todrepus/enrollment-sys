package com.todrepus.enrollmentsys.domain.member;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.todrepus.enrollmentsys.domain.BaseTimeEntity;
import com.todrepus.enrollmentsys.domain.course.Course;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@ToString
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="memberId")
    private Long id;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy="id")
    private Set<Course> courseSet = new HashSet<>();

    @Builder
    public Member(String userId, String name, String password, Role role){
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Member update(String name, String password, Role role, Set<Course> courseSet){
        this.name = name;
        this.password = password;
        this.role = role;
        this.courseSet = courseSet;
        return this;
    }

    public boolean addCourse(Course course){
        if (courseSet.contains(course))
            return false;
        courseSet.add(course);
        return true;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}

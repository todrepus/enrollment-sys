package com.todrepus.enrollmentsys.domain.member;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.todrepus.enrollmentsys.domain.BaseTimeEntity;
import com.todrepus.enrollmentsys.domain.course.Course;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="memberId")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy="id")
    private List<Course> courseList = new ArrayList<>();

    @Builder
    public Member(String name, String email, String picture, Role role){
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public Member update(String name, String picture){
        this.name= name;
        this.picture = picture;

        return this;
    }

    public boolean addCourse(Course course){
        if (courseList.contains(course))
            return false;
        courseList.add(course);
        return true;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}

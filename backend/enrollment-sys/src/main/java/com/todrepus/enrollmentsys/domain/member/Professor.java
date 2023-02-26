package com.todrepus.enrollmentsys.domain.member;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.courseEnroll.CourseEnroll;
import com.todrepus.enrollmentsys.domain.department.Department;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name="professors")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "jsonId")
@NoArgsConstructor
@PrimaryKeyJoinColumn(name="professor_id")
public class Professor extends Member{
    @ManyToOne
    @JoinColumn(name="department_id")
    Department department;

    @Column
    Integer year;

    @OneToMany(mappedBy = "professor")
    private Set<Course> courseSet = new HashSet<>();

    public Professor(Member member){
        super(member);
    }
}

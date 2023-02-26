package com.todrepus.enrollmentsys.domain.member;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.courseEnroll.CourseEnroll;
import com.todrepus.enrollmentsys.domain.department.Department;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity(name="students")
@NoArgsConstructor
@PrimaryKeyJoinColumn(name="student_id")
public class Student extends Member{
    @Column
    Long semester;

    @Column(name="state")
    @Enumerated(EnumType.STRING)
    StudentState state;

    @ManyToOne
    @JoinColumn(name="department_id")
    Department department;

    @OneToMany(mappedBy = "student")
    private Set<CourseEnroll> courseEnrollSet = new HashSet<>();


    public Student(Member member){
        super(member);
        this.state = StudentState.ENROLLED;
    }

}

package com.todrepus.enrollmentsys.domain.courseEnroll;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.member.Student;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name="course_enrolls")
public class CourseEnroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne
    @JoinColumn(name="student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_date", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdDate;

    @Builder
    public CourseEnroll(Student student, Course course){
        this.student = student;
        this.course = course;
    }

}

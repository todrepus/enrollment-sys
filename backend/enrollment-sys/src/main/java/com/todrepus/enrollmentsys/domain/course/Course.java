package com.todrepus.enrollmentsys.domain.course;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.todrepus.enrollmentsys.domain.BaseTimeEntity;
import com.todrepus.enrollmentsys.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Course extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="courseId")
    Long id;

    @Column(nullable = false)
    String room; // 강의실

    // 수많은 강의는 하나의 교수에 분류된다.
    @ManyToOne
    @JoinColumn(name = "professorId")
    Member professor; // 교수

    @Column(nullable = false)
    String department; // 학과(부서)

    @Column(nullable = false)
    Integer enrollNum; // 수강신청한 인원수

    @Column(nullable = false)
    Integer maxNum; // 최대수강인원 (수강정원)

    @Convert(converter= CourseDateListConverter.class)
    private List<CourseDate> courseDateList;

    @Builder
    public Course(String room, Member professor, String department, Integer enrollNum, Integer maxNum, List<CourseDate> courseDateList){
        this.room = room;
        this.professor = professor;
        this.department = department;
        this.enrollNum = enrollNum;
        this.maxNum = maxNum;
        this.courseDateList = courseDateList;
    }

    Course update(String room, Member professor, String department, Integer enrollNum, Integer maxNum, List<CourseDate> courseDateList){
        this.room = room;
        this.professor = professor;
        this.department = department;
        this.enrollNum = enrollNum;
        this.maxNum = maxNum;
        this.courseDateList = courseDateList;
        return this;
    }

    boolean updateCourseDate(Integer idx, CourseDate courseDate){
        if (idx < 0 || idx >= courseDateList.size())
            return false;
        if (courseDate == null)
            return false;
        courseDateList.set(idx, courseDate);
        return true;
    }

}


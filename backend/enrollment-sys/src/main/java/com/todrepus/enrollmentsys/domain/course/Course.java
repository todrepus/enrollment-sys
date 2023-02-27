package com.todrepus.enrollmentsys.domain.course;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.todrepus.enrollmentsys.domain.department.Department;
import com.todrepus.enrollmentsys.domain.member.Professor;
import com.todrepus.enrollmentsys.domain.room.Room;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity(name="courses")
public class Course{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_id")
    Long id;
    
    @Column(nullable = false)
    String name; // 강의명
    @ManyToOne
    @JoinColumn(name = "room_id")
    Room room; // 강의실

    @ManyToOne
    @JoinColumn(name = "department_id")
    Department department; // 학과

    // 수많은 강의는 하나의 교수에 분류된다.
    @ManyToOne
    @JoinColumn(name = "professor_id")
    Professor professor; // 교수

    @Column(nullable = false, name="enroll_num")
    Integer enrollNum; // 수강신청한 인원수

    @Column(nullable = false, name="max_num")
    Integer maxNum; // 최대수강인원 (수강정원)

    @OneToMany(mappedBy = "course")
    private List<CourseSchedule> courseScheduleList;

    @Builder
    public Course(String name, Room room, Professor professor, Department department,
                  Integer enrollNum, Integer maxNum, List<CourseSchedule> courseScheduleList){
        this.name = name;
        this.room = room;
        this.professor = professor;
        this.department = department;
        this.enrollNum = enrollNum;
        this.maxNum = maxNum;
        this.courseScheduleList = courseScheduleList;
    }

    Course update(Room room, Department department, Integer enrollNum, Integer maxNum, List<CourseSchedule> courseScheduleList){
        this.room = room;
        //this.professor = professor;
        this.department = department;
        this.enrollNum = enrollNum;
        this.maxNum = maxNum;
        this.courseScheduleList = courseScheduleList;
        return this;
    }

    boolean updateCourseDate(Integer idx, CourseSchedule courseSchedule){
        if (idx < 0 || idx >= courseScheduleList.size())
            return false;
        if (courseSchedule == null)
            return false;
        courseScheduleList.set(idx, courseSchedule);
        return true;
    }

    public void addSchedule(CourseSchedule schedule){
        if (this.courseScheduleList == null)
            this.courseScheduleList = new ArrayList<>();
        this.getCourseScheduleList().add(schedule);
        schedule.setCourse(this);
    }

}


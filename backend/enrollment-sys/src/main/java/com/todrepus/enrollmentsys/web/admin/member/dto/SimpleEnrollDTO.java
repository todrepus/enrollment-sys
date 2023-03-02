package com.todrepus.enrollmentsys.web.admin.member.dto;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.web.admin.course.dto.CourseResponseDTO;
import lombok.Data;

@Data
public class SimpleEnrollDTO {
    private Long id;
    private CourseResponseDTO course;

    public SimpleEnrollDTO(Long id, Course course) {
        this.id = id;
        this.course = new CourseResponseDTO(course);
    }
}

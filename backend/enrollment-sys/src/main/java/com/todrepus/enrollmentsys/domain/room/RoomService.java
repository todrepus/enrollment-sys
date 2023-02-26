package com.todrepus.enrollmentsys.domain.room;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.course.CourseRepository;
import com.todrepus.enrollmentsys.domain.course.CourseSchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public Room findRoom(Long id){
        return roomRepository.findById(id).orElseThrow(
                ()-> new NoSuchElementException("해당 id를 가진 room이 없습니다.")
        );
    }

    public Room deleteRoom(Room room){
        List<Course> courseList =  room.getCourseList();
        for (Course course : courseList) {
            course.setRoom(null);
        }

        roomRepository.delete(room);
        room.getCourseList().clear();
        return room;
    }
}

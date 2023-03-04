package com.todrepus.enrollmentsys.domain.room;

import com.todrepus.enrollmentsys.domain.course.Course;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public List<Room> findRoomListStartWith(String words, int recommend_num){
        return roomRepository.findAll().stream()
                .filter((e -> e.getLocation().startsWith(words)))
                .limit(recommend_num)
                .toList();
    }

    public Page<Room> findRoomsOnPage(int page, int size){
        return roomRepository.findAll(PageRequest.of(page, size));
    }
}

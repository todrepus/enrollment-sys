package com.todrepus.enrollmentsys.domain.room;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.todrepus.enrollmentsys.domain.course.Course;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "jsonId")
@Entity(name="rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    Long id;

    @Column(name="location")
    String location; // 위치

    @Column(name="ho")
    String ho; //  몇호?

    @OneToMany(mappedBy="room")
    List<Course> courseList;

    @Builder
    public Room(String location, String ho){
        this.location = location;
        this.ho = ho;
    }
}

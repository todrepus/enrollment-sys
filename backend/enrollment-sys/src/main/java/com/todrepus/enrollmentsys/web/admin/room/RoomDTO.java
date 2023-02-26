package com.todrepus.enrollmentsys.web.admin.room;

import com.todrepus.enrollmentsys.domain.room.Room;
import lombok.Data;

@Data
public class RoomDTO {
    private Long id;
    private String location;
    private String ho;

    public RoomDTO(Room room){
        this.id = room.getId();
        this.location = room.getLocation();
        this.ho = room.getHo();
    }
}

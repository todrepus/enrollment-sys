package com.todrepus.enrollmentsys.web.admin.room.dto;

import com.todrepus.enrollmentsys.domain.room.Room;
import lombok.Data;

@Data
public class RoomResponseDTO {
    private Long id;
    private String location;
    private String ho;

    public RoomResponseDTO(Room room){
        this.id = room.getId();
        this.location = room.getLocation();
        this.ho = room.getHo();
    }
}

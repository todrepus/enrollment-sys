package com.todrepus.enrollmentsys.web.room.dto;

import com.todrepus.enrollmentsys.domain.room.Room;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RoomResponseDTO {
    private Long id;
    private String location;
    private String ho;
    private String name;

    public RoomResponseDTO(Room room){
        if (room == null)
            return;
        this.id = room.getId();
        this.location = room.getLocation();
        this.ho = room.getHo();
        this.name = this.location + " " + this.ho;
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Room room))
            return false;
        return this.id.equals(room.getId()) && this.location.equals(room.getLocation())
                && this.ho.equals(room.getHo());
    }
}

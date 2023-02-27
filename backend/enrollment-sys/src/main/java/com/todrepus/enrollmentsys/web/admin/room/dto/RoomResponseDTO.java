package com.todrepus.enrollmentsys.web.admin.room.dto;

import com.todrepus.enrollmentsys.domain.room.Room;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
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

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Room room))
            return false;
        return this.id.equals(room.getId()) && this.location.equals(room.getLocation())
                && this.ho.equals(room.getHo());
    }
}

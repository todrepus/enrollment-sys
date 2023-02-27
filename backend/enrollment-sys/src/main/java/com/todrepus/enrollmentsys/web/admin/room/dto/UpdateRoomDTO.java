package com.todrepus.enrollmentsys.web.admin.room.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateRoomDTO {
    @NotEmpty
    String location;
    @NotEmpty
    String ho;
}

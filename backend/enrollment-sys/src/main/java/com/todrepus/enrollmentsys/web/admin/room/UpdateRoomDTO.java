package com.todrepus.enrollmentsys.web.admin.room;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateRoomDTO {
    @NotEmpty
    String location;
    @NotEmpty
    String ho;
}

package com.todrepus.enrollmentsys.web.admin.room;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddRoomDTO {
    @NotEmpty
    String location;
    @NotEmpty
    String ho;
}

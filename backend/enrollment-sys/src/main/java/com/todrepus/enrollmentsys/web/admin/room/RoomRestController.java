package com.todrepus.enrollmentsys.web.admin.room;

import com.todrepus.enrollmentsys.domain.room.Room;
import com.todrepus.enrollmentsys.domain.room.RoomRepository;
import com.todrepus.enrollmentsys.domain.room.RoomService;
import com.todrepus.enrollmentsys.web.RestResponseDTO;
import com.todrepus.enrollmentsys.web.RestState;
import com.todrepus.enrollmentsys.web.admin.course.CourseResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/rooms")
public class RoomRestController {
    private final RoomRepository roomRepository;
    private final RoomService roomService;

    @GetMapping("")
    public RestResponseDTO getRooms(){
        List<RoomDTO> rooms = roomRepository.findAll().stream()
                .map(RoomDTO::new)
                .toList();

        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .message("모든 강의실 조회")
                .state(RestState.OK)
                .build();
        responseDTO.addParam("data", rooms);
        return responseDTO;
    }

    @GetMapping("/{roomId}")
    public RestResponseDTO getRoomById(@PathVariable Long roomId){
        Room room = roomService.findRoom(roomId);
        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .message("강의실 조회")
                .state(RestState.OK)
                .build();
        responseDTO.addParam("room", new RoomDTO(room));
        return responseDTO;
    }

    @PostMapping("/add")
    public RestResponseDTO addRoom(@Validated @RequestBody AddRoomDTO addRoomDTO,
                                   BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", addRoomDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.builder()
                    .state(RestState.FAIL)
                    .build();
        }

        Room room = Room.builder()
                .location(addRoomDTO.getLocation())
                .ho(addRoomDTO.getHo())
                .build();

        roomRepository.save(room);

        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .message("강의실 추가")
                .state(RestState.OK)
                .build();
        responseDTO.addParam("room", new RoomDTO(room));
        return responseDTO;
    }

    @PostMapping("/{roomId}/update")
    public RestResponseDTO updateRoom(@PathVariable Long roomId, @Validated @RequestBody UpdateRoomDTO updateRoomDTO,
                                      BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", updateRoomDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.builder()
                    .state(RestState.FAIL)
                    .build();
        }

        Room room = roomService.findRoom(roomId);
        room.setLocation(updateRoomDTO.getLocation());
        room.setHo(updateRoomDTO.getHo());

        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .message("강의실 수정")
                .state(RestState.OK)
                .build();
        responseDTO.addParam("room", new RoomDTO(room));
        return responseDTO;
    }

    @PostMapping("/{roomId}/delete")
    public RestResponseDTO deleteRoom(@PathVariable Long roomId){
        Room room = roomService.findRoom(roomId);
        room = roomService.deleteRoom(room);
        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .message("강의실 삭제")
                .state(RestState.OK)
                .build();
        responseDTO.addParam("room", new RoomDTO(room));
        return responseDTO;
    }

}

package com.todrepus.enrollmentsys.web.admin.room;

import com.todrepus.enrollmentsys.domain.room.Room;
import com.todrepus.enrollmentsys.domain.room.RoomRepository;
import com.todrepus.enrollmentsys.domain.room.RoomService;
import com.todrepus.enrollmentsys.web.RestResponseDTO;
import com.todrepus.enrollmentsys.web.admin.AdminPageConst;
import com.todrepus.enrollmentsys.web.admin.room.dto.AddRoomDTO;
import com.todrepus.enrollmentsys.web.admin.room.dto.RoomResponseDTO;
import com.todrepus.enrollmentsys.web.admin.room.dto.UpdateRoomDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/rooms")
public class RoomRestController {
    private final RoomRepository roomRepository;
    private final RoomService roomService;

    @GetMapping("")
    public RestResponseDTO<List<RoomResponseDTO>> getRooms(@RequestParam(required = false) Integer page){
        if (page == null || page <= 0){
            page = 1;
        }

        List<Room> rooms = roomRepository.findAll();
        int size = rooms.size();
        int maxPage = Math.ceilDiv(size, AdminPageConst.ELEMENT_NUM);
        int start = (page - 1) * AdminPageConst.ELEMENT_NUM;
        List<RoomResponseDTO> roomsOnPage = rooms.stream().skip(start)
                .limit(AdminPageConst.ELEMENT_NUM)
                .map(RoomResponseDTO::new).toList();

        RestResponseDTO<List<RoomResponseDTO>> responseDTO =
                RestResponseDTO.getSuccessResponse("강의실 목록 조회");
        responseDTO.setData(roomsOnPage);
        responseDTO.addParam("nowPage", page);
        responseDTO.addParam("maxPage", maxPage);
        return responseDTO;
    }

    @GetMapping("/{roomId}")
    public RestResponseDTO<RoomResponseDTO> getRoomById(@PathVariable Long roomId){
        Room room = roomService.findRoom(roomId);
        RestResponseDTO<RoomResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("강의실 조회 성공");
        responseDTO.setData(new RoomResponseDTO(room));
        return responseDTO;
    }

    @PostMapping("/add")
    public RestResponseDTO<RoomResponseDTO> addRoom(@Validated @RequestBody AddRoomDTO addRoomDTO,
                                   BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", addRoomDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("강의실 생성 실패");
        }

        Room room = Room.builder()
                .location(addRoomDTO.getLocation())
                .ho(addRoomDTO.getHo())
                .build();

        roomRepository.save(room);

        RestResponseDTO<RoomResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("강의실 생성 성공");
        responseDTO.setData(new RoomResponseDTO(room));
        return responseDTO;
    }

    @PostMapping("/{roomId}/update")
    public RestResponseDTO<RoomResponseDTO> updateRoom(@PathVariable Long roomId, @Validated @RequestBody UpdateRoomDTO updateRoomDTO,
                                      BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", updateRoomDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("강의실 업데이트 실패");
        }

        Room room = roomService.findRoom(roomId);
        room.setLocation(updateRoomDTO.getLocation());
        room.setHo(updateRoomDTO.getHo());

        RestResponseDTO<RoomResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("강의실 업데이트 성공");
        responseDTO.setData(new RoomResponseDTO(room));
        return responseDTO;
    }

    @PostMapping("/{roomId}/delete")
    public RestResponseDTO<RoomResponseDTO> deleteRoom(@PathVariable Long roomId){
        Room room = roomService.findRoom(roomId);
        room = roomService.deleteRoom(room);
        RestResponseDTO<RoomResponseDTO> responseDTO =
                RestResponseDTO.getSuccessResponse("강의실 삭제 성공");
        responseDTO.setData(new RoomResponseDTO(room));
        return responseDTO;
    }

}

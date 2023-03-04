package com.todrepus.enrollmentsys.web.department;

import com.todrepus.enrollmentsys.domain.department.Department;
import com.todrepus.enrollmentsys.domain.department.DepartmentRepository;
import com.todrepus.enrollmentsys.domain.department.DepartmentService;
import com.todrepus.enrollmentsys.web.RestResponseDTO;
import com.todrepus.enrollmentsys.web.admin.AdminPageConst;
import com.todrepus.enrollmentsys.web.department.dto.AddDepartmentDTO;
import com.todrepus.enrollmentsys.web.department.dto.DepartmentResponseDTO;
import com.todrepus.enrollmentsys.web.department.dto.UpdateDepartmentDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentRestController {
    private final DepartmentService departmentService;
    private final DepartmentRepository departmentRepository;
    
    @GetMapping("")
    public RestResponseDTO<List<DepartmentResponseDTO>> getDepartments(@RequestParam(required = false) Integer page){
        if (page == null || page < 0){
            page = 0;
        }

        Page<Department> pageDepartment = departmentService.findDepartmentsOnPage(page, AdminPageConst.ELEMENT_NUM);
        List<Department> departments = pageDepartment.getContent();
        int maxPage = pageDepartment.getTotalPages();
        List<DepartmentResponseDTO> departmentsOnPage = departments.stream()
                .map(DepartmentResponseDTO::new)
                .toList();

        RestResponseDTO<List<DepartmentResponseDTO>> responseDTO =
                RestResponseDTO.getSuccessResponse("학과 목록 조회");
        responseDTO.setData(departmentsOnPage);
        responseDTO.addParam("nowPage", page);
        responseDTO.addParam("maxPage", maxPage);
        return responseDTO;
    }

    @GetMapping("/{id}")
    public RestResponseDTO<DepartmentResponseDTO> getDepartment(@PathVariable Long id){
        Department department = departmentService.findDepartment(id);
        DepartmentResponseDTO departmentResponseDTO = new DepartmentResponseDTO(department);

        RestResponseDTO<DepartmentResponseDTO> responseDTO = RestResponseDTO.getSuccessResponse("학과 조회");
        responseDTO.setData(departmentResponseDTO);

        return responseDTO;
    }

    @PostMapping("/add")
    public RestResponseDTO<DepartmentResponseDTO> addDepartment(@Validated @RequestBody AddDepartmentDTO addDepartmentDTO,
                                                                BindingResult bindingResult, HttpServletResponse httpServletResponse){
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("학과 추가 실패");
        }

        Department department = new Department();
        department.setName(addDepartmentDTO.getName());

        department = departmentRepository.save(department);

        RestResponseDTO<DepartmentResponseDTO> responseDTO = RestResponseDTO.getSuccessResponse("학과 추가");
        responseDTO.setData(new DepartmentResponseDTO(department));
        return responseDTO;
    }

    @PostMapping("/{id}/delete")
    public RestResponseDTO<DepartmentResponseDTO> deleteDepartment(@PathVariable Long id){
        Department department = departmentService.findDepartment(id);
        departmentRepository.delete(department);

        RestResponseDTO<DepartmentResponseDTO> responseDTO = RestResponseDTO.getSuccessResponse("학과 삭제");
        responseDTO.setData(new DepartmentResponseDTO(department));

        return responseDTO;
    }

    @PostMapping("/{id}/update")
    public RestResponseDTO<DepartmentResponseDTO> deleteDepartment(@PathVariable Long id, @Validated @RequestBody UpdateDepartmentDTO updateDepartmentDTO,
                                                                   BindingResult bindingResult, HttpServletResponse httpServletResponse){
        log.debug("{}", updateDepartmentDTO);
        if (bindingResult.hasErrors()){
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("학과 업데이트 실패");
        }

        Department department = departmentService.findDepartment(id);
        department.setName(updateDepartmentDTO.getName());

        RestResponseDTO<DepartmentResponseDTO> responseDTO = RestResponseDTO.getSuccessResponse("학과 업데이트");
        responseDTO.setData(new DepartmentResponseDTO(department));
        return responseDTO;
    }
}

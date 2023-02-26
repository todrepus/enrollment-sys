package com.todrepus.enrollmentsys.domain.department;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Department findDepartment(Long id){
        return departmentRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 id를 가진 department가 존재하지 않습니다.")
        );
    }
}

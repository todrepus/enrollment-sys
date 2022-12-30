package com.todrepus.enrollmentsys.web.admin;

import com.todrepus.enrollmentsys.domain.course.Course;
import com.todrepus.enrollmentsys.domain.course.CourseRepository;
import com.todrepus.enrollmentsys.domain.member.Member;
import com.todrepus.enrollmentsys.domain.member.MemberRepository;
import com.todrepus.enrollmentsys.domain.member.MemberService;
import com.todrepus.enrollmentsys.domain.member.Role;
import com.todrepus.enrollmentsys.web.RestResponseDTO;
import com.todrepus.enrollmentsys.web.RestState;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@RequestMapping("/api/admin")
@RestController
public class AdminRestController {
    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final MemberService memberService;

    /*
    멤버 수정 및 관리
    1. 멤버 목록 조회 -> 멤버 내용을 JSON으로 보내주는 기능 필요.
    -> 이게 admin에 있어야하나? -> 근데 멤버 목록조회를 관리자말고 할 일이 있나?
    -> 그리고, 페이징 처리가 되어야함... 굳이 모든 멤버를 받을 필요가 없잖음??
    -> findSubList 구현은, @Query로 재 구현해야되는것 같음.
    -> 아직 Querydsl을 공부하긴 힘드니, 일단 findAll로 구현하고 나중에 수정하도록 하자.
    2. 새로운 맴버 추가
    3. 특정 멤버 수정
     */

    @SuppressWarnings("현재 모든 멤버를 조회하도록 구현됨. -> Querydsl로 일부만 받도록 수정해야함.")
    @GetMapping("/members")
    public RestResponseDTO getMembers(@RequestParam(required = false) Integer page) {
        if (page == null || page <= 0)
            page = 1;

        List<Member> members = memberRepository.findAll();
        int size = members.size();
        int maxPage = Math.ceilDiv(size, AdminPageConst.ELEMENT_NUM);
        int start = (page - 1) * AdminPageConst.ELEMENT_NUM;
        List<MemberResponseDTO> membersOnPage = members.stream().skip(start)
                .limit(AdminPageConst.ELEMENT_NUM)
                .map(MemberResponseDTO::new).toList();

        RestResponseDTO response = RestResponseDTO.builder()
                .state(RestState.OK)
                .build();

        response.addParam("data", membersOnPage);
        response.addParam("maxPage", maxPage);
        return response;
    }

    @PostMapping("/members/add")
    public RestResponseDTO addMember(@Validated @RequestBody JoinRequestDTO joinRequestDTO, BindingResult bindingResult,
                                     HttpServletResponse httpServletResponse) {
        log.debug("{}",joinRequestDTO);
        if (bindingResult.hasErrors()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.builder()
                    .state(RestState.FAIL).build();
        }

        String userId = joinRequestDTO.getUserId();
        String name = joinRequestDTO.getName();
        ;
        String password = joinRequestDTO.getPassword();
        ;
        Role role = joinRequestDTO.getRole();

        Member newMember = Member.builder()
                .userId(userId)
                .name(name)
                .password(password)
                .role(role).build();

        Member result = memberService.joinMember(newMember);
        log.debug("newMember {}", newMember);
        log.debug("result {}", result);
        if (result == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return RestResponseDTO.builder()
                    .state(RestState.FAIL)
                    .message("가입에 실패하였습니다.")
                    .build();
        } else {
            RestResponseDTO response = RestResponseDTO.builder()
                                        .state(RestState.OK)
                                        .message("가입에 성공하였습니다.")
                                        .build();

            MemberResponseDTO memberResponseDTO = new MemberResponseDTO(result);
            response.addParam("data", memberResponseDTO);
            return response;
        }
    }

    @SuppressWarnings("courseIdList부분을 한번에 조회하도록 고쳐봐야함.")
    @PostMapping("/members/{id}/update")
    public RestResponseDTO updateMember(@PathVariable Long id, @Validated @RequestBody UpdateRequestDTO updateRequestDTO,
                                        BindingResult bindingResult, HttpServletResponse httpServletResponse) {
        log.debug("{}", updateRequestDTO);
        if (bindingResult.hasErrors()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.builder()
                    .state(RestState.FAIL)
                    .build();
        }

        Member member = memberRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("해당 id를 가진 member를 찾지 못했습니다"));

        String name = updateRequestDTO.getName();
        String password = updateRequestDTO.getPassword();
        Role role = updateRequestDTO.getRole();
        List<Long> courseIdList = updateRequestDTO.getCourseIdList();
        Set<Course> courseSet = courseIdList.stream().map(
                (Long courseId) -> courseRepository.findById(courseId)
                        .orElseThrow(() -> new NoSuchElementException("해당 id를 가진 course를 찾지 못했습니다")))
                        .collect(Collectors.toSet());

        Member updatedMember = member.update(name, password, role, courseSet);
        log.debug("updateMember : {}", updatedMember);

        return RestResponseDTO.builder()
                .state(RestState.OK)
                .message("update에 성공했습니다.")
                .build();
    }

    @GetMapping("/members/{id}")
    public RestResponseDTO getMember(@PathVariable Long id){
        Member foundMember = memberRepository.findById(id).orElseThrow(
                ()-> new NoSuchElementException("해당 id를 가진 member를 찾지 못했습니다.")
        );

        RestResponseDTO responseDTO = RestResponseDTO.builder()
                .state(RestState.OK)
                .message("조회")
                .build();
        responseDTO.addParam("member", foundMember);
        return responseDTO;
    }

    /*
    강의 수정 및 관리
     */

}

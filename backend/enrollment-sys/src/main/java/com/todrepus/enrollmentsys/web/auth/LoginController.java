package com.todrepus.enrollmentsys.web.auth;

import com.todrepus.enrollmentsys.domain.member.Member;
import com.todrepus.enrollmentsys.domain.member.MemberService;
import com.todrepus.enrollmentsys.web.RestResponseDTO;
import com.todrepus.enrollmentsys.web.auth.dto.LoginDTO;
import com.todrepus.enrollmentsys.web.auth.dto.SimpleMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class LoginController {
    private final MemberService memberService;

    @GetMapping("/info")
    public RestResponseDTO<SimpleMember> getLoginInfo(@SessionAttribute(name = LoginConst.LOGIN_SIMPLE_MEMBER, required = false) SimpleMember loginMember,
                                                           HttpServletResponse response) {
        RestResponseDTO<SimpleMember> responseDTO;
        if (loginMember == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseDTO = RestResponseDTO.getInternalErrorResponse("로그인이 되어있지 않습니다.");
            responseDTO.addParam("callback", "/login");
            return responseDTO;
        }

        responseDTO = RestResponseDTO.getSuccessResponse("로그인 멤버정보");
        responseDTO.setData(loginMember);

        return responseDTO;
    }
    @PostMapping("/login")
    public RestResponseDTO<Object> login(@Validated @RequestBody LoginDTO loginDTO, BindingResult bindingResult,
                                         HttpServletRequest request, HttpServletResponse response){
        log.debug("{}", loginDTO);

        if (bindingResult.hasErrors()){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("아이디 혹은 비밀번호가 공란입니다.");
        }

        String userId = loginDTO.getUserId();
        String password = loginDTO.getPassword();

        Member loginMember = memberService.login(userId, password);
        if (loginMember == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return RestResponseDTO.getBadRequestResponse("아이디 혹은 비밀번호가 잘못되었습니다.");
        }

        HttpSession session = request.getSession();
        session.setAttribute(LoginConst.LOGIN_SIMPLE_MEMBER, new SimpleMember(loginMember));

        RestResponseDTO<Object> responseDTO = RestResponseDTO.getSuccessResponse("로그인 성공");
        responseDTO.addParam("callback", "/home");
        return responseDTO;
    }

    @PostMapping("/logout")
    public RestResponseDTO<Object> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        return RestResponseDTO.getSuccessResponse("로그아웃 성공");
    }
}

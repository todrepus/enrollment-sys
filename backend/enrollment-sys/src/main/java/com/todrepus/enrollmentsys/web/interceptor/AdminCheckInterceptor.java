package com.todrepus.enrollmentsys.web.interceptor;

import com.todrepus.enrollmentsys.domain.member.Role;
import com.todrepus.enrollmentsys.web.auth.LoginConst;
import com.todrepus.enrollmentsys.web.auth.dto.SimpleMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class AdminCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SimpleMember member = (SimpleMember)request.getAttribute(LoginConst.LOGIN_SIMPLE_MEMBER);
        if (member == null){
            log.info("로그인 안되어있음.");
            return false;
        }
        if (member.getRole() != Role.ADMIN){
            log.info("관리자가 아님");
            return false;
        }
        return true;
    }
}


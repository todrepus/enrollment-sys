package com.todrepus.enrollmentsys.web.interceptor;

import com.todrepus.enrollmentsys.web.auth.LoginConst;
import com.todrepus.enrollmentsys.web.auth.dto.SimpleMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null){
            log.info("로그인 안되어있음");
            return false;
        }
        SimpleMember member = (SimpleMember)session.getAttribute(LoginConst.LOGIN_SIMPLE_MEMBER);
        if (member == null){
            log.info("로그인 안되어있음");
            return false;
        }

        request.setAttribute(LoginConst.LOGIN_SIMPLE_MEMBER, member);
        return true;
    }
}

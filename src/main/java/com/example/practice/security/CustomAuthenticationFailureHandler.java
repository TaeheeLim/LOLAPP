package com.example.practice.security;

import com.example.practice.entity.Member;
import com.example.practice.mapper.MemberMapper;
import com.example.practice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private String username;
    private String password;
    private String loginRedirectUrl;
    private String exceptionMsgName;
    private String defaultFailureUrl;

    private final MemberMapper memberMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");
        String msg = "";
        if(exception instanceof BadCredentialsException){
            msg = failCnt(username);
        }else if(exception instanceof InternalAuthenticationServiceException){
            msg = "Account";
        }else if(exception instanceof InsufficientAuthenticationException){
            msg = "Secret";
        }else if(exception instanceof LockedException){
            msg = "Disable";
        }else if(exception instanceof SessionAuthenticationException){
            msg = "Duplicate";
        }else if(exception instanceof DisabledException){
            System.out.print(exception);
        }else{
            System.out.print(exception);
        }
        setDefaultFailureUrl("/login?error=true&exception=" + msg);
        super.onAuthenticationFailure(request, response, exception);
    }

    public String failCnt(String loginId){
        Member member = memberMapper.selectMember(loginId);
        if(member == null){
            return "Account";
        }
        if(member.getLoginFailCount() >= 5){
            return "Disable";
        } else {
            memberMapper.updateFailCount(loginId);
            Member getMember = memberMapper.selectMember(loginId);
            return "Password&cnt=" + getMember.getLoginFailCount();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginRedirectUrl() {
        return loginRedirectUrl;
    }

    public void setLoginRedirectUrl(String loginRedirectUrl) {
        this.loginRedirectUrl = loginRedirectUrl;
    }

    public String getExceptionMsgName() {
        return exceptionMsgName;
    }

    public void setExceptionMsgName(String exceptionMsgName) {
        this.exceptionMsgName = exceptionMsgName;
    }

    public String getUrl() {
        return defaultFailureUrl;
    }

    public void setUrl(String url) {
        this.defaultFailureUrl = url;
    }
}

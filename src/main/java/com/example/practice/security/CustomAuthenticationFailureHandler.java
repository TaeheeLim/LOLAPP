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

    private final MessageSource messageSource;
    private final MemberMapper memberMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String loginId = request.getParameter("username");
        String errormsg = exception.getMessage();

        if(exception instanceof BadCredentialsException) {
            errormsg = failCnt(loginId);
            // 잠긴계정인지 확인하여, errormsg변경해준다.
//            boolean userUnLock = true;
//            userUnLock = failCnt(loginId);
//            if ( !userUnLock )
//                errormsg = messageSource.getMessage("AccountStatusUserDetailsChecker.disabled", null , Locale.KOREA);
//            else
//                errormsg = messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", null , Locale.KOREA);
        } else if(exception instanceof InternalAuthenticationServiceException) {
            errormsg = messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.InternalAuthentication", null , Locale.KOREA);
        } else if(exception instanceof DisabledException) {
            errormsg = messageSource.getMessage("AccountStatusUserDetailsChecker.disabled", null , Locale.KOREA);
        } else if(exception instanceof CredentialsExpiredException) {
            errormsg = messageSource.getMessage("AccountStatusUserDetailsChecker.expired", null , Locale.KOREA);
        } else if(exception instanceof UsernameNotFoundException) {
            Object[] args = new String[] { loginId } ;
            errormsg = messageSource.getMessage("DigestAuthenticationFilter.usernameNotFound", args , Locale.KOREA);
        } else if(exception instanceof AccountExpiredException) {
            errormsg = messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.expired", null , Locale.KOREA);
        } else if(exception instanceof LockedException) {
            errormsg = messageSource.getMessage("AbstractUserDetailsAuthenticationProvider.locked", null , Locale.KOREA);
        }

        setDefaultFailureUrl("/login?error=true&exception="+errormsg);
        super.onAuthenticationFailure(request, response, exception);
    }

    public String failCnt(String loginId){
        Member member = memberMapper.selectMember(loginId);
        if(member.getLoginFailCount() >= 5){
            return "Disabled";
        } else {
            memberMapper.updateFailCount(loginId);
            Member getMember = memberMapper.selectMember(loginId);
            return "Password&cnt = " + getMember.getLoginFailCount();
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

package com.example.practice.service;

import com.example.practice.entity.Member;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    int insertMember(Member member);

    boolean checkIsMemberIdExist(String memId);

    boolean checkIsMemberNickExist(String memNick);

    boolean checkIsMemberEmailExist(String email);

    Member selectMember(String memIdx);

    boolean forgotPassword(Member member);

    int updatePassword(String memPassword, String memId);
}

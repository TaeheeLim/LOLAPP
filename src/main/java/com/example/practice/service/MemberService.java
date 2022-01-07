package com.example.practice.service;

import com.example.practice.entity.Member;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    int insertMember(Member member);
}

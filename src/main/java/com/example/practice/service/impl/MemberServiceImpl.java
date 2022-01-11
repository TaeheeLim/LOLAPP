package com.example.practice.service.impl;

import com.example.practice.Role;
import com.example.practice.entity.AccountContext;
import com.example.practice.entity.Member;
import com.example.practice.mapper.MemberMapper;
import com.example.practice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;


    //로그인 하면 일로옴 by Spring Security
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberMapper.selectMember(id);

        if(member == null){
            throw new UsernameNotFoundException(id + "is not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.ROLE_USER.getCode()));

        AccountContext accountContext = new AccountContext(member, authorities);
        return accountContext;
    }

    public int insertMember(Member member){
        member.setMemPassword(passwordEncoder.encode(member.getMemPassword()));
        member.setMemRole(Role.ROLE_USER.getCode());
        member.setSignDate(LocalDate.now());
        return memberMapper.insertMember(member);
    }

    @Override
    public boolean checkIsMemberIdExist(String memId) {
        int result = memberMapper.checkIsMemberIdExist(memId);
        if(result != 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkIsMemberNickExist(String memNick) {
        int result = memberMapper.checkIsMemberNickExist(memNick);
        if(result != 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean checkIsMemberEmailExist(String email){
        Member member = memberMapper.selectMemberByEmail(email);
        if(member != null){
            return true;
        }
        return false;
    }

    @Override
    public Member selectMember(String memIdx){
        return memberMapper.selectMember(memIdx);
    }

    @Override
    public boolean forgotPassword(Member member) {
        Member isMember = memberMapper.forgotPassword(member);
        if(isMember != null){
            return true;
        }
        return false;
    }

    @Override
    public int updatePassword(String memPassword, String memId) {
        return memberMapper.updatePassword(memPassword, memId);
    }

}

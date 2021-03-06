package com.example.practice.mapper;

import com.example.practice.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MemberMapper {
    List<Member> findAll();

    Member selectMember(String id);

    int insertMember(Member member);

    Member selectMemberByEmail(String email);

    int checkIsMemberIdExist(String memId);

    int checkIsMemberNickExist(String memNick);

    Member forgotPassword(Member member);

    int updatePassword(String memPassword, String memId);

    int updateFailCount(String memId);

    int resetFailCount(String memId);
}

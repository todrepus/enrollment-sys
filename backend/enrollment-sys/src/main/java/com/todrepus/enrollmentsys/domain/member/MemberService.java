package com.todrepus.enrollmentsys.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public Member joinMember(Member member){
        if (memberRepository.findByUserId(member.getUserId()).isPresent()){
            log.info("userId : {} 는 이미 있는 아이디 입니다.", member.getUserId());
            return null;
        }

        return memberRepository.save(member);
    }
}

package com.laundrygo.shorturl.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.laundrygo.shorturl.domain.Member;
import com.laundrygo.shorturl.repository.MemberRepository;
import com.laundrygo.shorturl.service.response.MemberResponse;

import lombok.RequiredArgsConstructor;

/**
 * @author laundrygo
 * @version 0.1.0
 * @since 2021/06/22 7:06 오후
 */
@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public List<MemberResponse> findAll() {
		List<Member> members = memberRepository.findAll();
		return members.stream().map(MemberResponse::of).collect(Collectors.toList());
	}
}

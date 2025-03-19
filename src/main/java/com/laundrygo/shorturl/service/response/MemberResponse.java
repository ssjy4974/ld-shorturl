package com.laundrygo.shorturl.service.response;

import com.laundrygo.shorturl.domain.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {
	private Integer id;
	private String name;

	public static MemberResponse of(Member member) {
		return MemberResponse.builder()
			.id(member.getId())
			.name(member.getName())
			.build();
	}
}

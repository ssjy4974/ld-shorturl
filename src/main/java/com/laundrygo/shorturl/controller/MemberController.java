package com.laundrygo.shorturl.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.laundrygo.shorturl.service.MemberService;
import com.laundrygo.shorturl.service.response.MemberResponse;

import lombok.RequiredArgsConstructor;

/**
 * @author laundrygo
 * @version 0.1.0
 * @since 2021/06/22 7:05 오후
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
	private final MemberService memberService;

	@GetMapping
	public ResponseEntity<List<MemberResponse>> findAll() {
		return ResponseEntity.ok(memberService.findAll());
	}
}

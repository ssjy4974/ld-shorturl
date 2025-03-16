package com.laundrygo.shorturl.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import com.laundrygo.shorturl.domain.Member;

/**
 * @author laundrygo
 * @version 0.1.0
 * @since 2021/06/22 7:06 오후
 */
@Mapper
public interface MemberRepository extends JpaRepository<Member, Long> {
	List<Member> findAll();
}

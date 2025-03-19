package com.laundrygo.shorturl.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.laundrygo.shorturl.domain.UrlAccessHistory;
import com.laundrygo.shorturl.repository.response.UrlStatResponse;

@Repository
public interface UrlAccessHistoryRepository extends JpaRepository<UrlAccessHistory, Long> {

	@Query(value =
		"SELECT new com.laundrygo.shorturl.repository.response.UrlStatResponse(h.shortUrl, h.originUrl, COUNT(h.id)) " +
			"FROM UrlAccessHistory h " +
			"WHERE h.requestDttm >= :startTime " +
			"GROUP BY h.shortUrl, h.originUrl " +
			"ORDER BY COUNT(h.id) DESC",
		countQuery = "SELECT COUNT(DISTINCT h.shortUrl) " +
			"FROM UrlAccessHistory h " +
			"WHERE h.requestDttm >= :startTime")
	Page<UrlStatResponse> findRequestCountsLast24HoursWithPaging(
		@Param("startTime") LocalDateTime startTime,
		Pageable pageable);

	List<UrlAccessHistory> findByShortUrl(String shortUrl);
}

package com.laundrygo.shorturl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laundrygo.shorturl.domain.UrlAccessHistory;

@Repository
public interface UrlAccessHistoryRepository extends JpaRepository<UrlAccessHistory, Long> {
	List<UrlAccessHistory> findByShortUrl(String shortUrl);
}

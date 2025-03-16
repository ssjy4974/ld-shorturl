package com.laundrygo.shorturl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laundrygo.shorturl.domain.UrlAccessHistory;

public interface UrlAccessHistoryRepository extends JpaRepository<UrlAccessHistory, Long> {
}

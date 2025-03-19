package com.laundrygo.shorturl.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laundrygo.shorturl.domain.UrlMapping;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
	Optional<UrlMapping> findByOriginUrl(String originalUrl);

	Optional<UrlMapping> findByShortUrl(String shortUrl);
}

package com.laundrygo.shorturl.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.laundrygo.shorturl.repository.UrlAccessHistoryRepository;
import com.laundrygo.shorturl.repository.UrlMappingRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShortUrlService {
	private final UrlMappingRepository urlMappingRepository;
	private final UrlAccessHistoryRepository urlAccessHistoryRepository;
}

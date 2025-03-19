package com.laundrygo.shorturl.repository.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UrlStatResponse {
	private String shortUrl;
	private String originUrl;
	private Long requestCount;
}

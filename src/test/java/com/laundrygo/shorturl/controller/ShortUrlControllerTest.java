package com.laundrygo.shorturl.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import com.laundrygo.shorturl.ControllerTestSupport;
import com.laundrygo.shorturl.controller.request.ShortUrlRequest;

class ShortUrlControllerTest extends ControllerTestSupport {

	@Test
	@DisplayName("원본 url을 입력받아 단축 url을 반환한다. (정상응답)")
	void getShortUrl() throws Exception {
		// given
		String originUrl = "http://www.naver.com";
		ShortUrlRequest request = new ShortUrlRequest(originUrl);
		// when then
		mockMvc.perform(post("/api/v1/short-url").contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
	}

	@Test
	@DisplayName("원본 URL이 유효하지않은경우 요청은 실패한다. (null)")
	void getShortUrlWithInvalidParam_null() throws Exception {
		// given
		String originUrl = null;
		ShortUrlRequest request = new ShortUrlRequest(originUrl);
		// when then
		mockMvc.perform(post("/api/v1/short-url").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("url은 필수 값입니다."));
	}

	@Test
	@DisplayName("원본 URL이 유효하지않은경우 요청은 실패한다. (empty)")
	void getShortUrlWithInvalidParam_empty() throws Exception {
		// given
		String originUrl = "";
		ShortUrlRequest request = new ShortUrlRequest(originUrl);
		// when then
		mockMvc.perform(post("/api/v1/short-url").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("url은 필수 값입니다."));
	}

	@Test
	@DisplayName("원본 URL이 유효하지않은경우 요청은 실패한다. (InvalidUrl)")
	void getShortUrlWithInvalidParam_InvalidUrl() throws Exception {
		// given
		String originUrl = "abcdswq";
		ShortUrlRequest request = new ShortUrlRequest(originUrl);
		// when then
		mockMvc.perform(post("/api/v1/short-url").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("유효하지 않은 URL 입니다."));
	}

}
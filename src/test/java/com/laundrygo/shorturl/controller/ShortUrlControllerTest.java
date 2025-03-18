package com.laundrygo.shorturl.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

	@Test
	@DisplayName("단축url로 원본url을 조회한다.")
	public void getOriginUrl_success() throws Exception {
		// given
		String shortUrl = "abc123de";
		String originUrl = "https://www.example.com/very/long/url/path";

		when(shortUrlService.getOriginUrl(shortUrl)).thenReturn(originUrl);

		// when & then
		mockMvc.perform(get("/api/v1/origin-url/{shortUrl}", shortUrl))
			.andExpect(status().isOk())
			.andExpect(content().string(originUrl));

		verify(shortUrlService, times(1)).getOriginUrl(shortUrl);
	}

	@Test
	@DisplayName("존재하지 않는 단축URL로 원본URL 조회시 실패한다.")
	public void getOriginUrl_NotFound() throws Exception {
		// given
		String shortUrl = "notfound";

		when(shortUrlService.getOriginUrl(shortUrl))
			.thenThrow(new NoSuchElementException("요청정보가 존재하지 않습니다."));

		// when & then
		mockMvc.perform(get("/api/v1/origin-url/{shortUrl}", shortUrl))
			.andExpect(status().isNotFound());

		verify(shortUrlService, times(1)).getOriginUrl(shortUrl);
	}

	@ParameterizedTest
	@ValueSource(strings = {"abc-123", "abcdefghi"})
	@DisplayName("유효하지 않는 단축URL로 원본URL 조회 시 실패한다.")
	public void getOriginUrl_BadRequest(String invalidShortUrl) throws Exception {
		// when & then
		mockMvc.perform(get("/api/v1/origin-url/{shortUrl}", invalidShortUrl))
			.andExpect(status().isBadRequest());

		// UrlValidator에서 예외가 발생하므로 서비스까지 호출되지 않음
		verify(shortUrlService, never()).getOriginUrl(invalidShortUrl);
	}

}
package com.laundrygo.shorturl.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlRequest {
	@NotBlank(message = "url은 필수 값입니다.")
	@Pattern(regexp = "^(https?)://([a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)(?::\\d+)?(/.*)?$", message = "유효하지 않은 URL 입니다.")
	private String originUrl;
}

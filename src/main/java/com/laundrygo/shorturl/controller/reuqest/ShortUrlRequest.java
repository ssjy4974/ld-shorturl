package com.laundrygo.shorturl.controller.reuqest;

import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShortUrlRequest {
	@Pattern(regexp = "^(https?)://([a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)(?::\\d+)?(/.*)?$", message = "유효하지 않은 URL 입니다.")
	private String originUrl;
}

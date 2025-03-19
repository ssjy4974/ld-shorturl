package com.laundrygo.shorturl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laundrygo.shorturl.controller.ShortUrlController;
import com.laundrygo.shorturl.service.ShortUrlService;

@WebMvcTest(controllers = {ShortUrlController.class})
public class ControllerTestSupport {
	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected ObjectMapper objectMapper;
	@MockBean
	protected ShortUrlService shortUrlService;
}

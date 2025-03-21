package com.laundrygo.shorturl.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlAccessHistory extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, length = 255)
	private String originUrl;
	@Column(nullable = false, length = 8)
	private String shortUrl;
	@Column(nullable = false)
	private LocalDateTime requestDttm;

	public static UrlAccessHistory create(String originUrl, String shortUrl) {
		UrlAccessHistory urlAccessHistory = new UrlAccessHistory();
		urlAccessHistory.originUrl = originUrl;
		urlAccessHistory.shortUrl = shortUrl;
		urlAccessHistory.requestDttm = LocalDateTime.now();
		return urlAccessHistory;
	}

	public void setTestRequestDttm(LocalDateTime time) {
		this.requestDttm = time;
	}
}

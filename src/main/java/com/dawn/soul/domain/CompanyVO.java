package com.dawn.soul.domain;

import lombok.Data;

@Data
public class CompanyVO {
	
	private int companyNo; // 상조회사 번호 (pk)
	private String companyName; // 상조회사명
	private String searchToken; // 검색용
}

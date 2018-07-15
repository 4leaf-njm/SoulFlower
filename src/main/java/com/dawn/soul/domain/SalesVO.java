package com.dawn.soul.domain;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class SalesVO {
	
	private int salesNo; 
	private String areaName; // 지역명
	private String companyName; // 상조회사명
	private String funeral; // 장례식장
	private String deadName; // 고인명
	private String hosil;  // 호실
	private String leader;  // 팀장명
	private Date salesDate;  // 영업일
	private Timestamp regDate; // 등록일
	private String depyn; // 입금 여부
	private int noneDep; // 미수금
}

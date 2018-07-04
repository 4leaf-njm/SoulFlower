package com.dawn.soul.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AdminVO {
	
	private String adminId; // 관리자 아이디 (pk)
	private String adminPwd; // 관리자 비밀번호 
	private String adminName; // 관리자 이름
	private Timestamp adminRegdate; // 관리자 가입일
	private String adminUseyn; // 관리자 사용유무
	private String adminRootyn; // 관리자 루트 권한 여부
}

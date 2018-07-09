package com.dawn.soul.domain;

import lombok.Data;

@Data
public class SalesDetailVO {
	
	private int salesDetNo;
	private String itemName; // 품목
	private int itemPrice; // 가격
	private int amount; // 수량
	private int profit; // 수익
	private int rebate; // 리베이트
	private String rebateType; // per: 비율, self: 직접입력
	private int rebateNum; // 리베이트 설정가
	private int salesNo;
}

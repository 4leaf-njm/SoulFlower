package com.dawn.soul.domain;

import lombok.Data;

@Data
public class ItemVO {
	
	private int itemNo; // 품목 번호 (pk)
	private String itemName; // 품목명
	private String itemUnit; // 단위
}

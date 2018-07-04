package com.dawn.soul.domain;

import java.util.List;

import lombok.Data;

@Data
public class SalesListRequest {
	
	private List<SalesDetailVO> salesList;
}

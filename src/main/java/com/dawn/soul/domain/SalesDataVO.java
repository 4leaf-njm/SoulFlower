package com.dawn.soul.domain;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SalesDataVO {
	private List<SalesVO> salesList;
	private List<String> amountList;
	private Map<Integer, List<SalesDetailVO>> salesDetMap;
	private int profit;
	private int rebate;
	private int realProfit;
}


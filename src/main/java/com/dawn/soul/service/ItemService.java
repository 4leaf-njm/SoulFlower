package com.dawn.soul.service;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.ItemVO;

public interface ItemService {

	List<ItemVO> getItemList() throws SQLException;
	
	ItemVO getItemById(int itemNo) throws SQLException;
	
	void insertItem(ItemVO item) throws SQLException;
	
	void modifyItem(ItemVO item) throws SQLException;
	
	void removeItem(int itemNo) throws SQLException;
	
}

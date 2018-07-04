package com.dawn.soul.dao;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.ItemVO;

public interface ItemDAO {

	List<ItemVO> selectItemList() throws SQLException;

	ItemVO selectItemByNo(int itemNo) throws SQLException;

	void insertItem(ItemVO item) throws SQLException;

	void updateItem(ItemVO item) throws SQLException;

	void deleteItem(int itemNo) throws SQLException;
}

package com.dawn.soul.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawn.soul.dao.ItemDAO;
import com.dawn.soul.domain.ItemVO;
import com.dawn.soul.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemDAO itemDAO;
	
	@Override
	public List<ItemVO> getItemList() throws SQLException {
		return itemDAO.selectItemList();
	}

	@Override
	public ItemVO getItemById(int itemNo) throws SQLException {
		return itemDAO.selectItemByNo(itemNo);
	}

	@Override
	public void insertItem(ItemVO item) throws SQLException {
		itemDAO.insertItem(item);
	}

	@Override
	public void modifyItem(ItemVO item) throws SQLException {
		itemDAO.updateItem(item);
	}

	@Override
	public void removeItem(int itemNo) throws SQLException {
		itemDAO.deleteItem(itemNo);
	}

}

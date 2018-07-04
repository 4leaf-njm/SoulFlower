package com.dawn.soul.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dawn.soul.dao.ItemDAO;
import com.dawn.soul.domain.ItemVO;

@Repository
public class ItemDAOImpl implements ItemDAO {

	private static final String NAMESPACE = "Item-Mapper";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<ItemVO> selectItemList() throws SQLException {
		return sqlSession.selectList(NAMESPACE + ".selectItemList");
	}

	@Override
	public ItemVO selectItemByNo(int itemNo) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + ".selectItemByNo", itemNo);
	}

	@Override
	public void insertItem(ItemVO item) throws SQLException {
		sqlSession.insert(NAMESPACE + ".insertItem", item);
	}

	@Override
	public void updateItem(ItemVO item) throws SQLException {
		sqlSession.update(NAMESPACE + ".updateItem", item);
	}

	@Override
	public void deleteItem(int itemNo) throws SQLException {
		sqlSession.delete(NAMESPACE + ".deleteItem", itemNo);
	}

}

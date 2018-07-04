package com.dawn.soul.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dawn.soul.dao.AreaDAO;
import com.dawn.soul.domain.AreaVO;

@Repository
public class AreaDAOImpl implements AreaDAO {

	private static final String NAMESPACE = "Area-Mapper";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<AreaVO> selectAreaList() throws SQLException {
		return sqlSession.selectList(NAMESPACE + ".selectAreaList");
	}

	@Override
	public AreaVO selectAreaByNo(int areaNo) throws SQLException {
		return sqlSession.selectOne(NAMESPACE + ".selectAreaByNo", areaNo);
	}

	@Override
	public void insertArea(AreaVO area) throws SQLException {
		sqlSession.insert(NAMESPACE + ".insertArea", area);
	}

	@Override
	public void updateArea(AreaVO area) throws SQLException {
		sqlSession.update(NAMESPACE + ".updateArea", area);
	}

	@Override
	public void deleteArea(int areaNo) throws SQLException {
		sqlSession.delete(NAMESPACE + ".deleteArea", areaNo);
	}

}

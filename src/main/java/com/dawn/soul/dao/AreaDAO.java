package com.dawn.soul.dao;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.AreaVO;

public interface AreaDAO {

	List<AreaVO> selectAreaList() throws SQLException;

	AreaVO selectAreaByNo(int areaNo) throws SQLException;

	void insertArea(AreaVO area) throws SQLException;

	void updateArea(AreaVO area) throws SQLException;

	void deleteArea(int areaNo) throws SQLException;
}

package com.dawn.soul.service;

import java.sql.SQLException;
import java.util.List;

import com.dawn.soul.domain.AreaVO;

public interface AreaService {

	List<AreaVO> getAreaList() throws SQLException;
	
	AreaVO getAreaById(int areaNo) throws SQLException;
	
	void insertArea(AreaVO area) throws SQLException;
	
	void modifyArea(AreaVO area) throws SQLException;
	
	void removeArea(int areaNo) throws SQLException;
	
}

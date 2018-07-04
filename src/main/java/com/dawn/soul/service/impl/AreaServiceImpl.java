package com.dawn.soul.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dawn.soul.dao.AreaDAO;
import com.dawn.soul.domain.AreaVO;
import com.dawn.soul.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDAO areaDAO;
	
	@Override
	public List<AreaVO> getAreaList() throws SQLException {
		return areaDAO.selectAreaList();
	}

	@Override
	public AreaVO getAreaById(int areaNo) throws SQLException {
		return areaDAO.selectAreaByNo(areaNo);
	}

	@Override
	public void insertArea(AreaVO area) throws SQLException {
		areaDAO.insertArea(area);
	}

	@Override
	public void modifyArea(AreaVO area) throws SQLException {
		areaDAO.updateArea(area);
	}

	@Override
	public void removeArea(int areaNo) throws SQLException {
		areaDAO.deleteArea(areaNo);
	}

}

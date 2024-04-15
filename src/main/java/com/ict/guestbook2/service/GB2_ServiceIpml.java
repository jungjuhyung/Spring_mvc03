package com.ict.guestbook2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.guestbook2.dao.GB2_DAO;
import com.ict.guestbook2.dao.GB2_VO;

@Service
public class GB2_ServiceIpml implements GB2_Service {
	
	@Autowired
	private GB2_DAO dao;
	
	@Override
	public List<GB2_VO> getList() {
		return dao.getList();
	}

	@Override
	public GB2_VO getDetail(String idx) {
		return dao.getDetail(idx);
	}

	@Override
	public int getInsert(GB2_VO vo) {
		return dao.getInsert(vo);
	}

	@Override
	public int getUpdate(GB2_VO vo) {
		return 0;
	}

	@Override
	public int getDelete(String idx) {
		return 0;
	}
	
}

package com.ict.guestbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ict.guestbook.dao.GB_DAO;
import com.ict.guestbook.dao.GB_VO;

@Service
public class GB_ServiceImpl implements GB_Service {
	@Autowired
	private GB_DAO dao;
	
	@Override
	public List<GB_VO> getGuestList() {
		return dao.getGuestList();
	}

	@Override
	public int getGuestInsert(GB_VO gvo) {
		return dao.getGuestInsert(gvo);
	}

	@Override
	public GB_VO getDetail(String idx) {
		return dao.getDetail(idx);
	}

	@Override
	public int getGuestDelete(String idx) {
		return dao.getGuestDelete(idx);
	}

	@Override
	public int getGuestUpdate(GB_VO gvo) {
		return dao.getGuestUpdate(gvo);
	}



}

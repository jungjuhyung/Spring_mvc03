package com.ict.guestbook.service;

import java.util.List;

import com.ict.guestbook.dao.GB_VO;

public interface GB_Service {
	// 리스트
	public List<GB_VO> getGuestList();
	
	// 삽입
	public  int getGuestInsert(GB_VO gvo);
	
	// 상세보기
	public GB_VO getDetail(String idx);
	
	// 삭제하기
	public  int getGuestDelete(String idx);
	
	// 수정하기
	public  int getGuestUpdate(GB_VO gvo);
}

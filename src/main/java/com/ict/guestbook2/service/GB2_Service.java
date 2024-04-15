package com.ict.guestbook2.service;

import java.util.List;

import com.ict.guestbook2.dao.GB2_VO;

public interface GB2_Service {
	// 전체 보기
	public List<GB2_VO> getList();
	
	// 한명 보기
	public GB2_VO getDetail(String idx);
	
	// 삽입
	public int getInsert(GB2_VO vo);
	
	// 수정
	public int getUpdate(GB2_VO vo);
	
	// 제거
	public int getDelete(String idx);
}

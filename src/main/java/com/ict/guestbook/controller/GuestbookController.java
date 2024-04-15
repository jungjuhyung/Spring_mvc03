package com.ict.guestbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ict.guestbook.dao.GB_VO;
import com.ict.guestbook.service.GB_Service;

import oracle.jdbc.proxy.annotation.Post;


@Controller
public class GuestbookController {
	@Autowired
	private GB_Service service; 
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("gb1_list.do")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("guestbook/list");
		List<GB_VO> list = service.getGuestList();
		if (list != null) {
			mv.addObject("list", list);
			return mv;			
		}
		return new ModelAndView("guestbook/error");
	}
	
	@GetMapping("gb1_write.do")
	public ModelAndView write() {
		ModelAndView mv = new ModelAndView("guestbook/write");
		return mv;
	}
	
	@PostMapping("gb1_writeok.do")
	public ModelAndView writeOK(GB_VO vo) {
		ModelAndView mv = new ModelAndView("redirect:gb1_list.do");
		
		String epwd = passwordEncoder.encode(vo.getPwd());
		System.out.println(epwd);
		vo.setPwd(epwd);
		int res = service.getGuestInsert(vo);
		if (res > 0) {
			return mv;
		}
		return new ModelAndView("guestbook/error");
	}
	
	@GetMapping("gb1_onelist.do")
	public ModelAndView writeOK(String idx) {
		ModelAndView mv = new ModelAndView("guestbook/onelist");
		System.out.println(idx);
		GB_VO gvo = service.getDetail(idx);
		if (gvo != null) {
			System.out.println("정상");
			mv.addObject("gvo", gvo);
			return mv;			
		}
		System.out.println("비정상");
		return new ModelAndView("guestbook/error");
	}
	
	@PostMapping("gb1_delete.do")
	public ModelAndView delete(@ModelAttribute("idx") String idx) {
		return new ModelAndView("guestbook/delete");
	}
	
	@PostMapping("gb1_delete_ok.do")
	public ModelAndView deleteOK(GB_VO gvo) {
		ModelAndView mv = new ModelAndView();
		String cpwd = gvo.getPwd();
		
		GB_VO g2vo = service.getDetail(gvo.getIdx());
		String dpwd = g2vo.getPwd();
		System.out.println(dpwd);
		if (! passwordEncoder.matches(cpwd, dpwd)) {
			mv.setViewName("guestbook/delete");
			mv.addObject("pwdchk", "fail");
			mv.addObject("idx", gvo.getIdx());
			return mv;
		}else {
			int res = service.getGuestDelete(gvo.getIdx());
			if (res > 0) {
				mv.setViewName("redirect:gb1_list.do");
				return mv;
			}
		}
		mv.setViewName("guestbook/error");
		return mv;
	}
	@PostMapping("gb1_update.do")
	public ModelAndView udate(String  idx) {
		ModelAndView mv = new ModelAndView("guestbook/update");
		GB_VO gvo = service.getDetail(idx);
		if (gvo != null) {
			mv.addObject("gvo", gvo);	
			return mv;
		}
		return new ModelAndView("guestbook/error");
	}
	@PostMapping("gb1_update_ok.do")
	public ModelAndView updateOK(GB_VO gvo) {
		System.out.println("제발");
		ModelAndView mv = new ModelAndView();
		String cpwd = gvo.getPwd();
		
		GB_VO g2vo = service.getDetail(gvo.getIdx());
		String dpwd = g2vo.getPwd();
		if (! passwordEncoder.matches(cpwd, dpwd)) {
			System.out.println("qqqqq");
			mv.setViewName("guestbook/update");
			mv.addObject("pwdchk", "fail");
			// 수정 전 내용을 되돌려 주기 : g2vo
			// 수정 후 내용을 되돌려 주기 : gvo
			mv.addObject("gvo", g2vo);
			return mv;
		}else {
			System.out.println("dddd");
			int res = service.getGuestUpdate(gvo);
			System.out.println(res);
			if (res > 0) {
				mv.setViewName("redirect:gb1_onelist.do?idx="+gvo.getIdx());
				return mv;
			}
		}
		mv.setViewName("guestbook/error");
		return mv;
	}
}
